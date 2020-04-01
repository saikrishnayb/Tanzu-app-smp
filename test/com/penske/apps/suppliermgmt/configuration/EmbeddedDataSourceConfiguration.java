package com.penske.apps.suppliermgmt.configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.penske.apps.smccore.base.annotation.qualifier.CoreDataSourceQualifier;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.plugins.TimingBean;
import com.penske.apps.smccore.base.plugins.TimingBeanImpl;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.DBCro;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.beans.DefaultSuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.util.LookupManager;
import com.penske.apps.ucsc.exception.UsrCreationSvcException;
import com.penske.apps.ucsc.model.CreatedUser;
import com.penske.apps.ucsc.model.LDAPAttributes;
import com.penske.apps.ucsc.model.UsrCreationBean;
import com.penske.apps.ucsc.service.UserInfoService;

/**
* This class is use to instantiates 
* and configures beans defined for 
* DataSources. Typically used 
* for unit testing
*
*/
@Configuration
@EnableTransactionManagement
@Profile(ProfileType.TEST)
public class EmbeddedDataSourceConfiguration {
    
	@Bean
    public PlatformTransactionManager annotationDrivenTransactionManager () throws NamingException {	
        DataSource dataSource = smcDataSource();

        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        return dataSourceTransactionManager;
    }

    @Bean
    @DBSmc
    @DBCro
    @CoreDataSourceQualifier
    public DataSource smcDataSource() throws NamingException {
    	
        EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();

        return datasource;
    }

    /**
     * Creates a dummy lookup manager that has no lookup values.
     */
    @Bean
    public LookupManager lookupManager()
    {
    	return new LookupManager(){
    		@Override public List<LookUp> getLookUpListByName(String lookUpName)
    		{
    			return Collections.emptyList();
    		}
    	};
    }

    /**
     * Creates a dummy user service that automatically succeeds at every request, since tests aren't connected to the actual user creation service.
     */
    @Bean
    public UserInfoService userInfoService()
    {
    	return new UserInfoService() {
			@Override
			public String resetB2bLdapUserPassword(String arg0) throws UsrCreationSvcException
			{
				return null;
			}
			
			@Override
			public UsrCreationBean createNewUser(UsrCreationBean arg0) throws UsrCreationSvcException
			{
				return null;
			}
			
			@Override
			public CreatedUser createB2bLdapUser(List<LDAPAttributes> arg0) throws UsrCreationSvcException
			{
				CreatedUser user = new CreatedUser();
				user.setResponseMessage("Success!");
				user.setDefaultPassword("testtest");
				user.setGessouid("testtestid");
				return user;
			}
			
			@Override
			public UsrCreationBean addUserToGE(UsrCreationBean arg0) throws UsrCreationSvcException
			{
				return null;
			}
		};
    }
    
    @Bean
    @CommonStaticUrl
    public URL commonStaticURL() throws MalformedURLException {
        return new URL("http://google.com");
    }
    
    @Bean
    public SuppliermgmtSessionBean suppliermgmtSessionBean() {
        return new DefaultSuppliermgmtSessionBean();
    }
    
    @Bean
    @Profile(ProfileType.NOT_PRODUCTION)
    public TimingBean timingRequestBean() {
    	return new TimingBeanImpl();
    }
}