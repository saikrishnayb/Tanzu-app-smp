package com.penske.apps.adminconsole.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.RoleService;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * Controller handling all mapping and functionality for the Admin Console Security sub tab
 * @author erik.munoz 600139451
 *
 */
@Version1Controller
@RequestMapping("/admin-console/security")
public class SecurityController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @VendorAllowed
    @RequestMapping(value = {"/navigate-security"})
    public ModelAndView navigateAppConfig() {

        Set<SecurityFunction> securityFunctions = sessionBean.getUserContext().getSecurityFunctions();

        List<LeftNav> leftNavs = SubTab.SECURITY.getLeftNavs();

        for (LeftNav leftNav : leftNavs) {

            SecurityFunction securityFunction = leftNav.getSecurityFunction();

            boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
            if (noAccess) continue;

            return new ModelAndView("redirect:/app/" + leftNav.getUrlEntry());
        }

        return new ModelAndView("/admin-console/security/noAccess");
    }

    /* ================== Users ================== */
    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value ={"/users"})
    public ModelAndView getUsersPage() {
    	
        ModelAndView mav = new ModelAndView("/admin-console/security/users");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        // If the user is a supplier.
        if (isSupplier) {
            return getVendorMainContentPage();
        }
        else {

            HeaderUser userSearchForm = new HeaderUser();
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("userList", securityService.getSearchUserList(userSearchForm, userContext));
            mav.addObject("roleList", securityService.getUserRoles(userContext.getRoleId()));
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.PENSKEUSER, userContext));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ={"/vendorUsers"})
    public ModelAndView getVendorMainContentPage(){
        UserContext userContext = sessionBean.getUserContext();
        if(CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, userContext)){
            return getVendorPageData();
        }
        if(CommonUtils.hasAccess(ApplicationConstants.ROLES_ACCESS, userContext)){
            return getRolesPage();
        }
        if(CommonUtils.hasAccess(ApplicationConstants.ORG_ACCESS, userContext)){
            return getOrgPage();
        }
        ModelAndView mav = new ModelAndView("/admin-console/security/noAccess");
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value = "/users-search")
    public ModelAndView getUserSearchResults(HeaderUser userSearchForm) {
        ModelAndView mav = new ModelAndView("/admin-console/security/users");
        UserContext userContext = sessionBean.getUserContext();

        boolean isSupplier = userContext.isVendorUser();

        if(isSupplier || userSearchForm.isVendorSearch()){
            userSearchForm.setUserTypeId(ApplicationConstants.SUPPLIER_USER);
            mav = new ModelAndView("/admin-console/security/vendorUsers");
            mav.addObject("roleList", securityService.getVendorRoles(false,userContext.getRoleId(),userContext.getOrgId()));
            mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, userContext));
            List<Org> orgList = securityService.getOrgList(null, userContext);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
        } else {
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("roleList", securityService.getUserRoles(userContext.getRoleId()));
            mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, userContext));
            mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.PENSKEUSER, userContext));
        }

        mav.addObject("userList", securityService.getSearchUserList(userSearchForm, userContext));
        mav.addObject("userSearchForm", userSearchForm);
        mav.addObject("hasBeenSearched", true);

        return mav;
    }

    /* ================== Roles ================== */
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("roles")
    public ModelAndView getRolesPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/roles");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();

        List<Role> myRoleList=null;
        if(isSupplier){
            myRoleList=roleService.getMyRoles(userContext.getRoleId(),userContext.getOrgId(), 0,null,isSupplier);
        }else{
            myRoleList=roleService.getMyRoles(userContext.getRoleId(),userContext.getOrgId(), 0,null,isSupplier);
        }
        Collections.sort(myRoleList, Role.ROLE_NAME_ASC);
        mav.addObject("roles", myRoleList);
        mav.addObject("myRole", userContext.getRoleId());
        mav.addObject("baseRoles",myRoleList);
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("roles-advanced-search")
    public ModelAndView getRolesAdvancedSearch(Role role) {
        ModelAndView mav = new ModelAndView("/admin-console/security/roles");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        // For reloading the search parameters into the form.
        mav.addObject("searchedRole", role);

        // For populating the rest of the page
        List<Role> myRoles = roleService.getMyRoles(userContext.getRoleId(), userContext.getOrgId(), role.getBaseRoleId(), role.getRoleName(), isSupplier);
        Collections.sort(myRoles, Role.ROLE_NAME_ASC);
        mav.addObject("roles", myRoles);
        List<Role> myRoles2 = roleService.getMyRoles(userContext.getRoleId(), userContext.getOrgId(), 0, null, isSupplier);
        Collections.sort(myRoles2, Role.ROLE_NAME_ASC);
        mav.addObject("baseRoles", myRoles2);

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("modify-role")
    public ModelAndView getModifyRolePage(@RequestParam("roleId") int roleId,@RequestParam("editOrCopy") String editOrCopy) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modify-role");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        List<Role> myRoles=roleService.getMyRoleDescend(userContext.getRoleId(), userContext.getOrgId(),isSupplier);
        //Removed current role and its child -- to prevent from choosing while modify
        List<Role> roles=roleService.removeCurrentRoleAndChild(roleId, myRoles, userContext.getOrgId());
        Collections.sort(roles, Role.ORG_NAME_ASC_ROLE_NAME_ASC);
        mav.addObject("roles",roles);
        mav.addObject("editRole", roleService.getRoleById(roleId));
        mav.addObject("editOrCopy", editOrCopy);
        mav.addObject("tabs", roleService.getEditRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("create-new-role")
    public ModelAndView getCreateRolePage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-new-role");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        List<Role> myRoleDescend = roleService.getMyRoleDescend(userContext.getRoleId(), userContext.getOrgId(), isSupplier);
        Collections.sort(myRoleDescend, Role.ORG_NAME_ASC_ROLE_NAME_ASC);
        mav.addObject("roles", myRoleDescend);

        return mav;
    }

    /* ================== Vendors ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors")
    public ModelAndView getVendorsPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("vendors", vendorService.getAllVendors(userContext.getOrgId()));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", userContext.isVisibleToPenske());
        mav.addObject("hasBeenSearched", false);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors-advanced-search")
    public ModelAndView getVendorsAdvancedSearch(Vendor vendor) {
        return getVendorSearchDetails(vendor);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors-advanced-search-alert")
    public ModelAndView getVendorsAdvancedSearchAlert(@RequestParam(value ="alertType", required = true) String alertType) {
        Vendor vendor=new Vendor();
        User user=new User();
        vendor.setPlanningAnalyst(user);
        vendor.setSupplySpecialist(user);
        if(alertType !=null){
            alertType=alertType.trim();
        }
        vendor.setAlertType(alertType);
        return getVendorSearchDetails(vendor);
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ={"/org"})
    public ModelAndView getOrgPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/organisation");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        // If the user is a supplier.
        if (isSupplier) {
        	List<Org> orgList=securityService.getOrgList(null, userContext);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        else {

            List<Org> orgList=securityService.getOrgList(null, userContext);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("myOrg", userContext.getOrgId());
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value = "/org-search")
    public ModelAndView getOrgSearchResults(Org org) {
        ModelAndView mav = new ModelAndView("/admin-console/security/organisation");
        UserContext userContext = sessionBean.getUserContext();
        boolean isSupplier = userContext.isVendorUser();
        if(StringUtils.isBlank(org.getOrgName())){
            org.setOrgName(null);
        }
        // If the user is a supplier.
        if (isSupplier) {
            mav.addObject("orgList", securityService.getOrgList(org, userContext));
        }
        else {
            mav.addObject("orgList", securityService.getOrgList(org, userContext));
        }
        mav.addObject("orgListDrop", securityService.getOrgList(null, userContext));
        mav.addObject("hasBeenSearched", true);
        mav.addObject("myOrg", userContext.getOrgId());
        return mav;
    }

    private ModelAndView getVendorPageData(){
        ModelAndView mav = new ModelAndView("/admin-console/security/vendorUsers");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("userList", securityService.getVendorUserList(userContext));
        // If the user is a supplier.
        boolean isVendor = userContext.isVendorUser();
        mav.addObject("roleList", securityService.getVendorRoles(isVendor,userContext.getRoleId(),userContext.getOrgId()));
        List<Org> orgList = securityService.getOrgList(null, userContext);
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, userContext));
        return mav;
    }

    private ModelAndView getVendorSearchDetails(Vendor vendor){
    	UserContext userContext = sessionBean.getUserContext();
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        mav.addObject("searchedVendor", vendor);

        // For populating the rest of the page
        mav.addObject("vendors", vendorService.getVendorsBySearchConditions(userContext.getOrgId() , vendor));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", userContext.isVisibleToPenske());
        mav.addObject("hasBeenSearched", true);
        return mav;
    }
}
