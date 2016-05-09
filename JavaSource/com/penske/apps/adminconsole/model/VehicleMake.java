package com.penske.apps.adminconsole.model;

/**
 * This class represents the vehicle name/abbreviation combination used in the Dynamic Rules page for users to select
 * a vehicle make to correspond to the rule.
 * 
 * @author 600143568
 */
public class VehicleMake implements Comparable<VehicleMake> {
	private String makeName;		// the vehicle make
	private String abbreviation;	// the vehicle make abbreviation

	// Getters
	public String getMakeName() {
		return makeName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	// Setters
	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		return "VehicleMake [makeName=" + makeName + ", abbreviation="
				+ abbreviation + "]";
	}

	@Override
	public int compareTo(VehicleMake make) {
		return makeName.compareTo(make.getMakeName());
	}
}
