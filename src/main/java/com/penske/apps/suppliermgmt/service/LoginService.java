package com.penske.apps.suppliermgmt.service;
/**
 ************************************************************************************
 * File Name     : LoginService
 * Description   : Service class for login
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

import org.apache.ibatis.annotations.Param;

import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;

public interface LoginService {

    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);
}