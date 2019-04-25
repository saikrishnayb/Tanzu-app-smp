package com.pensle.apps.adminconsole.dao;

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

import com.penske.apps.adminconsole.dao.RoleDao;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;
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
public class RoleDaoTest extends MyBatisDaoTest {

	@Autowired
	private RoleDao roleDao;
	
	private int roleId = 1;
	private String tabKey = "SMCOF";
	private int functionId = 2;
	private Role role;
	private UserContext user;
	
	@Before
	public void setup()
	{
		user = new UserContext();
		user.setUserSSO("600166698");
		role = new Role();
		role.setRoleName("TestRole");
		role.setBaseRoleId(1);
		role.setRoleDescription("test");
		role.setCreatedBy(user.getUserSSO());
    	roleDao = this.trackMethodCalls(roleDao, RoleDao.class);
	}
	
    @Test
    public void shouldGetSecurityFunctionTabs() {
    	roleDao.getSecurityFunctionTabs(1);
    }

    @Test
    public void shouldGetRoleSecurityFunctions() {
    	roleDao.getRoleSecurityFunctions(roleId);
    }

    @Test
    public void shouldGetBaseRoleId() {
    	roleDao.getBaseRoleId(roleId);
    }

    @Test
    public void shouldGetNewRoleId() {
    	roleDao.getNewRoleId();
    }

    @Test
    public void shouldAddRole() {
    	roleDao.addRole(role);
    }

    @Test
    public void shouldAddRoleSecurityFunction() {
    	roleDao.addRoleSecurityFunction(roleId, functionId);
    }

    @Test
    public void shouldModifyRoleName() {
    	roleDao.modifyRoleName(role);
    }

    @Test
    public void shouldCheckRoleExist() {
    	roleDao.checkRoleExist(role);
    }

    @Test
    public void shouldGetRolePermissions() {
    	roleDao.getRolePermissions(roleId, tabKey);
    }
    
}
