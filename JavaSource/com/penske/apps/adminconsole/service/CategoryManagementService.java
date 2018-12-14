package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This interface is used for categorymanagement and category association pages
 */
public interface CategoryManagementService {

	public List<SubCategory> getAllSubCategories();
	
	public SubCategory getSelectedSubCategory(int subCategoryId);
	
	public void updateSubCategory(SubCategory category);
	
	public void insertSubCategory(SubCategory category);
	
	public List<CategoryAssociation> getAllCategoryAssociation();

	public List<SubCategory> getSubCategories(int poCategoryId);
	
	public void addCategoryAssociation(CategoryAssociation addAssociationForm);
	
	public int getMaxSubCategoryId();

	void modifySubCatStatus(int subCatId);

	public void modifyAssStatus(int assId,String status,int poCatId, int subCatId);
	
	public boolean checkSubCategoryExist(SubCategory subCategory,boolean isCreate);

	public CategoryAssociation getEditCategoryAssociation(int associationId);

	public void updateCategoryAssociation(CategoryAssociation addAssociationForm);
}
