package com.penske.apps.adminconsole.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.adminconsole.service.ComponentVisibilityService;
import com.penske.apps.adminconsole.util.ApplicationConstants;

/**
 * 
 * @author 600144005 
 * 
 * This Controller class contains all of the AJAX request methods for all pages under components tab
 * 
 * 
 */

@Controller
@RequestMapping("/admin-console/components")
public class ComponentsRestController {

	//////////////////////////////////////////////////////////////////////
	// Service Members
	@Autowired
	private ComponentVisibilityService componentVisibilityService;
	@Autowired
	private ComponentVendorTemplateService componentVendorTemplateService;
	//////////////////////////////////////////////////////////////////////
	
	@Autowired
	private CategoryManagementService categoryManagementService;
	
	@RequestMapping(value="get-add-visibility-modal-content")
	@ResponseBody
	public ModelAndView getAddVisibilityModalContent(){
		
		List<ComponentVisibility> componentNames = componentVisibilityService.getComponentName();
		List<PoCategory> categoriesList = componentVisibilityService.getCategoryList();
		ModelAndView modelAndView = new ModelAndView("/jsp-fragment/admin-console/components/add-visibility-modal-content");
		modelAndView.addObject("categoriesList",categoriesList);
		modelAndView.addObject("componentNames",componentNames);
		return modelAndView;
		
	}
	
	
	@RequestMapping(value="get-compnent-list")
	@ResponseBody
	public List<ComponentVisibility>  getComponentList(@RequestParam("poCategoryId")int poCategoryId){
		
		List<ComponentVisibility> componentList = componentVisibilityService.getComponentList(poCategoryId);
		return componentList;
		
	}
	
	@RequestMapping(value="post-save-visibility")
	@ResponseBody
	public void postSaveVisibility(@RequestParam("isComponentVehicle") String isComponentVehicle , @RequestParam("componentId") int componentId,@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId){
		
		
		if("Yes".equalsIgnoreCase(isComponentVehicle))
		{
			componentVisibilityService.addComponentVisibility(componentId, poCategoryId, subCategoryId);
		}
		else {
			componentVisibilityService.addVehicleVisibility(componentId, poCategoryId, subCategoryId);
		}
	}
	
	@RequestMapping(value="get-po-categories")
	@ResponseBody
	public List<ComponentVisibility> getPoCategories(@RequestParam("isComponentVehicle") String isComponentVehicle,@RequestParam("componentId") int componentId){
		boolean isComponent = "Yes".equalsIgnoreCase(isComponentVehicle);
		if(isComponent)
		{
		
			List<ComponentVisibility> poCategories = componentVisibilityService.getCategory(componentId);
			return poCategories;
		}
		else {
			List<ComponentVisibility> poCategories = componentVisibilityService.getVehicleCategory(componentId) ;
			return poCategories;
		}
	}
		
	@RequestMapping(value="get-po-sub-categories")
	@ResponseBody
	public List<SubCategory> getPoSubCategories(@RequestParam("poCategoryId")int poCategoryId){
		
		
		
			List<SubCategory> subCategories = componentVisibilityService.getSubCategoryList(poCategoryId);
			return subCategories;
	
	}
		
	@RequestMapping(value="delete-table-component")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public void deleteVisibility(@RequestParam("isComponentVehicle")int isComponentVehicle,@RequestParam("componentId")int componentId,@RequestParam("category")int category,@RequestParam("subCategory")int subCategory)
	{
		
		boolean isComponent = isComponentVehicle==ApplicationConstants.VEHICLE_COMPONENT;
		if(isComponent) {
		 componentVisibilityService.deleteComponentVisibility(componentId, category, subCategory);
		}
		else {
			 componentVisibilityService.deleteVehicleVisibility(componentId, category, subCategory);
		}
	}
	
	@RequestMapping(value="get-delete-modal-content")
	@ResponseBody
	public ModelAndView getDeleteModalContent(@RequestParam("isComponentVehicle")int isComponentVehicle,@RequestParam("componentId")int componentId,@RequestParam("category")int category,@RequestParam("subCategory")int subCategory)
	{
		
		boolean isComponent = isComponentVehicle==ApplicationConstants.VEHICLE_COMPONENT;
		ComponentVisibility componentVisibility = null;
		
		if(isComponent) {
			componentVisibility = componentVisibilityService.getComponentDetails(componentId, category, subCategory);
		}
		else {
			componentVisibility = componentVisibilityService.getVehicleComponentDetails(componentId, category, subCategory);
		}
   	
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/component-delete-modal-content");
		mav.addObject("componentVisibility",componentVisibility);
		return mav;
	}
	
	//templates
	
