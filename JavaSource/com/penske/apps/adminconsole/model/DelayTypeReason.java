package com.penske.apps.adminconsole.model;

/**
 * This model object contains all of the information to construct a Delay Reason
 * on the Delay Reason Codes page.
 * 
 * @author 600132441 M.Leis
 * 
 */
public class DelayTypeReason {

	private int typeId; 			// delay type ID
	private int reasonId; 			// delay reason ID
	private String typeVal; 			// delay type ID
	private String reasonVal; 
	private int delayAssocid;
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getReasonId() {
		return reasonId;
	}
	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}
	public int getDelayAssocid() {
		return delayAssocid;
	}
	public void setDelayAssocid(int delayAssocid) {
		this.delayAssocid = delayAssocid;
	}
	public String getTypeVal() {
		return typeVal;
	}
	public void setTypeVal(String typeVal) {
		this.typeVal = typeVal;
	}
	public String getReasonVal() {
		return reasonVal;
	}
	public void setReasonVal(String reasonVal) {
		this.reasonVal = reasonVal;
	}	

}
