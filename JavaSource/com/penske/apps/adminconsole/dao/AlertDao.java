package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface AlertDao {

	public List<Alert> getAllAlertsAndHeaders();
	
	public List<SearchTemplate> getAllTemplateNames();
	
	public int checkForTemplateId(@Param("alertId") int alertId, @Param("templateId") int templateId);
	
	public void modifyAlertHeader(Alert alert);

	public void modifyAlertDetail(Alert alert);
}
