package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.TermsAndConditions;

/**
 * Interface for the Terms and Conditions service layer.
 * 
 * @author mark.weaver 600144069
 *
 */
public interface TermsAndConditionsService {
	public List<TermsAndConditions> getAllTermsAndConditions();
	
	public String getTermsAndConditionsFrequency();
	
	public void updateTermsAndConditionsFrequency(String frequency);
	
	public String getTermsAndConditionsText(int versionNumber);
}
