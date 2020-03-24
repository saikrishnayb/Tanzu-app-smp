package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.BodyPlantBuildHistory;

public interface BodyPlantBuildHistoryService {

	public List<BodyPlantBuildHistory> getAllBuildHistory();

	public List<String> getAllbuildStatusTerms();
	
	public int startNewBuild();

}
