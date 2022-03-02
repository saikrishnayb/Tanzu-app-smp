package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.ImageFile;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.RoleService;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.UserCreationService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.LookupKey;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.exception.SMCException;

@Version1Controller
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
    @Autowired
    private LookupManager lookupManager;

    private static final Logger LOGGER = LogManager.getLogger(SecurityRestController.class);

    /* ================== Users ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="get-edit-user-modal-content")
    @ResponseBody
    public ModelAndView getEditInfo(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/edit-user-modal-content");
        User user = sessionBean.getUser();
        EditableUser editableUser = securityService.getEditInfo(userId, userType);

        mav.addObject("isCreatePage", false);

        if (userType.equalsIgnoreCase("vendor")) {

        }
        else {
            List<Role> penskeRoles = securityService.getPenskeRoles(user.getRoleId());
            Collections.sort(penskeRoles, Role.ROLE_NAME_ASC);
            mav.addObject("userRoles", penskeRoles);
        }

        if(user.getUserType() == UserType.VENDOR) {

        }
        else {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
        }
        
    	List<Org> orgList=securityService.getPenskeUserOrgList();
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("penskeUserType", UserType.PENSKE);
        mav.addObject("vendorUserType", UserType.VENDOR);
        mav.addObject("currentUser", user);
        mav.addObject("orgList", orgList);
        mav.addObject("editableUser", editableUser);
        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

        return mav;
    }
    
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="get-create-edit-vendor-user")
    @ResponseBody
    public ModelAndView getCreateEditVendor(@RequestParam(value="isCreate") boolean isCreate, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="userType", required=false) String userType, @RequestParam(value="roleId", required=false) String roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/create-edit-vendor-user");
        
        User user = sessionBean.getUser();
        mav.addObject("currentUser", user);
        boolean isVendor = user.getUserType() == UserType.VENDOR;
        if(!isCreate) {
        	EditableUser editableUser = securityService.getEditInfo(userId, userType);
        	mav.addObject("editableUser", editableUser);
        	mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
        }

        List<Role> vendorRoles = securityService.getVendorRoles(isVendor, user.getRoleId(), user.getOrgId());
        Collections.sort(vendorRoles, Role.ROLE_NAME_ASC);

        mav.addObject("userRoles", vendorRoles);
        mav.addObject("userTypes", securityService.getVendorUserTypes());

        List<Org> vendorOrg = securityService.getVendorOrg(isVendor, user.getOrgId());
        Collections.sort(vendorOrg, Org.ORG_NAME_ASC);
        mav.addObject("penskeUserType", UserType.PENSKE);
        mav.addObject("vendorUserType", UserType.VENDOR);
        mav.addObject("orgList", vendorOrg);
        mav.addObject("isCreatePage", isCreate);
        return mav;
    }
    
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="get-resend-email-modal-content")
    public ModelAndView getResendEmail(@RequestParam(value="userId") String userId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/resend-email-modal-content");
        
        EditableUser editableUser = securityService.getEditInfo(userId, "VENDOR");
        mav.addObject("editableUser", editableUser);

        return mav;
    }
    
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="resend-email")
    public void resendEmail(@RequestParam(value="userId") String userId) {
    	User user = sessionBean.getUser();
        EditableUser editableUser = securityService.getEditInfo(userId, "VENDOR");
        userCreationService.resendVendorEmail(user, editableUser);
    }
    
    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="get-vendor-user-table-contents")
    @ResponseBody
    public List<EditableUser> getSearchedVendorUserTableContents(UserSearchForm userSearchForm) {
    	User user = sessionBean.getUser();
    	List<EditableUser> vendorUsers = securityService.getSearchUserList(userSearchForm, user);
    	return vendorUsers;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS, SecurityFunction.MANAGE_ORG})
    @RequestMapping(value ="get-permissions-accordion-content")
    @ResponseBody
    public ModelAndView getPermissionsInfo(@RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/permissions-accordion");

        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

        return mav;
    }
    
    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS, SecurityFunction.MANAGE_ORG})
    @RequestMapping(value ="get-permissions-accordions")
    @ResponseBody
    public ModelAndView getPermissionsAccordions(@RequestParam(value="roleId") String roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/permissions-accordion-v2");

        mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = {SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS})
    @RequestMapping("deactivate-user")
    @ResponseBody
    public void modifyUserStatus(@RequestParam(value="userId") int userId,@RequestParam(value="isVendorUser") boolean isVendorUserFlow, HttpServletResponse response) throws Exception {
        try{
        	User user = sessionBean.getUser();
            if(isVendorUserFlow){//going to vendor user deactivation flow
                userCreationService.isEligibleToDeactivate(userId, isVendorUserFlow,user.getSso());
            }else{
                securityService.modifyUserStatus(userId, user);
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
            @RequestParam(value="isVendorUser") boolean isVendorUser, @RequestParam(value="isV2") boolean isV2) {
    	ModelAndView mav;
    	if(isV2)
    		mav = new ModelAndView("/admin-console/security/modal/deactivate-user-v2");
    	else
    		mav = new ModelAndView("/admin-console/security/modal/deactivate-user-modal-content");

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
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/deactivate-org-modal-content");
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
        	User user = sessionBean.getUser();
        	String userSSO = user.getSso();
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
    	User user = sessionBean.getUser();

        //Not A Supplier
        if(user.getUserType() != UserType.VENDOR) {
            // Penske User
            if(userTypeId == 1) {
                return securityService.getPenskeRoles(user.getRoleId());
            }
        }

        return securityService.getSupplierRoles(manufacturer, user);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="edit-user-static")
    @ResponseBody
    public EditableUser modifyUserInfoStatic(EditableUser user, @RequestParam("vendorIds") int[] vendorIds, @RequestParam(value="signatureImage", required=false) MultipartFile signatureImage,
            @RequestParam(value="initialsImage", required=false) MultipartFile initialsImage){

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

        User currentUser = sessionBean.getUser();

        securityService.modifyUserInfo(user, vendorIds, currentUser);
        EditableUser userInfo = securityService.getUser(user.getUserId());

        return userInfo;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ="edit-vendor-user-static")
    @ResponseBody
    public EditableUser modifyVendorUserInfoStatic(EditableUser user, HttpServletResponse response) throws Exception{

        try{
            User currentUser = sessionBean.getUser();
            user.setModifiedBy(currentUser.getSso());
            userCreationService.updateUserInfo(user, false);
            EditableUser userInfo = securityService.getUser(user.getUserId());
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
    @RequestMapping(value ="is-username-valid",  method = RequestMethod.POST)
    @ResponseBody
    public EditableUser isUsernameValid(@RequestParam("ssoId")String ssoId, @RequestParam("userId") int userId,
            @RequestParam("isCreateOrEdit") String isCreateOrEdit,HttpServletResponse response) throws Exception {
        try{
        	LookupContainer lookups = lookupManager.getLookupContainer();
            EditableUser user=securityService.doesUserExistVendor(ssoId, userId, true, isCreateOrEdit);
            if(user.getReturnFlg() == 2){
                user.setSupportNumber(lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM));
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
        User user = sessionBean.getUser();
        mav.addObject("currentUser", user);
        if(user.getUserType() == UserType.PENSKE) {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
        }
        List<Org> orgList = securityService.getPenskeUserOrgList();
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("penskeUserType", UserType.PENSKE);
        mav.addObject("vendorUserType", UserType.VENDOR);
        mav.addObject("orgList", orgList);
        // If the page is an error page.
        mav.addObject("isCreatePage", true);

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_USERS)
    @RequestMapping(value ="/create-user", method = RequestMethod.POST)
    @ResponseBody
    public void addUser(EditableUser user, @RequestParam("vendorIds")int[] vendorIds, @RequestParam(value="signatureImage", required=false)MultipartFile signatureImage,
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
            User currentUser = sessionBean.getUser();
            securityService.addUser(user, vendorIds, currentUser);
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
    public void addVendorUser(EditableUser user, HttpServletResponse response) throws Exception{
        try{

            User currentUser = sessionBean.getUser();
            user.setCreatedBy(currentUser.getSso());
            userCreationService.insertUserInfo(currentUser, user);
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
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/role-hierarchy");
        User user = sessionBean.getUser();
        if (roleId != 0) {
            mav.addObject("role", roleService.getCreateRoleHierarchy(roleId,user.getOrgId()));
        }

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-edit-role-hierarchy")
    @ResponseBody
    public ModelAndView getEditRoleHierarchy(@RequestParam("roleId") int roleId, @RequestParam("flag") int flag) {
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/role-hierarchy");
        User user = sessionBean.getUser();
        mav.addObject("role", roleService.getEditRoleHierarchy(roleId, flag, user.getOrgId()));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-edit-role-permissions")
    @ResponseBody
    public ModelAndView getEditRolePermissions(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/role-permissions");
        mav.addObject("tabs", roleService.getEditRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("get-role-permissions")
    @ResponseBody
    public ModelAndView getRolePermissions(@RequestParam("roleId") int roleId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/role-permissions");

        mav.addObject("tabs", roleService.getCreateRolePermissions(roleId));

        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ROLES)
    @RequestMapping("insert-role")
    @ResponseBody
    public void insertRole(Role role, @RequestParam("functionIds") int[] functionIds, HttpServletResponse response) throws Exception {
    	User user = sessionBean.getUser();
        role.setCreatedBy(user.getSso());
        role.setOem(String.valueOf(user.getOrgId()));
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
    	User user = sessionBean.getUser();
        role.setModifiedBy(user.getSso());
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
        User user = sessionBean.getUser();
        // If any users are found with the role.
        if (roleService.checkForUsers(roleId)) {
            mav.setViewName("/admin-console/security/modal/deactivate-role-error-modal");
        }
        else {
            mav.setViewName("/admin-console/security/modal/deactivate-role-modal");
            List<Role> subRoles=roleService.getMyDescendRoleByRoleIdOrgId(roleId, user.getOrgId());
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
        User user = sessionBean.getUser();

        roleService.modifyRoleStatus(roleId,user.getSso());
    }

    /* ================== Vendors ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("edit-vendor")
    @ResponseBody
    public ModelAndView getEditVendorInformation(@RequestParam("vendorId") int vendorId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/edit-vendor-modal");

        mav.addObject("vendor", vendorService.getVendorById(vendorId));
        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("view-vendor")
    @ResponseBody
    public ModelAndView getViewVendorInformation(@RequestParam("vendorId") int vendorId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/view-vendor-modal");

        mav.addObject("vendor", vendorService.getVendorById(vendorId));

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("get-analysts-and-specialists")
    @ResponseBody
    public ModelAndView getAllAnalystsAndSpecialists() {
        ModelAndView mav = new ModelAndView("/admin-console/security/modal/mass-update-vendor-modal");

        mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
        mav.addObject("specialists", vendorService.getAllSupplySpecialists());

        return mav;
    }

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("modify-vendor-info")
	@ResponseBody
	public Vendor modifyVendorInfo(Vendor vendor) {
		User user = sessionBean.getUser();
		return vendorService.modifyVendorSingleUpdate(vendor, user);
	}

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping("mass-update-vendors")
    @ResponseBody
    public void modifyVendorsByVendorId(Vendor vendor, @RequestParam("vendorIds") int... vendorIdsToApplyChange) {
    	User user = sessionBean.getUser();
    	vendorService.modifyVendorsMassUpdate(vendor, user, vendorIdsToApplyChange);
    }


    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
    @RequestMapping(value="sso-user-lookup" , method=RequestMethod.GET)
    @ResponseBody
    public EditableUser modifyVendorsByVendorId(@RequestParam("ssoId") String ssoId,HttpServletResponse response) {
        EditableUser user = null;
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
    public ModelAndView ssoLookupRefresh(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="isV2") boolean isV2, HttpServletResponse response) {
        ModelAndView mav; 
        if(isV2)
        	mav = new ModelAndView("/admin-console/security/modal/sso-refresh-modal-content-v2");
        else
        	mav = new ModelAndView("/admin-console/security/modal/sso-refresh-modal-content");

        if("1".equalsIgnoreCase(userType)){
            userType = "Penske";

        }else{
            userType = "Vendor";
        }
        EditableUser editableUser = securityService.getEditInfo(userId, userType);

        EditableUser user = null;
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
    public EditableUser refreshUserWithSSOData( @RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType,HttpServletResponse response){

        if("1".equalsIgnoreCase(userType)){
            userType = "Penske";

        }else{
            userType = "Vendor";
        }
        EditableUser editableUser = securityService.getEditInfo(userId, userType);

        EditableUser user = null;
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
            User user = sessionBean.getUser();
            org.setModifiedBy(user.getSso());
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
        User user = sessionBean.getUser();
        if(user.getUserType() == UserType.VENDOR) {
            mav.addObject("orgList", securityService.getOrgList(null, user));
        }
        else if(user.getUserType() == UserType.PENSKE) {
            mav.addObject("userTypes", securityService.getUserTypes());
            mav.addObject("vendorNames", securityService.getVendorNames());
            mav.addObject("userDepts", securityService.getUserDepts());
            mav.addObject("orgList", securityService.getOrgList(null, user));
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
            User user = sessionBean.getUser();
            org.setCreatedBy(user.getSso());
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
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/vendor-hierarchy");
        mav.addObject("vendorList", securityService.getVendorList("","",parentOrg));
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping(value ="filter-vendor-list")
    @ResponseBody
    public ModelAndView filterVendorList( @RequestParam(value="corp") String corp, @RequestParam(value="parentOrg") int parentOrg,@RequestParam(value="vendor") String vendor){
        ModelAndView mav = new ModelAndView("/admin-console/security/includes/vendor-hierarchy");
        mav.addObject("vendorList", securityService.getVendorList(corp,vendor,parentOrg));//0- Filter Flow
        return mav;
    }

    @VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_ORG)
    @RequestMapping("modify-org")
    public ModelAndView getEditOrgInfo(@RequestParam(value="orgId") int orgId) {
        ModelAndView mav = new ModelAndView("/admin-console/security/create-org");
        User user = sessionBean.getUser();
        Org editableOrg = securityService.getEditOrgInfo(orgId);
        List<Integer> vendorList=securityService.getOrgVendor(editableOrg.getOrgId());
        if(vendorList !=null && !vendorList.isEmpty()){
            String vendorListStr = vendorList.toString().substring(1, vendorList.toString().length() - 1).replace(", ", ",");
            mav.addObject("selVendorList",vendorListStr);
        }
        mav.addObject("isCreatePage", false);

        List<Org> myOrgList=null;
        if(user.getUserType() == UserType.VENDOR) {
            myOrgList=securityService.getOrgList(null, user);
        }
        else if(user.getUserType() == UserType.PENSKE) {
            myOrgList=securityService.getOrgList(null, user);
        }
        mav.addObject("orgList", securityService.removeCurrentOrgAndChild(orgId, myOrgList));
        mav.addObject("vendorList", securityService.getVendorList("","",editableOrg.getParentOrgId()));
        mav.addObject("editableOrg", editableOrg);
        return mav;
    }
}
