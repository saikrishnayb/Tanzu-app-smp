package com.penske.apps.buildmatrix.model;

import java.util.List;

public class BuildMixForm {
	
	private List<AttributeRow> attributeRows;
	private int buildId;
	private boolean guidance;
	
	public int getBuildId() {
		return buildId;
	}
	
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	
	public boolean isGuidance() {
		return guidance;
	}
	
	public void setGuidance(boolean guidance) {
		this.guidance = guidance;
	}
	
	public List<AttributeRow> getAttributeRows() {
		return attributeRows;
	}
	
	public void setAttributeRows(List<AttributeRow> attributeRows) {
		this.attributeRows = attributeRows;
	}
	
	public static class AttributeRow {
		private String awardKey;
		private String groupKey;
		private int awardQuantity;
		private int awardPercentage;
		private Integer awardExcess;
		
		public String getAwardKey() {
			return awardKey;
		}

		public void setAwardKey(String awardKey) {
			this.awardKey = awardKey;
		}

		public String getGroupKey() {
			return groupKey;
		}

		public void setGroupKey(String groupKey) {
			this.groupKey = groupKey;
		}

		public int getAwardQuantity() {
			return awardQuantity;
		}

		public void setAwardQuantity(int awardQuantity) {
			this.awardQuantity = awardQuantity;
		}

		public int getAwardPercentage() {
			return awardPercentage;
		}

		public void setAwardPercentage(int awardPercentage) {
			this.awardPercentage = awardPercentage;
		}
		
		public Integer getAwardExcess() {
			return awardExcess;
		}

		public void setAwardExcess(Integer awardExcess) {
			this.awardExcess = awardExcess;
		}

	}

}
