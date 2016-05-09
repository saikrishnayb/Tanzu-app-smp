/**
 ************************************************************************************
 * File Name     : LabelValue
 * Description   : model class for DelayHistory
 * Project       : SMC
 * Package       : com.penske.apps.smcop.model
 * Author        : 502403391
 * Date			 : Mar 24, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 *===================================================================================
 * Version  |   Date    |   Change Details
 * ==================================================================================
 *
 * **********************************************************************************
 */
package com.penske.apps.suppliermgmt.common.util;

import java.util.ArrayList;
import java.util.List;

import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.LookUp;


public class LabelValueUtil {
	
	/**
	 * This method is used set the lookup values to list of label values
	 * 
	 * @param labelList
	 * @param lookUpName
	 */
	public static void setDropDownsData(List<LabelValue> labelList,String lookUpName)
	{
	
		LabelValue labelValue=null;
		LookupManager lookUpManager=new LookupManager();
		List<LookUp> lookupList=lookUpManager.getLookUpListByName(lookUpName);
		if(lookupList!=null)
		{
			for(LookUp lookUp:lookupList)
			{
				labelValue=new LabelValue();
				labelValue.setLabelId(0);
				labelValue.setLabelValue(lookUp.getLookUpValue());
				labelList.add(labelValue);
			}
		}
	}
	/**
	 * It will convert list of strings to list of labelvalues
	 * 
	 * @param List<String>
	 * @return List<LabelValue>
	 */
	public static List<LabelValue> getLabelValue(List<String> list){
		List<LabelValue> labelValueList = new ArrayList<LabelValue>();
		LabelValue bean = null;
		if(list!=null)
		{
			for(String value:list){
				bean = new LabelValue();
				bean.setLabelId(0);
				bean.setLabelValue(value);
				labelValueList.add(bean);
			}
		}
		return labelValueList;
	}
	/**
	 * It will convert List of LableValues to list strings
	 * @return List<String>
	 * @param List<LabelValue>
	 *  
	 */
	public static List<String> getStringList(List<LabelValue> list){
		
		List<String> labelValueList = new ArrayList<String>();
		if(list!=null)
		{
			for(LabelValue label:list)
			{
				labelValueList.add(label.getLabelValue());
			}
		}
		return labelValueList;
		
	}

}
