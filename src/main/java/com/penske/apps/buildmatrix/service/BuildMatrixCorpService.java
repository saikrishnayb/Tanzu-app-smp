package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;

public interface BuildMatrixCorpService {
	
	// ***** BUILD MATRIX WORKFLOW *****/
	
	// AVAILABLE CHASSIS //
	public int getAvailableChasisCount();
	
	public AvailableChassisSummaryModel getAvailableChassis(List<String> excludedUnits);
	
}
