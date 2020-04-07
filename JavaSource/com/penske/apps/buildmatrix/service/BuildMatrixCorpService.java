package com.penske.apps.buildmatrix.service;

import java.util.List;
import java.util.Set;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;

public interface BuildMatrixCorpService {
	
	public int getAvailableChasisCount();
	
	public List<ManufacturerDetails> getManufacturersByType(PoCategoryType poCategoryType);

	public AvailableChassisSummaryModel getAvailableChassis(List<String> excludedUnits);
	
	

}
