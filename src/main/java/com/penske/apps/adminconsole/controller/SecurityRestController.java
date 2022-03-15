package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorPoInformation;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
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

	private static final Logger LOGGER = LogManager.getLogger(SecurityRestController.class);

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
	@RequestMapping("purchasing-details")
	@ResponseBody
	public ModelAndView getPurchasingDetails(@RequestParam("vendorId") int vendorId) {
		ModelAndView mav = new ModelAndView("/admin-console/security/modal/purchasing-details-modal");
		Vendor vendor = vendorService.getVendorById(vendorId);
		List<VendorPoInformation> info = vendorService.getVendorPoInformation(Arrays.asList(vendor.getVendorId()));
		mav.addObject("vendor", vendor);
		mav.addObject("purchasingSummary", info == null || info.isEmpty() ? new VendorPoInformation(vendor.getVendorId()) : info.get(0));
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
	@RequestMapping(value = "sso-user-lookup", method = RequestMethod.GET)
	@ResponseBody
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
	@ResponseBody
	public List<Vendor> getVendorTableContents() {
		User user = sessionBean.getUser();
		List<Vendor> vendors = vendorService.getAllVendors(user.getOrgId());
		return vendors;
	}

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("get-vendor-table-contents-advanced-search")
	@ResponseBody
	public List<Vendor> getVendorTableContentsAdvancedSearch(Vendor vendor) {
		User user = sessionBean.getUser();
		List<Vendor> vendors = vendorService.getVendorsBySearchConditions(user.getOrgId(), vendor);
		return vendors;
	}
}
