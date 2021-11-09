package com.penske.apps.adminconsole.model;

import java.math.BigDecimal;
/**
 * This class will hold all relevant data on vendor report
 * objects,  these will be used to upload excel documents.
 * @author 600139251
 *
 */
public class VendorReport {
	String  reportId;
	BigDecimal rowSeq;
	BigDecimal colSeq;
	String colValue;
	int isHeader;
	String userId;

	/**
	 * @return the colSeq
	 */
	public BigDecimal getColSeq() {
		return colSeq;
	}
	/**
	 * @param colSeq the colSeq to set
	 */
	public void setColSeq(BigDecimal colSeq) {
		this.colSeq = colSeq;
	}
	/**
	 * @return the colValue
	 */
	public String getColValue() {
		return colValue;
	}
	/**
	 * @param colValue the colValue to set
	 */
	public void setColValue(String colValue) {
		this.colValue = colValue;
	}
	
	/**
	 * @return the isHeader
	 */
	public int getIsHeader() {
		return isHeader;
	}
	/**
	 * @param isHeader the isHeader to set
	 */
	public void setIsHeader(int isHeader) {
		this.isHeader = isHeader;
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
	/**
	 * @return the rowSeq
	 */
	public BigDecimal getRowSeq() {
		return rowSeq;
	}
	/**
	 * @param rowSeq the rowSeq to set
	 */
	public void setRowSeq(BigDecimal rowSeq) {
		this.rowSeq = rowSeq;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
