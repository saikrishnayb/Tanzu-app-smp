package com.penske.apps.suppliermgmt.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDao;
import com.penske.apps.suppliermgmt.model.Alert;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.HomeDashboardService;


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
		List<Tab> tabs = homeDashboardDao.selectTabs(userModel.getRoleId());
		
		 // Iterate through each tab and get the necessary Alert Headers and Alerts.
		 for (Tab currentTab : tabs) {
			// Get the list of Alert Headers for the current tab.
			List<AlertHeader> headers = homeDashboardDao.selectHeaders(currentTab.getTabId());
			
			// Iterate through each alert header and get the necessary Alerts.
			for (AlertHeader currentHeader : headers) {
				// Get the list of Alerts for the current alert header.
				currentHeader.setAlerts(homeDashboardDao.selectAlerts(currentHeader.getHeaderId()));
				
				// Set alert links for the dashboard if the alerts are actionable.
				for (Alert alert : currentHeader.getAlerts()) {
					if (alert.getActionable() == 1) {
						switch(currentTab.getTabId()){
						case ApplicationConstants.ORDERFULFILLMENT_ID : alert.setLink("./order-fulfillment?alertId=" + alert.getAlertId());
						break;
						case ApplicationConstants.ORDERCONFIRMATION_ID: alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
						break;
						case ApplicationConstants.PRODUCTION_ID: alert.setLink("./production?alertId=" + alert.getAlertId());
						break;
						case ApplicationConstants.COMMUNICATION_ID: String alertName = alert.getAlertName();
						if (alertName.length() >= 18 && alertName.substring(0, 18).equalsIgnoreCase("order confirmation")) {
						alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
						}
						else if (alertName.length() >= 10 && alertName.substring(0, 10).equalsIgnoreCase("production")) {
							alert.setLink("./production?alertId=" + alert.getAlertId());
						}
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
	
	public List<AlertHeader> getAlerts(String sso,String tabKey){
		LOGGER.debug("Inside getAlerts()");
		int tabId = homeDashboardDao.selectTabId(tabKey);
		List<AlertHeader> headers = homeDashboardDao.selectHeaders(tabId);
		Set<String> alertIdList = new HashSet<String>();
		String[] countFlagArray;

		Map<String,String[]> alertMap = getActionCount(sso,tabId);
		if(alertMap != null || alertMap.size() > 0 ){
			alertIdList =  alertMap.keySet();
		}
		for (AlertHeader currentHeader : headers) {
			currentHeader.setAlerts(homeDashboardDao.selectAlerts(currentHeader.getHeaderId()));
			for(Alert alert : currentHeader.getAlerts()){
				if(alertIdList != null || alertIdList.size() > 0){
					for(String key : alertIdList){
						if(alert.getAlertKey().equalsIgnoreCase(key)){
							countFlagArray = alertMap.get(key);
							alert.setCount(Integer.parseInt(countFlagArray[0]));
							alert.setFlag(countFlagArray[1]);
						}
						if(alert.getActionable() == 1) {
						switch(tabId){
							case ApplicationConstants.ORDERFULFILLMENT_ID : alert.setLink("./order-fulfillment?alertId=" + alert.getAlertId());
							break;
							case ApplicationConstants.ORDERCONFIRMATION_ID: alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
							break;
							case ApplicationConstants.PRODUCTION_ID: alert.setLink("./production?alertId=" + alert.getAlertId());
							break;
							case ApplicationConstants.COMMUNICATION_ID: alert.setLink("./communication?alertId=" + alert.getAlertId());
							break;
							default : break;
							}
						}
					}
				}
			}
		}
		return headers;
	}
	/**
	 * Method to fetch alert count and flag value
	 * @param current SSOId,tabId
	 * @return Map<Integer,String[]>
	 */
	public Map<String,String[]>  getActionCount (String sso,int tabId){

		Map<String, Object> alertParamMap = new HashMap<String, Object>();
		Map<String,String[]> alertCountMap = new HashMap<String, String[]>();

		LOGGER.debug("Inside getActionCount()");
		alertParamMap.put("IN_SSO_ID", sso);
		alertParamMap.put("OUT_ACTION_ITEMS","");
		alertParamMap.put("ERR_CODE", 0);
		alertParamMap.put("ERR_MSG", "");
		
		switch(tabId){
		case ApplicationConstants.ORDERFULFILLMENT_ID : homeDashboardDao.getOrderFullfillmentActionItems(alertParamMap);
		break;
		case ApplicationConstants.ORDERCONFIRMATION_ID: homeDashboardDao.getOrderConfirmationActionItems(alertParamMap);
		break;
		case ApplicationConstants.PRODUCTION_ID: homeDashboardDao.getProductionActionItems(alertParamMap);
		break;
		case ApplicationConstants.COMMUNICATION_ID:
			homeDashboardDao.getCommunicationActionItems(alertParamMap);
		break;
		default : break;
		}
		if (!StringUtils.trimToEmpty(String.valueOf(alertParamMap.get("ERR_MSG"))).equals("SUCCESS")) {
			LOGGER.debug("Error message: " + alertParamMap.get("ERR_MSG"));
		} else {
			String actionItems= String.valueOf(alertParamMap.get("OUT_ACTION_ITEMS"));
			if(actionItems.length() > 0 && actionItems != ""){
				StringTokenizer alertIdCount = new StringTokenizer(actionItems,"|");
				String countResult[];
				String countFlagVal[];
				while (alertIdCount.hasMoreTokens()){
					countResult= alertIdCount.nextToken().split(",");
					countFlagVal = new String[2] ;
					countFlagVal[0] = StringUtils.trimToEmpty(countResult[1]);
					countFlagVal[1] = StringUtils.trimToEmpty(countResult[2]);
					alertCountMap.put(StringUtils.trimToEmpty(countResult[0]), countFlagVal);
				}
			}
			
		}

		return alertCountMap;
	}	
}