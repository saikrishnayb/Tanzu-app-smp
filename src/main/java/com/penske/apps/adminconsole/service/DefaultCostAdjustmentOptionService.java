package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.CostAdjustmentOptionDao;
import com.penske.apps.adminconsole.model.CostAdjustmentOption;

/**
 * This class is used for queries to the database for the Cost Sheet Adjustment
 * Options page in the Admin Console under the App Config tab.
 * 
 * @author Maxim.Timofeev
 */
@Service
public class DefaultCostAdjustmentOptionService implements CostAdjustmentOptionService {

	@Autowired
	private CostAdjustmentOptionDao costAdjustmentOptionDao;

	@Override
	public List<CostAdjustmentOption> getAllAdjustmentOptions() {
		return costAdjustmentOptionDao.getAllAdjustmentOptions();
	}

	@Override
	public CostAdjustmentOption getAdjustmentOption(int caOptionId) {
		return costAdjustmentOptionDao.getAdjustmentOption(caOptionId);
	}

	@Override
	public void addAdjustmentOption(CostAdjustmentOption caOption) {
		costAdjustmentOptionDao.addAdjustmentOption(caOption);
	}

	@Override
	public void updateAdjustmentOption(CostAdjustmentOption caOption) {
		costAdjustmentOptionDao.updateAdjustmentOption(caOption);
	}

	@Override
	public void deleteAdjustmentOption(int caOptionId) {
		costAdjustmentOptionDao.deleteAdjustmentOption(caOptionId);
	}
}
