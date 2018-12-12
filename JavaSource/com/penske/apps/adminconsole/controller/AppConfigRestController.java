package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.DynamicRulePriorityException;
import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.NotificationForm;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;
import com.penske.apps.adminconsole.model.Subject;
import com.penske.apps.adminconsole.model.UnitException;
import com.penske.apps.adminconsole.service.AlertService;
import com.penske.apps.adminconsole.service.DynamicRuleService;
import com.penske.apps.adminconsole.service.ExceptionService;
import com.penske.apps.adminconsole.service.LoadSheetManagementService;
import com.penske.apps.adminconsole.service.NotificationService;
import com.penske.apps.adminconsole.service.SearchTemplateService;
import com.penske.apps.adminconsole.service.SubjectService;
import com.penske.apps.adminconsole.service.TabService;
import com.penske.apps.adminconsole.service.TermsAndConditionsService;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.AppConfigSessionData;
import com.penske.apps.suppliermgmt.model.AppConfigSessionData.LoadSheetCategoryDetails;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * This Controller class contains all of the AJAX request methods for any
 * pages that fall under the App Config tree of the application.
 * @author 600132441 M.Leis
 *
 */
@Controller
@RequestMapping("/admin-console/app-config")
public class AppConfigRestController {

    private static final Logger LOGGER = Logger.getLogger(AppConfigRestController.class);

    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    
    @Autowired
    private ExceptionService exceptionService;
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

        LOGGER.error("getNotificationModalContent is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/notification-modal-content");

        Notification notification = notificationService.getNotification(notificationId);
        notificationService.getSortedNotificationParties(notification);
        mav.addObject("notification", notification);

