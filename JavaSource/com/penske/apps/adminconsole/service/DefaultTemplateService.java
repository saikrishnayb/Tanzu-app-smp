package com.penske.apps.adminconsole.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.TemplateDao;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This class is used for queries to the database for the Template Management page in the Admin Console under the App Config tab.
 * 
 * @author Seth.Bauman 600143568
 */
@Service
public class DefaultTemplateService implements TemplateService {
	private static final String DELIMITER = "||";
	
	@Autowired
	private TemplateDao templateDao;

	@Override
	public String getAllTemplateNames() {
		StringBuilder sb = new StringBuilder();
		
		List<String> templateNames = templateDao.getAllTemplateNames();
		
		Iterator<String> templateIter = templateNames.iterator();
		
		while (templateIter.hasNext()) {
			sb.append(templateIter.next());
			
			if (templateIter.hasNext()) {
				sb.append(DELIMITER);
			}
		}
		
		return sb.toString();
	}
	
	@Override
	public List<SearchTemplate> getAllTemplates() {
		return templateDao.getAllTemplates();
	}

	@Override
	public SearchTemplate getSearchTemplate(int templateId) {
		SearchTemplate searchTemplate = templateDao.selectSearchTemplate(templateId);
		
		return searchTemplate;
	}
}
