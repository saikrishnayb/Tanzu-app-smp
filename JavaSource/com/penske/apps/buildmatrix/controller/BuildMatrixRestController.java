/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;
import com.penske.apps.buildmatrix.service.BuildMatrixCorpService;
import com.penske.apps.buildmatrix.service.BuildMatrixSmcService;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;

/**
 * Controller to service AJAX requests for navigating between screens in the change order module
 */
@RestController
@RequestMapping(value = "/admin-console/oem-build-matrix")
public class BuildMatrixRestController
{
	@Autowired
	private BuildMatrixCorpService buildMatrixCorpService;
	
	@Autowired
	private BuildMatrixSmcService buildMatrixSmcService;
	
	/**
	 * method to load add oem popup
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/exclude-units")
	public void excludeUnits(@RequestParam("excludedUnits[]") List<String> excludedUnits) {
		buildMatrixSmcService.excludeUnits(excludedUnits);
	}
	
	/**
	 * method to load add oem popup
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/delete-excluded-units")
	public void deleteExcludeUnits(@RequestParam("excludedUnits[]") List<String> excludedUnits) {
		buildMatrixSmcService.deleteExcludedUnits(excludedUnits);
	}
}
