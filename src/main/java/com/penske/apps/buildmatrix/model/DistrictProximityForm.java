package com.penske.apps.buildmatrix.model;

import java.util.List;

import com.penske.apps.buildmatrix.domain.PlantProximity;

public class DistrictProximityForm {
	private Integer plantId;
	private List<PlantProximity> plantProximities;
	public Integer getPlantId() {
		return plantId;
	}
	public List<PlantProximity> getPlantProximities() {
		return plantProximities;
	}
	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}
	public void setPlantProximities(List<PlantProximity> plantProximities) {
		this.plantProximities = plantProximities;
	}
	
	
}
