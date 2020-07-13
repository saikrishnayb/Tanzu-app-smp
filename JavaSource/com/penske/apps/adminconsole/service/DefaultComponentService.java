package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HoldPayment;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.suppliermgmt.domain.ComponentGroup;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * 
 * @author 600144005
 * This class has service methods of component management page
 */

@Service
public class DefaultComponentService implements ComponentService {

    @Autowired
    private ComponentDao componentDao;

    @Override
    public List<Template> getAllTemplates(){
        return componentDao.getAllTemplates();
    }
    @Override
    public Template getTemplatesById(int templateID){
        return componentDao.getTemplatesById(templateID);
    }
    @Override
    public List<Components> getAllComponent(){
        return componentDao.getAllComponent();
    }

    @Override
    @Transactional
    public void addTemplate(Template template){
        componentDao.addTemplate(template);
        int templateID=template.getTemplateID();
        List<Components> componentList=template.getComponentList();
        if(componentList !=null && !componentList.isEmpty()){
            addTemplateComponent(componentList, templateID,template);
        }else{
        	throw new IllegalArgumentException("Please select at least one component before saving the unit template.");
        }
    }

    @Override
    public void deActivateTemplate(int templateID) {
        componentDao.deActivateTemplate(templateID);
    }
    
    @Override
    public void activateTemplate(int templateID) {
        componentDao.activateTemplate(templateID);
    }

    @Override
    public List<Components> getTemplateComponentById(List<Components> compList,int templateId){
        List<Components> availableCompList= componentDao.getTemplateComponentById(templateId);
        if(compList !=null && !compList.isEmpty() && availableCompList !=null && !availableCompList.isEmpty()){
            return getMassageCompList(compList, availableCompList);
        }else{
            return compList;
        }
    }
    private List<Components> getMassageCompList(List<Components> compList,List<Components> availableCompList){
        for (Components components : compList) {
            for (Components availComponents : availableCompList) {
                if(components.getComponentId() !=null && availComponents.getComponentId() !=null
                        && components.getComponentId().equalsIgnoreCase(availComponents.getComponentId())){
                	components.setTemplateComponentId(availComponents.getTemplateComponentId());
                	components.setRuleCount(availComponents.getRuleCount());
                    if(availComponents.getEditRequiredStr() !=null
                            && availComponents.getEditRequiredStr().equalsIgnoreCase("R")){
                        components.setViewable(true);
                        components.setEditable(true);
                        components.setRequired(true);
                        components.setForRules(true);
                    }else if(availComponents.getEditRequiredStr() !=null
                            && availComponents.getEditRequiredStr().equalsIgnoreCase("E")){
                        components.setViewable(true);
                        components.setEditable(true);
                        components.setForRules(true);
                    }else if(availComponents.getEditRequiredStr() !=null
                            && availComponents.getEditRequiredStr().equalsIgnoreCase("V")){
                        components.setViewable(true);
                        components.setForRules(true);
                    }else if(availComponents.getEditRequiredStr() !=null
                            && availComponents.getEditRequiredStr().equalsIgnoreCase("N")){
                        components.setForRules(true);
                    }
                    if(availComponents.getExcelStr() !=null && availComponents.getExcelStr().equalsIgnoreCase("Y")){
                        components.setExcel(true);
                    }
                    if(availComponents.getDispOtherPOStr() !=null && availComponents.getDispOtherPOStr().equalsIgnoreCase("Y")){
                        components.setDispOtherPO(true);
                    }
                }
            }
        }
        return compList;
    }

    @Override
    @Transactional
    public void updateTemplate(Template template){
        componentDao.updateTemplate(template);
        int templateID=template.getTemplateID();
        List<Components> componentList=template.getComponentList();
        if(componentList !=null && !componentList.isEmpty()){
            addTemplateComponent(componentList, templateID,template);
        }else{
        	throw new IllegalArgumentException("Error occured during updation of template for the templateId: "+templateID);
        }
      
    }

