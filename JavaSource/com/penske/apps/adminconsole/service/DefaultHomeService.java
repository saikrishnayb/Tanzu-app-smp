package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.HomeDao;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.AlertHeader;
import com.penske.apps.adminconsole.model.Tab;

/**
 * This class is used to access the database for the home/dashboard page.
 * 
 * @author 600143568
 */
@Service
public class DefaultHomeService implements HomeService {   
   
	@Autowired
    private HomeDao homeDao;

	private static Logger logger = Logger.getLogger(DefaultHomeService.class);
	
	@Override
	public List<Tab> selectTabs() {
		// Get the list of Dashboard Tabs from the database.
		List<Tab> tabs = null;
		try{
		 tabs = homeDao.selectTabs();
		}catch(Exception e){
			logger.error(e.getMessage());
			}
		
		// Iterate through each tab and get the necessary Alert Headers and Alerts.
		for (Tab currentTab : tabs) {
			// Get the list of Alert Headers for the current tab.
			List<AlertHeader> headers = homeDao.selectHeaders(currentTab.getTabId());
			
			// Iterate through each alert header and get the necessary Alerts.
			for (AlertHeader currentHeader : headers) {
				// Get the list of Alerts for the current alert header.
				currentHeader.setAlerts(homeDao.selectAlerts(currentHeader.getHeaderId()));
				
				// Set alert links for the dashboard if the alerts are actionable.
				for (Alert alert : currentHeader.getAlerts()) {
					if (alert.getActionable() == 1) {
						if (currentTab.getTabName().equalsIgnoreCase("order fulfillment")) {
							alert.setLink("./order-fulfillment?alertId=" + alert.getAlertId());
						}
						else if (currentTab.getTabName().equalsIgnoreCase("order confirmation")) {
							alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
						}
						else if (currentTab.getTabName().equalsIgnoreCase("production")) {
							alert.setLink("./production?alertId=" + alert.getAlertId());
						}
						else if (currentTab.getTabName().equalsIgnoreCase("communications")) {
							String alertName = alert.getAlertName();
							
							if (alertName.length() >= 18 && alertName.substring(0, 18).equalsIgnoreCase("order confirmation")) {
								alert.setLink("./order-confirmation?alertId=" + alert.getAlertId());
							}
							else if (alertName.length() >= 10 && alertName.substring(0, 10).equalsIgnoreCase("production")) {
								alert.setLink("./production?alertId=" + alert.getAlertId());
							}
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
}