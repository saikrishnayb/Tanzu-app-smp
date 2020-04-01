package com.penske.apps.buildmatrix.model;

public class ManufacturerDetails {
	/**
	 * The 3-character code for this manufacturer.
	 */
	private String mfrCode;
	/**
	 * The full human-readable name of this manufacturer.
	 */
	private String mfrName;
	
	protected ManufacturerDetails() {}
	
	//***** DEFUALT ACCESSORS *****//
	public String getMfrCode() {
		return mfrCode;
	}
	
	public String getMfrName() {
		return mfrName;
	}

}
