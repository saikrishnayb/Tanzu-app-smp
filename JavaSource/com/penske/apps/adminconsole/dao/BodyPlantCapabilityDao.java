package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.BodyPlantCapability;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

@DBSmc
public interface BodyPlantCapabilityDao {

	@NonVendorQuery
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();

	@NonVendorQuery
	public BodyPlantCapability getCapabilityDetails(int capabilityId);

}
