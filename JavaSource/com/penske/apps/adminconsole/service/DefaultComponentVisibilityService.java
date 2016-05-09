package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This class has service methods of visiblity by category page
 */

@Service
public class DefaultComponentVisibilityService implements ComponentVisibilityService {

	
	@Autowired
	private ComponentDao componentDao;
	
	
	@Override
	public List<ComponentVisibility> getComponent() {
		List<ComponentVisibility> componentList = componentDao.getComponent();
				
		return componentList;
	}


	@Override
	public List<ComponentVisibility> getCategory(int componentId) {
		List<ComponentVisibility> categoryList = componentDao.getCategory(componentId);
		return categoryList;
	}
	
	@Override
	public List<ComponentVisibility> getVehicleCategory(int componentId) {
		List<ComponentVisibility> categoryList = componentDao.getVehicleCategory(componentId);
		return categoryList;
		
	}
	
	@Override
	public List<ComponentVisibility> getComponentName() {
		List<ComponentVisibility> componentNames = componentDao.getComponentName();;
		return componentNames;
	}


	

	@Override
	public ComponentVisibility getComponentDetails(int componentId,int poCategoryId, int subCategoryId) {
		
		ComponentVisibility componentVisibility =  componentDao.getComponentDetails(componentId, poCategoryId, subCategoryId);
		return componentVisibility;
		
	}


	@Override
	public ComponentVisibility getVehicleComponentDetails(int componentId,int poCategoryId, int subCategoryId) {
		
		ComponentVisibility componentVehicleDetails = componentDao.getVehicleComponentDetails(componentId, poCategoryId, subCategoryId);
		return componentVehicleDetails;
		
	}


	@Override
	public void deleteComponentVisibility(int componentId, int poCategoryId,int subCategoryId) {
		componentDao.deleteComponentVisibility(componentId, poCategoryId, subCategoryId);
	}


	@Override
	public void deleteVehicleVisibility(int componentId, int poCategoryId,int subCategoryId) {
		componentDao.deleteVehicleVisibility(componentId, poCategoryId, subCategoryId);
		
	}


	@Override
	public List<SubCategory> getComponentSubCategory(int componentId,int poCategoryId) {
		List<SubCategory> subCategories = componentDao.getComponentSubCategory(componentId, poCategoryId);
		return subCategories;
	}


	@Override
	public List<SubCategory> getVehicleSubCategory(int componentId,int poCategoryId) {
		List<SubCategory> subCategories =componentDao.getVehicleSubCategory(componentId, poCategoryId);
		return subCategories;
	}


	@Override
	public void addComponentVisibility(int componentId, int poCategoryId,int subCategoryId) {
		componentDao.addComponentVisibility(componentId, poCategoryId, subCategoryId);
		
	}


	@Override
	public void addVehicleVisibility(int componentId, int poCategoryId,int subCategoryId) {
		componentDao.addVehicleVisibility(componentId, poCategoryId, subCategoryId);
		
	}


	@Override
	public List<PoCategory> getCategoryList() {
		// TODO Auto-generated method stub
		return componentDao.getCategoryList();
	}


	@Override
	public List<SubCategory> getSubCategoryList(int poCategoryId) {
		// TODO Auto-generated method stub
		return componentDao.getSubCategoryList(poCategoryId);
	}


	@Override
	public List<ComponentVisibility> getComponentList(int poCategoryId) {
		// TODO Auto-generated method stub
		return componentDao.getComponentList(poCategoryId);
	}


	



	
	
	
}
