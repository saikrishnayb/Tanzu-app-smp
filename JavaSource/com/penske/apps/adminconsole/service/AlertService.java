package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
public interface AlertService {

	public List<Alert> getAllAlertsAndHeaders();
	
	public List<SearchTemplate> getAllTemplateNames();

	public void modifyAlertHeader(Alert alert);

	public void modifyAlertDetail(Alert alert);
	
	public boolean checkForTemplateAssociation(int alertId, int templateId);
}
