package com.penske.apps.adminconsole.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.service.SecurityService;
import com.penske.apps.adminconsole.service.VendorService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.DefaultController;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;

/**
 * Controller to render pages on the Admin Console Security sub-tab that use the
 * v2 page template.
 * 
 * @author Maxim Timofeev
 */
@DefaultController
@RequestMapping("/admin-console/security")
public class SecurityController {

	@Autowired
	private VendorService vendorService;
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	@Autowired
	private SecurityService securityService;
	
	/* ================== Vendors ================== */
	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("vendors")
	public ModelAndView getVendorsPage() {
		ModelAndView mav = new ModelAndView("/admin-console/security/vendors");
		User user = sessionBean.getUser();
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
		mav.addObject("analysts", vendorService.getAllPlanningAnalysts());
		mav.addObject("specialists", vendorService.getAllSupplySpecialists());
		mav.addObject("alertTypeList", vendorService.getAllAlerts());
		mav.addObject("isPenskeUser", user.getUserType() == UserType.PENSKE);
		mav.addObject("hasBeenSearched", true);
		return mav;
	}
	
	/* ================== Vendor Users ================== */
	@VendorAllowed
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDOR_USERS)
    @RequestMapping(value ={"/vendor-users"})
    public ModelAndView getVendorUsers(){
        User user = sessionBean.getUser();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS))
            return getVendorUsersPageData();
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ROLES))
            return new ModelAndView("forward:roles");
        if(user.hasSecurityFunction(SecurityFunction.MANAGE_ORG))
            return new ModelAndView("forward:org");
        ModelAndView mav = new ModelAndView("/admin-console/security/noAccess");
        return mav;
    }
	
    private ModelAndView getVendorUsersPageData(){
        ModelAndView mav = new ModelAndView("/admin-console/security/vendor-users");
        User user = sessionBean.getUser();
        // If the user is a supplier.
        mav.addObject("roleList", securityService.getVendorRoles(user.getRoleId(),user.getOrgId()));
        List<Org> orgList = securityService.getOrgList(null, user);
        Collections.sort(orgList, Org.ORG_NAME_ASC);
        mav.addObject("orgList", orgList);
        mav.addObject("hasBeenSearched", false);
        mav.addObject("userTypeList", securityService.getUserTypes());
        mav.addObject("accessVendor", user.hasSecurityFunction(SecurityFunction.MANAGE_VENDOR_USERS));
        mav.addObject("vendorUsersPage", true);
        return mav;
    }
}
