package com.penske.apps.suppliermgmt.model;

/**
 *****************************************************************************
 * File Name     : LabelValue 
 * Description   : POJO used to hold key and its values  
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
public class LabelValue{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911309572205062356L;
	private int labelId;
	private String labelValue;
	
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public String getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

}
