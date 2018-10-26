package com.penske.apps.adminconsole.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponent;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.adminconsole.service.ComponentVisibilityService;
import com.penske.apps.adminconsole.service.LoadSheetManagementService;
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

    private static final Logger LOGGER = Logger.getLogger(ComponentsRestController.class);

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
    
    @Autowired
    private LoadSheetManagementService loadsheetManagementService;

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-add-visibility-modal-content")
    @ResponseBody
    public ModelAndView getAddVisibilityModalContent(){
        LOGGER.debug("getAddVisibilityModalContent is used!!!! :)");
        List<ComponentVisibility> componentNames = componentVisibilityService.getComponentName();
        List<PoCategory> categoriesList = componentVisibilityService.getCategoryList();
        ModelAndView modelAndView = new ModelAndView("/jsp-fragment/admin-console/components/add-visibility-modal-content");
        modelAndView.addObject("categoriesList",categoriesList);
        modelAndView.addObject("componentNames",componentNames);
        return modelAndView;

    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-compnent-list")
    @ResponseBody
    public List<ComponentVisibility>  getComponentList(@RequestParam("poCategoryId")int poCategoryId){
        LOGGER.debug("getComponentList is used!!!! :)");
        List<ComponentVisibility> componentList = componentVisibilityService.getComponentList(poCategoryId);
        return componentList;

    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="post-save-visibility")
    @ResponseBody
    public void postSaveVisibility(@RequestParam("isComponentVehicle") String isComponentVehicle , @RequestParam("componentId") int componentId,@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId){

        LOGGER.debug("postSaveVisibility is used!!!! :)");

        if("Yes".equalsIgnoreCase(isComponentVehicle))
        {
            componentVisibilityService.addComponentVisibility(componentId, poCategoryId, subCategoryId);
        }
        else {
            componentVisibilityService.addVehicleVisibility(componentId, poCategoryId, subCategoryId);
        }
    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-po-categories")
    @ResponseBody
    public List<ComponentVisibility> getPoCategories(@RequestParam("isComponentVehicle") String isComponentVehicle,@RequestParam("componentId") int componentId){

        LOGGER.debug("getPoCategories is used!!!! :)");

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

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-po-sub-categories")
    @ResponseBody
    public List<SubCategory> getPoSubCategories(@RequestParam("poCategoryId")int poCategoryId){

        LOGGER.debug("getPoSubCategories is used!!!! :)");

        List<SubCategory> subCategories = componentVisibilityService.getSubCategoryList(poCategoryId);
        return subCategories;

    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="delete-table-component")
    @ResponseBody
    @ResponseStatus(value=HttpStatus.OK)
    public void deleteVisibility(@RequestParam("isComponentVehicle")int isComponentVehicle,@RequestParam("componentId")int componentId,@RequestParam("category")int category,@RequestParam("subCategory")int subCategory)
    {

        LOGGER.debug("deleteVisibility is used!!!! :)");

        boolean isComponent = isComponentVehicle==ApplicationConstants.VEHICLE_COMPONENT;
        if(isComponent) {
            componentVisibilityService.deleteComponentVisibility(componentId, category, subCategory);
        }
        else {
            componentVisibilityService.deleteVehicleVisibility(componentId, category, subCategory);
        }
    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-delete-modal-content")
    @ResponseBody
    public ModelAndView getDeleteModalContent(@RequestParam("isComponentVehicle")int isComponentVehicle,@RequestParam("componentId")int componentId,@RequestParam("category")int category,@RequestParam("subCategory")int subCategory)
    {

        LOGGER.debug("getDeleteModalContent is used!!!! :)");

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
    // TODO SMCSEC is this even used
    @RequestMapping(value="get-vendor-template")
    @ResponseBody
    public ModelAndView getVendorTemplate(@RequestParam("vendorNumber") int vendorNumber,@RequestParam("corpCode")  String corpCode){

        LOGGER.debug("getVendorTemplate is used!!!! :)");

        List<VendorTemplate> template = componentVendorTemplateService.getVendorCategories(vendorNumber,corpCode);
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-template-modal-content");
        mav.addObject("template",template);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-accordion-category")
    @ResponseBody
    public void deleteCategory(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,@RequestParam("templateId") int templateId)
    {

        LOGGER.debug("deleteCategory is used!!!! :)");

        componentVendorTemplateService.deleteCategory(poCategoryId, subCategoryId, templateId);
        int templateComponentcount = componentVendorTemplateService.getTemplateComponentCount(templateId);

        if(templateComponentcount<1) {
            componentVendorTemplateService.deleteVendorTemplate(templateId);
        }


    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-category-content")
    @ResponseBody
    public ModelAndView deleteCategoryContent(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId)
    {

        LOGGER.debug("deleteCategoryContent is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/category-delete-modal-content");
        TemplatePoCategorySubCategory categories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
        mav.addObject("categories",categories);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-category-content-edit")
    @ResponseBody
    public ModelAndView deleteCategoryContentEditModal(@RequestParam("templateId") int templateId,@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId)
    {

        LOGGER.debug("deleteCategoryContentEditModal is used!!!! :)");
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/template-category-edit-delete-modal-content");
        TemplatePoCategorySubCategory categories = componentVendorTemplateService.getDeleteInEditModalContent(templateId,poCategoryId,subCategoryId);
        mav.addObject("categories",categories);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-add-po-categories")
    @ResponseBody
    public ModelAndView getCategories() {
        LOGGER.debug("getCategories is used!!!! :)");
        List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
        mav.addObject("categories",categories);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-edit-add-po-categories")
    @ResponseBody
    public ModelAndView getAddCategories() {
        LOGGER.debug("getAddCategories is used!!!! :)");
        List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
        mav.addObject("categories",categories);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-add-po-sub-categories")
    @ResponseBody
    public List<SubCategory> getsubCategories(@RequestParam("poCategoryId") int poCategoryId) {
        LOGGER.debug("getsubCategories is used!!!! :)");
        List<SubCategory> categories = componentVendorTemplateService.getSubCategories(poCategoryId);
        return categories;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-category-components")
    @ResponseBody
    public ModelAndView getCategoryComponents(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
        LOGGER.debug("getCategoryComponents is used!!!! :)");
        List<TemplatePoCategorySubCategory> poCategorySubCategory =new ArrayList<TemplatePoCategorySubCategory>();
        for (int subCategoryId : subCategoryIds) {

            TemplatePoCategorySubCategory poCategorySubCategories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
            poCategorySubCategory.add(poCategorySubCategories);
        }


        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-accordion-content");
        mav.addObject("category",poCategorySubCategory);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-add-category-components")
    @ResponseBody
    public ModelAndView getAddCategoryComponents(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
        LOGGER.debug("getAddCategoryComponents is used!!!! :)");
        List<TemplatePoCategorySubCategory> poCategorySubCategory =new ArrayList<TemplatePoCategorySubCategory>();
        for (int subCategoryId : subCategoryIds) {

            TemplatePoCategorySubCategory poCategorySubCategories = componentVendorTemplateService.getPoCategorySubCategory(poCategoryId, subCategoryId);
            poCategorySubCategory.add(poCategorySubCategories);
        }

        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-accordion-content");
        mav.addObject("category",poCategorySubCategory);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-delete-template-modal-content")
    @ResponseBody
    public ModelAndView getVendorInfo(@RequestParam("templateId") int templateId) {
        LOGGER.debug("getVendorInfo is used!!!! :)");
        VendorTemplate vendorTemplate =componentVendorTemplateService.getVendorTemplate(templateId);
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/template-delete-modal-content");
        mav.addObject("vendorTemplate",vendorTemplate);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-template-table-content")
    @ResponseBody
    public void deleteTemplateTableContent(@RequestParam("templateId") int templateId) {
        LOGGER.debug("deleteTemplateTableContent is used!!!! :)");
        componentVendorTemplateService.deleteTemplate(templateId);
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="add-template")
    @ResponseBody
    public int addTemplate(@RequestParam("vendorNumber") int vendorNumber,@RequestParam("corpCode") String corpCode, HttpSession session) {
        LOGGER.debug("addTemplate is used!!!! :)");
        HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
        componentVendorTemplateService.addTemplate(vendorNumber, currentUser.getSso());

        VendorTemplate template =  componentVendorTemplateService.getTemplateId(vendorNumber, corpCode);
        int templateId =template.getTemplateId();
        return templateId;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-vendor-numbers-by-mfr")
    @ResponseBody
    public List<Integer> addTemplate(@RequestParam("MFR") String mfr) {
        LOGGER.debug("addTemplate / get-vendor-numbers-by-mfr  is used!!!! :)");
        List<Integer> vendorNumbers =  componentVendorTemplateService.getVendorNumberByMfr(mfr);
        return vendorNumbers;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="add-template-components")
    @ResponseBody
    public void addTemplate(TemplateComponents serializedObject,@RequestParam("poCategoryId")int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,@RequestParam("templateId") String templateId) {
        LOGGER.debug("addTemplate / add-template-components is used!!!! :)");
        componentVendorTemplateService.addTemplateComponents(serializedObject, poCategoryId, subCategoryId);

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="update-template-components")
    @ResponseBody
    public void updateTemplateComponents(TemplateComponents serializedObject) {
        LOGGER.debug("updateTemplateComponents is used!!!! :)");
        componentVendorTemplateService.updateTemplateComponents(serializedObject);
    }

    //CATEGORY MANAGEMENT//
    // TODO SMCSEC is this even used
    @RequestMapping(value="get-edit-category-content")
    @ResponseBody
    public ModelAndView getEditCategoryContent(@RequestParam("poCategoryId") int poCategoryId) {
        LOGGER.debug("getEditCategoryContent is used!!!! :)");
        PoCategory poCategory =categoryManagementService.getSelectedPoCategory(poCategoryId);
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-category-modal");
        mav.addObject("category", poCategory);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="add-category-modal-content")
    @ResponseBody
    public ModelAndView addCategoryContent() {

        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/category-management-add-po-category-modal");
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="update-po-category")
    @ResponseBody
    public ModelAndView updatePoCategory(PoCategory categoryData,HttpServletResponse response) throws Exception {
        LOGGER.debug("updatePoCategory is used!!!! :)");
        try{
            if(categoryManagementService.checkCategoryExist(categoryData, false)){
                categoryManagementService.updatePoCategory(categoryData);
            }else{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PO Category Already exists.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("PO Category Already exists.");
                response.flushBuffer();
            }
        }catch (Exception e) {
            LOGGER.error("Error while Updating PO Category: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Update PO Category");
        }
        return null;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="insert-po-category")
    @ResponseBody
    public PoCategory insertPoCategory(PoCategory categoryData,HttpSession session,HttpServletResponse response) throws Exception {

        LOGGER.error("insertPoCategory is used!!!! :)");

        try{
            HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
            categoryData.setCreatedBy(currentUser.getSso());
            if(categoryManagementService.checkCategoryExist(categoryData, true)){
                categoryManagementService.insertPoCategory(categoryData);
                PoCategory category =categoryManagementService.getMaxCategoryId();
                return category;
            }else{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PO Category Already exists.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("PO Category Already exists.");
                response.flushBuffer();
            }
        }catch (Exception e) {
            LOGGER.error("Error while adding PO Category: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Insert PO Category");
        }
        return null;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="get-edit-sub-category-content")
    @ResponseBody
    public ModelAndView getEditSubCategoryContent(@RequestParam("subCategoryId") int subCategoryId) {

        SubCategory subCategory =categoryManagementService.getSelectedSubCategory(subCategoryId);
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/edit-sub-category-modal");
        mav.addObject("category", subCategory);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="update-sub-category")
    @ResponseBody
    public ModelAndView updatesubCategory(SubCategory categoryData,HttpServletResponse response) throws Exception {
        try{
            if(categoryManagementService.checkSubCategoryExist(categoryData, false)){
                categoryManagementService.updateSubCategory(categoryData);
            }else{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Sub-Category Already exists.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Sub-Category Category Already exists.");
                response.flushBuffer();
            }
        }catch (Exception e) {
            LOGGER.error("Error while updating Sub-Category: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the updating Sub-Category");
        }
        return null;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="add-sub-category-modal-content")
    @ResponseBody
    public ModelAndView addSubCategoryContent() {

        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-sub-category-modal");
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="insert-sub-category")
    @ResponseBody
    public int insertSubCategory(SubCategory categoryData,HttpSession session,HttpServletResponse response) throws Exception {
        try{
            HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
            categoryData.setCreatedBy(currentUser.getSso());
            if(categoryManagementService.checkSubCategoryExist(categoryData, true)){
                categoryManagementService.insertSubCategory(categoryData);
                int subCategoryId=categoryManagementService.getMaxSubCategoryId();
                return subCategoryId;
            }else{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Sub-Category Already exists.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Sub-Category Already exists.");
                response.flushBuffer();
            }
        }catch (Exception e) {
            LOGGER.error("Error while adding Sub-Category: "+e.getMessage(), e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Insert Sub-Category");
        }
        return 0;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-po-category")
    @ResponseBody
    public void deletePoCategory(@RequestParam("poCatId") int poCatId) {
        LOGGER.debug("deletePoCategory is used!!!! :)");
        categoryManagementService.modifyPoCatStatus(poCatId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="delete-sub-category")
    @ResponseBody
    public void deleteSubCategory(@RequestParam("subCatId") int subCatId) {
        categoryManagementService.modifySubCatStatus(subCatId);
    }

    //category association//

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="get-add-category-association-modal-content")
    @ResponseBody
    public ModelAndView getAddCategoryAssociationContent() {

        List<PoCategory> categoryList = componentVendorTemplateService.getPoCategories();
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/add-category-association-content");
        mav.addObject("categoryList",categoryList);
        mav.addObject("isEditPage",false);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="get-edit-category-association-modal-content")
    @ResponseBody
    public ModelAndView getEditCategoryAssociationContent(int associationId) {

        CategoryAssociation categoryAssociation = categoryManagementService.getEditCategoryAssociation(associationId);
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/add-category-association-content");
        mav.addObject("categoryAssociation",categoryAssociation);
        mav.addObject("isEditPage",true);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="get-sub-categories-association")
    @ResponseBody
    public  Map<String, Object> getSubCategoryAssociation(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("poCategoryText") PoCategoryType poCategory,HttpServletResponse response) {

        Map<String,Object> subCategoryAssociationMap = new HashMap<String, Object>();
        List<SubCategory> categoryList = new ArrayList<SubCategory>();
        try{
            categoryList = categoryManagementService.getSubCategories(poCategoryId);
        }catch (Exception e) {
        	LOGGER.error("Error :" + e.getMessage(),e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        boolean makeModelYearReqdAsDefault = PoCategoryType.isManufacturerInfoRequired(poCategory);
        boolean vehicleTypeReqdAsDefault =  PoCategoryType.isVehicleTypeRequired(poCategory);
        boolean vehicleSizeReqdAsDefault	= PoCategoryType.isSizeRequired(poCategory);
        subCategoryAssociationMap.put("subCategoryList",categoryList);
        subCategoryAssociationMap.put("makeModelYearReqdAsDefault",makeModelYearReqdAsDefault);
        subCategoryAssociationMap.put("vehicleTypeReqdAsDefault",vehicleTypeReqdAsDefault);
        subCategoryAssociationMap.put("vehicleSizeReqdAsDefault",vehicleSizeReqdAsDefault);
        return subCategoryAssociationMap;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="add-category-association")
    @ResponseBody
    public void addCategoryAssociation(@ModelAttribute("addAssociationForm") CategoryAssociation addAssociationForm,HttpServletResponse response) throws Exception  {
        categoryManagementService.addCategoryAssociation(addAssociationForm);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="update-category-association")
    @ResponseBody
    public void updateCategoryAssociation(@ModelAttribute("addAssociationForm") CategoryAssociation addAssociationForm,HttpServletResponse response) throws Exception  {
        categoryManagementService.updateCategoryAssociation(addAssociationForm);
    }

    @RequestMapping(value="change-category-association-status")
    @ResponseBody
    public void changeAssociationStatus(@RequestParam("assId") int assId,@RequestParam("status") String status,@RequestParam("poCatId") int poCatId,
            @RequestParam("subCatId") int subCatId,HttpServletResponse response) {

        try{
            categoryManagementService.modifyAssStatus(assId,status,poCatId,subCatId);//1- Active, 0-Inactive
        }catch (Exception e) {
        	LOGGER.error("Error :" + e.getMessage(),e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENTS)
    @RequestMapping(value = "copy-corp-component")
    @ResponseBody
    public void copyCorpComponent(@RequestParam("componentId") int componentId, @RequestParam("componentGroupId") int componentGroupId) {
    		componentService.copyCorpComponentRow(componentId, componentGroupId);
    }
    
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENTS)
    @RequestMapping(value = "allow-duplicate-components")
    @ResponseBody
    public void allowDuplicateComponents(@RequestParam("componentId") int componentId,@RequestParam("allowDuplicates") boolean allowDuplicates) {
    		componentService.allowDuplicateComponents(componentId,allowDuplicates);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping(value ="/create-template", method = RequestMethod.POST)
    @ResponseBody
    public String addTemplate(@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList,HttpSession session, HttpServletResponse response){
        String status=null;
    	try{
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
	        	CommonUtils.getCommonErrorAjaxResponse(response,"Template Already exists.");
	        }else
	            componentService.addTemplate(template);
	        return String.valueOf(template.getTemplateID());
        }catch(Exception e){
        	LOGGER.error("Error during creating template: "+e.getMessage(), e);
    		status=e.getMessage();
        }
		return status;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping(value ="/update-template", method = RequestMethod.POST)
    @ResponseBody
    public String updateTemplate(@RequestParam("templateId") int templateId,@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList,HttpSession session, HttpServletResponse response){
    	String status=null;
    	try{
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
	        	CommonUtils.getCommonErrorAjaxResponse(response,"Template Already exists.");
	        }else
	        	componentService.updateTemplate(template);
    	
	        return String.valueOf(templateId);
    	}catch(Exception e){
    		LOGGER.error("Error during updating template for the templateId:"+e.getMessage(), e);
    		status=e.getMessage();
        }
		return status;
	}


    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("get-deactivate-template-modal-content")
    @ResponseBody
    public ModelAndView getDeactivateTemplateInfo(@RequestParam(value="templateName") String templateName, @RequestParam(value="templateId") String templateId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/deactivate-template-modal-content");
        mav.addObject("templateName", templateName);
        mav.addObject("templateId", templateId);

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("deactivate-template")
    @ResponseBody
    public void deActivateTemplate(@RequestParam(value="templateId") int templateId) {
        componentService.deActivateTemplate(templateId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("activate-template")
    @ResponseBody
    public void activateTemplate(@RequestParam(value="templateId") int templateId) {
        componentService.activateTemplate(templateId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
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

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
    @RequestMapping("get-component-search-page")
    @ResponseBody
    public ModelAndView getComponentSearchPage(@RequestParam(value="val") int val) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/component-search-page");
        List<Components> comp=componentService.getAllComponent();
        mav.addObject("allComponent",comp);
        mav.addObject("val", val);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
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
            LOGGER.error("Error while adding COMPONENT VISIBILITY OVERRIDES: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
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
            LOGGER.error("Error while updating COMPONENT VISIBILITY OVERRIDES: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
    @RequestMapping("get-deactivate-visiblity-override-modal-content")
    @ResponseBody
    public ModelAndView getDeactivateVisiblityOverride(@RequestParam("overrideId") int overrideId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/deactivate-visibility-override-modal-content");
        mav.addObject("overrideId", overrideId);

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
    @RequestMapping("delete-visiblity-override")
    @ResponseBody
    public void deleteComponentVisibilityOverrides(@RequestParam(value="overrideId") int overrideId, HttpSession session) {
        componentService.deleteComponentVisibilityOverrides(overrideId);
    }

    /*==============Load template component sequence===================*/
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/get-template-component-sequence")
    @ResponseBody
    public ModelAndView getTemplateComponents(@RequestParam("templateId") int templateId){

        ModelAndView mav = new ModelAndView("/admin-console/components/excel-sequence-components");

        Template template = componentVendorTemplateService.getExcelSeqTemplate(templateId);

        List<ComponentSequence> templateComponentList = componentVendorTemplateService.getTemplateComponentSequences(templateId);

        TemplateComponent templateComponents = new TemplateComponent(templateId);
        templateComponents.setComponents(templateComponentList);

        mav.addObject("template", template);
        mav.addObject("templateComponents", templateComponents);

        return mav;

    }
    /*==============update template component sequence===================*/
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/update-template-componenet-sequence")
    @ResponseBody
    public String updateTemplateComponentSequence(TemplateComponent templateComponents,HttpServletResponse response) throws Exception{
        String status="SUCCESS";
        try{
            componentVendorTemplateService.updateTemplateComponentSequence(templateComponents);
        }catch (Exception e) {
            LOGGER.error("Error while updating TEMPLATE COMPONENT SEQUENCE: "+e.getMessage(), e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
        }
        return status;
    }
    

    /* =============== Create New Rule ==================*/
    @RequestMapping(value={"/create-template-rule"})
    @ResponseBody
    public String insertRuleDetails(RuleMaster ruleMaster,HttpServletResponse response,int ruleId){
    	String status=null;
    	try{
    		ruleId = loadsheetManagementService.createNewRule(ruleMaster);
    		status=String.valueOf(ruleId);
    	}
    	catch(Exception e){
    		LOGGER.error("Error while creating rule: "+e.getMessage(), e);
    		status=e.getMessage();
       }
    	return status;
    }
    
    
    /* ================Update Rule =======================*/
    @RequestMapping(value={"/update-template-rule"})
    @ResponseBody
    public String updateRuleDetails(RuleMaster ruleMaster,HttpServletResponse response){
    	String status=null;
    	try{
    	    loadsheetManagementService.updateRuleDetails(ruleMaster);
    	    status = String.valueOf(ruleMaster.getRuleId());
    	}
    	catch(Exception e){
    		LOGGER.error("Error while updating rule: "+e.getMessage(),e);
    		status=e.getMessage();
    	}
    	return status;
    }
    
    
    /* ========== Check For Unique rule Name =============== */
    @RequestMapping(value = "/check-unique-rule-name", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkForUniqueRuleName(@RequestParam("ruleName") String ruleName, @RequestParam("ruleId") int ruleId) {

        return loadsheetManagementService.checkForUniqueRuleName(ruleName, ruleId);
    }
    
   
    @RequestMapping(value = "/update-componet-rules-priority", method = RequestMethod.POST)
    public void updateComponentRulesPriority(@RequestParam("ruleList") List<Integer> ruleList,@RequestParam("templateComponentId")int templateComponentId,HttpServletResponse response) {
    	 try{
    		 loadsheetManagementService.updateComponentRulesPriority(ruleList,templateComponentId);
         }catch (Exception e) {
        	 LOGGER.error("Error :" + e.getMessage(),e);
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         }
        
    }
    
    @RequestMapping(value = "/check-iscomponent-associatedToRules")
    @ResponseBody
    public ModelAndView checkIsComponentHasRules(@RequestParam(value="templateId") int templateId,@RequestParam(value="componentId") int componentId, @RequestParam(value="componentName") String componentName) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/view-rules-associtaed-to-component-modal-alert");
        List<String> ruleList= new  ArrayList<String>();
        ruleList=loadsheetManagementService.getRulesByComponentIdAndTemplateId(templateId,componentId);
        mav.addObject("ruleList", ruleList);
        mav.addObject("componentName",componentName);
        return mav;
    }
    
    /*==============Display Existing Rule===================*/
    @RequestMapping(value={"/get-rule-popup"})
    public ModelAndView editRuleDefinitions(@RequestParam("templateComponentId")int templateComponentId,@RequestParam("templateId") int templateId,HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("/admin-console/components/template-rule");
        List<RuleMaster> rulesList= new  ArrayList<RuleMaster>();
        try{
        rulesList=loadsheetManagementService.getRulesByTemplateComponentId(templateComponentId);
        RuleMaster ruleMaster = new RuleMaster();
        if(!rulesList.isEmpty()){
        	String requestFrom="templateRule";
	        RuleMaster rule=rulesList.get(0);
	        ruleMaster=loadsheetManagementService.getRuleDetails(rule.getRuleId(),requestFrom);
	        loadsheetManagementService.getTemplateComponentRuleVisibilty(templateComponentId,rule.getRuleId(),ruleMaster);
        }
        mav.addObject("componentsList", componentService.getTemplateComponentByTempId(templateId));
        mav.addObject("pageAction","UPDATE");
        mav.addObject("ruleMaster",ruleMaster);
        mav.addObject("rulesList",rulesList);
        mav.addObject("templateComponentId",templateComponentId);
        }catch(Exception e){
        	  LOGGER.error("Error :" + e.getMessage(),e);
        	  CommonUtils.getCommonErrorAjaxResponse(response,e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping(value={"/get-rule-details"})
    public ModelAndView getRuleDetails(@RequestParam("ruleId")int ruleId,@RequestParam("templateComponentId") int templateComponentId,@RequestParam("templateId") int templateId,HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/ruleDetails");
    	try{
    	String requestFrom="templateRule";
        RuleMaster ruleMaster=loadsheetManagementService.getRuleDetails(ruleId,requestFrom);//ruleId null check to be done
        loadsheetManagementService.getTemplateComponentRuleVisibilty(templateComponentId,ruleId,ruleMaster);
        mav.addObject("componentsList", componentService.getTemplateComponentByTempId(templateId));
        mav.addObject("pageAction","UPDATE");
        mav.addObject("ruleMaster",ruleMaster);
        mav.addObject("templateComponentId",templateComponentId);
        mav.addObject("templateId",templateId);
    	}catch (Exception e) {
            LOGGER.error("Error :" + e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,e.getMessage());
        }
        return mav;
    }
    
    
/*    ==============Create Rule===================*/
    @RequestMapping("/load-create-rule")
    public ModelAndView openCreateRule(@RequestParam("templateComponentId") int templateComponentId,@RequestParam("templateId") int templateId){
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/ruleDetails");
        RuleMaster ruleMaster=new RuleMaster();
        List<RuleDefinitions> ruleDefLst=new ArrayList<RuleDefinitions>();
        RuleDefinitions ruleDef=new RuleDefinitions();
        ruleDef.setCriteriaGroup(1);
        ruleDef.setIsGroupHeader(true);
        ruleDefLst.add(ruleDef);	//Creating one empty row in create rule page
        ruleMaster.setRuleDefinitionsList(ruleDefLst);

        mav.addObject("componentsList", componentService.getTemplateComponentByTempId(templateId));
        mav.addObject("ruleMaster",ruleMaster);
        mav.addObject("pageAction","CREATE");
        mav.addObject("templateComponentId",templateComponentId);
        mav.addObject("templateId",templateId);
        return mav;
    }
    
/*==============Delete Rule===================*/
    @RequestMapping("delete-rule")
    @ResponseBody
    public String deleteRule(@RequestParam(value="ruleId") int ruleId) {
    	String status=null;
    	if(ruleId!=0){
	    	loadsheetManagementService.DeleteRuleDetails(ruleId);
	    	status="DELETED";
    	}
    	return status;
    	
    }

    
}
