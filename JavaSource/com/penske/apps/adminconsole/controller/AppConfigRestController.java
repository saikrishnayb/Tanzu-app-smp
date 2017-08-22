package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.exceptions.DynamicRulePriorityException;
import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.DateType;
import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;
import com.penske.apps.adminconsole.model.DelayTypeReason;
import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.LoadSheetCategoryDetails;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.NotificationForm;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;
import com.penske.apps.adminconsole.model.Subject;
import com.penske.apps.adminconsole.model.UnitException;
import com.penske.apps.adminconsole.service.AlertService;
import com.penske.apps.adminconsole.service.DelayService;
import com.penske.apps.adminconsole.service.DynamicRuleService;
import com.penske.apps.adminconsole.service.ExceptionService;
import com.penske.apps.adminconsole.service.LoadSheetManagementService;
import com.penske.apps.adminconsole.service.NotificationService;
import com.penske.apps.adminconsole.service.SearchTemplateService;
import com.penske.apps.adminconsole.service.SubjectService;
import com.penske.apps.adminconsole.service.TabService;
import com.penske.apps.adminconsole.service.TermsAndConditionsService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;

/**
 * This Controller class contains all of the AJAX request methods for any
 * pages that fall under the App Config tree of the application.
 * @author 600132441 M.Leis
 *
 */
@Controller
@RequestMapping("/admin-console/app-config")
public class AppConfigRestController {

    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private DelayService delayService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DynamicRuleService dynamicRuleService;
    @Autowired
    private AlertService alertService;
    @Autowired
    private TabService tabService;
    @Autowired
    private SearchTemplateService searchTemplateService;
    @Autowired
    private TermsAndConditionsService termsAndConditionsService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LoadSheetManagementService loadsheetManagementService;

    private static Logger logger = Logger.getLogger(AppConfigRestController.class);

    /* ================== Subject Management ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_SUBJECTS)
    @RequestMapping(value="/modify-subject")
    @ResponseBody
    public void modifySubject(Subject subject) {
        subjectService.modifySubject(subject);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_SUBJECTS)
    @RequestMapping(value="/modify-subject-status")
    @ResponseBody
    public void modifySubjectStatus(@RequestParam(value="subjectId") int subjectId) {
        subjectService.modifySubjectStatus(subjectId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_SUBJECTS)
    @RequestMapping(value="/add-subject")
    @ResponseBody
    public void addSubject(Subject subject) {
        subjectService.addSubject(subject);
    }

    /* ================== Notifications ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("get-notification-modal-content")
    @ResponseBody
    public ModelAndView getNotificationModalContent(@RequestParam(value="notificationId") int notificationId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/notification-modal-content");

	@RequestMapping("get-add-delay-type-modal-page")
	@ResponseBody
	public ModelAndView getAddDelayTypeModal() {
		
		return new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-type-modal");
	}
	
	/* ================== Terms And Conditions ================== */
	@RequestMapping("update-t-and-c-frequency")
	@ResponseBody
	public void updateTermsAndConditionsFrequency(@RequestParam(value="frequencyDays") String frequencyDays) {
		try {
			Integer.parseInt(frequencyDays);
		} 
		catch (Exception e) {
			//Frequency was not a number, what to do here?
			logger.debug(e);
			
			return;
		}
		
		termsAndConditionsService.updateTermsAndConditionsFrequency(frequencyDays);
	}
	
	@RequestMapping("view-t-and-c")
	@ResponseBody
	public String viewTermsAndConditions(@RequestParam(value="versionNumber") int versionNumber) {
		return termsAndConditionsService.getTermsAndConditionsText(versionNumber);
	}
	
	/* ================== Get Rule Association model ================== */
	@RequestMapping(value="/get-rule-association-modal-data", method=  RequestMethod.POST)
	@ResponseBody
	public ModelAndView getRuleAssociationModalData(HttpServletRequest request,@ModelAttribute("componentRule") ComponentRuleAssociation componentRule,@RequestParam("componentId") int componentId,@RequestParam("componentVisibleId") int componentVisibleId,@RequestParam(value="viewMode") String viewMode) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/add-rule-association-modal");
		componentRule.setRule(loadsheetManagementService.getComponentVisibilityRules(componentVisibleId));
		componentRule.setComponentVisibilityId(componentVisibleId);
		mav.addObject("rules", loadsheetManagementService.getComponentRules());
		mav.addObject("componentRule", componentRule);
		mav.addObject("viewMode", viewMode);
		
