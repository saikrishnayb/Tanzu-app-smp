package com.penske.apps.suppliermgmt.service;
/**
 ************************************************************************************
 * File Name     : LookUpDataService
 * Description   : Service class for loading lookup data
 * Project       : SMC
 * Package       : com.penske.apps.smcop.service
 * Author        : 502403391
 * Date			 : Mar 24, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 *===================================================================================
 * Version  |   Date    |   Change Details
 * ==================================================================================
 *
 * **********************************************************************************
 */
import java.util.List;

import com.penske.apps.suppliermgmt.model.LookUp;

public interface LookUpDataService  {

	public List<LookUp> getAllLookupList();
	
	public LookUp getLookupDetails();
		

}
