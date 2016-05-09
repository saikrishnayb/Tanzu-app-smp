package com.penske.apps.adminconsole.model;

public class Components {
	private String componentName;
	private String componentId;
	private int templateId;
	private String visibility;
	private boolean editable;
	private boolean required;
	private boolean dispOtherPO;
	private boolean excel;
	private String editRequiredStr;
	private int dispOtherPOStr=0;
	private int excelStr=0;
	private String wasChecked;
	private String createdBy;
	private String modifiedBy;
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isDispOtherPO() {
		return dispOtherPO;
	}
	public void setDispOtherPO(boolean dispOtherPO) {
		this.dispOtherPO = dispOtherPO;
	}
	public boolean isExcel() {
		return excel;
	}
	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	public String getWasChecked() {
		return wasChecked;
	}
	public void setWasChecked(String wasChecked) {
		this.wasChecked = wasChecked;
	}
	public String getEditRequiredStr() {
		return editRequiredStr;
	}
	public void setEditRequiredStr(String editRequiredStr) {
		this.editRequiredStr = editRequiredStr;
	}
	public int getDispOtherPOStr() {
		return dispOtherPOStr;
	}
	public void setDispOtherPOStr(int dispOtherPOStr) {
		this.dispOtherPOStr = dispOtherPOStr;
	}
	public int getExcelStr() {
		return excelStr;
	}
	public void setExcelStr(int excelStr) {
		this.excelStr = excelStr;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
