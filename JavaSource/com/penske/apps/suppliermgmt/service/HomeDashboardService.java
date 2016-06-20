package com.penske.apps.suppliermgmt.service;

import java.util.List;
import java.util.Map;

import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.UserContext;


/**
 * This interface is used for queries to the database for the home/dashboard page.
 * 
 * @author Seth.Bauman 600143568
 */
public interface HomeDashboardService {
	public List<Tab> selectTabs(UserContext userModel);
	//public List<LookUp> getTheCount(String headerName,List<String> templateIdList,UserContext userModel);
	public Map<String, String[]> getActionCount (String SSO,String TabKey);
	public List<AlertHeader> getAlerts(String SSO,String TabKey,int userType);
}
