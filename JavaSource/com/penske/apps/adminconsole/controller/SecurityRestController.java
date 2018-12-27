package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.ImageFile;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.RoleService;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.UserCreationService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.exception.SMCException;
import com.penske.apps.suppliermgmt.model.UserContext;

@Controller
@RequestMapping("/admin-console/security")
public class SecurityRestController {

	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
    @Autowired
    private SecurityService securityService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserCreationService userCreationService;

    private static final Logger LOGGER = Logger.getLogger(SecurityRestController.class);

    /* ================== Users ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="get-edit-user-modal-content")
    @ResponseBody
    public ModelAndView getEditInfo(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-user-modal-content");
        UserContext userContext = sessionBean.getUserContext();
        User editableUser = securityService.getEditInfo(userId, userType);

        mav.addObject("isCreatePage", false);

        if (userType.equalsIgnoreCase("vendor")) {

        }
        else {
            List<Role> penskeRoles = securityService.getPenskeRoles(userContext.getRoleId());
            Collections.sort(penskeRoles, Role.ROLE_NAME_ASC);
            mav.addObject("userRoles", penskeRoles);
        }

        if(userContext.isVendorUser()) {

        }
        else {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
        }
        
    	List<Org> orgList=securityService.getPenskeUserOrgList();
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("currentUser", userContext);
        mav.addObject("orgList", orgList);
        mav.addObject("editableUser", editableUser);
        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="get-edit-vendor-user-modal-content")
    @ResponseBody
    public ModelAndView getEditVendorInfo(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-vendor-user-modal-content");
        UserContext userContext = sessionBean.getUserContext();
        boolean isVendor = userContext.isVendorUser();
        User editableUser = securityService.getEditInfo(userId, userType);
        mav.addObject("isCreatePage", false);
        mav.addObject("userTypes", securityService.getVendorUserTypes());
        mav.addObject("currentUser", userContext);

        List<Role> vendorRoles = securityService.getVendorRoles(isVendor, userContext.getRoleId(), userContext.getOrgId());
        Collections.sort(vendorRoles, Role.ROLE_NAME_ASC);

        mav.addObject("userRoles", vendorRoles);

        List<Org> vendorOrg = securityService.getVendorOrg(isVendor, userContext.getOrgId());
        Collections.sort(vendorOrg, Org.ORG_NAME_ASC);
        mav.addObject("orgList", vendorOrg);
        mav.addObject("editableUser", editableUser);
        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS, SecurityFunction.MANAGE_ORG})
    @RequestMapping(value ="get-permissions-accordion-content")
    @ResponseBody
    public ModelAndView getPermissionsInfo(@RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/permissions-accordion");

        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("deactivate-user")
    @ResponseBody
    public void modifyUserStatus(@RequestParam(value="userId") int userId,@RequestParam(value="isVendorUser") boolean isVendorUserFlow, HttpServletResponse response) throws Exception {
        try{
        	UserContext userContext = sessionBean.getUserContext();
            if(isVendorUserFlow){//going to vendor user deactivation flow
                userCreationService.isEligibleToDeactivate(userId, isVendorUserFlow,userContext.getUserSSO());
            }else{
                securityService.modifyUserStatus(userId, userContext);
            }
        }catch (Exception e) {
            LOGGER.error("Error while deactivation user: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while deactivation user.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while deactivation user.");
            response.flushBuffer();
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("get-deactivate-user-modal-content")
    @ResponseBody
    public ModelAndView getDeactivateInfo(@RequestParam(value="email") String email, @RequestParam(value="userId") String userId,
            @RequestParam(value="isVendorUser") boolean isVendorUser) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/deactivate-user-modal-content");

        mav.addObject("email", email);
        mav.addObject("userId", userId);
        mav.addObject("isVendorUser",isVendorUser);
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping("get-deactivate-org-modal-content")
    @ResponseBody
    public ModelAndView getDeactivateOrgInfo(@RequestParam(value="orgName") String orgName, @RequestParam(value="orgId") int orgId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/deactivate-org-modal-content");
        boolean canDelete=securityService.checkForUsers(orgId);
        mav.addObject("canDelete", canDelete);
        mav.addObject("orgName", orgName);
        if(canDelete){
            mav.addObject("childOrgName", securityService.getOrgChild(orgId));
            mav.addObject("roles", roleService.getMyDescendRoleByOrgId(orgId));
        }
        mav.addObject("orgId", orgId);

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping("delete-org")
    @ResponseBody
    public void modifyOrgStatus(@RequestParam(value="orgId") int orgId, HttpServletResponse response) throws Exception {
        try{
        	UserContext userContext = sessionBean.getUserContext();
        	String userSSO = userContext.getUserSSO();
            securityService.deleteOrg(orgId, userSSO);
        }catch (Exception e) {
            LOGGER.error("Error while deactivation ORG: " + e);
            CommonUtils.getCommonErrorAjaxResponse(response,"");
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping(value ="get-role-list")
    @ResponseBody
    public List<Role> getRoles(@RequestParam("userTypeId") int userTypeId, @RequestParam(value="manufacturer", required=false) String manufacturer) {
    	UserContext userContext = sessionBean.getUserContext();

        //Not A Supplier
        if(!userContext.isVendorUser()) {
            // Penske User
            if(userTypeId == 1) {
                return securityService.getPenskeRoles(userContext.getRoleId());
            }
        }

        return securityService.getSupplierRoles(manufacturer, userContext);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="edit-user-static")
    @ResponseBody
    public User modifyUserInfoStatic(User user, @RequestParam("vendorIds") int[] vendorIds, @RequestParam(value="signatureImage", required=false)MultipartFile signatureImage,
            @RequestParam(value="initialsImage", required=false)MultipartFile initialsImage){

        boolean initialsEmpty = initialsImage == null || initialsImage.getSize() == 0;
        boolean signatureEmpty = signatureImage == null || signatureImage.getSize() == 0;

        if(!initialsEmpty){
            ImageFile initFile = new ImageFile(initialsImage);
            user.setInitFile(initFile);
        }

        if(!signatureEmpty){
            ImageFile signFile = new ImageFile(signatureImage);
            user.setSignFile(signFile);
        }

        UserContext userContext = sessionBean.getUserContext();

        securityService.modifyUserInfo(user, vendorIds, userContext);
        User userInfo = securityService.getUser(user.getUserId());

        return userInfo;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="edit-vendor-user-static")
    @ResponseBody
    public User modifyVendorUserInfoStatic(User user, HttpServletResponse response) throws Exception{

        try{
            UserContext userContext = sessionBean.getUserContext();
            user.setModifiedBy(userContext.getUserSSO());
            userCreationService.updateUserInfo(user, false);
            User userInfo = securityService.getUser(user.getUserId());
            return userInfo;
        }
        catch (UserServiceException e) {
            if(IUserConstants.DUP_SSO_ERROR_CODE==e.getErrorCode()){
                LOGGER.error(IUserConstants.DUP_SSO_ERROR_MESSAGE + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_DUP_SSO:"+String.valueOf(e.getErrorCode()));
            }else if(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE==e.getErrorCode()){
                LOGGER.error(IUserConstants.NOT_STANDARD_SSO_ERROR_MESSAGE + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_NOT_STANDARD_SSO:"+String.valueOf(e.getErrorCode()));
            }else{
                LOGGER.error("Error while creating user: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while creating user. "+e.getErrorCode());
            response.flushBuffer();
        }
        catch (Exception e) {
            LOGGER.error("Error while updating user: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while updating user.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while updating user.");
            response.flushBuffer();
        }
        return null;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping(value ="is-username-available",  method = RequestMethod.POST)
    @ResponseBody
    public void isUserNameAvailible(@RequestParam("ssoId")String ssoId, @RequestParam("userId") int userId,HttpServletResponse response) throws IOException {
        if(securityService.doesUserExist(ssoId, userId)) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An active user already exists with the user name " + ssoId + ".");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An active / inactive user already exists with the user name " + ssoId + ".");
                response.flushBuffer();
            } catch (IOException e) {
                throw e;
            }
        }else{
            LOGGER.info("No issue>>>>>>>>>>>>>>>");
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="is-username-exist",  method = RequestMethod.POST)
    @ResponseBody
    public User isUserExist(@RequestParam("ssoId")String ssoId, @RequestParam("userId") int userId,
            @RequestParam("isCreateOrEdit") String isCreateOrEdit,HttpServletResponse response) throws Exception {
        try{
            User user=securityService.doesUserExistVendor(ssoId, userId, true, isCreateOrEdit);
            if(user.getReturnFlg() == 2){
                user.setSupportNumber(userCreationService.getSupportNumber());
            }
            return user;
        }catch (Exception e) {
            LOGGER.error("Error in processing the last request.: " + e);
            CommonUtils.getCommonErrorAjaxResponse(response,"");
        }
        return null;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="/create-user-page")
    @ResponseBody
    public ModelAndView getCreateUSerPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-user");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("currentUser", userContext);
        if(userContext.isVendorUser()) {

        }
        else if(userContext.isVisibleToPenske()) {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
        }
        List<Org> orgList = securityService.getPenskeUserOrgList();
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        // If the page is an error page.
        mav.addObject("isCreatePage", true);

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="/create-vendor-user-page")
    @ResponseBody
    public ModelAndView getCreateVendorUserPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-vendor-user");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("currentUser", userContext);
        boolean isVendor = userContext.isVendorUser();
        mav.addObject("userTypes", securityService.getVendorUserTypes());
        mav.addObject("userRoles", securityService.getVendorRoles(isVendor, userContext.getRoleId(), userContext.getOrgId()));
        List<Org> orgList = securityService.getVendorOrg(isVendor, userContext.getOrgId());
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        // If the page is an error page.
        mav.addObject("isCreatePage", true);

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="/create-user", method = RequestMethod.POST)
    @ResponseBody
    public void addUser(User user, @RequestParam("vendorIds")int[] vendorIds, @RequestParam(value="signatureImage", required=false)MultipartFile signatureImage,
            @RequestParam(value="initialsImage", required=false)MultipartFile initialsImage,HttpServletResponse response) throws Exception{
        try{
            boolean initialsEmpty = initialsImage == null;
            boolean signatureEmpty = signatureImage == null;

            if(!initialsEmpty){
                ImageFile initFile = new ImageFile(initialsImage);
                user.setInitFile(initFile);
            }

            if(!signatureEmpty){
                ImageFile signFile = new ImageFile(signatureImage);
                user.setSignFile(signFile);
            }
            UserContext userContext = sessionBean.getUserContext();
            securityService.addUser(user, vendorIds, userContext);
        }catch (Exception e) {
            LOGGER.error("Error while creating user: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while creating user.");
            response.flushBuffer();
        }
    }


    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="/create-vendor-user", method = RequestMethod.POST)
    @ResponseBody
    public void addVendorUser(User user, HttpServletResponse response) throws Exception{
        try{

            UserContext userContext = sessionBean.getUserContext();
            user.setCreatedBy(userContext.getUserSSO());
            userCreationService.insertUserInfo(user);
        }
        catch (UserServiceException e) {
            if(IUserConstants.DUP_SSO_ERROR_CODE==e.getErrorCode()){
                LOGGER.error(IUserConstants.DUP_SSO_ERROR_MESSAGE + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_DUP_SSO:"+String.valueOf(e.getErrorCode()));
            }else if(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE==e.getErrorCode()){
                LOGGER.error(IUserConstants.NOT_STANDARD_SSO_ERROR_MESSAGE + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_NOT_STANDARD_SSO:"+String.valueOf(e.getErrorCode()));
            }else{
                LOGGER.error("Error while creating user: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while creating user. "+e.getErrorCode());
            response.flushBuffer();
        }
        catch (Exception e) {
            LOGGER.error("Error while creating user: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while creating user.");
            response.flushBuffer();
        }
    }

    /* ================== Roles ================== */
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-create-role-hierarchy")
    @ResponseBody
    public ModelAndView getCreateRoleHierarchy(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-hierarchy");
        UserContext userContext = sessionBean.getUserContext();
        if (roleId != 0) {
            mav.addObject("role", roleService.getCreateRoleHierarchy(roleId,userContext.getOrgId()));
        }

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-edit-role-hierarchy")
    @ResponseBody
    public ModelAndView getEditRoleHierarchy(@RequestParam("roleId") int roleId, @RequestParam("flag") int flag) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-hierarchy");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("role", roleService.getEditRoleHierarchy(roleId, flag, userContext.getOrgId()));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-edit-role-permissions")
    @ResponseBody
    public ModelAndView getEditRolePermissions(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-permissions");
        mav.addObject("tabs", roleService.getEditRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-role-permissions")
    @ResponseBody
    public ModelAndView getRolePermissions(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-permissions");

        mav.addObject("tabs", roleService.getCreateRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("insert-role")
    @ResponseBody
    public void insertRole(Role role, @RequestParam("functionIds") int[] functionIds, HttpServletResponse response) throws Exception {
    	UserContext userContext = sessionBean.getUserContext();
        role.setCreatedBy(userContext.getUserSSO());
        role.setOem(String.valueOf(userContext.getOrgId()));
        if(roleService.checkRoleExist(role,true)) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An active role already exists with the role name "+role.getBaseRoleName()+".");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An active role already exists with the role name "+role.getBaseRoleName()+".");
                response.flushBuffer();
            }
            catch (IOException e) {
                throw e;
            }
        }else{
            roleService.addRole(role, functionIds);
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("modify-role-submit")
    @ResponseBody
    public void modifyRole(Role role, @RequestParam("functionIds") int[] functionIds,HttpServletResponse response) throws Exception {
    	UserContext userContext = sessionBean.getUserContext();
        role.setModifiedBy(userContext.getUserSSO());
        if(roleService.checkRoleExist(role,false)) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An active role already exists with the role name "+role.getBaseRoleName()+".");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An active role already exists with the role name "+role.getBaseRoleName()+".");
                response.flushBuffer();
            }
            catch (IOException e) {
                throw e;
            }
        }else{
            roleService.modifyRole(role, functionIds);
        }

    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("deactivate-role")
    @ResponseBody
    public ModelAndView deactivateRole(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView();
        UserContext userContext = sessionBean.getUserContext();
        // If any users are found with the role.
        if (roleService.checkForUsers(roleId)) {
            mav.setViewName("/jsp-fragment/admin-console/security/deactivate-role-error-modal");
        }
        else {
            mav.setViewName("/jsp-fragment/admin-console/security/deactivate-role-modal");
            List<Role> subRoles=roleService.getMyDescendRoleByRoleIdOrgId(roleId, userContext.getOrgId());
            if(subRoles !=null && !subRoles.isEmpty()){
                Role baseRole=subRoles.get(0);
                if(baseRole.getRoleId() == roleId){
                    subRoles.remove(0);
                }
                mav.addObject("subRoles",subRoles);
            }
        }

        mav.addObject("role", roleService.getRoleById(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("deactivate-role-confirm")
    @ResponseBody
    public void confirmRoleDeactivation(@RequestParam("roleId") int roleId) {
        UserContext userContext = sessionBean.getUserContext();

        roleService.modifyRoleStatus(roleId,userContext.getUserSSO());
    }

    /* ================== Vendors ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("edit-vendor")
    @ResponseBody
    public ModelAndView getEditVendorInformation(@RequestParam("vendorId") int vendorId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-vendor-modal");

        mav.addObject("vendor", vendorService.getEditVendorInformation(vendorId));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("view-vendor")
    @ResponseBody
    public ModelAndView getViewVendorInformation(@RequestParam("vendorId") int vendorId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/view-vendor-modal");

        mav.addObject("vendor", vendorService.getViewVendorInformation(vendorId));

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("get-analysts-and-specialists")
    @ResponseBody
    public ModelAndView getAllAnalystsAndSpecialists() {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/mass-update-vendor-modal");

        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("modify-vendor-info")
    @ResponseBody
    public Vendor modifyVendorInfo(Vendor vendor) {
        UserContext userContext = sessionBean.getUserContext();
        vendorService.modifyVendorInformation(vendor,userContext);
        List<Vendor> updatedVendors = vendorService.getAllVendors( userContext.getOrgId() );
        for(Vendor updatedVendor: updatedVendors){
            if(updatedVendor.getVendorId() == vendor.getVendorId())
                return updatedVendor;
        }
        return null;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("mass-update-vendors")
    @ResponseBody
    public void modifyVendorsByVendorId(Vendor vendor, @RequestParam("vendorIds") int[] vendorIds) {
        vendorService.modifyVendorsMassUpdate(vendorIds, vendor);
    }


    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping(value="sso-user-lookup" , method=RequestMethod.GET)
    @ResponseBody
    public User modifyVendorsByVendorId(@RequestParam("ssoId") String ssoId,HttpServletResponse response) {
        User user = null;
        try{
            user = securityService.doesUserExistInPenske(ssoId);
        }catch(SMCException sme){
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sme.getErrorDetails().getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(sme.getErrorDetails().getMessage());
                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }catch (Exception e) {
            LOGGER.debug(e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading user data");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error while loading user data");
                response.flushBuffer();
            } catch (IOException ie) {
                LOGGER.error(ie.getMessage());
            }
        }
        return	user;

    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("get-signature-preview")
    @ResponseBody
    public String getSignatureImagePreview(@RequestParam(value="userId") int userId) {
        String signFile = securityService.getSignatureImage(userId);


        return signFile;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("get-intials-preview")
    @ResponseBody
    public String getInitialsImagePreview(@RequestParam(value="userId") int userId) {
        String initFile = securityService.getInitialsImage(userId);


        return initFile;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("delete-intials")
    @ResponseBody
    public String deleteInitialsImage(@RequestParam(value="userId") int userId,@RequestParam(value="ssoId") String ssoId) {
        boolean deleted = securityService.deleteInitialsImage(userId,ssoId);



        return "Succes:"+deleted;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("delete-signature")
    @ResponseBody
    public String deleteSignatureImage(@RequestParam(value="userId") int userId,@RequestParam(value="ssoId") String ssoId) {
        boolean  deleted = securityService.deleteSignatureImage(userId,ssoId);


        return "Succes:"+deleted;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("sso-user-lookup-refresh")
    @ResponseBody
    public ModelAndView ssoLookupRefresh(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType,HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/sso-refresh-modal-content");

        if("1".equalsIgnoreCase(userType)){
            userType = "Penske";

        }else{
            userType = "Vendor";
        }
        User editableUser = securityService.getEditInfo(userId, userType);

        User user = null;
        try{
            user = securityService.doesUserExistInPenske(editableUser.getSsoId());
        }catch(SMCException sme){
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sme.getErrorDetails().getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(sme.getErrorDetails().getMessage());
                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }catch (Exception e) {
            LOGGER.debug(e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading user data");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error while loading user data");
                response.flushBuffer();
            } catch (IOException ie) {
                LOGGER.error(ie.getMessage());
            }
        }
        user.setSsoUserUpdated( editableUser.validateUserWithSSOData(user));
        mav.addObject("editableUser", editableUser);
        mav.addObject("ssoDataUpdated", user);

        return mav;
    }


    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="refresh-user-with-sso-data")
    @ResponseBody
    public User refreshUserWithSSOData( @RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType,HttpServletResponse response){

        if("1".equalsIgnoreCase(userType)){
            userType = "Penske";

        }else{
            userType = "Vendor";
        }
        User editableUser = securityService.getEditInfo(userId, userType);

        User user = null;
        try{
            user = securityService.doesUserExistInPenske(editableUser.getSsoId());
        }catch(SMCException sme){
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sme.getErrorDetails().getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(sme.getErrorDetails().getMessage());
                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        editableUser.setEmail(user.getEmail());
        editableUser.setPhoneLdap(user.getPhone());
        editableUser.setPhone(editableUser.getPhoneLdap());
        editableUser.setFirstName(user.getFirstName());
        editableUser.setLastName(user.getLastName());
        editableUser.setModifiedBy(editableUser.getSsoId());
        editableUser.setGessouid(user.getGessouid());
        securityService.refreshUserWithSSOData(editableUser);

        return editableUser;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="/update-org", method = RequestMethod.POST)
    @ResponseBody
    public void updateOrg(Org org, HttpServletResponse response) throws Exception{
        try{
            UserContext userContext = sessionBean.getUserContext();
            org.setModifiedBy(userContext.getUserSSO());
            securityService.updateOrg(org);
        }catch (Exception e) {
            LOGGER.error("Error Processing the Org: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Processing the Org");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error Processing the Org");
            response.flushBuffer();
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="/create-org-page")
    @ResponseBody
    public ModelAndView getCreateOrgPage() {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-org");
        UserContext userContext = sessionBean.getUserContext();
        if(userContext.isVendorUser()) {
            mav.addObject("orgList", securityService.getOrgList(null, userContext));
        }
        else if(userContext.isVisibleToPenske()) {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
            mav.addObject("orgList", securityService.getOrgList(null, userContext));
        }
        // If the page is an error page.
        mav.addObject("isCreatePage", true);

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="/create-org", method = RequestMethod.POST)
    @ResponseBody
    public void addOrg(Org org, HttpServletResponse response) throws Exception{
        try{
            UserContext userContext = sessionBean.getUserContext();
            org.setCreatedBy(userContext.getUserSSO());
            securityService.addOrg(org);
        }catch (Exception e) {
            LOGGER.error("Error Processing the Org: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Processing the Org");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error Processing the Org");
            response.flushBuffer();
        }
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="/get-vendor-hierarchy-page")
    @ResponseBody
    public ModelAndView getVendorHierarchyPage(@RequestParam(value="parentOrg") int parentOrg) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/vendor-hierarchy");
        mav.addObject("vendorList", securityService.getVendorList("","",parentOrg));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="filter-vendor-list")
    @ResponseBody
    public ModelAndView filterVendorList( @RequestParam(value="corp") String corp, @RequestParam(value="parentOrg") int parentOrg,@RequestParam(value="vendor") String vendor){
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/vendor-hierarchy");
        mav.addObject("vendorList", securityService.getVendorList(corp,vendor,parentOrg));//0- Filter Flow
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping("modify-org")
    public ModelAndView getEditOrgInfo(@RequestParam(value="orgId") int orgId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-org");
        UserContext userContext = sessionBean.getUserContext();
        Org editableOrg = securityService.getEditOrgInfo(orgId);
        List<Integer> vendorList=securityService.getOrgVendor(editableOrg.getOrgId());
        if(vendorList !=null && !vendorList.isEmpty()){
            String vendorListStr = vendorList.toString().substring(1, vendorList.toString().length() - 1).replace(", ", ",");
            mav.addObject("selVendorList",vendorListStr);
        }
        mav.addObject("isCreatePage", false);

        List<Org> myOrgList=null;
        if(userContext.isVendorUser()) {
            myOrgList=securityService.getOrgList(null, userContext);
        }
        else if(userContext.isVisibleToPenske()) {
            myOrgList=securityService.getOrgList(null, userContext);
        }
        mav.addObject("orgList", securityService.removeCurrentOrgAndChild(orgId, myOrgList));
        mav.addObject("vendorList", securityService.getVendorList("","",editableOrg.getParentOrgId()));
        mav.addObject("editableOrg", editableOrg);
        return mav;
    }
}
