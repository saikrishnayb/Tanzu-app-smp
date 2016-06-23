package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.ComponentVendorTemplateDao;
import com.penske.apps.adminconsole.model.CorpCode;
import com.penske.apps.adminconsole.model.Manufacture;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorLocation;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.model.VendorTemplateSearch;
/**
 * 
 * @author 600144005
 * class has all service methods of templates page
 */

@Service
public class DefaultComponentVendorTemplateService implements ComponentVendorTemplateService {

	
	@Autowired
	private  ComponentVendorTemplateDao templateDao;

	private static Logger logger = Logger.getLogger(DefaultComponentVendorTemplateService.class);
	
	@Override
	public List<VendorTemplate> getVendorTemplates() {
		
		
		List<TemplatePoCategorySubCategory> poCategorySubCategory = templateDao.getTemplatePoCategorySubCategory();
		List<VendorTemplate> templates = templateDao.getVendorTemplates();
		
		for (VendorTemplate vendorTemplate : templates) {
			
			List<TemplatePoCategorySubCategory> category =new ArrayList<TemplatePoCategorySubCategory>();
			for (TemplatePoCategorySubCategory tempPoSub : poCategorySubCategory) {
				
				if(vendorTemplate.getTemplateId()==tempPoSub.getTemplateId()) {
				category.add(tempPoSub);
				}
				
			}
		vendorTemplate.setTemplatePoCategorySubCategory(category);
		}
		
		return templates;
	}

