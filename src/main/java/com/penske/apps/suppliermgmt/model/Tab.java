package com.penske.apps.suppliermgmt.model;

import java.util.List;

import com.penske.apps.smccore.base.domain.enums.SmcTab;



/**
 *****************************************************************************
 * File Name     : Tab 
 * Description   : POJO used to hold application tabs 
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
 */
public class Tab{
	
	/**
	 * 
	 */
	private String tabKey;
	private int tabId;
	private String tabName;
	private Integer displaySequence;
	private String imageUrl;
	private String defaultTemplateKey;
	private Integer dashboardTab;
	private List<AlertHeader> alertHeaders;	  // the list of alert headers for the tab
	
	public SmcTab getSmcTab()
	{
		return SmcTab.findByTabKey(this.tabKey);
	}
	
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public Integer getDisplaySequence() {
		return displaySequence;
	}
	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getDefaultTemplateKey() {
		return defaultTemplateKey;
	}
	public void setDefaultTemplateKey(String defaultTemplateKey) {
		this.defaultTemplateKey = defaultTemplateKey;
	}
	public Integer getDashboardTab() {
		return dashboardTab;
	}
	public void setDashboardTab(Integer dashboardTab) {
		this.dashboardTab = dashboardTab;
	}
	public List<AlertHeader> getAlertHeaders() {
		return alertHeaders;
	}
	
	public void setAlertHeaders(List<AlertHeader> alertHeaders) {
		this.alertHeaders = alertHeaders;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the tabKey
	 */
	public String getTabKey() {
		return tabKey;
	}
	/**
	 * @param tabKey the tabKey to set
	 */
	public void setTabKey(String tabKey) {
		this.tabKey = tabKey;
	}
	public int getTabId() {
		return tabId;
	}
	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

}
