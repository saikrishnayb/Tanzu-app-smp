package com.penske.apps.adminconsole.service;

import java.util.List;




import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibilityModel;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequence;
import com.penske.apps.adminconsole.model.RuleMaster;

public interface LoadSheetManagementService {
	
	public List<ComponentVisibilityModel> getLoadsheetComponents(String categoryId);
	public List<LoadsheetSequence> getLoadsheetSequences(String category,String type);
	public List<RuleMaster> getComponentRules(int componentId);
	public List<ConfigureRule> getComponentVisibilityRules(int componentVisibleId);
	public List<LoadsheetManagement> getLoadsheetManagementDetails();
	public List<RuleMaster> getLoadsheetRuleDetails();
	public void saveComponentRules(ComponentRuleAssociation componentRule);
	public List<LoadSheetComponentDetails> getComponents();
	public void createNewRule(RuleMaster rule);
	public RuleMaster getRuleDetails(String ruleId);
	
}
