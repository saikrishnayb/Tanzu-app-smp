package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.domain.ComponentGroup;
import com.penske.apps.adminconsole.domain.ComponentInfoDetail;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplatePoAssociation;
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
    public void addTemplateComponents(Components components);

    @NonVendorQuery
    public void updateTemplate(Template template);

    @NonVendorQuery
    public void deleteTemplate(@Param("templateID")int templateID);

    @NonVendorQuery
    public void deleteTemplateComponents(@Param("templateID")int templateID);

    @NonVendorQuery
    public List<Components> getTemplateComponentById(@Param("templateID")int templateID);

    @NonVendorQuery
    public List<Integer> findTemplateExist(Template template);

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForAdd();

    @NonVendorQuery
    public List<TemplatePoAssociation> getAllPoAssociationForEdit(@Param("assocId")int assocId);
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
    public void markTemplateForRebuild(@Param("templateId") int templateId);

    @NonVendorQuery
    public void insertTemplateForRegen(@Param("templateId") int templateId);
}
