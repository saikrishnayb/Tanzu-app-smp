package com.penske.apps.buildmatrix.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;

public class BuildMatrixBodyPlant {
	
	private int plantId;
	private String plantMfrCode;
	private String plantManufacturer;
	private String plantName;
	private String state;
	private String zip;
	private String country;
	private String city;
	private Date offlineStartDate;
	private Date offlineEndDate;
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
	public Date getOfflineStartDate() {
		return offlineStartDate;
	}
	public Date getOfflineEndDate() {
		return offlineEndDate;
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
	public void setOfflineStartDate(Date offlineStartDate) {
		this.offlineStartDate = offlineStartDate;
	}
	public void setOfflineEndDate(Date offlineEndDate) {
		this.offlineEndDate = offlineEndDate;
	}
	public String getFormattedOfflineStartDate() {
	        return StringUtils.defaultString(DateUtil.formatDateUS(offlineStartDate));
	}
	public String getFormattedOfflineEndDate() {
        return StringUtils.defaultString(DateUtil.formatDateUS(offlineEndDate));
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	    
}
