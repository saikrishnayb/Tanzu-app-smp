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
	private List<RuleDefinitions> ruleDefinitionsList;

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
		return DateUtil.formatDate(this.editDate,DateUtil.MM_dd_yy_HH_mm_a);
	}
	public List<RuleDefinitions> getRuleDefinitionsList() {
		return ruleDefinitionsList;
	}
	public void setRuleDefinitionsList(List<RuleDefinitions> ruleDefinitionsList) {
		this.ruleDefinitionsList = ruleDefinitionsList;
	}
	

}
