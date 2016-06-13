package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	public List<Integer> getDateTypes();
	public List<DelayModel> getDelays();
	public List<DelayReasonModel> getReasons();
	public List<Integer> getAssocReasonIds(Integer typeId);
	public DelayReasonModel getAssocReason(Integer reasonId);
	public DelayModel getTypeId(int delayId);
	public List<DelayTypeModel> getTypes();
	public void modifyDelay(DelayModel delay);
	public void addDelay(DelayModel delay);
	public void addDelayAssoc(DelayModel delay);
	public void deleteDelay(int delayId);
	public List <DelayPoModel> getPOs();
	public Integer getId(@Param("dateTypeId")String dateTypeId, @Param("poCategoryId")Integer poCategoryId, @Param("delayTypeReasonId")Integer delayTypeReasonId);
	public Integer checkDelay(DelayModel delay);
	// Delay Reason Types DAO methods
	public void addDelayType(String delayType);
	public DelayTypeModel getDelayType(String delayType);
	public List<Integer> getTypeAssociations(Integer typeId);
	public void deleteDelayReasonOfType(Integer reasonId);
	public void deleteDelayType(Integer typeId);
	public void modifyDelayType(DelayTypeModel delayType);
	//public void deleteDelaysWithType(int typeId);
	// Delay Reason Codes DAO methods
	public List<DelayModel> getAssociations();
	//public void deleteDelayAssociation(@Param("reasonId")Integer reasonId, @Param("typeId")Integer typeId);
	public void deleteDelayReasonName(Integer reasonId);
	public Integer checkAssociation(DelayReasonModel reason);
	public Integer checkReason(String reasonName);
	//public void modifyDelayReason(@Param("newReason")DelayReasonModel newReason, @Param("oldReason")DelayReasonModel oldReason);
	public void modifyReasonName(@Param("newName")String newName, @Param("id")Integer reasonId);
	public void addDelayReason(String reasonName);
	public Integer getDelayReasonId(String reasonName);
	public void addDelayAssociation(DelayTypeReason typeReason);
	public void deleteDelaysWithReason(int reasonId);
	public DelayTypeReason getTypeReasonAssoc(Integer reasonId);
	public DelayTypeReason  getTypeReasonAssocDate(Integer reasonId);
	public void  modifyDelayTypeReasonAssoc(DelayTypeReason typeReason);
	public void deleteDelayTypeReasonByAssoc(@Param("delayAssocid") Integer delayAssocid);
	public void deleteDelayByAssoc(@Param("delayAssocid") Integer delayAssocid);
	public void deleteAllDelayByReasonId(@Param("reasonId")Integer reasonId);
	public void deleteAllTypeReasonAssocByReasonId(@Param("reasonId")Integer reasonId);
	public void deleteAllDelayByTypeId(@Param("typeId")Integer typeId);
	public void deleteAllTypeReasonAssocByTypeId(@Param("typeId")Integer typeId);
	public DelayTypeReason  getAssocByTypeReasonId(@Param("typeId")Integer typeId,@Param("reasonId")Integer reasonId);
	public Integer checkDelayTypeExist(@Param("delayType") String delayType);
}
