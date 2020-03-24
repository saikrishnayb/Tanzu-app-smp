package com.penske.apps.suppliermgmt.configuration;

import java.net.URL;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.penske.apps.smccore.base.annotation.qualifier.CoreDataSourceQualifier;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.DBCro;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.annotation.UserCreationServiceUrl;

/**
 * Configuration class that creates any data source beans and URL's used in the application
 */
@Configuration
@Profile(ProfileType.NOT_TEST)
public class JndiConfiguration {

	private static final Logger logger = Logger.getLogger(JndiConfiguration.class);

	@Bean
	@DBSmc
	@CoreDataSourceQualifier
	public DataSource dataSource() throws NamingException {
		InitialContext context = new InitialContext();

		try {
			return (DataSource) context.lookup("jdbc/ds_suppliermgmt"); // Websphere
		} catch (NameNotFoundException exception) {
			logger.info(exception);
			return (DataSource) context.lookup("java:comp/env/jdbc/ds_suppliermgmt"); // Tomcat
		}
	}
	
	@Bean
    @DBCro
	public DataSource buildMatrixCroDataSource() throws NamingException
	{
    	InitialContext context = new InitialContext();
    	
    	try {
			return (DataSource) context.lookup("jdbc/ds_cro"); // Websphere
		} catch (NameNotFoundException exception) {
			logger.info(exception);
			return (DataSource) context.lookup("java:comp/env/jdbc/ds_cro"); //Tomcat
		}
	}

	@Bean
	@CommonStaticUrl
	public URL commonStaticURL() throws NamingException {
		Context context = new InitialContext();

		try {
			return (URL) context.lookup("url/url_common_static"); // Websphere
		} catch (NameNotFoundException exception) {
			logger.info(exception);
			return (URL) context.lookup("java:comp/env/url/url_common_static"); // Tomcat
		}
	}

	@Bean
	@UserCreationServiceUrl
	public URL userCreationServiceURL() throws NamingException {
		Context context = new InitialContext();

		try {
			return (URL) context.lookup("url/url_usrcreationsvc"); // Websphere
		} catch (NameNotFoundException exception) {
			logger.info(exception);
			return (URL) context.lookup("java:comp/env/url/url_usrcreationsvc"); // Tomcat
		}
	}

	@Bean
	public MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:conf/suppliermgmt");

		return messageSource;
	}
}
