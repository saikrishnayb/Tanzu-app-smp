package com.penske.apps.suppliermgmt.dao;

import java.util.List;
import java.util.Map;

import com.penske.apps.suppliermgmt.model.Alert;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;





/**
 * This interface is used for queries to the database for the home/dashboard page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
public interface HomeDashboardDao {

	public List<Tab> selectTabs(int roleId);

	public List<AlertHeader> selectHeaders(int tabId);

	public List<Alert> selectAlerts(int headerId);
	
	public String getOrderFullfillmentActionItems(Map<String, Object> errorMap);
	
	public String getOrderConfirmationActionItems(Map<String, Object> errorMap);
	
	public String getProductionActionItems(Map<String, Object> errorMap);
	
	public String getCommunicationActionItems(Map<String, Object> errorMap);
	
//	public LookUp getTheCount(OrderFulfillment orderFulfillment);
}
