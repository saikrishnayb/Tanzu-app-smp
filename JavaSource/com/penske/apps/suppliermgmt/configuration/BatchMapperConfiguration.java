package com.penske.apps.suppliermgmt.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.penske.apps.adminconsole.batch.dao.BatchMapperMarker;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({BaseMapperConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackageClasses={BatchMapperMarker.class}, sqlSessionFactoryRef="batchSessionFactory")
public class BatchMapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;
	
	@Autowired
	@DBSmc
	private DataSource dataSource;
	
	@Bean
	@Qualifier("batch")
	public SqlSessionFactory batchSessionFactory() throws Exception
	{
		return baseMapperConfiguration.getBaseSqlSessionFactory("classpath*:conf/xml/mapper/batch/", dataSource, true);
	}
}
