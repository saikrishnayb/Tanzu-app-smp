package com.penske.apps.adminconsole.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.Manufacture;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.model.VendorTemplateSearch;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.adminconsole.service.ComponentVisibilityService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;


/**
 * Controller handling all mapping and functionality for the Admin Console Components sub tab
 * @author erik.munoz 600139451
 * @author 600144005
 */
@DefaultController
@RequestMapping("/admin-console/components")
public class ComponentsController {
	
	@Autowired
	private ComponentVisibilityService componentVisibilityService;
	
	@Autowired
	private ComponentVendorTemplateService componentVendorTemplateService;
	
	@Autowired
	private CategoryManagementService categoryManagementService;
	
	
	@RequestMapping(value={"/visibility-by-category"})
	public ModelAndView getVisisbilityByCategoryPage(){
		
		List<ComponentVisibility> componentList = componentVisibilityService.getComponent();
		ModelAndView modelAndView = new ModelAndView("/admin-console/components/visibility-by-category");
		modelAndView.addObject("componentList",componentList);
		return modelAndView;
		
	}	
	
	@RequestMapping("/templates")
	public ModelAndView getTemplatesPage(){
		
		List<VendorTemplate> templates = componentVendorTemplateService.getVendorTemplates();
		List<String> manufactures = componentVendorTemplateService.getAllTemplateManufactures();
		List<PoCategory> categories = componentVendorTemplateService.getTemplatePoCategory();
		
		ModelAndView mav =new ModelAndView("/admin-console/components/templates");
		mav.addObject("vendorTemplate",templates);
		mav.addObject("manufactures",manufactures);
		mav.addObject("categories",categories);
		return mav;
	}
	
	@RequestMapping(value="/category-management")
	public ModelAndView getCategoryByManagementPage(){
		
		List<PoCategory> categoryList = componentVendorTemplateService.getPoCategories();
		ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-management");
		modelAndView.addObject("categoryList",categoryList);
		
		List<SubCategory> subCategoryList = categoryManagementService.getAllSubCategories();
		modelAndView.addObject("subCategoryList",subCategoryList);
		return modelAndView;
		
	}	
	
	
	@RequestMapping(value="/category-association")
	public ModelAndView getCategoryAssociationPage(HttpSession session){
		
		List<CategoryAssociation> categoryAssociations = categoryManagementService.getAllCategoryAssociation();
		ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-association");
		modelAndView.addObject("categoryAssociation",categoryAssociations);
		modelAndView.addObject("access",CommonUtils.hasAccess(ApplicationConstants.COMPONENTS, session));
		return modelAndView;
		
	}	
	
	@RequestMapping(value="vendor-template-search")
	public ModelAndView vendorTemplateSearch(@ModelAttribute("command") VendorTemplateSearch template) {
		
	
		List<VendorTemplate> templates = componentVendorTemplateService.selectVenderBySearchCriteria(template);
		List<String> manufactures = componentVendorTemplateService.getAllTemplateManufactures();
		List<PoCategory> categories = componentVendorTemplateService.getTemplatePoCategory();
		ModelAndView mav =new ModelAndView("/admin-console/components/templates");
		
		mav.addObject("searchForm", template);
		mav.addObject("vendorTemplate",templates);
		mav.addObject("manufactures",manufactures);
		mav.addObject("categories",categories);
		
		return mav;
		
	}
	
	@RequestMapping("/create-template")
	public ModelAndView getCreateTemplatePage(){
	
		List<Manufacture> manufacture = componentVendorTemplateService.getManufacture();
		ModelAndView mav =new ModelAndView("/admin-console/components/create-template");
		mav.addObject("manufacture",manufacture);
		return mav;
	}
	
	
	
	
	

	
	
}
