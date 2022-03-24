package com.penske.apps.adminconsole.model;

public class UserForm {
	private Integer userId;
	private String email;
	private String ssoId;
	private String firstName;
	private String lastName;
	private String phone;
	private String extension;
	private int userTypeId;
	private int orgId;
	private int roleId;
	private String gessouid;
	private boolean dailyOptIn;
	private boolean holdEnrollmentEmail;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getGessouid() {
		return gessouid;
	}

	public void setGessouid(String gessouid) {
		this.gessouid = gessouid;
	}

	public boolean isDailyOptIn() {
		return dailyOptIn;
	}

	public void setDailyOptIn(boolean dailyOptIn) {
		this.dailyOptIn = dailyOptIn;
	}

	public boolean isHoldEnrollmentEmail() {
		return holdEnrollmentEmail;
	}

	public void setHoldEnrollmentEmail(boolean holdEnrollmentEmail) {
		this.holdEnrollmentEmail = holdEnrollmentEmail;
	}
	
}
