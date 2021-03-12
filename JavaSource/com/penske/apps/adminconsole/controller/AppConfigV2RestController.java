/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.CostAdjustmentOption;
import com.penske.apps.adminconsole.model.CostTolerance;
import com.penske.apps.adminconsole.model.Manufacturer;
import com.penske.apps.adminconsole.service.CostAdjustmentOptionService;
import com.penske.apps.adminconsole.service.CostToleranceService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.smccore.base.exception.AppValidationException;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;

/**
 * A controller to answer AJAX requests on the App Config subtab, for pages that use the v2 page template.
 */
@RestController
@RequestMapping("/admin-console/app-config")
public class AppConfigV2RestController
{
	@Autowired
	private CostAdjustmentOptionService costAdjustmentOptionService;
	@Autowired
	private CostToleranceService costToleranceService;

	/* ================== Cost Sheet Adjustment Options ================== */
	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_ADJUSTMENT_OPTIONS)
	@RequestMapping("get-cost-sheet-adjustment-option-modal")
	@ResponseBody
	public ModelAndView getCostAdjustmentOptionModal(@RequestParam(value = "caOptionId") int caOptionId) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/modal/cost-sheet-adjustment-option-modal");

		CostAdjustmentOption caOption = caOptionId > 0 ? costAdjustmentOptionService.getAdjustmentOption(caOptionId)
				: new CostAdjustmentOption();
		mav.addObject("adjustmentOption", caOption);

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_ADJUSTMENT_OPTIONS)
	@RequestMapping(value = "add-cost-sheet-adjustment-option", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addCostAdjustmentOption(CostAdjustmentOption caOption) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-adjustment-option-table");

		try {
			costAdjustmentOptionService.addAdjustmentOption(caOption);
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			throw new AppValidationException(
					"Order code '" + caOption.getOrderCode() + "' is already an adjustment option");
		}

		mav.addObject("adjustmentOptions", costAdjustmentOptionService.getAllAdjustmentOptions());

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_ADJUSTMENT_OPTIONS)
	@RequestMapping(value = "update-cost-sheet-adjustment-option", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateCostAdjustmentOption(CostAdjustmentOption caOption) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-adjustment-option-table");

		try {
			costAdjustmentOptionService.updateAdjustmentOption(caOption);
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			throw new AppValidationException(
					"Order code '" + caOption.getOrderCode() + "' is already an adjustment option");
		}

		mav.addObject("adjustmentOptions", costAdjustmentOptionService.getAllAdjustmentOptions());

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_ADJUSTMENT_OPTIONS)
	@RequestMapping(value = "delete-cost-sheet-adjustment-option", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteCostAdjustmentOption(@RequestParam(value = "caOptionId") int caOptionId) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-adjustment-option-table");

		costAdjustmentOptionService.deleteAdjustmentOption(caOptionId);

		mav.addObject("adjustmentOptions", costAdjustmentOptionService.getAllAdjustmentOptions());

		return mav;
	}

	/* ================== Cost Sheet Tolerances ================== */
	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_TOLERANCES)
	@RequestMapping("get-cost-sheet-tolerance-modal")
	@ResponseBody
	public ModelAndView getCostToleranceModal(@RequestParam(value = "costToleranceId") int costToleranceId) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/modal/cost-sheet-tolerance-modal");

		CostTolerance costTolerance = costToleranceId > 0 ? costToleranceService.getTolerance(costToleranceId)
				: new CostTolerance();
		mav.addObject("tolerance", costTolerance);

		List<PoCategoryType> poCategoryList = Arrays.asList(PoCategoryType.values());
		mav.addObject("poCategoryList", poCategoryList);

		List<Manufacturer> vehicleMakeList = costToleranceService.getVehicleMakeList();
		mav.addObject("vehicleMakeList", vehicleMakeList);
		mav.addObject("makesJson", CommonUtils.serializeJson(vehicleMakeList, true));

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_TOLERANCES)
	@RequestMapping(value = "add-cost-sheet-tolerance", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addCostTolerance(CostTolerance costTolerance) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-tolerance-table");

		try {
			costToleranceService.addTolerance(costTolerance);
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			throw new AppValidationException("Tolerance already exists for '"
					+ costTolerance.getPoCategory().getPoCategoryName() + "' / '" + costTolerance.getMfrCode() + "'");
		}

		mav.addObject("tolerances", costToleranceService.getAllTolerances());

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_TOLERANCES)
	@RequestMapping(value = "update-cost-sheet-tolerance", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateCostTolerance(CostTolerance costTolerance) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-tolerance-table");

		costToleranceService.updateTolerance(costTolerance);

		mav.addObject("tolerances", costToleranceService.getAllTolerances());

		return mav;
	}

	@SmcSecurity(securityFunction = SecurityFunction.COST_SHEET_TOLERANCES)
	@RequestMapping(value = "delete-cost-sheet-tolerance", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteCostTolerance(@RequestParam(value = "costToleranceId") int costToleranceId) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/fragment/cost-sheet-tolerance-table");

		costToleranceService.deleteTolerance(costToleranceId);

		mav.addObject("tolerances", costToleranceService.getAllTolerances());

		return mav;
	}
}
