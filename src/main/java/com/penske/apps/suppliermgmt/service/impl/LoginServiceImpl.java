package com.penske.apps.suppliermgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.suppliermgmt.dao.LoginDAO;
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
}
