package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserForm;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorPoInformation;
import com.penske.apps.adminconsole.model.VendorUser;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.UserCreationService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.UserSecurity;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.smccore.base.service.UserService;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.exception.SMCException;

/**
 * A controller to answer AJAX requests on the Admin Console Security sub-tab,
 * for pages that use the v2 page template.
 * 
 * @author Maxim Timofeev
 */
@RestController
@RequestMapping("/admin-console/security")
public class SecurityRestController {

	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCreationService userCreationService;
	@Autowired
	private LookupManager lookupManager;
	@Autowired
	@CommonStaticUrl
	private URL commonStaticUrl;

	private static final Logger LOGGER = LogManager.getLogger(SecurityRestController.class);

	/* ================== Vendors ================== */
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("edit-vendor")
	
	public ModelAndView getEditVendorInformation(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/edit-vendor-modal");
		mav.addObject("vendor", vendorService.getVendorById(vendorId));
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("view-vendor")
	
	public ModelAndView getViewVendorInformation(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/view-vendor-modal");
		mav.addObject("vendor", vendorService.getVendorById(vendorId));
		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("purchasing-details")
	
	public ModelAndView getPurchasingDetails(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/purchasing-details-modal");
		Vendor vendor = vendorService.getVendorById(vendorId);
		List<VendorPoInformation> info = vendorService.getVendorPoInformation(Arrays.asList(vendor.getVendorId()));
		mav.addObject("vendor", vendor);
		mav.addObject("purchasingSummary",
				info == null || info.isEmpty() ? new VendorPoInformation(vendor.getVendorId()) : info.get(0));
		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("get-analysts-and-specialists")
	
	public ModelAndView getAllAnalystsAndSpecialists() {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/mass-update-vendor-modal");
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("modify-vendor-info")
	
	public Vendor modifyVendorInfo(Vendor vendor) {
		User user = sessionBean.getUser();
		return vendorService.modifyVendorSingleUpdate(vendor, user);
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("mass-update-vendors")
	
	public void modifyVendorsByVendorId(Vendor vendor, @RequestParam("vendorIds") int... vendorIdsToApplyChange) {
		User user = sessionBean.getUser();
		vendorService.modifyVendorsMassUpdate(vendor, user, vendorIdsToApplyChange);
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping(value = "sso-user-lookup", method = RequestMethod.GET)
	
	public EditableUser modifyVendorsByVendorId(@RequestParam("ssoId") String ssoId, HttpServletResponse response) {
		EditableUser user = null;
		try {
			user = securityService.doesUserExistInPenske(ssoId);
		} catch (SMCException sme) {
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sme.getErrorDetails().getMessage());
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write(sme.getErrorDetails().getMessage());
				response.flushBuffer();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		} catch (Exception e) {
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

		return user;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("get-vendor-table-contents")
	
	public List<Vendor> getVendorTableContents() {
		User user = sessionBean.getUser();
		List<Vendor> vendors = vendorService.getAllVendors(user.getOrgId());
		return vendors;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("get-vendor-table-contents-advanced-search")
	
	public List<Vendor> getVendorTableContentsAdvancedSearch(Vendor vendor) {
		User user = sessionBean.getUser();
		List<Vendor> vendors = vendorService.getVendorsBySearchConditions(user.getOrgId(), vendor);
		return vendors;
	}

	/* ================== Vendor Users ================== */
	@VendorAllowed
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "get-create-edit-vendor-user")
	
	public ModelAndView getCreateEditVendor(@RequestParam(value = "isCreate") boolean isCreate,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "userType", required = false) String userType,
			@RequestParam(value = "roleId", required = false) String roleId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/create-edit-vendor-user");

		User user = sessionBean.getUser();
		mav.addObject("currentUser", user);
		if (!isCreate) {
			EditableUser editableUser = securityService.getEditInfo(userId, userType);
			mav.addObject("editableUser", editableUser);
			mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));
		}

		List<Role> vendorRoles = securityService.getVendorRoles(user.getRoleId(), user.getOrgId());
		Collections.sort(vendorRoles, Role.ROLE_NAME_ASC);

		mav.addObject("userRoles", vendorRoles);
		mav.addObject("userTypes", securityService.getVendorUserTypes());

		List<Org> vendorOrg = securityService.getVendorOrg(user.isVendorUser(), user.getOrgId());
		Collections.sort(vendorOrg, Org.ORG_NAME_ASC);
		mav.addObject("penskeUserType", UserType.PENSKE);
		mav.addObject("vendorUserType", UserType.VENDOR);
		mav.addObject("orgList", vendorOrg);
		mav.addObject("isCreatePage", isCreate);
		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "get-resend-email-modal-content")
	public ModelAndView getResendEmail(@RequestParam(value = "userId") String userId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/resend-email-modal-content");

		EditableUser editableUser = securityService.getEditInfo(userId, "VENDOR");
		mav.addObject("editableUser", editableUser);

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "resend-email")
	
	public void resendEmail(@RequestParam(value = "userId") int userId) {
		LookupContainer lookups = lookupManager.getLookupContainer();
		User user = sessionBean.getUser();

		User vendorUser = userService.getUser(userId, false, false);
		UserSecurity vendorUserSecurity = userService.getUserSecurity(vendorUser);

		userService.sendNewUserEmail(user, vendorUser, vendorUserSecurity, true, lookups, commonStaticUrl);
	}

	@VendorAllowed
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "get-vendor-user-table-contents")
	
	public List<VendorUser> getSearchedVendorUserTableContents(UserSearchForm userSearchForm) {
		User user = sessionBean.getUser();
		userSearchForm.setUserTypeId(ApplicationConstants.SUPPLIER_USER);

		List<VendorUser> vendorUsers = securityService.getVendorUsers(userSearchForm, user);

		return vendorUsers;
	}

	@VendorAllowed
	@SmcSecurity(securityFunction = { SecurityFunction.MANAGE_USERS, SecurityFunction.MANAGE_VENDOR_USERS,
			SecurityFunction.MANAGE_ORG })
	@RequestMapping(value = "get-permissions-accordions")
	
	public ModelAndView getPermissionsAccordions(@RequestParam(value = "roleId") String roleId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/includes/permissions-accordion-v2");

		mav.addObject("tabPermissionsMap", securityService.getPermissions(roleId));

		return mav;
	}

	@VendorAllowed
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "/create-vendor-user", method = RequestMethod.POST)
	
	public void addVendorUser(UserForm userForm, HttpServletResponse response) throws Exception {
		LookupContainer lookups = lookupManager.getLookupContainer();

		User currentUser = sessionBean.getUser();
		userCreationService.insertUserInfo(currentUser, userForm, lookups, commonStaticUrl);

	}

	@VendorAllowed
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
	@RequestMapping(value = "edit-vendor-user-static")
	
	public EditableUser modifyVendorUserInfoStatic(UserForm userForm, HttpServletResponse response) throws Exception {
		EditableUser editableUser = new EditableUser();
		editableUser.setEmail(userForm.getEmail());
		editableUser.setSsoId(userForm.getSsoId());
		editableUser.setFirstName(userForm.getFirstName());
		editableUser.setLastName(userForm.getLastName());
		editableUser.setPhone(userForm.getPhone());
		editableUser.setExtension(userForm.getExtension());
		AdminConsoleUserType userType = new AdminConsoleUserType();
		userType.setUserTypeId(userForm.getUserTypeId());
		editableUser.setUserType(userForm.getUserType());
		editableUser.setUserId(userForm.getUserId());
		editableUser.setOrgId(userForm.getOrgId());
		editableUser.setRole(userForm.getRole());
		editableUser.setUserDept(userForm.getUserDept());
		editableUser.setGessouid(userForm.getGessouid());
		editableUser.setDailyOptIn(userForm.isDailyOptIn());
		editableUser.setModifiedBy(userForm.getModifiedBy());
		User currentUser = sessionBean.getUser();
		editableUser.setModifiedBy(currentUser.getSso());
		userCreationService.updateUserInfo(editableUser, false);
		EditableUser userInfo = securityService.getUser(editableUser.getUserId());
		return userInfo;
	}

}
