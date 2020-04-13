/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
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
	
	private static final Logger LOGGER = Logger.getLogger(BuildMatrixRestController.class);
	
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
	public ModelAndView getAddAttributeContent(@RequestParam("attributeId") int attributeId, HttpServletResponse response) {
		//BuildMatrixAttribute buildMatrixAttribute = buildMatrixSmcService.getAttributeDetails(attributeId);
		BuildAttribute buildAttribute = buildMatrixSmcService.getBuildAttributeById(attributeId);
		ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/oem-build-matrix/edit-attribute-modal");
		try {
			model.addObject("addPopup", true);
			//model.addObject("attribute", buildMatrixAttribute);
			model.addObject("buildAttribute", buildAttribute);
		} catch (Exception e) {
			LOGGER.error("Error in loading Add Attribute Value popup" .concat(e.getLocalizedMessage()) );
		}
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
		//BuildMatrixAttribute buildMatrixAttribute = buildMatrixSmcService.getAttributeDetails(attributeId);
		BuildAttribute buildAttribute = buildMatrixSmcService.getBuildAttributeById(attributeId);
		ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/oem-build-matrix/edit-attribute-modal");
		try {
			model.addObject("editPopup", true);
			//model.addObject("attribute", buildMatrixAttribute);
			model.addObject("buildAttribute", buildAttribute);
		} catch (Exception e) {
			LOGGER.error("Error in loading Edit Attribute popup" .concat(e.getLocalizedMessage()) );
		}
		return model;
	}
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="update-attribute")
    public void updateAttribute(@RequestParam("attributeId") int attributeId, @RequestParam("attrValueIds[]") List<Integer> attrValueIds) {
        	buildMatrixSmcService.updateAttribute(attributeId, attrValueIds);
    }
	
	@SmcSecurity(securityFunction = SecurityFunction.OEM_BUILD_MATRIX)
    @RequestMapping(value="add-attribute")
    public BuildAttributeValue addAttribute(@RequestParam(value="attributeId") int attributeId, @RequestParam(value="attributeValue") String attributeValue) throws Exception {
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
}
