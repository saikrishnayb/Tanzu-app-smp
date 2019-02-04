/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.penske.apps.smccore.base.annotation.qualifier.VendorQueryWrappingPluginQualifier;
import com.penske.apps.smccore.base.configuration.CoreMapperConfiguration;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.QueryLoggingPlugin;
import com.penske.apps.smccore.base.plugins.TimingBean;
import com.penske.apps.smccore.base.util.SpringConfigUtil;
import com.penske.apps.suppliermgmt.plugins.VendorQueryWrappingPlugin;

/**
 * Base configuration common to all DB connections. Individual MyBatis configurations can leverage this
 */
@Configuration
@Import({JndiConfiguration.class})
public class BaseMapperConfiguration
{
	@Autowired
	private DataSource dataSource;

	@Autowired(required = false)
	private TimingBean timingBean;
	
	@Autowired(required = false)
	private QueryLoggingPlugin queryLoggingPlugin;
	
	@Autowired
	@VendorQueryWrappingPluginQualifier
	private Interceptor vendorWrappingPlugin;
	
	public SqlSessionFactory getBaseSqlSessionFactory(String baseMapperPath, boolean batch) throws Exception
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		sessionFactory.setConfiguration(configuration);
		
		//***** TYPE ALIASES *****//
		Set<String> typeAliasPackages = SpringConfigUtil.getPackageNames(
			com.penske.apps.suppliermgmt.domain.TypeAliasMarker.class,
			com.penske.apps.adminconsole.domain.TypeAliasMarker2.class
		);
		
		//***** GLOBAL TYPE HANDLERS *****//
		Set<String> typeHandlerPackages = Collections.emptySet();
		
		//***** MYBATIS PLUGINS *****//
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		if(queryLoggingPlugin != null)			interceptors.add(queryLoggingPlugin);
		if(vendorWrappingPlugin != null)		interceptors.add(vendorWrappingPlugin);
		
		//***** MAPPER FILES *****//
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		String basePath = baseMapperPath;
		List<String> mapperFileNames = Arrays.asList("*.xml");
		
		List<Resource> mapperLocations = SpringConfigUtil.getMapperFileResources(patternResolver, basePath, mapperFileNames);
		
		//***** ENUM TYPE HANDLER CONFIGURATION *****//
		//Register a custom enum type handler for all the enum types listed in the @MappedEnumTypes annotation on the following classes.
		List<Class<?>> mappedTypes = SpringConfigUtil.getMappedEnumTypes(
			CoreMapperConfiguration.class
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
	
	@Bean
	@Profile({ProfileType.NOT_PRODUCTION})
	public QueryLoggingPlugin queryLoggingPlugin()
	{
		return new QueryLoggingPlugin(timingBean);
	}

	@Bean
	@VendorQueryWrappingPluginQualifier
	public Interceptor vendorWrappingPlugin()
	{
		return new VendorQueryWrappingPlugin();
	}

	@Bean
	@Profile(ProfileType.NOT_TEST)
	public PlatformTransactionManager transactionManager()
	{
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);

		return dataSourceTransactionManager;
	}
}
