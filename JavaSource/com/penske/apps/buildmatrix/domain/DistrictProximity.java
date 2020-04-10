package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class DistrictProximity {

	private List<String> district;
	private int tier;
	private String area;
	private int plantId;
	
	public DistrictProximity(){		
	}
	
	public DistrictProximity(List<String> district, int tier, String area) {
		super();
		this.district = district;
		this.tier = tier;
		this.area = area;
	}

	public DistrictProximity(List<String> district, int tier) {
		super();
		this.district = district;
		this.tier = tier;
	}

	public List<String> getDistrict() {
		return district;
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
	
	public void setDistrict(List<String> district) {
		this.district = district;
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
}
