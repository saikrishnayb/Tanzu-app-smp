package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.ProductionSlotsMaintenance;

public interface BodyPlantMaintSummaryService {
	
	public List<ProductionSlotsMaintenance> getMaintenanceSummary();
}
