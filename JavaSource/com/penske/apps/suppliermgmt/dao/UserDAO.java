package com.penske.apps.suppliermgmt.dao;

/**
 *****************************************************************************
 * File Name     : UserDAO
 * Description   : Interface for dao layer of fulfillment module's buddieslist
 * Project       : smcof Module
 * Package       : com.penske.apps.smcof.dao
 * Author        : 502409273
 * Date			 : APRIL 30, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 */

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.domain.Organization;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;

public interface UserDAO {

    @NonVendorQuery
    public void deleteBuddyList(String userSSO) throws  SQLException;

    @NonVendorQuery
    public void addBuddyList(List<Buddies> newBuddyList)throws  SQLException;

    @NonVendorQuery
    public List<User> getUserList(@Param("userType")int userType)throws  SQLException;

    @NonVendorQuery
    public void addBuddyBasedOnselectionType(Buddies buddy);

    @NonVendorQuery
    public String getSelectionType(@Param("loggedInSso") String loggedInSso);

    @NonVendorQuery
    public List<Buddies> getExistingBuddiesListFromUserMaster(@Param("selectionType") String selectionType,@Param("loggedInSso") String loggedInSso);

    @NonVendorQuery
    public List<Buddies> getExistingBuddiesList(@Param("userSso")String userSSO);

    @NonVendorQuery
    public List<LabelValue> getDeptDetailList();

    @NonVendorQuery
    public String getTermsAndCondition(@Param("date")Date date,@Param("status")String status);

    public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);

    @NonVendorQuery
    public List<Organization> getAllOrganizations();

    @NonVendorQuery
    public Organization getOrganizationWithOrgId(@Param("orgId") int orgId);

    @NonVendorQuery
    public void deletePreviousUserVendorFilters(@Param("userId") int userId);

    @NonVendorQuery
    public void saveUserVendorFilterSelections(@Param("vendorIds") Collection<Integer> vendorIds, @Param("userId") int userId);

}
