/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.servlet;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.varia.LevelRangeFilter;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.penske.apps.dlinks.filters.SSOAuthenticationFilter;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.QueryLoggingPlugin;
import com.penske.apps.smccore.base.util.SpringConfigUtil;
import com.penske.apps.suppliermgmt.filter.DummySecurityFilter;
import com.penske.apps.suppliermgmt.filter.SerializableFilter;
import com.penske.apps.suppliermgmt.filter.SessionValidationFilter;
import com.penske.apps.suppliermgmt.interceptor.RequestLoggingHandlerInterceptor;

/**
 * Initializes the Spring DispatcherServlet for the main web application
 */
public class WebAppInitializer implements WebApplicationInitializer
{
	private static final String CONTEXT_ROOT = "suppliermgmt";
	private static final String CONTEXT_ROOT_QA2 = "suppliermgmt2";
	
	/** {@inheritDoc} */
	@Override
	public void onStartup(ServletContext container) throws ServletException
	{
		String activeProfile;
		//We need to wrap this in a try / catch block so that we can log any startup errors to System.err, at least up through logging being initialized
		try {
			if(container.getContextPath().equals("/" + CONTEXT_ROOT_QA2))
				activeProfile = ProfileType.QA2;
			else
				activeProfile = SpringConfigUtil.getSpringProfile();
			
			if(!SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2, ProfileType.PRODUCTION))
				throw new IllegalStateException("Unrecognized Spring profile: " + activeProfile);
			
			container.setInitParameter("webAppRootKey", CONTEXT_ROOT);
			container.setInitParameter("spring.profiles.active", activeProfile);
			
			setupLogging(activeProfile);
		} catch(RuntimeException ex) {
			//Normally, we don't handle exceptions like this (printing to System.err), but this occurs before application startup, so we haven't initialized logging yet.
			System.err.println("Exception caught during application startup of " + CONTEXT_ROOT);
			System.err.println("Exception class: " + ex.getClass().getName());
			System.err.println("Message: " + ex.getMessage());
			ex.printStackTrace(System.err);
			throw ex;
		}
		
