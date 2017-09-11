package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;
import com.penske.apps.adminconsole.model.DelayTypeReason;

/**
 * This is the Dao interface for method calls to the database
 * for Delay Management, Delay Types, and Delay Reasons
 * @author 600132441
 *
 */

public interface DelayDao{
   
	//ModelName getSomething(@Param("paramName") String paramName);  // ^^-- Do not use annotations to use xml.
	@NonVendorQuery //TODO: Review Query
	public List<Integer> getDateTypes();
	
	@NonVendorQuery //TODO: Review Query
	public List<DelayModel> getDelays();
	
	@NonVendorQuery //TODO: Review Query
	public List<DelayReasonModel> getReasons();
	
	@NonVendorQuery //TODO: Review Query
	public List<Integer> getAssocReasonIds(Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public DelayReasonModel getAssocReason(Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public DelayModel getTypeId(int delayId);
	
	@NonVendorQuery //TODO: Review Query
	public List<DelayTypeModel> getTypes();
	
	@NonVendorQuery //TODO: Review Query
	public void modifyDelay(DelayModel delay);
	
	@NonVendorQuery //TODO: Review Query
	public void addDelay(DelayModel delay);
	
	@NonVendorQuery //TODO: Review Query
	public void addDelayAssoc(DelayModel delay);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelay(int delayId);
	
	@NonVendorQuery //TODO: Review Query
	public List <DelayPoModel> getPOs();
	
	@NonVendorQuery //TODO: Review Query
	public Integer getId(@Param("dateTypeId")String dateTypeId, @Param("poCategoryId")Integer poCategoryId, @Param("delayTypeReasonId")Integer delayTypeReasonId);
	
	@NonVendorQuery //TODO: Review Query
	public Integer checkDelay(DelayModel delay);
	
	// Delay Reason Types DAO methods
	@NonVendorQuery //TODO: Review Query
	public void addDelayType(String delayType);
	
	@NonVendorQuery //TODO: Review Query
	public DelayTypeModel getDelayType(String delayType);
	
	@NonVendorQuery //TODO: Review Query
	public List<Integer> getTypeAssociations(Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelayReasonOfType(Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelayType(Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyDelayType(DelayTypeModel delayType);
	
	//public void deleteDelaysWithType(int typeId);
	// Delay Reason Codes DAO methods
	
	@NonVendorQuery //TODO: Review Query
	public List<DelayModel> getAssociations();
	//public void deleteDelayAssociation(@Param("reasonId")Integer reasonId, @Param("typeId")Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelayReasonName(Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public Integer checkAssociation(DelayReasonModel reason);
	
	@NonVendorQuery //TODO: Review Query
	public Integer checkReason(String reasonName);
	//public void modifyDelayReason(@Param("newReason")DelayReasonModel newReason, @Param("oldReason")DelayReasonModel oldReason);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyReasonName(@Param("newName")String newName, @Param("id")Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public void addDelayReason(String reasonName);
	
	@NonVendorQuery //TODO: Review Query
	public Integer getDelayReasonId(String reasonName);
	
	@NonVendorQuery //TODO: Review Query
	public void addDelayAssociation(DelayTypeReason typeReason);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelaysWithReason(int reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public DelayTypeReason getTypeReasonAssoc(Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public DelayTypeReason  getTypeReasonAssocDate(Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public void  modifyDelayTypeReasonAssoc(DelayTypeReason typeReason);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelayTypeReasonByAssoc(@Param("delayAssocid") Integer delayAssocid);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDelayByAssoc(@Param("delayAssocid") Integer delayAssocid);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteAllDelayByReasonId(@Param("reasonId")Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteAllTypeReasonAssocByReasonId(@Param("reasonId")Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteAllDelayByTypeId(@Param("typeId")Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteAllTypeReasonAssocByTypeId(@Param("typeId")Integer typeId);
	
	@NonVendorQuery //TODO: Review Query
	public DelayTypeReason  getAssocByTypeReasonId(@Param("typeId")Integer typeId,@Param("reasonId")Integer reasonId);
	
	@NonVendorQuery //TODO: Review Query
	public Integer checkDelayTypeExist(@Param("delayType") String delayType);
}
