package com.penske.apps.buildmatrix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;

public class ImportSlotsResults {

	private ProductionSlotsMaintenanceSummary summary;
	private List<BuildMatrixBodyPlant> bodyPlantList;
	private List<String> datesNotInYear;
	private List<String> plantsNotFound;
	
	public ImportSlotsResults(ProductionSlotsMaintenanceSummary summary, List<BuildMatrixBodyPlant> bodyPlantList, 
			List<String> datesNotInYear, Map<Integer, ImportSlotsHeader> plantsNotFound) {
		this.summary = summary;
		this.bodyPlantList = bodyPlantList;
		this.datesNotInYear = datesNotInYear;
		List<String> plantsNotFoundList = new ArrayList<>();
		for(ImportSlotsHeader header: plantsNotFound.values()) {
			plantsNotFoundList.add(header.getManufacturer() + " - " + header.getCity() + ", " + header.getState());
		}
		this.plantsNotFound = plantsNotFoundList;
	}

	public ProductionSlotsMaintenanceSummary getSummary() {
		return summary;
	}

	public List<String> getDatesNotInYear() {
		return datesNotInYear;
	}

	public List<String> getPlantsNotFound() {
		return plantsNotFound;
	}
	
	public List<BuildMatrixBodyPlant> getBodyPlantList() {
		return bodyPlantList;
	}

}
