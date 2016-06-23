package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.VehicleMake;

/**
 * This interface is used for queries to the database for the Dynamic Rules page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface DynamicRuleDao {
	public List<DynamicRule> getAllDynamicRulesByStatus(String status);
	
	public Integer getDynamicRuleByPriority(int priority);
	
	public DynamicRule getDynamicRuleById(int dynamicRuleId);
	
	// TODO This method uses an old table and may be changed to use a different table.
	public List<String> getAllCorpCodes();
	
	// TODO This method uses an old table and may be changed to use a different table.
	public List<VehicleMake> getAllVehicleMakes();
	
	// TODO This method uses an old table and may be changed to use a different table.
	public List<String> getVehicleModelsByMake(String make);
	
	public void addDynamicRule(DynamicRule rule);
	
	public void modifyDynamicRule(DynamicRule rule);
	
	public void modifyDynamicRuleStatus(@Param("dynamicRuleId") int dynamicRuleId,@Param("modifiedBy") String modifiedBy);
	
	public void modifyDynamicRulesByPriority(@Param("priority") int priority, @Param("increment") int increment);

	public List<String> getAvailableStatus();
	
	public List<DynamicRule> getDynamicRuleByNonPriority(DynamicRule rule);
	
	public Integer reOrderPriority(int newPriority);
	
	public int getMaxPriority();
	
	public void deleteDynamicRule(@Param("dynamicRuleId") int dynamicRuleId);
}
