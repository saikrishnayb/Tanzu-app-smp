package com.penske.apps.suppliermgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.dao.LoginDAO;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.model.VendorLocation;
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
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @Override
    public User getUserDetails(User userModel){
        return loginDao.getUserDetails(userModel);
    }


    @Override
    public List<VendorLocation> getAssociatedVendors(int orgId)
    {
        List<VendorLocation> vendorLocationList = new ArrayList<VendorLocation>();

        vendorLocationList = loginDao.getAssociatedVendors(orgId);

        return vendorLocationList;
    }


    @Override
    public Map<String, Map<String,String>> getTabs(int roleId)
    {
        List<Tab> tabList = new ArrayList<Tab>();
        Map<String,String> securityFunctions= null;
        List<String> secFunctionList = null;
        Map<String, Map<String,String>> tabSecFuncMap = new HashMap<String, Map<String,String>>();
        tabList = loginDao.getTabs(roleId);
        for(Tab tabs: tabList){
            securityFunctions= new HashMap<String, String>();
            secFunctionList = new ArrayList<String>();
            secFunctionList = loginDao.getSecurityFunctions(roleId, tabs.getTabKey());
            for(String secFunction: secFunctionList){
                securityFunctions.put(secFunction,secFunction);
            }
            tabSecFuncMap.put(tabs.getTabName(), securityFunctions);
        }

        return tabSecFuncMap;
    }

    @Override
    public Set<SecurityFunction> getAllUserSecurityFunctions(UserContext userContext) {

        Set<SecurityFunction> userSecurityFunctions = new HashSet<SecurityFunction>();
        List<String> allSecurityFunctionsWithUser = loginDao.getAllSecurityFunctionsWithUser(userContext);

        for (String securityFunctionString : allSecurityFunctionsWithUser) {

            SecurityFunction securityFunction = SecurityFunction.findByName(securityFunctionString);
            if (securityFunction == null) continue;

            userSecurityFunctions.add(securityFunction);
        }

        return userSecurityFunctions;
    }


    @Override
    public List<UserVendorFilterSelection> getUserVendorFilterSelections(
            int userId) {
        List<UserVendorFilterSelection> userVendorFilterSelections = loginDao.getUserVendorFilterSelections(userId);
        return userVendorFilterSelections;
    }


    @Override
    public void recordUserLogin(HttpServletRequest request, UserContext userContext) {

        UserLoginHistory userLoginHistory = loginDao.getUserLoginHistory(userContext);
        sessionBean.setLastUserLoginDate(userLoginHistory);

        int loginCount = userLoginHistory.getLoginCount();
        String serverName = request.getServerName();

        /*
         * If the user has logged in more than 30 times, pass in their firstLogin date so we can update that
         * row with this current login. If they have not logged in 30 times pass in null to force the query
         * to insert a new row.
         */
        loginDao.putUserLogin(userContext, serverName, loginCount > 29? userLoginHistory.getFirstLoginDate() : null);

    }

}
