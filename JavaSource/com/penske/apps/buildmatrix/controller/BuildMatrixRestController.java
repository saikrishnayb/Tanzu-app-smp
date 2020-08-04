/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.FileUtil;
import com.penske.apps.adminconsole.util.FileUtil.UploadInvalidReason;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.DistrictProximityForm;
import com.penske.apps.buildmatrix.model.ImportRegionSlotsResults;
import com.penske.apps.buildmatrix.model.ImportSlotsResults;
import com.penske.apps.buildmatrix.model.SavePlantRegionForm;
import com.penske.apps.buildmatrix.model.SaveRegionSlotsForm;
import com.penske.apps.buildmatrix.model.SaveSlotsForm;
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
	
	// EXPORT TO EXCEL //
	/**************************************************************************************************************
	 * Method to load data into excel file.
	 * @param request
	 * @param response
	 * @param confirmationSearch
	 ***************************************************************************************************************
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="/exportToExcel",method = {RequestMethod.POST })
	public void exportToExcel(HttpServletResponse response,@ModelAttribute("buildId")int buildId)
	{
		SXSSFWorkbook workbook = buildMatrixSmcService.downloadProductionSlotResultsDocument(buildId);
		if(workbook != null){
			try(OutputStream out = response.getOutputStream()) {
				if(buildId!=0){
					response.setHeader("Set-Cookie", "fileDownload=true; path=/");

					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE_XLSX);
					response.setHeader(ApplicationConstants.CONTENT_DISPOSITION_HEADER,"attachment;filename=\"Order Confirmation-Template.xlsx\"");
					response.setHeader("Pragma",ApplicationConstants.EXCEL_HEADER_TYPE);
					response.setHeader("Expires",ApplicationConstants.EXCEL_EXPIRES);
					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE);
					workbook.write(out);
				}
				else{
					response.getWriter().write("Production slot results not available to process the template");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}


			} catch(IOException ex) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				throw new RuntimeException(ex.getMessage(), ex);
			}finally{
				workbook.dispose();
			}
		}
	}
	//***** OEM MIX MAINTENACE *****//
	/**
	 * Method to save OEM Mix Maintenance page
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/save-business-award-maint")
	public void saveBusinessAwardMaint(BusinessAwardForm businessAwardForm) {
		buildMatrixSmcService.saveBusinessAwardMaintenance(businessAwardForm);
	}
	
	//***** BUILD ATTRIBUTE *****//
	/**
	 * Method to Loads Add Attribute Value Popup
	 * 
	 * @param request
	 * @return ModelAndView
	 */ 
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/get-add-attribute-content",method = { RequestMethod.GET })
	@ResponseBody
	public ModelAndView getAddAttributeContent(@RequestParam("attributeId") int attributeId) {
		BuildAttribute buildAttribute = buildMatrixSmcService.getBuildAttributeById(attributeId);
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/add-update-attribute-modal");
		model.addObject("addPopup", true);
		model.addObject("buildAttribute", buildAttribute);
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
	public ModelAndView getEditAttributeContent(@RequestParam("attributeId") int attributeId) {

		BuildAttribute buildAttribute = buildMatrixSmcService.getBuildAttributeById(attributeId);
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/add-update-attribute-modal");
		model.addObject("editPopup", true);
		model.addObject("buildAttribute", buildAttribute);
		return model;
	}
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="update-attribute")
    public void updateAttribute(@RequestParam("attributeId") int attributeId, @RequestParam("attributeValues[]") List<String> attributeValues) {
        	buildMatrixSmcService.updateAttribute(attributeId, attributeValues);
    }
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="add-attribute")
    public BuildAttributeValue addAttribute(@RequestParam(value="attributeId") int attributeId, @RequestParam(value="attributeValue") String attributeValue) {
        BuildAttributeValue attrValue = buildMatrixSmcService.addOrUpdateAttribute(attributeId, attributeValue);
        return attrValue;
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
		isUnique = buildMatrixSmcService.checkForUniqueAttributeValue(attributeId, attributeValue);
        return isUnique;
    }

	///***** PLANT MAINTENANCE *****//
	/**
	 *Method to load offline dates setup model 
	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value = "/get-offline-date-setup-modal", method = RequestMethod.POST)
    public ModelAndView getModalData(@RequestParam("plantId") int plantId)
	{
		ModelAndView mav = new ModelAndView("/admin-console/oem-build-matrix/modal/set-offline-dates-modal");
		mav.addObject("plantData", buildMatrixSmcService.getPlantData(plantId));
		return mav;
	}
	
	/**
	 * Method to save offline dates for a plant
	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="save-offline-dates")
    public ModelAndView saveOfflineDates(BuildMatrixBodyPlant plantData) {
       	buildMatrixSmcService.saveOfflineDates(plantData);
       	return new ModelAndView("redirect:/app/admin-console/oem-build-matrix/maintenance-summary");
    }

	/**
	 * method to save proximity configuration
	 * @return 
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/save-district-proximity")
	public ModelAndView saveDistrictProximity(DistrictProximityForm districtProximityForm) {
		if(districtProximityForm.getPlantId() !=0) {
		 buildMatrixSmcService.saveDistrictProximity(districtProximityForm.getPlantProximities());
		}
		return new ModelAndView("redirect:/app/admin-console/oem-build-matrix/maintenance-summary");
	}
	
	/**
	 * Method to Loads Edit Dimension Popup Modal
	 * 
	 * @param attributeId, plantId, key, attributeName
	 * 
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/load-edit-dimension-popup-modal", method = { RequestMethod.GET })
	public ModelAndView loadEditDimensionPopup(@RequestParam("attributeId") int attributeId,
											   @RequestParam("plantId") int plantId, 
											   @RequestParam("key") String key,
											   @RequestParam("attributeName") String attributeName) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/edit-dimension");
		List<BodyPlantCapability> bodyPlantCapability = buildMatrixSmcService.getBodyPlantExceptionsById(plantId, attributeId);
		
		if (!bodyPlantCapability.get(0).getAttributeValuesMap().isEmpty()) {
			model.addObject("isDataAvailable", true);
			model.addObject("bodyPlantCapability", bodyPlantCapability);
		}
		
		model.addObject("plantId", plantId);
		model.addObject("attributeId", attributeId);
		model.addObject("attributeKey", key);
		model.addObject("attributeName", attributeName);

		return model;
	}

	/**
	 * method to update capability
	 * 
	 * @param plantId,
	 *            attributeKey, capabilityUpdatelist[]
	 * @return 
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/update-capability")
	public void updateCapability(@RequestParam("plantId") int plantId,
								 @RequestParam("attributeKey") String attributeKey,
							     @RequestParam("capabilityUpdatelist[]") List<String> capabilityUpdatelist) {
		String attributesCommaSeparated = capabilityUpdatelist.stream().collect(Collectors.joining(","));
		buildMatrixSmcService.updateCapability(plantId, attributeKey, attributesCommaSeparated);
	}

	/**
	 * Method to load plant region association setup model
	 */
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
	@RequestMapping(value = "/get-region-association-modal", method = RequestMethod.POST)
	public ModelAndView getRegionAssociationModalData(@RequestParam("plantId") int plantId) {
		ModelAndView mav = new ModelAndView("/admin-console/oem-build-matrix/modal/region-association-modal");
		mav.addObject("plantData", buildMatrixSmcService.getPlantData(plantId));
		mav.addObject("regionData", buildMatrixSmcService.getRegionAssociationData(plantId));
		return mav;
	}

	/**
	 * method to save plant region association
	 * 
	 * @return
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/save-region-association")
	public ModelAndView savePlantRegionAssociation(SavePlantRegionForm savePlantRegionForm) {
		buildMatrixSmcService.savePlantRegionAssociation(savePlantRegionForm.getRegionPlantAssociationList());
		return new ModelAndView("redirect:/app/admin-console/oem-build-matrix/maintenance-summary");
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/get-update-build-params")
	public ModelAndView getUpdateBuildParams(@RequestParam("buildId") int buildId) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/update-build-params");
		BuildSummary summary = buildMatrixSmcService.getBuildSummary(buildId);
		
		if(summary == null)
			throw new IllegalArgumentException("Could not find build with the provided Build ID - " + buildId);
		
		model.addObject("summary", summary);
		model.addObject("buildId", buildId);

		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/update-build-params")
	public void updateBuildParams(@RequestParam("buildId") int buildId, @RequestParam("maxWeeksBefore") int maxWeeksBefore, @RequestParam("maxWeeksAfter") int maxWeeksAfter) {
		BuildSummary summary = buildMatrixSmcService.getBuildSummary(buildId);
		
		if(summary == null)
			throw new IllegalArgumentException("Could not find build with the provided Build ID - " + buildId);
		
		summary.setMaxWeeksBefore(maxWeeksBefore);
		summary.setMaxWeeksAfter(maxWeeksAfter);
		
		buildMatrixSmcService.updateBuildParams(summary);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/get-release-units-modal")
	public ModelAndView getReleaseUnitsModal(@RequestParam("region") String region,
			@RequestParam("dateId") int dateId,
			@RequestParam("plantId") int plantId,
			@RequestParam("slotId") int slotId,
			@RequestParam("slotRegionId") int slotRegionId,
			@RequestParam("regionDesc") String regionDesc) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/release-units-modal");
		
		BuildMatrixBodyPlant bodyPlant = buildMatrixSmcService.getBodyPlantById(plantId);
		BuildMatrixSlotDate slotDate = buildMatrixSmcService.getSlotDate(dateId);
		List<ProductionSlotResult> slotReservations = buildMatrixSmcService.getSlotReservationsByIdAndRegion(slotId, region);
		
		model.addObject("region", region);
		model.addObject("regionDesc", regionDesc);
		model.addObject("slotDate", slotDate);
		model.addObject("bodyPlant", bodyPlant);
		model.addObject("slotReservations", slotReservations);
		
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/release-units")
	public void releaseUnits(@RequestParam("slotReservationIds[]") List<Integer> slotReservationIds) {
		buildMatrixSmcService.releaseUnits(slotReservationIds);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/delete-build")
	public void deleteBuild(@RequestParam("buildId") int buildId) {
		buildMatrixSmcService.deleteBuild(buildId);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/rework-build")
	public void reworkBuild(@RequestParam("buildId") int buildId) {
		buildMatrixSmcService.reworkBuild(buildId);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/get-create-slots-modal")
	public ModelAndView getCreateSlotsModal() {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/create-slots-modal");
		
		List<BuildMatrixSlotType> buildMatrixSlotTypes = buildMatrixSmcService.getAllVehicleTypes();
		
		int currentYear = LocalDate.now().getYear();
				
		model.addObject("buildMatrixSlotTypes", buildMatrixSlotTypes);
		model.addObject("currentYear", currentYear);
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/check-slots-exist")
	public boolean checkSlotsExist(@RequestParam("year") int year, @RequestParam("slotTypeId") int slotTypeId) {
		boolean result = buildMatrixSmcService.checkSlotsExist(year, slotTypeId);
		
		return result;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/create-slots")
	public void createSlots(@RequestParam("year") int year, @RequestParam("slotTypeId") int slotTypeId) {
		buildMatrixSmcService.createSlots(year, slotTypeId);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="get-import-slot-maintenance",method = {RequestMethod.GET })
	public ModelAndView getImportSlotMaintenance(@RequestParam("year") String yearString, @RequestParam("slotTypeId")  String slotTypeIdString) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/import-slot-maintenance");
		
		int year = Integer.parseInt(yearString);
		int slotTypeId = Integer.parseInt(slotTypeIdString);
		
		BuildMatrixSlotType slotType = buildMatrixSmcService.getVehicleTypeById(slotTypeId);
		
		model.addObject("slotType", slotType);
		model.addObject("year", year);
		
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="get-import-region-slot-maintenance",method = {RequestMethod.GET })
	public ModelAndView getImportRegionSlotMaintenance(@RequestParam("year") String yearString, @RequestParam("slotTypeId")  String slotTypeIdString,
			@RequestParam("region")  String region, @RequestParam("regionDesc") String regionDesc) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/import-region-slot-maintenance");
		
		int year = Integer.parseInt(yearString);
		int slotTypeId = Integer.parseInt(slotTypeIdString);
		
		BuildMatrixSlotType slotType = buildMatrixSmcService.getVehicleTypeById(slotTypeId);
		
		model.addObject("slotType", slotType);
		model.addObject("year", year);
		model.addObject("region", region);
		model.addObject("regionDesc", regionDesc);
		
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="get-export-slot-maintenance",method = {RequestMethod.GET })
	public ModelAndView getExportSlotMaintenance(@RequestParam("year") String yearString, @RequestParam("slotTypeId")  String slotTypeIdString) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/export-slot-maintenance");
		
		int year = Integer.parseInt(yearString);
		int slotTypeId = Integer.parseInt(slotTypeIdString);
		
		BuildMatrixSlotType slotType = buildMatrixSmcService.getVehicleTypeById(slotTypeId);
		
		Map<String, String> mfrMap = buildMatrixSmcService.getMfrListForExport();
		
		model.addObject("slotType", slotType);
		model.addObject("year", year);
		model.addObject("mfrMap", mfrMap);
		
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="get-body-plants-by-mfr-code",method = {RequestMethod.GET })
	public List<BuildMatrixBodyPlant> getBodyPlantsForMfr(@RequestParam("mfrCode") String mfrCode) {
		return buildMatrixSmcService.getBodyPlantsByMfrCode(mfrCode);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="export-slot-maintenance",method = {RequestMethod.POST })
	public void exportSlotMaintenance(HttpServletResponse response, @RequestParam("slotTypeId") int slotTypeId, 
			@RequestParam("year") int year,
			@RequestParam("plantIds[]") List<Integer> plantIds) {
		
		SXSSFWorkbook workbook = buildMatrixSmcService.exportSlotMaintenance(year, slotTypeId, plantIds);
		if(workbook != null){
			try(OutputStream out = response.getOutputStream()) {
				if(year!=0){
					response.setHeader("Set-Cookie", "fileDownload=true; path=/");

					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE_XLSX);
					response.setHeader(ApplicationConstants.CONTENT_DISPOSITION_HEADER,"attachment;filename=\"Order Confirmation-Template.xlsx\"");
					response.setHeader("Pragma",ApplicationConstants.EXCEL_HEADER_TYPE);
					response.setHeader("Expires",ApplicationConstants.EXCEL_EXPIRES);
					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE);
					workbook.write(out);
				}
				else{
					response.getWriter().write("Slot Maintenance not available to process the template");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}


			} catch(IOException ex) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				throw new RuntimeException(ex.getMessage(), ex);
			}finally{
				workbook.dispose();
			}
		}
		
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/show-accept-button")
	public boolean showAcceptButton(@RequestParam("buildId") int buildId) {
		List<ProductionSlotResult> slotResults = buildMatrixSmcService.getSlotResultsByFilter(buildId, null);
		boolean showAcceptBtn = !slotResults.stream().anyMatch(order -> !order.showAcceptBtn());
		return showAcceptBtn;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(method = RequestMethod.POST, value = "/import-slot-maintenance")
	public ModelAndView importSlotMaintenanceExcelFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "slotTypeId") int slotTypeId, @RequestParam(value = "year") int year, 
    		HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/import-slots-confirmation");
        //The file name is required for the save function.
        String fileName = file.getOriginalFilename();
        ImportSlotsResults results = null;
        try{
			UploadInvalidReason errorCode = FileUtil.validateFileUpload(file, Collections.singleton("xlsx"));
			if(errorCode != null)
			{
				//We do some error message translation with the generic file type message, to make sure the user knows it's specific to XLSX files.
				String message;
				if(errorCode == UploadInvalidReason.INVALID_FILE_TYPE)
					message = fileName + " is not a valid Excel file, Please ensure your file is an XLSX file type and try again.";
				else
					message = errorCode.getErrorMessage(fileName);

				response.getWriter().write(message);
			}
			else
			{
				results = buildMatrixSmcService.importSlotMaintenace(file, fileName, slotTypeId, year);
			}
        } catch(Exception ex){
			//handleException(ex,request);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
        
        BuildMatrixSlotType slotType = buildMatrixSmcService.getVehicleTypeById(slotTypeId);
        
        String backUrl = "/suppliermgmt/app/admin-console/oem-build-matrix/prod-slot-maintenance.htm?slotType=" + slotTypeId + "&year=" + year;
        
        model.addObject("results", results);
        model.addObject("slotType", slotType);
        model.addObject("year", year);
        model.addObject("backUrl", backUrl);
        return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(method = RequestMethod.POST, value = "/import-region-slot-maintenance")
	public ModelAndView importRegionSlotMaintenanceExcelFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "slotTypeId") int slotTypeId, @RequestParam(value = "year") int year, @RequestParam(value = "region") String region, 
			@RequestParam(value = "regionDesc") String regionDesc, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/import-region-slots-confirmation");
        //The file name is required for the save function.
        String fileName = file.getOriginalFilename();
        ImportRegionSlotsResults results = null;
        try{
			UploadInvalidReason errorCode = FileUtil.validateFileUpload(file, Collections.singleton("xlsx"));
			if(errorCode != null)
			{
				//We do some error message translation with the generic file type message, to make sure the user knows it's specific to XLSX files.
				String message;
				if(errorCode == UploadInvalidReason.INVALID_FILE_TYPE)
					message = fileName + " is not a valid Excel file, Please ensure your file is an XLSX file type and try again.";
				else
					message = errorCode.getErrorMessage(fileName);

				response.getWriter().write(message);
			}
			else
			{
				results = buildMatrixSmcService.importRegionSlotMaintenace(file, fileName, slotTypeId, year, region);
			}
        } catch(Exception ex){
			//handleException(ex,request);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
        
        BuildMatrixSlotType slotType = buildMatrixSmcService.getVehicleTypeById(slotTypeId);
        
        String backUrl = "/suppliermgmt/app/admin-console/oem-build-matrix/prod-slot-region-maintenance.htm?slotType=" + slotTypeId 
        		+ "&year=" + year + "&region=" + region;
        
        model.addObject("results", results);
        model.addObject("slotType", slotType);
        model.addObject("year", year);
        model.addObject("region", region);
        model.addObject("regionDesc", regionDesc);
        model.addObject("backUrl", backUrl);
        return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(method = RequestMethod.POST, value = "/save-slots")
	public ModelAndView saveSlots(SaveSlotsForm form) {
		
		buildMatrixSmcService.saveSlots(form);
		
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("slotType", form.getSlotTypeId());
		modelMap.addAttribute("year", form.getYear());
		return new ModelAndView("redirect:/app/admin-console/oem-build-matrix/prod-slot-maintenance", modelMap);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(method = RequestMethod.POST, value = "/save-region-slots")
	public ModelAndView saveRegionSlots(SaveRegionSlotsForm form) {
		
		buildMatrixSmcService.saveRegionSlots(form);
		
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("slotType", form.getSlotTypeId());
		modelMap.addAttribute("year", form.getYear());
		modelMap.addAttribute("region", form.getRegion());
		return new ModelAndView("redirect:/app/admin-console/oem-build-matrix/prod-slot-region-maintenance", modelMap);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })	
	@RequestMapping(value="/update-run-status", method = {RequestMethod.GET })
	public void updateRunSummary(@RequestParam("buildId") int buildId) {
		buildMatrixSmcService.updateRunSummary(buildId);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/delete-reservation-data")
	public void deleteReservationData(@RequestParam("slotReservationIdList[]") List<Integer> slotReservationIdList) {
		buildMatrixSmcService.deleteReservationData(slotReservationIdList);
	}
	
	/**
	 * Method to Loads Edit Dimension Popup Modal
	 * 
	 * @param attributeId, plantId, key, attributeName
	 * 
	 * @return ModelAndView
	 * @throws JsonProcessingException 
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/load-update-reservation-popup-modal")
	public ModelAndView loadUpdateReservationPopup(@RequestParam("buildId") int buildId, @RequestParam("reservationId") int reservationId) throws JsonProcessingException {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/update-reservation-modal");
		ProductionSlotResult productionSlotResult = buildMatrixSmcService.getProductionSlotResults(buildId, reservationId);
		String unitNumber = productionSlotResult.getUnitNumber();
		model.addObject("plantList", buildMatrixSmcService.getAllPlants());
		model.addObject("productionSlotList", buildMatrixSmcService.getProductionSlotList(buildId, unitNumber));
		model.addObject("productionSlotResult",productionSlotResult);
		if(productionSlotResult.getPlantId()!= 0)
		 {
			 ObjectMapper mapper = new ObjectMapper();
			 String jsonString = mapper.writeValueAsString(buildMatrixSmcService.getSlotDatesForPlant(productionSlotResult.getPlantId(), productionSlotResult.getVehicleType(), productionSlotResult.getRegion()));
			 model.addObject("slotDates",jsonString);
		 }
		return model;
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/get-available-slot-dates")
	@ResponseBody
	public List<BuildMatrixSlotDate> getAvailableSlotDates(@RequestParam("plantId") int plantId, @RequestParam("vehicleType") String vehicleType, @RequestParam("region") String region) {
		List<BuildMatrixSlotDate> slotDates=buildMatrixSmcService.getSlotDatesForPlant(plantId, vehicleType, region);
		return slotDates;
		
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/update-reservation-data")
	public void updateReservationData(@RequestParam("slotReservationId") int slotReservationId, @RequestParam("slotId") int slotId,
									  @RequestParam("plantId") int plantId, @RequestParam("unitNumber") String unitNumber) {
		UserContext user = sessionBean.getUserContext();
		buildMatrixSmcService.updateReservationData(slotReservationId, slotId, plantId, unitNumber, user);
	}
	
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value="export-region-slot-maintenance",method = {RequestMethod.POST })
	public void exportRegionSlotMaintenance(HttpServletResponse response, @RequestParam("slotTypeId") int slotTypeId, 
			@RequestParam("year") int year,
			@RequestParam("region") String region) {
		
		SXSSFWorkbook workbook = buildMatrixSmcService.exportRegionSlotMaintenance(year, slotTypeId, region);
		if(workbook != null){
			try(OutputStream out = response.getOutputStream()) {
				if(year!=0){
					response.setHeader("Set-Cookie", "fileDownload=true; path=/");

					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE_XLSX);
					response.setHeader(ApplicationConstants.CONTENT_DISPOSITION_HEADER,"attachment;filename=\"Order Confirmation-Template.xlsx\"");
					response.setHeader("Pragma",ApplicationConstants.EXCEL_HEADER_TYPE);
					response.setHeader("Expires",ApplicationConstants.EXCEL_EXPIRES);
					response.setContentType(ApplicationConstants.EXCEL_CONTENT_TYPE);
					workbook.write(out);
				}
				else{
					response.getWriter().write("Slot Maintenance not available to process the template");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}


			} catch(IOException ex) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				throw new RuntimeException(ex.getMessage(), ex);
			}finally{
				workbook.dispose();
			}
		}
		
	}
	
}
