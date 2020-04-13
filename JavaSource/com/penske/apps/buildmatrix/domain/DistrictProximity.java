package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class DistrictProximity {

	private int proximityId;
	private List<String> districtValues;
	private int tier;
	private String area;
	private int plantId;
	private String district;
	
	public DistrictProximity(){		
	}
	
	public DistrictProximity(List<String> districtValues, int tier, String area) {
		super();
		this.districtValues = districtValues;
		this.tier = tier;
		this.area = area;
	}

	public DistrictProximity(List<String> districtValues, int tier) {
		super();
		this.districtValues = districtValues;
		this.tier = tier;
	}

	public int getProximityId() {
		return proximityId;
	}
	
	public List<String> getDistrictValues() {
		return districtValues;
	}
	
	public int getTier() {
		return tier;
	}
	
	public String getArea() {
		return area;
	}
	
	public int getPlantId() {
		return plantId;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setProximityId(int proximityId) {
		this.proximityId = proximityId;
	}
	
	public void setDistrict(List<String> districtValues) {
		this.districtValues = districtValues;
	}
	
	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
}
