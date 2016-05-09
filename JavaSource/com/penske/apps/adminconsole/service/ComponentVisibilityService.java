package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This interface is used for visibility by category page
 */
public interface ComponentVisibilityService {

	public List<ComponentVisibility> getComponent();
	
	public List<ComponentVisibility> getCategory(int componenetId);
	
	public List<SubCategory>getComponentSubCategory(int componentId,int poCategoryId);
	
	public List<SubCategory>getVehicleSubCategory(int componentId,int poCategoryId);
	
	public List<ComponentVisibility> getVehicleCategory(int componentId);
	
	public List<ComponentVisibility> getComponentName();
	
	public void deleteComponentVisibility(int componentId,int poCategoryId,int subCategoryId);
	
	public void deleteVehicleVisibility(int componentId,int poCategoryId,int subCategoryId);
	
	public void addComponentVisibility(int componentId,int poCategoryId,int subCategoryId);

	public void addVehicleVisibility(int componentId,int poCategoryId,int subCategoryId);
	
	public ComponentVisibility getComponentDetails(int componentId,int poCategoryId,int subCategoryId);
	
	public ComponentVisibility getVehicleComponentDetails(int componentId,int poCategoryId,int subCategoryId);

	public List<PoCategory> getCategoryList();

	public List<SubCategory> getSubCategoryList(int poCategoryId);

	public List<ComponentVisibility> getComponentList(int poCategoryId);
}
