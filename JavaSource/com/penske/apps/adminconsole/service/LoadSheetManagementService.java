package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibilityModel;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequenceMaster;
import com.penske.apps.adminconsole.model.RuleMaster;

public interface LoadSheetManagementService {
	
	public List<ComponentVisibilityModel> getLoadsheetComponents(String category,String type);
	public List<LoadsheetSequenceMaster> getLoadsheetSequences(String category,String type);
	public String getUsesDefaultForCategoryAndType(String category,String type);
	public List<LoadsheetSequenceMaster> getLoadsheetSequences();
	public List<RuleMaster> getComponentRules();
	public List<ConfigureRule> getComponentVisibilityRules(int componentVisibleId);
	public List<LoadsheetManagement> getLoadsheetManagementDetails();
	public List<RuleMaster> getLoadsheetRuleDetails();
	public boolean checkForUniqueRuleName(String newRuleName,int ruleId);
	public void saveComponentRules(ComponentRuleAssociation componentRule);
	public List<LoadSheetComponentDetails> getComponents();
	public void createNewRule(RuleMaster rule);
	public void updateRuleDetails(RuleMaster rule);
	public RuleMaster getRuleDetails(int ruleId);
	public void DeleteRuleDetails(int ruleId);
	public List<LoadsheetManagement> getAssignedLoadsheetCategories(int ruleId);
	public List<String> getCategoryList();
	public List<String> getTypeList(String category);
	public List<String>  getMfrList();
	public List<LoadSheetComponentDetails> getUnAssignedComponents(LoadsheetSequenceMaster seqMaster);
	public void createLoadSheetSequencing(LoadsheetSequenceMaster seqMaster);
	public LoadsheetSequenceMaster getSequenceMasterDetails(int seqMasterId);
	public void updateLoadsheetSequencingDetails(LoadsheetSequenceMaster seqMaster);
	public void deleteLoadsheetSequence(int sequenceId);
	public boolean checkForUniqueSequenceName(String newName,int seqId);
	public int checkForUniqueSequence(String catgeory, String type,String mfr,int seqId);
	
}
