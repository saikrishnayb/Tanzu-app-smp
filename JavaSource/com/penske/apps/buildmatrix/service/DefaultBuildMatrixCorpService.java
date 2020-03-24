package com.penske.apps.buildmatrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.buildmatrix.dao.BuildMatrixCorpDAO;

@Service
public class DefaultBuildMatrixCorpService implements BuildMatrixCorpService {

	@Autowired
	BuildMatrixCorpDAO buildMatrixCorpDAO;
	
	@Override
	public int getAvailableChasisCount() {
		return buildMatrixCorpDAO.getAvailableChassisCount();
	}
}
