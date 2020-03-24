package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.BodyPlantCapability;

public interface BodyPlantCapabilityService {
	
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();

	public BodyPlantCapability getCapabilityDetails(int capabilityId);

}
