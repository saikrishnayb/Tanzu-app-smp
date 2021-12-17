/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.servlet;

import java.sql.ResultSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
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
			LogManager.getLogger(this.getClass()).error(errorMessage);
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
		
		
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder()
			.setConfigurationName(CONTEXT_ROOT);
		
		//***** Appender Setup *****//
		AppenderRefComponentBuilder fileAppender = 		SpringConfigUtil.makeRollingFileAppender(builder, "file", "1024KB", 10, "%d %-5p (%F:%M():%L)  - %m%n", "/logs/apps/" + logFolderName + "/log.txt");
		AppenderRefComponentBuilder splunkAppender =	SpringConfigUtil.makeRollingFileAppender(builder, "splunk", "1000KB", 9, "%d [%t] %-5p (%F:%M():%L) - %m%n", "/logs/apps/" + logFolderName + "/splunk.spk");

		//Setup email logging - email doesn't get sent from local machines - too much email
		AppenderRefComponentBuilder emailAppender;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2, ProfileType.PRODUCTION))
		{
			builder.add(builder.newAppender("email", "SMTP")
				.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.DENY)
					.addAttribute("level", Level.ERROR))
				.add(builder.newLayout("PatternLayout").addAttribute("pattern", "[%d{ISO8601}]%n%n%-5p%n%n%c%n%n%m%n%n"))
				.addAttribute("bufferSize", 0)
				.addAttribute("smtpHost", "mail.penske.com")
				.addAttribute("from", "log4j." + activeProfile + "@suppliermgmt.penske.com")
				.addAttribute("to", "smc.developers@penske.com")
				.addAttribute("subject", "Suppliermgmt - " + activeProfile + " - Errors")
			);
			emailAppender = builder.newAppenderRef("email");
		}
		else
			emailAppender = null;
		
		//Setup console logging - console logging only happens in local - shared environments should use file logging
		AppenderRefComponentBuilder consoleAppender;
		if(SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL))
		{
			builder.add(builder.newAppender("console", "Console")
				.add(builder.newLayout("PatternLayout")
					.addAttribute("pattern", "%d %-5p (%F:%M():%L) - %m%n")
				)
			);
			consoleAppender = builder.newAppenderRef("console");
		}
		else
			consoleAppender = null;
		
		//***** Add Appenders and Set Levels *****//
		//Most things log at DEBUG level
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.DEBUG)
			.add(fileAppender);
		if(consoleAppender != null)	rootLogger.add(consoleAppender);
		if(emailAppender != null)	rootLogger.add(emailAppender);
		builder.add(rootLogger);

		//Splunk logs separately
		builder.add(builder.newLogger("splunk", Level.DEBUG)
			.addAttribute("additivity", false)
			.add(splunkAppender));

		//Turn logs down for certain things that tend to be very verbose at DEBUG level
		builder.add(builder.newLogger(ResultSet.class.getName(),					Level.ERROR))
			.add(builder.newLogger(SqlSessionUtils.class.getName(),				Level.INFO))
			.add(builder.newLogger(SpringManagedTransaction.class.getName(),		Level.INFO))
			.add(builder.newLogger(DataSourceUtils.class.getName(),				Level.INFO))
			.add(builder.newLogger(HttpEntityMethodProcessor.class.getName(),	Level.INFO))
			.add(builder.newLogger(RequestResponseBodyMethodProcessor.class.getName(),	Level.INFO));

		//Performance trace logging is turned off in production
		if(!SpringConfigUtil.isProfile(activeProfile, ProfileType.PRODUCTION))
		{
			builder.add(builder.newLogger(QueryLoggingPlugin.class.getName(),				Level.TRACE))
				.add(builder.newLogger(RequestLoggingHandlerInterceptor.class.getName(),	Level.TRACE));
		}
		
		Configurator.initialize(builder.build());
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
