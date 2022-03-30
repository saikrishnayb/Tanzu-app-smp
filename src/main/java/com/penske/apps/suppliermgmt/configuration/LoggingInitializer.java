/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.configuration;

import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.QueryLoggingPlugin;
import com.penske.apps.smccore.base.util.SpringConfigUtil;
import com.penske.apps.suppliermgmt.interceptor.RequestLoggingHandlerInterceptor;
import com.penske.apps.suppliermgmt.main.SuppliermgmtApplication;
import com.zaxxer.hikari.pool.HikariPool;

/**
 * Sets up logging just after Spring boot is initialized, so that our programmatic configuration overrides Spring's default configuration
 */
public class LoggingInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
	/** {@inheritDoc} */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext)
	{
		String activeProfile = getActiveProfile(applicationContext.getEnvironment());
		
		String logFolderName = SuppliermgmtApplication.CONTEXT_ROOT;
		
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder()
			.setConfigurationName(SuppliermgmtApplication.CONTEXT_ROOT);
		
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
				.addAttribute("from", "log4j." + activeProfile + "@" + SuppliermgmtApplication.CONTEXT_ROOT + ".penske.com")
				.addAttribute("to", "smc.developers@penske.com")
				.addAttribute("subject", StringUtils.capitalize(SuppliermgmtApplication.CONTEXT_ROOT) + " - " + activeProfile + " - Errors")
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
			.add(builder.newLogger(SqlSessionUtils.class.getName(),					Level.INFO))
			.add(builder.newLogger(SpringManagedTransaction.class.getName(),		Level.INFO))
			.add(builder.newLogger(DataSourceUtils.class.getName(),					Level.INFO))
			.add(builder.newLogger(HttpEntityMethodProcessor.class.getName(),		Level.INFO))
			.add(builder.newLogger(RequestResponseBodyMethodProcessor.class.getName(),	Level.INFO))
			.add(builder.newLogger(HikariPool.class.getName(),						Level.INFO))
		;

		//Performance trace logging is turned off in production
		if(!SpringConfigUtil.isProfile(activeProfile, ProfileType.PRODUCTION))
		{
			builder.add(builder.newLogger(QueryLoggingPlugin.class.getName(),				Level.TRACE))
				.add(builder.newLogger(RequestLoggingHandlerInterceptor.class.getName(),	Level.TRACE));
		}
		
		Configurator.reconfigure(builder.build());

		//Print our logging config to the logs (partly to verify it's working)
		Logger logger = LogManager.getLogger(SuppliermgmtApplication.class);
		String logConfiguration = SpringConfigUtil.getLogConfiguration(SuppliermgmtApplication.CONTEXT_ROOT);
		logger.info("\n" + logConfiguration);
	}
	
	private String getActiveProfile(Environment env)
	{
		String[] parts = env.getActiveProfiles();
		
		if(parts == null || parts.length == 0 || StringUtils.isBlank(parts[0]))
			throw new IllegalStateException("No active spring profile found. Is srvenv missing from system properties?");
		if(parts.length > 1)
			throw new IllegalStateException("Only one active spring profile allowed");
		return parts[0];
	}
}