		//Adding details to session for create rule back button
		HttpSession session = request.getSession(false); 
		if(session != null){
			//Adding component ID and Visibility ID to session category object
			LoadSheetCategoryDetails catDetails=(LoadSheetCategoryDetails)session.getAttribute("CATEGORY_DETAILS");
			catDetails.setComponentId(componentId);
			catDetails.setVisibilityId(componentVisibleId);
			session.setAttribute("CATEGORY_DETAILS", catDetails);
		}
		
		return mav;
	}
	
	/* ================== Save Rule Association ================== */
	@RequestMapping(value="/save-rule-association-modal-data", method=  RequestMethod.POST)
	@ResponseBody
	public void saveRuleAssociationModalData(@ModelAttribute("componentRule") ComponentRuleAssociation componentRule,HttpSession session,HttpServletResponse response) throws Exception {
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		componentRule.setCreatedBy(currentUser.getSso());
		try{
		loadsheetManagementService.saveComponentRules(componentRule);
		}catch(Exception e){
			CommonUtils.getCommonErrorAjaxResponse(response,"Error ocuured while adding the rules, please contact system admin.");
		}
	}
	
	/* =========================DELETE Rule ========================*/
	@RequestMapping(value="/delete-rule",method= RequestMethod.POST)
	@ResponseBody
	public void deleteTheRuleDetails(@RequestParam("ruleId") int ruleId,HttpServletResponse response) throws Exception{
		
		try{
			loadsheetManagementService.DeleteRuleDetails(ruleId);
		}catch(Exception e){
			CommonUtils.getCommonErrorAjaxResponse(response,"");
		}
	}
	
	/* ========== Check For Unique rule Name ===============*/
	@RequestMapping(value="/check-unique-rule-name",method=RequestMethod.POST)
	@ResponseBody
	public boolean checkForUniqueRuleName(@RequestParam("ruleName") String ruleName,@RequestParam("ruleId") int ruleId){
		
		return loadsheetManagementService.checkForUniqueRuleName(ruleName, ruleId);
	}
	
	/* =========================DELETE Rule ========================*/
	@RequestMapping(value="/delete-sequence",method= RequestMethod.POST)
	@ResponseBody
	public void deleteSequence(@RequestParam("sequenceId") int sequenceId,HttpServletResponse response) throws Exception{
		
		try{
			loadsheetManagementService.deleteLoadsheetSequence(sequenceId);
		}catch(Exception e){
			CommonUtils.getCommonErrorAjaxResponse(response,"");
		}
	}
	
	/* ========== Check For Unique sequence Name ===============*/
	@RequestMapping(value="/check-unique-name",method=RequestMethod.POST)
	@ResponseBody
	public int checkForUniqueName(@RequestParam("seqName") String seqName,@RequestParam("seqId") int seqId,@RequestParam("category") String category,
			@RequestParam("type") String type,@RequestParam("mfr") String mfr){
		int UniqueStatus=0;
		//if category type is empty then set default type(VOD-351).
		if(StringUtils.isEmpty(type)){
			type=ApplicationConstants.DEFAULT_TYPE;
		}
		boolean status= loadsheetManagementService.checkForUniqueSequenceName(seqName, seqId);
		if(status){
			if(StringUtils.isNotBlank(category)){
				int count = loadsheetManagementService.checkForUniqueSequence(category,type, mfr,seqId);
				if(count>0){
				 UniqueStatus=2; // duplicate sequence for category,type and mfr.	
				}else{
					UniqueStatus=0; // sequence is unique.
				}
			}
			return UniqueStatus;
		}else{
			UniqueStatus=1;
			return UniqueStatus; //duplicate sequence name.
		}
	}
	
}
