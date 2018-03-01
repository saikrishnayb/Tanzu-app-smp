package com.penske.apps.adminconsole.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
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
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;


/**
 * Controller handling all mapping and functionality for the Admin Console Components sub tab
 * @author erik.munoz 600139451
 * @author 600144005
 */
@DefaultController
@RequestMapping("/admin-console/components")
public class ComponentsController {

    private static final Logger LOGGER = Logger.getLogger(ComponentsController.class);

    @Autowired
    private ComponentVisibilityService componentVisibilityService;
    @Autowired
    private ComponentVendorTemplateService componentVendorTemplateService;
    @Autowired
    private CategoryManagementService categoryManagementService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @RequestMapping(value = {"/navigate-components"})
    public ModelAndView navigateAppConfig(HttpServletRequest request) {

        Set<SecurityFunction> securityFunctions = sessionBean.getUserContext().getSecurityFunctions();

        List<LeftNav> leftNavs = SubTab.COMPONENTS.getLeftNavs();

        for (LeftNav leftNav : leftNavs) {

            SecurityFunction securityFunction = leftNav.getSecurityFunction();

            boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
            if (noAccess) continue;

            return new ModelAndView("redirect:/" + leftNav.getUrlEntry());
        }

        return new ModelAndView("/admin-console/security/noAccess");
    }

    // TODO SMCSEC is this even used
    @Deprecated
    @RequestMapping(value={"/visibility-by-category"})
    public ModelAndView getVisisbilityByCategoryPage(){
        LOGGER.error("getVisisbilityByCategoryPage is used!!!! :)");
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
        LOGGER.error("getTemplatesPage is used!!!! :)");
        List<VendorTemplate> templates = componentVendorTemplateService.getVendorTemplates();
        List<String> manufactures = componentVendorTemplateService.getAllTemplateManufactures();
        List<PoCategory> categories = componentVendorTemplateService.getTemplatePoCategory();

        ModelAndView mav =new ModelAndView("/admin-console/components/templates");
        mav.addObject("vendorTemplate",templates);
        mav.addObject("manufactures",manufactures);
        mav.addObject("categories",categories);
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
        UserContext userContext = sessionBean.getUserContext();
        ModelAndView modelAndView = new ModelAndView("/admin-console/components/category-association");
        modelAndView.addObject("categoryAssociation",categoryAssociations);
        modelAndView.addObject("access",CommonUtils.hasAccess(ApplicationConstants.COMPONENTS, userContext));
        return modelAndView;

    }

    // TODO SMCSEC is this even used
    @RequestMapping(value="vendor-template-search")
    public ModelAndView vendorTemplateSearch(@ModelAttribute("command") VendorTemplateSearch template) {

        LOGGER.error("vendorTemplateSearch is used!!!! :)");
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
        LOGGER.error("getCreateTemplatePage is used!!!! :)");
        List<Manufacture> manufacture = componentVendorTemplateService.getManufacture();
        ModelAndView mav =new ModelAndView("/admin-console/components/create-template");
        mav.addObject("manufacture",manufacture);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TEMPLATE)
    @RequestMapping("/template")
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
    public ModelAndView getComponentVisibilityOverrides() {
        ModelAndView mav = new ModelAndView("/admin-console/components/component-Visibility-Override");
        mav.addObject("overrideList", componentService.getAllComponentVisibilityOverrides());
        return mav;
    }


}
