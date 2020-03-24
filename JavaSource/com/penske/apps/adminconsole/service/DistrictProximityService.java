package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.DistrictProximity;

public interface DistrictProximityService {
	
	public List<DistrictProximity> getDistrictProximity();
	
	public List<DistrictProximity> getDistrictProximityMockService();
	
	public void insertProximityValues(DistrictProximity districtProximity);

}
