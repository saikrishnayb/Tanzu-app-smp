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
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;

@DBSmc
public interface LoginDAO {

    @NonVendorQuery
    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);

    @NonVendorQuery
    public UserLoginHistory getUserLoginHistory(@Param("user") User user);

    @NonVendorQuery
    @SkipQueryTest
    public void putUserLogin(@Param("user") User user, @Param("serverLocation") String serverLocation, @Param("loginDate") Date loginDate);

    @NonVendorQuery
	public void deleteOtp(@Param("user") User user);

}