        List<String> userEmails = notificationService.getUserEmails();
        mav.addObject("userEmails", userEmails);

        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-user-emails")
    @ResponseBody
    public String getUserEmails() {

        LOGGER.error("getUserEmails is used!!!! :)");

        StringBuilder emails = new StringBuilder();
        List<String> userEmails = notificationService.getUserEmails();

        if (userEmails == null) {
            // List of user emails was null, what to do here?

            return null;
        }

        for (String email : userEmails) {
            emails.append(email);
            emails.append(";");
        }

        return emails.toString();
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("update-notification")
    @ResponseBody
    public Notification updateNotification(NotificationForm notificationForm) {

        LOGGER.error("updateNotification is used!!!! :)");

        notificationService.updateNotification(notificationForm);
        notificationService.updateNotificationParty(notificationForm);

        Notification notification = notificationService.getNotification(notificationForm.getNotificationId());
        notificationService.getSortedNotificationParties(notification);

        return notification;
    }

    /* ================== Global Exceptions ================== */
    /*    * 
    * @param exceptionId
    * 
    * @param unitNumber - To display in edit modal light box
    * 
    * @param poNumber	- To display in  edit modal light box
    */
    @RequestMapping("get-global-exceptions-edit-modal")
    @ResponseBody
    public ModelAndView getGlobalExceptionsEditModal(@RequestParam(value = "exceptionId") int exceptionId,@RequestParam(value="unitNumber", required=false) String unitNumber,@RequestParam(value="poNumber",required=false) int poNumber) {

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-global-exceptions-modal");
        List<GlobalException> exception = exceptionService.getException(exceptionId);
        mav.addObject("poNumber", poNumber);
        mav.addObject("unitNumber", unitNumber);
        mav.addObject("exception", exception);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-sub-groups")
    @ResponseBody
    public List<String> getSubGroups(@RequestParam(value = "selectedOption") String selectedOption) {


        List<String> subGroups = exceptionService.getSubGroups(selectedOption);
        return subGroups;
    }

    @RequestMapping("edit-global-exception")
    @ResponseBody
    public void modifyGlobalException(@RequestParam(value = "exceptionId") int exceptionId, @RequestParam(value = "providervendorId") int providervendorId, @RequestParam(value = "poCategoryAssociationId") int poCategoryAssociationId) {

    	UserContext userContext = sessionBean.getUserContext();
        String userSSO = userContext.getUserSSO();
        exceptionService.modifyGlobalException(exceptionId, providervendorId, poCategoryAssociationId,userSSO);
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping("get-global-exceptions-delete-modal")
    @ResponseBody
    public ModelAndView getGlobalExceptionsDeleteModal(@RequestParam(value = "exceptionId") int exceptionId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-global-exception-modal");
        List<GlobalException> exception = exceptionService.getException(exceptionId);
        mav.addObject("exception", exception);
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping("delete-global-exception")
    @ResponseBody
    public void deleteGlobalException(@RequestParam(value = "exceptionId") int exceptionId) {

        exceptionService.deleteGlobalException(exceptionId);
    }

    /* ================== Dynamic Rules ================== */
    @SmcSecurity(securityFunction = SecurityFunction.DYNAMIC_RULES_MANAGEMENT)
    @RequestMapping(value = "/get-rule-modal-data", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getModalData(@RequestParam("make") String make, @RequestParam("modalName") String modalName) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/" + modalName + "-rule-modal");

        mav.addObject("corpCodes", dynamicRuleService.getAllCorpCodes());
        mav.addObject("makes", dynamicRuleService.getAllVehicleMakes());
        mav.addObject("statusValues", dynamicRuleService.getAvailableStatus());

        if (make.length() > 0) {
            mav.addObject("models", dynamicRuleService.getVehicleModelsByMake(make));
        }
        // if(status !=null && !status.isEmpty() && "Inactive".equalsIgnoreCase(status.trim())){
        mav.addObject("maxPriority", dynamicRuleService.getMaxPriority());
        // }
        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping(value = "/get-models-by-make", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getVehicleModelsByMake(@RequestParam("make") String make, @RequestParam("modalName") String modalName) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/" + modalName + "-rule-modal");

        mav.addObject("models", dynamicRuleService.getVehicleModelsByMake(make));

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping(value = "/add-dynamic-rule", method = RequestMethod.POST)
    @ResponseBody
    public void addDynamicRule(DynamicRule rule, HttpServletResponse response) {
    	UserContext userContext = sessionBean.getUserContext();
        String userSSO = userContext.getUserSSO();
        rule.setCreatedBy(userSSO);
        rule.setModifiedBy(userSSO);
        try {
            dynamicRuleService.addDynamicRule(rule);
        } catch (DynamicRulePriorityException dpe) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, dpe.getErrorMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(dpe.getErrorMessage());
                response.flushBuffer();
                // throw dpe;
            } catch (IOException e) {
            	LOGGER.error(e.getMessage());
            }
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping(value = "/modify-dynamic-rule", method = RequestMethod.POST)
    @ResponseBody
    public void modifyDynamicRule(DynamicRule rule, HttpServletResponse response) {
        try {
        	UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext.getUserSSO();
            rule.setModifiedBy(userSSO);
            dynamicRuleService.modifyDynamicRule(rule);
        } catch (DynamicRulePriorityException dpe) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, dpe.getErrorMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(dpe.getErrorMessage());
                response.flushBuffer();
                // throw dpe;
            } catch (IOException e) {
            	LOGGER.error(e.getMessage());
            }
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping(value = "/delete-dynamic-rule", method = RequestMethod.POST)
    @ResponseBody
    public void modifyDynamicRuleStatus(@RequestParam("dynamicRuleId") int dynamicRuleId) {
        dynamicRuleService.deleteDynamicRule(dynamicRuleId);
    }

    /* ================== Search Templates ================== */
    @SmcSecurity(securityFunction = SecurityFunction.SEARCH_TEMPLATES)
    @RequestMapping("get-search-template-modal-content")
    @ResponseBody
    public ModelAndView getSearchTemplateModalContent(@RequestParam(value = "templateId") int templateId) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/search-template-modal-content");

        SearchTemplate searchTemplate = searchTemplateService.getSearchTemplate(templateId);

        mav.addObject("searchTemplate", searchTemplate);

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.SEARCH_TEMPLATES)
    @RequestMapping("update-search-template")
    @ResponseBody
    public SearchTemplate updateSearchTemplate(SearchTemplateForm searchTemplateForm) {
        searchTemplateService.updateSearchTemplate(searchTemplateForm);

        if (searchTemplateForm.getDefaultForTab() != null) {
            if (searchTemplateForm.getDefaultForTab().equals("YES"))
                tabService.updateDefaultTemplate(searchTemplateForm);
        }

        SearchTemplate searchTemplate = searchTemplateService.getSearchTemplate(searchTemplateForm.getTemplateId());

        return searchTemplate;
    }

    @SmcSecurity(securityFunction = SecurityFunction.SEARCH_TEMPLATES)
    @RequestMapping("is-search-template-name-available")
    @ResponseBody
    public void isSearchTemplateNameAvailable(@RequestParam("templateName") String templateName, @RequestParam("templateId") int templateId) {
        if (searchTemplateService.doesSearchTemplateNameExist(templateName, templateId)) {
            String errorMessage = "A search template already exists with the name " + templateName + ".";
            throw new TemplateNameAlreadyExistsException(errorMessage);
        }
    }

    /* ================== Alerts ================== */
    @SmcSecurity(securityFunction = SecurityFunction.ALERT_MANAGEMENT)
    @RequestMapping("/get-search-templates")
    @ResponseBody
    public ModelAndView getSearchTemplates() {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-alert-detail-modal");

        // Load the Alerts and Alert Headers for the datatable.
        mav.addObject("templates", alertService.getAllTemplateNames());

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.ALERT_MANAGEMENT)
    @RequestMapping("/update-alert-header")
    @ResponseBody
    public void updateAlertHeader(Alert alert) {
        // Update the Alert Header based on information provided by the user.
        alertService.modifyAlertHeader(alert);
    }

    @SmcSecurity(securityFunction = SecurityFunction.ALERT_MANAGEMENT)
    @RequestMapping("/update-alert-detail")
    @ResponseBody
    public void updateAlertDetail(Alert alert) {
        // Update the Alert Header based on information provided by the user.
        alertService.modifyAlertDetail(alert);
    }

    /* ================== Unit Exceptions ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("get-delete-unit-exception-modal")
    @ResponseBody
    public ModelAndView getUnitExceptionsDeleteModal(@RequestParam(value = "exceptionId") int exceptionId) {

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-unit-exception-modal");
        UnitException exception = exceptionService.getUnitException(exceptionId);
        List<String> poGroup = exceptionService.splitGroup(exception.getPoGroup());
        mav.addObject("poGroup", poGroup);
        mav.addObject("exception", exception);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("delete-unit-exception")
    @ResponseBody
    public void deleteUnitException(@RequestParam(value = "exceptionId") int exceptionId) {

        LOGGER.error("deleteUnitException is used!!!! :)");

        exceptionService.deleteUnitException(exceptionId);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-unit-exceptions-edit-modal")
    @ResponseBody
    public ModelAndView getUnitExceptionsEditModal(@RequestParam(value = "exceptionId") int exceptionId) {

        LOGGER.error("getUnitExceptionsEditModal is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-unit-exceptions-modal");

        UnitException exception = exceptionService.getUnitException(exceptionId);
        List<String> poGroup = exceptionService.splitGroup(exception.getPoGroup());
        mav.addObject("poGroup", poGroup);
        mav.addObject("exception", exception);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("edit-unit-exception")
    @ResponseBody
    public void editUnitException(@RequestParam(value = "exceptionId") int exceptionId, @RequestParam(value = "provider") String provider, @RequestParam(value = "subProvider") String subProvider, @RequestParam(value = "globalFlag") boolean globalFlag) {

        LOGGER.error("editUnitException is used!!!! :)");

        if (globalFlag) {
            // get Unit Exception by passed id, delete unit exception, then add new global exception
            UnitException exception = exceptionService.getUnitException(exceptionId);
            exceptionService.deleteUnitException(exceptionId);
            exceptionService.addGlobalException(exception);
        } else {

            exceptionService.modifyUnitException(exceptionId, provider, subProvider);
        }
    }

    /* ================== Delays ================== */
    // TODO SMCSEC is this even used?????
    // TODO SMCSEC is this even used?????
    // TODO SMCSEC is this even used?????
    // TODO SMCSEC is this even used?????

    // TODO SMCSEC is this even used?????

    // TODO SMCSEC is this even used?????



    // TODO SMCSEC is this even used?????

    // TODO SMCSEC is this even used?????


    // TODO SMCSEC is this even used?????
    // TODO SMCSEC is this even used?????

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-add-delay-type-modal-page")
    @ResponseBody
    public ModelAndView getAddDelayTypeModal() {
        LOGGER.error("getAddDelayTypeModal is used!!!! :)");
        return new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-type-modal");
    }

    /* ================== Terms And Conditions ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("update-t-and-c-frequency")
    @ResponseBody
    public void updateTermsAndConditionsFrequency(@RequestParam(value = "frequencyDays") String frequencyDays) {
        LOGGER.error("updateTermsAndConditionsFrequency is used!!!! :)");
        try {
            Integer.parseInt(frequencyDays);
        } catch (Exception e) {
            // Frequency was not a number, what to do here?
        	LOGGER.debug(e);

            return;
        }

        termsAndConditionsService.updateTermsAndConditionsFrequency(frequencyDays);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TC)
    @RequestMapping("view-t-and-c")
    @ResponseBody
    public String viewTermsAndConditions(@RequestParam(value = "versionNumber") int versionNumber) {
        return termsAndConditionsService.getTermsAndConditionsText(versionNumber);
    }

    /* ================== Get Rule Association model ================== */
    @RequestMapping(value = "/get-rule-association-modal-data", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getRuleAssociationModalData(@ModelAttribute("componentRule") ComponentRuleAssociation componentRule, @RequestParam("componentId") int componentId, @RequestParam("componentVisibleId") int componentVisibleId, @RequestParam(value = "viewMode") String viewMode, @RequestParam(value = "displayName") String displayName) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/add-rule-association-modal");
        componentRule.setRule(loadsheetManagementService.getComponentVisibilityRules(componentVisibleId));
        componentRule.setComponentVisibilityId(componentVisibleId);
        componentRule.setDisplayName(displayName);
        mav.addObject("rules", loadsheetManagementService.getComponentRules());
        mav.addObject("componentRule", componentRule);
        mav.addObject("viewMode", viewMode);

        // Adding details to session for create rule back button
        AppConfigSessionData appConfigData = sessionBean.getAppConfigSessionData();
        LoadSheetCategoryDetails catDetails = appConfigData.getCategoryDetails();
        if (catDetails != null)
        	catDetails.updateComponentVisibleId(componentId, componentVisibleId);

        return mav;
    }

    /* ================== Save Rule Association ================== */
    @RequestMapping(value = "/save-rule-association-modal-data", method = RequestMethod.POST)
    @ResponseBody
    public void saveRuleAssociationModalData(@ModelAttribute("componentRule") ComponentRuleAssociation componentRule, HttpServletResponse response) throws Exception {
    	UserContext userContext = sessionBean.getUserContext();
        String userSSO = userContext.getUserSSO();
        componentRule.setCreatedBy(userSSO);
        try {
            loadsheetManagementService.saveComponentRules(componentRule);
        } catch (Exception e) {
        	LOGGER.info(e);
            CommonUtils.getCommonErrorAjaxResponse(response, "Error ocuured while adding the rules, please contact system admin.");
        }
    }

    /* =========================DELETE Rule ======================== */
    @RequestMapping(value = "/delete-rule", method = RequestMethod.POST)
    @ResponseBody
    public void deleteTheRuleDetails(@RequestParam("ruleId") int ruleId, HttpServletResponse response) throws Exception {

        try {
            loadsheetManagementService.DeleteRuleDetails(ruleId);
        } catch (Exception e) {
        	LOGGER.info(e);
            CommonUtils.getCommonErrorAjaxResponse(response, "");
        }
    }

    /* ========== Check For Unique rule Name =============== */
    @RequestMapping(value = "/check-unique-rule-name", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkForUniqueRuleName(@RequestParam("ruleName") String ruleName, @RequestParam("ruleId") int ruleId) {

        return loadsheetManagementService.checkForUniqueRuleName(ruleName, ruleId);
    }

    /* =========================DELETE Rule ======================== */
    @RequestMapping(value = "/delete-sequence", method = RequestMethod.POST)
    @ResponseBody
    public void deleteSequence(@RequestParam("sequenceId") int sequenceId, HttpServletResponse response) throws Exception {

        try {
            loadsheetManagementService.deleteLoadsheetSequence(sequenceId);
        } catch (Exception e) {
        	LOGGER.info(e);
            CommonUtils.getCommonErrorAjaxResponse(response, "");
        }
    }

    /* ========== Check For Unique sequence Name =============== */
    @RequestMapping(value = "/check-unique-name", method = RequestMethod.POST)
    @ResponseBody
    public int checkForUniqueName(@RequestParam("seqName") String seqName, @RequestParam("seqId") int seqId, @RequestParam("category") String category, @RequestParam("type") String type, @RequestParam("mfr") String mfr) {
        int UniqueStatus = 0;
        boolean status = loadsheetManagementService.checkForUniqueSequenceName(seqName, seqId);
        if (status) {
            if (StringUtils.isNotBlank(category)) {
                int count = loadsheetManagementService.checkForUniqueSequence(category, type, mfr, seqId);
                if (count > 0) {
                    UniqueStatus = 2; // duplicate sequence for category,type and mfr.
                } else {
                    UniqueStatus = 0; // sequence is unique.
                }
            }
            return UniqueStatus;
        } else {
            UniqueStatus = 1;
            return UniqueStatus; // duplicate sequence name.
        }
    }

}
