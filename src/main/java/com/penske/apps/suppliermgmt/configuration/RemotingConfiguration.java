/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.configuration;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.annotation.UserCreationServiceUrl;
import com.penske.apps.ucsc.service.UserInfoService;

/**
 * Sets up the componentry to communicate with any remote services used by this application.
 */
@Configuration
@Import({JndiConfiguration.class})
@Profile(ProfileType.NOT_TEST)
public class RemotingConfiguration
{
	@Autowired
	@UserCreationServiceUrl
	private URL userCreationServiceUrl;
	
	@Bean
	public HttpInvokerProxyFactoryBean remoteDocumentArchive()
	{
		HttpInvokerProxyFactoryBean bean = new HttpInvokerProxyFactoryBean();
		bean.setServiceInterface(UserInfoService.class);
		bean.setServiceUrl(userCreationServiceUrl.toString());
		return bean;
	}
}
