package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.VehicleMake;

/**
 * This interface is used for queries to the database for the Dynamic Rules page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
public interface DynamicRuleService {
	public List<DynamicRule> getAllDynamicRulesByStatus(String status);
	
	public List<String> getAllCorpCodes();

	public List<VehicleMake> getAllVehicleMakes();
	
	public List<String> getVehicleModelsByMake(String make);
	
	public void addDynamicRule(DynamicRule rule);
	
	public void modifyDynamicRule(DynamicRule rule);
	
	public void modifyDynamicRuleStatus(int dynamicRuleId, int priority, String modifiedBy);

	public List<String> getAvailableStatus();
	
	public void deleteDynamicRule(int dynamicRuleId);
	
	public int getMaxPriority();
}
