/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.service.CostAdjustmentOptionService;
import com.penske.apps.adminconsole.service.CostToleranceService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.DefaultController;

/**
 * Controller to render pages on the App Config sub-tab that use the v2 page template.
 */
@DefaultController
@RequestMapping("/admin-console/app-config")
public class AppConfigController
{
	@Autowired
	private CostAdjustmentOptionService costAdjustmentOptionService;
	@Autowired
	private CostToleranceService costToleranceService;

	/* ================== Cost Sheet Adjustment Options ================== */
	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_ADJUSTMENT_OPTIONS)
	@RequestMapping("/cost-sheet-adjustment-options")
	public ModelAndView getCostSheetAdjustmentOptionsPage() {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/cost-sheet-adjustment-options");

		mav.addObject("adjustmentOptions", costAdjustmentOptionService.getAllAdjustmentOptions());

		return mav;
	}

	/* ================== Cost Sheet Tolerances ================== */
	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_TOLERANCES)
	@RequestMapping("/cost-sheet-tolerances")
	public ModelAndView getCostSheetTolerancesPage() {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/cost-sheet-tolerances");

		mav.addObject("tolerances", costToleranceService.getAllTolerances());

		return mav;
	}
}
