package com.penske.apps.buildmatrix.domain;

public class BuildAttributeValue {
	private int attributeValueId;
	private String attributeValue;
	private int defaultPercentage;
	private int itemOrder;
	
	protected BuildAttributeValue() {}
	
	public int getAttributeValueId() {
		return attributeValueId;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}
	
	public int getDefaultPercentage() {
		return defaultPercentage;
	}
	
	public int getItemOrder() {
		return itemOrder;
	}
}
