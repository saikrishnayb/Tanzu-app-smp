/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.service.BuildMatrixSmcService;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * Controller to service AJAX requests for navigating between screens in the change order module
 */
@RestController
@RequestMapping(value = "/admin-console/oem-build-matrix")
public class BuildMatrixRestController {
	
	@Autowired
	private BuildMatrixSmcService buildMatrixSmcService;
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// AVAILABLE CHASSIS SUMMARY //
	/**
	 * Method to exclude chassis from builds
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/exclude-units")
	public void excludeUnits(@RequestParam("excludedUnits[]") List<String> excludedUnits) {
		buildMatrixSmcService.excludeUnits(excludedUnits);
	}
	
	/**
	 * Method to delete excludes from builds
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/delete-excluded-units")
	public void deleteExcludeUnits(@RequestParam("excludedUnits[]") List<String> excludedUnits) {
		buildMatrixSmcService.deleteExcludedUnits(excludedUnits);
	}
	
	// SUBMIT BUILD //
	/**
	 * Method to submit the build
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/submit-build")
	public void submitBuild(BuildMixForm buildMixForm) {
		UserContext userContext = sessionBean.getUserContext();
		buildMatrixSmcService.submitBuild(buildMixForm, userContext);
	}
	
	// ***** OEM MIX MAINTENACE *****//
	/**
	 * Method to save OEM Mix Maintenance page
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/save-business-award-maint")
	public void saveBusinessAwardMaint(BusinessAwardForm businessAwardForm) {
		buildMatrixSmcService.saveBusinessAwardMaintenance(businessAwardForm);
	}
}
