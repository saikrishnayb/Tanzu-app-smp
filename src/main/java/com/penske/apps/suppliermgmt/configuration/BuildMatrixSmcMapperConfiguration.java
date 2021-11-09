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
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * Configuration class that sets up the dao/mybatis configuration. Sets up the
 * transaction management and sql session factory, and binds the DAOs to  the
 * session factory.
 */
@Configuration
@Import({JndiConfiguration.class, BaseMapperConfiguration.class})
@MapperScan(basePackageClasses={BuildMatrixMapperMarker.class}, annotationClass=DBSmc.class, sqlSessionFactoryRef="sqlSessionFactory")
@MappedEnumTypes({
	ApprovalStatus.class,
	BuildStatus.class
})
public class BuildMatrixSmcMapperConfiguration {

	@Autowired
	private BaseMapperConfiguration baseMapperConfiguration;
	
	@Autowired
	@DBSmc
	private DataSource dataSource;

	@Bean
	@DBSmc
	public SqlSessionFactory sqlSessionFactory() throws Exception
	{
		String baseMapperPath = "classpath:conf/xml/mapper/buildmatrix/smc/";
		
		return baseMapperConfiguration.getBaseSqlSessionFactory(baseMapperPath, dataSource, false);
	}
}
