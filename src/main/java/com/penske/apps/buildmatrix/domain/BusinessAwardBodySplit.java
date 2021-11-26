package com.penske.apps.buildmatrix.domain;

public class BusinessAwardBodySplit {
	private int bodySplitId;
	private int buildId;
	private String make;
	private int vanQty;
	private int reeferQty;
	private int flatbedQty;
	
	protected BusinessAwardBodySplit() {}
	
	public BusinessAwardBodySplit(int buildId, String make, int vanQty, int reeferQty,
			int flatbedQty) {
		this.buildId = buildId;
		this.make = make;
		this.vanQty = vanQty;
		this.reeferQty = reeferQty;
		this.flatbedQty = flatbedQty;
	}

	public int getBodySplitId() {
		return bodySplitId;
	}

	public int getBuildId() {
		return buildId;
	}

	public String getMake() {
		return make;
	}

	public int getVanQty() {
		return vanQty;
	}

	public int getReeferQty() {
		return reeferQty;
	}

	public int getFlatbedQty() {
		return flatbedQty;
	}
	
	
}
