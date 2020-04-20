package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class FreightMileage {

	private int plantId;
	private String area;
	private List<String> districts;
	
	public int getPlantId() {
		return plantId;
	}
	public String getArea() {
		return area;
	}
	public List<String> getDistricts() {
		return districts;
	}
	
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setDistricts(List<String> districts) {
		this.districts = districts;
	}
	
}
