package com.penske.apps.buildmatrix.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.toList;

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
	
	//MODIFIED ACCESSORS
	public String getAttributeValueList(){
		return StringUtils.join(attributeValues.stream().map(val -> val.getAttributeValue()).collect(toList()), ',');
	}
	
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
