package com.penske.apps.buildmatrix.domain;

public class DistrictProximity {

	private String district;
	private int tier;

	public DistrictProximity(String district, int tier) {
		super();
		this.district = district;
		this.tier = tier;
	}

	public String getDistrict() {
		return district;
	}
	
	public int getTier() {
		return tier;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public void setTier(int tier) {
		this.tier = tier;
	}

}
