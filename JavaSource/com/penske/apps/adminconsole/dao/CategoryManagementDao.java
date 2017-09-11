package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
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

	@NonVendorQuery //TODO: Review Query
	public List<SubCategory> getAllSubCategories();
	
	@NonVendorQuery //TODO: Review Query
	public PoCategory getSelectedPoCategory(int poCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void updatePoCategory(PoCategory category);
	
	@NonVendorQuery //TODO: Review Query
	public void insertPoCategory(PoCategory category);
	
	@NonVendorQuery //TODO: Review Query
	public SubCategory getSelectedSubCategory(int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateSubCategory(SubCategory category);
	
	@NonVendorQuery //TODO: Review Query
	public void insertSubCategory(SubCategory category);
	
	@NonVendorQuery //TODO: Review Query
	public List<CategoryAssociation> getAllCategoryAssociation();

	@NonVendorQuery //TODO: Review Query
	public List<SubCategory> getSubCategories(int poCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void addCategoryAssociation(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public CategoryAssociation getNewCategoryAssociation(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public PoCategory getMaxCategoryId();
	
	@NonVendorQuery //TODO: Review Query
	public int getMaxSubCategoryId();

	@NonVendorQuery //TODO: Review Query
	public void modifyPoCatStatus(@Param("poCatId")  int poCatId,@Param("status") String status);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyPoCatAssocStatus(@Param("poCatId")  int poCatId,@Param("status") String status);
	
	@NonVendorQuery //TODO: Review Query
	public void modifySubCatStatus(@Param("subCatId") int subCatId,@Param("status") String status);
	
	@NonVendorQuery //TODO: Review Query
	public void modifySubCatAssocStatus(@Param("subCatId") int subCatId,@Param("status") String status);

	@NonVendorQuery //TODO: Review Query
	public void modifyAssociationStatus(@Param("assId") int assId,@Param("status") String status);
	
	@NonVendorQuery //TODO: Review Query
	public int getAssociationExist(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public int getAnyOtherAssociationExist(@Param("poCatId")  int poCatId,@Param("subCatId") int subCatId,@Param("assId") int assId);
	
	@NonVendorQuery //TODO: Review Query
	public PoCategory getPoCategoryByName(PoCategory category);
	
	@NonVendorQuery //TODO: Review Query
	public SubCategory getSubCategoryByName(SubCategory subCategory);
}
