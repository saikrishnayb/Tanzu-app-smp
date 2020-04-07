package com.penske.apps.buildmatrix.domain;

import com.penske.apps.smccore.base.util.SortableUnit;

public class AvailableChassis implements SortableUnit{

	String unitNumber;
	String corp;
	String regionNumber;
	String districtNumber;
	
	protected AvailableChassis() {}

	@Override
	public String getUnitNumber() {
		return unitNumber;
	}

	public String getCorp() {
		return corp;
	}

	public String getRegionNumber() {
		return regionNumber;
	}
	
	public String getDistrictNumber() {
		return districtNumber;
	}
}
