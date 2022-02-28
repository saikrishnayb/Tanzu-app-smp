package com.penske.apps.adminconsole.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.CostToleranceDao;
import com.penske.apps.adminconsole.domain.CostTolerance;
import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.Manufacturer;
import com.penske.apps.smccore.base.exception.AppValidationException;

/**
 * This class is used for queries to the database for the Cost Sheet Tolerances
 * page in the Admin Console under the App Config tab.
 * 
 * @author Maxim.Timofeev
 */
@Service
public class CostToleranceService
{
	private final CostToleranceDao costToleranceDAO;

	@Autowired
	public CostToleranceService(CostToleranceDao costToleranceDAO)
	{
		this.costToleranceDAO = costToleranceDAO;
	}

	public List<CostTolerance> getAllTolerances() {
		return costToleranceDAO.getTolerances(null);
	}

	public CostTolerance getTolerance(Integer costToleranceId) {
		if(costToleranceId == null)
			return null;
		
		List<CostTolerance> tolerances = costToleranceDAO.getTolerances(costToleranceId);
		return tolerances == null || tolerances.isEmpty() ? null : tolerances.get(0);
	}

	@Transactional
	public CostTolerance addTolerance(PoCategoryType poCategory, String mfrCode, Integer poVendorNumber, BigDecimal tolerance)
	{
		CostTolerance costTolerance = new CostTolerance(poCategory, mfrCode, poVendorNumber, tolerance);
		
		try {
			costToleranceDAO.addTolerance(costTolerance);
		} catch (DuplicateKeyException ex) {
			throw new AppValidationException("Tolerance already exists for '" + costTolerance.getPoCategory().getPoCategoryName() + "' / '" +
				costTolerance.getMfrCode() + "'/' " + costTolerance.getPoVendorNumber() + "'");
		}
		
		return costTolerance;
	}

	@Transactional
	public void updateTolerance(CostTolerance costTolerance, BigDecimal newTolerance)
	{
		costTolerance.updateTolerance(newTolerance);
		
		costToleranceDAO.updateTolerance(costTolerance);
	}

	public void deleteTolerance(int costToleranceId) {
		costToleranceDAO.deleteTolerance(costToleranceId);
	}

	public List<Manufacturer> getVehicleMakeList() {
		return costToleranceDAO.getAllManufacturers();
	}
}
