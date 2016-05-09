package com.penske.apps.adminconsole.model;

public class VendorTemplateSearch {

	private String manufacture;
	private int VendorNumber;
	private int poCategoryId;
	private int subCategoryId;
	private String corpCode;

	// Getters
	public String getManufacture() {
		return manufacture;
	}

	public int getVendorNumber() {
		return VendorNumber;
	}

	public int getPoCategoryId() {
		return poCategoryId;
	}

	public int getSubCategoryId() {
		return subCategoryId;
	}

	public String getCorpCode() {
		return corpCode;
	}

	// Setters
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	public void setVendorNumber(int vendorNumber) {
		VendorNumber = vendorNumber;
	}

	public void setPoCategoryId(int poCategoryId) {
		this.poCategoryId = poCategoryId;
	}

	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	@Override
	public String toString() {
		return "VendorTemplateSearch [manufacture=" + manufacture
				+ ", VendorNumber=" + VendorNumber + ", poCategoryId="
				+ poCategoryId + ", subCategoryId=" + subCategoryId
				+ ", corpCode=" + corpCode + "]";
	}
}
