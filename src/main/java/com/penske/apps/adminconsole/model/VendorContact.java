package com.penske.apps.adminconsole.model;

public class VendorContact {
	private int vendorId;
	private String contactType;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private int responsibility;
	private String createdBy;
	private String modifiedBy;

	// Getters
	public int getVendorId() {
		return vendorId;
	}
	
	public String getContactType() {
		return contactType;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public int getResponsibility() {
		return responsibility;
	}

	// Setters
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setResponsibility(int responsibility) {
		this.responsibility = responsibility;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
