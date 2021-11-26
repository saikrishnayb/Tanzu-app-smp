package com.penske.apps.adminconsole.model;

import java.util.Date;

/**
 * This class represents the rules created by users for the Get Next Deal process.
 * 
 * @author Seth.Bauman 600143568
 */
public class DynamicRule {
	private int dynamicRuleId;		// the dynamic rule ID
	private int priority;			// the rule's priority
	private String corpCode;		// the rule's Corp Code
	private String manufacturer;	// the rule's manufacturer
	private String model;			// the rule's model
	private int modelYear;			// the rule's model year
	private String status;				// the rule's status
	private String createdBy;
	private Date createdDate;
	private String  modifiedBy;
	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	// Getters
	public int getDynamicRuleId() {
		return dynamicRuleId;
	}

	public int getPriority() {
		return priority;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public int getModelYear() {
		return modelYear;
	}

	public String getStatus() {
		return status;
	}

	// Setters
	public void setDynamicRuleId(int dynamicRuleId) {
		this.dynamicRuleId = dynamicRuleId;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "DynamicRule [dynamicRuleId=" + dynamicRuleId + ", priority="
				+ priority + ", corpCode=" + corpCode + ", manufacturer="
				+ manufacturer + ", model=" + model + ", modelYear="
				+ modelYear + ", status=" + status + "]";
	}
}
