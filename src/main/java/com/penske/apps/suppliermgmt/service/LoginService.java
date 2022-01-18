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

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;

public interface LoginService {

    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);

    /**
     * Records the fact that the user logged in, and returns an object representing the PREVIOUS time they logged in.
     * @param request The request, to get the server name from
     * @param user The user that is logging in
     * @return The user's previous login history. This does not include the current login.
     */
    public UserLoginHistory recordUserLogin(HttpServletRequest request, User user);

	public void deleteOTP(User user);

}