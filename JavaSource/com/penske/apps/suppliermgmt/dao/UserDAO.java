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
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;


public interface UserDAO {
		
	public  void deleteBuddyList(String userSSO) throws  SQLException;
	
	public  void addBuddyList(List<Buddies> newBuddyList)throws  SQLException; 

	public List<User> getUserList(@Param("userType")int userType)throws  SQLException;
	

	public void addBuddyBasedOnselectionType(Buddies buddy);
	
	public String getSelectionType(@Param("loggedInSso") String loggedInSso);
	
	public List<Buddies> getExistingBuddiesListFromUserMaster(@Param("selectionType") String selectionType,@Param("loggedInSso") String loggedInSso);
	
	public List<Buddies> getExistingBuddiesList(@Param("userSso")String userSSO);

	public List<LabelValue> getDeptDetailList();

	public String getTermsAndCondition(@Param("date")Date date,@Param("status")String status);

	
	

	
	
	}
