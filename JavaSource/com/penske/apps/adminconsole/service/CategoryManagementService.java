package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This interface is used for categorymanagement and category association pages
 */
public interface CategoryManagementService {

	public List<SubCategory> getAllSubCategories();
	
	public PoCategory getSelectedPoCategory(int poCategoryId);

	
	public void updatePoCategory(PoCategory category);
	
	public void insertPoCategory(PoCategory category);
	
	public SubCategory getSelectedSubCategory(int subCategoryId);
	
	public void updateSubCategory(SubCategory category);
	
	public void insertSubCategory(SubCategory category);
	
	public List<CategoryAssociation> getAllCategoryAssociation();

	public List<SubCategory> getSubCategories(int poCategoryId);
	
	public void addCategoryAssociation(CategoryAssociation addAssociationForm);
	
	public PoCategory getMaxCategoryId();
	
	public int getMaxSubCategoryId();

	public void modifyPoCatStatus(int poCatId);

	void modifySubCatStatus(int subCatId);

	public void modifyAssStatus(int assId,String status,int poCatId, int subCatId);
	
	public int getAssociationExist(int poCategoryId, int subCategoryId);
	
	public boolean checkCategoryExist(PoCategory category,boolean isCreate);
	public boolean checkSubCategoryExist(SubCategory subCategory,boolean isCreate);

	public CategoryAssociation getEditCategoryAssociation(int associationId);

	public void updateCategoryAssociation(CategoryAssociation addAssociationForm);

	
}
