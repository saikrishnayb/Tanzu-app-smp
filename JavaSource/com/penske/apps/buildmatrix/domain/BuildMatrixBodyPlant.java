package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class BuildMatrixBodyPlant {
	
	private int plantId;
	private String plantMfrCode;
	private String plantManufacturer;
	private String plantName;
	private String state;
	private String zip;
	private String country;
	private String city;
	private List<PlantOfflineDate> offlineDates;
	private List<Integer> offlineDateToRemove;
	
	public int getPlantId() {
		return plantId;
	}
	public String getPlantMfrCode() {
		return plantMfrCode;
	}
	public String getPlantManufacturer() {
		return plantManufacturer;
	}
	public String getPlantName() {
		return plantName;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public List<PlantOfflineDate> getOfflineDates() {
		return offlineDates;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public void setPlantMfrCode(String plantMfrCode) {
		this.plantMfrCode = plantMfrCode;
	}
	public void setPlantManufacturer(String plantManufacturer) {
		this.plantManufacturer = plantManufacturer;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setOfflineDates(List<PlantOfflineDate> offlineDates) {
		this.offlineDates = offlineDates;
	}
	public List<Integer> getOfflineDateToRemove() {
		return offlineDateToRemove;
	}
	public void setOfflineDateToRemove(List<Integer> offlineDateToRemove) {
		this.offlineDateToRemove = offlineDateToRemove;
	}
	
	    
}
