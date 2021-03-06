/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.penske.apps.adminconsole.domain.AdminConsoleTypeAliasMarker;
import com.penske.apps.buildmatrix.domain.BuildMatrixTypeAlias;
import com.penske.apps.smccore.base.annotation.qualifier.VendorQueryWrappingPluginQualifier;
import com.penske.apps.smccore.base.configuration.CoreMapperConfiguration;
import com.penske.apps.smccore.base.plugins.ClientInfoPlugin;
import com.penske.apps.smccore.base.plugins.QueryLoggingPlugin;
import com.penske.apps.smccore.base.util.SpringConfigUtil;
import com.penske.apps.smccore.typehandlers.CoreTypeHandlerMarker;
import com.penske.apps.suppliermgmt.domain.TypeAliasMarker;

/**
 * Base configuration common to all DB connections. Individual MyBatis configurations can leverage this
 */
@Configuration
@Import({MyBatisPluginConfiguration.class})
public class BaseMapperConfiguration
{
	@Autowired(required = false)
	private QueryLoggingPlugin queryLoggingPlugin;
	
	@Autowired(required = false)
	private ClientInfoPlugin clientInfoPlugin;
	
	@Autowired
	@VendorQueryWrappingPluginQualifier
	private Interceptor vendorWrappingPlugin;
	
	public SqlSessionFactory getBaseSqlSessionFactory(String baseMapperPath, DataSource dataSource, boolean batch) throws Exception
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		sessionFactory.setConfiguration(configuration);
		
		//***** TYPE ALIASES *****//
		Set<String> typeAliasPackages = SpringConfigUtil.getPackageNames(
			TypeAliasMarker.class,
			AdminConsoleTypeAliasMarker.class,
			BuildMatrixTypeAlias.class
		);
		
		//***** GLOBAL TYPE HANDLERS *****//
		Set<String> typeHandlerPackages = SpringConfigUtil.getPackageNames(
			CoreTypeHandlerMarker.class
		);
		
		//***** MYBATIS PLUGINS *****//
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		if(queryLoggingPlugin != null)			interceptors.add(queryLoggingPlugin);
		if(vendorWrappingPlugin != null)		interceptors.add(vendorWrappingPlugin);
		if(clientInfoPlugin != null)			interceptors.add(clientInfoPlugin);
		
		//***** MAPPER FILES *****//
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		String basePath = baseMapperPath;
		List<String> mapperFileNames = Arrays.asList("*.xml");
		
		List<Resource> mapperLocations = SpringConfigUtil.getMapperFileResources(patternResolver, basePath, mapperFileNames);
		
		//***** ENUM TYPE HANDLER CONFIGURATION *****//
		//Register a custom enum type handler for all the enum types listed in the @MappedEnumTypes annotation on the following classes.
		List<Class<?>> mappedTypes = SpringConfigUtil.getMappedEnumTypes(
			CoreMapperConfiguration.class,
			BuildMatrixCroMapperConfiguration.class,
			BuildMatrixSmcMapperConfiguration.class
		);
		
		
		//***** GENERAL CONFIG *****//
		sessionFactory.setTypeAliasesPackage(StringUtils.join(typeAliasPackages, ","));
		sessionFactory.setTypeHandlersPackage(StringUtils.join(typeHandlerPackages, ","));
		sessionFactory.setPlugins(interceptors.toArray(new Interceptor[interceptors.size()]));
		sessionFactory.setMapperLocations(mapperLocations.toArray(new Resource[mapperLocations.size()]));
		
		SpringConfigUtil.registerMappedEnumTypeHandlers(configuration.getTypeHandlerRegistry(), mappedTypes);
		
		configuration.setLazyLoadingEnabled(false);
		configuration.setCacheEnabled(false);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setMultipleResultSetsEnabled(true);
		configuration.setUseGeneratedKeys(false);
		configuration.setDefaultStatementTimeout(25000);
		if(batch)
			configuration.setDefaultExecutorType(ExecutorType.BATCH);
		
		return sessionFactory.getObject();
	}
}
