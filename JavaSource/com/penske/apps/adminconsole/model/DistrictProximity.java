package com.penske.apps.adminconsole.model;

public class DistrictProximity {

	private String district;
	private int tier;

	public DistrictProximity(String district, int tier) {
		super();
		this.district = district;
		this.tier = tier;
	}

	// getters
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	
	/**
	 * @return the tier
	 */
	public int getTier() {
		return tier;
	}
	// setters
	/**
	 * @param district
	 * district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	// setters
	/**
	 * @param tier
	 * tier to set
	 */
	public void setTier(int tier) {
		this.tier = tier;
	}

}
