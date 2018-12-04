package com.penske.apps.suppliermgmt.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.penske.apps.adminconsole.dao.AdminMapperMarker;
import com.penske.apps.suppliermgmt.dao.MapperMarker;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({BaseMapperConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackageClasses={MapperMarker.class, AdminMapperMarker.class}, sqlSessionFactoryRef="primarySessionFactory")
public class MapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;

	@Bean
	@Qualifier("primary")
	public SqlSessionFactory primarySessionFactory() throws Exception
	{
		return baseMapperConfiguration.getBaseSqlSessionFactory("classpath*:conf/xml/mapper/", false);
	}
}