    private void addTemplateComponent(List<Components> componentList,int templateID,Template template){
        List<Integer> deletedTempComponents = new ArrayList<Integer>();
    	
    	for (Components component : componentList) {
            Components dbComponent=new Components();
            dbComponent.setTemplateId(templateID);
            dbComponent.setComponentId(component.getComponentId());
            boolean compnentExists = componentDao.isTemplateComponentExist(templateID, Integer.parseInt(component.getComponentId()));
            boolean compnentVisibility = shouldBeOnTemplate(component);
	            if(component.isForRules() && !component.isRequired() && !component.isEditable() && !component.isViewable()){
	            	dbComponent.setEditRequiredStr("N");
	            }
	            else if(component.isRequired()){
	                dbComponent.setEditRequiredStr("R");
	            }
	            else if(!component.isRequired() && component.isEditable()){
	                dbComponent.setEditRequiredStr("E");
	            }
	            else if(!component.isRequired() && !component.isEditable() && component.isViewable()){ 
	                dbComponent.setEditRequiredStr("V");
	            }else if(!compnentExists && !compnentVisibility){
	            	throw new IllegalArgumentException("Error occured during insert due to bad visibility code for the componentId:"+dbComponent.getComponentId()+"in the templateId"+dbComponent.getTemplateId());
	            }
	            if(component.isDispOtherPO()){
	                dbComponent.setDispOtherPOStr("Y");
	            }
	            if(component.isExcel()){
	                dbComponent.setExcelStr("Y");
	            }
	            dbComponent.setCreatedBy(template.getCreatedBy());
	            dbComponent.setModifiedBy(template.getModifiedBy());
	            if(!compnentExists && compnentVisibility ){
	            	componentDao.addTemplateComponent(dbComponent);
	            }else if(compnentExists && compnentVisibility){
	            	componentDao.updateTemplateComponent(dbComponent);
	            }else if(compnentExists && !compnentVisibility ){
	            	deletedTempComponents.add(Integer.parseInt(component.getComponentId()));
	            }
        }
    	if(!deletedTempComponents.isEmpty())
    	componentDao.deleteTemplateComponents(deletedTempComponents,templateID);
    }
    
    private boolean shouldBeOnTemplate(Components component){
    	return component.isForRules() || component.isRequired() || component.isEditable() || component.isViewable() || component.isExcel() || component.isDispOtherPO();
    }
    @Override
    public List<Integer> findTemplateExist(Template template) {
        return componentDao.findTemplateExist(template);
    }


    @Override
    public List<TemplatePoAssociation> getAllPoAssociationAddEdit(boolean isAdd){
        if(isAdd){
            return componentDao.getAllPoAssociationForAdd();
        }else{
            return componentDao.getAllPoAssociationForEdit();
        }
    }

    @Override
    public List<Component> loadAllAvailableComponents() {
        return componentDao.loadAllAvailableComponents();
    }

    @Override
    @Transactional
    public void copyCorpComponentRow(int componentId, int componentGroupNumber) {
        ComponentGroup componentGroup = componentDao.getComponentGroup(componentGroupNumber);
        if (componentGroup == null)
            componentDao.copyCorpComponentGroupRow(componentGroupNumber);
        componentDao.copyCorpComponentRow(componentId);
    }
    
    @Override
    public List<LoadSheetComponentDetails> getTemplateComponentByTempId(int templateId){
    	return componentDao.getTemplateComponentByTempId(templateId);
    }
    
    @Override
    public void allowDuplicateComponents(int componentId,boolean allowDuplicates){
       
        componentDao.allowDuplicateComponents(componentId,allowDuplicates);
    }
    
    @Override
    public Component getComponentById(int componentId) {
    	return componentDao.getComponentById(componentId);
    }
    
    @Override
    public Map<Integer, List<HoldPayment>> getAllHoldPayments(){
    	List<HoldPayment> holdPayments = componentDao.getAllHoldPayments();
    	Map<Integer, List<HoldPayment>> holdPaymentsByCompId = new HashMap<>();
    	
    	for(HoldPayment holdPayment: holdPayments) {
    		List<HoldPayment> list = holdPaymentsByCompId.computeIfAbsent(holdPayment.getComponentId(), l -> new ArrayList<>());
    		list.add(holdPayment);
    	}
    	
    	return holdPaymentsByCompId;
    }
    
    @Override
    public List<HoldPayment> getHoldPaymentsByComponentId(int componentId) {
    	return componentDao.getHoldPaymentsByComponentId(componentId);
    }
    
    @Override
    public void saveHoldPayments(Component component, List<Integer> vendorIds, UserContext user) {
    	List<HoldPayment> holdPayments = componentDao.getHoldPaymentsByComponentId(component.getComponentId());
    	Map<Integer, HoldPayment> holdPaymentsByVendorId = holdPayments.stream()
    			.collect(Collectors.toMap(HoldPayment::getVendorId, hp->hp));
    	
    	List<HoldPayment> holdPaymentstoAdd = new ArrayList<>();
    	for(int vendorId: vendorIds) {
    		if(!holdPaymentsByVendorId.containsKey(vendorId))
    			holdPaymentstoAdd.add(new HoldPayment(component.getComponentId(), vendorId));
    	}
    	
    	List<HoldPayment> holdPaymentsToDelete = new ArrayList<>();
    	for(Entry<Integer, HoldPayment> entry: holdPaymentsByVendorId.entrySet()) {
    		int vendorId = entry.getKey();
    		HoldPayment holdPayment = entry.getValue();
    		
    		if(!vendorIds.contains(vendorId))
    			holdPaymentsToDelete.add(holdPayment);
    	}
    	
    	if(!holdPaymentstoAdd.isEmpty())
    		componentDao.addHoldPayments(holdPaymentstoAdd, user);
    	
    	if(!holdPaymentsToDelete.isEmpty())
    		componentDao.deleteHoldPayments(holdPaymentsToDelete);
    }
}
