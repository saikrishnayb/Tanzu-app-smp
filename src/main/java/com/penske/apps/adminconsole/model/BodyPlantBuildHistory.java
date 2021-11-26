package com.penske.apps.adminconsole.model;

public class BodyPlantBuildHistory {

	private int buildId;
	private long unitQty;
	private String status;
	
	private String startedBy;
	private String startedDate;
	private String submittedBy;
	private String submittedDate;	
	private String runStartDate;
	private String runEndDate;
	
	private String showRunBuildBtn;
	private String showViewReportBtn;
	
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	public long getUnitQty() {
		return unitQty;
	}
	public void setUnitQty(long unitQty) {
		this.unitQty = unitQty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartedBy() {
		return startedBy;
	}
	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getRunStartDate() {
		return runStartDate;
	}
	public void setRunStartDate(String runStartDate) {
		this.runStartDate = runStartDate;
	}
	public String getRunEndDate() {
		return runEndDate;
	}
	public void setRunEndDate(String runEndDate) {
		this.runEndDate = runEndDate;
	}
	public String getShowRunBuildBtn() {
		return showRunBuildBtn;
	}
	public void setShowRunBuildBtn(String showRunBuildBtn) {
		this.showRunBuildBtn = showRunBuildBtn;
	}
	public String getShowViewReportBtn() {
		return showViewReportBtn;
	}
	public void setShowViewReportBtn(String showViewReportBtn) {
		this.showViewReportBtn = showViewReportBtn;
	}	
}
