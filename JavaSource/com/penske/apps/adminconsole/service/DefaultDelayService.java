package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.DelayDao;
import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.LookUp;

/**
 * This is the Defauly Delay Service class that implements
 * Delay Service interface which contains all the business
 * logic for Delay Management, Delay Types, and Delay Reasons.
 * @author 600132441 M.Leis
 *
 */
@Service
public class DefaultDelayService implements DelayService{   
   
	@Autowired
    private DelayDao delayDao;
	@Autowired
	private LookupManager lookupManager;

	@Override
	public List<String> getDateTypes(){
		
	//	List<Integer> dateTypes = delayDao.getDateTypes();
		List<String> dateTypes=null;
		List<LookUp> lookUpDateTypes= lookupManager.getLookUpListByName(ApplicationConstants.DELAY_DATE_TYPE);
		if(lookUpDateTypes !=null){
			dateTypes=new ArrayList<String>();
			for (LookUp lookUp : lookUpDateTypes) {
				dateTypes.add(lookUp.getLookUpValue());
			}
		}
		return dateTypes;
	}
	@Override
	public boolean checkDelay(DelayModel delay){
		
		Integer result = delayDao.checkDelay(delay);
		if(result == null) return false;
		else if(result == 1) return true;
		return false;
	}
	@Override
	public List<DelayModel> getDelays() {
		
		List<DelayModel> delays = delayDao.getDelays();
		return delays;
	}
	@Override
	public List<DelayReasonModel> getReasons(){
		
		List<DelayReasonModel> reasons = delayDao.getReasons();
		return reasons;
	}
	@Override
	public int getTypeId(int delayId){
		
		int typeId = delayDao.getTypeId(delayId);
		return typeId;
	}
	@Override
	public List<DelayReasonModel> getAssocReasons(Integer typeId){
		
		List<Integer> reasonIds = delayDao.getAssocReasonIds(typeId);
		Iterator<Integer> reasonIter = reasonIds.iterator();
		List<DelayReasonModel> reasons = new ArrayList<DelayReasonModel>();
		while( reasonIter.hasNext() ){
			
			Integer thisReasonId = reasonIter.next();
			reasons.add( delayDao.getAssocReason(thisReasonId) );
		}
		return reasons;
	}
	@Override
	public List<DelayTypeModel> getTypes(){
		
		List<DelayTypeModel> types = delayDao.getTypes();
		return types;
	}
	@Override
	public void modifyDelay(DelayModel delay){
		
		delayDao.modifyDelay(delay);
	}
	@Override
	public void addDelay(DelayModel delay){
		
		// add to SMC_DELAYS
		delayDao.addDelay(delay);
	}
	@Override
	public void deleteDelay(int delayId){
		
		delayDao.deleteDelay(delayId);
	}
	@Override
	public List<DelayPoModel> getPOs(){
		
		List<DelayPoModel> POs = delayDao.getPOs();
		return POs;
	}
	@Override
	public Integer getId(String dateTypeId, Integer poCategoryId, Integer delayTypeId, Integer delayReasonId){
		
		Integer id = delayDao.getId(dateTypeId, poCategoryId, delayTypeId, delayReasonId);
		return id;
	}
	
	
	// Delay Reason Types service methods
	@Override
	public boolean checkForExistingType(String delayType){
		
		List<DelayTypeModel> types = delayDao.getTypes();
		Iterator<DelayTypeModel> typeIter = types.iterator();
		
		while( typeIter.hasNext() ){
			
			DelayTypeModel thisType = typeIter.next();
			
			if( thisType.getDelayType().equals(delayType) ){
				
				// name already exists.  return true
				return true;
			}
		}
		// no match found.  it's good to add the new delay type
		return false;
	}
	@Override
	public void addDelayType(String delayType){
		
		delayDao.addDelayType(delayType);
	}
	@Override
	public DelayTypeModel getDelayType(String delayType){
		
		DelayTypeModel type = delayDao.getDelayType(delayType);
		return type;
	}
	@Override
	public void deleteDelayType(Integer typeId){
		
		// get associated reason Ids to delete with the type
		List<Integer> reasonIds = delayDao.getTypeAssociations(typeId);
		// delete all of the associations and then the reasons associated
		// with this type.  associations must go first due to ref. constraint
		for(int i = 0; i < reasonIds.size(); i++){
			
			delayDao.deleteDelayAssociation(reasonIds.get(i), typeId);
			delayDao.deleteDelayReasonOfType( reasonIds.get(i) );
		}
		// delete all delays which have this typeId, then delete delay type
		delayDao.deleteDelaysWithType(typeId);
		delayDao.deleteDelayType(typeId);
	}
	@Override
	public void modifyDelayType(DelayTypeModel delayType){
		
		delayDao.modifyDelayType(delayType);
	}
	
	
	// Delay Reason Codes service methods
	@Override
	public List<DelayModel> getAssociations(){
		
		List<DelayModel> assocList = delayDao.getAssociations();
		return assocList;
	}
	@Override
	public void deleteDelayReason(Integer reasonId, Integer typeId){
		
		delayDao.deleteDelayAssociation(reasonId, typeId);
		delayDao.deleteDelaysWithReason(reasonId);
		delayDao.deleteDelayReasonName(reasonId);
	}
	@Override
	public boolean checkAssociation(DelayReasonModel reason){
		
		Integer result = delayDao.checkAssociation(reason);
		if(result == null){
			
			return false;
		}
		else if(result == 1){
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkReason(String reasonName){
		
		Integer result = delayDao.checkReason(reasonName);
		if(result == null){
			
			return false;
		}
		else if(result == 1){
			
			return true;
		}
		return false;
	}
	
	
	@Override
	public void modifyReason(DelayReasonModel newReason, DelayReasonModel oldReason){
		
		// modify the reason in the Delay Reason table first
		delayDao.modifyReasonName(newReason.getDelayReason(), newReason.getReasonId());
		// modify the association
		delayDao.modifyDelayReason(newReason, oldReason);
	}
	@Override
	public DelayReasonModel addDelayReason(Integer typeId, String reasonName){
		
		// add the Reason to the Delay Reason table
		delayDao.addDelayReason(reasonName);
		// get the newly created Reason's Id for Association creation
		Integer reasonId = delayDao.getDelayReasonId(reasonName);
		// Create the association using the typeId passed and the returned reasonId
		delayDao.addDelayAssociation(typeId, reasonId);
		DelayReasonModel reason = new DelayReasonModel();
		reason.setTypeId(typeId);
		reason.setReasonId(reasonId);
		return reason;
	}
}