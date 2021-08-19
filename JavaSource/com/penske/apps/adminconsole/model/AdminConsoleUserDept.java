package com.penske.apps.adminconsole.model;

public class AdminConsoleUserDept {
	private String userDept;
	private String description;
	private int userDeptId;

	// Getters
	public String getUserDept() {
		return userDept;
	}

	public String getDescription() {
		return description;
	}

	public int getUserDeptId() {
		return userDeptId;
	}

	// Setters
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUserDeptId(int userDeptId) {
		this.userDeptId = userDeptId;
	}
}
