package com.penske.apps.adminconsole.model;
/**
 * 
 * @author 600144005
 * Model object for holding information about components 
 */
public class TemplateComponents {
	private int tempCompId;
	private int templateId;
	private int componentId;
	private String componentName;
	private int visible;
	private int editable;
	private int required;
	private String dataType;

	// Getters
	public int getTempCompId() {
		return tempCompId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public int getComponentId() {
		return componentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public int getVisible() {
		return visible;
	}

	public int getEditable() {
		return editable;
	}

	public int getRequired() {
		return required;
	}

	public String getDataType() {
		return dataType;
	}

	// Setters
	public void setTempCompId(int tempCompId) {
		this.tempCompId = tempCompId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public void setEditable(int editable) {
		this.editable = editable;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "TemplateComponents [tempCompId=" + tempCompId + ", templateId="
				+ templateId + ", componentId=" + componentId
				+ ", componentName=" + componentName + ", visible=" + visible
				+ ", editable=" + editable + ", required=" + required
				+ ", dataType=" + dataType + "]";
	}
}
