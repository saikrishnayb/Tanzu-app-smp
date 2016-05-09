package com.penske.apps.suppliermgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.penske.apps.suppliermgmt.dao.LookUpDAO;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.service.LookUpDataService;

/**
 *****************************************************************************
 * File Name     : LookUpDataServiceImpl 
 * Description   : Service class to load lookup data
 * Project       : SMC
 * Package       : com.penske.apps.smc.model
 * Author        : 502299699
 * Date			 : May 15, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 *  */
@Service
public class LookUpDataServiceImpl implements LookUpDataService {
	
	@Autowired
	LookUpDAO lookUpDAO;
	
	@Override
	public List<LookUp> getAllLookupList() {
		List<LookUp> lst=lookUpDAO.getAllLookupList();
		return lst;

	}

	/**
	 * Method to get the lookup details
	 */
	@Override
	public LookUp getLookupDetails() {
		
		return lookUpDAO.getLookupDetails();
	}

}
