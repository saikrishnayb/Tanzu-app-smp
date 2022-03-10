package com.penske.apps.adminconsole.model;

import java.time.LocalDate;

public class VendorPoInformation {
	private int vendorId;
	private LocalDate lastPoDate;
	private int posIssuedInLast3Years;
	
	protected VendorPoInformation() {}
	
	public VendorPoInformation(int vendorId) {
		this.vendorId = vendorId;
		this.lastPoDate = null;
		this.posIssuedInLast3Years = 0;
	}

	public int getVendorId() {
		return vendorId;
	}
	
	public LocalDate getLastPoDate() {
		return lastPoDate;
	}
	
	public int getPosIssuedInLast3Years() {
		return posIssuedInLast3Years;
	}
}
