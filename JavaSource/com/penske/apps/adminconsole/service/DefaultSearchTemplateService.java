package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.SearchTemplateDao;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;

/**
 * This class is used for queries to the database for the Template Management page in the Admin Console under the App Config tab.
 * 
 * @author Mark.Weaver 600144069
 */
@Service
public class DefaultSearchTemplateService implements SearchTemplateService {
	
	@Autowired
	private SearchTemplateDao searchTemplateDao;
	
	private static Logger logger = Logger.getLogger(DefaultSearchTemplateService.class);

	@Override
	public List<SearchTemplate> getAllSearchTemplates() {
		return searchTemplateDao.selectAllSearchTemplates();
	}

	@Override
	public SearchTemplate getSearchTemplate(int templateId) {
		if (templateId <= 0) {
			logger.error("Search Template ID was zero when attempting to open the edit search template modal.");
			
			return null;
		}
		
		return searchTemplateDao.selectSearchTemplate(templateId);
	}
	
	@Override
	public boolean doesSearchTemplateNameExist(String templateName, int templateId) {
		SearchTemplate searchTemplate = searchTemplateDao.selectSearchTemplateByName(templateName, templateId);
		
		if (searchTemplate == null) return false;
		
		return true;
	}


	@Override
	public void updateSearchTemplate(SearchTemplateForm searchTemplateForm) {
		if (searchTemplateForm == null) {
			logger.error("Search Template Form was null when attemping to update search template.");
			
			return;
		}
		
		if (searchTemplateForm.getTemplateId() <= 0) {
			logger.error("Search Template ID was zero when attempting to update the search template.");
		}
		
		searchTemplateDao.updateSearchTemplate(searchTemplateForm);
	}

}
