package com.penske.apps.adminconsole.model;

import java.util.List;
/**
 * 
 * @author 600144005
 * This class reperesents corpcodes of a manufacturer
 */
public class CorpCode {

	private String corpCode;
	private String manufacture;
	private List<VendorLocation> vendorLocation;

	// Getters
	public String getCorpCode() {
		return corpCode;
	}

	public List<VendorLocation> getVendorLocation() {
		return vendorLocation;
	}

	public String getManufacture() {
		return manufacture;
	}

	// Setters
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setVendorLocation(List<VendorLocation> vendorLocation) {
		this.vendorLocation = vendorLocation;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	@Override
	public String toString() {
		return "CorpCode [corpCode=" + corpCode + ", manufacture="
				+ manufacture + ", vendorLocation=" + vendorLocation + "]";
	}

}
