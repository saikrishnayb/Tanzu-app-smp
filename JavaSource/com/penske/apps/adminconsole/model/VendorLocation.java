package com.penske.apps.adminconsole.model;

import java.util.List;

public class VendorLocation {

	private int vendorId;
	private int templateId;
	private String manufacturer;
	private int vendorNumber;
	private String corpCode;
	private List<TemplatePoCategorySubCategory> templatePoCategorySubCategory;
	private String state;
	private String city;
	private boolean checkable;

	// Getters
	public int getVendorId() {
		return vendorId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public int getVendorNumber() {
		return vendorNumber;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public List<TemplatePoCategorySubCategory> getTemplatePoCategorySubCategory() {
		return templatePoCategorySubCategory;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public boolean isCheckable() {
		return checkable;
	}

	// Setters
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setVendorNumber(int vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setTemplatePoCategorySubCategory(
			List<TemplatePoCategorySubCategory> templatePoCategorySubCategory) {
		this.templatePoCategorySubCategory = templatePoCategorySubCategory;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	@Override
	public String toString() {
		return "VendorLocation [vendorId=" + vendorId + ", templateId="
				+ templateId + ", manufacturer=" + manufacturer + ", vendorNumber="
				+ vendorNumber + ", corpCode=" + corpCode
				+ ", templatePoCategorySubCategory="
				+ templatePoCategorySubCategory + ", state=" + state + ", city="
				+ city + "]";
	}
	
}
