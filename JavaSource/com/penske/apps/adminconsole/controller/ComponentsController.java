package com.penske.apps.adminconsole.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibility;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Manufacture;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.model.VendorTemplateSearch;
import com.penske.apps.adminconsole.service.CategoryManagementService;
import com.penske.apps.adminconsole.service.ComponentService;
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

    @Autowired
    private ComponentService componentService;

    // TODO SMCSEC is this even used
    @Deprecated
    @RequestMapping(value={"/visibility-by-category"})
    public ModelAndView getVisisbilityByCategoryPage(){

        List<ComponentVisibility> componentList = componentVisibilityService.getComponent();
        ModelAndView modelAndView = new ModelAndView("/admin-console/components/visibility-by-category");
        modelAndView.addObject("componentList",componentList);
        return modelAndView;

    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENTS)
    @RequestMapping(value={"/component-management"})
    public ModelAndView getComponentManagementPage(){

        ModelAndView mav = new ModelAndView("/admin-console/components/component-management");
        mav.addObject("componentList", componentService.loadAllAvailableComponents());
        return mav;
    }

    // TODO SMCSEC is this even used
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


    /*========================Excel Sequence Templates ======================*/
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/load-excel-seq-templates")
    public ModelAndView getExcelSequenceTemplates(){
        ModelAndView mav = new ModelAndView("/admin-console/components/excel-sequence-templates");

        mav.addObject("excelSeqTemplates", componentVendorTemplateService.getExcelSeqTemplates());

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
    public ModelAndView getCategoryAssociationPage(HttpSession session){

        List<CategoryAssociation> categoryAssociations = categoryManagementService.getAllCategoryAssociation();
        ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-association");
        modelAndView.addObject("categoryAssociation",categoryAssociations);
        modelAndView.addObject("access",CommonUtils.hasAccess(ApplicationConstants.COMPONENTS, session));
        return modelAndView;

    }

    // TODO SMCSEC is this even used
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

    // TODO SMCSEC is this even used. This is also repeated in the rest controller....
    @RequestMapping("/create-template")
    public ModelAndView getCreateTemplatePage(){

        List<Manufacture> manufacture = componentVendorTemplateService.getManufacture();
        ModelAndView mav =new ModelAndView("/admin-console/components/create-template");
        mav.addObject("manufacture",manufacture);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping(value ={"/template"})
    public ModelAndView getTemplatePage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/components/template");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        // If the user is a supplier.
        if (isSupplier) {
        }
        else {
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("templateList", componentService.getAllTemplates());
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_COMPONENT_OVERRIDE)
    @RequestMapping(value ={"/component-Visibility-Override"})
    public ModelAndView getComponentVisibilityOverrides(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/components/component-Visibility-Override");
        mav.addObject("overrideList", componentService.getAllComponentVisibilityOverrides());
        return mav;
    }


}
