/**
 *****************************************************************************
 * File Name     : Buddies 
 * Description   : POJO used to hold Associated Buddies information  
 * Project       : SMC
 * Package       : com.penske.apps.smc.model
 * Author        : 502405417
 * Date			 : Apr 28, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 */

package com.penske.apps.suppliermgmt.model;
import java.io.Serializable;
import java.util.Date;

public class Buddies implements Serializable{
	
	
	private static final long serialVersionUID = -365389243253189648L;
	
	private String sso;
	private String buddySso;
	private String userDept;
	private String createdBy;
	private Date createdDate;
	private String selectionType;
	
	public String getSso() {
		return sso;
	}
	public void setSso(String sso) {
		this.sso = sso;
	}
	public String getBuddySso() {
		return buddySso;
	}
	public void setBuddySso(String buddySso) {
		this.buddySso = buddySso;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getSelectionType() {
		return selectionType;
	}
	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}
}
