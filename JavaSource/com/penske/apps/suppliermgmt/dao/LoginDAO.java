package com.penske.apps.suppliermgmt.dao;

import java.util.Date;
/**
 *****************************************************************************************************************
 * File Name     : LoginDAO
 * Description   : DAO class for fetching userdetails
 * Project       : SMC
 * Package       : com.penske.apps.smcop.dao
 * Author        : 502403391
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.model.VendorLocation;

public interface LoginDAO {

    @NonVendorQuery
    public User getUserDetails(User userModel);

    @NonVendorQuery // This need to be here since we need to get the ass vendors ids at least
    public List<VendorLocation> getAssociatedVendors(int orgId);

    @NonVendorQuery
    public List<Tab> getTabs(int roleId);

    @NonVendorQuery
    public List<String> getSecurityFunctions(@Param("roleId") int roleId,@Param("tabKey") String tabKey);

    @NonVendorQuery
    public List<String> getAllSecurityFunctionsWithUser(UserContext userContext);

    @NonVendorQuery
    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);

    @NonVendorQuery
    public UserLoginHistory getUserLoginHistory(UserContext userContext);

    @NonVendorQuery
    public void putUserLogin(@Param("user") UserContext user, @Param("serverLocation") String serverLocation, @Param("loginDate") Date loginDate);

}
