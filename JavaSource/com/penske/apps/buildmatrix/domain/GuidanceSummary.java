package com.penske.apps.buildmatrix.domain;

public class GuidanceSummary {
	private int guidanceId;
	private String groupKey;
	private String awardKey;
	private int guidanceTarget;
	private int guidanceAllocation;
	
	protected GuidanceSummary() {}

	public int getGuidanceId() {
		return guidanceId;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public String getAwardKey() {
		return awardKey;
	}

	public int getGuidanceTarget() {
		return guidanceTarget;
	}

	public int getGuidanceAllocation() {
		return guidanceAllocation;
	}
	
}
