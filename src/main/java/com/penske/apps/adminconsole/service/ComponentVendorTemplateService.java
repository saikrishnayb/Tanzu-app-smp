package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponent;
/**
 * 
 * @author 600144005
 * This interface is used for templates page
 */
public interface ComponentVendorTemplateService {

    public List<PoCategory> getPoCategories();

    public Template getExcelSeqTemplate(Integer templateId);

    public List<ComponentSequence> getTemplateComponentSequences(int templateID);

    public void updateTemplateComponentSequence(TemplateComponent templateComponents);
}

