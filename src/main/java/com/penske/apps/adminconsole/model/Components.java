package com.penske.apps.adminconsole.model;

public class Components {
	private String componentName;
	private String componentId;
	private int templateId;
	private int templateComponentId;
	private int ruleCount;
	private String visibility;
	private boolean viewable;
	private boolean editable;
	private boolean required;
	private boolean dispOtherPO;
	private boolean excel;
	private String editRequiredStr;
	private String dispOtherPOStr="N";
	private String excelStr="N";
	private String wasChecked;
	private String createdBy;
	private String modifiedBy;
	private String fullName;
	private boolean forRules;
	
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
	public String getDispOtherPOStr() {
		return dispOtherPOStr;
	}
	public void setDispOtherPOStr(String dispOtherPOStr) {
		this.dispOtherPOStr = dispOtherPOStr;
	}
	public String getExcelStr() {
		return excelStr;
	}
	public void setExcelStr(String excelStr) {
		this.excelStr = excelStr;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public int getTemplateComponentId() {
		return templateComponentId;
	}
	public void setTemplateComponentId(int templateComponentId) {
		this.templateComponentId = templateComponentId;
	}
	public int getRuleCount() {
		return ruleCount;
	}
	public void setRuleCount(int ruleCount) {
		this.ruleCount = ruleCount;
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
	public boolean isViewable() {
		return viewable;
	}
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isForRules() {
		return forRules;
	}
	public void setForRules(boolean forRules) {
		this.forRules = forRules;
	}
		
}
