package com.penske.apps.suppliermgmt.service;

import com.penske.apps.smccore.base.domain.enums.UserType;

/**
 ************************************************************************************
 * File Name     : HelpService
 * Description   : Service class for help
 * Project       : SMC
 * Package       : com.penske.apps.smcop.service
 * Author        : 600104283
 * Date			 : Mar 24, 2015
 * Copyright (C) 2016 Penske Truck Leasing
 * Modifications :
 *===================================================================================
 * Version  |   Date    |   Change Details
 * ==================================================================================
 *
 * **********************************************************************************
 */

public interface HelpService { 
	
	public String getHelp(UserType userType);
}


