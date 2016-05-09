package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
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
}
