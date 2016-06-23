package com.penske.apps.adminconsole.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.DynamicRuleDao;
import com.penske.apps.adminconsole.exceptions.DynamicRulePriorityException;
import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.VehicleMake;
import com.penske.apps.adminconsole.util.ApplicationConstants;

/**
 * This class is used for queries to the database for the Dynamic Rules page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
@Service
public class DefaultDynamicRuleService implements DynamicRuleService {
	@Autowired
	private DynamicRuleDao dynamicRuleDao;
	
	@Override
	public List<DynamicRule> getAllDynamicRulesByStatus(String status) {
		return dynamicRuleDao.getAllDynamicRulesByStatus(status);
	}

	@Override
	public void addDynamicRule(DynamicRule rule) {
		Integer searchRule = null;
		boolean reOrderRequired=false;
		if (validateRule(rule)) {
			searchRule = dynamicRuleDao.getDynamicRuleByPriority(rule.getPriority());
			
			// Check to see if a rule with the new rule's priority already exists.
			if (searchRule != null) {
				//throw new DynamicRulePriorityException("Priority Already exists for a different Dynamic Rule.");
				reOrderRequired=true;
			}
			List<DynamicRule> searchResult = dynamicRuleDao.getDynamicRuleByNonPriority(rule);
			// Check to see if a CORP/MANUFACTURER/MODEL/MODEL_YEAR already exists.
			if (searchResult != null && !searchResult.isEmpty()) {
				boolean isActive=true;
				DynamicRule searchDynamicRule=searchResult.get(0);
				if(searchDynamicRule!=null && searchDynamicRule.getStatus() !=null 
						&& searchDynamicRule.getStatus().equalsIgnoreCase("I")){
					isActive=false;
				}
				StringBuffer strBuilder=new StringBuffer(ApplicationConstants.DYNAMIC_RULE_ERROR_1);
				if(isActive){
					strBuilder.append("active status");
					throw new DynamicRulePriorityException(strBuilder.toString());
				}else{
					strBuilder.append("inactive status");
					throw new DynamicRulePriorityException(strBuilder.toString());
				}
			}
			if(reOrderRequired){
				dynamicRuleDao.reOrderPriority(rule.getPriority());
			}
			dynamicRuleDao.addDynamicRule(rule);
		}
	}
	
	@Override
	public void modifyDynamicRule(DynamicRule rule) {
		DynamicRule origRule = null;
		boolean reOrderRequired=false;
		Integer searchRule=null;
		if (validateRule(rule)) {
			origRule = dynamicRuleDao.getDynamicRuleById(rule.getDynamicRuleId());
			
			if (origRule == null) {
				return;
			}
			
		//	if ("A".equalsIgnoreCase(rule.getStatus())) {
			if(origRule.getPriority() !=  rule.getPriority()){
					searchRule = dynamicRuleDao.getDynamicRuleByPriority(rule.getPriority());
					// Check to see if a rule with the new rule's priority already exists.
					if (searchRule != null) {
						//throw new DynamicRulePriorityException("Priority Already exists for a different Dynamic Rule.");
						reOrderRequired=true;
					}
			}
				List<DynamicRule> searchResult = dynamicRuleDao.getDynamicRuleByNonPriority(rule);
				// Check to see if a CORP/MANUFACTURER/MODEL/MODEL_YEAR already exists.
				if (searchResult != null && ((searchResult.size()==1 && searchResult.get(0) !=null && 
						searchResult.get(0).getDynamicRuleId() != origRule.getDynamicRuleId())
						|| searchResult.size()>1)) {
					boolean isActive=true;
					DynamicRule searchDynamicRule=searchResult.get(0);
					if(searchDynamicRule!=null && searchDynamicRule.getStatus() !=null 
							&& searchDynamicRule.getStatus().equalsIgnoreCase("I")){
						isActive=false;
					}
					StringBuffer strBuilder=new StringBuffer(ApplicationConstants.DYNAMIC_RULE_ERROR_1);
					if(isActive){
						strBuilder.append("active status");
						throw new DynamicRulePriorityException(strBuilder.toString());
					}else{
						strBuilder.append("inactive status");
						throw new DynamicRulePriorityException(strBuilder.toString());
					}
				}else{
					
				}
				if(reOrderRequired){
					dynamicRuleDao.reOrderPriority(rule.getPriority());
				}
	//		}
			//if ("I".equalsIgnoreCase(rule.getStatus())){
				//TODO - Check with dav to set -1 / 0 , We can't set NULL
			//	rule.setPriority(0);
			//}
			dynamicRuleDao.modifyDynamicRule(rule);
		}
	}

	@Override
	public void modifyDynamicRuleStatus(int dynamicRuleId, int priority,String modifiedBy) {
		// The rule ID cannot be negative or 0.
		if (dynamicRuleId > 0) {
			//TODO - Check with dav to set -1 / 0 , We can't set NULL
			dynamicRuleDao.modifyDynamicRuleStatus(dynamicRuleId,modifiedBy);
			
			//dynamicRuleDao.modifyDynamicRulesByPriority(priority, -1);
		}
	}

	@Override
	public void deleteDynamicRule(int dynamicRuleId){
		// The rule ID cannot be negative or 0.
		if (dynamicRuleId > 0) {
			dynamicRuleDao.deleteDynamicRule(dynamicRuleId);
		}
	}
	
	@Override
	public List<String> getAllCorpCodes() {
		List<String> corpCodes = dynamicRuleDao.getAllCorpCodes();
		
		Collections.sort(corpCodes);
		
		return(corpCodes);
	}
	
	@Override
	public List<VehicleMake> getAllVehicleMakes() {
		List<VehicleMake> makes = dynamicRuleDao.getAllVehicleMakes();
		
		Collections.sort(makes);
		
		return makes;
	}
	
	@Override
	public List<String> getVehicleModelsByMake(String make) {
		List<String> models = dynamicRuleDao.getVehicleModelsByMake(make);
		
		Collections.sort(models);
		
		return models;
	}
	
	public boolean validateRule(DynamicRule rule) {
		// Corp Code, Manufacturer, and Model cannot be null or blank.
		if (StringUtils.isBlank(rule.getCorpCode()) && StringUtils.isBlank(rule.getManufacturer()) && StringUtils.isBlank(rule.getModel())) {
			if (rule.getModelYear() < 0)
				return false;
		}
		
		
		
		return true;
	}

	@Override
	public List<String> getAvailableStatus() {
		// TODO Auto-generated method stub
		return dynamicRuleDao.getAvailableStatus();
	}

	@Override
	public int getMaxPriority() {
		return dynamicRuleDao.getMaxPriority();
	}
}