	@Override
	public List<Manufacture> getManufacture() {
		List<Manufacture> manufactures = templateDao.getManufacture();
		
		try{
		for (Manufacture manufacture : manufactures) {
//			System.out.println("manufacture>>>>>>>>"+manufacture);
			List<CorpCode> codes= templateDao.getCorpCodes(manufacture.getManufacture());
			List<CorpCode> corpCodes =new ArrayList<CorpCode>();
			
			for (CorpCode corpCode : codes) {
//				System.out.println("corpCode>>>>>>>>"+corpCode);
				List<VendorLocation> locations= templateDao.getVendorLocation(manufacture.getManufacture(), corpCode.getCorpCode());
//				System.out.println("locations>>>>>>>>"+locations);
				corpCode.setVendorLocation(locations);
				
				if(manufacture.getManufacture().equals(corpCode.getManufacture())) {
					corpCodes.add(corpCode);
				}
			}
				
			manufacture.setCorpCodes(corpCodes);
		}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return manufactures;
	}

	//get vendor template for edit template modal
	@Override
	public List<VendorTemplate> getVendorCategories(int vendorNumber,String corpCode) {
		//get all categories from template components table
		List<TemplatePoCategorySubCategory> poCategorySubCategory = templateDao.getTemplatePoCategorySubCategory();
		//get selected vendor from vendor template tabl
		List<VendorTemplate> templates = templateDao.getVendorCategories(vendorNumber,corpCode);
		
		for (VendorTemplate vendorTemplate : templates) {
			
				List<TemplatePoCategorySubCategory> category =new ArrayList<TemplatePoCategorySubCategory>();
				for (TemplatePoCategorySubCategory tempPoSub : poCategorySubCategory) {
				
						List<TemplateComponents> templateComponentss= templateDao.getTemplateComponents(tempPoSub.getPoCategory().getCategoryId(),tempPoSub.getSubCategory().getSubCategoryId(),tempPoSub.getTemplateId());
							
						for (TemplateComponents templateComponents : templateComponentss) {
								if(templateComponents.getDataType().equalsIgnoreCase("smc_component_info")) {
									//getting component names 
									TemplateComponents component = templateDao.getComponentName(templateComponents.getComponentId());
									templateComponents.setComponentName(component.getComponentName());
								}
								else {
									TemplateComponents component = templateDao.getVehicleComponentName(templateComponents.getComponentId());
									templateComponents.setComponentName(component.getComponentName());
								}
						}	
					
						tempPoSub.setTemplateComponents(templateComponentss);
						
						if(vendorTemplate.getTemplateId()==tempPoSub.getTemplateId()) {
							
						category.add(tempPoSub);
						}
				
			   }
			   vendorTemplate.setTemplatePoCategorySubCategory(category);
		}
		return templates;
	}

	@Override
	public TemplatePoCategorySubCategory getDeleteModalContent(
			int poCategoryId, int subCategoryId) {
	
		TemplatePoCategorySubCategory modalContent = templateDao.getDeleteModalContent(poCategoryId, subCategoryId);
		return modalContent;
	}

	@Override
	public void deleteCategory(int poCategoryId, int subCategoryId,
			int templateId) {
		
		templateDao.deleteCategory(poCategoryId, subCategoryId, templateId);
		
	}

	@Override
	public List<PoCategory> getPoCategories() {
		
		List<PoCategory> poCategories =templateDao.getPoCategories();
		return poCategories;
	}

	@Override
	public List<SubCategory> getSubCategories(int poCategoryId) {
		List<SubCategory> subCategories =templateDao.getSubCategories(poCategoryId);
		return subCategories;
	}

	@Override
	public TemplatePoCategorySubCategory getPoCategorySubCategory(
			int poCategoryId, int subCategoryId) {
		
			TemplatePoCategorySubCategory poCategorySubCategory = templateDao.getPoCategorySubCategory(poCategoryId, subCategoryId);
		
			List<TemplateComponents> templateComponentss= templateDao.getCategoryComponents(poCategoryId, subCategoryId);
			
			poCategorySubCategory.setTemplateComponents(templateComponentss);
			
			return poCategorySubCategory;
	}

	@Override
	public VendorTemplate getVendorTemplate(int templateId) {
		
		VendorTemplate vendorTemplate = templateDao.getVendorTemplate(templateId);
		return vendorTemplate;
	}

	@Override
	public void addTemplate(int vendorNumber, String createdBy) {
		templateDao.addTemplate(vendorNumber, createdBy);
		
	}

	@Override
	public VendorTemplate getTemplateId(int vendorNumber, String corpCode) {
		VendorTemplate template =templateDao.getTemplateId(vendorNumber, corpCode);
		return template;
	}

	@Override
	public void addTemplateComponents(TemplateComponents templateComponents,
			int poCategoryId, int subCategoryId) {
		
		templateDao.addTemplateComponents(templateComponents, poCategoryId, subCategoryId);
		
	}

	@Override
	public void updateTemplateComponents(TemplateComponents templateComponents) {
		templateDao.updateTemplateComponents(templateComponents);
		
	}

	@Override
	public List<String> getAllTemplateManufactures() {
		List<String> manufactures=templateDao.getAllTemplateManufactures();
		return manufactures;
	}

	@Override
	public void deleteTemplate(int templateId) {
	
		templateDao.deleteTemplateComponents(templateId);
		templateDao.deleteTemplate(templateId);
	}
	@Override
	public void deleteVendorTemplate(int templateId) {
		templateDao.deleteTemplate(templateId);
		
	}
	@Override
	public List<VendorTemplate> selectVenderBySearchCriteria(VendorTemplateSearch template) {
		
	
		List<TemplatePoCategorySubCategory> searchCategorySubCategory = templateDao.getSearchTemplatePoCategorySubCategory(template.getPoCategoryId(), template.getSubCategoryId());
		
		List<VendorTemplate> templates = templateDao.selectVenderBySearchCriteria(template);
		List<VendorTemplate> resultTemplates;
		
		if(template.getPoCategoryId()== 0)
		
		{
			resultTemplates =selectVenderByMfr(templates, searchCategorySubCategory);
			
		}
		
		else {
			resultTemplates=selectVenderByCategory(templates,searchCategorySubCategory);
		}
		
		
		return resultTemplates;
	}

	@Override
	public List<VendorTemplate> selectVenderByCategory(List<VendorTemplate> templates,List<TemplatePoCategorySubCategory> searchCategorySubCategory) {
		Iterator<VendorTemplate> itr = templates.iterator();
	
		
		while(itr.hasNext()) {
				VendorTemplate vendorTemplate =itr.next();
				Iterator<TemplatePoCategorySubCategory> categoryItr = searchCategorySubCategory.iterator();
				List<TemplatePoCategorySubCategory> category =new ArrayList<TemplatePoCategorySubCategory>();
			boolean isTemplateMatch =false;
				
				while(categoryItr.hasNext()) {
						TemplatePoCategorySubCategory tempPoSub = categoryItr.next();
						if(vendorTemplate.getTemplateId() == tempPoSub.getTemplateId() ) {
							category.add(tempPoSub);
							isTemplateMatch=true;
						}
						
				}
				if(isTemplateMatch==false) {
					itr.remove();
				}
				else {
					vendorTemplate.setTemplatePoCategorySubCategory(category);
				}
		}
		
		return templates;
		
	}

	@Override
	public List<VendorTemplate> selectVenderByMfr(List<VendorTemplate> templates,List<TemplatePoCategorySubCategory> searchCategorySubCategory) {
		
		for (VendorTemplate vendorTemplate : templates) {
			
			List<TemplatePoCategorySubCategory> category =new ArrayList<TemplatePoCategorySubCategory>();
			for (TemplatePoCategorySubCategory tempPoSub : searchCategorySubCategory) {
			
				if(vendorTemplate.getTemplateId()==tempPoSub.getTemplateId()) {
					category.add(tempPoSub);
				}
			}
			vendorTemplate.setTemplatePoCategorySubCategory(category);
		}
		return templates;
	}
	
	
	
	@Override
	public List<PoCategory> getTemplatePoCategory() {
		List<PoCategory> categories=templateDao.getTemplatePoCategory();
		
		return categories;
	}

	
	@Override
	public TemplatePoCategorySubCategory getDeleteInEditModalContent(
			int templateId, int poCategoryId, int subCategoryId) {
		TemplatePoCategorySubCategory category =templateDao.getDeleteInEditModalContent(templateId, poCategoryId, subCategoryId);
		return category;
	}

	@Override
	public int getTemplateComponentCount(int templateId) {
	
		int templateComponentCount = templateDao.getTemplateComponentCount(templateId);
		return templateComponentCount;
	}

	@Override
	public List<Integer> getVendorNumberByMfr(String mfr) {
		List<Integer> vendorNumbers =templateDao.getVendorNumberByMfr(mfr);
		return vendorNumbers;
		
	}

	

	

	
	

	
}
