package com.penske.apps.buildmatrix.domain;

import java.util.Date;
import java.util.List;

public class ProductionSlotResult {
	private int runId;
	private long orderId;
	private String unitNumber;
	private String vehicleType;
	private String programName;
	private String region;
	private String area;
	private String districtNumber;
	private String districtName;
	private Date requestedDeliveryDate;
	private String productionSlot;
	private String productionDate;
	private String chassisMake;
	private String chassisModel;
	private int chassisModelYear;
	private String chassisColor;
	private String bodyMake;
	private String chassisLength;
	private String rearDoorMake;
	private String reeferMake;
	private String liftgateInstalled;
	private String liftgateMake;
	private String liftgateType;
	private String reservationStatus;
	private List<String> productionSlotList;

	// getters
	public long getOrderId() {
		return orderId;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getVehicleType() {
		return vehicleType;
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

	public int getRunId() {
		return runId;
	}
	
	public String getReservationStatus() {
		return reservationStatus;
	}
	
	public List<String> getProductionSlotList() {
		return productionSlotList;
	}
	
	public String getChassisMake() {
		return chassisMake;
	}

	public String getChassisModel() {
		return chassisModel;
	}

	public int getChassisModelYear() {
		return chassisModelYear;
	}

	public String getChassisColor() {
		return chassisColor;
	}

	public String getBodyMake() {
		return bodyMake;
	}

	public String getChassisLength() {
		return chassisLength;
	}

	public String getRearDoorMake() {
		return rearDoorMake;
	}

	public String getReeferMake() {
		return reeferMake;
	}

	public String getLiftgateInstalled() {
		return liftgateInstalled;
	}

	public String getLiftgateMake() {
		return liftgateMake;
	}

	public String getLiftgateType() {
		return liftgateType;
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

	public void setRunId(int runId) {
		this.runId = runId;
	}
	
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public void setProductionSlotList(List<String> productionSlotList) {
		this.productionSlotList = productionSlotList;
	}

}
