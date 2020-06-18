package com.penske.apps.suppliermgmt.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.penske.apps.suppliermgmt.annotation.DBSalesnet;
import com.penske.apps.suppliermgmt.dao.MapperMarker;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({JndiConfiguration.class, BaseMapperConfiguration.class})
@MapperScan(basePackageClasses={MapperMarker.class}, annotationClass=DBSalesnet.class, sqlSessionFactoryRef="salesnetSqlSessionFactory")
//NOTE: there is no transaction management enabled for this connection, so transactions against the Salesnet connection will fail
public class SalesnetMapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;
	
	@Autowired
	@DBSalesnet
	private DataSource dataSource;


	@Bean
	@DBSalesnet
	public SqlSessionFactory salesnetSqlSessionFactory() throws Exception
	{
		String basePath = "classpath:conf/xml/mapper/salesnet/";
		
		return baseMapperConfiguration.getBaseSqlSessionFactory(basePath, dataSource, false);
	}
}
