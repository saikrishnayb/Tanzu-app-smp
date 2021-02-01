package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.CostToleranceDao;
import com.penske.apps.adminconsole.model.CostTolerance;
import com.penske.apps.adminconsole.model.Manufacturer;

/**
 * This class is used for queries to the database for the Cost Sheet Tolerances
 * page in the Admin Console under the App Config tab.
 * 
 * @author Maxim.Timofeev
 */
@Service
public class DefaultCostToleranceService implements CostToleranceService {

	@Autowired
	private CostToleranceDao costToleranceDao;

	@Override
	public List<CostTolerance> getAllTolerances() {
		return costToleranceDao.getAllTolerances();
	}

	@Override
	public CostTolerance getTolerance(int costToleranceId) {
		return costToleranceDao.getTolerance(costToleranceId);
	}

	@Override
	public void addTolerance(CostTolerance costTolerance) {
		costToleranceDao.addTolerance(costTolerance);
	}

	@Override
	public void updateTolerance(CostTolerance costTolerance) {
		costToleranceDao.updateTolerance(costTolerance);
	}

	@Override
	public void deleteTolerance(int costToleranceId) {
		costToleranceDao.deleteTolerance(costToleranceId);
	}

	@Override
	public List<Manufacturer> getVehicleMakeList() {
		return costToleranceDao.getAllManufacturers();
	}
}
