package com.penske.apps.suppliermgmt.service.impl;
/**
 *****************************************************************************************************************
 * File Name     : UserServiceImpl
 * Description   : UserServiceImpl class for get buddies list order by User Department
 * Project       : smcof
 * Package       : com.penske.apps.smcof.service.impl
 * Author        : 502409273
 * Date			 : April 30, 2015
 * Copyright (C) 2015 Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.exception.SMCException;
import com.penske.apps.suppliermgmt.dao.UserDAO;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.service.UserService;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;
	
		

	@Override
	public List<User> getUserDetails() throws SMCException{
		try{
			 List<User> userDetails = userDao.getUserList(ApplicationConstants.PENSKE_USER_TYPE);
		     return userDetails;
		} catch(SQLException ex){
			throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
		}catch(Exception e){
			throw new SMCException(0,e.getMessage(),e);
		}
	}    		 
		 


	@Override
	public void addBuddyList(List<Buddies> newBuddyList) throws SMCException {
		try {
		userDao.addBuddyList(newBuddyList);
	} catch(SQLException ex){
		throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
	}catch(Exception e){
		throw new SMCException(0,e.getMessage(),e);
	}
	}
	
	@Override
	public void addBuddyBasedOnselectionType(Buddies buddy) {
	
		userDao.addBuddyBasedOnselectionType(buddy);
	}
	
	@Override
	public void deleteBuddyList(String userSSO) throws SMCException 
	{
		try
		{
			userDao.deleteBuddyList(userSSO);
		}
		catch(SQLException ex)
		{
			throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
		}catch(Exception e)
		{
			throw new SMCException(0,e.getMessage(),e);
		}
	}
	
	@Override
	public List<Buddies> getExistingBuddiesList(String userSSO){
		
		
		String  selectionType=getSelectionType(userSSO);
		List<Buddies> existingBuddiesList=new ArrayList<Buddies>();
		
		if(selectionType!=null){
			existingBuddiesList= userDao.getExistingBuddiesListFromUserMaster(selectionType,userSSO);
			for(Buddies buddy:existingBuddiesList){
				buddy.setSelectionType(selectionType);
			}
		}
			existingBuddiesList.addAll(userDao.getExistingBuddiesList(userSSO));

		
		return existingBuddiesList;
	}
	
	
	@Override
	public String getSelectionType(String loggedInSso) {
		
		return userDao.getSelectionType(loggedInSso);
	}

	


	@Override
	public List<LabelValue> getDeptDetailList() {
		return userDao.getDeptDetailList();
	}



	@Override
	public String getTermsAndCondition(){
		String terms="Terms and Conditions data not available";
		String status=ApplicationConstants.ACTIVE;
		Calendar calendar=Calendar.getInstance();
		Date currentDate = new Date(calendar.getTime().getTime());
		terms=userDao.getTermsAndCondition(currentDate,status);
		return terms;
	}



	



	
	
}