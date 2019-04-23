package com.penske.apps.suppliermgmt.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;
import com.penske.apps.suppliermgmt.dao.LoginDAO;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@SqlGroup({
    @Sql(scripts = "/setup/create-smc-database.sql"),
    @Sql(scripts = "/setup/drop-smc-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
    @Sql(scripts = "/setup/create-corp-database.sql"),
    @Sql(scripts = "/setup/drop-corp-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
}) 
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class LoginDAOTest extends MyBatisDaoTest{

	@Autowired
	private LoginDAO loginDao;

	@Before
	public void setup()
	{
		loginDao = this.trackMethodCalls(loginDao, LoginDAO.class);
	}
	
    @Test
    public void shouldGetUserDetails() {
    	User userModel = new User();
    	userModel.setSso("600166698");
    	userModel.setStatus("A");
    	loginDao.getUserDetails(userModel);
    }

    @Test 
    public void shouldGetAssociatedVendors() {
    	loginDao.getAssociatedVendors(1);
    }

    @Test
    public void shouldGetTabs() {
    	loginDao.getTabs(1);
    }

    @Test
    public void shouldGetSecurityFunctions() {
    	loginDao.getSecurityFunctions(1, "TAB_OF");
    }

    @Test
    public void shouldGetAllSecurityFunctionsWithUser() {
    	UserContext userContext = new UserContext();
    	userContext.setRoleId(1);
    	loginDao.getAllSecurityFunctionsWithUser(userContext);
    }

    @Test
    public void shouldGetUserVendorFilterSelections() {
    	loginDao.getUserVendorFilterSelections(1);
    }

    @Test
    public void shouldGetUserLoginHistory() {
    	UserContext userContext = new UserContext();
    	userContext.setUserSSO("600166698");
    	loginDao.getUserLoginHistory(userContext);
    }

}
