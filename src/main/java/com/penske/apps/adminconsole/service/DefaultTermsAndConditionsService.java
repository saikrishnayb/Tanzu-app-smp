package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.GlobalSettingsDao;
import com.penske.apps.adminconsole.dao.TermsAndConditionsDao;
import com.penske.apps.adminconsole.model.TermsAndConditions;
import com.penske.apps.adminconsole.util.ApplicationConstants;


/**
 * Implementation of TermsAndConditionsService.java. Used for accessing terms and conditions information.
 * 
 * @author mark.weaver 600144069
 *
 */
@Service
public class DefaultTermsAndConditionsService implements TermsAndConditionsService {

	//Member variable declarations
	@Autowired
	private TermsAndConditionsDao termsAndConditionsDao;
	
	@Autowired
	private GlobalSettingsDao globalSettingsDao;
	
	private static Logger logger = Logger.getLogger(DefaultTermsAndConditionsService.class);
	
	//Returns list of all terms and conditions
	@Override
	public List<TermsAndConditions> getAllTermsAndConditions() {
		return termsAndConditionsDao.selectAllTermsAndConditions();
	}

	//Returns the frequency with which users are prompted to confirm the terms and conditions
	@Override
	public String getTermsAndConditionsFrequency() {
		return globalSettingsDao.selectGlobalSettingsValueByKeyName(ApplicationConstants.TERMS_AND_CONDITIONS_DAYS);
	}

	//Updates the frequency with which users are prompted to confirm the terms and conditions
	@Override
	public void updateTermsAndConditionsFrequency(String frequency) {
		try {
			Integer.parseInt(frequency);
		} catch (NumberFormatException e) {
			logger.error("Terms and Conditions frequency value was not an integer when attempting to update this value in the global settings.",e);
			
			return;
		}
		
		globalSettingsDao.updateGlobalSettingsByKeyName(frequency, ApplicationConstants.TERMS_AND_CONDITIONS_DAYS);
	}

	//Gets the text of that version of the terms and conditions for display
	@Override
	public String getTermsAndConditionsText(int versionNumber) {
		if (versionNumber <= 0) {
			logger.error("Version number was less than or equal to zero when attempting to view terms and conditions.");
		}
		
		return termsAndConditionsDao.selectTermsAndConditionsText(versionNumber);
	}

}
