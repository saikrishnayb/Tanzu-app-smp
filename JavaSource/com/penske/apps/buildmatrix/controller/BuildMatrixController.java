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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.model.BuildMatrixAttribute;
import com.penske.apps.adminconsole.model.DistrictProximity;
import com.penske.apps.adminconsole.service.BodyPlantBuildHistoryService;
import com.penske.apps.adminconsole.service.BodyPlantCapabilityService;
import com.penske.apps.adminconsole.service.BodyPlantMaintSummaryService;
import com.penske.apps.adminconsole.service.BusinessAwardMaintService;
import com.penske.apps.adminconsole.service.DistrictProximityService;
import com.penske.apps.adminconsole.service.OEMAttributeService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
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

	@Autowired
	private OEMAttributeService attributeService;
	
	@Autowired
	private BodyPlantCapabilityService capabilityService;
	
	@Autowired
	private BodyPlantBuildHistoryService buildHistoryService;
	
	@Autowired
	private BodyPlantMaintSummaryService maintSummaryService;
	
	@Autowired
	private BusinessAwardMaintService businessAwardMaintService;
	
	@Autowired
	private DistrictProximityService districtProximityService;

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
	 * method to load attribute maintenance screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/attribute-maintenance")
	public ModelAndView getattributeMaintenace() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/attribute-setup");
		model.addObject("attributeList", attributeService.getAllBuildMatrixAttributes());
		return model;
	}

	/**
	 * Method to Loads Edit Attribute Popup
	 * 
	 * @param request
	 * @return ModelAndView
	 */ 
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/get-edit-attribute-content",method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView getEditAttributeContent(@RequestParam("attributeId") int attributeId, HttpServletResponse response) {
		BuildMatrixAttribute buildMatrixAttribute = attributeService.getAttributeDetails(attributeId);
		ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/oem-build-matrix/edit-attribute-modal");
		try {
			
			model.addObject("optionGroupList",attributeService.getDropdownOptionGrpList());//Need to be updated once mapping is known
			model.addObject("editPopup", true);
			model.addObject("attribute", buildMatrixAttribute);
		} catch (Exception e) {
			LOGGER.error("Error in loading Edit Attribute popup" .concat(e.getLocalizedMessage()) );
			//handleAjaxException(e, response);
		}
		return model;
	}

	/**
	 * Method to Loads Add Attribute Value Popup
	 * 
	 * @param request
	 * @return ModelAndView
	 */ 
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/get-add-attribute-content",method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView getAddAttributeContent(@RequestParam("attributeId") int attributeId, HttpServletResponse response) {
		BuildMatrixAttribute buildMatrixAttribute = attributeService.getAttributeDetails(attributeId);
		ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/oem-build-matrix/edit-attribute-modal");
		try {
			model.addObject("addPopup", true);
			model.addObject("attribute", buildMatrixAttribute);
		} catch (Exception e) {
			LOGGER.error("Error in loading Add Attribute Value popup" .concat(e.getLocalizedMessage()) );
		}
		return model;
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
		model.addObject("capabilityList", capabilityService.getAllBuildMatrixCapabilities());
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
		model.addObject("bodyPlantCapability",capabilityService.getCapabilityDetails(capabilityId));
		model.addObject("editPopup", true);
		return model;
	}

	/**
	 * method to load build history screen
	 * 
	 * @return
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

	/**
	 * method to load maintenance summary screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/maintenance-summary")
	public ModelAndView getMaintenanceSummary() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/maintenance-summary");
		model.addObject("maintenanceSummary", maintSummaryService.getMaintenanceSummary());
		return model;
	}
	
	/**
	 * method to load district proximity screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/district-proximity")
	public ModelAndView getDistrictProximity() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/district-proximity");
		//model.addObject("districtProximity", districtProximityService.getDistrictProximity());
		model.addObject("districtProximityList", districtProximityService.getDistrictProximityMockService());
		return model;
	}
	
	/**
	 * method to business award maintenance screen
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/business-award-maint")
	public ModelAndView getBusinessAwardMaintenance() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/business-award-maintenance");
		model.addObject("oemMaintenanceList", businessAwardMaintService.getAllOEMs());
		return model;
	}
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="insert-district-proximity")
    @ResponseBody
    public ModelAndView insertDistrictProximity(DistrictProximity districtProximity,HttpServletResponse response) throws Exception {
		/*
		 * try{ System.out.println("Inside insert-district-proximity!!!!!!!!!!");
		 * districtProximityService.insertDistrictProximity(districtProximity); }catch
		 * (Exception e) {
		 * LOGGER.error("Error while saving proximity: "+e.getMessage(),e);
		 * CommonUtils.getCommonErrorAjaxResponse(
		 * response,"Error Processing the updating Attribute"); }
		 */
        return null;
    }
	
	/*@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="insert-district-proximity")
    public int insertDistrictProximity(DistrictProximity districtProximity) throws Exception {
		System.out.println("Inside insert-district-proximity!!!!!!!!!!");
        districtProximityService.insertProximityValues(districtProximity);
        return 0;
    }
	*/
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
		int chassisAvailable = buildMatrixCorpService.getAvailableChasisCount();
		
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
		int chassisAvailable = buildMatrixCorpService.getAvailableChasisCount();
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
	@RequestMapping("/build-mix")
	public ModelAndView getConfirmSelection(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/build-mix");
		BuildSummary existingBuild = buildMatrixSmcService.getBuildSummary(buildId);
		if(existingBuild == null)
			throw new IllegalArgumentException("Couldn't find existing build");
		
		List<CroOrderKey> selectedOrderKeys = buildMatrixSmcService.getCroOrderKeysForBuild(existingBuild.getBuildId());
		List<ApprovedOrder> selectedOrders = buildMatrixCroService.getApprovedOrdersByIds(selectedOrderKeys);
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		int chassisAvailable = buildMatrixCorpService.getAvailableChasisCount();
		
		List<BuildAttribute> attributes = buildMatrixSmcService.getAttributesForBuild();
		
		model.addObject("bodiesOnOrder", bodiesOnOrder);
		model.addObject("chassisAvailable", chassisAvailable);
		model.addObject("buildId", buildId);
		model.addObject("attributes", attributes);
		
		return model;
	}
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="update-attribute")
    @ResponseBody
    public ModelAndView updateAttribute(BuildMatrixAttribute attributeData,HttpServletResponse response) throws Exception {
        try{
        	attributeService.updateAttribute(attributeData);
        }catch (Exception e) {
            LOGGER.error("Error while updating Attribute: "+e.getMessage(),e);
            CommonUtils.getCommonErrorAjaxResponse(response,"Error Processing the updating Attribute");
        }
        return null;
    }
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="add-attribute")
    public ModelAndView addAttribute(@RequestParam(value="attributeId") int attributeId, @RequestParam(value="attributeValue") String attributeValue) throws Exception {
        try{
        	attributeService.addAttribute(attributeId,attributeValue);
        }catch (Exception e) {
            LOGGER.error("Error while saving Attribute: "+e.getMessage(),e);
        }
        return null;
    }
	
	/**
	 * method to check for unique attribute value
	 * 
	 * 	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
	@RequestMapping(value = "/check-unique-attribute-value", method = RequestMethod.POST)
	@ResponseBody
    public boolean checkForUniqueAttributeValue(@RequestParam(value="attributeId") int attributeId, @RequestParam(value="attributeValue") String attributeValue) {
		boolean isUnique = true;
		isUnique = attributeService.checkForUniqueAttributeValue(attributeId, attributeValue);
        return isUnique;
    }
}