	@RequestMapping(value="get-vendor-template")
	@ResponseBody
	public ModelAndView getVendorTemplate(@RequestParam("vendorNumber") int vendorNumber,@RequestParam("corpCode")  String corpCode){
		
		List<VendorTemplate> template = componentVendorTemplateService.getVendorCategories(vendorNumber,corpCode);
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-template-modal-content");
		mav.addObject("template",template);
		return mav;
	}
	
	@RequestMapping(value="delete-accordion-category")
	@ResponseBody
	public void deleteCategory(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,@RequestParam("templateId") int templateId)
	{
		
		componentVendorTemplateService.deleteCategory(poCategoryId, subCategoryId, templateId);
		int templateComponentcount = componentVendorTemplateService.getTemplateComponentCount(templateId);
		
		if(templateComponentcount<1) {
			componentVendorTemplateService.deleteVendorTemplate(templateId);
		}
		
		
	}
	
	@RequestMapping(value="delete-category-content")
	@ResponseBody
	public ModelAndView deleteCategoryContent(int poCategoryId,int subCategoryId)
	{
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/category-delete-modal-content");
		TemplatePoCategorySubCategory categories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
		mav.addObject("categories",categories);
		return mav;
		
	}
	
	@RequestMapping(value="delete-category-content-edit")
	@ResponseBody
	public ModelAndView deleteCategoryContentEditModal(int templateId,int poCategoryId,int subCategoryId)
	{
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/template-category-edit-delete-modal-content");
		TemplatePoCategorySubCategory categories = componentVendorTemplateService.getDeleteInEditModalContent(templateId,poCategoryId,subCategoryId);
		mav.addObject("categories",categories);
		return mav;
		
	}
	@RequestMapping(value="get-add-po-categories")
	@ResponseBody
	public ModelAndView getCategories() {
		List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
		mav.addObject("categories",categories);
		return mav;
	}
	
	@RequestMapping(value="get-edit-add-po-categories")
	@ResponseBody
	public ModelAndView getAddCategories() {
		List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
		mav.addObject("categories",categories);
		return mav;
	}
	
	@RequestMapping(value="get-add-po-sub-categories")
	@ResponseBody
	public List<SubCategory> getsubCategories(@RequestParam("poCategoryId") int poCategoryId) {
		List<SubCategory> categories = componentVendorTemplateService.getSubCategories(poCategoryId);
		return categories;
	}
	
	
	@RequestMapping(value="get-category-components")
	@ResponseBody
	public ModelAndView getCategoryComponents(int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
		
		List<TemplatePoCategorySubCategory> poCategorySubCategory =new ArrayList<TemplatePoCategorySubCategory>();
		for (int subCategoryId : subCategoryIds) {
		
			TemplatePoCategorySubCategory poCategorySubCategories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
			poCategorySubCategory.add(poCategorySubCategories);
		}
		
		
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-accordion-content");
		mav.addObject("category",poCategorySubCategory);
		return mav;
		
	}
	
	@RequestMapping(value="get-add-category-components")
	@ResponseBody
	public ModelAndView getAddCategoryComponents(int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
		
		List<TemplatePoCategorySubCategory> poCategorySubCategory =new ArrayList<TemplatePoCategorySubCategory>();
		for (int subCategoryId : subCategoryIds) {
		
			TemplatePoCategorySubCategory poCategorySubCategories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
			poCategorySubCategory.add(poCategorySubCategories);
		}
		
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-accordion-content");
		mav.addObject("category",poCategorySubCategory);
		return mav;
		
	}
	
	@RequestMapping(value="get-delete-template-modal-content")
	@ResponseBody
	public ModelAndView getVendorInfo(int templateId) {
		
		VendorTemplate vendorTemplate =componentVendorTemplateService.getVendorTemplate(templateId);
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/template-delete-modal-content");
		mav.addObject("vendorTemplate",vendorTemplate);
		return mav;
		
	}
	
	@RequestMapping(value="delete-template-table-content")
	@ResponseBody
	public void deleteTemplateTableContent(int templateId) {
		
		componentVendorTemplateService.deleteTemplate(templateId);
	}
	
	@RequestMapping(value="add-template")
	@ResponseBody
	public int addTemplate(@RequestParam("vendorNumber") int vendorNumber,@RequestParam("corpCode") String corpCode, HttpSession session) {
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		componentVendorTemplateService.addTemplate(vendorNumber, currentUser.getSso());
		
		VendorTemplate template =  componentVendorTemplateService.getTemplateId(vendorNumber, corpCode);
		int templateId =template.getTemplateId();
		return templateId;
	}
	
	@RequestMapping(value="get-vendor-numbers-by-mfr")
	@ResponseBody
	public List<Integer> addTemplate(@RequestParam("MFR") String mfr) {
		
		List<Integer> vendorNumbers =  componentVendorTemplateService.getVendorNumberByMfr(mfr);
		return vendorNumbers;
	}
	
