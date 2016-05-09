package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Manufacture;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.model.VendorTemplateSearch;
/**
 * 
 * @author 600144005
 * This interface is used for templates page
 */
public interface ComponentVendorTemplateService {

	public List<VendorTemplate> getVendorTemplates() ;
	
	public List<VendorTemplate> getVendorCategories(int vendorNumber,String corpCode);
	
	public List<VendorTemplate> selectVenderBySearchCriteria(VendorTemplateSearch template);
	
	public List<VendorTemplate> selectVenderByCategory(List<VendorTemplate> templates,List<TemplatePoCategorySubCategory> searchCategorySubCategory);
	
	public List<VendorTemplate> selectVenderByMfr(List<VendorTemplate> templates,List<TemplatePoCategorySubCategory> searchCategorySubCategory);
	
	public VendorTemplate getVendorTemplate(int templateId);
	
	public List<Manufacture> getManufacture();
	
	public TemplatePoCategorySubCategory getDeleteModalContent(int poCategoryId,int subCategoryId);
	
	public void  deleteCategory(int poCategoryId,int subCategoryId,int templateId);
	
	public List<PoCategory> getPoCategories();
	
	public List<SubCategory> getSubCategories(int poCategoryId);
	
	public TemplatePoCategorySubCategory getPoCategorySubCategory(int poCategoryId,int subCategoryId);
	
	public void addTemplate(int vendorNumber, String createdBy);
	
	public VendorTemplate getTemplateId( int vendorNumber,String corpCode);
	
	public void addTemplateComponents(TemplateComponents templateComponents,int poCategoryId, int subCategoryId);
	
	public void updateTemplateComponents(TemplateComponents templateComponents);
	
	public List<String> getAllTemplateManufactures();
	
	public void deleteTemplate(int templateId);
	
	public void deleteVendorTemplate(int templateId);
	
	public List<PoCategory> getTemplatePoCategory();
	
	public int getTemplateComponentCount(int templateId);

	
	
	public TemplatePoCategorySubCategory getDeleteInEditModalContent(
			int templateId, int poCategoryId, int subCategoryId);

	public List<Integer> getVendorNumberByMfr(String mfr);
	//public TemplatePoCategorySubCategory getDeleteInEditModalContent(int templateId, int poCategoryId, int subCategoryId);

}	

