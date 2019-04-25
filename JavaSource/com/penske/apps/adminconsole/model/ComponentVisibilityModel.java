package com.penske.apps.adminconsole.model;


/**
 * 
 * @author 505001934
 * This class represents model for component visibility.
 */
public class ComponentVisibilityModel {
	private int categoryId;
	private int componentVisibilityId;
	private int componentId;
	private String componentName;
	private String compGroup;
	private String subGroup;
	private String loadSheet;
	private String lsOverride;
    private String screen2b;
    private int ruleCount;
    
    
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getComponentVisibilityId() {
		return componentVisibilityId;
	}
	public void setComponentVisibilityId(int componentVisibilityId) {
		this.componentVisibilityId = componentVisibilityId;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public int getRuleCount() {
		return ruleCount;
	}
	public void setRuleCount(int ruleCount) {
		this.ruleCount = ruleCount;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getGroup() {
		return compGroup;
	}
	public void setGroup(String group) {
		this.compGroup = group;
	}
	 
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getLoadSheet() { 
		return loadSheet;
	}
	public void setLoadSheet(String loadSheet) {
		this.loadSheet = loadSheet;
	}
	public String getLsOverride() {
		return lsOverride;
	}
	public void setLsOverride(String lsOverride) {
		this.lsOverride = lsOverride;
	}
	public String getScreen2b() {
		return screen2b;
	}
	public void setScreen2b(String screen2b) {
		this.screen2b = screen2b;
	}
	
	
	
	
	
	
	

}
