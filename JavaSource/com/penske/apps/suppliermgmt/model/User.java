package com.penske.apps.suppliermgmt.model;

import java.util.Date;
import java.util.List;


import org.apache.commons.lang3.StringUtils;





/**
 *****************************************************************************
 * File Name     : User 
 * Description   : POJO used to hold user information 
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
public class User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String sso;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String extension;
	private String status;
	private int roleId;
	private int userType;
	private int userDept;
	private String userDeptName;
	private int orgId;
	private List<SecurityFunction> secFuncList;
	private List<SecurityFunction> tabList;
	private int tcVersion;
	private Date tcConfirmedDate;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSso() {
		return sso;
	}
	public void setSso(String sso) {
		this.sso = sso;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName=StringUtils.trimToEmpty(firstName);
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = StringUtils.trimToEmpty(lastName);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getUserDept() {
		return userDept;
	}
	public void setUserDept(int userDept) {
		this.userDept = userDept;
	}
	public String getUserDeptName() {
		return userDeptName;
	}
	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public List<SecurityFunction> getSecFuncList() {
		return secFuncList;
	}
	public void setSecFuncList(List<SecurityFunction> secFuncList) {
		this.secFuncList = secFuncList;
	}
	public List<SecurityFunction> getTabList() {
		return tabList;
	}
	public void setTabList(List<SecurityFunction> tabList) {
		this.tabList = tabList;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getTcVersion() {
		return tcVersion;
	}
	public void setTcVersion(int tcVersion) {
		this.tcVersion = tcVersion;
	}
	public Date getTcConfirmedDate() {
		return tcConfirmedDate;
	}
	public void setTcConfirmedDate(Date tcConfirmedDate) {
		this.tcConfirmedDate = tcConfirmedDate;
	}
}
