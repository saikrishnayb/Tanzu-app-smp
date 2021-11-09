package com.penske.apps.adminconsole.model;

import java.util.Date;

import com.penske.apps.smccore.base.util.DateUtil;

/**
 * Model object for holding information about terms and conditions.
 * 
 * @author mark.weaver 600144069
 * 
 */

public class TermsAndConditions {
	private int versionNumber;
	private Date startDate;
	private Date endDate;
	private int status;
	private String termsAndConditionsText;

	// Getters
	public int getVersionNumber() {
		return versionNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getFormattedStartDate()
	{
		return DateUtil.formatDateUS(startDate);
	}
	
	public String getFormattedEndDate()
	{
		return DateUtil.formatDateUS(endDate);
	}
	
	public int getStatus() {
		return status;
	}

	public String getTermsAndConditionsText() {
		return termsAndConditionsText;
	}

	// Setters
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTermsAndConditionsText(String termsAndConditionsText) {
		this.termsAndConditionsText = termsAndConditionsText;
	}

}
