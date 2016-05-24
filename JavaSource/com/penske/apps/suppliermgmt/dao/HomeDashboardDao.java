package com.penske.apps.suppliermgmt.dao;

import java.util.List;
import java.util.Map;

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
public interface HomeDashboardDao {

	public List<Tab> selectTabs(int roleId);

	public List<AlertHeader> selectHeaders(String tabKey);
	
	public int selectTabId(String tabKey);

	public List<Alert> selectAlerts(int headerId);
	
	public String getOrderFullfillmentActionItems(Map<String, Object> errorMap);
	
	public String getOrderConfirmationActionItems(Map<String, Object> errorMap);
	
	public String getProductionActionItems(Map<String, Object> errorMap);
	
	public String getCommunicationActionItems(Map<String, Object> errorMap);
	
}
