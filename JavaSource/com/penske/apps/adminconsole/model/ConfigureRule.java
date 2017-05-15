package com.penske.apps.adminconsole.model;


/**
 * 
 * @author 505001934
 * This class represents model for rule configuration.
 */

public class ConfigureRule {
	private int ruleId;
	private String priority;
	private String lsOverride;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getLsOverride() {
		return lsOverride;
	}
	public void setLsOverride(String lsOverride) {
		this.lsOverride = lsOverride;
	}
	

}
