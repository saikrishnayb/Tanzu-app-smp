package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.BodyPlantBuildHistoryDao;
import com.penske.apps.adminconsole.model.BodyPlantBuildHistory;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBodyPlantBuildHistoryService implements BodyPlantBuildHistoryService {

	@Autowired
	BodyPlantBuildHistoryDao bodyPlantBuildHistoryDao;
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	static List<BodyPlantBuildHistory> bodyPlantBuildHistory = new ArrayList<BodyPlantBuildHistory>();
	static List<String> buildStatusTerms = new ArrayList<String>();

	@Override
	public List<BodyPlantBuildHistory> getAllBuildHistory() {
		List<BodyPlantBuildHistory> buildHistory = bodyPlantBuildHistoryDao.getAllBuildHistory();
		return buildHistory;
	}

	@Override
	public List<String> getAllbuildStatusTerms() {
		List<String> buildStatusTerms = getAllBuildStatusTermsMockService();
		//List<String> buildStatusTerms = bodyPlantBuildHistoryDao.getAllbuildStatusTerms();
		return buildStatusTerms;
	}

	// Mock service methods
/*	private List<BodyPlantBuildHistory> getAllBuildHistoryMockService() {
		bodyPlantBuildHistory.clear();
		try {
			BodyPlantBuildHistory buildHistory1 = new BodyPlantBuildHistory(5,new SimpleDateFormat("MM/dd/yyyy").parse("05/13/2020"), "Jeff Kulikowski", 222, "Submitted");
			BodyPlantBuildHistory buildHistory2 = new BodyPlantBuildHistory(4,new SimpleDateFormat("MM/dd/yyyy").parse("05/04/2020"), "Jeff Kulikowski", 81, "Running");
			BodyPlantBuildHistory buildHistory3 = new BodyPlantBuildHistory(3,new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2020"), "Jeff Kulikowski", 60, "Completed");
			BodyPlantBuildHistory buildHistory4 = new BodyPlantBuildHistory(2,new SimpleDateFormat("MM/dd/yyyy").parse("04/28/2020"), "Jeff Kulikowski", 140,"Completed with Exceptions");
			BodyPlantBuildHistory buildHistory5 = new BodyPlantBuildHistory(1,new SimpleDateFormat("MM/dd/yyyy").parse("04/15/2020"), "Jeff Kulikowski", 225, "Error");
			bodyPlantBuildHistory.add(buildHistory1);
			bodyPlantBuildHistory.add(buildHistory2);
			bodyPlantBuildHistory.add(buildHistory3);
			bodyPlantBuildHistory.add(buildHistory4);
			bodyPlantBuildHistory.add(buildHistory5);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return bodyPlantBuildHistory;
	}*/

	private List<String> getAllBuildStatusTermsMockService() {
		buildStatusTerms.clear();

		buildStatusTerms.add("Started - The run has been created but not yet submitted for processing");
		buildStatusTerms.add("Submitted - Build was submitted and waiting to be picked up by the reservation rules engine");
		buildStatusTerms.add("Running   - Reservation engine is working to generate a build plan based on the OEM mix,Plant Rules,Dates,and District proximity");
		buildStatusTerms.add("Completed - Build has run successfully and all requests have been fulfilled");
		buildStatusTerms.add("Completed with Exceptions - Build has run successfully and a portion of the requests could not be fulfilled");
		buildStatusTerms.add("Error - A technical / IT error occured during the build process - We don't anticipate this to be a common outcome but are calling out any possible outcomes on this page");
		return buildStatusTerms;
	}
	
	@Override
	public int startNewBuild()
	{
		UserContext user = sessionBean.getUserContext();
		BodyPlantBuildHistory newBuild=new BodyPlantBuildHistory();
		return bodyPlantBuildHistoryDao.insertNewBuild(newBuild,user);
	}
}
