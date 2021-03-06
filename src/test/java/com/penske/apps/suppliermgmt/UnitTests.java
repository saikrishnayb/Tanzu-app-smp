package com.penske.apps.suppliermgmt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.penske.apps.adminconsole.domain.CostToleranceTest;
import com.penske.apps.adminconsole.model.TransportUploadHandlerTest;
import com.penske.apps.adminconsole.model.VendorActivityReportTest;

@RunWith(Suite.class)
@SuiteClasses({
	//Utility classes
	
	//Domain Classes
	CostToleranceTest.class,
	
	//Service Tests
    
    //Transporter / Vendor Order Report Tests
    TransportUploadHandlerTest.class,
    VendorActivityReportTest.class

})
public class UnitTests{}
