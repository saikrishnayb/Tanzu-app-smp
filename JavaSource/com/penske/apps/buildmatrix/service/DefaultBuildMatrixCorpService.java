package com.penske.apps.buildmatrix.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.dao.BuildMatrixCorpDAO;
import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;
import com.penske.apps.smccore.base.util.UnitRangeBuilder;

@Service
public class DefaultBuildMatrixCorpService implements BuildMatrixCorpService {

	@Autowired
	BuildMatrixCorpDAO buildMatrixCorpDAO;
	
	@Override
	public int getAvailableChasisCount() {
		return buildMatrixCorpDAO.getAvailableChassisCount();
	}
	
	@Override
	public AvailableChassisSummaryModel getAvailableChassis(List<String> excludedUnits) {
		List<AvailableChassis> availableChassis = buildMatrixCorpDAO.getAvailableChassis();
		return new AvailableChassisSummaryModel(availableChassis, excludedUnits);
	}
	
	@Override
	public List<ManufacturerDetails> getManufacturersByType(PoCategoryType poCategoryType) {
		return buildMatrixCorpDAO.getManufacturersByType(poCategoryType);
	}
}
