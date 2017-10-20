package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface AlertDao {

	@NonVendorQuery //TODO: Review Query
	public List<Alert> getAllAlertsAndHeaders();
	
	@NonVendorQuery //TODO: Review Query
	public List<SearchTemplate> getAllTemplateNames();
	
	@NonVendorQuery //TODO: Review Query
	public int checkForTemplateId(@Param("alertId") int alertId, @Param("templateId") int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyAlertHeader(Alert alert);

	@NonVendorQuery //TODO: Review Query
	public void modifyAlertDetail(Alert alert);
}
