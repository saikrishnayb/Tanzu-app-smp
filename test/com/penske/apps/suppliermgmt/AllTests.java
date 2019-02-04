/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.suppliermgmt.dao.CategoryManagementDAOTest;
import com.penske.apps.suppliermgmt.dao.ShipThruLeadTimeDAOTest;
import com.pensle.apps.adminconsole.domain.ShipThruLeadTimeTest;

/**
 * Suite class to run all unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
    ShipThruLeadTimeTest.class,
	
	//Service Tests
	
	//DAO Tests (Integration)
	CategoryManagementDAOTest.class,
	ShipThruLeadTimeDAOTest.class,
})
public class AllTests {}
