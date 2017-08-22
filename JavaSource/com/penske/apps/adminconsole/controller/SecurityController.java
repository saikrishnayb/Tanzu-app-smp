package com.penske.apps.adminconsole.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.annotation.VendorAllowed;
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

/**
 * Controller handling all mapping and functionality for the Admin Console Security sub tab
 * @author erik.munoz 600139451
 *
 */
@DefaultController
@RequestMapping("/admin-console/security")
public class SecurityController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SecurityService securityService;


    /* ================== Users ================== */
    // TODO SMCSEC Admin console mapping never being hit
    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value ={"/users"})
    public ModelAndView getUsersPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/users");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        // If the user is a supplier.
        if (isSupplier) {
            return getVendorMainContentPage(session);
        }
        else {

            HeaderUser userSearchForm = new HeaderUser();
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("userList", securityService.getSearchUserList(userSearchForm, currentUser));
            mav.addObject("roleList", securityService.getUserRoles(currentUser.getRoleId()));
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.PENSKEUSER, session));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ={"/vendorUsers"})
    public ModelAndView getVendorMainContentPage(HttpSession session){
        if(CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, session)){
            return getVendorPageData(session);
        }
        if(CommonUtils.hasAccess(ApplicationConstants.ROLES_ACCESS, session)){
            return getRolesPage(session);
        }
        if(CommonUtils.hasAccess(ApplicationConstants.ORG_ACCESS, session)){
            return getOrgPage(session);
        }
        ModelAndView mav = new ModelAndView("/admin-console/security/noAccess");
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value = "/users-search")
    public ModelAndView getUserSearchResults(HeaderUser userSearchForm,HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/users");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");

        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;

        if(isSupplier || userSearchForm.isVendorSearch()){
            userSearchForm.setUserTypeId(ApplicationConstants.SUPPLIER_USER);
            mav = new ModelAndView("/admin-console/security/vendorUsers");
            mav.addObject("roleList", securityService.getVendorRoles(false,currentUser.getRoleId(),currentUser.getOrgId()));
            mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, session));
        } else {
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("roleList", securityService.getUserRoles(currentUser.getRoleId()));
            mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, session));
            mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.PENSKEUSER, session));
        }

        mav.addObject("userList", securityService.getSearchUserList(userSearchForm, currentUser));
        mav.addObject("userSearchForm", userSearchForm);
        mav.addObject("hasBeenSearched", true);

        return mav;
    }



    /* ================== Roles ================== */
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("roles")
    public ModelAndView getRolesPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/roles");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;

        List<Role> myRoleList=null;
        if(isSupplier){
            myRoleList=roleService.getMyRoles(currentUser.getRoleId(),currentUser.getOrgId(), 0,null,isSupplier);
        }else{
            myRoleList=roleService.getMyRoles(currentUser.getRoleId(),currentUser.getOrgId(), 0,null,isSupplier);
        }
        mav.addObject("roles", myRoleList);
        mav.addObject("myRole", currentUser.getRoleId());
        mav.addObject("baseRoles",myRoleList);
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("roles-advanced-search")
    public ModelAndView getRolesAdvancedSearch(Role role,HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/roles");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        // For reloading the search parameters into the form.
        mav.addObject("searchedRole", role);

        // For populating the rest of the page
        mav.addObject("roles", roleService.getMyRoles(currentUser.getRoleId(),currentUser.getOrgId(),role.getBaseRoleId(),role.getRoleName(),isSupplier));
        mav.addObject("baseRoles",roleService.getMyRoles(currentUser.getRoleId(),currentUser.getOrgId(), 0,null,isSupplier));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("modify-role")
    public ModelAndView getModifyRolePage(@RequestParam("roleId") int roleId,@RequestParam("editOrCopy") String editOrCopy,HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modify-role");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        List<Role> myRoles=roleService.getMyRoleDescend(currentUser.getRoleId(),currentUser.getOrgId(),isSupplier);
        //Removed current role and its child -- to prevent from choosing while modify
        List<Role> roles=roleService.removeCurrentRoleAndChild(roleId, myRoles,currentUser.getOrgId());
        mav.addObject("roles",roles);
        mav.addObject("editRole", roleService.getRoleById(roleId));
        mav.addObject("editOrCopy", editOrCopy);
        mav.addObject("tabs", roleService.getEditRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("create-new-role")
    public ModelAndView getCreateRolePage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-new-role");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        mav.addObject("roles", roleService.getMyRoleDescend(currentUser.getRoleId(),currentUser.getOrgId(),isSupplier));

        return mav;
    }

    /* ================== Vendors ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors")
    public ModelAndView getVendorsPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        mav.addObject("vendors", vendorService.getAllVendors(currentUser.getOrgId()));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", currentUser.getUserTypeId() == ApplicationConstants.PENSKE_USER);
        mav.addObject("hasBeenSearched", false);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors-advanced-search")
    public ModelAndView getVendorsAdvancedSearch(Vendor vendor,HttpSession session) {
        //	ModelAndView mav =getVendorSearchDetails(session, vendor);
        //	mav.addObject("hasBeenSearched", true);
        return getVendorSearchDetails(session, vendor);
    }


    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors-advanced-search-alert")
    public ModelAndView getVendorsAdvancedSearchAlert(@RequestParam(value ="alertType", required = true) String alertType,HttpSession session) {
        Vendor vendor=new Vendor();
        User user=new User();
        vendor.setPlanningAnalyst(user);
        vendor.setSupplySpecialist(user);
        if(alertType !=null){
            alertType=alertType.trim();
        }
        vendor.setAlertType(alertType);
        return getVendorSearchDetails(session, vendor);
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ={"/org"})
    public ModelAndView getOrgPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/organisation");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        // If the user is a supplier.
        if (isSupplier) {
            List<Org> orgList=securityService.getOrgList(currentUser);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        else {

            HeaderUser userSearchForm = new HeaderUser();
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            List<Org> orgList=securityService.getOrgList(currentUser);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("myOrg", currentUser.getOrgId());
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value = "/org-search")
    public ModelAndView getOrgSearchResults(Org org, HttpSession session) {
        ModelAndView mav = new ModelAndView("/admin-console/security/organisation");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        boolean isSupplier = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        currentUser.setOrgName(null);
        currentUser.setParentOrgId(null);
        if(org.getOrgName() !=null && !org.getOrgName().isEmpty()){
            currentUser.setOrgName(org.getOrgName());
        }
        if(org.getParentOrgId() !=0){
            currentUser.setParentOrgId(org.getParentOrgId());
        }
        // If the user is a supplier.
        if (isSupplier) {
            mav.addObject("orgList", securityService.getOrgList(currentUser));
        }
        else {
            mav.addObject("orgList", securityService.getOrgList(currentUser));
        }
        currentUser.setOrgName(null);
        currentUser.setParentOrgId(null);
        mav.addObject("orgListDrop", securityService.getOrgList(currentUser));
        mav.addObject("hasBeenSearched", true);
        mav.addObject("myOrg", currentUser.getOrgId());
        return mav;
    }

    private ModelAndView getVendorPageData(HttpSession session){
        ModelAndView mav = new ModelAndView("/admin-console/security/vendorUsers");
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        mav.addObject("userList", securityService.getVendorUserList(currentUser));
        // If the user is a supplier.
        boolean isVendor = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
        mav.addObject("roleList", securityService.getVendorRoles(isVendor,currentUser.getRoleId(),currentUser.getOrgId()));
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("accessVendor",CommonUtils.hasAccess(ApplicationConstants.VENDORUSER, session));
        return mav;
    }

    private ModelAndView getVendorSearchDetails(HttpSession session,Vendor vendor){
        HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        mav.addObject("searchedVendor", vendor);

        // For populating the rest of the page
        mav.addObject("vendors", vendorService.getVendorsBySearchConditions(currentUser.getOrgId() , vendor));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", currentUser.getUserTypeId() == ApplicationConstants.PENSKE_USER);
        mav.addObject("hasBeenSearched", true);
        return mav;
    }
}
