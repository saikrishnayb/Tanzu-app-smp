package com.penske.apps.adminconsole.model;

import java.util.List;

public class BuildMatrixAttribute {

	private int attributeId;
	private String attributeName;
	private String attributeType;
	private String optionGroup;
	private List<String> values;
	private String attributeValue;
	
	// getters
	/**
	 * @return the attributeId
	 */
	public int getAttributeId() {
		return attributeId;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @return the attributeType
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @return the optionGroup
	 */
	public String getOptionGroup() {
		return optionGroup;
	}
	
	/**
	 * @return the attributeValue
	 */
	public String getAttributeValue() {
		return attributeValue;
	}

	//setters
	/**
	 * @param attributeId
	 *            the attributeId to set
	 */
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @param attributeName
	 *            the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @param attributeType
	 *            the attributeType to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

	/**
	 * @param optionGroup the optionGroup to set
	 */
	public void setOptionGroup(String optionGroup) {
		this.optionGroup = optionGroup;
	}
	
	/**
	 * @param attributeValue the attributeValue to set
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}


}
