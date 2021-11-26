/**
 * 
 */
package com.penske.apps.adminconsole.model;

import java.math.BigDecimal;

/**
 * @author 600125544
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public class VendorReportResults {
	
	String reportId;
	BigDecimal count;
	String message;
	/**
	 * @return the count
	 */
	public BigDecimal getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	
	

}
