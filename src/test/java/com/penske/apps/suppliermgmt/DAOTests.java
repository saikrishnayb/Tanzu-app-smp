package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.adminconsole.dao.AlertDaoTest;
import com.penske.apps.adminconsole.dao.CategoryManagementDAOTest;
import com.penske.apps.adminconsole.dao.ComponentDaoTest;
import com.penske.apps.adminconsole.dao.ComponentVendorTemplateDaoTest;
import com.penske.apps.adminconsole.dao.CostAdjustmentOptionDaoTest;
import com.penske.apps.adminconsole.dao.CostToleranceDaoTest;
import com.penske.apps.adminconsole.dao.DynamicRuleDaoTest;
import com.penske.apps.adminconsole.dao.ExceptionDaoTest;
import com.penske.apps.adminconsole.dao.GlobalSettingsDaoTest;
import com.penske.apps.adminconsole.dao.LoadsheetManagementDaoTest;
import com.penske.apps.adminconsole.dao.RoleDaoTest;
import com.penske.apps.adminconsole.dao.SecurityDAOTest;
import com.penske.apps.adminconsole.dao.VendorDaoTest;
import com.penske.apps.suppliermgmt.dao.AdminConsoleUserDAOTest;
import com.penske.apps.suppliermgmt.dao.HelpDAOTest;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDaoTest;
import com.penske.apps.suppliermgmt.dao.LoginDAOTest;
import com.penske.apps.suppliermgmt.dao.LookUpDAOTest;

/**
 * Tests that require a database connection (generally to an in-memory database, like HSQLDB)
 */
@RunWith(Suite.class)
@SuiteClasses({
	//admin console
	AlertDaoTest.class,
	CategoryManagementDAOTest.class,
	ComponentDaoTest.class,
	ComponentVendorTemplateDaoTest.class,
	DynamicRuleDaoTest.class,
	ExceptionDaoTest.class,
	GlobalSettingsDaoTest.class,
	LoadsheetManagementDaoTest.class,
	RoleDaoTest.class,	
	CostAdjustmentOptionDaoTest.class,
	CostToleranceDaoTest.class,
	VendorDaoTest.class,
	SecurityDAOTest.class,
	
	//suppliermgmt
	HelpDAOTest.class,
	HomeDashboardDaoTest.class,
	LoginDAOTest.class,
	LookUpDAOTest.class,
	AdminConsoleUserDAOTest.class

})
public class DAOTests{}
