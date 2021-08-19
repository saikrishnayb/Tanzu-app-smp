package com.penske.apps.suppliermgmt.service;

import java.util.List;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SmcTab;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.Tab;


/**
 * This interface is used for queries to the database for the home/dashboard page.
 * 
 * @author Seth.Bauman 600143568
 */
public interface HomeDashboardService {
	public List<Tab> selectTabs(User user);
	public List<AlertHeader> getAlerts(User user,SmcTab tab);
}
