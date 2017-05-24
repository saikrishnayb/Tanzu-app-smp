package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibilityModel;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequence;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface LoadsheetManagementDao {
	
	public List<ComponentVisibilityModel> getLoadsheetComponents(@Param("categoryId") String categoryId);
	public List<LoadsheetSequence> getLoadsheetSequences(@Param("category") String category,@Param("type") String type);
	public List<LoadsheetManagement> getLoadsheetManagementDetails();
	public List<RuleMaster> getLoadsheetRules();
	public List<RuleMaster> getComponentRules(@Param("componetId")int componetId);
	public List<ConfigureRule> getComponentVisibilityRules(@Param("componentVisibleId")int componentVisibleId);
	public void deleteComponentVisibilityRules(@Param("componentVisibilityId")int componentVisibilityId);
	public void saveComponentVisibilityRules(ComponentRuleAssociation componentRule);
	public List<LoadSheetComponentDetails> getComponents();
	public void insertRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	public void insertRuleDefinitions(@Param("ruleDefList") List<RuleDefinitions> ruleDefList,@Param("user") UserContext user);
	public void updateRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	public void updateRuleDefinitions(@Param("ruleDef") RuleDefinitions ruleDef,@Param("user") UserContext user);
	public RuleMaster getRuleDetails(@Param("ruleId") int ruleId);
	public void deleteRuleDefinitions(@Param("ruleDefIdList") List<Integer> ruleDefIdList);
	public void deleteRuleMasterDetails(@Param("ruleId") int ruleId);
	public void deleteRuleDefDetails(@Param("ruleId") int ruleId);
	public List<LoadsheetManagement> getAssignedLoadsheetCategories(@Param("ruleId") int ruleId);
}
