package com.penske.apps.suppliermgmt.model;

import java.sql.Date;
/**
 *****************************************************************************
 * File Name     : UpstreamVendor 
 * Description   : POJO used to hold delivery vendor details 
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
public class UpstreamVendor{
	
	/**
	 * 
	 */
	private int vendorChangeId;
	private int upStreamVenNo;
	private String dataType;
	private String unitNum;
    private String newValue;
    private int downStreamVenNo;
    private Date downSreamVenDate;
    private String changedBy;
    private String modifiedBy;

}
