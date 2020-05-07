package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class BodyPlantCapability {
	
	private int attributeId;
	private String attributeKey;
	private String attributeName;
	private String attributeValue;
	private List<String> values;
	private int capabilityId;
	private String liftgateInstalled;
	private String rearDoorInstalled;
	private String reeferInstalled;
	private String vehicleType;
	private String chassisMake;
	private String gvw;
	private String bodyMake;
	private String liftgateMake;
	private String rearDoorMake;
	private String liftgateType;
	private String fuelType;
	private String chassisColor;
	private String chassisLength;
	private String wheelbase;
	private String wheelMaterial;
	private String corp;
	private String chassisModel;
	private String chassisModelYear;
	private String transmissionMake;
	private String brakeType;
	private String suspensionType;
	private String reeferMake;
	
	protected BodyPlantCapability() {}
	
	public BodyPlantCapability(int attributeId, String attributeKey, String attributeName) {
		super();
		this.attributeId = attributeId;
		this.attributeKey = attributeKey;
		this.attributeName = attributeName;
	}

	//***** DEFAULT ACCESSORS *****//
	public int getAttributeId() {
		return attributeId;
	}

	public String getAttributeKey() {
		return attributeKey;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public List<String> getValues() {
		return values;
	}
	
	public int getCapabilityId() {
		return capabilityId;
	}
	
	public String getLiftgateInstalled() {
		return liftgateInstalled;
	}

	public String getRearDoorInstalled() {
		return rearDoorInstalled;
	}

	public String getReeferInstalled() {
		return reeferInstalled;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public String getChassisMake() {
		return chassisMake;
	}

	public String getGvw() {
		return gvw;
	}

	public String getBodyMake() {
		return bodyMake;
	}

	public String getLiftgateMake() {
		return liftgateMake;
	}

	public String getRearDoorMake() {
		return rearDoorMake;
	}

	public String getLiftgateType() {
		return liftgateType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public String getChassisColor() {
		return chassisColor;
	}

	public String getChassisLength() {
		return chassisLength;
	}

	public String getWheelbase() {
		return wheelbase;
	}

	public String getWheelMaterial() {
		return wheelMaterial;
	}

	public String getCorp() {
		return corp;
	}

	public String getChassisModel() {
		return chassisModel;
	}

	public String getChassisModelYear() {
		return chassisModelYear;
	}

	public String getTransmissionMake() {
		return transmissionMake;
	}

	public String getBrakeType() {
		return brakeType;
	}

	public String getSuspensionType() {
		return suspensionType;
	}

	public String getReeferMake() {
		return reeferMake;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void setLiftgateInstalled(String liftgateInstalled) {
		this.liftgateInstalled = liftgateInstalled;
	}

	public void setRearDoorInstalled(String rearDoorInstalled) {
		this.rearDoorInstalled = rearDoorInstalled;
	}

	public void setReeferInstalled(String reeferInstalled) {
		this.reeferInstalled = reeferInstalled;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public void setChassisMake(String chassisMake) {
		this.chassisMake = chassisMake;
	}

	public void setGvw(String gvw) {
		this.gvw = gvw;
	}

	public void setBodyMake(String bodyMake) {
		this.bodyMake = bodyMake;
	}

	public void setLiftgateMake(String liftgateMake) {
		this.liftgateMake = liftgateMake;
	}

	public void setRearDoorMake(String rearDoorMake) {
		this.rearDoorMake = rearDoorMake;
	}

	public void setLiftgateType(String liftgateType) {
		this.liftgateType = liftgateType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public void setChassisColor(String chassisColor) {
		this.chassisColor = chassisColor;
	}

	public void setChassisLength(String chassisLength) {
		this.chassisLength = chassisLength;
	}

	public void setWheelbase(String wheelbase) {
		this.wheelbase = wheelbase;
	}

	public void setWheelMaterial(String wheelMaterial) {
		this.wheelMaterial = wheelMaterial;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public void setChassisModel(String chassisModel) {
		this.chassisModel = chassisModel;
	}

	public void setChassisModelYear(String chassisModelYear) {
		this.chassisModelYear = chassisModelYear;
	}

	public void setTransmissionMake(String transmissionMake) {
		this.transmissionMake = transmissionMake;
	}

	public void setBrakeType(String brakeType) {
		this.brakeType = brakeType;
	}

	public void setSuspensionType(String suspensionType) {
		this.suspensionType = suspensionType;
	}

	public void setReeferMake(String reeferMake) {
		this.reeferMake = reeferMake;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public void setCapabilityId(int capabilityId) {
		this.capabilityId = capabilityId;
	}
	
}
