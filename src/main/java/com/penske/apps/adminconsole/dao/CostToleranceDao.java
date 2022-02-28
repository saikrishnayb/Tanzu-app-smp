package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.domain.CostTolerance;
import com.penske.apps.adminconsole.model.Manufacturer;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface is used for queries to the database for the Cost Sheet
 * Tolerances page.
 * 
 * @author Maxim.Timofeev
 */
@DBSmc
public interface CostToleranceDao {

	@NonVendorQuery
	public List<Manufacturer> getAllManufacturers();

	@NonVendorQuery
	public List<CostTolerance> getTolerances(@Param("costToleranceId") Integer costToleranceId);

	@NonVendorQuery
	public void addTolerance(@Param("costTolerance") CostTolerance costTolerance);

	@NonVendorQuery
	public void updateTolerance(@Param("costTolerance") CostTolerance costTolerance);

	@NonVendorQuery
	public void deleteTolerance(@Param("costToleranceId") int costToleranceId);
}
