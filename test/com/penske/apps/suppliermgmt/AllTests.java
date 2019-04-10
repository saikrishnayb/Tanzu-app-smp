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
import com.pensle.apps.adminconsole.model.TransportUploadHandlerTest;

/**
 * Suite class to run all unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
    ShipThruLeadTimeTest.class,
	
	//Service Tests
    
    //Transporter / Vendor Order Report Tests
    TransportUploadHandlerTest.class,
	
	//DAO Tests (Integration)
	CategoryManagementDAOTest.class,
	ShipThruLeadTimeDAOTest.class,
})
public class AllTests {}
