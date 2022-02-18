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
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.RoleService;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;

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

        Set<SecurityFunction> securityFunctions = sessionBean.getUser().getSecurityFunctions();

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
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        // If the user is a supplier.
        if (isSupplier)
            return getVendorMainContentPage();
        else
        {
            UserSearchForm userSearchForm = new UserSearchForm();
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("userList", securityService.getSearchUserList(userSearchForm, user));
            mav.addObject("roleList", securityService.getUserRoles(user.getRoleId()));
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("access", user.hasSecurityFunction(SecurityFunction.MANAGE_USERS));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ={"/vendorUsers"})
    public ModelAndView getVendorMainContentPage(){
        User user = sessionBean.getUser();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS))
            return getVendorPageData();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ROLES))
            return getRolesPage();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ORG))
            return getOrgPage();
        ModelAndView mav = new ModelAndView("/admin-console/security/noAccess");
        return mav;
    }
    
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ={"/vendor-users"})
    public ModelAndView getVendorUsers(){
        User user = sessionBean.getUser();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS))
            return getVendorUsersPageData();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ROLES))
            return getRolesPage();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ORG))
            return getOrgPage();
        ModelAndView mav = new ModelAndView("/admin-console/security/noAccess");
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value = "/users-search")
    public ModelAndView getUserSearchResults(UserSearchForm userSearchForm) {
        ModelAndView mav = new ModelAndView("/admin-console/security/users");
        User user = sessionBean.getUser();

        boolean isSupplier = user.getUserType() == UserType.VENDOR;

        if(isSupplier || userSearchForm.isVendorSearch()){
            userSearchForm.setUserTypeId(ApplicationConstants.SUPPLIER_USER);
            mav = new ModelAndView("/admin-console/security/vendorUsers");
            mav.addObject("roleList", securityService.getVendorRoles(false,user.getRoleId(),user.getOrgId()));
            mav.addObject("accessVendor", user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS));
            List<Org> orgList = securityService.getOrgList(null, user);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
        } else {
            userSearchForm.setUserTypeId(ApplicationConstants.PENSKE_USER);
            mav.addObject("roleList", securityService.getUserRoles(user.getRoleId()));
            mav.addObject("accessVendor", user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS));
            mav.addObject("access", user.hasSecurityFunction(SecurityFunction.MANAGE_USERS));
        }

        mav.addObject("userList", securityService.getSearchUserList(userSearchForm, user));
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
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;

        List<Role> myRoleList=null;
        if(isSupplier){
            myRoleList=roleService.getMyRoles(user.getRoleId(),user.getOrgId(), 0,null,isSupplier);
        }else{
            myRoleList=roleService.getMyRoles(user.getRoleId(),user.getOrgId(), 0,null,isSupplier);
        }
        Collections.sort(myRoleList, Role.ROLE_NAME_ASC);
        mav.addObject("roles", myRoleList);
        mav.addObject("myRole", user.getRoleId());
        mav.addObject("baseRoles",myRoleList);
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("roles-advanced-search")
    public ModelAndView getRolesAdvancedSearch(Role role) {
        ModelAndView mav = new ModelAndView("/admin-console/security/roles");
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        // For reloading the search parameters into the form.
        mav.addObject("searchedRole", role);

        // For populating the rest of the page
        List<Role> myRoles = roleService.getMyRoles(user.getRoleId(), user.getOrgId(), role.getBaseRoleId(), role.getRoleName(), isSupplier);
        Collections.sort(myRoles, Role.ROLE_NAME_ASC);
        mav.addObject("roles", myRoles);
        List<Role> myRoles2 = roleService.getMyRoles(user.getRoleId(), user.getOrgId(), 0, null, isSupplier);
        Collections.sort(myRoles2, Role.ROLE_NAME_ASC);
        mav.addObject("baseRoles", myRoles2);

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("modify-role")
    public ModelAndView getModifyRolePage(@RequestParam("roleId") int roleId,@RequestParam("editOrCopy") String editOrCopy) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modify-role");
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        List<Role> myRoles=roleService.getMyRoleDescend(user.getRoleId(), user.getOrgId(),isSupplier);
        //Removed current role and its child -- to prevent from choosing while modify
        List<Role> roles=roleService.removeCurrentRoleAndChild(roleId, myRoles, user.getOrgId());
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
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        List<Role> myRoleDescend = roleService.getMyRoleDescend(user.getRoleId(), user.getOrgId(), isSupplier);
        Collections.sort(myRoleDescend, Role.ORG_NAME_ASC_ROLE_NAME_ASC);
        mav.addObject("roles", myRoleDescend);

        return mav;
    }

    /* ================== Vendors ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("vendors")
    public ModelAndView getVendorsPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        User user = sessionBean.getUser();
        mav.addObject("vendors", vendorService.getAllVendors(user.getOrgId()));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", user.getUserType() == UserType.PENSKE);
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
        EditableUser user = new EditableUser();
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
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        // If the user is a supplier.
        if (isSupplier) {
        	List<Org> orgList=securityService.getOrgList(null, user);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        else {

            List<Org> orgList=securityService.getOrgList(null, user);
            Collections.sort(orgList, Org.ORG_NAME_ASC);
            mav.addObject("orgList", orgList);
            mav.addObject("orgListDrop", orgList);
        }
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("myOrg", user.getOrgId());
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value = "/org-search")
    public ModelAndView getOrgSearchResults(Org org) {
        ModelAndView mav = new ModelAndView("/admin-console/security/organisation");
        User user = sessionBean.getUser();
        boolean isSupplier = user.getUserType() == UserType.VENDOR;
        if(StringUtils.isBlank(org.getOrgName())){
            org.setOrgName(null);
        }
        // If the user is a supplier.
        if (isSupplier) {
            mav.addObject("orgList", securityService.getOrgList(org, user));
        }
        else {
            mav.addObject("orgList", securityService.getOrgList(org, user));
        }
        mav.addObject("orgListDrop", securityService.getOrgList(null, user));
        mav.addObject("hasBeenSearched", true);
        mav.addObject("myOrg", user.getOrgId());
        return mav;
    }

    private ModelAndView getVendorPageData(){
        ModelAndView mav = new ModelAndView("/admin-console/security/vendorUsers");
        User user = sessionBean.getUser();
        mav.addObject("userList", securityService.getVendorUserList(user));
        // If the user is a supplier.
        boolean isVendor = user.getUserType() == UserType.VENDOR;
        mav.addObject("roleList", securityService.getVendorRoles(isVendor,user.getRoleId(),user.getOrgId()));
        List<Org> orgList = securityService.getOrgList(null, user);
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("accessVendor", user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS));
        return mav;
    }
    
    private ModelAndView getVendorUsersPageData(){
        ModelAndView mav = new ModelAndView("/admin-console/security/vendor-users");
        User user = sessionBean.getUser();
        mav.addObject("userList", securityService.getVendorUserList(user));
        // If the user is a supplier.
        boolean isVendor = user.getUserType() == UserType.VENDOR;
        mav.addObject("roleList", securityService.getVendorRoles(isVendor,user.getRoleId(),user.getOrgId()));
        List<Org> orgList = securityService.getOrgList(null, user);
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("accessVendor", user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS));
        mav.addObject("vendorUsersPage", true);
        return mav;
    }

    private ModelAndView getVendorSearchDetails(Vendor vendor){
    	User user = sessionBean.getUser();
        ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
        mav.addObject("searchedVendor", vendor);

        // For populating the rest of the page
        mav.addObject("vendors", vendorService.getVendorsBySearchConditions(user.getOrgId() , vendor));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());
        mav.addObject("alertTypeList", vendorService.getAllAlerts());
        mav.addObject("isPenskeUser", user.getUserType() == UserType.PENSKE);
        mav.addObject("hasBeenSearched", true);
        return mav;
    }
}
