package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;

public interface DelayService{
	// Delay Management Page Service methods
    public List<DelayModel> getDelays();
    public List<DelayPoModel> getPOs();
    public List<DelayReasonModel> getReasons();
    public List<DelayTypeModel> getTypes();
    public List<String> getDateTypes();
    public void modifyDelay(DelayModel delay);
    public void addDelay(DelayModel delay);
    public void deleteDelay(int delayId);
    public Integer getId(String dateTypeId, Integer poCategoryId, Integer typeId, Integer reasonId);
    public List<DelayReasonModel> getAssocReasons(Integer typeId);
    public int getTypeId(int delayId);
    public boolean checkDelay(DelayModel delay);
    // Delay Reason Types Page Service methods
    public boolean checkForExistingType(String delayType);
    public void addDelayType(String delayType);
    public DelayTypeModel getDelayType(String delayType);
    public void deleteDelayType(Integer delayId);
    public void modifyDelayType(DelayTypeModel delayType);
    // Delay Reason Codes Page Service methods
    public List<DelayModel> getAssociations();
    public void deleteDelayReason(Integer reasonId, Integer typeId);
    public boolean checkAssociation(DelayReasonModel reason);
    public boolean checkReason(String reasonName);
    public void modifyReason(DelayReasonModel newReason, DelayReasonModel oldReason);
    public DelayReasonModel addDelayReason(Integer typeId, String reasonName);
}