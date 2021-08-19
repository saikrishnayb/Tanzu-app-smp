package com.penske.apps.adminconsole.service;

import java.util.List;
import java.util.Map;

import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HoldPayment;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.smccore.base.domain.User;

/**
 * 
 * @author 502174985
 * This interface is used for Component management pages
 */
public interface ComponentService {
	
//Template page -- Start
	public List<Template> getAllTemplates();
	
	public Template getTemplatesById(int templateID);
	
	public List<Components> getAllComponent();
	
	public void addTemplate(Template template) throws Exception;
	
	public void updateTemplate(Template template) throws Exception;
	
	public void deActivateTemplate(int templateId);
	
	public void activateTemplate(int templateId);
	
	public List<Components> getTemplateComponentById(List<Components> compList,int templateId);
	
	public List<Integer> findTemplateExist(Template template);
	
	public List<TemplatePoAssociation> getAllPoAssociationAddEdit(boolean isAdd);
//Template page -- End
	
//COMPONENT VISIBILITY OVERRIDES -- start
	public List<Component> loadAllAvailableComponents();

    public void copyCorpComponentRow(int componentId, int componentGroupNumber);

	public List<LoadSheetComponentDetails> getTemplateComponentByTempId(int templateId);
	
	public void allowDuplicateComponents(int componentId,boolean allowDuplicates);

//COMPONENT VISIBILITY OVERRIDES -- End
	public Map<Integer, List<HoldPayment>> getAllHoldPayments();
	
	public List<HoldPayment> getHoldPaymentsByComponentId(int componentId);

	public Component getComponentById(int componentId);

	public void saveHoldPayments(Component component, List<Integer> vendorIds, User user);
}
