package com.penske.apps.adminconsole.model;

import java.util.Date;
import com.penske.apps.smccore.base.util.DateUtil;

public class LoadsheetManagement {

	private String catTypeId;
	private String category;
	private String type;
	private String usesDefault;
	private String componentId;
	private String editedBy;
	private Date editedDate;
	
	//added fields for loading assigned categories for one rule  
	private String componentGroup;
	private String subGroup;
	private String component;
	private String lsOverride;
	
	
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
		return DateUtil.formatDateTimeUS(this.editedDate);
	}
	public String getComponentGroup() {
		return componentGroup;
	}
	public void setComponentGroup(String componentGroup) {
		this.componentGroup = componentGroup;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getLsOverride() {
		return lsOverride;
	}
	public void setLsOverride(String lsOverride) {
		this.lsOverride = lsOverride;
	}
	
	
}
