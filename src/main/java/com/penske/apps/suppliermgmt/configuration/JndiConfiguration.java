package com.penske.apps.suppliermgmt.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.penske.apps.smccore.base.annotation.qualifier.CoreDataSourceQualifier;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.DBCro;
import com.penske.apps.suppliermgmt.annotation.DBSalesnet;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.annotation.UserCreationServiceUrl;

/**
 * Configuration class that creates any data source beans and URL's used in the application
 */
@Configuration
@Profile(ProfileType.NOT_TEST)
public class JndiConfiguration {

	@Bean
	@DBSmc
	@CoreDataSourceQualifier
	@ConfigurationProperties("spring.datasource.smc")
	public DataSource smcDataSource()
	{
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@DBCro
	@ConfigurationProperties("spring.datasource.cro")
	public DataSource croDataSource() throws NamingException {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
    @DBSalesnet
	@ConfigurationProperties("spring.datasource.salesnet")
	public DataSource salesnetDataSource() throws NamingException {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@CommonStaticUrl
	public URL commonStaticURL(@Value("${url.common.static}") String spec) throws MalformedURLException
	{
		return new URL(spec);
	}

	@Bean
	@UserCreationServiceUrl
	public URL userCreationServiceURL(@Value("${url.usercreationsvc}")String spec) throws MalformedURLException
	{
		return new URL(spec);
	}
	

	@Bean
	public MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:conf/suppliermgmt");

		return messageSource;
	}
}
