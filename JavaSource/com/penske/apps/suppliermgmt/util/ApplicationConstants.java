/**
 *****************************************************************************************************************
 * File Name     : ApplicationConstants
 * Description   : Class for holding Application constants 
 * Project       : SMC
 * Package       : com.penske.apps.smc.common.constants
 * Author        : 502328749
 * Date			 : Mar 24, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */

package com.penske.apps.suppliermgmt.util;


public class ApplicationConstants {

	public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
	public static final String SEC_FUNCTION_NOT_FOUND = "SEC_FUNCTION_NOT_FOUND";
	public static final String ASSOCIATED_VENDORS_NOT_FOUND = "ASSOCIATED_VENDORS_NOT_FOUND";
		
	/** Application name Constants **/
	public static final String SMCOF="smcof";
	public static final String SMCOP="smcop";
	public static final String ADMIN_CONSOLE="adminconsole";
	public static final String SLASH="/";
	public static final String DEV_ENTRY_SERVLET="/entry/ApplicationEntry?SSOID=";
	public static final String ENTRY_SERVLET="/entry/ApplicationEntry";
	
	public static final String URL="/entry/ApplicationEntry?SSOID=";
	public static final String DEV_CONTROLLER_NAME="&controllerName=";
	public static final String CONTROLLER_NAME="?controllerName=";
	public static final String DEV_TEMPLATE_KEY="&templateKey=";
	
	
	public static final String YES="yes";
	public static final String NO="no";
	
	/***Added for filter***/
	public static final String REFERER="Referer";
	public static final String SSOID="SSOID";
	public static final String COOKIE_DOMAIN_PRODQA = ".pensketruckleasing.net";
	public static final String COOKIE_DOMAIN_DEV = ".penske.com";
	public static final String EXTRANET_DOMAIN = "PENSKETRUCKLEASING.NET";
	
	/**Buddy Selection Type**/
	public static final String ALL_BUYER_SELECTION_TYPE = "ALLBUYERS";
	public static final String ALL_PLANNING_SELECTION_TYPE = "ALLPLANNING";
	public static final String ALL_SELECTION_TYPE = "ALL";

	public static final String ALL_CHECKBOX_ID="allCheckBox";
	public static final String ALL_PLANNING_CHECKBOX_ID="planCheckBox";
	public static final String ALL_BUYER_CHECKBOX_ID="buyCheckBox";
	public static final String ALL_RANDOM_CHECKBOX_ID="randomCheckBoxes";
	
	
	/**Penske user department ID**/
	public static final int PLAN_DEPT_ID = 2;
	public static final int PURCHASE_DEPT_ID = 1;
	
	public static final int INT_ZERO=0;
	public static final int INT_ONE=1;
	public static final int INT_TWO=2;
	public static final int PENSKE_USER_TYPE = 1;
	public static final int VENDOR_USER_TYPE = 2;
	
	public static final String ACTIVE = "A";
	
	/**support num**/
	public static final String SUPP_NUM="SUPPORT_NUM";
	
	/*** Look up data ***/
	public static final String HELP_LINK="HELP_LINK";
	public static final String MAX_CREATED_DATE="MAX_CREATED_DATE";
	public static final String MAX_MODIFIED_DATE="MAX_MODIFIED_DATE";
	public static final String COUNT="COUNT";
	public static final String TIMESTAMP_FORMAT="yyyy-MM-dd hh:mm:ss.SSS";
	
	public static final String VENDOR_TEMPLATE_URL = "app/admin-console/security/vendors-advanced-search-alert.htm?alertType=";
	
	
	/**TAB Key constants**/
	public static final String ORDER_FULFILLMENT_TAB_KEY = "TAB_OF";
	public static final String ORDER_CONFIRMATION_TAB_KEY = "TAB_OC";
	public static final String PRODUCTION_TAB_KEY = "TAB_PROD";
	
	public static final int DASHBOARD_TAB_ID = 1;
	
	public static final String DEL_MISSED_INFO = "PROD_DELV_MISSING_INFO";
	public static final String PROD_MISSING_INFO = "PROD_PROD_MISSING_INFO";
	public static final String PROD_OTHER_TAB = "HDR_PROD_OTHER";
	public static final String MISSING_INFO = "ALL_MISSING_INFO";
	
}


