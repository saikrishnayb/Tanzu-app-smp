package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.suppliermgmt.dao.HelpDAOTest;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDaoTest;
import com.penske.apps.suppliermgmt.dao.LoginDAOTest;
import com.penske.apps.suppliermgmt.dao.LookUpDAOTest;
import com.penske.apps.suppliermgmt.dao.AdminConsoleUserDAOTest;
import com.pensle.apps.adminconsole.dao.AlertDaoTest;
import com.pensle.apps.adminconsole.dao.CategoryManagementDAOTest;
import com.pensle.apps.adminconsole.dao.ComponentDaoTest;
import com.pensle.apps.adminconsole.dao.ComponentVendorTemplateDaoTest;
import com.pensle.apps.adminconsole.dao.CostAdjustmentOptionDaoTest;
import com.pensle.apps.adminconsole.dao.CostToleranceDaoTest;
import com.pensle.apps.adminconsole.dao.DynamicRuleDaoTest;
import com.pensle.apps.adminconsole.dao.ExceptionDaoTest;
import com.pensle.apps.adminconsole.dao.GlobalSettingsDaoTest;
import com.pensle.apps.adminconsole.dao.LoadsheetManagementDaoTest;
import com.pensle.apps.adminconsole.dao.RoleDaoTest;

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
	
	//suppliermgmt
	HelpDAOTest.class,
	HomeDashboardDaoTest.class,
	LoginDAOTest.class,
	LookUpDAOTest.class,
	AdminConsoleUserDAOTest.class

})
public class DAOTests{}
