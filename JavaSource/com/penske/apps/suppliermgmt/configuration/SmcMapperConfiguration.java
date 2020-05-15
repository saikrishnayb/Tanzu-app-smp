package com.penske.apps.suppliermgmt.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.penske.apps.adminconsole.dao.AdminMapperMarker;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.dao.MapperMarker;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({BaseMapperConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackageClasses={MapperMarker.class, AdminMapperMarker.class}, annotationClass=DBSmc.class, sqlSessionFactoryRef="primarySessionFactory")
public class SmcMapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;
	
	@Autowired
	@DBSmc
	private DataSource dataSource;

	@Bean
	@Qualifier("primary")
	public SqlSessionFactory primarySessionFactory() throws Exception
	{
		return baseMapperConfiguration.getBaseSqlSessionFactory("classpath*:conf/xml/mapper/", dataSource, false);
	}
	
	@Bean
	@DBSmc
	@Primary	//This transaction manager should be used by default unless otherwise specified. Generally, the CRO database connection should not need @Transactional
	@Profile(ProfileType.NOT_TEST)
	public PlatformTransactionManager transactionManager()
	{
		PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);

		return platformTransactionManager;
	}
}
