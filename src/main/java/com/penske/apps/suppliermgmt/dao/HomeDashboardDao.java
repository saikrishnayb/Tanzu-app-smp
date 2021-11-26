package com.penske.apps.suppliermgmt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.smccore.base.domain.enums.SmcTab;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.domain.AlertCount;
import com.penske.apps.suppliermgmt.model.Alert;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;



/**
 *****************************************************************************************************************
 * File Name     : HomeDashboardDao
 * Description   : dao class for home/dashboard page. This interface is used for queries to the database
 * 				   for the home/dashboard page in the Admin Console under the App Config tab.
 * Project       : SMC
 * Package       : com.penske.apps.suppliermgmt.dao
 * Author        : 600143568
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 **/
@DBSmc
public interface HomeDashboardDao {

    @NonVendorQuery //FIXME: should this be filtered by vendor?
    public List<Tab> selectTabs(@Param("dashBoardId") int dashBoardId, @Param("roleId")int roleId, @Param("status") String status);

    @NonVendorQuery
    public List<AlertHeader> selectHeaders(@Param("tab") SmcTab tab);

    @NonVendorQuery
    public List<Alert> selectAlerts(@Param("headerId")int headerId, @Param("userType") UserType userType, @Param("penskeUserType") UserType penskeUserType);

    @SkipQueryTest
    @NonVendorQuery("Uses a stored procedure - can't filter by vendor ID")  //TODO: Review Query
    @MapKey("alertKey")
    public Map<String, AlertCount> getOrderConfirmationAlertCountsByAlertKey(@Param("sso") String sso);

    @SkipQueryTest
    @NonVendorQuery
    @MapKey("alertKey")
    public Map<String, AlertCount> getOrderFullfillmentCountsByAlertKey(@Param("sso") String sso);

    @SkipQueryTest
    @NonVendorQuery
    @MapKey("alertKey")
    public Map<String, AlertCount> getProductionAlertCountsByAlertKey(@Param("sso") String sso);

}
