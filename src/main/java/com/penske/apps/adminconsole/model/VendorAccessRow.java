package com.penske.apps.adminconsole.model;

public class VendorAccessRow {
	private Vendor vendor;
	private EditableUser vendorUser;
	
	public VendorAccessRow(Vendor vendor, EditableUser vendorUser) {
		this.vendor = vendor;
		this.vendorUser = vendorUser;
	}
	
	public Vendor getVendor() {
		return vendor;
	}
	
	public EditableUser getVendorUser() {
		return vendorUser;
	}
}
