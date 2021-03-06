package com.penske.apps.adminconsole.model;

public class Component {
	private String componentGroup;
	private String subGroup;
    private String subComponentName;
	 
	private Integer componentId;
	private Integer componentGroupNumber;
	private String componentType;
	private Integer displaySequence;
	private boolean visible;
	private boolean allowDuplicates;
	
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
    public String getSubComponentName() {
        return subComponentName;
	}
    public void setSubComponentName(String subComponentName) {

        this.subComponentName = subComponentName;
	}
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public Integer getComponentGroupNumber() {
		return componentGroupNumber;
	}
	public void setComponentGroupNumber(Integer componentGroupNumber) {
		this.componentGroupNumber = componentGroupNumber;
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
	public boolean isAllowDuplicates() {
		return allowDuplicates;
	}
	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	@Override
	public String toString() {

        return "Component [componentGroup=" + componentGroup + ", subGroup=" + subGroup + ", subComponentName=" + subComponentName + ", componentId=" + componentId + ", componentGroupNumber=" + componentGroupNumber + ", componentType=" + componentType + ", displaySequence=" + displaySequence + ", visible=" + visible + "]";
	}
	
	
}
