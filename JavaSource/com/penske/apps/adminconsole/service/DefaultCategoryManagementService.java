package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.CategoryManagementDao;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.SubCategory;
/**
 * 
 * @author 600144005
 * This class has service methods of category management and category association page
 */
@Service
public class DefaultCategoryManagementService implements CategoryManagementService {
	@Autowired
	private  CategoryManagementDao categoryDao;
	
	@Override
	public List<SubCategory> getAllSubCategories() {
		List<SubCategory> subCategoryList = categoryDao.getAllSubCategories();
		return subCategoryList;
	}
	
	@Override
	public SubCategory getSelectedSubCategory(int subCategoryId) {
		
		SubCategory category = categoryDao.getSelectedSubCategory(subCategoryId);
		return category;
	}

	@Override
	public void updateSubCategory(SubCategory category) {
		String associateStatus="1";
		if(category !=null && category.getStatus() !=null && category.getStatus().equalsIgnoreCase("I")){
			associateStatus="0";
		}
		categoryDao.updateSubCategory(category);
		categoryDao.modifySubCatAssocStatus(category.getSubCategoryId(),associateStatus);
		
	}

	@Override
	public void insertSubCategory(SubCategory category) {
		categoryDao.insertSubCategory(category);
		
	}

	@Override
	public List<CategoryAssociation> getAllCategoryAssociation() {
		List<CategoryAssociation> categoryAssociations =categoryDao.getAllCategoryAssociation();
		return categoryAssociations;
	}

	@Override
	public List<SubCategory> getSubCategories(int poCategoryId) {
		List<SubCategory> subCategory = categoryDao.getSubCategories(poCategoryId);
		return subCategory;
	}

	@Override
	public void addCategoryAssociation(CategoryAssociation addAssociationForm) {
		categoryDao.addCategoryAssociation(addAssociationForm);
	}

	@Override
	public CategoryAssociation getEditCategoryAssociation(int associationId) {
		CategoryAssociation categoryAssociation = categoryDao.getEditCategoryAssociation(associationId);
		return categoryAssociation;
	}

	@Override
	public int getMaxSubCategoryId() {
		int subCategoryId =categoryDao.getMaxSubCategoryId();
		return subCategoryId;
	}

	@Override
	public void modifySubCatStatus(int subCatId) {
		categoryDao.modifySubCatStatus(subCatId,"I");
		categoryDao.modifySubCatAssocStatus(subCatId,"0");
	}

	@Override
	public void modifyAssStatus(int assId,String status,int poCatId, int subCatId) {
		/*if(status !=null && status.equalsIgnoreCase("0")){ // Association Deactivate
			if(categoryDao.getAnyOtherAssociationExist(poCatId, subCatId, assId)==0){
				categoryDao.modifyPoCatStatus(poCatId,"I");
				categoryDao.modifySubCatStatus(subCatId,"I");
				categoryDao.modifyAssociationStatus(assId,status);
			}else{
				categoryDao.modifyAssociationStatus(assId,status);
			}
		}
		else if(status !=null && status.equalsIgnoreCase("1")){ // Association Activate
			categoryDao.modifyPoCatStatus(poCatId,"A");
			categoryDao.modifySubCatStatus(subCatId,"A");
			categoryDao.modifyAssociationStatus(assId,status);
		}else{
			categoryDao.modifyAssociationStatus(assId,status);
		}*/
		categoryDao.modifyAssociationStatus(assId,status);
	}

	@Override
	public boolean checkSubCategoryExist(SubCategory subCategory,
			boolean isCreate) {
		SubCategory selCategory=categoryDao.getSubCategoryByName(subCategory);
		boolean returnFlg=true;
		if(selCategory !=null){
			if(isCreate){
				returnFlg= false;
			}else{
				if(selCategory.getSubCategoryId() !=subCategory.getSubCategoryId()){
					returnFlg= false;
				}
			}
		}
		return returnFlg;
	}

	@Override
	public void updateCategoryAssociation(CategoryAssociation addAssociationForm) {
		categoryDao.updateCategoryAssociation(addAssociationForm);
		
	}
	
}
