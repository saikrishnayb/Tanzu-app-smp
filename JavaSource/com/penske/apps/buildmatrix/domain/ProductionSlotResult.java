package com.penske.apps.buildmatrix.domain;

import java.util.Date;

public class ProductionSlotResult {
	private long orderId;
	private String unitNumber;
	private String programName;
	private String region;
	private String area;
	private String districtNumber;
	private String districtName;
	private Date requestedDeliveryDate;
	private String productionSlot;
	private String productionDate;

	// getters
	public long getOrderId() {
		return orderId;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getProgramName() {
		return programName;
	}

	public String getRegion() {
		return region;
	}

	public String getArea() {
		return area;
	}

	public String getDistrictNumber() {
		return districtNumber;
	}

	public String getDistrictName() {
		return districtName;
	}

	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public String getProductionSlot() {
		return productionSlot;
	}

	public String getProductionDate() {
		return productionDate;
	}

	// setters
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	public void setProductionSlot(String productionSlot) {
		this.productionSlot = productionSlot;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

}
