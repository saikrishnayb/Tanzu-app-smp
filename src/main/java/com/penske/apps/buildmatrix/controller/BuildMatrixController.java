package com.penske.apps.buildmatrix.controller;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.CROBuildRequest;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.InvalidSlotsSummary;
import com.penske.apps.buildmatrix.model.OrderSelectionForm;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary;
import com.penske.apps.buildmatrix.model.ProximityViewModel;
import com.penske.apps.buildmatrix.service.BuildMatrixCorpService;
import com.penske.apps.buildmatrix.service.BuildMatrixCroService;
import com.penske.apps.buildmatrix.service.BuildMatrixSmcService;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.DefaultController;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;


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

		User user = sessionBean.getUser();

		List<LeftNav> leftNavs = SubTab.OEM_BUILD_MATRIX.getLeftNavs();

		for (LeftNav leftNav : leftNavs) {

			SecurityFunction securityFunction = leftNav.getSecurityFunction();

			boolean noAccess = securityFunction != null && !user.hasSecurityFunction(securityFunction);
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
		Set<Integer> invalidSlotIds = buildMatrixSmcService.getInvalidSlotIds();
		if(!invalidSlotIds.isEmpty()) {
			Map<Integer, Pair<BuildMatrixSlot, BuildMatrixSlotDate>> slotAndSlotDateBySlotId = buildMatrixSmcService.getSlotsAndSlotDatesBySlotIds(invalidSlotIds);
			
			List<Integer> plantIds = slotAndSlotDateBySlotId.values().stream()
					.map(p -> p.getLeft().getPlantId())
					.collect(toList());
			List<BuildMatrixBodyPlant> bodyPlants = buildMatrixSmcService.getBodyPlantsByPlantIds(plantIds);
			Map<Integer, BuildMatrixBodyPlant> bodyPlantsById = bodyPlants.stream()
					.collect(toMap(BuildMatrixBodyPlant::getPlantId, bp -> bp));
			
			model.addObject("slotAndSlotDateBySlotId", slotAndSlotDateBySlotId);
			model.addObject("bodyPlantsById", bodyPlantsById);
			
		}
		
		model.addObject("invalidSlotIds", invalidSlotIds);
		model.addObject("buildHistoryList", buildHistoryList);
		model.addObject("showStartBuildBtn", showStartBuildBtn);
		model.addObject("buildStatuses", BuildStatus.values());
		return model;
	}
	
	// VIEW SLOT RESULTS //
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/view-slot-results-filter")
	public ModelAndView viewSlotResultsByFilters(@RequestParam("buildId") int buildId,
												 @RequestParam("selectedFiltersList") String selectedFiltersList,
												 @RequestParam("checkedFilter") String checkedFilter) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/production-slot-results");
		List<String> selectedFilters = Arrays.asList(selectedFiltersList.split(","));
		List<ProductionSlotResult> slotResults=buildMatrixSmcService.getSlotResultsByFilter(buildId, null);
		
		List<ProductionSlotResult> filteredSlotResults = slotResults.stream().filter(e ->selectedFilters.contains(e.getReservationStatus())).collect(Collectors.toList());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(filteredSlotResults);
			 model.addObject("resultData",jsonString);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		
		
		model.addObject("slotResults",filteredSlotResults);
		boolean showAcceptBtn = !slotResults.stream().anyMatch(order->!order.showAcceptBtn());
		if (StringUtils.equals(checkedFilter, ApplicationConstants.String_ZERO)) {
			model.addObject("checkedFilter", true);
		}
		else {
			model.addObject("checkedFilter", false);
		}
		if (StringUtils.equals(selectedFiltersList, ApplicationConstants.RESERVATION_STATUS_ASSIGNED)) {
			model.addObject("approvedBuild", true);
		}
		else {
			model.addObject("approvedBuild", false);
		}
		model.addObject("showAcceptBtn", showAcceptBtn);
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
	public ModelAndView getOrderSummary(@RequestParam(value="buildId", required=false) Integer buildId, @RequestParam(value="guidance", required=false) boolean guidance) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/order-summary");
		List<ApprovedOrder> approvedOrders = buildMatrixCroService.getApprovedOrdersForBuildMatrix();
		List<ApprovedOrder> unFulfilledOrders = buildMatrixSmcService.getUnfulfilledOrders(approvedOrders);
		Map<CroOrderKey, ApprovedOrder> approvedOrdersByKey = unFulfilledOrders.stream().collect(toMap(order->new CroOrderKey(order), order-> order));
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		
		List<CROBuildRequest> buildRequests = new ArrayList<>();
		if(buildId != null) {
			BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
			if(existingBuild == null)
				throw new IllegalArgumentException("Couldn't find existing build");
			buildRequests = buildMatrixSmcService.getCroOrdersForBuild(buildId);
			guidance = "Y".equals(existingBuild.getGuidanceMode());
		}
		
		Map<CroOrderKey, Integer> unitsToConsiderByOrderKey = buildRequests.stream()
				.collect(toMap(br -> new CroOrderKey(br), br -> br.getRequestedQty()));
		
		model.addObject("approvedOrdersByKey", approvedOrdersByKey);
		model.addObject("unitsToConsiderByOrderKey", unitsToConsiderByOrderKey);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("guidance", guidance);
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
		List<ApprovedOrder> ordersToAdd = buildMatrixSmcService.getUnfulfilledOrders(selectedOrders);
		User user = sessionBean.getUser();
		
		int buildId = 0;
		if(formBuildId == null) {
			BuildSummary newBuild = buildMatrixSmcService.startNewBuild(ordersToAdd, orderSelectionForm.getUnitsToConsiderByCroOrderKey(), orderSelectionForm.isGuidance(), user);
			buildId = newBuild.getBuildId();
		}
		else {
			BuildSummary existingBuild = buildMatrixSmcService.updateExistingBuild(formBuildId, orderSelectionForm.getUnitsToConsiderByCroOrderKey(), ordersToAdd);
			buildId = existingBuild.getBuildId();
		}
		
		List<CROBuildRequest> croBuildRequests = buildMatrixSmcService.getCroOrdersForBuild(buildId);
		Map<CroOrderKey, CROBuildRequest> croBuildRequestsByOrderKey = croBuildRequests.stream()
				.collect(toMap(br -> new CroOrderKey(br), br -> br));
		Map<CroOrderKey, Pair<ApprovedOrder, CROBuildRequest>> orderMap = buildMatrixSmcService.getCroOrderMap(croBuildRequestsByOrderKey, ordersToAdd);
		int bodiesOnOrder = orderSelectionForm.getUnitsToConsiderByCroOrderKey().values().stream()
				.collect(Collectors.summingInt(utc->utc));
		
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		
		model.addObject("orderMap", orderMap);
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
		
		List<CROBuildRequest> croBuildRequests = buildMatrixSmcService.getCroOrdersForBuild(buildId);
		Map<CroOrderKey, CROBuildRequest> croBuildRequestsByOrderKey = croBuildRequests.stream()
				.collect(toMap(br -> new CroOrderKey(br), br -> br));
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(new ArrayList<>(croBuildRequestsByOrderKey.keySet()));
		List<ApprovedOrder> ordersToAdd = buildMatrixSmcService.getUnfulfilledOrders(selectedOrders);
		Map<CroOrderKey, Pair<ApprovedOrder, CROBuildRequest>> orderMap = buildMatrixSmcService.getCroOrderMap(croBuildRequestsByOrderKey, ordersToAdd);
		int bodiesOnOrder = orderMap.values().stream()
				.collect(Collectors.summingInt(pa->pa.getRight().getRequestedQty()));
		
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		
		model.addObject("orderMap", orderMap);
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
		
		List<CROBuildRequest> croBuildRequests = buildMatrixSmcService.getCroOrdersForBuild(buildId);
		int bodiesOnOrder = croBuildRequests.stream().collect(Collectors.summingInt(br->br.getRequestedQty()));
		
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
		
		buildMatrixSmcService.deleteBodySplitsForBuild(buildId);
		
		List<CROBuildRequest> croBuildRequests = buildMatrixSmcService.getCroOrdersForBuild(buildId);
		Map<CroOrderKey, CROBuildRequest> croBuildRequestsByOrderKey = croBuildRequests.stream()
				.collect(toMap(br -> new CroOrderKey(br), br -> br));
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(new ArrayList<>(croBuildRequestsByOrderKey.keySet()));
		List<ApprovedOrder> ordersToAdd = buildMatrixSmcService.getUnfulfilledOrders(selectedOrders);
		
		int bodiesOnOrder = croBuildRequests.stream().collect(Collectors.summingInt(br->br.getRequestedQty()));
				
		int totalChassis = buildMatrixCorpService.getAvailableChasisCount();
		int excludedChassis = buildMatrixSmcService.getExcludedUnitCount();
		int chassisAvailable = totalChassis - excludedChassis;
		List<BuildAttribute> attributes = buildMatrixSmcService.getAttributesForBuild();
		
		Map<String,Map<String,BusinessAward>> buildMixMap = buildMatrixSmcService.getExistingBuildMixData(existingBuild.getBuildId());
		
		int reeferUnits = ordersToAdd.stream()
				.filter(order->order.isHasReeferUnits())
				.collect(Collectors.summingInt(order -> croBuildRequestsByOrderKey.get(new CroOrderKey(order)).getRequestedQty()));
		int reardoorUnits = ordersToAdd.stream()
				.filter(order->order.isHasReardoorUnits())
				.collect(Collectors.summingInt(order -> croBuildRequestsByOrderKey.get(new CroOrderKey(order)).getRequestedQty()));
		int liftgateUnits = ordersToAdd.stream()
				.filter(order->order.isHasLiftgateUnits())
				.collect(Collectors.summingInt(order -> croBuildRequestsByOrderKey.get(new CroOrderKey(order)).getRequestedQty()));
		
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
		
		ProductionSlotsMaintenanceSummary summary = buildMatrixSmcService.getSlotMaintenanceSummary(Integer.valueOf(slotTypeId), Integer.valueOf(selectedYear));
		List<BuildMatrixBodyPlant> bodyplantList = buildMatrixSmcService.getAllBodyPlantsforSlotMaintenance();
		
		model.addObject("vehicleTypes", buildMatrixSlotTypes);
		model.addObject("years", yearsForDropdown);
		model.addObject("bodyplantList", bodyplantList);
		model.addObject("summary", summary);
		model.addObject("slotTypeId", Integer.valueOf(slotTypeId));
		model.addObject("selectedYear", Integer.valueOf(selectedYear));
		return model;
	}
 	
 	//***** Slot utilization *****//
 	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
 	@RequestMapping("/prod-slot-utilization")
 	public ModelAndView getProdSlotUtilization(@RequestParam("slotType") String slotTypeId,@RequestParam("year") String selectedYear, @RequestParam("region") String selectedRegion) 
	{
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/prod-slot-utilization");
		List<BuildMatrixSlotType> buildMatrixSlotTypes = buildMatrixSmcService.getAllVehicleTypes();
		List<Integer> yearsForDropdown = buildMatrixSmcService.getYearsforSLotMaintenance();
		if (StringUtils.equals(slotTypeId, ApplicationConstants.String_ZERO)) 
		{
			slotTypeId = String.valueOf(buildMatrixSlotTypes.get(0).getSlotTypeId());
			selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			selectedRegion = "";
		}
		
		Map<String, RegionPlantAssociation> regionMap = buildMatrixSmcService.getRegionAssociationDataMap();
		if(selectedRegion=="")
			selectedRegion = regionMap.keySet().iterator().next();
		
		ProductionSlotsUtilizationSummary summary = buildMatrixSmcService.getUtilizationSummary(Integer.valueOf(slotTypeId), Integer.valueOf(selectedYear), selectedRegion);
		List<BuildMatrixBodyPlant> bodyplantList = new ArrayList<>();
		Set<Integer> plantIds = summary.getBodyPlantById().keySet();
		if(!plantIds.isEmpty())
			bodyplantList = buildMatrixSmcService.getBodyPlantsByPlantIds(plantIds);
		
		model.addObject("summary", summary);
		model.addObject("bodyPlantList", bodyplantList);
		model.addObject("vehicleTypes", buildMatrixSlotTypes);
		model.addObject("years", yearsForDropdown);
		model.addObject("slotTypeId", Integer.valueOf(slotTypeId));
		model.addObject("selectedYear", selectedYear);
		model.addObject("selectedRegion", selectedRegion);
		model.addObject("regionMap", regionMap);
		return model;
	}
 	
 	//
 	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
 	@RequestMapping("/prod-slot-region-maintenance")
 	public ModelAndView getProdSlotRegionMaintenance(@RequestParam("slotType") String slotTypeId,@RequestParam("year") String selectedYear, @RequestParam("region") String selectedRegion) 
	{
 		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/prod-slot-region-maintenance");
		List<BuildMatrixSlotType> buildMatrixSlotTypes = buildMatrixSmcService.getAllVehicleTypes();
		List<Integer> yearsForDropdown = buildMatrixSmcService.getYearsforSLotMaintenance();
		if (StringUtils.equals(slotTypeId, ApplicationConstants.String_ZERO)) 
		{
			slotTypeId = String.valueOf(buildMatrixSlotTypes.get(0).getSlotTypeId());
			selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			selectedRegion = "";
		}
		
		Map<String, RegionPlantAssociation> regionMap = buildMatrixSmcService.getRegionAssociationDataMap();
		if(selectedRegion=="")
			selectedRegion = regionMap.keySet().iterator().next();
		
		ProductionSlotsUtilizationSummary summary = buildMatrixSmcService.getUtilizationSummary(Integer.valueOf(slotTypeId), Integer.valueOf(selectedYear), selectedRegion);
		
		List<BuildMatrixBodyPlant> bodyplantList = new ArrayList<>();
		Set<Integer> plantIds = summary.getBodyPlantById().keySet();
		if(!plantIds.isEmpty())
			bodyplantList = buildMatrixSmcService.getBodyPlantsByPlantIds(plantIds);
		
		model.addObject("summary", summary);
		model.addObject("bodyPlantList", bodyplantList);
		model.addObject("vehicleTypes", buildMatrixSlotTypes);
		model.addObject("years", yearsForDropdown);
		model.addObject("slotTypeId", Integer.valueOf(slotTypeId));
		model.addObject("selectedYear", selectedYear);
		model.addObject("selectedRegion", selectedRegion);
		model.addObject("regionMap", regionMap);
		return model;
	}
 	
 	//
 	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
 	@RequestMapping("/invalid-slots")
 	public ModelAndView getInvalidSlots(@RequestParam("plantId") String plantId, @RequestParam("slotTypeId") String slotTypeId) 
	{
 		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/invalid-slot-maintenance");
		if (StringUtils.equals(plantId, ApplicationConstants.String_ZERO)) 
		{
			Set<Integer> invalidSlotIds = buildMatrixSmcService.getInvalidSlotIds();
			if(!invalidSlotIds.isEmpty()) {
				BuildMatrixSlot slot = buildMatrixSmcService.getSlotById(invalidSlotIds.iterator().next());
				BuildMatrixBodyPlant bodyPlant = buildMatrixSmcService.getBodyPlantById(slot.getPlantId());
				plantId = Integer.toString(bodyPlant.getPlantId());
				Set<Integer> slotTypeIds = buildMatrixSmcService.getInvalidSlotTypesforPlant(bodyPlant.getPlantId());
				slotTypeId = Integer.toString(slotTypeIds.iterator().next());
			}
		}
		boolean invalidSlotsExist = false;
		if (!StringUtils.equals(plantId, ApplicationConstants.String_ZERO)) {
			invalidSlotsExist = true;
			InvalidSlotsSummary invalidSlotsSummary = buildMatrixSmcService.getInvalidSlotSummaryForPlantAndSlotType(plantId, slotTypeId);
			model.addObject("invalidSlotsSummary", invalidSlotsSummary);
			model.addObject("selectedMfr", invalidSlotsSummary.getBodyPlant().getPlantMfrCode());
			model.addObject("selectedPlant", invalidSlotsSummary.getBodyPlant().getPlantId());
			model.addObject("selectedSlotType", invalidSlotsSummary.getSlotType().getSlotTypeId());
			model.addObject("plantList", buildMatrixSmcService.getInvalidBodyPlantsByMfrCode(invalidSlotsSummary.getBodyPlant().getPlantMfrCode()));
			model.addObject("mfrMap", buildMatrixSmcService.getInvalidMfrList());
			Set<Integer> slotTypeIds = buildMatrixSmcService.getInvalidSlotTypesforPlant(invalidSlotsSummary.getBodyPlant().getPlantId());
			model.addObject("slotTypeList", buildMatrixSmcService.getVehicleTypeByIds(slotTypeIds));
			
		}
		model.addObject("invalidSlotsExist", invalidSlotsExist);
		return model;
	}
  
}