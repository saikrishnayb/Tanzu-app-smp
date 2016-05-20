package com.penske.apps.suppliermgmt.model;

/**
 *****************************************************************************
 * File Name     : SecurityFunction 
 * Description   : POJO used to hold security functions for logged in user 
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
public class SecurityFunction {
	
	/**
	 * 
	 */
	public Integer getSecurityFunctionId() {
		return securityFunctionId;
	}
	public void setSecurityFunctionId(Integer securityFunctionId) {
		this.securityFunctionId = securityFunctionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTabKey() {
		return tabKey;
	}
	public void setTabKey(String tabKey) {
		this.tabKey = tabKey;
	}

	private Integer securityFunctionId;
	private String name;
	private String description;
	private String tabKey;
	

}
