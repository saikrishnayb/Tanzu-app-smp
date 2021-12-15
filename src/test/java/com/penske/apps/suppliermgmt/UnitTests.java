package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.adminconsole.model.TransportUploadHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
	
	//Service Tests
    
    //Transporter / Vendor Order Report Tests
    TransportUploadHandlerTest.class

})
public class UnitTests{}
