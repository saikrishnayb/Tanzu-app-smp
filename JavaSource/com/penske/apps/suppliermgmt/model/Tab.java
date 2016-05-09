package com.penske.apps.suppliermgmt.model;

import java.util.List;



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
	private Integer tabId;
	private String tabKey;
	private String tabName;
	private Integer displaySequence;
	private String imageUrl;
	private Integer defaultTemplateId;
	private Integer dashboardTab;
	private List<AlertHeader> alertHeaders;	  // the list of alert headers for the tab
	private List<Permission> permissions;
	
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
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

	public Integer getDefaultTemplateId() {
		return defaultTemplateId;
	}
	public void setDefaultTemplateId(Integer defaultTemplateId) {
		this.defaultTemplateId = defaultTemplateId;
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
	
	
	public List<Permission> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
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
	
	
	
	
	

}
