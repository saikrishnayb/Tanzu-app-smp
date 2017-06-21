package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.ImageFile;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorLocation;
import com.penske.apps.adminconsole.service.RoleService;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.UserCreationService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.suppliermgmt.common.exception.SMCException;

@Controller
@RequestMapping("/admin-console/security")
public class SecurityRestController {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserCreationService userCreationService;
	
	private static final Logger logger = Logger.getLogger(SecurityRestController.class);
	
	/* ================== Users ================== */
	@RequestMapping(value ="get-edit-user-modal-content")
	@ResponseBody
	public ModelAndView getEditInfo(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="roleId") String roleId, HttpSession session) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-user-modal-content");
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		User editableUser = securityService.getEditInfo(userId, userType);

		mav.addObject("isCreatePage", false);
		
		if (userType.equalsIgnoreCase("vendor")) {
	
		}
		else {
			mav.addObject("userRoles", securityService.getPenskeRoles(currentUser.getRoleId()));
		}
		
		if(currentUser.getUserTypeId() == 2) {
		
		} 
		else {
			mav.addObject("userTypes", securityService.getUserTypes());
			mav.addObject("vendorNames", securityService.getVendorNames());
			mav.addObject("userDepts", securityService.getUserDepts());
		}
		mav.addObject("orgList", securityService.getPenskeUserOrgList(currentUser));
		mav.addObject("editableUser", editableUser);		
		mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
		
		return mav;
	}
	
	
	@RequestMapping(value ="get-edit-vendor-user-modal-content")
	@ResponseBody
	public ModelAndView getEditVendorInfo(@RequestParam(value="userId") String userId, @RequestParam(value="userType") String userType, @RequestParam(value="roleId") String roleId, HttpSession session) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-vendor-user-modal-content");
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		boolean isVendor = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
		User editableUser = securityService.getEditInfo(userId, userType);
		mav.addObject("isCreatePage", false);
		mav.addObject("userTypes", securityService.getVendorUserTypes());
		mav.addObject("userRoles", securityService.getVendorRoles(isVendor, currentUser.getRoleId(),currentUser.getOrgId()));
		mav.addObject("orgList", securityService.getVendorOrg(isVendor, currentUser.getOrgId()));
		mav.addObject("editableUser", editableUser);		
		mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
		return mav;
	}
	
	@RequestMapping(value ="get-permissions-accordion-content")
	@ResponseBody
	public ModelAndView getPermissionsInfo(@RequestParam(value="roleId") String roleId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/permissions-accordion");
		
		mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
		
		return mav;
	}
	
	@RequestMapping("get-vendor-locations-content")
	@ResponseBody
	public List<VendorLocation> getVendorLocations(@RequestParam(value="vendorName") String vendorName, HttpSession session) {
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		
		return securityService.getVendorLocations(vendorName, currentUser.getUserId(), currentUser.getUserTypeId());
	}
	
	@RequestMapping("deactivate-user")
	@ResponseBody
	public void modifyUserStatus(@RequestParam(value="userId") int userId,@RequestParam(value="isVendorUser") boolean isVendorUserFlow,
			HttpSession session,HttpServletResponse response) throws Exception {
		try{
			HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
			//boolean isVendor = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
			if(isVendorUserFlow){//going to vendor user deactivation flow
				userCreationService.isEligibleToDeactivate(userId, isVendorUserFlow,currentUser.getSso());
			}else{
				securityService.modifyUserStatus(userId, currentUser);
			}
		}catch (Exception e) {
			logger.error("Error while deactivation user: "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while deactivation user.");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Error while deactivation user.");
		    response.flushBuffer();
		}
	}
	
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
	
	
	
	@RequestMapping("delete-org")
	@ResponseBody
	public void modifyOrgStatus(@RequestParam(value="orgId") int orgId, HttpSession session,HttpServletResponse response) throws Exception {
		try{
			HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
			securityService.deleteOrg(orgId, currentUser.getSso());
		}catch (Exception e) {
			logger.error("Error while deactivation ORG: "+e);
			CommonUtils.getCommonErrorAjaxResponse(response,"");
		}
	}
	
	@RequestMapping("vendor-templates")
	@ResponseBody
	public ModelAndView getVendorTemplateInfo(@RequestParam(value="vendorIds[]") String[] vendorIds) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/templates-accordion");
		
		if(vendorIds[0].equalsIgnoreCase("empty")) {
			return null;
		}
		
		List<VendorLocation> vendorIdList = new ArrayList<VendorLocation>(vendorIds.length);
		
		for (String vendorId : vendorIds) {
			VendorLocation vendLoc = new VendorLocation();
			vendLoc.setVendorId(Integer.parseInt(vendorId));
			
			vendorIdList.add(vendLoc);
		}
			
		List<VendorLocation> vendorTemplateList = securityService.getVendorTemplates(vendorIdList);
		
		
		mav.addObject("vendorTemplates", vendorTemplateList);
		
		return mav;
	}
	
	@RequestMapping(value ="get-role-list")
	@ResponseBody
	public List<Role> getRoles(@RequestParam("userTypeId") int userTypeId, @RequestParam(value="manufacturer", required=false) String manufacturer, HttpSession session) {
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		
		//Not A Supplier
		if(currentUser.getUserTypeId() != 2) {
			// Penske User
			if(userTypeId == 1) {
				return securityService.getPenskeRoles(currentUser.getRoleId());
			}
		}
		
		return securityService.getSupplierRoles(manufacturer, currentUser);
	}
	
	@RequestMapping(value ="edit-user-static")
	@ResponseBody
	public User modifyUserInfoStatic(User user, @RequestParam("vendorIds") int[] vendorIds, HttpSession session, @RequestParam(value="signatureImage", required=false)MultipartFile signatureImage,
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
		
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		
		securityService.modifyUserInfo(user, vendorIds, currentUser); 
		User userInfo = securityService.getUser(user.getUserId());
		
		return userInfo;
	}
	
	
	@RequestMapping(value ="edit-vendor-user-static")
	@ResponseBody
	public User modifyVendorUserInfoStatic(User user, HttpSession session,HttpServletResponse response) throws Exception{
		
		try{
			HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
			user.setModifiedBy(currentUser.getSso());
			userCreationService.updateUserInfo(user, false);
			User userInfo = securityService.getUser(user.getUserId());
			return userInfo;
		}
		catch (UserServiceException e) {
			if(IUserConstants.DUP_SSO_ERROR_CODE==e.getErrorCode()){
				logger.error(IUserConstants.DUP_SSO_ERROR_MESSAGE+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_DUP_SSO:"+String.valueOf(e.getErrorCode()));
			}else if(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE==e.getErrorCode()){
				logger.error(IUserConstants.NOT_STANDARD_SSO_ERROR_MESSAGE+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_NOT_STANDARD_SSO:"+String.valueOf(e.getErrorCode()));
			}else{
				logger.error("Error while creating user: "+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
			}
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Error while creating user. "+e.getErrorCode());
		    response.flushBuffer();
		}
		catch (Exception e) {
			logger.error("Error while updating user: "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while updating user.");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Error while updating user.");
		    response.flushBuffer();
		}
		return null;
	}
	
	@RequestMapping(value ="edit-user-submit")
	@ResponseBody
	public ModelAndView modifyUserInfoSubmit(User user, int[] vendorIds, HttpSession session, @RequestParam(value="signatureFile", required=false)MultipartFile signatureImage,
			@RequestParam(value="initialsFile", required=false)MultipartFile initialsImage){

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
		
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		
		securityService.modifyUserInfo(user, vendorIds, currentUser); 
		ModelAndView mav = new ModelAndView("redirect:/admin-console/security/users");
		return mav;
	}
	
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
			logger.info("No issue>>>>>>>>>>>>>>>");
		}
	}
	
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
			logger.error("Error in processing the last request.: "+e);
		   CommonUtils.getCommonErrorAjaxResponse(response,"");
		}
		return null;
	}
	
	@RequestMapping(value ="/create-user-page")
	@ResponseBody
	public ModelAndView getCreateUSerPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/security/create-user");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("currentUser", currentUser);
		if(currentUser.getUserTypeId() == 2) {
		
		}
		else if(currentUser.getUserTypeId() == 1) {
			mav.addObject("userTypes", securityService.getUserTypes());
			mav.addObject("vendorNames", securityService.getVendorNames());
			mav.addObject("userDepts", securityService.getUserDepts());
		}
		mav.addObject("orgList", securityService.getPenskeUserOrgList(currentUser));
		// If the page is an error page.
		mav.addObject("isCreatePage", true);
		
		return mav;
	}
	
	@RequestMapping(value ="/create-vendor-user-page")
	@ResponseBody
	public ModelAndView getCreateVendorUserPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/security/create-vendor-user");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("currentUser", currentUser);
		boolean isVendor = currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER;
		mav.addObject("userTypes", securityService.getVendorUserTypes());
		mav.addObject("userRoles", securityService.getVendorRoles(isVendor, currentUser.getRoleId(),currentUser.getOrgId()));
		mav.addObject("orgList", securityService.getVendorOrg(isVendor, currentUser.getOrgId()));
		// If the page is an error page.
		mav.addObject("isCreatePage", true);
		
		return mav;
	}
	
	@RequestMapping(value ="/create-user", method = RequestMethod.POST)
	@ResponseBody
	public void addUser(User user, @RequestParam("vendorIds")int[] vendorIds, HttpSession session, @RequestParam(value="signatureImage", required=false)MultipartFile signatureImage,
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
			HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
			securityService.addUser(user, vendorIds, currentUser);
		}catch (Exception e) {
				logger.error("Error while creating user: "+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			    response.getWriter().write("Error while creating user.");
			    response.flushBuffer();
		}
	}
	
	
	@RequestMapping(value ="/create-vendor-user", method = RequestMethod.POST)
	@ResponseBody
	public void addVendorUser(User user, HttpSession session,HttpServletResponse response) throws Exception{
		try{
						
			HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
			user.setCreatedBy(currentUser.getSso());
			userCreationService.insertUserInfo(user);
		}
		catch (UserServiceException e) {
			if(IUserConstants.DUP_SSO_ERROR_CODE==e.getErrorCode()){
				logger.error(IUserConstants.DUP_SSO_ERROR_MESSAGE+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_DUP_SSO:"+String.valueOf(e.getErrorCode()));
			}else if(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE==e.getErrorCode()){
				logger.error(IUserConstants.NOT_STANDARD_SSO_ERROR_MESSAGE+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"USER_SERVICE_NOT_STANDARD_SSO:"+String.valueOf(e.getErrorCode()));
			}else{
				logger.error("Error while creating user: "+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
			}
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    response.getWriter().write("Error while creating user. "+e.getErrorCode());
		    response.flushBuffer();
		}
		catch (Exception e) {
				logger.error("Error while creating user: "+e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating user.");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			    response.getWriter().write("Error while creating user.");
			    response.flushBuffer();
		}
	}
	
	/* ================== Roles ================== */
	@RequestMapping("get-create-role-hierarchy")
	@ResponseBody
	public ModelAndView getCreateRoleHierarchy(@RequestParam("roleId") int roleId, HttpSession session) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-hierarchy");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		if (roleId != 0) {
			mav.addObject("role", roleService.getCreateRoleHierarchy(roleId,currentUser.getOrgId()));
		}
		
		return mav;
	}
	
	@RequestMapping("get-edit-role-hierarchy")
	@ResponseBody
	public ModelAndView getEditRoleHierarchy(@RequestParam("roleId") int roleId, @RequestParam("flag") int flag, HttpSession session) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-hierarchy");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("role", roleService.getEditRoleHierarchy(roleId,flag,currentUser.getOrgId()));
		
		return mav;
	}
	
	
	@RequestMapping("get-edit-role-permissions")
	@ResponseBody
	public ModelAndView getEditRolePermissions(@RequestParam("roleId") int roleId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-permissions");
		mav.addObject("tabs", roleService.getEditRolePermissions(roleId));
		
		return mav;
	}
	
	@RequestMapping("get-role-permissions")
	@ResponseBody
	public ModelAndView getRolePermissions(@RequestParam("roleId") int roleId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/role-permissions");
		
		mav.addObject("tabs", roleService.getCreateRolePermissions(roleId));
		
		return mav;
	}
	
	@RequestMapping("insert-role")
	@ResponseBody
	public void insertRole(Role role, @RequestParam("functionIds") int[] functionIds, HttpSession session,HttpServletResponse response) throws Exception {
		HeaderUser user = (HeaderUser)session.getAttribute("currentUser");
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
			roleService.addRole(role, functionIds, user.getManufacturer());
		}
	}
	
	@RequestMapping("modify-role-submit")
	@ResponseBody
	public void modifyRole(Role role, @RequestParam("functionIds") int[] functionIds, HttpSession session,HttpServletResponse response) throws Exception {
		HeaderUser user = (HeaderUser)session.getAttribute("currentUser");
		role.setModifiedBy(user.getSso());
		role.setOem(user.getManufacturer());
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
	
	@RequestMapping("deactivate-role")
	@ResponseBody
	public ModelAndView deactivateRole(@RequestParam("roleId") int roleId, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		// If any users are found with the role.
		if (roleService.checkForUsers(roleId)) {
			mav.setViewName("/jsp-fragment/admin-console/security/deactivate-role-error-modal");
		}
		else {
			mav.setViewName("/jsp-fragment/admin-console/security/deactivate-role-modal");
			List<Role> subRoles=roleService.getMyDescendRoleByRoleIdOrgId(roleId,currentUser.getOrgId());
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
	
	@RequestMapping("deactivate-role-confirm")
	@ResponseBody
	public void confirmRoleDeactivation(@RequestParam("roleId") int roleId, HttpSession session) {
		HeaderUser user = (HeaderUser)session.getAttribute("currentUser");
	
		roleService.modifyRoleStatus(roleId,user.getSso());
	}
	
	/* ================== Vendors ================== */
	@RequestMapping("edit-vendor")
	@ResponseBody
	public ModelAndView getEditVendorInformation(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/edit-vendor-modal");

		mav.addObject("vendor", vendorService.getEditVendorInformation(vendorId));
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		
		return mav;
	}
	
	@RequestMapping("view-vendor")
	@ResponseBody
	public ModelAndView getViewVendorInformation(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/view-vendor-modal");
		
		mav.addObject("vendor", vendorService.getViewVendorInformation(vendorId));
		
		return mav;
	}
	
	@RequestMapping("get-analysts-and-specialists")
	@ResponseBody
	public ModelAndView getAllAnalystsAndSpecialists() {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/mass-update-vendor-modal");
		
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		
		return mav;
	}
	
	@RequestMapping("modify-vendor-info")
	@ResponseBody
	public Vendor modifyVendorInfo(Vendor vendor, HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
		HeaderUser user = (HeaderUser)session.getAttribute("currentUser");
		vendorService.modifyVendorInformation(vendor,user);
		List<Vendor> updatedVendors = vendorService.getAllVendors( user.getOrgId() );
		for(Vendor updatedVendor: updatedVendors){
			if(updatedVendor.getVendorId() == vendor.getVendorId())
				return updatedVendor;
		}
		return null;
	}
	
	@RequestMapping("mass-update-vendors")
	@ResponseBody
	public void modifyVendorsByVendorId(Vendor vendor, @RequestParam("vendorIds") int[] vendorIds) {
		vendorService.modifyVendorsMassUpdate(vendorIds, vendor);
	}
	
	
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
				logger.error(e.getMessage());
			}
		}catch (Exception e) {
			logger.debug(e);
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading user data");
				  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			        response.getWriter().write("Error while loading user data");
			        response.flushBuffer();
			} catch (IOException ie) {
				logger.error(ie.getMessage());
			}
		}
		return	user;
	 
	}
	
	
	@RequestMapping("get-signature-preview")
	@ResponseBody
	public String getSignatureImagePreview(@RequestParam(value="userId") int userId, HttpServletResponse response) {
		String signFile = securityService.getSignatureImage(userId);
		
       
		return signFile;
	}
	
	@RequestMapping("get-intials-preview")
	@ResponseBody
	public String getInitialsImagePreview(@RequestParam(value="userId") int userId, HttpServletResponse response) {
		String initFile = securityService.getInitialsImage(userId);
		
       
		return initFile;
	}
	
	@RequestMapping("delete-intials")
	@ResponseBody
	public String deleteInitialsImage(@RequestParam(value="userId") int userId,@RequestParam(value="ssoId") String ssoId, HttpServletResponse response) {
		boolean deleted = securityService.deleteInitialsImage(userId,ssoId);
		
		
       
		return "Succes:"+deleted;
	}
	
	@RequestMapping("delete-signature")
	@ResponseBody
	public String deleteSignatureImage(@RequestParam(value="userId") int userId,@RequestParam(value="ssoId") String ssoId, HttpServletResponse response) {
		boolean  deleted = securityService.deleteSignatureImage(userId,ssoId);
		
       
		return "Succes:"+deleted;
	}
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
				logger.error(e.getMessage());
			}
		}catch (Exception e) {
			logger.debug(e);
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while loading user data");
				  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			        response.getWriter().write("Error while loading user data");
			        response.flushBuffer();
			} catch (IOException ie) {
				logger.error(ie.getMessage());
			}
		}
	    user.setSsoUserUpdated( editableUser.validateUserWithSSOData(user));
		mav.addObject("editableUser", editableUser);
		mav.addObject("ssoDataUpdated", user);
		
		return mav;
	}
	
	
	
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
				logger.error(e.getMessage());
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
	
	
	@RequestMapping(value ="/update-org", method = RequestMethod.POST)
	@ResponseBody
	public void updateOrg(Org org,HttpSession session,HttpServletResponse response) throws Exception{
		try{
			HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
			org.setModifiedBy(currentUser.getSso());
			securityService.updateOrg(org);
		}catch (Exception e) {
			logger.error("Error Processing the Org: "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Processing the Org");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error Processing the Org");
	        response.flushBuffer();
		}
	}
	
	
	@RequestMapping(value ="/create-org-page")
	@ResponseBody
	public ModelAndView getCreateOrgPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/security/create-org");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("currentUser", currentUser);
		if(currentUser.getUserTypeId() == 2) {
			mav.addObject("orgList", securityService.getOrgList(currentUser));
		}
		else if(currentUser.getUserTypeId() == 1) {
			mav.addObject("userTypes", securityService.getUserTypes());
			mav.addObject("vendorNames", securityService.getVendorNames());
			mav.addObject("userDepts", securityService.getUserDepts());
			mav.addObject("orgList", securityService.getOrgList(currentUser));
		}
		// If the page is an error page.
		mav.addObject("isCreatePage", true);
		
		return mav;
	}
	
	@RequestMapping(value ="/create-org", method = RequestMethod.POST)
	@ResponseBody
	public void addOrg(Org org,HttpSession session,HttpServletResponse response) throws Exception{
		try{
			HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
			org.setCreatedBy(currentUser.getSso());
			securityService.addOrg(org);
		}catch (Exception e) {
			logger.error("Error Processing the Org: "+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Processing the Org");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error Processing the Org");
	        response.flushBuffer();
		}
	}
	
	@RequestMapping(value ="/get-vendor-hierarchy-page")
	@ResponseBody
	public ModelAndView getVendorHierarchyPage(@RequestParam(value="parentOrg") int parentOrg,HttpSession session) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/vendor-hierarchy");
		HeaderUser currentUser = (HeaderUser)session.getAttribute("currentUser");
		mav.addObject("currentUser", currentUser);
		mav.addObject("vendorList", securityService.getVendorList("","",parentOrg));
		return mav;
	}
	
	@RequestMapping(value ="filter-vendor-list")
	@ResponseBody
	public ModelAndView filterVendorList( @RequestParam(value="corp") String corp, @RequestParam(value="parentOrg") int parentOrg,@RequestParam(value="vendor") String vendor){
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/vendor-hierarchy");
		mav.addObject("vendorList", securityService.getVendorList(corp,vendor,parentOrg));//0- Filter Flow
		return mav;
	}
	
	@RequestMapping("modify-org")
	public ModelAndView getEditOrgInfo(@RequestParam(value="orgId") int orgId, HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin-console/security/create-org");
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		Org editableOrg = securityService.getEditOrgInfo(orgId);
		List<Integer> vendorList=securityService.getOrgVendor(editableOrg.getOrgId());
		if(vendorList !=null && !vendorList.isEmpty()){
			String vendorListStr = vendorList.toString().substring(1, vendorList.toString().length() - 1).replace(", ", ",");
			mav.addObject("selVendorList",vendorListStr);
		}
		mav.addObject("isCreatePage", false);
		
		List<Org> myOrgList=null;
		if(currentUser.getUserTypeId() == 2) {
			myOrgList=securityService.getOrgList(currentUser);
		}
		else if(currentUser.getUserTypeId() == 1) {
			myOrgList=securityService.getOrgList(currentUser);
		} 
		mav.addObject("orgList", securityService.removeCurrentOrgAndChild(orgId, myOrgList));
		mav.addObject("vendorList", securityService.getVendorList("","",editableOrg.getParentOrgId()));
		mav.addObject("editableOrg", editableOrg);		
		return mav;
	}
}
