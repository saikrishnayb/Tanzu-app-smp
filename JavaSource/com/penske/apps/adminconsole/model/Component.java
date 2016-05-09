package com.penske.apps.adminconsole.model;

import java.util.List;

public class Component {
	private String componentName;
	private String componentId;

	private String visibility;
	
	private List<PoCategory> category;
	private List<SubCategory> subCategory;
	
	
	
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
	public List<PoCategory> getCategory() {
		return category;
	}
	public void setCategory(List<PoCategory> category) {
		this.category = category;
	}
	public List<SubCategory> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(List<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}
	
	
	

}
