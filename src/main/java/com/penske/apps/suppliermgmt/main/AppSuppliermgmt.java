/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.main;

import java.net.MalformedURLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.penske.apps.dlinks.filters.SSOAuthenticationFilter;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.util.SpringConfigUtil;
import com.penske.apps.suppliermgmt.configuration.LoggingInitializer;
import com.penske.apps.suppliermgmt.configuration.WebConfiguration;
import com.penske.apps.suppliermgmt.filter.DummySecurityFilter;
import com.penske.apps.suppliermgmt.filter.SerializableFilter;
import com.penske.apps.suppliermgmt.filter.SessionValidationFilter;
import com.penske.apps.suppliermgmt.servlet.ApplicationEntry;
import com.penske.apps.suppliermgmt.servlet.SMCLogOff;

/**
 * Main entry point to run the application using Spring Boot.
 * The fact that this is annotated with {@code @SpringBootApplication} means that Spring will scan this package and any subpackages for components and configuration automatically.
 */
@SpringBootApplication
@PropertySource(value="classpath:/config/db.local.properties", ignoreResourceNotFound = true)
@Import({WebConfiguration.class})
public class AppSuppliermgmt
{
	public static final String CONTEXT_ROOT = "suppliermgmt";
	
	public static void main(String[] args)
	{
		String activeProfile;
		//We need to wrap this in a try / catch block so that we can log any startup errors to System.err, at least up through logging being initialized
		try {
			activeProfile = SpringConfigUtil.getSpringProfile();
			
			if(!SpringConfigUtil.isProfile(activeProfile, ProfileType.LOCAL, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2, ProfileType.PRODUCTION))
				throw new IllegalStateException("Unrecognized Spring profile: " + activeProfile);
		} catch(RuntimeException ex) {
			//Normally, we don't handle exceptions like this (printing to System.err), but this occurs before application startup, so we haven't initialized logging yet.
			System.err.println("Exception caught during application startup of " + CONTEXT_ROOT);
			System.err.println("Exception class: " + ex.getClass().getName());
			System.err.println("Message: " + ex.getMessage());
			ex.printStackTrace(System.err);
			throw ex;
		}
		
		SpringApplication app = new SpringApplication(AppSuppliermgmt.class);
		app.setAdditionalProfiles(activeProfile);
		app.addInitializers(new LoggingInitializer());
		app.run();
	}
	
	@Bean
	@Profile({ProfileType.LOCAL, ProfileType.DEVELOPMENT})
	public FilterRegistrationBean<DummySecurityFilter> securityFilterLocal()
	{
		DummySecurityFilter filter = new DummySecurityFilter();
		FilterRegistrationBean<DummySecurityFilter> registration = new FilterRegistrationBean<>(filter);
		
		registration.addUrlPatterns("/entry/*");
		registration.setOrder(1);
		
		return registration;
	}
	
	@Bean
	@Profile({ProfileType.QA, ProfileType.QA2, ProfileType.PRODUCTION})
	public FilterRegistrationBean<SSOAuthenticationFilter> securityFilterProd()
	{
		SSOAuthenticationFilter filter = new SSOAuthenticationFilter();
		FilterRegistrationBean<SSOAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
		
		registration.addInitParameter("LoginUserID", "true");
		registration.addInitParameter("FirstName", "true");
		registration.addInitParameter("LastName", "true");
		registration.addInitParameter("GESSOEmail", "true");
		registration.addInitParameter("GESSOMailStop", "true");
		registration.addInitParameter("StoreInAttribute", "true");
		registration.addInitParameter("StoreSessionInfoInAttrib", "true");
		registration.addInitParameter("ExtendedLogging", "false");
		
		registration.addUrlPatterns("/entry/*");
		registration.setOrder(1);
		
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean<SessionValidationFilter> sessionValidationFilter()
	{
		SessionValidationFilter filter = new SessionValidationFilter();
		FilterRegistrationBean<SessionValidationFilter> registration = new FilterRegistrationBean<>(filter);
		
		registration.addUrlPatterns("/*");
		registration.setOrder(2);
		
		return registration;
	}
	
	@Bean
	@Profile({ProfileType.LOCAL, ProfileType.DEVELOPMENT, ProfileType.QA, ProfileType.QA2})
	public FilterRegistrationBean<SerializableFilter> serializationFilter()
	{
		SerializableFilter filter = new SerializableFilter();
		FilterRegistrationBean<SerializableFilter> registration = new FilterRegistrationBean<>(filter);
		
		registration.addUrlPatterns("/*");
		registration.setOrder(3);
		
		return registration;
	}

	@Bean
	@Profile(ProfileType.NOT_TEST)
	public ServletRegistrationBean<ApplicationEntry> applicationEntryServletServlet() throws MalformedURLException
	{
		return new ServletRegistrationBean<>(new ApplicationEntry(), "/entry/ApplicationEntry");
	}
	
	@Bean
	@Profile(ProfileType.NOT_TEST)
	public ServletRegistrationBean<SMCLogOff> smcLogoffServlet() throws MalformedURLException
	{
		return new ServletRegistrationBean<>(new SMCLogOff(), "/SMCLogOff");
	}
}