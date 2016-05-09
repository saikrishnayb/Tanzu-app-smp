package com.penske.apps.suppliermgmt.model;

import java.util.Date;
/**
 *****************************************************************************
 * File Name     : LookUp 
 * Description   : POJO used to hold lookup data 
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
public class LookUp{

	private int lookUpId;
	private String lookUpName;
	private String lookUpValue;
	private String lookUpDesc;
	private int lookUpSeq;
	private String lookUpStatus;
	private String createdBy;
	private Date createdDate;
	private String maxCreatedDate;
	private String maxModifiedDate;
	private int count;
	
	public int getLookUpId() {
		return lookUpId;
	}
	public void setLookUpId(int lookUpId) {
		this.lookUpId = lookUpId;
	}
	public String getLookUpName() {
		return lookUpName;
	}
	public void setLookUpName(String lookUpName) {
		this.lookUpName = lookUpName;
	}
	public String getLookUpValue() {
		return lookUpValue;
	}
	public void setLookUpValue(String lookUpValue) {
		this.lookUpValue = lookUpValue;
	}
	public String getLookUpDesc() {
		return lookUpDesc;
	}
	public void setLookUpDesc(String lookUpDesc) {
		this.lookUpDesc = lookUpDesc;
	}
	public int getLookUpSeq() {
		return lookUpSeq;
	}
	public void setLookUpSeq(int lookUpSeq) {
		this.lookUpSeq = lookUpSeq;
	}
	public String getLookUpStatus() {
		return lookUpStatus;
	}
	public void setLookUpStatus(String lookUpStatus) {
		this.lookUpStatus = lookUpStatus;
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
	public String getMaxCreatedDate() {
		return maxCreatedDate;
	}
	public void setMaxCreatedDate(String maxCreatedDate) {
		this.maxCreatedDate = maxCreatedDate;
	}
	public String getMaxModifiedDate() {
		return maxModifiedDate;
	}
	public void setMaxModifiedDate(String maxModifiedDate) {
		this.maxModifiedDate = maxModifiedDate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
