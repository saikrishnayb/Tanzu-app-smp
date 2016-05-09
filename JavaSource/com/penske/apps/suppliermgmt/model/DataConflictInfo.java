package com.penske.apps.suppliermgmt.model;

import java.util.List;
/*******************************************************************************
*
* @Author 		: 502299699
* @Version 	: 1.0
* @Date Created: May 15, 2015
* @Date Modified : 
* @Modified By : 
* @Contact 	:
* @Description : Model to hold data conflicts for production.
* @History		:
*
******************************************************************************/
public class DataConflictInfo{
	
	/**
	 * 
	 */
	private int dataConflictId;
	private String corp;
	private String unitNum;
	private String componentId;
	private String isGlobal;
	private String poCategory;
	private String poSubCategory;
	private String createdBy;
	private String modifiedBy;
	List<LabelValue> vendorList;

}
