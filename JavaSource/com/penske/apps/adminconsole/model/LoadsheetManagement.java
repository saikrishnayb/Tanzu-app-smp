package com.penske.apps.adminconsole.model;

import java.util.Date;

public class LoadsheetManagement {

	private String catTypeId;
	private String category;
	private String type;
	private String usesDefault;
	private String editedBy;
	private Date editedDate;
	
	public String getCatTypeId() {
		return catTypeId;
	}
	public void setCatTypeId(String catTypeId) {
		this.catTypeId = catTypeId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsesDefault() {
		return usesDefault;
	}
	public void setUsesDefault(String usesDefault) {
		this.usesDefault = usesDefault;
	}
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	public Date getEditedDate() {
		return editedDate;
	}
	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}
	
	public String getFmtEditedDate() {
		return DateUtil.formatDate(this.editedDate,DateUtil.MM_dd_yy_HH_mm_a);
	}
	
	
}
