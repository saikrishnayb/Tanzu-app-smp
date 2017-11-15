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
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.model.VendorLocation;

public interface LoginService {

    public User getUserDetails(User userModel);

    public List<VendorLocation> getAssociatedVendors(int orgId);

    public Map<String, Map<String,String>> getTabs(int roleId);

    public Set<SecurityFunction> getAllUserSecurityFunctions(UserContext userContext);
    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);

}


