package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.dao.LoadsheetManagementDao;
import com.penske.apps.adminconsole.enums.PoCategoryType;
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
import com.penske.apps.adminconsole.model.TemplateComponentRuleAssociation;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultLoadSheetManagementService implements LoadSheetManagementService {
	
	@Autowired
    private LoadsheetManagementDao loadsheetManagementDao;
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	@Autowired
	private ComponentDao componentDao;
	
	private static final char UNIT_TEMPLATE_RULE='U';

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
	/* this method will insert/update all the component-rule visibility mappings for selected rule.*/
	private void createOrUpdateTemplateComponentRules(RuleMaster ruleMaster,char operationType) throws Exception {
		UserContext user = sessionBean.getUserContext();
		TemplateComponentRuleAssociation templateComponentRuleAssociation = new TemplateComponentRuleAssociation();
		templateComponentRuleAssociation.setTemplateComponentId(ruleMaster.getTemplateComponentId());
		templateComponentRuleAssociation.setRuleId(ruleMaster.getRuleId());
		templateComponentRuleAssociation.setCreatedBy(user.getUserSSO());
        if(ruleMaster.isForRules() && !ruleMaster.isRequired() && !ruleMaster.isEditable() && !ruleMaster.isViewable()){
        	templateComponentRuleAssociation.setLsOverride('N');
        }
        else if(ruleMaster.isRequired()){
            templateComponentRuleAssociation.setLsOverride('R');
        }
        else if(!ruleMaster.isRequired() && ruleMaster.isEditable()){
            templateComponentRuleAssociation.setLsOverride('E');
        }
        else if(!ruleMaster.isRequired() && !ruleMaster.isEditable() &&  ruleMaster.isViewable()){ 
            templateComponentRuleAssociation.setLsOverride('V');
        }else{
        	throw new Exception("Atleast one checkbox must be checked");
        }
        if(operationType=='I'){
            Map<String, Integer> compTempCompMap = getTemplateComponentMap(ruleMaster.getTemplateId());
            	//to update rule priority for the selected component when new rule is added
    			saveTemplateComponentVisibilityRules(ruleMaster.getRuleId(),templateComponentRuleAssociation);    			 	
			 	for(RuleDefinitions ruleDef:ruleMaster.getNewRuleDef()){
			 		//to update rule priority for components(one or multiple) mapped to the rule for rule name 
			 		templateComponentRuleAssociation.setTemplateComponentId(compTempCompMap.get(ruleDef.getComponentId()));
    				saveTemplateComponentVisibilityRules(ruleMaster.getRuleId(),templateComponentRuleAssociation);
			 	}
        }else{//update component visibility of all components for the selected rule
            	loadsheetManagementDao.updateTemplateComponentVisibilityRules(templateComponentRuleAssociation);
        }
    }
    	/**
    	 * 
    	 * @param ruleId
    	 * @param templateComponentRuleAssociation
    	 */
    	private void saveTemplateComponentVisibilityRules(int ruleId, TemplateComponentRuleAssociation templateComponentRuleAssociation) {
    		int ruleCount = 0;
    		boolean isExistComponent = componentDao.isComponentExistInRule(ruleId,templateComponentRuleAssociation.getTemplateComponentId());
     		if(!isExistComponent){
	    		ruleCount= getRuleCountByTempCompId(templateComponentRuleAssociation.getTemplateComponentId());
	    		templateComponentRuleAssociation.setPriority(ruleCount+1);
	    	    loadsheetManagementDao.saveTemplateComponentVisibilityRules(templateComponentRuleAssociation);	
     		 }
    	}
	
	/* this method will return  component - templateComponent map for given template id .*/
	private Map<String,Integer> getTemplateComponentMap(int templateId) {
		List<String> compTempcomponents = componentDao.getCompTempCompMapByTempId(templateId);
		HashMap<String,Integer>  compTempCompMap = new HashMap<String, Integer>();
		for (String component : compTempcomponents) {
			String CompId = component.split("-")[0];
			Integer tempCompId = Integer.parseInt(component.split("-")[1]);
			compTempCompMap.put(CompId, tempCompId);
		}
        return compTempCompMap;
	}

	@Override
	public List<LoadSheetComponentDetails> getComponents() {
		
		return loadsheetManagementDao.getComponents();
	}
	/**
	 * Method to create new Rule
	 * First inserts the data into rule master table and then inserts the list of Rule definitions
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public int createNewRule(RuleMaster rule) throws Exception{
		
		UserContext user = sessionBean.getUserContext();
		rule.setDescription(rule.getDescription().trim());
		loadsheetManagementDao.insertRuleMasterDetails(rule, user);
		populateRuleMaster(rule);
		for(RuleDefinitions ruleDef:rule.getRuleDefinitionsList()){
			if(ruleDef.getOperand().equals("E") || ruleDef.getOperand().equals("=")){
				if(ruleDef.getComponentId()!=null && ruleDef.getValue()==null)
					ruleDef.setValue("");
			}
		}
		loadsheetManagementDao.insertRuleDefinitions(rule.getRuleDefinitionsList(), user);
		if(rule.getRuleType()==UNIT_TEMPLATE_RULE){
			rule.setNewRuleDef(rule.getRuleDefinitionsList());
			createOrUpdateTemplateComponentRules(rule,'I');
		}
		
		return rule.getRuleId();
	}
	//to Update the rule master and rule definitions
	@Override
	@Transactional
	public void updateRuleDetails(RuleMaster rule) throws Exception{
		UserContext user = sessionBean.getUserContext();
		List<RuleDefinitions> newRuleDefList=new ArrayList<RuleDefinitions> ();
		rule.setDescription(rule.getDescription().trim());
		loadsheetManagementDao.updateRuleMasterDetails(rule, user);
		
		populateRuleMaster(rule);
		for(RuleDefinitions ruleDef:rule.getRuleDefinitionsList()){
			if(ruleDef.getOperand().equals("E") || ruleDef.getOperand().equals("=")){
				if(ruleDef.getValue()==null)
					ruleDef.setValue("");
			}
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
			if(rule.getRuleType()==UNIT_TEMPLATE_RULE){
				rule.setNewRuleDef(newRuleDefList);
				createOrUpdateTemplateComponentRules(rule,'I');
			}
		}
		//Delete the deleted rule definitions
		if(rule.getDeletedRuleDefIds().size()>0){
			loadsheetManagementDao.deleteRuleDefinitions(rule.getDeletedRuleDefIds());
			if(rule.getRuleType()==UNIT_TEMPLATE_RULE){
			    Map<String, Integer> compTempCompMap = getTemplateComponentMap(rule.getTemplateId());
				List<Integer> components = componentDao.getComponetsByRuleDefIds(rule.getDeletedRuleDefIds());
				 for (Integer componentId : components) {
					 boolean compnentExists = componentDao.isComponentExistInRuleDefinitions(rule.getRuleId(),componentId);
						if(!compnentExists)
							componentDao.deleteComponentRuleMapping(rule.getRuleId(),compTempCompMap.get(componentId));
				}
			}
				
		}
		if(rule.getRuleType()==UNIT_TEMPLATE_RULE)
		createOrUpdateTemplateComponentRules(rule,'U');
		formTheComponentDropdownValue(rule);
	}
	
	/**
	 * Method to form the RuleMaster Object for insert/update actions
	 * @param rule
	 * @return rule
	 * @throws Exception 
	 */
	private RuleMaster populateRuleMaster(RuleMaster rule){
		
		String[] componentValue;
		Set<Integer> criteraiGrpValues=new HashSet<Integer>();
		RuleDefinitions ruleDef;
		int criteriaGrpCount=0;
		
		String requestFrom="";
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
		
		rule=getOperandsDropdownValues(rule,requestFrom);
		return rule;
	}
		

	/**
	 * Method to get the rule details On click of Edit
	 * @throws Exception 
	 */
	@Override
	public RuleMaster getRuleDetails(int ruleId,String requestFrom){
		
		RuleMaster ruleMaster=loadsheetManagementDao.getRuleDetails(ruleId, requestFrom);
		
		ruleMaster=formTheComponentDropdownValue(ruleMaster);
		ruleMaster=getOperandsDropdownValues(ruleMaster,requestFrom);
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
	private RuleMaster getOperandsDropdownValues(RuleMaster ruleMaster,String requestFrom){
		List<String> operandsList;
		
		for(RuleDefinitions ruleDef:ruleMaster.getRuleDefinitionsList()){
			
			//populate operands list for the particular ruledef
			operandsList=new ArrayList<String>();
			if(ruleDef.getComponentType().equals("N") || ruleDef.getComponentType().equals("Y")){
				operandsList.add("<");
				operandsList.add("<=");
				operandsList.add("=");
				operandsList.add(">");
				operandsList.add(">=");
			}else{
				operandsList.add("=");
			}
			if(requestFrom.equals("templateRule"))
				operandsList.add("E");
			
			ruleDef.setOperandsList(operandsList);
		}
				
		return ruleMaster;
	}
	
	/**
	 * Method to check for unique rule name
	 */
	@Override
	public boolean checkForUniqueRuleName(String newRuleName,int ruleId){
		
		List<String> existingNames=null;
		boolean isUnique=true;
		
		existingNames=loadsheetManagementDao.getAllRuleNames(ruleId);
		
		for(String name:existingNames){
			if(name.equalsIgnoreCase(newRuleName.trim())){
				isUnique=false;
			}
		}
		
		return isUnique;
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
	 * Method to get Assigned categories for the given rule
	 */
	@Override
	public List<LoadsheetManagement> getAssignedLoadsheetCategories(int ruleId) {
		
		List<LoadsheetManagement> loadSheetList=null;
		
		loadSheetList=loadsheetManagementDao.getAssignedLoadsheetCategories(ruleId);
		
		for(LoadsheetManagement loadSheetManagement:loadSheetList){
			//Setting uses default to N if it is empty
			if(loadSheetManagement.getUsesDefault().equals(" ")){
				loadSheetManagement.setUsesDefault("N");
			}
			
		}
		
		return loadSheetList;
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
	public List<String> getTypeList(String category) {
		return loadsheetManagementDao.getTypeList(category);
	}
	
	/**
	 * Method to get MFR list in loadsheet sequence screen.
	 */
	@Override
	public List<String>  getMfrList(PoCategoryType poCategoryType){
		
		if(poCategoryType == null || poCategoryType.getMfrFieldCode() == null)
			return Collections.emptyList();
		
		return loadsheetManagementDao.getMfrList(poCategoryType);
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
			
		UserContext user = sessionBean.getUserContext();
		List<LoadsheetCompGrpSeq> cmpGrpSeqList=null;
		LoadsheetCompGrpSeq cmpGrpSeq;
		//if category type is empty then set default type(VOD-351).
		if(StringUtils.isEmpty(seqMaster.getType())){
			seqMaster.setType(com.penske.apps.adminconsole.util.ApplicationConstants.DEFAULT_TYPE);
		}
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
		
		UserContext user = sessionBean.getUserContext();
		List<LoadsheetCompGrpSeq> newCompGrpSeqList=null;
		List<Integer> existingCompIds=null; //list for deleting the deleted components using NOT IN
		List<Integer> existingGroupIds=new ArrayList<Integer>();
		
		if(seqMaster.getId()!=0){
			//update sequence master details
			loadsheetManagementDao.updateSeqMasterDetails(seqMaster, user);
			
			//Delete the deleted components
			for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
				if(grpMaster.getDisplaySeq()!=0){
					if(grpMaster.getGrpMasterId()!=0){
						if(grpMaster.getCompGrpSeqList()!=null){
						existingCompIds=getCmpGrpSeqIds(grpMaster);
						//Delete the deleted Components
						loadsheetManagementDao.deleteCmpGrpSeqDetails(existingCompIds,grpMaster.getGrpMasterId());
						}else{
						//Delete all Components
						loadsheetManagementDao.deleteCmpGrpSeq(grpMaster.getGrpMasterId());
						}
					}
				}
			}
			
			//Delete the deleted Groups
			if(seqMaster.getGroupMasterList()!=null){
				    existingGroupIds=getGrpMasterIds(seqMaster.getGroupMasterList());
				    //Delete the deleted Groups
					loadsheetManagementDao.deleteGrpMasterDetails(existingGroupIds,seqMaster.getId());
			}else{
					//Delete all groups
					loadsheetManagementDao.deleteGrpMaster(seqMaster.getId());	
			}
			
			//Update Group Details
			for(LoadsheetSequenceGroupMaster grpMaster:seqMaster.getGroupMasterList()){
				
				if(grpMaster.getDisplaySeq()!=0){
						
						newCompGrpSeqList=new ArrayList<LoadsheetCompGrpSeq>();
						
						
						if(grpMaster.getGrpMasterId()!=0){//Update the Group Details
							loadsheetManagementDao.updateGrpMasterDetails(grpMaster, user);
						}else{//Insert the New Group
							grpMaster.setSeqMasterId(seqMaster.getId());
							loadsheetManagementDao.insertGrpMasterDetails(grpMaster, user);
						}
						
						//Update Components Info
						for(LoadsheetCompGrpSeq cmpGrpSeq:grpMaster.getCompGrpSeqList()){
							
							if(cmpGrpSeq.getDisplaySeq()!=0){
								cmpGrpSeq.setGrpMasterId(grpMaster.getGrpMasterId());
								if(cmpGrpSeq.getCompGrpSeqId()!=0){//Update the componentID sequence
									loadsheetManagementDao.updateCmpGrpSeqDeatils(cmpGrpSeq, user);
								}else{//Add new components to list
									newCompGrpSeqList.add(cmpGrpSeq);
								}
							}
						}
						//Insert new Components
						if(newCompGrpSeqList.size()>0){
							loadsheetManagementDao.insertCmpGrpSeqDetails(newCompGrpSeqList, user);
						}
						
			
				}
			}	
			
		}
		
	}
	/**
	 * Method to get component sequence id's for the group.
	 */
	
	private List<Integer> getCmpGrpSeqIds(LoadsheetSequenceGroupMaster grpMaster) {
		List<Integer> existingCompIds=new ArrayList<Integer>();
		for(LoadsheetCompGrpSeq cmpGrpSeq:grpMaster.getCompGrpSeqList()){
			if(cmpGrpSeq!=null){
					existingCompIds.add(cmpGrpSeq.getCompGrpSeqId());
			}
		}
		return existingCompIds;
	}
	/**
	 * Method to get group master id's for the sequence.
	 */
	private List<Integer> getGrpMasterIds(List<LoadsheetSequenceGroupMaster> groupMasterList) {
		List<Integer> existingGroupIds=new ArrayList<Integer>();
		for(LoadsheetSequenceGroupMaster grpMaster:groupMasterList){
			if(grpMaster!=null){
					existingGroupIds.add(grpMaster.getGrpMasterId());
			}
		}
		return existingGroupIds;
	}

	/**
	 * Method to Delete the sequence by using sequence id.
	 */
	@Override
	@Transactional
	public void deleteLoadsheetSequence(int sequenceId) {
		loadsheetManagementDao.deleteLoadsheetGroupSequnece(sequenceId);
		loadsheetManagementDao.deleteLoadsheetGroupMaster(sequenceId);
		loadsheetManagementDao.deleteLoadsheetSequenceMaster(sequenceId);
		
		
	}
	/**
	 * Method to get Uses Default value for selected category and type.
	 */
	@Override
	public String getUsesDefaultForCategoryAndType(String category, String type) {
		return loadsheetManagementDao.getUsesDefaultForCategoryAndType(category, type);
	}
	
	/**
	 * Method to check for Unique sequencename
	 */
	@Override
	public boolean checkForUniqueSequenceName(String newName,int seqId){
		
		List<String> existingNames=null;
		boolean isUnique=true;
		
		existingNames=loadsheetManagementDao.getAllSequenceNames(seqId);
		
		for(String name:existingNames){
			if(name.equalsIgnoreCase(newName.trim())){
				isUnique=false;
			}
		}
		
		return isUnique;
	}
	/**
	 * Method to check for Unique sequence for selected category,type and oem.
	 */
	@Override
	public int checkForUniqueSequence(String catgeory, String type,String mfr,int seqId) {
		return loadsheetManagementDao.getSequenceCount(catgeory,type,mfr,seqId);
	}

	@Override
	public List<RuleMaster> getRulesByTemplateComponentId(int templateComponentId) {
		return loadsheetManagementDao.getRulesByTemplateComponentId(templateComponentId);
	}
	
	/**
	 * Method to update component rules priority
	 * @param ruleList
	 * @param templateComponentId
	 */
	@Override
	public void updateComponentRulesPriority(List<Integer> ruleList,int templateComponentId){
		int priority = 1;
		UserContext user = sessionBean.getUserContext();
		for(Integer ruleId:ruleList){
	        loadsheetManagementDao.updateComponentRulePriority(templateComponentId,ruleId,priority,user.getUserSSO());
		    priority++;
		}
	}

	
	@Override
	public void getTemplateComponentRuleVisibilty(int templateComponentId,int ruleId,RuleMaster ruleMaster) throws Exception{
		TemplateComponentRuleAssociation templateComponentRuleAssociation = loadsheetManagementDao.getTemplateComponentRuleVisibilty(templateComponentId,ruleId);
		if(templateComponentRuleAssociation.getLsOverride() !=' ' 
                 && templateComponentRuleAssociation.getLsOverride()=='R'){
			 ruleMaster.setViewable(true);
			 ruleMaster.setEditable(true);
			 ruleMaster.setRequired(true);
			 ruleMaster.setForRules(true);
         }else if(templateComponentRuleAssociation.getLsOverride() !=' '
                 && templateComponentRuleAssociation.getLsOverride()=='E'){
        	 ruleMaster.setViewable(true);
        	 ruleMaster.setEditable(true);
        	 ruleMaster.setForRules(true);
         }else if(templateComponentRuleAssociation.getLsOverride() !=' '
                 && templateComponentRuleAssociation.getLsOverride()=='V'){
        	 ruleMaster.setViewable(true);
        	 ruleMaster.setForRules(true);
         }else if(templateComponentRuleAssociation.getLsOverride() !=' '
                 && templateComponentRuleAssociation.getLsOverride() == 'N'){
        	 ruleMaster.setForRules(true);
         }else{
        	 throw new Exception("Error during fetching rule visibilty");
         }
		 
	}
	
	public int getRuleCountByTempCompId(int templateComponentId){
		return loadsheetManagementDao.getRuleCountByTemplateComponentId(templateComponentId);
	}

}
