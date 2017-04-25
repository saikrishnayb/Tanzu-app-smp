package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	public  List<ComponentVisibility> getComponent();
	
	public List<ComponentVisibility> getCategory(@Param("componentId")int componentId);
	
	public List<ComponentVisibility> getVehicleCategory(@Param("componentId")int componentId);
	
	public List<SubCategory>getComponentSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);
	
	public List<SubCategory>getVehicleSubCategory(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId);
	
	public List<ComponentVisibility> getComponentName();
	
	public void deleteComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public void deleteVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public void addComponentVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public void addVehicleVisibility(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public ComponentVisibility getComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public ComponentVisibility getVehicleComponentDetails(@Param("componentId")int componentId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

	public List<PoCategory> getCategoryList();

	public List<SubCategory> getSubCategoryList(int poCategoryId);

	public List<ComponentVisibility> getComponentList(int poCategoryId);
	
	
	//Template Page --- start
	
	public List<Template> getAllTemplates();
	
	public List<TemplatePoAssociation> getAllPoAssociation();
	
	public Template getTemplatesById(@Param("templateID")int templateID);
	
	public List<Components> getAllComponent();
	
	public void addTemplate(Template template);
	
	public void addTemplateComponents(Components components);
	
	public void updateTemplate(Template template);
	
	public void deleteTemplate(@Param("templateID")int templateID);
	
	public void deleteTemplateComponents(@Param("templateID")int templateID);
	
	public List<Components> getTemplateComponentById(@Param("templateID")int templateID);
	
	public List<Integer> findTemplateExist(Template template);
	
	public List<TemplatePoAssociation> getAllPoAssociationForAdd();
	
	public List<TemplatePoAssociation> getAllPoAssociationForEdit(@Param("assocId")int assocId);
		//Template Page --- End
	//COMPONENT VISIBILITY OVERRIDES -- start
	
	public List<ComponentVisibilityOverride> getAllComponentVisibilityOverrides();
	
	public void addComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);
	
	public void updateComponentVisibilityOverrides(ComponentVisibilityOverride componentVisibilityOverride);
	
	public void deleteComponentVisibilityOverrides(@Param("visiblityOverrideId") int visiblityOverrideId);
	
	public ComponentVisibilityOverride getComponentVisibilityOverridesById(@Param("visiblityOverrideId") int visiblityOverrideId);
	
	public ComponentVisibilityOverride  checkComponentVisibilityOverrideExist(ComponentVisibilityOverride componentVisibilityOverride);
	//COMPONENT VISIBILITY OVERRIDES -- End

	public List<Component> loadAllAvailableComponents();

    public ComponentInfoDetail getComponentInfoDetail(@Param("componentId") int componentId);
    public ComponentGroup getComponentGroup(@Param("groupId") int groupId);

    public void copyCorpComponentGroupRow(@Param("groupId") int groupId);
    public void copyCorpComponentRow(@Param("componentId") int componentId);
}