	@RequestMapping(value="add-template-components")
	@ResponseBody
	public void addTemplate(TemplateComponents serializedObject,@RequestParam("poCategoryId")int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,@RequestParam("templateId") String templateId) {
	
		componentVendorTemplateService.addTemplateComponents(serializedObject, poCategoryId, subCategoryId);
		
	}
	
	@RequestMapping(value="update-template-components")
	@ResponseBody
	public void updateTemplateComponents(TemplateComponents serializedObject) {
		
		componentVendorTemplateService.updateTemplateComponents(serializedObject);
	}
	
	//CATEGORY MANAGEMENT//
	
	@RequestMapping(value="get-edit-category-content")
	@ResponseBody
	public ModelAndView getEditCategoryContent(@RequestParam("poCategoryId") int poCategoryId) {
		
		PoCategory poCategory =categoryManagementService.getSelectedPoCategory(poCategoryId);
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-category-modal");
		mav.addObject("category", poCategory);
		return mav;
	}
	@RequestMapping(value="add-category-modal-content")
	@ResponseBody
	public ModelAndView addCategoryContent() {
		
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/category-management-add-po-category-modal");
		return mav;
	}
	

	@RequestMapping(value="update-po-category")
	@ResponseBody
	public ModelAndView updatePoCategory(PoCategory categoryData) {
		
		categoryManagementService.updatePoCategory(categoryData);
		return null;
	}
	
	@RequestMapping(value="insert-po-category")
	@ResponseBody
	public PoCategory insertPoCategory(PoCategory categoryData,HttpSession session) {
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		categoryData.setCreatedBy(currentUser.getSso());
		categoryManagementService.insertPoCategory(categoryData);
		PoCategory category =categoryManagementService.getMaxCategoryId();
		return category;
	}
	
	
	@RequestMapping(value="get-edit-sub-category-content")
	@ResponseBody
	public ModelAndView getEditSubCategoryContent(@RequestParam("subCategoryId") int subCategoryId) {
		
		SubCategory subCategory =categoryManagementService.getSelectedSubCategory(subCategoryId);
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-sub-category-modal");
		mav.addObject("category", subCategory);
		return mav;
	}
	
	@RequestMapping(value="update-sub-category")
	@ResponseBody
	public ModelAndView updatesubCategory(SubCategory categoryData) {
		
		categoryManagementService.updateSubCategory(categoryData);
		return null;
	}
	

	@RequestMapping(value="add-sub-category-modal-content")
	@ResponseBody
	public ModelAndView addSubCategoryContent() {
		
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-sub-category-modal");
		return mav;
	}
	
	@RequestMapping(value="insert-sub-category")
	@ResponseBody
	public int insertSubCategory(SubCategory categoryData,HttpSession session) {
		
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		categoryData.setCreatedBy(currentUser.getSso());
		categoryManagementService.insertSubCategory(categoryData);
		int subCategoryId=categoryManagementService.getMaxSubCategoryId();
		return subCategoryId;
	}
	
	@RequestMapping(value="delete-po-category")
	@ResponseBody
	public void deletePoCategory(@RequestParam("poCatId") int poCatId) {
		categoryManagementService.modifyPoCatStatus(poCatId);
	}
	
	@RequestMapping(value="delete-sub-category")
	@ResponseBody
	public void deleteSubCategory(@RequestParam("subCatId") int subCatId) {
		categoryManagementService.modifySubCatStatus(subCatId);
	}
	
	//category association//
	
	
	@RequestMapping(value="get-add-category-association-modal-content")
	@ResponseBody
	public ModelAndView getAddCategoryAssociationContent() {
		
		List<PoCategory> categoryList = componentVendorTemplateService.getPoCategories();
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/add-category-association-content");
		mav.addObject("categoryList",categoryList);
		return mav;
	}
	
	
	@RequestMapping(value="get-sub-categories-association")
	@ResponseBody
	public List<SubCategory> getSubCategoryAssociation(@RequestParam("poCategoryId") int poCategoryId) {
		
		List<SubCategory> categoryList = categoryManagementService.getSubCategories(poCategoryId);
		return categoryList;
	}
	
	
	@RequestMapping(value="add-category-association")
	@ResponseBody
	public CategoryAssociation addCategoryAssociation(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,HttpServletResponse response) throws Exception  {

		 categoryManagementService.addCategoryAssociation(poCategoryId, subCategoryId);
		 CategoryAssociation categoryAssociation = categoryManagementService.getNewCategoryAssociation(poCategoryId, subCategoryId);
		 return categoryAssociation;

	}

	@RequestMapping(value="change-category-association-status")
	@ResponseBody
	public void changeAssociationStatus(@RequestParam("assId") int assId,@RequestParam("status") String status,@RequestParam("poCatId") int poCatId,
			@RequestParam("subCatId") int subCatId,HttpServletResponse response) {
		try{
			categoryManagementService.modifyAssStatus(assId,status,poCatId,subCatId);//1- Active, 0-Inactive
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}