package com.penske.apps.adminconsole.model;

import java.util.List;


/**
 * 
 * @author 505001934
 * This class represents model for component rule mapping
 */
public class ComponentRuleAssociation {
	
	private int componentVisibilityId;
	private String createdBy;
	private List<ConfigureRule> rule;
	private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getComponentVisibilityId() {
		return componentVisibilityId;
	}
	public void setComponentVisibilityId(int componentVisibilityId) {
		this.componentVisibilityId = componentVisibilityId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<ConfigureRule> getRule() {
		return rule;
	}
	public void setRule(List<ConfigureRule> rule) {
		this.rule = rule;
	}
}
