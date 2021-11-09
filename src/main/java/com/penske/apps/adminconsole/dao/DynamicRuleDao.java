package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.VehicleMake;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface is used for queries to the database for the Dynamic Rules page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
@DBSmc
public interface DynamicRuleDao {

    @NonVendorQuery
    public List<DynamicRule> getAllDynamicRulesByStatus(String status);

    @NonVendorQuery
    public Integer getDynamicRuleByPriority(int priority);

    @NonVendorQuery
    public DynamicRule getDynamicRuleById(int dynamicRuleId);

    // TODO This method uses an old table and may be changed to use a different table.
    @NonVendorQuery
    public List<String> getAllCorpCodes();

    // TODO This method uses an old table and may be changed to use a different table.
    @NonVendorQuery
    public List<VehicleMake> getAllVehicleMakes();

    // TODO This method uses an old table and may be changed to use a different table.
    @NonVendorQuery
    public List<String> getVehicleModelsByMake(String make);

    @NonVendorQuery
    public void addDynamicRule(DynamicRule rule);

    @NonVendorQuery
    public void modifyDynamicRule(DynamicRule rule);

    @NonVendorQuery
    public List<String> getAvailableStatus();

    @NonVendorQuery
    public List<DynamicRule> getDynamicRuleByNonPriority(DynamicRule rule);

    @NonVendorQuery
    public Integer reOrderPriority(int newPriority);

    @NonVendorQuery
    public int getMaxPriority();

    @NonVendorQuery
    public void deleteDynamicRule(@Param("dynamicRuleId") int dynamicRuleId);
}
