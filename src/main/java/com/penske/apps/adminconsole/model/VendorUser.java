package com.penske.apps.adminconsole.model;

public class VendorUser {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String formattedPhone;
	private String org;
	private String formattedCreatedDate;
	private String formattedLastLoginDate;
	private boolean hasOtp;
	
	private Role role;
	
	
	public VendorUser(EditableUser editableUser) {
		this.userId = editableUser.getUserId();
		this.firstName = editableUser.getFirstName();
		this.lastName= editableUser.getLastName();
		this.email= editableUser.getEmail();
		this.formattedPhone= editableUser.getFormattedPhone();
		this.org= editableUser.getOrg();
		this.formattedCreatedDate= editableUser.getFormattedCreatedDate();
		this.formattedLastLoginDate= editableUser.getFormattedLastLoginDate();
		this.role = editableUser.getRole();
		this.hasOtp = editableUser.isHasOtp();
	}

	public int getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getFormattedPhone() {
		return formattedPhone;
	}

	public String getOrg() {
		return org;
	}

	public String getFormattedCreatedDate() {
		return formattedCreatedDate;
	}

	public String getFormattedLastLoginDate() {
		return formattedLastLoginDate;
	}

	public boolean isHasOtp() {
		return hasOtp;
	}

	public Role getRole() {
		return role;
	}
	
}
