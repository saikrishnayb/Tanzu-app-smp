package com.penske.apps.buildmatrix.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary.ProductionSlotsUtilizationCell;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary.ProductionSlotsUtilizationRow;

public class ImportRegionSlotsResults {

	private ProductionSlotsUtilizationSummary summary;
	private List<BuildMatrixBodyPlant> bodyPlantList;
	private List<String> datesNotInYear;
	private List<String> plantsNotFound;
	
	public ImportRegionSlotsResults(ProductionSlotsUtilizationSummary summary, List<BuildMatrixBodyPlant> bodyPlantList, 
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
	
	public List<Pair<BuildMatrixSlotDate, ProductionSlotsUtilizationCell>> getInvalidSlots() {
		List<Pair<BuildMatrixSlotDate, ProductionSlotsUtilizationCell>> invalidSlots = new ArrayList<>();
		for(ProductionSlotsUtilizationRow row: summary.getRows()) {
			for(ProductionSlotsUtilizationCell cell: row.getCells()) {
				if(cell.getRegionAvailability().isInvalidSlot())
					invalidSlots.add(Pair.of(row.getSlotDate(), cell));
			}
		}
		return invalidSlots;
	}

	public ProductionSlotsUtilizationSummary getSummary() {
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
