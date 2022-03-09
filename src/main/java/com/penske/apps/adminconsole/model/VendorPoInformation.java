package com.penske.apps.adminconsole.model;

import java.time.LocalDate;

import com.penske.apps.smccore.base.util.DateUtil;

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
	
	@Override
	public String toString() {
		return "VendorPoInformation [vendorNumber=" + vendorNumber + ", lastPoDate=" + lastPoDate
				+ ", posIssuedInLast3Years=" + posIssuedInLast3Years + "]";
	}

	public String getFormattedLastPoDate() {
		return posIssuedInLast3Years == 0 ? "(Never)" : DateUtil.formatDateUS(lastPoDate);
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
