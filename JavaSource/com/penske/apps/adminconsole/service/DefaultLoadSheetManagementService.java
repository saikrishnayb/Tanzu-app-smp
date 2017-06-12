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
import com.penske.apps.adminconsole.model.LoadsheetCompGrpSeq;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequenceGroupMaster;
import com.penske.apps.adminconsole.model.LoadsheetSequenceMaster;
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
	public List<ComponentVisibilityModel> getLoadsheetComponents(String category,String type) {
		return loadsheetManagementDao.getLoadsheetComponents(category,type);
		
	}
	
	@Override
	public List<LoadsheetSequenceMaster> getLoadsheetSequences(String category,String type) {
		return loadsheetManagementDao.getLoadsheetSequences(category,type);
	}
	@Override
	public List<LoadsheetSequenceMaster> getLoadsheetSequences() {
		return loadsheetManagementDao.getLoadsheetSequence();
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
	public List<RuleMaster> getComponentRules() {
		return loadsheetManagementDao.getComponentRules();
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
		if(componentRule.getRule()!=null && componentRule.getRule().size()>0){
		// to remove the null rules which are deleted from the UI using delete icon.
		  for(ConfigureRule rule:componentRule.getRule()){
			if(rule.getRuleId()!=0 && rule.getPriority()!=null && rule.getLsOverride()!=null){
				configureRules.add(rule);
			}
		  }
		}
		if(configureRules.size()>0){
		componentRule.setRule(configureRules);
		loadsheetManagementDao.saveComponentVisibilityRules(componentRule);
		}
		
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
		
		
		loadsheetManagementDao.insertRuleMasterDetails(rule, user);
		
		populateRuleMaster(rule);
		
		loadsheetManagementDao.insertRuleDefinitions(rule.getRuleDefinitionsList(), user);
		
	}
	//to Update the rule master and rule definitions
	@Override
	@Transactional
	public void updateRuleDetails(RuleMaster rule) {
		UserContext user = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);
		List<RuleDefinitions> newRuleDefList=new ArrayList<RuleDefinitions> ();
		loadsheetManagementDao.updateRuleMasterDetails(rule, user);
		
		populateRuleMaster(rule);
		for(RuleDefinitions ruleDef:rule.getRuleDefinitionsList()){
			if(ruleDef.getRuleDefId() != 0){
				loadsheetManagementDao.updateRuleDefinitions(ruleDef, user);
			}else{
				//New Rules Definitions 
				newRuleDefList.add(ruleDef);
			}
		}
		//Insert the new RuleDefinitions
		if(newRuleDefList.size()>0){
			loadsheetManagementDao.insertRuleDefinitions(newRuleDefList, user);
		}
		//Delete the deleted rule definitions
		if(rule.getDeletedRuleDefIds().size()>0){
			loadsheetManagementDao.deleteRuleDefinitions(rule.getDeletedRuleDefIds());
		}
		
		formTheComponentDropdownValue(rule);
	}
	
	/**
	 * Method to form the RuleMaster Object for insert/update actions
	 * @param rule
	 * @return rule
	 */
	private RuleMaster populateRuleMaster(RuleMaster rule){
		
		String[] componentValue;
		Set<Integer> criteraiGrpValues=new HashSet<Integer>();
		RuleDefinitions ruleDef;
		int criteriaGrpCount=0;
		
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
		
		rule=getOperandsDropdownValues(rule);
		
		return rule;
	}

	/**
	 * Method to get the rule details On click of Edit
	 */
	@Override
	public RuleMaster getRuleDetails(int ruleId) {
		
		RuleMaster ruleMaster=loadsheetManagementDao.getRuleDetails(ruleId);
		
		ruleMaster=formTheComponentDropdownValue(ruleMaster);
		ruleMaster=getOperandsDropdownValues(ruleMaster);
		return ruleMaster;
	}
	
	/**
	 * Method to form the component drop down values in create rule page
	 * @param rule
	 * @return
	 */
	private RuleMaster formTheComponentDropdownValue(RuleMaster ruleMaster){
		
		//sort list based on criteria group count
		if (ruleMaster.getRuleDefinitionsList().size() > 0) {
			  Collections.sort(ruleMaster.getRuleDefinitionsList(), new Comparator<RuleDefinitions>() {
			      @Override
			      public int compare(final RuleDefinitions rd1, final RuleDefinitions rd2) {
			    	  return rd1.getCriteriaGroup()>rd2.getCriteriaGroup()?+1
			    			 :rd1.getCriteriaGroup()<rd2.getCriteriaGroup()?-1:0;
			      }
			  });
			}
		
		Set<Integer> criteraiGrpValues=new HashSet<Integer>();
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
	
	/**
	 * method to populate operands dropdown during Edit functionality
	 * @param ruleMaster
	 * @return
	 */
	private RuleMaster getOperandsDropdownValues(RuleMaster ruleMaster){
		List<String> operandsList;
		
		for(RuleDefinitions ruleDef:ruleMaster.getRuleDefinitionsList()){
			
			//populate operands list for the particular ruledef
			operandsList=new ArrayList<String>();
			if(!ruleDef.getComponentType().equals("T")){
				operandsList.add("<");
				operandsList.add("<=");
				operandsList.add("=");
				operandsList.add(">");
				operandsList.add(">=");
			}else{
				operandsList.add("=");
			}
			ruleDef.setOperandsList(operandsList);
		}
				
		return ruleMaster;
	}
	
	/**
	 * Method to Delete the rule details based on rule Id
	 */
	@Override
	@Transactional
	public void DeleteRuleDetails(int ruleId) {
		
		loadsheetManagementDao.deleteRuleAssociation(ruleId);
		loadsheetManagementDao.deleteRuleMasterDetails(ruleId);
		loadsheetManagementDao.deleteRuleDefDetails(ruleId);
	}
	/**
	 * Method to get loadsheet categories list in loadsheet sequence screen.
	 */
	@Override
	public List<String> getCategoryList() {
		return loadsheetManagementDao.getCategoryList();
	}
	/**
	 * Method to get loadsheet types list in loadsheet sequence screen.
	 */
	@Override
	public List<String> getTypeList() {
		return loadsheetManagementDao.getTypeList();
	}
	
	/**
	 * Method to get MFR list in loadsheet sequence screen.
	 */
	@Override
	public List<String>  getMfrList(){
		return loadsheetManagementDao.getMfrList();
	}

	/**
	 * Method to get Unassigned components for Loadsheet sequencing based on category and Type
	 */
	@Override
	public List<LoadSheetComponentDetails> getUnAssignedComponents(LoadsheetSequenceMaster seqMaster) {
		
		//Remove the Assigned components from un assigned components
		List<LoadSheetComponentDetails> allComponents=loadsheetManagementDao.getUnAssignedComponents(seqMaster.getCategory(), seqMaster.getType());
		
		if(seqMaster.getId() != 0){
			for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
				allComponents.removeAll(grpMaster.getCompGrpSeqList());
			}
		}

		return allComponents;
	}
	
	/**
	 * Method to insert loadsheet sequencing details 
	 */
	@Override
	@Transactional
	public void createLoadSheetSequencing(LoadsheetSequenceMaster seqMaster) {
			
		UserContext user = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);
		List<LoadsheetCompGrpSeq> cmpGrpSeqList=null;
		LoadsheetCompGrpSeq cmpGrpSeq;
		
		loadsheetManagementDao.insertSeqMasterDetails(seqMaster,user);
		for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
			grpMaster.setSeqMasterId(seqMaster.getId());
				if(grpMaster.getDisplaySeq()!=0){
					loadsheetManagementDao.insertGrpMasterDetails(grpMaster,user);
					
					
					cmpGrpSeqList=grpMaster.getCompGrpSeqList();
					
					//removing the empty lists from cmpGrpSeqList
					Iterator<LoadsheetCompGrpSeq> cmpGrpSeqIt = cmpGrpSeqList.iterator();
					
					while (cmpGrpSeqIt.hasNext()) {
						
						cmpGrpSeq=cmpGrpSeqIt.next();
						if(cmpGrpSeq.getDisplaySeq()!=0){
							cmpGrpSeq.setGrpMasterId(grpMaster.getGrpMasterId());
						}else{
							cmpGrpSeqIt.remove();	//remove empty values
						}
						
					}
					
					loadsheetManagementDao.insertCmpGrpSeqDetails(cmpGrpSeqList,user);
				}
		}
		
	}

	/**
	 * Method get the laodsheet sequencing details based on sequence id
	 */
	@Override
	public LoadsheetSequenceMaster getSequenceMasterDetails(int seqMasterId) {
		
		return loadsheetManagementDao.getSequenceMasterDetails(seqMasterId);
	}

	/**
	 * Method to update loadsheet sequencing details
	 */
	@Override
	@Transactional
	public void updateLoadsheetSequencingDetails(LoadsheetSequenceMaster seqMaster) {
		
		UserContext user = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);
		List<LoadsheetCompGrpSeq> newCompGrpSeqList=null;
		List<Integer> existingCompIds=null; //list for deleting the deleted components using NOT IN
		List<Integer> existingGroupIds=new ArrayList<Integer>();
		
		if(seqMaster.getId()!=0){
			//update sequence master details
			loadsheetManagementDao.updateSeqMasterDetails(seqMaster, user);
			
			//Delete the deleted groups
			for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
				if(grpMaster.getDisplaySeq()!=0){
					if(grpMaster.getGrpMasterId()!=0){
						existingGroupIds.add(grpMaster.getGrpMasterId());
					}
				}
			}
			
			//Delete the deleted Groups and its associated Components
			if(existingGroupIds.size()>0){
				loadsheetManagementDao.deleteGrpMasterDetails(existingGroupIds,seqMaster.getId());
				loadsheetManagementDao.deleteCmpGrpSeqDetailsUsingGrpId(existingGroupIds);
			}
			
			
			//Update Group Details
			for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
				
				if(grpMaster.getDisplaySeq()!=0){
						
						newCompGrpSeqList=new ArrayList<LoadsheetCompGrpSeq>();
						existingCompIds=new ArrayList<Integer>();
						
						if(grpMaster.getGrpMasterId()!=0){//Update the Group Details
							loadsheetManagementDao.updateGrpMasterDetails(grpMaster, user);
						}else{//Insert the New Group
							grpMaster.setSeqMasterId(seqMaster.getId());
							loadsheetManagementDao.insertGrpMasterDetails(grpMaster, user);
						}
						
						//Update Components Info
						for(LoadsheetCompGrpSeq cmpGrpSeq:grpMaster.getCompGrpSeqList()){
							
							if(cmpGrpSeq.getDisplaySeq()!=0){
								if(cmpGrpSeq.getCompGrpSeqId()!=0){//Update the componentID sequence
									loadsheetManagementDao.updateCmpGrpSeqDeatils(cmpGrpSeq, user);
								}else{//Add new components to list
									cmpGrpSeq.setGrpMasterId(grpMaster.getGrpMasterId());
									newCompGrpSeqList.add(cmpGrpSeq);
								}
								
								if(cmpGrpSeq.getCompGrpSeqId()!=0){
									existingCompIds.add(cmpGrpSeq.getCompGrpSeqId());
								}
								
							}
							
							
						}
						
						//delete the deleted Components
						if(existingCompIds.size()>0){
							loadsheetManagementDao.deleteCmpGrpSeqDetails(existingCompIds,grpMaster.getGrpMasterId());
						}
						
						//Insert new Components
						if(newCompGrpSeqList.size()>0){
							loadsheetManagementDao.insertCmpGrpSeqDetails(newCompGrpSeqList, user);
						}
						
			
				}
			}	
			
		}
		
	}



}
