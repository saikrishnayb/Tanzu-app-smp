package com.penske.apps.suppliermgmt.model;

import java.io.Serializable;


/**
 *****************************************************************************
 * File Name     : VendorLocation 
 * Description   : POJO used to hold user information 
 * Project       : SMC
 * Package       : com.penske.apps.smc.model
 * Author        : 502299699
 * Date			 : May 15, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 *  */
public class VendorLocation implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2021454016074028191L;
	private Integer vendorId;
	private Integer vendorNumber;
	private String corpCode;
	private String companyCode;
	private String vendorName;
	private String notificationException;
	private String annualAgreement;
	private Integer planningAnalyst;
	private Integer supplySpecialist;
	private String manufacturer;
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(Integer vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getNotificationException() {
		return notificationException;
	}
	public void setNotificationException(String notificationException) {
		this.notificationException = notificationException;
	}
	public String getAnnualAgreement() {
		return annualAgreement;
	}
	public void setAnnualAgreement(String annualAgreement) {
		this.annualAgreement = annualAgreement;
	}
	public Integer getPlanningAnalyst() {
		return planningAnalyst;
	}
	public void setPlanningAnalyst(Integer planningAnalyst) {
		this.planningAnalyst = planningAnalyst;
	}
	public Integer getSupplySpecialist() {
		return supplySpecialist;
	}
	public void setSupplySpecialist(Integer supplySpecialist) {
		this.supplySpecialist = supplySpecialist;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	

}
