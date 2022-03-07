package com.penske.apps.adminconsole.model;

import java.time.LocalDate;

public class VendorPoInformation {
	private int vendorNumber;
	private LocalDate lastPoDate;
	private int posIssuedInLast3Years;
	
	protected VendorPoInformation() {}
	
	public VendorPoInformation(int vendorNumber) {
		this.vendorNumber = vendorNumber;
		this.lastPoDate = null;
		this.posIssuedInLast3Years = 0;
	}
	
	public int getVendorNumber() {
		return vendorNumber;
	}
	public LocalDate getLastPoDate() {
		return lastPoDate;
	}
	public int getPosIssuedInLast3Years() {
		return posIssuedInLast3Years;
	}
}
