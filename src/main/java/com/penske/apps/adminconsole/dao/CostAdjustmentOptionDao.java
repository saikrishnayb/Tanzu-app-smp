package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.CostAdjustmentOption;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface is used for queries to the database for the Cost Sheet
 * Adjustment Options page.
 * 
 * @author Maxim.Timofeev
 */
@DBSmc
public interface CostAdjustmentOptionDao {

	@NonVendorQuery
	public List<CostAdjustmentOption> getAllAdjustmentOptions();

	@NonVendorQuery
	public CostAdjustmentOption getAdjustmentOption(@Param("caOptionId") int caOptionId);

	@NonVendorQuery
	public void addAdjustmentOption(@Param("caOption") CostAdjustmentOption caOption);

	@NonVendorQuery
	public void updateAdjustmentOption(@Param("caOption") CostAdjustmentOption caOption);

	@NonVendorQuery
	public void deleteAdjustmentOption(@Param("caOptionId") int caOptionId);
}
