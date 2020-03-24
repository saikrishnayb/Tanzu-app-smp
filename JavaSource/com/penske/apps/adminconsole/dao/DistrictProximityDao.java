package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.DistrictProximity;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

@DBSmc
public interface DistrictProximityDao {
	@NonVendorQuery
	public List<DistrictProximity> getDistrictProximity();
	
	@NonVendorQuery
    public void insertProximityValues(DistrictProximity districtProximity);
}
