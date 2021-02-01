package com.penske.apps.adminconsole.model;

import java.math.BigDecimal;

import com.penske.apps.adminconsole.enums.PoCategoryType;

/**
 * This is the basic model object for a Cost Tolerance, which is displayed in
 * the datatable on the Cost Sheet Tolerances page.
 * 
 * @author Maxim.Timofeev
 */
public class CostTolerance {
	private int toleranceId;
	private PoCategoryType poCategory;
	private String mfrCode;
	private BigDecimal tolerance;

	@Override
	public String toString() {
		return "CostTolerance [toleranceId=" + toleranceId + ", poCategory=" + poCategory + ", mfrCode=" + mfrCode
				+ ", tolerance=" + tolerance + "]";
	}

	public int getToleranceId() {
		return toleranceId;
	}

	public void setToleranceId(int toleranceId) {
		this.toleranceId = toleranceId;
	}

	public PoCategoryType getPoCategory() {
		return poCategory;
	}

	public void setPoCategory(PoCategoryType poCategory) {
		this.poCategory = poCategory;
	}

	public String getMfrCode() {
		return mfrCode;
	}

	public void setMfrCode(String mfrCode) {
		this.mfrCode = mfrCode;
	}

	public BigDecimal getTolerance() {
		return tolerance;
	}

	public void setTolerance(BigDecimal tolerance) {
		this.tolerance = tolerance;
	}
}
