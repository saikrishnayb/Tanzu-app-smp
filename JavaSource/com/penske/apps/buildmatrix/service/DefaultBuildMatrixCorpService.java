package com.penske.apps.buildmatrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.buildmatrix.dao.BuildMatrixCorpDAO;
import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;

@Service
public class DefaultBuildMatrixCorpService implements BuildMatrixCorpService {

	@Autowired
	BuildMatrixCorpDAO buildMatrixCorpDAO;
	
	// ***** BUILD MATRIX WORKFLOW *****/
	
	// AVAILABLE CHASSIS //
	@Override
	public int getAvailableChasisCount() {
		return buildMatrixCorpDAO.getAvailableChassisCount();
	}
	
	@Override
	public AvailableChassisSummaryModel getAvailableChassis(List<String> excludedUnits) {
		List<AvailableChassis> availableChassis = buildMatrixCorpDAO.getAvailableChassis();
		return new AvailableChassisSummaryModel(availableChassis, excludedUnits);
	}
	
}
