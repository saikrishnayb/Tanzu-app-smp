package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.domain.ComponentGroup;
import com.penske.apps.adminconsole.domain.ComponentInfoDetail;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in category-management-mapper for visibility by category page
 */

public interface ComponentDao {

    @NonVendorQuery
    public  List<ComponentVisibility> getComponent();

    @NonVendorQuery
    public List<ComponentVisibility> getCategory(@Param("componentId")int componentId);

    @NonVendorQuery
    public List<ComponentVisibility> getVehicleCategory(@Param("componentId")int componentId);

    @NonVendorQuery
    public List<SubCategory>getComponentSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);

    @NonVendorQuery
    public List<SubCategory>getVehicleSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);

    @NonVendorQuery
    public List<ComponentVisibility> getComponentName();

    @NonVendorQuery
    public void deleteComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public void deleteVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public void addComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public void addVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public ComponentVisibility getComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public ComponentVisibility getVehicleComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public List<PoCategory> getCategoryList();

    @NonVendorQuery
    public List<SubCategory> getSubCategoryList(int poCategoryId);

    @NonVendorQuery
    public List<ComponentVisibility> getComponentList(int poCategoryId);


    //Template Page --- start
    @NonVendorQuery
    public List<Template> getAllTemplates();

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociation();

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
    public boolean isComponentExistInRule(@Param("ruleId") int templateId,@Param("tempCompId") int tempCompId);
   
    @NonVendorQuery
	public boolean isComponentExistInRuleDefinitions(@Param("ruleId") int ruleId,@Param("componentId") int componentId);
    
    @NonVendorQuery
    public List<Integer> getComponetsByRuleDefIds(@Param("ruleDefIds") List<Integer> ruleDefIds);
    
    @NonVendorQuery
    public void deActivateTemplate(@Param("templateID")int templateID);
    
    @NonVendorQuery
    public void activateTemplate(@Param("templateID")int templateID);

    @NonVendorQuery
    public void deleteTemplateComponents(@Param("deletedTempComponents")List<Integer> deletedTempComponents,@Param("templateId")int templateId);
    
    @NonVendorQuery
    public void deleteComponentRuleMapping(@Param("ruleId")int ruleId,@Param("tempCompId") int tempCompId);
    

    @NonVendorQuery
    public List<Components> getTemplateComponentById(@Param("templateID")int templateID);

    @NonVendorQuery
    public List<Integer> findTemplateExist(Template template);

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForAdd();

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForEdit();
    //Template Page --- End
    //COMPONENT VISIBILITY OVERRIDES -- start

    @NonVendorQuery
    public List<ComponentVisibilityOverride> getAllComponentVisibilityOverrides();

    @NonVendorQuery
    public void addComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);

    @NonVendorQuery
    public void updateComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);

    @NonVendorQuery
    public void deleteComponentVisibilityOverrides(@Param("visiblityOverrideId") int visiblityOverrideId);

    @NonVendorQuery
    public ComponentVisibilityOverride getComponentVisibilityOverridesById(@Param("visiblityOverrideId") int visiblityOverrideId);

    @NonVendorQuery
    public ComponentVisibilityOverride  checkComponentVisibilityOverrideExist(ComponentVisibilityOverride componentVisibilityOverride);
    //COMPONENT VISIBILITY OVERRIDES -- End

    @NonVendorQuery
    public List<Component> loadAllAvailableComponents();

    @NonVendorQuery
    public ComponentInfoDetail getComponentInfoDetail(@Param("componentId") int componentId);

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
