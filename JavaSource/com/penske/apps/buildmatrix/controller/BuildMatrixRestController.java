/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.PlantProximity;
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
    public void updateAttribute(@RequestParam("attributeId") int attributeId, @RequestParam("attrValueIds[]") List<Integer> attrValueIds) {
        	buildMatrixSmcService.updateAttribute(attributeId, attrValueIds);
    }
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="add-attribute")
    public BuildAttributeValue addAttribute(@RequestParam(value="attributeId") int attributeId, @RequestParam(value="attributeValue") String attributeValue) {
        BuildAttributeValue attrValue = buildMatrixSmcService.addAttribute(attributeId,attributeValue);
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
    public void saveOfflineDates(@RequestBody BuildMatrixBodyPlant plantData) {
       	buildMatrixSmcService.saveOfflineDates(plantData);
    }

	/**
	 * method to save proximity configuration
	 * @return 
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping("/save-district-proximity")
	public void saveDistrictProximity(@RequestBody List<PlantProximity> plantProximityList) {
		 buildMatrixSmcService.saveDistrictProximity(plantProximityList);
	}

	/**
	 * Method to Loads Edit Dimension Popup Modal
	 * 
	 * @param attributeId, plantId, key
	 * 
	 * @return ModelAndView
	 */
	@SmcSecurity(securityFunction = { SecurityFunction.OEM_BUILD_MATRIX })
	@RequestMapping(value = "/load-edit-dimension-popup-modal", method = { RequestMethod.GET })
	public ModelAndView loadEditDimensionPopup(@RequestParam("attributeId") int attributeId,
											   @RequestParam("plantId") int plantId, 
											   @RequestParam("key") String key,
											   @RequestParam("attributeName") String attributeName,
											   @RequestParam("attributeValues") String attributeValues) {
		ModelAndView model = new ModelAndView("/admin-console/oem-build-matrix/modal/edit-dimension");
		
		if (!attributeValues.isEmpty()) {
		Map<String, String> reconstructedAttributeMap = Arrays.stream(attributeValues.split(","))
	            											  .map(s -> s.split("="))
	            											  .collect(Collectors.toMap(s -> s[0], s -> s[1]));
		
		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
		 
		reconstructedAttributeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
		    					 			.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		
			model.addObject("isDataAvailable", true);
			model.addObject("attributeValuesMap", sortedMap);
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
}
