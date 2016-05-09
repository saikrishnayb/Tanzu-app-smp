package com.penske.apps.adminconsole.model;

import java.util.List;

public class VendorTree {
	private String vendorName;
	private List<Vendor> vendorNumberList;	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public List<Vendor> getVendorNumberList() {
		return vendorNumberList;
	}
	public void setVendorNumberList(List<Vendor> vendorNumberList) {
		this.vendorNumberList = vendorNumberList;
	}


	
}
