package com.penske.apps.suppliermgmt.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.dao.LoginDAO;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.service.LoginService;
/**
 *****************************************************************************
 * File Name     : LoginServiceImpl
 * Description   : Service class to load user session
 * Project       : SMC
 * Package       : com.penske.apps.smc.model
 * Author        : 502299699
 * Date			 : May 15, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 *  */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginDAO loginDao;

    @Override
    public List<UserVendorFilterSelection> getUserVendorFilterSelections(
            int userId) {
        List<UserVendorFilterSelection> userVendorFilterSelections = loginDao.getUserVendorFilterSelections(userId);
        return userVendorFilterSelections;
    }

    @Override
    public UserLoginHistory recordUserLogin(HttpServletRequest request, User user) {

        UserLoginHistory userLoginHistory = loginDao.getUserLoginHistory(user);

        int loginCount = userLoginHistory.getLoginCount();
        String serverName = request.getServerName();

        /*
         * If the user has logged in more than 30 times, pass in their firstLogin date so we can update that
         * row with this current login. If they have not logged in 30 times pass in null to force the query
         * to insert a new row.
         */
        loginDao.putUserLogin(user, serverName, loginCount > 29? userLoginHistory.getFirstLoginDate() : null);

        return userLoginHistory;
    }

}
