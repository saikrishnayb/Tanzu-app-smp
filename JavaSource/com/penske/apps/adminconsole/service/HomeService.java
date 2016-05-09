package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Tab;

/**
 * This interface is used for queries to the database for the home/dashboard page.
 * 
 * @author Seth.Bauman 600143568
 */
public interface HomeService {
	public List<Tab> selectTabs();
}
