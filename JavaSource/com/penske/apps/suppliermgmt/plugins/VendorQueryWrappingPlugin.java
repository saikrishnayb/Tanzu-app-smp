/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.plugins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.apache.log4j.Logger;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.common.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.common.util.SpringBeanHelper;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * MyBatis plugin that wraps all queries with SQL that filters the results by vendor ID.
 * It is assumed that all queries will filter by vendor ID unless explicitly marked with {@link NonVendorQuery}.
 * Thus:<ul>
 *   <li>All queries not marked with {@link NonVendorQuery} must return a field called VENDOR_ID</li>
 *   <li>If the user is not a vendor user (vendor users are userType = 2), the query will not be wrapped with any SQL</li>
 *   <li>If the user is a vendor user, any query not marked with {@link NonVendorQuery} will be wrapped with the following SQL:
 *   	<p><pre>SELECT * FROM ({query SQL here}) WHERE VENDOR_ID IN ({user's associated vendor list here})</pre></p>
 *   </li>
 * </ul>
 * @see NonVendorQuery
 */
@Intercepts({
	@Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class VendorQueryWrappingPlugin implements Interceptor
{
    private static final Logger logger = Logger.getLogger(VendorQueryWrappingPlugin.class);
	private static final String WRAPPER_SQL_PREFIX = "SELECT inside.* FROM (";
	private static final String WRAPPER_SQL_MIDDLE = ") inside WHERE VENDOR_ID IN (";
	private static final String WRAPPER_SQL_SUFFIX = ")";
	private static final String VENDOR_ID_PARAM = "__associatedVendorIds";
	
	/** {@inheritDoc} */
	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{
		Object[] args = invocation.getArgs();
		MappedStatement statement = (MappedStatement) args[0];
		
		Method invokedMethod = getInvokedQueryMethod(statement);
		if(invokedMethod == null)
			throw new IllegalStateException("Could not locate a DAO method for query " + statement.getId() + ". Can not determine whether to filter by vendor ID or not.");
		
		boolean filterByVendorId = shouldFilterByVendorId(invokedMethod);
		if(filterByVendorId)
		{
			UserContext currentUser = SpringBeanHelper.getUserContext();
			if(currentUser == null)
				throw new IllegalStateException("Could not find logged-in user for query " + statement.getId() + ". Can not determine user's type or associated vendors.");
			
			//Only do vendor ID filtering for vendor users
			if(currentUser.isVendorUser())
			{
				Set<Integer> vendorIds = new HashSet<Integer>(currentUser.getAssociatedVendorIds());
				if(vendorIds == null || vendorIds.isEmpty())
					throw new IllegalStateException("Vendor user " + currentUser.getUserSSO() + " has no associated vendor IDs. Could not filter query " + statement.getId() + " by vendor.");
				
				Object parameter = args[1];
				
				WrappedQueryMetadata wrappedQuery = getWrappedQuery(statement, parameter, vendorIds);
				args[0] = wrappedQuery.getMappedStatement();
				args[1] = wrappedQuery.getParameters();
			}
		}
		
		Object result = null;
		try {
			result = invocation.proceed();
		} catch(InvocationTargetException ex) {
			Throwable cause = ex.getCause();
			String message = cause == null ? ex.getMessage() : cause.getMessage();
			boolean isSqlException = cause != null && cause.getClass().isAssignableFrom(SQLException.class);
			if(isSqlException && (StringUtils.contains(message, "Token VENDOR_ID was not valid.") || StringUtils.contains(message, "Column or global variable VENDOR_ID not found.")))
			{
				HumanReadableException ex2 = new HumanReadableException("Could not find field VENDOR_ID in returned query results for " + statement.getId() + ". Is this query supposed to be filtered by Vendor ID? If so, make sure one of the returned fields is VENDOR_ID. If not, add @NonVendorQuery to the query method.",
						"Unable to filter results by vendor ID.", ex, true);
				throw ex2;
			}
			else if(isSqlException && StringUtils.contains(message, "Keyword AS not expected."))
			{
				HumanReadableException ex2 = new HumanReadableException("Keyword AS not expected in query: " + statement.getId() + ". Did you use a WITH clause without also setting @NonVendorQuery on the query method?",
						"Unable to filter results by vendor ID.",ex, true);
				throw ex2;
			}
			else
				throw ex;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setProperties(Properties properties) {}
	
	/**
	 * Locate the actual DAO method that was called, based on the ID of the mapped statement.
	 * This relies on the fact that each mapped statement ID is unique, so method overloading in DAOs is not allowed for MyBatis mappers.
	 * @param statement The mapped statement object from MyBatis, which contains the query metadata.
	 * @return The actual DAO method that got invoked to run the query.
	 */
	private Method getInvokedQueryMethod(MappedStatement statement)
	{
		String queryId = statement.getId();
		String className = StringUtils.substringBeforeLast(queryId, ".");
		String methodName = StringUtils.substringAfterLast(queryId, ".");
		methodName=StringUtils.substringBefore(methodName, "!");	//removing the selectKey from method name
		Class<?> daoClass;
		try {
			daoClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
		    logger.info(e);
			throw new IllegalStateException("Could not find DAO class for query " + queryId);
		}
		
		Method[] methods = daoClass.getMethods();
		if(methods == null)
			return null;
		
		for(Method m : methods)
		{
			//For a normal class, which could have overloaded methods, this would not work.
			// In this particular case, because each method in a MyBatis-Spring DAO maps to a query with the same ID as the method name,
			// and because the IDs in a MyBatis mapper have to be unique, it is not possible to have overloaded methods within a single mapper.
			// If there were such, MyBatis would generate an error on startup, so looking at just the method names and not the parameters will work in this case.
			if(m.getName().equals(methodName))
				return m;
		}
		
		return null;
	}

	/**
	 * Determines if the invoked query method needs to be filtered by vendor ID.
	 * @param queryMethod The DAO method invoked to run the current query.
	 * @return True if the query method should be filtered by vendor ID (the default position).
	 * 	False if vendor filtering should be skipped (if the {@link NonVendorQuery} annotation is present)
	 */
	private boolean shouldFilterByVendorId(Method queryMethod)
	{
		//If we're not sure, assume we should filter
		if(queryMethod == null)
			return true;
		
		return !queryMethod.isAnnotationPresent(NonVendorQuery.class);
	}
	
	/**
	 * Uses the MyBatis metatdata to extract a map of which parameters were passed into the DAO method
	 * @param statement The mapped statement from MyBatis
	 * @param boundSql The bound SQL from MyBatis
	 * @param parameterObject The parameter object from MyBatis
	 * @return A map, keyed by parameter name, of the parameters passed into the DAO method.
	 */
	private Map<String, Object> getParametersByName(MappedStatement statement, BoundSql boundSql, Object parameterObject)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		if(parameterObject instanceof Map)
		{
			@SuppressWarnings({ "unchecked"})	//MyBatis always uses a Map<String, Object> for its parameters, so it is safe to do this
			Map<String, Object> params = (Map<String, Object>)parameterObject;
            result.putAll(params);
		}
        else if( parameterObject != null)
        {
            Class<?> cls = parameterObject.getClass();
            if(cls.isPrimitive() || cls.isArray() ||
                    SimpleTypeRegistry.isSimpleType(cls) ||
                    Enum.class.isAssignableFrom(cls) ||
                    Collection.class.isAssignableFrom(cls))
            {
                for (ParameterMapping parameterMapping : parameterMappings)
                    result.put(parameterMapping.getProperty(),parameterObject);
            }
            else
            {
                MetaObject metaObject = statement.getConfiguration().newMetaObject(parameterObject);
                ObjectWrapper wrapper = metaObject.getObjectWrapper();
                for (ParameterMapping parameterMapping : parameterMappings) {
                    PropertyTokenizer prop = new PropertyTokenizer(parameterMapping.getProperty());
                    result.put(parameterMapping.getProperty(),wrapper.get(prop));
                }
            }
        }
		
		return result;
	}
	
	/**
	 * Gets metadata used to build the new (filtered) query.
	 * @param statement The original MappedStatement object from MyBatis.
	 * @param parameterObject The original parameter object from MyBatis.
	 * @param vendorIds The set of associated vendor IDs that should be used to filter results.
	 * @return Metadata object for building the new (filtered) query.
	 */
	private WrappedQueryMetadata getWrappedQuery(MappedStatement statement, Object parameterObject, Set<Integer> vendorIds)
	{
		if(vendorIds == null || vendorIds.isEmpty())
			throw new IllegalStateException("Vendor user has no associated vendor IDs. Could not filter by vendor ID for query " + statement.getId() + ".");
		
		BoundSql boundSql = statement.getBoundSql(parameterObject);
		Map<String, Object> parameters = getParametersByName(statement, boundSql, parameterObject);
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>(boundSql.getParameterMappings());
		
		addVendorIdParams(statement, parameters, parameterMappings, vendorIds);
		
		WrappedSqlSource sqlSource = new WrappedSqlSource(statement, boundSql, parameterMappings, parameters, vendorIds);
		
		//Now, create a new MappedStatement that uses the BoundSql object
		MappedStatement newMappedStatement = new Builder(statement.getConfiguration(), statement.getId(), sqlSource, statement.getSqlCommandType())
			.resource(statement.getResource())
			.fetchSize(statement.getFetchSize())
			.statementType(statement.getStatementType())
			.keyGenerator(statement.getKeyGenerator())
			.keyProperty(StringUtils.join(statement.getKeyProperties(), ","))
			.timeout(statement.getTimeout())
			.parameterMap(statement.getParameterMap())
			.resultMaps(statement.getResultMaps())
			.resultSetType(statement.getResultSetType())
			.cache(statement.getCache())
			.flushCacheRequired(statement.isFlushCacheRequired())
			.useCache(statement.isUseCache())
			.build();

		return new WrappedQueryMetadata(newMappedStatement, parameters);
	}
	
	/**
	 * Takes the existing query metadata and adds parameter mappings and parameter values for associated vendor IDs to filter by.
	 * @param statement The original MappedStatement for the original query from MyBatis.
	 * @param parameters The original map of parameters for the original query. This will have parameters added to it for the associated vendor IDs.
	 * @param parameterMappings The original list of parameters from the original query. This will have a mapping added to it for each associated vendor ID.
	 * @param vendorIds The set of vendor IDs that should be used to filter the query. One parameter mapping and one parameter will be added for each one of these IDs.
	 */
	private void addVendorIdParams(MappedStatement statement, Map<String, Object> parameters, List<ParameterMapping> parameterMappings, Set<Integer> vendorIds)
	{
		//Find a parameter name that isn't in use
		int currentParamIndex = 0;
		for(Integer vendorId : vendorIds)
		{
			
			Pair<Integer, String> nextIdInfo = getNextAvailableVendorIdIndex(parameters, currentParamIndex);
			if(nextIdInfo == null)
				throw new IllegalArgumentException("Unable to find an available parameter name for vendor ID filter for " + statement.getId() + ", vendor param prefix: " + VENDOR_ID_PARAM);
			currentParamIndex = nextIdInfo.getLeft();
			String paramName = nextIdInfo.getRight();
			
			//Make new parameter mapping, and also add it to the parameters object
			ParameterMapping mapping = new ParameterMapping.Builder(statement.getConfiguration(), paramName, Integer.class).build();
			parameterMappings.add(mapping);
			parameters.put(paramName, vendorId);
		}
	}
	
	/**
	 * Attempts to generate a parameter name for the next associated vendor ID parameter that isn't already in use.
	 * @param parameters The existing set of parameters for the query.
	 * @param currentIndex The index to start looking for an available parameter name at. This is so we don't look at IDs that are already taken.
	 * @return A parameter name that is currently unused and could be used for vendor IDs. If no ID could be found, null will be returned.
	 */
	private Pair<Integer, String> getNextAvailableVendorIdIndex(Map<String, Object> parameters, int currentIndex)
	{
		for(int i = currentIndex; i < 1000; i++)
		{
			String paramName = VENDOR_ID_PARAM + i;
			if(parameters.containsKey(paramName))
				continue;
			
			return Pair.of(i, paramName);
		}
		return null;
	}
	
	/**
	 * Metadata object used to replace the existing query with a new (wrapped) query.
	 */
	private static class WrappedQueryMetadata
	{
		/** The new mapped statement containing the wrapped SQL */
		private final MappedStatement mappedStatement;
		/** The new parameter mapping that contains all the parameters for the associated vendor IDs. */
		private final Map<String, Object> parameters;
		
		private WrappedQueryMetadata(MappedStatement mappedStatement, Map<String, Object> parameters)
		{
			this.mappedStatement = mappedStatement;
			this.parameters = parameters;
		}

		/**
		 * @return the mappedStatement
		 */
		public MappedStatement getMappedStatement()
		{
			return mappedStatement;
		}

		/**
		 * @return the parameters
		 */
		public Map<String, Object> getParameters()
		{
			return Collections.unmodifiableMap(parameters);
		}
	}
	
	/**
	 * A MyBatis object used to create the new bound SQL for the wrapped query.
	 */
	private static class WrappedSqlSource implements SqlSource
	{
		/** The new BoundSql object that will be used by MyBatis to get the query text to actually send to the database. */
		private BoundSql boundSql;
		
		/**
		 * Creates a new SQL source to be used when generating the wrapped query.
		 * @param statement The MappedStatement object for the original query. Used to get the MyBatis configuration for the new query.
		 * @param sqlToWrap The BoundSql object for the original query. Used to get the SQL text to wrap with a vendor filter. Also used to get the contents of parameter lists and other properties.
		 * @param newParameterMappings The list of parameter mappings that should apply to the new query. This should include a parameter mapping for the associated vendor IDs.
		 * @param newParameters The new set of parameter values that should be set when preparing the wrapped query to be sent to the database. This should include the list of associated vendor IDs.
		 * @param vendorIds The actual vendor ID values. Used to add one parameter marker to the new SQL for each vendor ID.
		 */
		private WrappedSqlSource(MappedStatement statement, BoundSql sqlToWrap, List<ParameterMapping> newParameterMappings, Map<String, Object> newParameters, Collection<Integer> vendorIds)
		{
			List<String> paramMarkers = new ArrayList<String>(vendorIds.size());
			for(int i = 0 ; i < vendorIds.size() ; i++)
				paramMarkers.add("?");
			
			//Create a new BoundSql object with the new SQL
			StringBuilder newSql = new StringBuilder(WRAPPER_SQL_PREFIX.length() + sqlToWrap.getSql().length() + WRAPPER_SQL_SUFFIX.length())
				.append(WRAPPER_SQL_PREFIX)
				.append(sqlToWrap.getSql())
				.append(WRAPPER_SQL_MIDDLE)
				.append(StringUtils.join(paramMarkers, ","))
				.append(WRAPPER_SQL_SUFFIX);
			
			this.boundSql = new BoundSql(statement.getConfiguration(), newSql.toString(), newParameterMappings, newParameters);
			for(ParameterMapping mapping : sqlToWrap.getParameterMappings())
			{
				String property = mapping.getProperty();
				if(sqlToWrap.hasAdditionalParameter(property))
					this.boundSql.setAdditionalParameter(property, sqlToWrap.getAdditionalParameter(property));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public BoundSql getBoundSql(Object parameterObject)
		{
			//We ignore the parameter argument here because we're creating a BoundSql object directly in the constructor.
			return boundSql;
		}
	}
}
