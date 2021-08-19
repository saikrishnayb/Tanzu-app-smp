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

import com.penske.apps.smccore.CoreTestUtil;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

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
	private LoginDAO dao;

	@Before
	public void setup()
	{
		dao = this.trackMethodCalls(dao, LoginDAO.class);
	}

    @Test
    public void shouldGetUserVendorFilterSelections() {
    	dao.getUserVendorFilterSelections(1);
    }

    @Test
	public void shouldGetUserLoginHistory()
	{
    	User user = CoreTestUtil.createUser(1234, "600555555", "Joe", "Test", "joe.test@penske.com", UserType.PENSKE);
		dao.getUserLoginHistory(user);
	}
}
