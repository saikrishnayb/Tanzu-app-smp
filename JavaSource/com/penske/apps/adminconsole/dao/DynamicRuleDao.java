package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.VehicleMake;

/**
 * This interface is used for queries to the database for the Dynamic Rules page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface DynamicRuleDao {
	@NonVendorQuery //TODO: Review Query
	public List<DynamicRule> getAllDynamicRulesByStatus(String status);
	
	@NonVendorQuery //TODO: Review Query
	public Integer getDynamicRuleByPriority(int priority);
	
	@NonVendorQuery //TODO: Review Query
	public DynamicRule getDynamicRuleById(int dynamicRuleId);
	
	// TODO This method uses an old table and may be changed to use a different table.
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllCorpCodes();
	
	// TODO This method uses an old table and may be changed to use a different table.
	@NonVendorQuery //TODO: Review Query
	public List<VehicleMake> getAllVehicleMakes();
	
	// TODO This method uses an old table and may be changed to use a different table.
	@NonVendorQuery //TODO: Review Query
	public List<String> getVehicleModelsByMake(String make);
	
	@NonVendorQuery //TODO: Review Query
	public void addDynamicRule(DynamicRule rule);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyDynamicRule(DynamicRule rule);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyDynamicRuleStatus(@Param("dynamicRuleId") int dynamicRuleId,@Param("modifiedBy") String modifiedBy);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyDynamicRulesByPriority(@Param("priority") int priority, @Param("increment") int increment);

	@NonVendorQuery //TODO: Review Query
	public List<String> getAvailableStatus();
	
	@NonVendorQuery //TODO: Review Query
	public List<DynamicRule> getDynamicRuleByNonPriority(DynamicRule rule);
	
	@NonVendorQuery //TODO: Review Query
	public Integer reOrderPriority(int newPriority);
	
	@NonVendorQuery //TODO: Review Query
	public int getMaxPriority();
	
	@NonVendorQuery //TODO: Review Query
	public void deleteDynamicRule(@Param("dynamicRuleId") int dynamicRuleId);
}
