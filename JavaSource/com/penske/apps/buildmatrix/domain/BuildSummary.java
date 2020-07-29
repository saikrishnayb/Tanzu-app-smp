package com.penske.apps.buildmatrix.domain;

import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.smccore.base.util.DateUtil;
import com.penske.apps.suppliermgmt.model.UserContext;

public class BuildSummary {
	private Integer buildId;
	private int reqQty;
	private int fulfilledQty;
	private BuildStatus buildStatus;
	private String startedBySso;
	private String startedByName;
	private Date startedDate;
	private String submittedBySso;
	private String submittedByName;
	private Date submittedDate;
	private Date runStartDate;
	private Date runEndDate;
	private int maxWeeksBefore;
	private int maxWeeksAfter;
	
	protected BuildSummary() {}
	
	public BuildSummary(int reqQty, UserContext userContext, int maxWeeksBefore, int maxWeeksAfter) {
		this.reqQty = reqQty;
		this.buildStatus = BuildStatus.STARTED;
		this.startedByName = userContext.getFirstName() + " " + userContext.getLastName();
		this.startedBySso = userContext.getUserSSO();
		this.maxWeeksBefore = maxWeeksBefore;
		this.maxWeeksAfter = maxWeeksAfter;
	}
	
	@Override
	public String toString() {
		return "BuildSummary (Build ID: " + buildId + "), Quantity: " + reqQty + ", Status: " + buildStatus + ", Started By: " + startedByName;
	}

	//***** MODIFIED ACCESSORS *****//
	public boolean showStartBuildBtn() {
		if(BuildStatus.STARTED == buildStatus || BuildStatus.SUBMITTED == buildStatus)
			return false;
		else
			return true;
	}
	
	public boolean showViewReportBtn() {
		if(BuildStatus.COMPLETED == buildStatus || BuildStatus.APPROVED == buildStatus)
			return true;
		else
			return false;
	}
	
	public boolean showReworkBtn() {
		if(BuildStatus.COMPLETED == buildStatus && BuildStatus.APPROVED != buildStatus)
			return true;
		else
			return false;
	}
	
	//***** DEFAULT ACCESSORS *****//
	public Integer getBuildId() {
		return buildId;
	}

	public BuildStatus getBuildStatus() {
		return buildStatus;
	}

	public String getStartedBySso() {
		return startedBySso;
	}

	public String getStartedByName() {
		return startedByName;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public String getSubmittedBySso() {
		return submittedBySso;
	}

	public String getSubmittedByName() {
		return submittedByName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public Date getRunStartDate() {
		return runStartDate;
	}

	public Date getRunEndDate() {
		return runEndDate;
	}
	
	public int getMaxWeeksBefore() {
		return maxWeeksBefore;
	}

	public void setMaxWeeksBefore(int maxWeeksBefore) {
		this.maxWeeksBefore = maxWeeksBefore;
	}

	public int getMaxWeeksAfter() {
		return maxWeeksAfter;
	}

	public void setMaxWeeksAfter(int maxWeeksAfter) {
		this.maxWeeksAfter = maxWeeksAfter;
	}

	public int getFulfilledQty() {
		return fulfilledQty;
	}

	public void setFulfilledQty(int fulfilledQty) {
		this.fulfilledQty = fulfilledQty;
	}

	public int getReqQty() {
		return reqQty;
	}

	public void setReqQty(int reqQty) {
		this.reqQty = reqQty;
	}
	
	public String getFormattedStartDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(startedDate));
	}
	
	public String getFormattedSubmittedDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(submittedDate));
	}
	
	public String getFormattedRunEndDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(runEndDate));
	}
	
}
