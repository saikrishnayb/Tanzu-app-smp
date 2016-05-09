package com.penske.apps.suppliermgmt.model;

import java.util.List;

/**
 * The Alert Header groups different Alerts into categories to be displayed on the dashboard screen. Alert Headers
 * belong to a tab that, when clicked, displays the necessary Alert Headers and Alerts on the dashboard.
 * 
 * @author Seth.Bauman 600143568
 */
public class AlertHeader {
	private int headerId;			// the header ID
	private String headerName;		// the header name
	private int tabId;				// the header tab ID
	private String helpText;		// the header help text
	private List<Alert> alerts;		// the list of alerts for the header

	// Getters
	public int getHeaderId() {
		return headerId;
	}

	public String getHeaderName() {
		return headerName;
	}

	public int getTabId() {
		return tabId;
	}

	public String getHelpText() {
		return helpText;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	// Setters
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
}
