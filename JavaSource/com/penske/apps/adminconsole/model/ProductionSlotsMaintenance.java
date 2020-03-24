package com.penske.apps.adminconsole.model;

import java.util.Date;

public class ProductionSlotsMaintenance {
private int slotId;
private String plantName;
private String city;
private String state;
private String region;
private String area;
private Date offlineDates;


public ProductionSlotsMaintenance(int slotId, String plantName, String city, String state, String region, String area,
		Date offlineDates) {
	super();
	this.slotId = slotId;
	this.plantName = plantName;
	this.city = city;
	this.state = state;
	this.region = region;
	this.area = area;
	this.offlineDates = offlineDates;
}
//getters
public int getSlotId() {
	return slotId;
}
public String getPlantName() {
	return plantName;
}
public String getCity() {
	return city;
}
public String getState() {
	return state;
}
public String getRegion() {
	return region;
}
public String getArea() {
	return area;
}
public Date getOfflineDates() {
	return offlineDates;
}
//setters
public void setSlotId(int slotId) {
	this.slotId = slotId;
}
public void setPlantName(String plantName) {
	this.plantName = plantName;
}
public void setCity(String city) {
	this.city = city;
}
public void setState(String state) {
	this.state = state;
}
public void setRegion(String region) {
	this.region = region;
}
public void setArea(String area) {
	this.area = area;
}
public void setOfflineDates(Date offlineDates) {
	this.offlineDates = offlineDates;
}


}
