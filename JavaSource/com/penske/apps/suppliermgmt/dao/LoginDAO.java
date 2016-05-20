package com.penske.apps.suppliermgmt.dao;
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

import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.VendorLocation;

public interface LoginDAO {

	public User getUserDetails(User userModel);

	public List<VendorLocation> getAssociatedVendors(int orgId);
	
	public List<Tab> getTabs(int roleId);

	public List<String> getSecurityFunctions(@Param("roleId") int roleId,@Param("tabKey") String tabKey);

}