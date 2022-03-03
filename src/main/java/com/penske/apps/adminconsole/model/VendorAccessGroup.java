package com.penske.apps.adminconsole.model;

import java.util.List;

public class VendorAccessGroup {
	private String vendorName;
	private List<VendorAccessRow> vendorAccessRows;
	
	public VendorAccessGroup(String vendorName, List<VendorAccessRow> vendorAccessRows) {
		this.vendorName = vendorName;
		this.vendorAccessRows = vendorAccessRows;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	
	public List<VendorAccessRow> getVendorAccessRows() {
		return vendorAccessRows;
	}
}
