package com.penske.apps.adminconsole.model;

public class Component {
	private String componentGroup;
	private String subGroup;
	private String subComponentGroup;
	 
	private Integer componentId;
	private String componentGroupId;
	private String componentType;
	private Integer displaySequence;
	private boolean visible;
	 
	public String getComponentGroup() {
		return componentGroup;
	}
	public void setComponentGroup(String componentGroup) {
		this.componentGroup = componentGroup;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getSubComponentGroup() {
		return subComponentGroup;
	}
	public void setSubComponentGroup(String subComponentGroup) {
		this.subComponentGroup = subComponentGroup;
	}
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public String getComponentGroupId() {
		return componentGroupId;
	}
	public void setComponentGroupId(String componentGroupId) {
		this.componentGroupId = componentGroupId;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public Integer getDisplaySequence() {
		return displaySequence;
	}
	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public String toString() {
		return "Component [componentGroup=" + componentGroup + ", subGroup=" + subGroup + ", subComponentGroup=" + subComponentGroup + ", componentId=" + componentId + ", componentGroupId=" + componentGroupId + ", componentType=" + componentType + ", displaySequence=" + displaySequence + ", visible=" + visible + "]";
	}
	
	
}
