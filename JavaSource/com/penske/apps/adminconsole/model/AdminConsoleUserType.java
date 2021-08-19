package com.penske.apps.adminconsole.model;

public class AdminConsoleUserType {

	private String userType;
	private String description;
	private int userTypeId;

	// Getters
	public String getUserType() {
		return userType;
	}

	public String getDescription() {
		return description;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	// Setters
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

}
