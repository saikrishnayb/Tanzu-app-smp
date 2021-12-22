package com.penske.apps.suppliermgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.smccore.base.domain.ConfirmationAlertData;
import com.penske.apps.smccore.base.domain.FulfillmentAlertData;
import com.penske.apps.smccore.base.domain.ProductionAlertData;
import com.penske.apps.smccore.base.domain.SmcAlert;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SmcTab;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.smccore.base.service.AlertsService;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDao;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.AlertView;
import com.penske.apps.suppliermgmt.model.Tab;
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
	@Autowired
	private AlertsService alertsService;

	@Override
	public List<Tab> selectTabs(User user) {
		// Get the list of Dashboard Tabs from the database.
		List<Tab> tabs = homeDashboardDao.selectTabs(ApplicationConstants.DASHBOARD_TAB_ID,user.getRoleId(),ApplicationConstants.ACTIVE);

		// Iterate through each tab and get the necessary Alert Headers and Alerts.
		for (Tab currentTab : tabs) {
			// Put the tab name in all caps for display on the dashboard.
			currentTab.setTabName(currentTab.getTabName().toUpperCase());
		}

		return tabs;
	}
	
	@Override
	public List<AlertHeader> getAlertHeaders(SmcTab tabKey) {
		return homeDashboardDao.selectHeaders(tabKey);
	}

	/**
	 * Method to fetch alertHeaders with corresponding alert count
	 * @param current SSOId,tabKey
	 * @return List<AlertHeader>
	 */
	public Map<Integer, List<AlertView>> getAlertsByHeaderId(User user, SmcTab tabKey, List<AlertHeader> alertHeaders) {

		FulfillmentAlertData fulfillmentAlertData;
		ConfirmationAlertData confirmationAlertData;
		ProductionAlertData productionAlertData;

		switch(tabKey){
		case ORDER_FULFILLMENT : 
			fulfillmentAlertData = alertsService.getFulfillmentAlertData(user);
			confirmationAlertData = null;
			productionAlertData = null;
			break;
		case ORDER_CONFIRMATION: 
			fulfillmentAlertData = null;
			confirmationAlertData = alertsService.getConfirmationAlertDataByVendorId(user);
			productionAlertData = null;
			break;
		case PRODUCTION: 
			fulfillmentAlertData = null;
			confirmationAlertData = null;
			productionAlertData = alertsService.getProductionAlertDataByVendorId(user);
			break;
		default : 
			fulfillmentAlertData = null;
			confirmationAlertData = null;
			productionAlertData = null;
			break;
		}
		Map<Integer, List<AlertView>> result = new HashMap<>();
		for (AlertHeader currentHeader : alertHeaders) {

			List<SmcAlert> alerts = alertsService.getAlertsForTab(tabKey, currentHeader.getHeaderId(), user.getUserType(), UserType.PENSKE);

			for(SmcAlert alert : alerts){
				int alertCount = alert.getAlertType().extractCount(fulfillmentAlertData, confirmationAlertData, productionAlertData);

				String alertLink = "";
				if(alert.isActionable()) {
					switch(tabKey){
					case ORDER_FULFILLMENT : 
						alertLink = "./order-fulfillment?alertId=" + alert.getAlertID();
						break;
					case ORDER_CONFIRMATION: 
						alertLink = "./order-confirmation?alertId=" + alert.getAlertID();
						break;
					case PRODUCTION: 
						alertLink = "./production?alertId=" + alert.getAlertID();
						break;
					default : break;
					}
				}
				List<AlertView> alertViews = result.computeIfAbsent(alert.getHeaderId(), list -> new ArrayList<>());
				alertViews.add(new AlertView(alert, alertCount, alertLink));
			}
		}
		return result;
	}
}