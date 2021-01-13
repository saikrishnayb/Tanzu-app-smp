package com.penske.apps.adminconsole.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HoldPayment;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponent;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.adminconsole.service.LoadSheetManagementService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.smccore.base.domain.enums.CorpCode;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * 
 * @author 600144005
 * 
 * This Controller class contains all of the AJAX request methods for all pages under components tab
 * 
 * 
 */

@Version1Controller
@RequestMapping("/admin-console/components")
public class ComponentsRestController {

    private static final Logger LOGGER = Logger.getLogger(ComponentsRestController.class);

    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    
    //////////////////////////////////////////////////////////////////////
    // Service Members
    @Autowired
    private ComponentVendorTemplateService componentVendorTemplateService;
    //////////////////////////////////////////////////////////////////////

    @Autowired
    private CategoryManagementService categoryManagementService;

    @Autowired
    private ComponentService componentService;
    
    @Autowired
    private VendorService vendorService;
    
    @Autowired
    private LoadSheetManagementService loadsheetManagementService;

    //CATEGORY MANAGEMENT//
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="get-edit-sub-category-content")
    @ResponseBody
    public ModelAndView getEditSubCategoryContent(@RequestParam("subCategoryId") int subCategoryId) {

        SubCategory subCategory =categoryManagementService.getSelectedSubCategory(subCategoryId);
        ModelAndView mav =new ModelAndView("/admin-console/components/modal/edit-sub-category-modal");
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

        ModelAndView mav =new ModelAndView("/admin-console/components/modal/add-sub-category-modal");
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="insert-sub-category")
    @ResponseBody
    public int insertSubCategory(SubCategory categoryData,HttpServletResponse response) throws Exception {
        try{
        	UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext.getUserSSO();
            categoryData.setCreatedBy(userSSO);
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
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/add-category-association-content");
        mav.addObject("categoryList",categoryList);
        mav.addObject("isEditPage",false);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="get-edit-category-association-modal-content")
    @ResponseBody
    public ModelAndView getEditCategoryAssociationContent(int associationId) {

        CategoryAssociation categoryAssociation = categoryManagementService.getEditCategoryAssociation(associationId);
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/add-category-association-content");
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
    public void addCategoryAssociation(@ModelAttribute("addAssociationForm") CategoryAssociation addAssociationForm) throws Exception  {
        categoryManagementService.addCategoryAssociation(addAssociationForm);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="update-category-association")
    @ResponseBody
    public void updateCategoryAssociation(@ModelAttribute("addAssociationForm") CategoryAssociation addAssociationForm) throws Exception  {
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
    public void copyCorpComponent(@RequestParam("componentId") int componentId, @RequestParam("componentGroupNumber") int componentGroupNumber) {
    		componentService.copyCorpComponentRow(componentId, componentGroupNumber);
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
    public String addTemplate(@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList, HttpServletResponse response){
        String status=null;
    	try{
        	UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext.getUserSSO();
	        Template template=new Template();
	        template.setTemplateDesc(tempDesc);
	        template.setPoCatAssID(poCatAssID);
	        template.setComponentList(compList);
	        template.setCreatedBy(userSSO);
	        template.setModifiedBy(userSSO);
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
    public String updateTemplate(@RequestParam("templateId") int templateId,@RequestParam("tempDesc") String tempDesc,@RequestParam("poCatAssID") String poCatAssID,@RequestBody List<Components> compList, HttpServletResponse response){
    	String status=null;
    	try{
        	UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext.getUserSSO();
	        Template template=new Template();
	        template.setTemplateID(templateId);
	        template.setTemplateDesc(tempDesc);
	        template.setPoCatAssID(poCatAssID);
	        template.setComponentList(compList);
	        template.setCreatedBy(userSSO);
	        template.setModifiedBy(userSSO);
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
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/deactivate-template-modal-content");
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

    /*==============Load template component sequence===================*/
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/get-template-component-sequence")
    @ResponseBody
    public ModelAndView getTemplateComponents(@RequestParam("templateId") int templateId){

        ModelAndView mav = new ModelAndView("/admin-console/components/modal/excel-sequence-components");

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
    public String insertRuleDetails(RuleMaster ruleMaster, int ruleId){
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
    public String updateRuleDetails(RuleMaster ruleMaster){
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
    public boolean checkForUniqueRuleName(@RequestParam("ruleName") String ruleName, @RequestParam("ruleId") int ruleId, @RequestParam("templateId") int templateId) {

        return loadsheetManagementService.checkForUniqueUnitTemplateRuleName(ruleName, ruleId, templateId);
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
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/view-rules-associtaed-to-component-modal-alert");
        List<String> ruleList= new  ArrayList<String>();
        ruleList=loadsheetManagementService.getRulesByComponentIdAndTemplateId(templateId,componentId);
        mav.addObject("ruleList", ruleList);
        mav.addObject("componentName",componentName);
        return mav;
    }
    
    /*==============Display Existing Rule===================*/
    @RequestMapping(value={"/get-rule-popup"})
    public ModelAndView editRuleDefinitions(@RequestParam("templateComponentId")int templateComponentId,@RequestParam("templateId") int templateId,HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("/admin-console/components/modal/template-rule");
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
    	ModelAndView mav = new ModelAndView("/admin-console/components/modal/ruleDetails");
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
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/ruleDetails");
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
    
    /*==============Hold Payment===================*/
    @RequestMapping("/get-hold-payment-content")
    public ModelAndView getHoldPayment(@RequestParam("componentId") int componentId){
        ModelAndView mav = new ModelAndView("/admin-console/components/modal/hold-payment-modal");
        UserContext user = sessionBean.getUserContext();
        Component component = componentService.getComponentById(componentId);
        List<CorpCode> corpCodes = Arrays.asList(CorpCode.values());
        Map<String, CorpCode> corpByCorpCode = corpCodes.stream()
        		.collect(Collectors.toMap(CorpCode::getCode, c->c));
        List<HoldPayment> holdPayments = componentService.getHoldPaymentsByComponentId(componentId);
        Map<Integer, HoldPayment> holdpaymentsByVendorId = holdPayments.stream()
        		.collect(Collectors.toMap(HoldPayment::getVendorId, hp->hp));
        List<Vendor> vendors = vendorService.getAllVendors(user.getOrgId());
        
        mav.addObject("component", component);
        mav.addObject("holdpaymentsByVendorId", holdpaymentsByVendorId);
        mav.addObject("vendors", vendors);
        mav.addObject("corpByCorpCode", corpByCorpCode);
        return mav;
    }
    
    @RequestMapping(value="/save-hold-payment", method = RequestMethod.POST)
    public void saveHoldPayment(@RequestParam("componentId") int componentId, @RequestParam(value="vendorIds[]", required=false) List<Integer> vendorIds, HttpServletResponse response){
        UserContext user = sessionBean.getUserContext();
        Component component = componentService.getComponentById(componentId);
        if(component == null)
        	throw new IllegalArgumentException("Component not found");
        if(vendorIds == null)
        	vendorIds = Collections.emptyList();
        
        try{
        	componentService.saveHoldPayments(component, vendorIds, user);
        }catch (Exception e) {
       	 LOGGER.error("Error :" + e.getMessage(),e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
    }
}