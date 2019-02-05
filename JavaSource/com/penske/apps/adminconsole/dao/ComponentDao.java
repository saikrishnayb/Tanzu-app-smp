package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.domain.ComponentGroup;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in category-management-mapper for visibility by category page
 */

public interface ComponentDao {

    //Template Page --- start
    @NonVendorQuery
    public List<Template> getAllTemplates();

    @NonVendorQuery
    public Template getTemplatesById(@Param("templateID")int templateID);

    @NonVendorQuery
    public List<Components> getAllComponent();

    @NonVendorQuery
    public void addTemplate(Template template);

    @NonVendorQuery
    public void addTemplateComponent(Components components);
    
    @NonVendorQuery
    public void updateTemplateComponent(Components component);

    @NonVendorQuery
    public void updateTemplate(Template template);
    
    @NonVendorQuery
    public boolean isTemplateComponentExist(@Param("templateId") int templateId,@Param("componentId") int componentId);
    
    @NonVendorQuery
    public void deActivateTemplate(@Param("templateID")int templateID);
    
    @NonVendorQuery
    public void activateTemplate(@Param("templateID")int templateID);

    @NonVendorQuery
    public void deleteTemplateComponents(@Param("deletedTempComponents")List<Integer> deletedTempComponents,@Param("templateId")int templateId);
    
    @NonVendorQuery
    public List<Components> getTemplateComponentById(@Param("templateID")int templateID);

    @NonVendorQuery
    public List<Integer> findTemplateExist(Template template);

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForAdd();

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForEdit();
    //Template Page --- End

    @NonVendorQuery
    public List<Component> loadAllAvailableComponents();

    @NonVendorQuery
    public ComponentGroup getComponentGroup(@Param("groupId") int groupId);

    @NonVendorQuery
    public void copyCorpComponentGroupRow(@Param("groupId") int groupId);

    @NonVendorQuery
    public void copyCorpComponentRow(@Param("componentId") int componentId);

    @NonVendorQuery
	public List<LoadSheetComponentDetails> getTemplateComponentByTempId(@Param("templateId")int templateId);
    
    @NonVendorQuery
    public void allowDuplicateComponents(@Param("componentId") int componentId,@Param("allowDuplicates")boolean allowDuplicates);

    
}
