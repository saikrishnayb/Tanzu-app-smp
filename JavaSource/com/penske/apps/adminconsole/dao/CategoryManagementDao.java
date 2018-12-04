package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in category-management-mapper
 * that are used for categor management /category association pages
 */

public interface CategoryManagementDao {

    @NonVendorQuery
    public List<SubCategory> getAllSubCategories();

    @NonVendorQuery
    public PoCategory getSelectedPoCategory(int poCategoryId);

    @NonVendorQuery
    public void updatePoCategory(PoCategory category);

    @NonVendorQuery
    public void insertPoCategory(PoCategory category);

    @NonVendorQuery
    public SubCategory getSelectedSubCategory(int subCategoryId);

    @NonVendorQuery
    public void updateSubCategory(SubCategory category);

    @NonVendorQuery
    public void insertSubCategory(SubCategory category);

    @NonVendorQuery
    public List<CategoryAssociation> getAllCategoryAssociation();

    @NonVendorQuery
    public List<SubCategory> getSubCategories(int poCategoryId);

    @NonVendorQuery
    public void addCategoryAssociation(CategoryAssociation addAssociationForm);

    @NonVendorQuery
    public PoCategory getMaxCategoryId();

    @NonVendorQuery
    public int getMaxSubCategoryId();

    @NonVendorQuery
    public void modifyPoCatStatus(@Param("poCatId")  int poCatId,@Param("status") String status);

    @NonVendorQuery
    public void modifyPoCatAssocStatus(@Param("poCatId")  int poCatId,@Param("status") String status);

    @NonVendorQuery
    public void modifySubCatStatus(@Param("subCatId") int subCatId,@Param("status") String status);

    @NonVendorQuery
    public void modifySubCatAssocStatus(@Param("subCatId") int subCatId,@Param("status") String status);

    @NonVendorQuery
    public void modifyAssociationStatus(@Param("assId") int assId,@Param("status") String status);

    @NonVendorQuery
    public int getAssociationExist(@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);

    @NonVendorQuery
    public int getAnyOtherAssociationExist(@Param("poCatId")  int poCatId,@Param("subCatId") int subCatId,@Param("assId") int assId);

    @NonVendorQuery
    public PoCategory getPoCategoryByName(PoCategory category);

    @NonVendorQuery
    public SubCategory getSubCategoryByName(SubCategory subCategory);

    @NonVendorQuery
	public CategoryAssociation getEditCategoryAssociation(int associationId);

    @NonVendorQuery
	public void updateCategoryAssociation(CategoryAssociation addAssociationForm);
}
