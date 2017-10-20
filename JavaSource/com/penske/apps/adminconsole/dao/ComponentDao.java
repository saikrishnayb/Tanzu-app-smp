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
	@NonVendorQuery //TODO: Review Query
    public  List<ComponentVisibility> getComponent();

	@NonVendorQuery //TODO: Review Query
    public List<ComponentVisibility> getCategory(@Param("componentId")int componentId);

	@NonVendorQuery //TODO: Review Query
    public List<ComponentVisibility> getVehicleCategory(@Param("componentId")int componentId);

	@NonVendorQuery //TODO: Review Query
    public List<SubCategory>getComponentSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);

	@NonVendorQuery //TODO: Review Query
    public List<SubCategory>getVehicleSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);

	@NonVendorQuery //TODO: Review Query
    public List<ComponentVisibility> getComponentName();

	@NonVendorQuery //TODO: Review Query
    public void deleteComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public void deleteVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public void addComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public void addVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public ComponentVisibility getComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public ComponentVisibility getVehicleComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	@NonVendorQuery //TODO: Review Query
    public List<PoCategory> getCategoryList();

	@NonVendorQuery //TODO: Review Query
    public List<SubCategory> getSubCategoryList(int poCategoryId);

	@NonVendorQuery //TODO: Review Query
    public List<ComponentVisibility> getComponentList(int poCategoryId);


    //Template Page --- start
	@NonVendorQuery //TODO: Review Query
    public List<Template> getAllTemplates();

	@NonVendorQuery //TODO: Review Query
    public List<TemplatePoAssociation> getAllPoAssociation();

	@NonVendorQuery //TODO: Review Query
    public Template getTemplatesById(@Param("templateID")int templateID);

	@NonVendorQuery //TODO: Review Query
    public List<Components> getAllComponent();

	@NonVendorQuery //TODO: Review Query
    public void addTemplate(Template template);

	@NonVendorQuery //TODO: Review Query
    public void addTemplateComponents(Components components);

	@NonVendorQuery //TODO: Review Query
    public void updateTemplate(Template template);

	@NonVendorQuery //TODO: Review Query
    public void deleteTemplate(@Param("templateID")int templateID);

	@NonVendorQuery //TODO: Review Query
    public void deleteTemplateComponents(@Param("templateID")int templateID);

	@NonVendorQuery //TODO: Review Query
    public List<Components> getTemplateComponentById(@Param("templateID")int templateID);

	@NonVendorQuery //TODO: Review Query
    public List<Integer> findTemplateExist(Template template);

	@NonVendorQuery //TODO: Review Query
    public List<TemplatePoAssociation> getAllPoAssociationForAdd();

	@NonVendorQuery //TODO: Review Query
    public List<TemplatePoAssociation> getAllPoAssociationForEdit(@Param("assocId")int assocId);
    //Template Page --- End
    //COMPONENT VISIBILITY OVERRIDES -- start

	@NonVendorQuery //TODO: Review Query
    public List<ComponentVisibilityOverride> getAllComponentVisibilityOverrides();

	@NonVendorQuery //TODO: Review Query
    public void addComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);

	@NonVendorQuery //TODO: Review Query
    public void updateComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);

	@NonVendorQuery //TODO: Review Query
    public void deleteComponentVisibilityOverrides(@Param("visiblityOverrideId") int visiblityOverrideId);

	@NonVendorQuery //TODO: Review Query
    public ComponentVisibilityOverride getComponentVisibilityOverridesById(@Param("visiblityOverrideId") int visiblityOverrideId);

	@NonVendorQuery //TODO: Review Query
    public ComponentVisibilityOverride  checkComponentVisibilityOverrideExist(ComponentVisibilityOverride componentVisibilityOverride);
    //COMPONENT VISIBILITY OVERRIDES -- End

	@NonVendorQuery //TODO: Review Query
    public List<Component> loadAllAvailableComponents();

	@NonVendorQuery //TODO: Review Query
    public ComponentInfoDetail getComponentInfoDetail(@Param("componentId") int componentId);
	
	@NonVendorQuery //TODO: Review Query
    public ComponentGroup getComponentGroup(@Param("groupId") int groupId);

	@NonVendorQuery //TODO: Review Query
    public void copyCorpComponentGroupRow(@Param("groupId") int groupId);
    
	@NonVendorQuery //TODO: Review Query
	public void copyCorpComponentRow(@Param("componentId") int componentId);

	@NonVendorQuery //TODO: Review Query
    public void markTemplateForRebuild(@Param("templateId") int templateId);

	@NonVendorQuery //TODO: Review Query
    public void insertTemplateForRegen(@Param("templateId") int templateId);
}