		//Once logging is initialized, but before the spring context is started, we need to explicitly log errors, as opposed to letting the LoggingHandlerExceptionResolver handle it
		try {
			addFilters(container, activeProfile);
		} catch(RuntimeException ex) {
			String errorMessage = "Exception occurred during startup: " + ex.getMessage() + "\n\n" + StringUtils.join(ex.getStackTrace(), "\n");
			Logger.getLogger(this.getClass()).error(errorMessage);
			throw ex;
		}
	}
	
	private void setupLogging(String activeProfile)
	{
		String logFolderName;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.QA2))
			logFolderName = CONTEXT_ROOT_QA2;
		else
			logFolderName = CONTEXT_ROOT;
		
		//***** Appender Setup *****//
		//Set up file logging (both normal and splunk)
		RollingFileAppender fileAppender;
		RollingFileAppender splunkAppender;
		try {
			fileAppender =		new RollingFileAppender(new PatternLayout("%d %-5p (%F:%M():%L)  - %m%n"),		"/logs/apps/" + logFolderName + "/log.txt");
			splunkAppender =	new RollingFileAppender(new PatternLayout("%d [%t] %-5p (%F:%M():%L) - %m%n"),	"/logs/apps/" + logFolderName + "/splunk.spk");
		} catch(IOException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		fileAppender.setMaxBackupIndex(10);
		fileAppender.setMaxFileSize("1024KB");
		fileAppender.activateOptions();
		splunkAppender.setMaxBackupIndex(9);
		splunkAppender.setMaxFileSize("1000KB");
		splunkAppender.activateOptions();

		//Setup email logging - email doesn't get sent from local machines - too much email
		SMTPAppender emailAppender;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2, ProfileType.PRODUCTION))
		{
			LevelRangeFilter emailFilter = new LevelRangeFilter();
			emailFilter.setLevelMin(Level.ERROR);
			emailFilter.setLevelMax(Level.FATAL);
			
			emailAppender = new SMTPAppender();
			emailAppender.setName("email");
			emailAppender.addFilter(emailFilter);
			emailAppender.setBufferSize(1);
			emailAppender.setSMTPHost("mail.penske.com");
			emailAppender.setFrom("log4j." + activeProfile + "@suppliermgmt.penske.com");
			emailAppender.setTo("smc.developers@penske.com");
			emailAppender.setSubject("Suppliermgmt - " + activeProfile + " - Errors");
			emailAppender.setLayout(new PatternLayout("[%d{ISO8601}]%n%n%-5p%n%n%c%n%n%m%n%n"));
			emailAppender.activateOptions();
		}
		else
			emailAppender = null;
		
		//Setup console logging - console logging only happens in local - shared environments should use file logging
		ConsoleAppender consoleAppender;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL))
			consoleAppender = new ConsoleAppender(new PatternLayout("%d %-5p (%F:%M():%L) - %m%n"));
		else
			consoleAppender = null;
		
		//***** Add Appenders *****//
		Logger rootLogger = Logger.getRootLogger();
		
		if(fileAppender != null) 	rootLogger.addAppender(fileAppender);
		if(consoleAppender != null)	rootLogger.addAppender(consoleAppender);
		if(emailAppender != null)	rootLogger.addAppender(emailAppender);
		
		Logger splunkLogger = Logger.getLogger("splunk");
		//Things written to splunk should not be written to the root logger
		splunkLogger.setAdditivity(false);

		if(splunkAppender != null)	splunkLogger.addAppender(splunkAppender);
		
		//***** Logger Levels *****//
		rootLogger.setLevel(Level.DEBUG);
		splunkLogger.setLevel(Level.DEBUG);
		
		//Turn logs down for certain things that tend to be very verbose at DEBUG level
		Logger.getLogger(ResultSet.class)					.setLevel(Level.ERROR);
		Logger.getLogger(SqlSessionUtils.class)				.setLevel(Level.INFO);
		Logger.getLogger(SpringManagedTransaction.class)	.setLevel(Level.INFO);
		Logger.getLogger(DataSourceUtils.class)				.setLevel(Level.INFO);
		Logger.getLogger(HttpEntityMethodProcessor.class)	.setLevel(Level.INFO);
		Logger.getLogger(RequestResponseBodyMethodProcessor.class)	.setLevel(Level.INFO);

		//Performance trace logging is turned off in production
		if(!SpringConfigUtil.isProfile(activeProfile, ProfileType.PRODUCTION))
		{
			Logger.getLogger(QueryLoggingPlugin.class)				.setLevel(Level.TRACE);
			Logger.getLogger(RequestLoggingHandlerInterceptor.class).setLevel(Level.TRACE);
		}
	}
	
	private void addFilters(ServletContext container, String activeProfile)
	{
		//Auth filter is dispatched
		FilterRegistration authFilter; 
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL, ProfileType.DEVELOPMENT))
			authFilter = container.addFilter("DummySecurityFilter", new DummySecurityFilter());
		else
		{
			authFilter = container.addFilter("SSOAuthenticationFilter", new SSOAuthenticationFilter());
			authFilter.setInitParameter("LoginUserID", "true");
			authFilter.setInitParameter("FirstName", "true");
			authFilter.setInitParameter("LastName", "true");
			authFilter.setInitParameter("GESSOEmail", "true");
			authFilter.setInitParameter("GESSOMailStop", "true");
			authFilter.setInitParameter("StoreInAttribute", "true");
			authFilter.setInitParameter("StoreSessionInfoInAttrib", "true");
			authFilter.setInitParameter("ExtendedLogging", "false");
		}
		
		//Session validation filter is present in all environments
		FilterRegistration sessionValidationFilter = container.addFilter("SessionValidationFilter", new SessionValidationFilter());
		
		//Serialization filter is present in all environments other than prod, to warn us about large session sizes
		FilterRegistration serializationFilter;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2))
			serializationFilter = container.addFilter("SerializationFilter", new SerializableFilter());
		else
			serializationFilter = null;
		
		if(authFilter != null)				authFilter				.addMappingForUrlPatterns(null, true, "/entry/*");
		if(sessionValidationFilter != null)	sessionValidationFilter	.addMappingForUrlPatterns(null, true, "/*");
		if(serializationFilter != null)		serializationFilter		.addMappingForUrlPatterns(null, true, "/*");
	}
}
