package com.penske.apps.adminconsole.model;
/**
 * 
 * @author 502174985
 * This class represents all visible override components
 */
public class ComponentVisibilityOverride {
	private int visiblityOverrideId;
	private int poCategoryAssocId;
	private String poCategoryAssocName;
	private int dependentComponentId;
	private String dependentComponentName;
	private String overrideType;
	private int controllingComponentId;
	private String controllingComponentName;
	private String controllingValue;
	public int getVisiblityOverrideId() {
		return visiblityOverrideId;
	}
	public void setVisiblityOverrideId(int visiblityOverrideId) {
		this.visiblityOverrideId = visiblityOverrideId;
	}
	public int getPoCategoryAssocId() {
		return poCategoryAssocId;
	}
	public void setPoCategoryAssocId(int poCategoryAssocId) {
		this.poCategoryAssocId = poCategoryAssocId;
	}
	public int getDependentComponentId() {
		return dependentComponentId;
	}
	public void setDependentComponentId(int dependentComponentId) {
		this.dependentComponentId = dependentComponentId;
	}
	public String getOverrideType() {
		return overrideType;
	}
	public void setOverrideType(String overrideType) {
		this.overrideType = overrideType;
	}
	public int getControllingComponentId() {
		return controllingComponentId;
	}
	public void setControllingComponentId(int controllingComponentId) {
		this.controllingComponentId = controllingComponentId;
	}
	public String getControllingValue() {
		return controllingValue;
	}
	public void setControllingValue(String controllingValue) {
		this.controllingValue = controllingValue;
	}
	public String getDependentComponentName() {
		return dependentComponentName;
	}
	public void setDependentComponentName(String dependentComponentName) {
		this.dependentComponentName = dependentComponentName;
	}
	public String getControllingComponentName() {
		return controllingComponentName;
	}
	public void setControllingComponentName(String controllingComponentName) {
		this.controllingComponentName = controllingComponentName;
	}
	public String getPoCategoryAssocName() {
		return poCategoryAssocName;
	}
	public void setPoCategoryAssocName(String poCategoryAssocName) {
		this.poCategoryAssocName = poCategoryAssocName;
	}
}
