package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.ComponentVendorTemplateDao;
import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponent;
/**
 * 
 * @author 600144005
 * class has all service methods of templates page
 */

@Service
public class DefaultComponentVendorTemplateService implements ComponentVendorTemplateService {


    @Autowired
    private  ComponentVendorTemplateDao templateDao;

    @Override
    public List<PoCategory> getPoCategories() {

        List<PoCategory> poCategories =templateDao.getPoCategories();
        return poCategories;
    }

    @Override
    public Template getExcelSeqTemplate(Integer templateId) {

        List<Template> excelSeqTemplates = templateDao.getExcelSeqTemplates(templateId);

        return excelSeqTemplates.size() == 0? null : excelSeqTemplates.get(0);
    }

    @Override
    public List<ComponentSequence> getTemplateComponentSequences(int templateId) {
        return templateDao.getTemplateComponentSequences(templateId);
    }

    @Override
    public void updateTemplateComponentSequence(TemplateComponent templateComponents) {
        //Update template component sequences

        if(templateComponents.getTemplateId()!=0 && !templateComponents.getComponents().isEmpty()){

            for(ComponentSequence componentSequence:templateComponents.getComponents()){
                templateDao.updateTemplateComponentSequence(templateComponents.getTemplateId(), componentSequence.getComponentId(),componentSequence.getComponentSequence());

            }

        }

    }







}
