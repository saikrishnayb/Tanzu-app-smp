/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.configuration;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.penske.apps.smccore.base.annotation.qualifier.VendorQueryWrappingPluginQualifier;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.ClientInfoPlugin;
import com.penske.apps.smccore.base.plugins.QueryLoggingPlugin;
import com.penske.apps.smccore.base.plugins.TimingBean;
import com.penske.apps.suppliermgmt.plugins.VendorQueryWrappingPlugin;

/**
 * Configures MyBatis plugins
 */
@Configuration
@Import({JndiConfiguration.class})
public class MyBatisPluginConfiguration
{
	@Autowired(required = false)
	private TimingBean timingBean;
	
	@Bean
	@Profile({ProfileType.NOT_PRODUCTION})
	public QueryLoggingPlugin queryLoggingPlugin()
	{
		return new QueryLoggingPlugin(timingBean);
	}

	@Bean
	@Profile(ProfileType.NOT_TEST)
	public ClientInfoPlugin clientInfoPlugin()
	{
		return new ClientInfoPlugin();
	}
	
	@Bean
	@VendorQueryWrappingPluginQualifier
	public Interceptor vendorWrappingPlugin()
	{
		return new VendorQueryWrappingPlugin();
	}
}
