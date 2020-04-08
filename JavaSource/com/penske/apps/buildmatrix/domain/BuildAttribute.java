package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class BuildAttribute {

	private int attributeId;
	private String attributeKey;
	private String attributeName;
	
	private Integer groupId;
	private String groupKey;
	private String groupDescription;
	private int groupOrder;
	
	List<BuildAttributeValue> attributeValues;
	
	protected BuildAttribute() {}
	
	//DEFAULT ACCESSORS
	public int getAttributeId() {
		return attributeId;
	}
	public String getAttributeKey() {
		return attributeKey;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public int getGroupOrder() {
		return groupOrder;
	}
	public List<BuildAttributeValue> getAttributeValues() {
		return attributeValues;
	}
}
