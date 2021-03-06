package com.penske.apps.buildmatrix.domain;

public class BuildMatrixSlotType {
	private int slotTypeId;
	private String slotType;
	private String slotTypeDesc;
	private int chassisLeadTime;

	public int getSlotTypeId() {
		return slotTypeId;
	}

	public String getSlotType() {
		return slotType;
	}

	public String getSlotTypeDesc() {
		return slotTypeDesc;
	}

	public void setSlotTypeId(int slotTypeId) {
		this.slotTypeId = slotTypeId;
	}

	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}

	public void setSlotTypeDesc(String slotTypeDesc) {
		this.slotTypeDesc = slotTypeDesc;
	}

	public int getChassisLeadTime() {
		return chassisLeadTime;
	}
	
	public int getNegativeChassisLeadTime() {
		return -chassisLeadTime;
	}

}
