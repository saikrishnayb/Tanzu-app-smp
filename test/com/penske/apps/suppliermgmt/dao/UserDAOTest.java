package com.penske.apps.suppliermgmt.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

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
import com.penske.apps.suppliermgmt.dao.UserDAO;
import com.penske.apps.suppliermgmt.model.Buddies;

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
public class UserDAOTest extends MyBatisDaoTest{

	@Autowired
	private UserDAO userDao;

	@Before
	public void setup()
	{
		userDao = this.trackMethodCalls(userDao, UserDAO.class);
	}

    @Test
    public void shouldDeleteBuddyList() {
    	try {
			userDao.deleteBuddyList("600166698");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void shouldAddBuddyList() {
    	List<Buddies> newBuddyList = new ArrayList<Buddies>();
    	Buddies buddy = new Buddies();
    	buddy.setSso("600166698");
    	buddy.setUserDept("MIS");
    	buddy.setBuddySso("600166699");
    	buddy.setSelectionType("Test");
    	newBuddyList.add(buddy);
    	try {
			userDao.addBuddyList(newBuddyList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void shouldGetUserList() {
    	try {
			userDao.getUserList(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void shouldAddBuddyBasedOnselectionType() {
    	Buddies buddy = new Buddies();
    	buddy.setSso("600166698");
    	buddy.setUserDept("MIS");
    	buddy.setBuddySso("600166699");
    	buddy.setSelectionType("Test");
    	userDao.addBuddyBasedOnselectionType(buddy);
    }

    @Test
    public void shouldGetSelectionType() {
    	userDao.getSelectionType("600166698");
    }

    @Test
    public void shouldGetExistingBuddiesListFromUserMaster() {
    	userDao.getExistingBuddiesListFromUserMaster("Test", "600166698");
    }

    @Test
    public void shouldGetExistingBuddiesList() {
    	userDao.getExistingBuddiesList("600166698");
    }

    @Test
    public void shouldGetDeptDetailList() {
    	userDao.getDeptDetailList();
    }

    @Test
    public void shouldGetTermsAndCondition() {
    	userDao.getTermsAndCondition(new Date(0), "A");
    }
    
    @Test
    public void shouldGetUserVendorFilterSelectionsAsPenskeUser() {
    	userDao.getUserVendorFilterSelections(1);
    }

    @Test
    public void shouldGetAllOrganizations() {
    	userDao.getAllOrganizations();
    }

    @Test
    public void shouldGetOrganizationWithOrgId() {
    	userDao.getOrganizationWithOrgId(1);
    }

    @Test
    public void shouldDeletePreviousUserVendorFilters() {
    	userDao.deletePreviousUserVendorFilters(1);
    }

    @Test
    public void shouldSaveUserVendorFilterSelections() {
    	userDao.saveUserVendorFilterSelections(Arrays.asList(1), 1);
    }

    @Test
	public void shouldToggleVendorFilter() {
    	userDao.toggleVendorFilter(1);
    }

}
