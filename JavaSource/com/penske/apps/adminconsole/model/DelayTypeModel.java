package com.penske.apps.adminconsole.model;

/**
 * This model object contains all of the information to populate a Delay Type on
 * the Delay Reason Types page.
 * 
 * @author 600132441 M.Leis
 * 
 */
public class DelayTypeModel {

	private int typeId; 		// delay type ID
	private String delayType; 	// delay type name

	// Getters
	public int getTypeId() {
		return typeId;
	}

	public String getDelayType() {
		return delayType;
	}

	// Setters
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public void setDelayType(String delayType) {
		this.delayType = delayType;
	}

	@Override
	public String toString() {
		return "DelayTypeModel [typeId=" + typeId + ", delayType=" + delayType
				+ "]";
	}
}
