/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite class to run all unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
	UnitTests.class,
    DAOTests.class
})
public class AllTests {}
