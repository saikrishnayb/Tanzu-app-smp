package com.penske.apps.adminconsole.model;

/**
 * This model contains a full Delay object that is displayed in the datatable on
 * the Delay Management page of Application Configuration.
 * 
 * @author 600132441 M.Leis
 * 
 */
public class DelayModel {

	private int delayId; // delay ID
	private String dateTypeId; // date type ID
	private String dateType; // date type
	private int poCategoryId; // po category ID
	private String poCategory; // po category
	private int delayTypeId; // delay type ID
	private String delayType; // delay type
	private int delayReasonId; // delay reason ID
	private String delayReason; // delay reason
	private String dateTypeDesc; // dateTypeDesc
	private int delayTypeReasonId; // delay Type / reason ID

	// Getters
	public int getDelayId() {
		return delayId;
	}

	public String getDateType() {
		return dateType;
	}

	public int getPoCategoryId() {
		return poCategoryId;
	}

	public String getPoCategory() {
		return poCategory;
	}

	public int getDelayTypeId() {
		return delayTypeId;
	}

	public String getDelayType() {
		return delayType;
	}

	public int getDelayReasonId() {
		return delayReasonId;
	}

	public String getDelayReason() {
		return delayReason;
	}

	// Setters
	public void setDelayId(int delayId) {
		this.delayId = delayId;
	}

	
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public void setPoCategoryId(int poCategoryId) {
		this.poCategoryId = poCategoryId;
	}

	public void setPoCategory(String poCategory) {
		this.poCategory = poCategory;
	}

	public void setDelayTypeId(int delayTypeId) {
		this.delayTypeId = delayTypeId;
	}

	public void setDelayType(String delayType) {
		this.delayType = delayType;
	}

	public void setDelayReasonId(int delayReasonId) {
		this.delayReasonId = delayReasonId;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	@Override
	public String toString() {
		return "DelayModel [delayId=" + delayId + ", dateTypeId=" + dateTypeId
				+ ", dateType=" + dateType + ", poCategoryId=" + poCategoryId
				+ ", poCategory=" + poCategory + ", delayTypeId=" + delayTypeId
				+ ", delayType=" + delayType + ", delayReasonId="
				+ delayReasonId + ", delayReason=" + delayReason + "]";
	}

	public String getDateTypeId() {
		return dateTypeId;
	}

	public void setDateTypeId(String dateTypeId) {
		this.dateTypeId = dateTypeId;
	}

	public String getDateTypeDesc() {
		return dateTypeDesc;
	}

	public void setDateTypeDesc(String dateTypeDesc) {
		this.dateTypeDesc = dateTypeDesc;
	}

	public int getDelayTypeReasonId() {
		return delayTypeReasonId;
	}

	public void setDelayTypeReasonId(int delayTypeReasonId) {
		this.delayTypeReasonId = delayTypeReasonId;
	}
}
