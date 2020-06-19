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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.dao.SalesnetDAO;
import com.penske.apps.suppliermgmt.dao.UserDAO;
import com.penske.apps.suppliermgmt.domain.Organization;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.exception.SMCException;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.OrgFilter;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.model.VendorFilter;
import com.penske.apps.suppliermgmt.service.UserService;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private SalesnetDAO salesnetDao;
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	@Override
	public List<User> getUserDetails(boolean active) throws SMCException {
		try{
			 List<User> userDetails = userDao.getUserList(ApplicationConstants.PENSKE_USER_TYPE, active);
		     return userDetails;
		} catch(SQLException ex){
			throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
		}catch(Exception e){
			throw new SMCException(0,e.getMessage(),e);
		}
	}    		 

	@Override
	public void addBuddyList(List<Buddies> newBuddyList, String sso) throws SMCException {
		try {
    		userDao.addBuddyList(newBuddyList);
    		salesnetDao.addBuddyList(newBuddyList, sso);
    		
    	} catch(SQLException ex){
    		throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
    	}catch(Exception e){
    		throw new SMCException(0,e.getMessage(),e);
    	}
	}
	
	@Override
	public void addBuddyBasedOnselectionType(Buddies buddy, String sso) throws SMCException {
		try {
			userDao.addBuddyBasedOnselectionType(buddy);
			List<Buddies> buddyList = getExistingBuddiesList(sso);
			salesnetDao.addBuddyList(buddyList, sso);
		
		} catch(SQLException ex){
			throw new SMCException(ex.getErrorCode(),ex.getMessage(),ex);
		}catch(Exception e){
			throw new SMCException(0,e.getMessage(),e);
		}
	}
	
	@Override
	public void deleteBuddyList(String userSSO) throws SMCException {
		try
		{
			userDao.deleteBuddyList(userSSO);
			salesnetDao.deleteBuddyList(userSSO);
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
	public List<Buddies> getExistingBuddiesList(String userSSO) {
		
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

    @Override
    public List<OrgFilter> getAllOrgFilters() {
        
        UserContext userContext = sessionBean.getUserContext();
        Integer userId = userContext.getUserId();
        
        List<OrgFilter> orgFilters = new ArrayList<OrgFilter>();
        
        List<Organization> allOrganizations = userDao.getAllOrganizations();
        List<UserVendorFilterSelection> userVendorFilterSelections = userDao.getUserVendorFilterSelections(userId);
        
        //Lets create a quick map of all vendor ids the user selected by org id to make it easier to use later in this method
        Map<Integer, List<Integer>> userVendorIdSelectionsByOrgId = new HashMap<Integer, List<Integer>>();
        
        for (UserVendorFilterSelection userVendorFilterSelection : userVendorFilterSelections) {
            
            int orgId = userVendorFilterSelection.getOrgId();
            int vendorId = userVendorFilterSelection.getVendorId();
            
            boolean containsOrgId = userVendorIdSelectionsByOrgId.containsKey(orgId);
            
            if(containsOrgId)
                userVendorIdSelectionsByOrgId.get(orgId).add(vendorId);
            else
                userVendorIdSelectionsByOrgId.put(orgId, new ArrayList<Integer>(Arrays.asList(vendorId)));
        }
        
        for (Organization organization : allOrganizations) {
            
            int organizationId = organization.getOrganizationId();
            
            List<Integer> vendorIdSelections = userVendorIdSelectionsByOrgId.get(organizationId);
            
            orgFilters.add(new OrgFilter(organization, vendorIdSelections));
            
        }
        
        Collections.sort(orgFilters, OrgFilter.ORG_NAME);
        
        return orgFilters;
    }
    
    @Override
    public List<VendorFilter> getAllVendorFilters(int organizationId) {
        
        List<VendorFilter> vendorFilters = new ArrayList<VendorFilter>();
        
        Organization organization = userDao.getOrganizationWithOrgId(organizationId);
        
        for (Vendor vendor : organization.getVendors())
            vendorFilters.add(new VendorFilter(vendor));
        
        return vendorFilters;
    }

    @Override
    public void saveUserVendorFilterSelections(Collection<Integer> vendorIds) {

        UserContext userContext = sessionBean.getUserContext();
        Integer userId = userContext.getUserId();
        
        userDao.deletePreviousUserVendorFilters(userId);
        
        boolean noVendorIdSelections = vendorIds == null || vendorIds.size() == 0;
        if(noVendorIdSelections) return;
        
        userDao.saveUserVendorFilterSelections(vendorIds, userId);
        
    }
    
    @Override
    public void toggleVendorFilter() {
    	UserContext userContext = sessionBean.getUserContext();
    	Integer userId = userContext.getUserId();
    	
    	userDao.toggleVendorFilter(userId);
    	
    }
	
}