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

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.domain.Organization;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;

public interface UserDAO {
		
	@NonVendorQuery //TODO: Review Query	
	
	public void deleteBuddyList(String userSSO) throws  SQLException;
	@NonVendorQuery //TODO: Review Query
	
	public void addBuddyList(List<Buddies> newBuddyList)throws  SQLException; 
	@NonVendorQuery //TODO: Review Query
	
	public List<User> getUserList(@Param("userType")int userType)throws  SQLException;
	
	@NonVendorQuery //TODO: Review Query
	public void addBuddyBasedOnselectionType(Buddies buddy);
	
	@NonVendorQuery //TODO: Review Query
	public String getSelectionType(@Param("loggedInSso") String loggedInSso);
	
	@NonVendorQuery //TODO: Review Query
	public List<Buddies> getExistingBuddiesListFromUserMaster(@Param("selectionType") String selectionType,@Param("loggedInSso") String loggedInSso);
	
	@NonVendorQuery //TODO: Review Query
	public List<Buddies> getExistingBuddiesList(@Param("userSso")String userSSO);

	@NonVendorQuery //TODO: Review Query
	public List<LabelValue> getDeptDetailList();

	@NonVendorQuery //TODO: Review Query
	public String getTermsAndCondition(@Param("date")Date date,@Param("status")String status);

	@NonVendorQuery //TODO: Review Query
	public List<UserVendorFilterSelection> getUserVendorFilterSelections(@Param("userId") int userId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Organization> getAllOrganizations();
	
	@NonVendorQuery //TODO: Review Query
	public Organization getOrganizationWithOrgId(@Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public void deletePreviousUserVendorFilters(@Param("userId") int userId);
	
	@NonVendorQuery //TODO: Review Query
	public void saveUserVendorFilterSelections(@Param("vendorIds") Collection<Integer> vendorIds, @Param("userId") int userId);
	
}
