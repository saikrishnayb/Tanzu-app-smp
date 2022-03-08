package com.penske.apps.adminconsole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.DefaultController;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;

/**
 * Controller to render pages on the Admin Console Security sub-tab that use the
 * v2 page template.
 * 
 * @author Maxim Timofeev
 */
@DefaultController
@RequestMapping("/admin-console/security")
public class SecurityControllerV2 {

	@Autowired
	private VendorService vendorService;
	@Autowired
	private SuppliermgmtSessionBean sessionBean;

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
	public ModelAndView getVendorsAdvancedSearchAlert(
			@RequestParam(value = "alertType", required = true) String alertType) {
		Vendor vendor = new Vendor();
		EditableUser user = new EditableUser();
		vendor.setPlanningAnalyst(user);
		vendor.setSupplySpecialist(user);
		if (alertType != null) {
			alertType = alertType.trim();
		}
		vendor.setAlertType(alertType);
		return getVendorSearchDetails(vendor);
	}

	private ModelAndView getVendorSearchDetails(Vendor vendor) {
		User user = sessionBean.getUser();
		ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
		mav.addObject("searchedVendor", vendor);

		// For populating the rest of the page
		mav.addObject("vendors", vendorService.getVendorsBySearchConditions(user.getOrgId(), vendor));
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		mav.addObject("alertTypeList", vendorService.getAllAlerts());
		mav.addObject("isPenskeUser", user.getUserType() == UserType.PENSKE);
		mav.addObject("hasBeenSearched", true);
		return mav;
	}
}
