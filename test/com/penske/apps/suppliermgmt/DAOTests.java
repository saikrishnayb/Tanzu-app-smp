package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.suppliermgmt.dao.HelpDAOTest;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDaoTest;
import com.penske.apps.suppliermgmt.dao.LoginDAOTest;
import com.penske.apps.suppliermgmt.dao.LookUpDAOTest;
import com.penske.apps.suppliermgmt.dao.UserDAOTest;
import com.pensle.apps.adminconsole.dao.CategoryManagementDAOTest;
import com.pensle.apps.adminconsole.dao.ShipThruLeadTimeDAOTest;

/**
 * Tests that require a database connection (generally to an in-memory database, like HSQLDB)
 */
@RunWith(Suite.class)
@SuiteClasses({
	//admin console
	CategoryManagementDAOTest.class,
	ShipThruLeadTimeDAOTest.class,
	
	//supplimgmt
	HelpDAOTest.class,
	HomeDashboardDaoTest.class,
	LoginDAOTest.class,
	LookUpDAOTest.class,
	UserDAOTest.class

})
public class DAOTests{}
