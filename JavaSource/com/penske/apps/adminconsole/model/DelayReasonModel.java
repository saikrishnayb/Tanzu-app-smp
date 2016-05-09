package com.penske.apps.adminconsole.model;

/**
 * This model object contains all of the information to construct a Delay Reason
 * on the Delay Reason Codes page.
 * 
 * @author 600132441 M.Leis
 * 
 */
public class DelayReasonModel {

	private int typeId; 			// delay type ID
	private int reasonId; 			// delay reason ID
	private String delayReason; 	// delay reason name

	// Getters
	public int getTypeId() {
		return typeId;
	}

	public int getReasonId() {
		return reasonId;
	}

	public String getDelayReason() {
		return delayReason;
	}
	
	// Setters
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	@Override
	public String toString() {
		return "DelayReasonModel [typeId=" + typeId + ", reasonId=" + reasonId
				+ ", delayReason=" + delayReason + "]";
	}
}
