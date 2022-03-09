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
	private int returnFlg;
	private String gessouid;
	private boolean dailyOptIn;
	private boolean holdEnrollmentEmail;
	
	private String otp;
	
	public Integer getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSsoId() {
		return ssoId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public int getUserTypeId() {
		return userTypeId;
	}
	
	public int getOrgId() {
		return orgId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public int getReturnFlg() {
		return returnFlg;
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
	
	public boolean isHoldEnrollmentEmail() {
		return holdEnrollmentEmail;
	}
	
	public String getOtp() {
		return otp;
	}
	
	public void setOtp(String otp) {
		this.otp = otp;
	}
}
