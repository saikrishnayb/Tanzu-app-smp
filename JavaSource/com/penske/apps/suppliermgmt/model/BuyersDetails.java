/**
 *****************************************************************************
 * File Name     : BuyersDetails 
 * Description   : POJO used to carry basic details of a buyer.  
 * Project       : SMC
 * Package       : com.penske.apps.smc.model
 * Author        : 502405417
 * Date			 : 
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 */
package com.penske.apps.suppliermgmt.model;


public class BuyersDetails{

	/**
	 * 
	 */
	private String firstName;
	private String lastName;
	private int userId;
	private String userInitials;
	private String userName;
	
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserInitials() {
		return userInitials;
	}
	public void setUserInitials(String userInitials) {
		this.userInitials = userInitials;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
