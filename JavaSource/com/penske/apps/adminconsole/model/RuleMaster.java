package com.penske.apps.adminconsole.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 505001934
 * This class represents model for component rules
 */

public class RuleMaster {
	private int ruleId;
	private String ruleName;
	private String description;
	private String timesUsed;
	private String createdBy;
	private Date createdDate;
	private String editBy;
	private Date editDate;
	private String requestedFrom; //To identify from where the create rule request came
	private List<RuleDefinitions> ruleDefinitionsList;
	private List<Integer> deletedRuleDefIds;	//to store the deleted rule definition id's
	private List<RuleDefinitions> newRuleDef;	//to store new rule definition id's while updating rule
	private int templateComponentId;
	private char ruleType;
	private int templateId;
	private int priority;
	private boolean viewable;
	private boolean editable;
	private boolean required;
	private boolean forRules;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimesUsed() {
		return timesUsed;
	}
	public void setTimesUsed(String timesUsed) {
		this.timesUsed = timesUsed;
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
	public String getFmtEditedDate() {
		return DateUtil.formatDate(this.editDate,DateUtil.MM_dd_yy_HH_mm_a);
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
	public String getFmtCreatedDate() {
		return DateUtil.formatDate(this.createdDate,DateUtil.MM_dd_yy_HH_mm_a);
	}
	public List<RuleDefinitions> getRuleDefinitionsList() {
		return ruleDefinitionsList;
	}
	public void setRuleDefinitionsList(List<RuleDefinitions> ruleDefinitionsList) {
		this.ruleDefinitionsList = ruleDefinitionsList;
	}
	public List<Integer> getDeletedRuleDefIds() {
		return deletedRuleDefIds;
	}
	public void setDeletedRuleDefIds(List<Integer> deletedRuleDefIds) {
		this.deletedRuleDefIds = deletedRuleDefIds;
	}
	public String getRequestedFrom() {
		return requestedFrom;
	}
	public void setRequestedFrom(String requestedFrom) {
		this.requestedFrom = requestedFrom;
	}
	public int getTemplateComponentId() {
		return templateComponentId;
	}
	public void setTemplateComponentId(int templateComponentId) {
		this.templateComponentId = templateComponentId;
	}
	public char getRuleType() {
		return ruleType;
	}
	public void setRuleType(char ruleType) {
		this.ruleType = ruleType;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isViewable() {
		return viewable;
	}
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
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
	public boolean isForRules() {
		return forRules;
	}
	public void setForRules(boolean forRules) {
		this.forRules = forRules;
	}
	public List<RuleDefinitions> getNewRuleDef() {
		return newRuleDef;
	}
	public void setNewRuleDef(List<RuleDefinitions> newRuleDef) {
		this.newRuleDef = newRuleDef;
	}


}
