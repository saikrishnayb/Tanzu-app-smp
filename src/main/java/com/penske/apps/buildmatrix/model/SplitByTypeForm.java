package com.penske.apps.buildmatrix.model;

import java.util.List;

public class SplitByTypeForm {
	private int buildId;
	private List<BodySplitRow> bodySplitRows;
	
	public int getBuildId() {
		return buildId;
	}
	
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	
	public List<BodySplitRow> getBodySplitRows() {
		return bodySplitRows;
	}
	
	public void setBodySplitRows(List<BodySplitRow> bodySplitRows) {
		this.bodySplitRows = bodySplitRows;
	}
	
	public static class BodySplitRow {
		private String make;
		private int vanQty;
		private int reeferQty;
		private int flatbedQty;
		
		public String getMake() {
			return make;
		}
		public void setMake(String make) {
			this.make = make;
		}
		public int getVanQty() {
			return vanQty;
		}
		public void setVanQty(int vanQty) {
			this.vanQty = vanQty;
		}
		public int getReeferQty() {
			return reeferQty;
		}
		public void setReeferQty(int reeferQty) {
			this.reeferQty = reeferQty;
		}
		public int getFlatbedQty() {
			return flatbedQty;
		}
		public void setFlatbedQty(int flatbedQty) {
			this.flatbedQty = flatbedQty;
		}
		
	}
	
}
