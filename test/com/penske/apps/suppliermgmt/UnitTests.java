package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.pensle.apps.adminconsole.domain.ShipThruLeadTimeTest;
import com.pensle.apps.adminconsole.model.TransportUploadHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
    ShipThruLeadTimeTest.class,
	
	//Service Tests
    
    //Transporter / Vendor Order Report Tests
    TransportUploadHandlerTest.class

})
public class UnitTests{}
