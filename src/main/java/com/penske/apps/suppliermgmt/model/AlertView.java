package com.penske.apps.suppliermgmt.model;

import com.penske.apps.smccore.search.domain.SmcAlert;

public class AlertView {
	private SmcAlert alert;
	private int alertCount;
	private String alertLink;
	
	public AlertView(SmcAlert alert, int alertCount, String alertLink) {
		super();
		this.alert = alert;
		this.alertCount = alertCount;
		this.alertLink = alertLink;
	}
	
	public SmcAlert getAlert() {
		return alert;
	}
	
	public int getAlertCount() {
		return alertCount;
	}
	
	public String getAlertLink() {
		return alertLink;
	}
}
