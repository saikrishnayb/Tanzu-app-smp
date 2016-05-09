package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Template Management page in the Admin Console under the App Config tab.
 * 
 * @author Seth.Bauman 600143568
 */
public interface TemplateService {
	public String getAllTemplateNames();
	
	public List<SearchTemplate> getAllTemplates();
	
	public SearchTemplate getSearchTemplate(int templateId);
}
