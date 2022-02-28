package com.penske.apps.adminconsole.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.smccore.base.exception.AppValidationException;
import com.penske.apps.smccore.base.exception.BusinessRuleException;

/**
 * This is the basic model object for a Cost Tolerance, which is displayed in
 * the datatable on the Cost Sheet Tolerances page.
 * 
 * @author Maxim.Timofeev
 */
//FIXME: test
public class CostTolerance
{
	/** Internal database ID */
	private int toleranceId;
	/** The category of vehicle that this tolerance applies to */
	private PoCategoryType poCategory;
	/** If this is non-null, this tolerance only applies to PO / Units whose spec has the given MFR code */
	private String mfrCode;
	/** If this is non-null, this tolerance only applies to PO / Units where the PO is being issued to the given vendor number */
	private Integer poVendorNumber;
	/** Represents the amount the PO total is allowed to be less than the CATS total without the user having to enter a comment on the cost sheet */
	private BigDecimal tolerance;

	protected CostTolerance() {}

	/**
	 * Creates a new cost tolerance. Either MFR code or PO vendor number must be provided
	 * @param poCategory The category of vehicle
	 * @param mfrCode The MFR code that this tolerance appiles to
	 * @param poVendorNumber The PO vendor number that this tolerance applies to
	 * @param tolerance The amount of the tolerance
	 */
	public CostTolerance(PoCategoryType poCategory, String mfrCode, Integer poVendorNumber, BigDecimal tolerance)
	{
		if(poCategory == null)
			throw new BusinessRuleException("PO Category is required");
		if(StringUtils.isBlank(mfrCode) && poVendorNumber == null)
			throw new AppValidationException("Please provide either MFR code or PO Vendor Number");
		
		this.poCategory = poCategory;
		this.mfrCode = StringUtils.isBlank(mfrCode) ? null : mfrCode;
		this.poVendorNumber = poVendorNumber;
		
		updateTolerance(tolerance);
	}
	
	@Override
	public String toString() {
		return "CostTolerance [toleranceId=" + toleranceId + ", poCategory=" + poCategory + ", mfrCode=" + mfrCode
				+ ", tolerance=" + tolerance + "]";
	}

	/**
	 * Changes the amount of this tolerance record
	 * @param tolerance The new amount. Must be positive
	 */
	public void updateTolerance(BigDecimal tolerance)
	{
		if(tolerance == null)
			throw new BusinessRuleException("Tolerance is required");
		if(tolerance.compareTo(BigDecimal.ZERO) < 0)
			throw new BusinessRuleException("Tolerance must be positive");
		
		this.tolerance = tolerance;
	}
	
	//***** DEFAULT ACCESSORS *****//
	public int getToleranceId() {
		return toleranceId;
	}

	public PoCategoryType getPoCategory() {
		return poCategory;
	}

	public String getMfrCode() {
		return mfrCode;
	}

	public BigDecimal getTolerance() {
		return tolerance;
	}

	public Integer getPoVendorNumber()
	{
		return poVendorNumber;
	}

}
