package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;

/**
 * 
 * @author 600144005
 * This class has service methods of component management page
 */

@Service
public class DefaultComponentService implements ComponentService {

	@Autowired
	private ComponentDao componentDao;
	
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
	public void addTemplate(Template template) {
		componentDao.addTemplate(template);
		int templateID=template.getTemplateID();
		List<Components> componentList=template.getComponentList();
		if(componentList !=null && !componentList.isEmpty()){
			addTemplateComponent(componentList, templateID,template);
		}
	}

	@Override
	@Transactional
	public void deleteTemplate(int templateID) {
		componentDao.deleteTemplate(templateID);
		componentDao.deleteTemplateComponents(templateID);
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
					if(availComponents.getEditRequiredStr() !=null 
							&& availComponents.getEditRequiredStr().equalsIgnoreCase("R")){
						components.setViewable(true);
						components.setEditable(true);
						components.setRequired(true);
					}else if(availComponents.getEditRequiredStr() !=null 
							&& availComponents.getEditRequiredStr().equalsIgnoreCase("E")){
						components.setViewable(true);
						components.setEditable(true);
					}else if(availComponents.getEditRequiredStr() !=null 
							&& availComponents.getEditRequiredStr().equalsIgnoreCase("V")){
						components.setViewable(true);
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
	public void updateTemplate(Template template) {
		componentDao.updateTemplate(template);
		int templateID=template.getTemplateID();
		componentDao.deleteTemplateComponents(templateID);
		List<Components> componentList=template.getComponentList();
		if(componentList !=null && !componentList.isEmpty()){
			addTemplateComponent(componentList, templateID,template);
		}
	}
	
	private void addTemplateComponent(List<Components> componentList,int templateID,Template template){
		for (Components components : componentList) {
			Components dbComponents=new Components();
			dbComponents.setTemplateId(templateID);
			dbComponents.setComponentId(components.getComponentId());
			dbComponents.setEditRequiredStr("I"); //Check with Dav. //TODO
			if(components.isRequired()){
				dbComponents.setEditRequiredStr("R");
			}
			if(!components.isRequired() && components.isEditable()){
				dbComponents.setEditRequiredStr("E");
			}
			if(!components.isRequired() && !components.isEditable() && components.isViewable()){
				dbComponents.setEditRequiredStr("V");
			}
			if(components.isDispOtherPO()){
				dbComponents.setDispOtherPOStr("Y");
			}
			if(components.isExcel()){
				dbComponents.setExcelStr("Y");
			}
			dbComponents.setCreatedBy(template.getCreatedBy());
			dbComponents.setModifiedBy(template.getModifiedBy());
			componentDao.addTemplateComponents(dbComponents);
		}
	}

	@Override
	public List<Integer> findTemplateExist(Template template) {
		return componentDao.findTemplateExist(template);
	}
	
	//Template Page -- End
}
