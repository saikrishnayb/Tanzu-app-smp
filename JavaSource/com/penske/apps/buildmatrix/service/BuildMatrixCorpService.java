package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;

public interface BuildMatrixCorpService {
	
	public int getAvailableChasisCount();

	public List<ManufacturerDetails> getManufacturersByType(PoCategoryType poCategoryType);
}
