package com.penske.apps.adminconsole.model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 600144005
 * This class represents all templates created
 */
public class VendorTemplate {

	private int templateId;
	private String manufacture;
	private int vendorNumber;
	private String city;
	private String state;
	private String corpCode;
	private List<TemplatePoCategorySubCategory> templatePoCategorySubCategory;

	// Getters
	public int getTemplateId() {
		return templateId;
	}

	public String getManufacture() {
		return manufacture;
	}

	public int getVendorNumber() {
		return vendorNumber;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCorpCode() {
		return corpCode;
	}
	
	public List<TemplatePoCategorySubCategory> getTemplatePoCategorySubCategory() {
		return templatePoCategorySubCategory;
	}

	// Setters
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public void setVendorNumber(int vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setTemplatePoCategorySubCategory(List<TemplatePoCategorySubCategory> templatePoCategorySubCategory) {
		this.templatePoCategorySubCategory = templatePoCategorySubCategory;
	}

	public void setTemplatePoCategorySubCategory(TemplatePoCategorySubCategory templatePoCategorySubCategory) {
		boolean subCategoryListIsNull = (templatePoCategorySubCategory == null);

		if (subCategoryListIsNull) {
			this.templatePoCategorySubCategory = new ArrayList<TemplatePoCategorySubCategory>();
		}

		this.templatePoCategorySubCategory.add(templatePoCategorySubCategory);
	}

	@Override
	public String toString() {
		return "VendorTemplate [templateId=" + templateId + ", manufacture="
				+ manufacture + ", vendorNumber=" + vendorNumber + ", city="
				+ city + ", state=" + state + ", corpCode=" + corpCode
				+ ", templatePoCategorySubCategory="
				+ templatePoCategorySubCategory + "]";
	}
}
