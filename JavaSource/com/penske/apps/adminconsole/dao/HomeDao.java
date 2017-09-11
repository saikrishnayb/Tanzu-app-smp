package com.penske.apps.adminconsole.dao;

import java.util.List;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.AlertHeader;
import com.penske.apps.adminconsole.model.Tab;

/**
 * This interface is used for queries to the database for the home/dashboard page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface HomeDao {

	@NonVendorQuery //TODO: Review Query
	public List<Tab> selectTabs();

	@NonVendorQuery //TODO: Review Query
	public List<AlertHeader> selectHeaders(int tabId);

	@NonVendorQuery //TODO: Review Query
	public List<Alert> selectAlerts(int headerId);
}
