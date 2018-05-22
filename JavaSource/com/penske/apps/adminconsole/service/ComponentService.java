package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.suppliermgmt.model.LookUp;

/**
 * 
 * @author 502174985
 * This interface is used for Component management pages
 */
public interface ComponentService {
	
//Template page -- Start
	public List<Template> getAllTemplates();
	
	public List<TemplatePoAssociation> getAllPoAssociation();
	
	public Template getTemplatesById(int templateID);
	
	public List<Components> getAllComponent();
	
	public void addTemplate(Template template) throws Exception;
	
	public void updateTemplate(Template template) throws Exception;
	
	public void deActivateTemplate(int templateId);
	
	public void activateTemplate(int templateId);
	
	public List<Components> getTemplateComponentById(List<Components> compList,int templateId);
	
	public List<Integer> findTemplateExist(Template template);
	
	public List<LookUp> getOverrideTypes();
	
	public List<TemplatePoAssociation> getAllPoAssociationAddEdit(boolean isAdd);
//Template page -- End
	
//COMPONENT VISIBILITY OVERRIDES -- start
	public List<ComponentVisibilityOverride> getAllComponentVisibilityOverrides();
	
	public void addComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);
	
	public void updateComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);
	
	public void deleteComponentVisibilityOverrides(int visiblityOverrideId);
	
	public ComponentVisibilityOverride getComponentVisibilityOverridesById(int visiblityOverrideId);
	
	public boolean  checkComponentVisibilityOverrideExist(ComponentVisibilityOverride componentVisibilityOverride,boolean isCreate);
	
	public List<Component> loadAllAvailableComponents();

    public void copyCorpComponentRow(int componentId, int componentGroupId);

	public List<LoadSheetComponentDetails> getTemplateComponentByTempId(int templateId);

//COMPONENT VISIBILITY OVERRIDES -- End
		
}
