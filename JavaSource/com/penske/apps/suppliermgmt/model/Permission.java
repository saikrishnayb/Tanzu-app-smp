package com.penske.apps.suppliermgmt.model;

public class Permission {
	private int securityFunctionId;
	private String name;
	private String description;
	private String tabName;
	private boolean available;

	// Getters
	public int getSecurityFunctionId() {
		return securityFunctionId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getTabName() {
		return tabName;
	}
	
	public boolean getAvailable() {
		return available;
	}

	// Setters
	public void setSecurityFunctionId(int securityFunctionId) {
		this.securityFunctionId = securityFunctionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
}
