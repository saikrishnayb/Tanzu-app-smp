package com.penske.apps.buildmatrix.model;

public class ImportSlotsHeader {

	private String manufacturer;
	private String city;
	private String state;
	
	public ImportSlotsHeader(String manufacturer, String city, String state) {
		this.manufacturer = manufacturer;
		this.city = city;
		this.state = state;
	}

	public String getCity() {
		return city;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getState() {
		return state;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
