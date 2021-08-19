package com.penske.apps.suppliermgmt.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class AdminConsoleUserDAOTest extends MyBatisDaoTest{

	@Autowired
	private AdminConsoleUserDAO dao;

	@Before
	public void setup()
	{
		dao = this.trackMethodCalls(dao, AdminConsoleUserDAO.class);
	}

    @Test
    public void shouldDeleteBuddyList() {
    	try {
			dao.deleteBuddyList("600166698");
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
			dao.addBuddyList(newBuddyList);
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
    	dao.addBuddyBasedOnselectionType(buddy);
    }

    @Test
    public void shouldGetSelectionType() {
    	dao.getSelectionType("600166698");
    }

    @Test
    public void shouldGetExistingBuddiesListFromUserMaster() {
    	dao.getExistingBuddiesListFromUserMaster("Test", "600166698");
    }

    @Test
    public void shouldGetExistingBuddiesList() {
    	dao.getExistingBuddiesList("600166698");
    }

    @Test
    public void shouldGetDeptDetailList() {
    	dao.getDeptDetailList();
    }

    @Test
    public void shouldGetTermsAndCondition() {
    	dao.getTermsAndCondition(new Date(0), "A");
    }
    
    @Test
    public void shouldGetUserVendorFilterSelectionsAsPenskeUser() {
    	dao.getUserVendorFilterSelections(1);
    }

    @Test
    public void shouldGetAllOrganizations() {
    	dao.getAllOrganizations();
    }

    @Test
    public void shouldGetOrganizationWithOrgId() {
    	dao.getOrganizationWithOrgId(1);
    }

    @Test
    public void shouldDeletePreviousUserVendorFilters() {
    	dao.deletePreviousUserVendorFilters(1);
    }

    @Test
    public void shouldSaveUserVendorFilterSelections() {
    	dao.saveUserVendorFilterSelections(Arrays.asList(1), 1);
    }

    @Test
	public void shouldToggleVendorFilter() {
    	dao.toggleVendorFilter(1);
    }

}
