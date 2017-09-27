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

import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.ComponentVisibilityOverride;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.PoCategory;
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

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="get-add-visibility-modal-content")
    @ResponseBody
    public ModelAndView getAddVisibilityModalContent(){
        LOGGER.error("getAddVisibilityModalContent is used!!!! :)");
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
        LOGGER.error("getComponentList is used!!!! :)");
        List<ComponentVisibility> componentList = componentVisibilityService.getComponentList(poCategoryId);
        return componentList;

    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="post-save-visibility")
    @ResponseBody
    public void postSaveVisibility(@RequestParam("isComponentVehicle") String isComponentVehicle , @RequestParam("componentId") int componentId,@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId){

        LOGGER.error("postSaveVisibility is used!!!! :)");

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

        LOGGER.error("getPoCategories is used!!!! :)");

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

        LOGGER.error("getPoSubCategories is used!!!! :)");

        List<SubCategory> subCategories = componentVisibilityService.getSubCategoryList(poCategoryId);
        return subCategories;

    }

    // TODO SMCSEC is this even being used????
    @RequestMapping(value="delete-table-component")
    @ResponseBody
    @ResponseStatus(value=HttpStatus.OK)
    public void deleteVisibility(@RequestParam("isComponentVehicle")int isComponentVehicle,@RequestParam("componentId")int componentId,@RequestParam("category")int category,@RequestParam("subCategory")int subCategory)
    {

        LOGGER.error("deleteVisibility is used!!!! :)");

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

        LOGGER.error("getDeleteModalContent is used!!!! :)");

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

        LOGGER.error("getVendorTemplate is used!!!! :)");

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

        LOGGER.error("deleteCategory is used!!!! :)");

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

        LOGGER.error("deleteCategoryContent is used!!!! :)");

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

        LOGGER.error("deleteCategoryContentEditModal is used!!!! :)");
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/components/template-category-edit-delete-modal-content");
        TemplatePoCategorySubCategory categories = componentVendorTemplateService.getDeleteInEditModalContent(templateId,poCategoryId,subCategoryId);
        mav.addObject("categories",categories);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-add-po-categories")
    @ResponseBody
    public ModelAndView getCategories() {
        LOGGER.error("getCategories is used!!!! :)");
        List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
        mav.addObject("categories",categories);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-edit-add-po-categories")
    @ResponseBody
    public ModelAndView getAddCategories() {
        LOGGER.error("getAddCategories is used!!!! :)");
        List<PoCategory> categories = componentVendorTemplateService.getPoCategories();
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/add-category-modal-content");
        mav.addObject("categories",categories);
        return mav;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-add-po-sub-categories")
    @ResponseBody
    public List<SubCategory> getsubCategories(@RequestParam("poCategoryId") int poCategoryId) {
        LOGGER.error("getsubCategories is used!!!! :)");
        List<SubCategory> categories = componentVendorTemplateService.getSubCategories(poCategoryId);
        return categories;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="get-category-components")
    @ResponseBody
    public ModelAndView getCategoryComponents(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryIds[]") int [] subCategoryIds) {
        LOGGER.error("getCategoryComponents is used!!!! :)");
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
        LOGGER.error("getAddCategoryComponents is used!!!! :)");
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
        LOGGER.error("getVendorInfo is used!!!! :)");
        VendorTemplate vendorTemplate =componentVendorTemplateService.getVendorTemplate(templateId);
        ModelAndView mav =new ModelAndView("/jsp-fragment/admin-console/components/template-delete-modal-content");
        mav.addObject("vendorTemplate",vendorTemplate);
        return mav;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-template-table-content")
    @ResponseBody
    public void deleteTemplateTableContent(@RequestParam("templateId") int templateId) {
        LOGGER.error("deleteTemplateTableContent is used!!!! :)");
        componentVendorTemplateService.deleteTemplate(templateId);
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="add-template")
    @ResponseBody
    public int addTemplate(@RequestParam("vendorNumber") int vendorNumber,@RequestParam("corpCode") String corpCode, HttpSession session) {
        LOGGER.error("addTemplate is used!!!! :)");
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
        LOGGER.error("addTemplate / get-vendor-numbers-by-mfr  is used!!!! :)");
        List<Integer> vendorNumbers =  componentVendorTemplateService.getVendorNumberByMfr(mfr);
        return vendorNumbers;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="add-template-components")
    @ResponseBody
    public void addTemplate(TemplateComponents serializedObject,@RequestParam("poCategoryId")int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,@RequestParam("templateId") String templateId) {
        LOGGER.error("addTemplate / add-template-components is used!!!! :)");
        componentVendorTemplateService.addTemplateComponents(serializedObject, poCategoryId, subCategoryId);

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="update-template-components")
    @ResponseBody
    public void updateTemplateComponents(TemplateComponents serializedObject) {
        LOGGER.error("updateTemplateComponents is used!!!! :)");
        componentVendorTemplateService.updateTemplateComponents(serializedObject);
    }

    //CATEGORY MANAGEMENT//
    // TODO SMCSEC is this even used
    @RequestMapping(value="get-edit-category-content")
    @ResponseBody
    public ModelAndView getEditCategoryContent(@RequestParam("poCategoryId") int poCategoryId) {
        LOGGER.error("getEditCategoryContent is used!!!! :)");
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
        LOGGER.error("updatePoCategory is used!!!! :)");
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
            LOGGER.error("Error while Updating PO Category: " + e);
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
            LOGGER.error("Error while adding PO Category: " + e);
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
            LOGGER.error("Error while updating Sub-Category: " + e);
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
            LOGGER.error("Error while adding Sub-Category: " + e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Insert Sub-Category");
        }
        return 0;
    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="delete-po-category")
    @ResponseBody
    public void deletePoCategory(@RequestParam("poCatId") int poCatId) {
        LOGGER.error("deletePoCategory is used!!!! :)");
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
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="get-sub-categories-association")
    @ResponseBody
    public List<SubCategory> getSubCategoryAssociation(@RequestParam("poCategoryId") int poCategoryId) {

        List<SubCategory> categoryList = categoryManagementService.getSubCategories(poCategoryId);
        return categoryList;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="add-category-association")
    @ResponseBody
    public CategoryAssociation addCategoryAssociation(@RequestParam("poCategoryId") int poCategoryId,@RequestParam("subCategoryId") int subCategoryId,HttpServletResponse response) throws Exception  {

        categoryManagementService.addCategoryAssociation(poCategoryId, subCategoryId);
        CategoryAssociation categoryAssociation = categoryManagementService.getNewCategoryAssociation(poCategoryId, subCategoryId);
        return categoryAssociation;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="change-category-association-status")
    @ResponseBody
    public void changeAssociationStatus(@RequestParam("assId") int assId,@RequestParam("status") String status,@RequestParam("poCatId") int poCatId,
            @RequestParam("subCatId") int subCatId,HttpServletResponse response) {

        LOGGER.error("changeAssociationStatus is used!!!! :)");

        try{
            categoryManagementService.modifyAssStatus(assId,status,poCatId,subCatId);//1- Active, 0-Inactive
        }catch (Exception e) {
            LOGGER.debug(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENTS)
    @RequestMapping(value = "copy-corp-component")
    @ResponseBody
    public void copyCorpComponent(@RequestParam("componentId") int componentId, @RequestParam("componentGroupId") int componentGroupId) {

        componentService.copyCorpComponentRow(componentId, componentGroupId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping(value ="/create-modify-template-page")
    @ResponseBody
    public ModelAndView getCreateModifyTemplatePage(@RequestParam("isCreatePage") Boolean isCreatePage,@RequestParam(value="templateId") int templateId, HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/components/create-edit-template");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        mav.addObject("currentUser", currentUser);
        //mav.addObject("allPoAssocList", componentService.getAllPoAssociation());
        List<Components> comp=componentService.getAllComponent();
        if(isCreatePage){
            mav.addObject("allPoAssocList", componentService.getAllPoAssociationAddEdit(true, 0));
            mav.addObject("isCreatePage",true);
        }else{
            Template editableTemplate=componentService.getTemplatesById(templateId);
            if(editableTemplate !=null){
                mav.addObject("allPoAssocList", componentService
                        .getAllPoAssociationAddEdit(false, Integer
                                .parseInt(editableTemplate.getPoCatAssID())));
            }
            mav.addObject("editableTemplate",editableTemplate);
            comp=componentService.getTemplateComponentById(comp,templateId);
            mav.addObject("isCreatePage",false);
        }
        mav.addObject("allComponent",comp);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
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

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
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
    @RequestMapping("delete-template")
    @ResponseBody
    public void deleteTemplate(@RequestParam(value="templateId") int templateId, HttpSession session) {
        componentService.deleteTemplate(templateId);
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
            LOGGER.error("Error while adding COMPONENT VISIBILITY OVERRIDES: " + e);
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
            LOGGER.error("Error while updating COMPONENT VISIBILITY OVERRIDES: " + e);
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
    @RequestMapping("/load-template-componenet-sequence")
    @ResponseBody
    public ModelAndView getTemplateComponents(@RequestParam("templateId") int templateId){
        ModelAndView mav = new ModelAndView("/admin-console/components/excel-sequence-components");
        TemplateComponent templateComponents= new TemplateComponent();
        templateComponents.setTemplateId(templateId);
        List<ComponentSequence> templateComponentList=componentVendorTemplateService.getTemplateComponentSequences(templateId);
        templateComponents.setComponents(templateComponentList);
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
            LOGGER.error("Error while updating TEMPLATE COMPONENT SEQUENCE: " + e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Component Visibility Override");
        }
        return status;
    }
}
