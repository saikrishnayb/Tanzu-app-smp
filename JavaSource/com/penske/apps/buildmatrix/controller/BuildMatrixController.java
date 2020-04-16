package com.penske.apps.buildmatrix.controller;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.OrderSelectionForm;
import com.penske.apps.buildmatrix.service.BuildMatrixCorpService;
import com.penske.apps.buildmatrix.service.BuildMatrixCroService;
import com.penske.apps.buildmatrix.service.BuildMatrixSmcService;
import com.penske.apps.suppliermgmt.annotation.DefaultController;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;


/**
 * Non ajax controller for the change order pages
 * 
 * @author justin.frey
 */
@DefaultController
@RequestMapping(value = "/admin-console/oem-build-matrix")
public class BuildMatrixController {
	
	private static final Logger LOGGER = Logger.getLogger(BuildMatrixController.class);
	
	@Autowired
	private BuildMatrixCroService buildMatrixCroService;
	
	@Autowired
	private BuildMatrixCorpService buildMatrixCorpService;
	
	@Autowired
	private BuildMatrixSmcService buildMatrixSmcService;
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;

	/**
	 * Method to navigate to OEM-build matrix screen
	 * 
	 * @return
	 */
	@RequestMapping(value = "/navigate-oem-build-matrix", method = { RequestMethod.GET })
	public ModelAndView navigateOEMBuildMatrix() {

		Set<SecurityFunction> securityFunctions = sessionBean.getUserContext().getSecurityFunctions();

		List<LeftNav> leftNavs = SubTab.OEM_BUILD_MATRIX.getLeftNavs();

		for (LeftNav leftNav : leftNavs) {

			SecurityFunction securityFunction = leftNav.getSecurityFunction();

			boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
			if (noAccess)
				continue;

			return new ModelAndView("redirect:/app/" + leftNav.getUrlEntry());
		}

		return new ModelAndView("/admin-console/security/noAccess");
	}
	
