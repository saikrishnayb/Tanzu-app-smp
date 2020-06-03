package com.penske.apps.buildmatrix.controller;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.OrderSelectionForm;
import com.penske.apps.buildmatrix.model.ProximityViewModel;
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
	 * method to load Body Plant Exceptions screen
	 * 
	 * @param plantId
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/bodyplant-capabilities")
	public ModelAndView getBodyPlantCapabilities(@RequestParam("plantId") int plantId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/bodyplant-capabilities");
		List<BodyPlantCapability> bodyPlantCapabilityList = buildMatrixSmcService.getAllBuildMatrixExceptions(plantId);

		model.addObject("plantData", buildMatrixSmcService.getPlantData(plantId));
		model.addObject("bodyPlantCapability", bodyPlantCapabilityList);

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
	public ModelAndView getDistrictProximity(@RequestParam("plantId") int plantId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/district-proximity");
			List<FreightMileage> freightMileage = buildMatrixSmcService.getFreightMileageData(plantId);
			if(freightMileage.size() > 0){
				List<PlantProximity> proximities = buildMatrixSmcService.getPlantProximity(plantId);
				
				ProximityViewModel proximityModel =  new ProximityViewModel(freightMileage, proximities);
				
				model.addObject("isDataAvailable", true);
				model.addObject("proximityModel", proximityModel);
			}
			
			model.addObject("plantData", buildMatrixSmcService.getPlantData(plantId));
	      
		
		return model;
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
	
	// VIEW SLOT RESULTS //
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/view-slot-results")
	public ModelAndView viewSlotResults(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/production-slot-results");
		model.addObject("slotResults", buildMatrixSmcService.getProductionSlotResults(buildId));
		model.addObject("buildId", buildId);
		return model;
	}
	
	// ORDER SUMMARY //
	/**
	 * method to load order-summary screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/order-summary")
	public ModelAndView getOrderSummary(@RequestParam(value="buildId", required=false) Integer buildId) {
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
	
	/**
	 * method to load confirm-selection screen
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/back-to-confirm")
	public ModelAndView backToConfirmSelection(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/confirm-order-selection");
		BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
		if(existingBuild == null)
			throw new IllegalArgumentException("Couldn't find existing build");
		
		List<CroOrderKey> selectedOrderKeys = buildMatrixSmcService.getCroOrderKeysForBuild(existingBuild.getBuildId());
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(selectedOrderKeys);
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
		
		Map<String,Map<String,BusinessAward>> buildMixMap = buildMatrixSmcService.getExistingBuildMixData(existingBuild.getBuildId());
		
		int reeferUnits = selectedOrders.stream().filter(order->order.isHasReeferUnits()).collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		int reardoorUnits = selectedOrders.stream().filter(order->order.isHasReardoorUnits()).collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		int liftgateUnits = selectedOrders.stream().filter(order->order.isHasLiftgateUnits()).collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		
		model.addObject("bodiesOnOrder", bodiesOnOrder);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("summary", existingBuild);
		model.addObject("attributes", attributes);
		model.addObject("reeferUnits", reeferUnits);
		model.addObject("reardoorUnits", reardoorUnits);
		model.addObject("liftgateUnits", liftgateUnits);
		model.addObject("buildMixMap", buildMixMap);
		
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
   
	//***** Slot maintenance *****//
 	/**
 	 * method to production slot maintenance screen
 	 * 
 	 * @return
 	 */
 	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
 	@RequestMapping("/prod-slot-maintenance")
 	public ModelAndView getProdSlotMaintenance(@RequestParam("slotType") String slotTypeId,@RequestParam("year") String selectedYear) 
	{
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/prod-slot-maintenance");
		List<BuildMatrixSlotType> buildMatrixSlotTypes = buildMatrixSmcService.getAllVehicleTypes();
		List<Integer> yearsForDropdown = buildMatrixSmcService.getYearsforSLotMaintenance();
		if (StringUtils.equals(slotTypeId, ApplicationConstants.String_ZERO)) 
		{
			slotTypeId = String.valueOf(buildMatrixSlotTypes.get(0).getSlotTypeId());
			selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		}
		model.addObject("vehicleTypes", buildMatrixSlotTypes);
		model.addObject("years", yearsForDropdown);
		model.addObject("bodyplantList", buildMatrixSmcService.getAllBodyPlantsforSlotMaintenance());
		model.addObject("slotMaintenanceSummary", buildMatrixSmcService
				.getSlotMaintenanceSummary(Integer.valueOf(slotTypeId), Integer.valueOf(selectedYear)));
		model.addObject("slotTypeId", Integer.valueOf(slotTypeId));
		model.addObject("selectedYear", selectedYear);
		return model;
	}
  
}