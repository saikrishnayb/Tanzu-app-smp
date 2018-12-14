package com.penske.apps.suppliermgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.suppliermgmt.dao.HomeDashboardDao;
import com.penske.apps.suppliermgmt.domain.AlertCount;
import com.penske.apps.suppliermgmt.model.Alert;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.HomeDashboardService;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;


/**
 *****************************************************************************************************************
 * File Name     : DefaultHomeService
 * Description   : Service impl class for home/dashboard page. Used to access the database
 * 				   for getting tab details,alert details and alert count   
 * Project       : SMC
 * Package       : com.penske.apps.suppliermgmt.service.impl
 * Author        : 600143568
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
@Service
public class DefaultHomeDashboardService implements HomeDashboardService {   
   
	@Autowired
    private HomeDashboardDao homeDashboardDao;
	
	private static final Logger LOGGER = Logger.getLogger(DefaultHomeDashboardService.class);

	@Override
	public List<Tab> selectTabs(UserContext userModel) {
		// Get the list of Dashboard Tabs from the database.
		List<Tab> tabs = homeDashboardDao.selectTabs(ApplicationConstants.DASHBOARD_TAB_ID,userModel.getRoleId(),ApplicationConstants.ACTIVE);
		
		 // Iterate through each tab and get the necessary Alert Headers and Alerts.
		 for (Tab currentTab : tabs) {
			// Get the list of Alert Headers for the current tab.
			List<AlertHeader> headers = homeDashboardDao.selectHeaders(currentTab.getTabKey());
			
			// Iterate through each alert header and get the necessary Alerts.
			for (AlertHeader currentHeader : headers) {
				// Get the list of Alerts for the current alert header.
				currentHeader.setAlerts(homeDashboardDao.selectAlerts(currentHeader.getHeaderId(),userModel.getUserType()));
				
				// Set alert links for the dashboard if the alerts are actionable.
				for (Alert alert : currentHeader.getAlerts()) {
					if (alert.getActionable() == 1) {
						switch(TabKeyVal.valueOf(currentTab.getTabKey())){
						case TAB_OF : alert.setLink("./order-fulfillment?alertId=" + alert.getAlertId());
						break;
						case TAB_OC: alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
						break;
						case TAB_PROD: alert.setLink("./production?alertId=" + alert.getAlertId());
						break;
						default : break;
						}
					}
				}
			}
			
			currentTab.setAlertHeaders(headers);
			
			// Put the tab name in all caps for display on the dashboard.
			currentTab.setTabName(currentTab.getTabName().toUpperCase());
		}
		
		return tabs;
	}
	
	/**
	 * Method to fetch alertHeaders with corresponding alert count
	 * @param current SSOId,tabKey
	 * @return List<AlertHeader>
	 */
	public List<AlertHeader> getAlerts(String sso,String tabKey,int userType) {
	    
		LOGGER.debug("Inside getAlerts()");
		List<AlertHeader> headers = homeDashboardDao.selectHeaders(tabKey);
		
		Map<String, AlertCount> alertCounts = new HashMap<String, AlertCount>();
		
		switch(TabKeyVal.valueOf(tabKey)){
            case TAB_OF : alertCounts = homeDashboardDao.getOrderFullfillmentCountsByAlertKey(sso);
            break;
            case TAB_OC: alertCounts = homeDashboardDao.getOrderConfirmationAlertCountsByAlertKey(sso);
            break;
            case TAB_PROD: alertCounts = homeDashboardDao.getProductionAlertCountsByAlertKey(sso);
            break;
            default : break;
        }
		
		for (AlertHeader currentHeader : headers) {
		    
			currentHeader.setAlerts(homeDashboardDao.selectAlerts(currentHeader.getHeaderId(),userType));
			
			for(Alert alert : currentHeader.getAlerts()){
			    
			    String alertKey = alert.getAlertKey();
			    
			    AlertCount alertCount = alertCounts.get(alertKey);
			    
			    boolean noAlertCount = alertCount == null;
			    if(noAlertCount) continue;
			    
			    
			    alert.updateAlertCount(alertCount);
			    
				if(alert.getActionable() == 1) {
				switch(TabKeyVal.valueOf(tabKey)){
					case TAB_OF : alert.setLink("./order-fulfillment?alertId=" + alert.getAlertId());
					break;
					case TAB_OC: alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
					break;
					case TAB_PROD: alert.setLink("./production?alertId=" + alert.getAlertId());
					break;
					default : break;
				}
				}
			}
		}
		return headers;
	}
	
	/**
	 * enum method to provide tabKey values to check
	 **/
	public enum TabKeyVal{
		TAB_OF, TAB_OC, TAB_PROD
	}
}