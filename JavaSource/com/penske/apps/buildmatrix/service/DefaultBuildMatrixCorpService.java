package com.penske.apps.buildmatrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.dao.BuildMatrixCorpDAO;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;

@Service
public class DefaultBuildMatrixCorpService implements BuildMatrixCorpService {

	@Autowired
	BuildMatrixCorpDAO buildMatrixCorpDAO;
	
	@Override
	public int getAvailableChasisCount() {
		return buildMatrixCorpDAO.getAvailableChassisCount();
	}
	
	@Override
	public List<ManufacturerDetails> getManufacturersByType(PoCategoryType poCategoryType) {
		return buildMatrixCorpDAO.getManufacturersByType(poCategoryType);
	}
}
