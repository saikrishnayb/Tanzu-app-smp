package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;

/**
 * This interface is used for queries to the database for the Template Management page in the Admin Console under the App Config tab.
 * 
 * @author Mark.Weaver 600144069
 */
public interface SearchTemplateService {
	public List<SearchTemplate> getAllSearchTemplates();
	public SearchTemplate getSearchTemplate(int templateId);
	public boolean doesSearchTemplateNameExist(String templateName, int templateId);
	public void updateSearchTemplate(SearchTemplateForm searchTemplateForm);
}
