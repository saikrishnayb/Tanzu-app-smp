package com.penske.apps.adminconsole.model;

import java.util.List;

/**
 * This class is the model object for the user of the application to store the header information.
 * 
 * @author kenneth.french
 *
 */

public class HeaderUser {
	
	private int userId;				// user ID
	private String ssoId;				// SSO (for Penske users only)
	private String email;			// email
	private String firstName;		// first name
	private String lastName;		// last name
	private String roleName;		// user role
	private String userTypeDesc;	// user type
	private int roleId;				// user role ID
	private int userTypeId;			// user type ID
	private String manufacturer;	// manufacturer
	private int orgId;
	private String orgName;
	private Integer parentOrgId;
	private boolean vendorSearch=false;
	private List<Integer> orgIds;
	
	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	// Getters
	public int getUserId() {
		return userId;
	}

	public String getSso() {
		return ssoId;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getUserTypeDesc() {
		return userTypeDesc;
	}

	public int getRoleId() {
		return roleId;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	// Setters
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setSso(String sso) {
		this.ssoId = sso;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "HeaderUser [userId=" + userId + ", sso=" + ssoId + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roleName=" + roleName + ", roleId=" + roleId
				+ ", userTypeId=" + userTypeId + ", manufacturer="
				+ manufacturer + "]";
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public boolean isVendorSearch() {
		return vendorSearch;
	}

	public void setVendorSearch(boolean vendorSearch) {
		this.vendorSearch = vendorSearch;
	}
	public List<Integer> getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(List<Integer> orgIds) {
		this.orgIds = orgIds;
	}

}
