package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.CostAdjustmentOption;

/**
 * This interface is used for queries to the database for the Cost Sheet
 * Adjustment Options page in the Admin Console under the App Config tab.
 * 
 * @author Maxim.Timofeev
 */
public interface CostAdjustmentOptionService {

	public List<CostAdjustmentOption> getAllAdjustmentOptions();

	public CostAdjustmentOption getAdjustmentOption(int caOptionId);

	public void addAdjustmentOption(CostAdjustmentOption caOption);

	public void updateAdjustmentOption(CostAdjustmentOption caOption);

	public void deleteAdjustmentOption(int caOptionId);
}
