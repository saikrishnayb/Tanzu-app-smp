package com.penske.apps.suppliermgmt.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
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
 * A controller to answer AJAX requests from the two factor authentication screen
 * 
 */
@RestController
@RequestMapping("/two-factor-auth")
public class TwoFactorRestController {

	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private VendorService vendorService;

	private static final Logger LOGGER = LogManager.getLogger(TwoFactorRestController.class);

	@SmcSecurity(securityFunction = SecurityFunction.MANAGE_VENDORS)
	@RequestMapping("check-access-code")
	@ResponseBody
	public Pair<Boolean, Boolean> checkAccessCode(@RequestParam("userId") int userId, @RequestParam("accessCode") int accessCode) {
	}
	
}

