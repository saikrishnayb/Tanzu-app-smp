package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.domain.ComponentGroup;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.LookUp;

/**
 * 
 * @author 600144005
 * This class has service methods of component management page
 */

@Service
public class DefaultComponentService implements ComponentService {

    @Autowired
    private ComponentDao componentDao;

    @Autowired
    private LookupManager lookupManager;

    //Template Page -- start

    @Override
    public List<Template> getAllTemplates(){
        return componentDao.getAllTemplates();
    }
    @Override
    public List<TemplatePoAssociation> getAllPoAssociation(){
        return componentDao.getAllPoAssociation();
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
    public List<LookUp> getOverrideTypes() {
        return lookupManager.getLookUpListByName(ApplicationConstants.EXCEPTIONS_TYPES);
    }
    @Override
    public List<ComponentVisibilityOverride> getAllComponentVisibilityOverrides() {
        return componentDao.getAllComponentVisibilityOverrides();
    }
    @Override
    public void addComponentVisibilityOverrides(
            ComponentVisibilityOverride componentVisibilityOverride) {
        componentDao.addComponentVisibilityOverrides(componentVisibilityOverride);
    }
    @Override
    public void updateComponentVisibilityOverrides(
            ComponentVisibilityOverride componentVisibilityOverride) {
        componentDao.updateComponentVisibilityOverrides(componentVisibilityOverride);

    }
    @Override
    public void deleteComponentVisibilityOverrides(int visiblityOverrideId) {
        componentDao.deleteComponentVisibilityOverrides(visiblityOverrideId);
    }

    @Override
    public ComponentVisibilityOverride getComponentVisibilityOverridesById(int visiblityOverrideId){
        return componentDao.getComponentVisibilityOverridesById(visiblityOverrideId);
    }
    @Override
    public boolean checkComponentVisibilityOverrideExist(
            ComponentVisibilityOverride componentVisibilityOverride,boolean isCreate) {
        ComponentVisibilityOverride overrideObj=componentDao.checkComponentVisibilityOverrideExist(componentVisibilityOverride);
        boolean returnFlg=true;
        if(overrideObj !=null){
            if(isCreate){
                returnFlg= false;
            }else{
                if(overrideObj.getVisiblityOverrideId() !=componentVisibilityOverride.getVisiblityOverrideId()){
                    returnFlg= false;
                }
            }
        }
        return returnFlg;
    }

    @Override
    public List<Component> loadAllAvailableComponents() {
        return componentDao.loadAllAvailableComponents();
    }

    @Override
    public void copyCorpComponentRow(int componentId, int componentGroupId) {
        ComponentGroup componentGroup = componentDao.getComponentGroup(componentGroupId);
        if (componentGroup == null)
            componentDao.copyCorpComponentGroupRow(componentGroupId);
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
}
