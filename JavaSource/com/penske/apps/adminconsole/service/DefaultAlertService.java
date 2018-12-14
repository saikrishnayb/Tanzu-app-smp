package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.AlertDao;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This class is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
@Service
public class DefaultAlertService implements AlertService {
	@Autowired
	private AlertDao alertDao;
	
	@Override
	public List<Alert> getAllAlertsAndHeaders() {
		return alertDao.getAllAlertsAndHeaders();
	}

	@Override
	public List<SearchTemplate> getAllTemplateNames() {
		return alertDao.getAllTemplateNames();
	}
	
	@Override
	public void modifyAlertHeader(Alert alert) {
		// The header name cannot be null or blank.
		if (StringUtils.isEmpty(alert.getHeaderName())) {
			return;
		}
		
		alertDao.modifyAlertHeader(alert);
	}
	
	@Override
	public void modifyAlertDetail(Alert alert) {

		// The alert name cannot be null or blank.
		if (StringUtils.isEmpty(alert.getAlertName())) {
			return;
		}
		// The template ID cannot be negative.
		else if (alert.getTemplateId() <= 0) {
			return;
		}
		// The visibility can only be 1, 2, or 3.
		else if (alert.getVisibility() < 1 || alert.getVisibility() > 3) {
			return;
		}
		else if (alertDao.checkForTemplateId(alert.getAlertId(), alert.getTemplateId()) == 1) {
			return;
		}
		
		// Set the visibility for Penske users and vendors.
		// 1 = Visible to Vendors Only
		if (alert.getVisibility() == 1) {
			alert.setVisibilityVendor(1);
			alert.setVisibilityPenske(0);
		}
		// 2 = Visible to Penske Users Only
		else if (alert.getVisibility() == 2) {
			alert.setVisibilityVendor(0);
			alert.setVisibilityPenske(1);
		}
		// 3 = Visible to Both
		else if (alert.getVisibility() == 3) {
			alert.setVisibilityVendor(1);
			alert.setVisibilityPenske(1);
		}

		alertDao.modifyAlertDetail(alert);
	}
}
