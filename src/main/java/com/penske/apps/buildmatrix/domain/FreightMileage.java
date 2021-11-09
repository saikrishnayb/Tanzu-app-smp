package com.penske.apps.buildmatrix.domain;

import java.util.List;
import java.util.Map;

public class FreightMileage {

	private int plantId;
	private String area;
	private String areaDesc;
	private List<Map<String,String>> districts;
	
	public int getPlantId() {
		return plantId;
	}
	public String getArea() {
		return area;
	}
	public List<Map<String,String>> getDistricts() {
		return districts;
	}
	
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setDistricts(List<Map<String,String>> districts) {
		this.districts = districts;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	
}
