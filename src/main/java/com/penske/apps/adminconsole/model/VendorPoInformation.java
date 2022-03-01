package com.penske.apps.adminconsole.model;

import java.time.LocalDate;

public class VendorPoInformation {
	private int vendorNumber;
	private LocalDate lastPoDate;
	private int posIssuedInLast3Years;
	
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
