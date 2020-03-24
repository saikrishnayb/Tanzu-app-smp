package com.penske.apps.suppliermgmt.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.penske.apps.buildmatrix.dao.BuildMatrixMapperMarker;
import com.penske.apps.buildmatrix.domain.enums.ApprovalStatus;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.smccore.base.annotation.MappedEnumTypes;
import com.penske.apps.suppliermgmt.annotation.DBCro;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({JndiConfiguration.class, BaseMapperConfiguration.class})
@MapperScan(basePackageClasses={BuildMatrixMapperMarker.class}, annotationClass=DBCro.class, sqlSessionFactoryRef="croSqlSessionFactory")
@MappedEnumTypes({
	ApprovalStatus.class,
	BuildStatus.class
})
//NOTE: there is no transaction management enabled for this connection, so transactions against the Salesnet connection will fail
public class BuildMatrixCroMapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;
	
	@Autowired
	@DBCro
	private DataSource dataSource;

	@Bean
	@DBCro
	public SqlSessionFactory croSqlSessionFactory() throws Exception
	{
		String baseMapperPath = "classpath:conf/xml/mapper/buildmatrix/cro/";
		
		return baseMapperConfiguration.getBaseSqlSessionFactory(baseMapperPath, dataSource, false);
	}
}
