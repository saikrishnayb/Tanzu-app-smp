package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;
import com.penske.apps.adminconsole.model.DelayTypeReason;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;

/**
 * This is the Dao interface for method calls to the database
 * for Delay Management, Delay Types, and Delay Reasons
 * @author 600132441
 *
 */

public interface DelayDao{

    //ModelName getSomething(@Param("paramName") String paramName);  // ^^-- Do not use annotations to use xml.
    @NonVendorQuery
    public List<Integer> getDateTypes();

    @NonVendorQuery
    public List<DelayModel> getDelays();

    @NonVendorQuery
    public List<DelayReasonModel> getReasons();

    @NonVendorQuery
    public List<Integer> getAssocReasonIds(Integer typeId);

    @NonVendorQuery
    public DelayReasonModel getAssocReason(Integer reasonId);

    @NonVendorQuery
    public DelayModel getTypeId(int delayId);

    @NonVendorQuery
    public List<DelayTypeModel> getTypes();

    @NonVendorQuery
    public void modifyDelay(DelayModel delay);

    @NonVendorQuery
    public void addDelay(DelayModel delay);

    @NonVendorQuery
    public void addDelayAssoc(DelayModel delay);

    @NonVendorQuery
    public void deleteDelay(int delayId);

    @NonVendorQuery
    public List <DelayPoModel> getPOs();

    @NonVendorQuery
    public Integer getId(@Param("dateTypeId")String dateTypeId, @Param("poCategoryId")Integer poCategoryId, @Param("delayTypeReasonId")Integer delayTypeReasonId);

    @NonVendorQuery
    public Integer checkDelay(DelayModel delay);

    // Delay Reason Types DAO methods
    @NonVendorQuery
    public void addDelayType(String delayType);

    @NonVendorQuery
    public DelayTypeModel getDelayType(String delayType);

    @NonVendorQuery
    public List<Integer> getTypeAssociations(Integer typeId);

    @NonVendorQuery
    public void deleteDelayReasonOfType(Integer reasonId);

    @NonVendorQuery
    public void deleteDelayType(Integer typeId);

    @NonVendorQuery
    public void modifyDelayType(DelayTypeModel delayType);

    //public void deleteDelaysWithType(int typeId);
    // Delay Reason Codes DAO methods

    @NonVendorQuery
    public List<DelayModel> getAssociations();
    //public void deleteDelayAssociation(@Param("reasonId")Integer reasonId, @Param("typeId")Integer typeId);

    @NonVendorQuery
    public void deleteDelayReasonName(Integer reasonId);

    @NonVendorQuery
    public Integer checkAssociation(DelayReasonModel reason);

    @NonVendorQuery
    public Integer checkReason(String reasonName);
    //public void modifyDelayReason(@Param("newReason")DelayReasonModel newReason, @Param("oldReason")DelayReasonModel oldReason);

    @NonVendorQuery
    public void modifyReasonName(@Param("newName")String newName, @Param("id")Integer reasonId);

    @NonVendorQuery
    public void addDelayReason(String reasonName);

    @NonVendorQuery
    public Integer getDelayReasonId(String reasonName);

    @NonVendorQuery
    public void addDelayAssociation(DelayTypeReason typeReason);

    @NonVendorQuery
    public void deleteDelaysWithReason(int reasonId);

    @NonVendorQuery
    public DelayTypeReason getTypeReasonAssoc(Integer reasonId);

    @NonVendorQuery
    public DelayTypeReason  getTypeReasonAssocDate(Integer reasonId);

    @NonVendorQuery
    public void  modifyDelayTypeReasonAssoc(DelayTypeReason typeReason);

    @NonVendorQuery
    public void deleteDelayTypeReasonByAssoc(@Param("delayAssocid") Integer delayAssocid);

    @NonVendorQuery
    public void deleteDelayByAssoc(@Param("delayAssocid") Integer delayAssocid);

    @NonVendorQuery
    public void deleteAllDelayByReasonId(@Param("reasonId")Integer reasonId);

    @NonVendorQuery
    public void deleteAllTypeReasonAssocByReasonId(@Param("reasonId")Integer reasonId);

    @NonVendorQuery
    public void deleteAllDelayByTypeId(@Param("typeId")Integer typeId);

    @NonVendorQuery
    public void deleteAllTypeReasonAssocByTypeId(@Param("typeId")Integer typeId);

    @NonVendorQuery
    public DelayTypeReason  getAssocByTypeReasonId(@Param("typeId")Integer typeId,@Param("reasonId")Integer reasonId);

    @NonVendorQuery
    public Integer checkDelayTypeExist(@Param("delayType") String delayType);
}
