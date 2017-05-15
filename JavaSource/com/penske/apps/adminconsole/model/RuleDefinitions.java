package com.penske.apps.adminconsole.model;

import java.util.Date;

/**
 * This class will store the rule definition details
 * 
 * @author 502403391
 *
 */

public class RuleDefinitions {
	
	private int ruleId;
	private int criteriaGroup;
	private String componentId;
	private String componentType;
	private String operand;
	private String value;
	private String createdBy;
	private Date createdDate;
	private String editBy;
	private Date editDate;
	private Boolean isGroupHeader;
	
	public int getCriteriaGroup() {
		return criteriaGroup;
	}
	public void setCriteriaGroup(int criteriaGroup) {
		this.criteriaGroup = criteriaGroup;
	}
	
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public Boolean getIsGroupHeader() {
		return isGroupHeader;
	}
	public void setIsGroupHeader(Boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}
	
	
}
