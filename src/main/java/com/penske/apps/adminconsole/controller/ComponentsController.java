package com.penske.apps.adminconsole.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.Component;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HoldPayment;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentService;
import com.penske.apps.adminconsole.service.ComponentVendorTemplateService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;


/**
 * Controller handling all mapping and functionality for the Admin Console Components sub tab
 * @author erik.munoz 600139451
 * @author 600144005
 */
@Version1Controller
@RequestMapping("/admin-console/components")
public class ComponentsController {

    @Autowired
    private ComponentVendorTemplateService componentVendorTemplateService;
    @Autowired
    private CategoryManagementService categoryManagementService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;
   
    @RequestMapping(value = {"/navigate-components"})
    public ModelAndView navigateAppConfig() {

        Set<SecurityFunction> securityFunctions = sessionBean.getUser().getSecurityFunctions();

        List<LeftNav> leftNavs = SubTab.COMPONENTS.getLeftNavs();

        for (LeftNav leftNav : leftNavs) {

            SecurityFunction securityFunction = leftNav.getSecurityFunction();

            boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
            if (noAccess) continue;

            return new ModelAndView("redirect:/app/" + leftNav.getUrlEntry());
        }

        return new ModelAndView("/admin-console/security/noAccess");
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENTS)
    @RequestMapping(value={"/component-management"})
    public ModelAndView getComponentManagementPage(){
        ModelAndView mav = new ModelAndView("/admin-console/components/component-management");
        List<Component> componentList = componentService.loadAllAvailableComponents();
        Map<Integer, List<HoldPayment>> holdPaymentsByCompId = componentService.getAllHoldPayments();
        mav.addObject("componentList", componentList);
        mav.addObject("holdPaymentsByCompId", holdPaymentsByCompId);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY)
    @RequestMapping(value="/category-management")
    public ModelAndView getCategoryByManagementPage(){

        List<PoCategory> categoryList = componentVendorTemplateService.getPoCategories();
        ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-management");
        modelAndView.addObject("categoryList",categoryList);

        List<SubCategory> subCategoryList = categoryManagementService.getAllSubCategories();
        modelAndView.addObject("subCategoryList",subCategoryList);
        return modelAndView;

    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_CATEGORY_ASSOCIATION)
    @RequestMapping(value="/category-association")
    public ModelAndView getCategoryAssociationPage(){

        List<CategoryAssociation> categoryAssociations = categoryManagementService.getAllCategoryAssociation();
        User user = sessionBean.getUser();
        ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-association");
        modelAndView.addObject("categoryAssociation",categoryAssociations);
        modelAndView.addObject("access", user.hasSecurityFunction(SecurityFunction.MANAGE_CATEGORY_ASSOCIATION));
        return modelAndView;

    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/template")
    public ModelAndView getTemplatePage(@RequestParam(value="selectedTemplateType",required = false,defaultValue="ACTIVE") String selectedTemplateType) {
        ModelAndView mav = new ModelAndView("/admin-console/components/template");
        mav.addObject("templateList", componentService.getAllTemplates());
        mav.addObject("selectedTemplateType",selectedTemplateType);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping(value ="/create-modify-template-page")
    public ModelAndView getCreateModifyTemplatePage(@RequestParam("isCreatePage") Boolean isCreatePage,@RequestParam(value="templateId") int templateId,
    		@RequestParam(value="tempCompId",required = false) Integer tempCompId,@RequestParam(value="toggleSelection",required = false) String toggleSelection) {
        ModelAndView mav = new ModelAndView("/admin-console/components/create-edit-template");
        //mav.addObject("allPoAssocList", componentService.getAllPoAssociation());
        List<Components> comp=componentService.getAllComponent();
        if(isCreatePage){
            mav.addObject("allPoAssocList", componentService.getAllPoAssociationAddEdit(true));
            mav.addObject("isCreatePage",true);
        }else{
            Template editableTemplate=componentService.getTemplatesById(templateId);
            if(editableTemplate !=null){
                mav.addObject("allPoAssocList", componentService.getAllPoAssociationAddEdit(false));
            }
            mav.addObject("editableTemplate",editableTemplate);
            comp=componentService.getTemplateComponentById(comp,templateId);
            mav.addObject("isCreatePage",false);
        }
        toggleSelection = StringUtils.isNotEmpty(toggleSelection)?toggleSelection:"SELECTED";
        mav.addObject("allComponent",comp);
        mav.addObject("tempCompId",tempCompId);
        mav.addObject("toggleSelection",toggleSelection);
        return mav;
    }
}
