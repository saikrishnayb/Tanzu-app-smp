package com.penske.apps.suppliermgmt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.TimingBean;
import com.penske.apps.smccore.base.plugins.TimingBeanImpl;
import com.penske.apps.suppliermgmt.aspect.AspectMarker;
import com.penske.apps.suppliermgmt.beans.DefaultSuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.beans.LoggingHandlerExceptionResolver;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.interceptor.CommonModelAttributesHandlerInterceptor;
import com.penske.apps.suppliermgmt.interceptor.RequestLoggingHandlerInterceptor;
import com.penske.apps.suppliermgmt.interceptor.SmcSecurityInterceptor;

/**
 * Main Spring configuration class. The web.xml points to this class to set up the Spring
 * DispatcherServlet. In here, various project configurations and settings get imported and
 * set up.
 */
@Configuration
@Import(value={ApplicationConfiguration.class})
@EnableWebMvc
@Profile(ProfileType.NOT_TEST)
@ComponentScan(basePackageClasses={
	AspectMarker.class
})
public class WebConfiguration extends WebMvcConfigurerAdapter {	
    
    @Autowired
    private RequestLoggingHandlerInterceptor requestLoggingInterceptor;
    @Autowired
    private SmcSecurityInterceptor securityInterceptor;
    @Autowired
    private CommonModelAttributesHandlerInterceptor commonModelAttributesHandlerInterceptor;
    @Autowired(required=false)
    private TimingBean timingBean;
    
    /** {@inheritDoc} */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    	configurer.favorPathExtension(false)
    	  .favorParameter(false)
    	  .ignoreAcceptHeader(false)
    	  .mediaType("atom", MediaType.APPLICATION_ATOM_XML)
    	  .mediaType("html", MediaType.TEXT_HTML)
    	  .mediaType("json", MediaType.APPLICATION_JSON)
    	  .mediaType("*", MediaType.ALL);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	if(timingBean != null)
    		requestLoggingInterceptor.setLogTimings(true);
    	
    	registry.addInterceptor(requestLoggingInterceptor);
    	registry.addInterceptor(securityInterceptor).excludePathPatterns("/login/*");
    	registry.addInterceptor(commonModelAttributesHandlerInterceptor);
    } 
    
    @Bean
    public CommonsMultipartResolver multipartResolver()
    {
    	return new CommonsMultipartResolver();
    }
    
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
    public SuppliermgmtSessionBean suppliermgmtSessionBean() {
        return new DefaultSuppliermgmtSessionBean();
    }
    
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    @Profile(ProfileType.NOT_PRODUCTION)
    public TimingBean timingRequestBean() {
    	return new TimingBeanImpl();
    }

    @Bean
    public LoggingHandlerExceptionResolver loggingExceptionResolver()
    {
    	return new LoggingHandlerExceptionResolver();
    }
}