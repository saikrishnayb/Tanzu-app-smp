package com.penske.apps.adminconsole.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.TabDao;
import com.penske.apps.adminconsole.model.SearchTemplateForm;

@Service
public class DefaultTabService implements TabService {
	
	@Autowired
	public TabDao tabDao;
	
	private static Logger logger = LogManager.getLogger(DefaultTabService.class);

	@Override
	public void updateDefaultTemplate(SearchTemplateForm searchTemplateForm) {
		if (searchTemplateForm == null) {
			logger.error("Search Template Form was null when attempting to update if it is the default for the tab.");
			
			return;
		}
		
		if (searchTemplateForm.getTemplateId() <= 0) {
			logger.error("Search Template ID was zero when attempting to update a tab's default template.");
			
			return;
		}
		
		if (searchTemplateForm.getTabId() <= 0) {
			logger.error("Tab ID was zero when attempting to update the tab's default template.");
			
			return;
		}
		
		tabDao.updateDefaultTemplate(searchTemplateForm);
	}
}
