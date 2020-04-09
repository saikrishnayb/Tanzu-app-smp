package com.penske.apps.buildmatrix.domain;

public class BusinessAward {
	
	private int businessAwardId;
	private int buildId;
	private String groupKey;
	private String attributeValueKey;
	private int awardOrder;
	private int percentage;
	private int quantity;
	private String awardType;
	
	protected BusinessAward() {}
	
	public BusinessAward(int buildId, String groupKey, String attributeValueKey, int awardOrder, int percentage, int quantity) {
		this.buildId = buildId;
		this.groupKey = groupKey;
		this.attributeValueKey = attributeValueKey;
		this.awardOrder = awardOrder;
		this.percentage = percentage;
		this.quantity = quantity;
		this.awardType = "Q";
	}

	public int getBusinessAwardId() {
		return businessAwardId;
	}

	public int getBuildId() {
		return buildId;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public String getAttributeValueKey() {
		return attributeValueKey;
	}

	public int getAwardOrder() {
		return awardOrder;
	}

	public int getPercentage() {
		return percentage;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getAwardType() {
		return awardType;
	}
	
}
