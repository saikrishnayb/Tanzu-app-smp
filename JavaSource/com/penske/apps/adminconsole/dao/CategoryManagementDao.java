package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in category-management-mapper 
 * that are used for categor management /category association pages
 */

public interface CategoryManagementDao {

	public List<SubCategory> getAllSubCategories();
	
	public PoCategory getSelectedPoCategory(int poCategoryId);
	
	public void updatePoCategory(PoCategory category);
	
	public void insertPoCategory(PoCategory category);
	
	public SubCategory getSelectedSubCategory(int subCategoryId);
	
	public void updateSubCategory(SubCategory category);
	
	public void insertSubCategory(SubCategory category);
	
	public List<CategoryAssociation> getAllCategoryAssociation();

	public List<SubCategory> getSubCategories(int poCategoryId);
	
	public void addCategoryAssociation(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	public CategoryAssociation getNewCategoryAssociation(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	public PoCategory getMaxCategoryId();
	
	public int getMaxSubCategoryId();

	public void modifyPoCatStatus(@Param("poCatId")  int poCatId,@Param("status") String status);
	
	public void modifyPoCatAssocStatus(@Param("poCatId")  int poCatId,@Param("status") String status);
	
	public void modifySubCatStatus(@Param("subCatId") int subCatId,@Param("status") String status);
	
	public void modifySubCatAssocStatus(@Param("subCatId") int subCatId,@Param("status") String status);

	public void modifyAssociationStatus(@Param("assId") int assId,@Param("status") String status);
	
	public int getAssociationExist(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	public int getAnyOtherAssociationExist(@Param("poCatId")  int poCatId,@Param("subCatId") int subCatId,@Param("assId") int assId);
	
	public PoCategory getPoCategoryByName(PoCategory category);
	
	public SubCategory getSubCategoryByName(SubCategory subCategory);
	
}
