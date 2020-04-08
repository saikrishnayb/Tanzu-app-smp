package com.penske.apps.buildmatrix.domain;

public class BusinessAwardDefault {
	
	private int businessDefaultId;
	private int groupId;
	private int attributeValueId;
	private int itemOrder;
	private int defaultPercentage;
	
	protected BusinessAwardDefault() {}
	
	public BusinessAwardDefault(int groupId, int attributeValueId, int defaultPercentage) {
		this.groupId = groupId;
		this.attributeValueId = attributeValueId;
		this.defaultPercentage = defaultPercentage;
		this.itemOrder = 0;
	}
	
	public int getAttributeValueId() {
		return attributeValueId;
	}
	
	public int getBusinessDefaultId() {
		return businessDefaultId;
	}
	
	public int getDefaultPercentage() {
		return defaultPercentage;
	}
	
	public void setDefaultPercentage(int defaultPercentage) {
		this.defaultPercentage = defaultPercentage;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getItemOrder() {
		return itemOrder;
	}

}
