package com.penske.apps.buildmatrix.domain;

public class PlantProximity {

	private int proximityId;
	private int plantId;
	private int tier;
	private String district;
	private boolean removeDistrict;
	
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
	
	public void setProximityId(int proximityId) {
		this.proximityId = proximityId;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public boolean isRemoveDistrict() {
		return removeDistrict;
	}

	public void setRemoveDistrict(boolean removeDistrict) {
		this.removeDistrict = removeDistrict;
	}

}
