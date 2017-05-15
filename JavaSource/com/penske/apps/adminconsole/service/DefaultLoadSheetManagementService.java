package com.penske.apps.adminconsole.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.LoadsheetManagementDao;
import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibilityModel;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequence;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultLoadSheetManagementService implements LoadSheetManagementService {
	
	@Autowired
    private LoadsheetManagementDao loadsheetManagementDao;
	@Autowired
    private HttpSession httpSession;

	@Override
	public List<ComponentVisibilityModel> getLoadsheetComponents(String categoryId) {
		return loadsheetManagementDao.getLoadsheetComponents(categoryId);
		
	}
	
	@Override
	public List<LoadsheetSequence> getLoadsheetSequences(String category,String type) {
		return loadsheetManagementDao.getLoadsheetSequences(category,type);
	}
	
	@Override
	public List<LoadsheetManagement> getLoadsheetManagementDetails(){
		
		return loadsheetManagementDao.getLoadsheetManagementDetails();
	}
	
	@Override
	public List<RuleMaster> getLoadsheetRuleDetails(){
		
		return loadsheetManagementDao.getLoadsheetRules();
		
		
	}
	@Override
	public List<RuleMaster> getComponentRules(int componentId) {
		return loadsheetManagementDao.getComponentRules(componentId);
	}

	@Override
	public List<ConfigureRule> getComponentVisibilityRules(int componentVisibleId) {
		return loadsheetManagementDao.getComponentVisibilityRules(componentVisibleId);
	}

	@Transactional
	@Override
	public void saveComponentRules(ComponentRuleAssociation componentRule) {
		loadsheetManagementDao.deleteComponentVisibilityRules(componentRule.getComponentVisibilityId());
		List<ConfigureRule> configureRules= new ArrayList<ConfigureRule>();
		// to remove the null rules which are deleted from the UI using delete icon.
		for(ConfigureRule rule:componentRule.getRule()){
			if(rule.getRuleId()!=0 && rule.getPriority()!=null){
				configureRules.add(rule);
			}
		}
		componentRule.setRule(configureRules);
		loadsheetManagementDao.saveComponentVisibilityRules(componentRule);
		
	}
	@Override
	public List<LoadSheetComponentDetails> getComponents() {
		
		return loadsheetManagementDao.getComponents();
	}
	/**
	 * Method to create new Rule
	 * First inserts the data into rule master table and then inserts the list of Rule definitions
	 */
	@Override
	@Transactional
	public void createNewRule(RuleMaster rule) {
		
		UserContext user = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);
		String[] componentValue;
		Set<Integer> criteraiGrpValues=new HashSet<Integer>();
		RuleDefinitions ruleDef;
		int criteriaGrpCount=0;
		
		loadsheetManagementDao.insertRuleMasterDetails(rule, user);
		
		List<RuleDefinitions> rulDefList=rule.getRuleDefinitionsList();
		//sort list based on criteria group count
		if (rulDefList.size() > 0) {
			  Collections.sort(rulDefList, new Comparator<RuleDefinitions>() {
			      @Override
			      public int compare(final RuleDefinitions rd1, final RuleDefinitions rd2) {
			    	  return rd1.getCriteriaGroup()>rd2.getCriteriaGroup()?+1
			    			 :rd1.getCriteriaGroup()<rd2.getCriteriaGroup()?-1:0;
			      }
			  });
			}
		Iterator<RuleDefinitions> ruleDefIt = rulDefList.iterator();
		//Extract Component Id from component dropdown value and set rule id
		while (ruleDefIt.hasNext()) {
			
			ruleDef=ruleDefIt.next();
			
			if(ruleDef.getCriteriaGroup() != 0){	//if the array is not empty
				componentValue=ruleDef.getComponentId().split("-");
				ruleDef.setComponentId(componentValue[0]);
				ruleDef.setComponentType(componentValue[1]);
				ruleDef.setRuleId(rule.getRuleId());
				if(criteraiGrpValues.add(ruleDef.getCriteriaGroup())){
					//new Group so increase the criteriaCount and reset the value
					criteriaGrpCount++;
					ruleDef.setCriteriaGroup(criteriaGrpCount);
				}else{//same old group
					ruleDef.setCriteriaGroup(criteriaGrpCount);
				}
			}else{
				ruleDefIt.remove();	//remove empty values
			}
			
		}
		
		loadsheetManagementDao.insertRuleDefinitions(rule.getRuleDefinitionsList(), user);
		
	}

	@Override
	public RuleMaster getRuleDetails(String ruleId) {
		
		Set<Integer> criteraiGrpValues=new HashSet<Integer>();
		
		RuleMaster ruleMaster=loadsheetManagementDao.getRuleDetails(ruleId);
		
		//forming the value for component dropdown & setting the isGroupHeader Value
		for(RuleDefinitions ruleDef:ruleMaster.getRuleDefinitionsList()){
			ruleDef.setComponentId(ruleDef.getComponentId()+"-"+ruleDef.getComponentType());
			if(criteraiGrpValues.add(ruleDef.getCriteriaGroup())){
				ruleDef.setIsGroupHeader(true);
			}else{
				ruleDef.setIsGroupHeader(false);
			}
		}
		
		
		
		return ruleMaster;
	}

	


}