	/**
	 * method to load edit program screen
	 * 
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/edit-program")
	public ModelAndView getEditProgram() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/edit-program");

		return model;
	}

	/**
	 * method to load bodyplant capabilities screen
	 * 
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/bodyplant-capabilities")
	public ModelAndView getAttributeMaintenance() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/bodyplant-capabilities");
		model.addObject("capabilityList", buildMatrixSmcService.getAllBuildMatrixCapabilities());
		return model;
	}

	/**
	 * Method to Loads Edit Dimension Popup
	 * 
	 * @param request
	 * 
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/load-edit-dimension-popup", method = { RequestMethod.POST })
	public ModelAndView loadEditDimensionPopup(@RequestParam("capabilityId") int capabilityId, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/edit-dimension");
		model.addObject("bodyPlantCapability",buildMatrixSmcService.getCapabilityDetails(capabilityId));
		model.addObject("editPopup", true);
		return model;
	}
	

	/**
	 * method to load maintenance summary screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/maintenance-summary")
	public ModelAndView getMaintenanceSummary() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/maintenance-summary");
		model.addObject("maintenanceSummary", buildMatrixSmcService.getAllBodyPlants());
		return model;
	}
	
	/**
	 * method to load district proximity screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/district-proximity")
	public ModelAndView getDistrictProximity(@RequestParam("plantId") int plantId, @RequestParam("plantName") String plantName, 
											 @RequestParam("plantCity") String plantCity, @RequestParam("plantState") String plantState) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/district-proximity");
		model.addObject("districtProximityList", buildMatrixSmcService.getDistrictProximity(plantId));
		model.addObject("plantName", plantName);
		model.addObject("plantCity", plantCity);
		model.addObject("plantState", plantState);
		return model;
	}
	
	/**
	 *Method to load offline dates setup model 
	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value = "/get-offline-date-setup-modal", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getModalData(@RequestParam("plantId") int plantId)
	 	{
	        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/oem-build-matrix/set-offline-dates-modal");
	        mav.addObject("plantData", buildMatrixSmcService.getPlantData(plantId));
	        return mav;
	    }

	/**
	 * Method to save offline dates for a plant
	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="save-offline-dates")
    @ResponseBody
    public ModelAndView saveOfflineDates(@RequestBody BuildMatrixBodyPlant plantData,HttpServletResponse response) throws Exception {
        try{
        	buildMatrixSmcService.saveOfflineDates(plantData);
        }catch (Exception e) {
            LOGGER.error("Error while setting Offline Dates: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the Setting Offline dates");
        }
        return null;
    }
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTORY //
	/**
	 * method to load build history screen
	 * 
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/build-history")
	public ModelAndView getBuildHistory() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/build-history");
		List<BuildSummary> buildHistoryList = buildMatrixSmcService.getAllBuildHistory();
		boolean showStartBuildBtn = !buildHistoryList.stream().anyMatch(bs->!bs.showStartBuildBtn());
		model.addObject("buildHistoryList", buildHistoryList);
		model.addObject("showStartBuildBtn", showStartBuildBtn);
		model.addObject("buildStatuses", BuildStatus.values());
		return model;
	}
	
	// ORDER SUMMARY //
	/**
	 * method to load order-summary screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/order-summary")
	public ModelAndView getorderSummary(@RequestParam(value="buildId", required=false) Integer buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/order-summary");
		List<ApprovedOrder> approvedOrders = buildMatrixCroService.getApprovedOrdersForBuildMatrix();
		Map<CroOrderKey, ApprovedOrder> approvedOrdersByKey = approvedOrders.stream().collect(toMap(order->new CroOrderKey(order), order-> order));
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		
		List<CroOrderKey> selectedOrderKeys = new ArrayList<>();
		if(buildId != null) {
			BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
			if(existingBuild == null)
				throw new IllegalArgumentException("Couldn't find existing build");
			selectedOrderKeys = buildMatrixSmcService.getCroOrderKeysForBuild(existingBuild.getBuildId());
		}
		
		model.addObject("approvedOrdersByKey", approvedOrdersByKey);
		model.addObject("selectedOrderKeys", selectedOrderKeys);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("buildId", buildId);
		return model;
	}
	
	// CONFIRM SELECTION //
	/**
	 * method to load confirm-selection screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/add-to-build")
	public ModelAndView getConfirmSelection(OrderSelectionForm orderSelectionForm) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/confirm-order-selection");
		Integer formBuildId = orderSelectionForm.getBuildId();
		List<CroOrderKey> selectedOrderKeys = orderSelectionForm.getCroOrderKeys();
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(selectedOrderKeys);
		UserContext userContext = sessionBean.getUserContext();
		
		int buildId = 0;
		if(formBuildId == null) {
			BuildSummary newBuild = buildMatrixSmcService.startNewBuild(selectedOrders, userContext);
			buildId = newBuild.getBuildId();
		}
		else {
			BuildSummary existingBuild = buildMatrixSmcService.updateExistingBuild(formBuildId, selectedOrders);
			buildId = existingBuild.getBuildId();
		}
		
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		
		model.addObject("selectedOrders", selectedOrders);
		model.addObject("bodiesOnOrder", bodiesOnOrder);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("buildId", buildId);
		
		return model;
	}
	
	// AVAILABLE CHASSIS SUMMARY //
	/**
	 * method to load confirm-selection screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/available-chassis-summary")
	public ModelAndView getAvailableChassisSummary(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/available-chassis-summary");
		BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
		if(existingBuild == null)
			throw new IllegalArgumentException("Couldn't find existing build");
		
		List<CroOrderKey> selectedOrderKeys = buildMatrixSmcService.getCroOrderKeysForBuild(existingBuild.getBuildId());
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(selectedOrderKeys);
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		
		List<String> excludedUnits = buildMatrixSmcService.getExcludedUnits();
		AvailableChassisSummaryModel summaryModel = buildMatrixCorpService.getAvailableChassis(excludedUnits);
		int chassisAvailable = summaryModel.getGroupedAvailableUnits().stream().mapToInt(ac->ac.size()).sum() + summaryModel.getGroupedExcludedUnits().stream().mapToInt(ac->ac.size()).sum();
		
		model.addObject("bodiesOnOrder", bodiesOnOrder);
		model.addObject("summaryModel", summaryModel);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("buildId", buildId);
		
		return model;
	}
	
	// BUILD OEM MIX //
	/**
	 * method to load build mix screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/build-mix")
	public ModelAndView getConfirmSelection(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/build-mix");
		BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
		if(existingBuild == null)
			throw new IllegalArgumentException("Couldn't find existing build");
		
		List<CroOrderKey> selectedOrderKeys = buildMatrixSmcService.getCroOrderKeysForBuild(existingBuild.getBuildId());
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(selectedOrderKeys);
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		List<BuildAttribute> attributes = buildMatrixSmcService.getAttributesForBuild();
		
		model.addObject("bodiesOnOrder", bodiesOnOrder);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("buildId", buildId);
		model.addObject("attributes", attributes);
		
		return model;
	}
	
	//***** BUILD ATTRIBUTE *****//
	/**
	 * method to load attribute maintenance screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/attribute-maintenance")
	public ModelAndView getattributeMaintenace() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/attribute-setup");
		List<BuildAttribute> attributeList = buildMatrixSmcService.getAllBuildMatrixAttributes();
		model.addObject("attributeList", attributeList);
		return model;
	}
	
	//***** BUSINESS AWARD MAINTENANCE *****//
	/**
	 * method to business award maintenance screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/business-award-maint")
	public ModelAndView getBusinessAwardMaintenance() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/business-award-maintenance");
		model.addObject("attributes", buildMatrixSmcService.getAttributesForBuild());
		return model;
	}
	
}