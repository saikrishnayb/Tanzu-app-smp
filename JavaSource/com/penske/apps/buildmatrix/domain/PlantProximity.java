package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class PlantProximity {

	private int proximityId;
	private int tier;
	private int plantId;
	private List<String> proximityDistrictValues;
	
	public PlantProximity(){		
	}
	
	public int getProximityId() {
		return proximityId;
	}

	public int getTier() {
		return tier;
	}
	
	public int getPlantId() {
		return plantId;
	}
	
	public List<String> getProximityDistrictValues() {
		return proximityDistrictValues;
	}

	public void setProximityId(int proximityId) {
		this.proximityId = proximityId;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public void setProximityDistrictValues(List<String> proximityDistrictValues) {
		this.proximityDistrictValues = proximityDistrictValues;
	}
}
