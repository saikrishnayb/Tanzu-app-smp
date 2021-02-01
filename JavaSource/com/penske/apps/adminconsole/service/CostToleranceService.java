package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.CostTolerance;
import com.penske.apps.adminconsole.model.Manufacturer;

/**
 * This interface is used for queries to the database for the Cost Sheet
 * Tolerances page in the Admin Console under the App Config tab.
 * 
 * @author Maxim.Timofeev
 */
public interface CostToleranceService {

	public List<CostTolerance> getAllTolerances();

	public CostTolerance getTolerance(int costToleranceId);

	public void addTolerance(CostTolerance costTolerance);

	public void updateTolerance(CostTolerance costTolerance);

	public void deleteTolerance(int costToleranceId);

	public List<Manufacturer> getVehicleMakeList();
}
