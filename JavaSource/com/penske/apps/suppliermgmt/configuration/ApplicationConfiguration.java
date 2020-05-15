package com.penske.apps.suppliermgmt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.penske.apps.adminconsole.controller.AdminControllerMarker;
import com.penske.apps.adminconsole.service.AdminServiceMarker;
import com.penske.apps.buildmatrix.controller.BuildMatrixControllerMarker;
import com.penske.apps.buildmatrix.dao.BuildMatrixMapperMarker;
import com.penske.apps.buildmatrix.service.BuildMatrixServiceMarker;
import com.penske.apps.smccore.base.configuration.CoreConfiguration;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.controller.ControllerMarker;
import com.penske.apps.suppliermgmt.dao.MapperMarker;
import com.penske.apps.suppliermgmt.interceptor.InterceptorMarker;
import com.penske.apps.suppliermgmt.service.ServiceMarker;
import com.penske.apps.suppliermgmt.util.LookupManager;
import com.penske.apps.suppliermgmt.util.SpringBeanHelper;

/**
 * General Spring application configuration. Sets up the ViewResolver and
 * imports the MapperConfiguration. It also scans the project for any Spring annotations
 * (such as @Autowired and friends)
 */
@Configuration
@Import(value = {CoreConfiguration.class, RemotingConfiguration.class, SmcMapperConfiguration.class, BatchMapperConfiguration.class, BuildMatrixSmcMapperConfiguration.class, BuildMatrixCroMapperConfiguration.class})
@ComponentScan(basePackageClasses={
	//Admin Console packages
	AdminControllerMarker.class, AdminServiceMarker.class, MapperMarker.class,
	//Main suppliermgmt packages
	ControllerMarker.class, ServiceMarker.class, MapperMarker.class,
	//OEM Build Matrix packages
	BuildMatrixControllerMarker.class, BuildMatrixServiceMarker.class, BuildMatrixMapperMarker.class,
	//Framework-level packages
	InterceptorMarker.class
})
@EnableAspectJAutoProxy
public class ApplicationConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean(initMethod="loadLookUpData")
	@Profile(ProfileType.NOT_TEST)
	public LookupManager lookupManager() {
		return new LookupManager();
	}
	
	@Bean
	public SpringBeanHelper springBeanHelper()
	{
		return new SpringBeanHelper(applicationContext);
	}

}
