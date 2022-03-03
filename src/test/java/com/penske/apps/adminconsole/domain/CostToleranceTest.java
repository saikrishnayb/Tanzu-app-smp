/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.penske.apps.adminconsole.enums.PoCategoryType;

/**
 * Class under test: {@link CostTolerance}
 */
public class CostToleranceTest
{
	private final PoCategoryType cat = PoCategoryType.TRUCK;
	private final BigDecimal cost = new BigDecimal("15.40");
	
	private final CostTolerance tolerance = new CostTolerance(cat, "FTL", null, cost);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void shouldCreate()
	{
		PoCategoryType cat = PoCategoryType.TRUCK;
		BigDecimal cost = new BigDecimal("15.40");
		
		assertTolerance(new CostTolerance(cat, "FTL", null, cost), cat, "FTL", null, cost);
		assertTolerance(new CostTolerance(cat, "", 540360, cost), cat, null, 540360, cost);
		assertTolerance(new CostTolerance(cat, null, 540360, cost), cat, null, 540360, cost);
		assertTolerance(new CostTolerance(cat, "FTL", 540360, cost), cat, "FTL", 540360, cost);
	}
	
	@Test
	public void shouldNotCreateWithoutPOCategory()
	{
		thrown.expectMessage("PO Category is required");
		new CostTolerance(null, "FTL", null, cost);
	}
	
	@Test
	public void shouldNotCreateWithoutMfrCodeAndVendorNumber()
	{
		thrown.expectMessage("Please provide either MFR code or PO Vendor Number");
		new CostTolerance(cat, "", null, cost);
	}

	@Test
	public void shouldUpdateTolerance()
	{
		tolerance.updateTolerance(new BigDecimal("38.50"));
		
		assertThat(tolerance.getTolerance().toPlainString(), is("38.50"));
		
		tolerance.updateTolerance(BigDecimal.ZERO);
		
		assertThat(tolerance.getTolerance(), is(BigDecimal.ZERO));
	}
	
	@Test
	public void shouldNotUpdateToleranceMissing()
	{
		thrown.expectMessage("Tolerance is required");
		tolerance.updateTolerance(null);
	}
	
	@Test
	public void shouldNotUpdateToleranceNegative()
	{
		thrown.expectMessage("Tolerance must be positive");
		tolerance.updateTolerance(new BigDecimal("-0.01"));
	}
	
	//***** HELPER METHODS *****//
	private void assertTolerance(CostTolerance tolerance, PoCategoryType cat, String mfrCode, Integer poVendorNumber, BigDecimal cost)
	{
		assertThat(tolerance.getPoCategory(), is(cat));
		assertThat(tolerance.getMfrCode(), is(mfrCode));
		assertThat(tolerance.getPoVendorNumber(), is(poVendorNumber));
		assertThat(tolerance.getTolerance(), is(cost));
	}
}
