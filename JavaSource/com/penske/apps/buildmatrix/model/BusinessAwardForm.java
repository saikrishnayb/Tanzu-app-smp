package com.penske.apps.buildmatrix.model;

import java.util.List;

public class BusinessAwardForm {
	
	private List<BusinessAwardRow> businessAwardRows;

	public List<BusinessAwardRow> getBusinessAwardRows() {
		return businessAwardRows;
	}
	
	public void setBusinessAwardRows(List<BusinessAwardRow> businessAwardRows) {
		this.businessAwardRows = businessAwardRows;
	}
	
	public static class BusinessAwardRow {
		private int attributeValueId;
		private int groupId;
		private int percentage;
		
		public int getAttributeValueId() {
			return attributeValueId;
		}
		
		public Integer getGroupId() {
			return groupId;
		}
		
		public int getPercentage() {
			return percentage;
		}
		
		public void setAttributeValueId(int attributeValueId) {
			this.attributeValueId = attributeValueId;
		}
		
		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}
		
		public void setPercentage(int percentage) {
			this.percentage = percentage;
		}
	}
}
