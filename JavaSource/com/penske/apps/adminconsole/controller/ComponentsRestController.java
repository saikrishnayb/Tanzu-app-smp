package com.penske.apps.adminconsole.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.adminconsole.service.ComponentVisibilityService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;

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
	
	private static final Logger logger = Logger.getLogger(ComponentsRestController.class);

	//////////////////////////////////////////////////////////////////////
	// Service Members
	@Autowired
	private ComponentVisibilityService componentVisibilityService;
	@Autowired
	private ComponentVendorTemplateService componentVendorTemplateService;
	//////////////////////////////////////////////////////////////////////
	
	@Autowired
	private CategoryManagementService categoryManagementService;
	
	@Autowired
	private ComponentService componentService;
	
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
	public ModelAndView deleteCategoryContent(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId)
	{
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/category-delete-modal-content");
		TemplatePoCategorySubCategory categories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
		mav.addObject("categories",categories);
		return mav;
		
	}
	
	@RequestMapping(value="delete-category-content-edit")
	@ResponseBody
	public ModelAndView deleteCategoryContentEditModal(@RequestParam("templateId") int templateId,@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId)
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
	public ModelAndView getCategoryComponents(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
		
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
	public ModelAndView getAddCategoryComponents(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
		
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
	public ModelAndView getVendorInfo(@RequestParam("templateId") int templateId) {
		
		VendorTemplate vendorTemplate =componentVendorTemplateService.getVendorTemplate(templateId);
		ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/template-delete-modal-content");
		mav.addObject("vendorTemplate",vendorTemplate);
		return mav;
		
	}
	
	@RequestMapping(value="delete-template-table-content")
	@ResponseBody
	public void deleteTemplateTableContent(@RequestParam("templateId") int templateId) {
		
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
			logger.debug(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="/create-modify-template-page")
	@ResponseBody
	public ModelAndView getCreateModifyTemplatePage(@RequestParam("isCreatePage") Boolean isCreatePage,@RequestParam(value="templateId") int templateId, HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/components/create-edit-template");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("currentUser", currentUser);
		mav.addObject("allPoAssocList", componentService.getAllPoAssociation());
		List<Components> comp=componentService.getAllComponent();
		if(isCreatePage){
			mav.addObject("isCreatePage",true);
		}else{
			mav.addObject("editableTemplate",componentService.getTemplatesById(templateId));
			comp=componentService.getTemplateComponentById(comp,templateId);
			mav.addObject("isCreatePage",false);
		}
		mav.addObject("allComponent",comp);
		return mav;
	}
	
	@RequestMapping(value ="/create-template", method = RequestMethod.POST)
	@ResponseBody
	public void addTemplate(@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList,HttpSession session, HttpServletResponse response) throws Exception{
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		Template template=new Template();
		template.setTemplateDesc(tempDesc);
		template.setPoCatAssID(poCatAssID);
		template.setComponentList(compList);
		template.setCreatedBy(currentUser.getSso());
		template.setModifiedBy(currentUser.getSso());
		String hashCodeStr=CommonUtils.getCompnentCheckSum(compList);
		template.setTemplateHash(hashCodeStr);
		List<Integer> templateId=componentService.findTemplateExist(template);
		if(templateId !=null && !templateId.isEmpty()){
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Template Already exists.");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Template Already exists.");
	        response.flushBuffer();
		}else{
			componentService.addTemplate(template);
		}
	}
	@RequestMapping(value ="/update-template", method = RequestMethod.POST)
	@ResponseBody
	public void updateTemplate(@RequestParam("templateId") int templateId,@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList,HttpSession session, HttpServletResponse response) throws Exception{
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		Template template=new Template();
		template.setTemplateID(templateId);
		template.setTemplateDesc(tempDesc);
		template.setPoCatAssID(poCatAssID);
		template.setComponentList(compList);
		template.setCreatedBy(currentUser.getSso());
		template.setModifiedBy(currentUser.getSso());
		String hashCodeStr=CommonUtils.getCompnentCheckSum(compList);
		template.setTemplateHash(hashCodeStr);
		List<Integer> templateTemplId=componentService.findTemplateExist(template);
		if(templateTemplId !=null && !templateTemplId.isEmpty() &&
			((templateTemplId.size()==1 && templateTemplId.get(0) !=templateId)) || templateTemplId.size()>1){
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Template Already exists.");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Template Already exists.");
	        response.flushBuffer();
		}else{
			componentService.updateTemplate(template);
		}
	}
	
	@RequestMapping("get-deactivate-template-modal-content")
	@ResponseBody
	public ModelAndView getDeactivateTemplateInfo(@RequestParam(value="templateName") String templateName, @RequestParam(value="templateId") String templateId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/deactivate-template-modal-content");
		mav.addObject("templateName", templateName);
		mav.addObject("templateId", templateId);
		
		return mav;
	}
	@RequestMapping("delete-template")
	@ResponseBody
	public void deleteTemplate(@RequestParam(value="templateId") int templateId, HttpSession session) {
		componentService.deleteTemplate(templateId);
	}
	
	
	
	@RequestMapping(value ="/create-modify-comp-visiblity-override-page")
	@ResponseBody
	public ModelAndView getCreateModifyCompVisiblityOverridePage(@RequestParam("isCreatePage") Boolean isCreatePage,
			@RequestParam("overrideId") int overrideId,HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/components/create-edit-comp-visiblity-override");
		mav.addObject("allPoAssocList", componentService.getAllPoAssociation());
		mav.addObject("overrideTypes", componentService.getOverrideTypes());
		if(isCreatePage){
			mav.addObject("isCreatePage",true);
		}else{
			mav.addObject("editOverride",componentService.getComponentVisibilityOverridesById(overrideId));
			mav.addObject("isCreatePage",false);
		}
		return mav;
	}
	
	@RequestMapping("get-component-search-page")
	@ResponseBody
	public ModelAndView getComponentSearchPage(@RequestParam(value="val") int val) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/component-search-page");
		List<Components> comp=componentService.getAllComponent();
		mav.addObject("allComponent",comp);
		mav.addObject("val", val);
		return mav;
	}
	
	@RequestMapping(value ="/create-comp-visiblity-override", method = RequestMethod.POST)
	@ResponseBody
	public void addCompVisiblityOverride(ComponentVisibilityOverride overrideObj, HttpServletResponse response) throws Exception{
		try{
			if(componentService.checkComponentVisibilityOverrideExist(overrideObj, true)){
				componentService.addComponentVisibilityOverrides(overrideObj);
			}else{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Component Visibility Override Already exists.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().write("Component Visibility Override Already exists.");
		        response.flushBuffer();
			}
		}catch (Exception e) {
			logger.error("Error while adding COMPONENT VISIBILITY OVERRIDES: "+e);
			CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
		}
	}
	
	@RequestMapping(value ="/update-comp-visiblity-override", method = RequestMethod.POST)
	@ResponseBody
	public void updateCompVisiblityOverride(ComponentVisibilityOverride overrideObj, HttpServletResponse response) throws Exception{
		try{
			if(componentService.checkComponentVisibilityOverrideExist(overrideObj, false)){
				componentService.updateComponentVisibilityOverrides(overrideObj);
			}else{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Component Visibility Override Already exists.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().write("Component Visibility Override Already exists.");
		        response.flushBuffer();
			}
		}catch (Exception e) {
			logger.error("Error while updating COMPONENT VISIBILITY OVERRIDES: "+e);
			CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
		}
	}
	
	@RequestMapping("get-deactivate-visiblity-override-modal-content")
	@ResponseBody
	public ModelAndView getDeactivateVisiblityOverride(@RequestParam("overrideId") int overrideId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/deactivate-visibility-override-modal-content");
		mav.addObject("overrideId", overrideId);
		
		return mav;
	}
	
	@RequestMapping("delete-visiblity-override")
	@ResponseBody
	public void deleteComponentVisibilityOverrides(@RequestParam(value="overrideId") int overrideId, HttpSession session) {
		componentService.deleteComponentVisibilityOverrides(overrideId);
	}
}
