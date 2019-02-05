/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.suppliermgmt.dao.CategoryManagementDAOTest;

/**
 * Suite class to run all unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
	
	//Service Tests
	
	//DAO Tests (Integration)
	CategoryManagementDAOTest.class,
})
public class AllTests {}
