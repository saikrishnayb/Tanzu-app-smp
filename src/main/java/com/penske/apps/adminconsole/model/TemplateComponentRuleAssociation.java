package com.penske.apps.adminconsole.model;

public class TemplateComponentRuleAssociation {

	private int templateComponentId;
	private int ruleId;
	private int priority;
	private char lsOverride;
	private String createdBy;
	public int getTemplateComponentId() {
		return templateComponentId;
	}
	public void setTemplateComponentId(int templateComponentId) {
		this.templateComponentId = templateComponentId;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public char getLsOverride() {
		return lsOverride;
	}
	public void setLsOverride(char lsOverride) {
		this.lsOverride = lsOverride;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
