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

    private static final Logger LOGGER = Logger.getLogger(AppConfigRestController.class);

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
    @RequestMapping("get-global-exceptions-edit-modal")
    @ResponseBody
    public ModelAndView getGlobalExceptionsEditModal(@RequestParam(value = "exceptionId") int exceptionId) {

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-global-exceptions-modal");
        List<GlobalException> exception = exceptionService.getException(exceptionId);
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
    public void modifyGlobalException(@RequestParam(value = "exceptionId") int exceptionId, @RequestParam(value = "providervendorId") int providervendorId, @RequestParam(value = "poCategoryAssociationId") int poCategoryAssociationId,HttpSession session) {

        HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
        String createdBy = currentUser.getSso();
        exceptionService.modifyGlobalException(exceptionId, providervendorId, poCategoryAssociationId,createdBy);
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
    public ModelAndView getModalData(@RequestParam("make") String make, @RequestParam("modalName") String modalName, @RequestParam("status") String status) {
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
    public void addDynamicRule(DynamicRule rule, HttpSession session, HttpServletResponse response) {
        HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
        rule.setCreatedBy(currentUser.getSso());
        rule.setModifiedBy(currentUser.getSso());
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
    public void modifyDynamicRule(DynamicRule rule, HttpServletResponse response, HttpSession session) {
        try {
            HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
            rule.setModifiedBy(currentUser.getSso());
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
    public void modifyDynamicRuleStatus(@RequestParam("dynamicRuleId") int dynamicRuleId, HttpSession session) {
        // HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");

        // dynamicRuleService.modifyDynamicRuleStatus(dynamicRuleId, priority,currentUser.getSso());
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
    @RequestMapping("get-add-delay-modal-content")
    @ResponseBody
    public ModelAndView getAddDelayModalContent() {

        LOGGER.error("getAddDelayModalContent is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-modal-content");
        List<DelayPoModel> POs = delayService.getPOs();
        List<DelayReasonModel> reasons = delayService.getReasons();
        List<DelayTypeModel> types = delayService.getTypes();
        List<DateType> dateTypes = delayService.getDateTypes();
        CommonUtils.sortDateType(dateTypes);
        mav.addObject("dateTypes", dateTypes);
        mav.addObject("POs", POs);
        mav.addObject("reasons", reasons);
        mav.addObject("types", types);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("add-new-delay")
    @ResponseBody
    public int addDelay(DelayModel delay, HttpServletResponse response) throws Exception {

        LOGGER.error("addDelay is used!!!! :)");

        // check if delay with these values already exists
        if (delay != null) {
            DelayTypeReason typeReason = delayService.getAssocByTypeReasonId(delay.getDelayTypeId(), delay.getDelayReasonId());
            if (typeReason != null) {
                delay.setDelayTypeReasonId(typeReason.getDelayAssocid());
                boolean alreadyExists = delayService.checkDelay(delay);
                if (alreadyExists) {
                    CommonUtils.getCommonErrorAjaxResponse(response, "Delay with these credentials already exists.");
                    return 0;
                } else {
                    // add the delay
                    delayService.addDelay(delay);
                    int delayId = delayService.getId(delay.getDateTypeId(), delay.getPoCategoryId(), delay.getDelayTypeReasonId());
                    return delayId;
                }
            } else {
                CommonUtils.getCommonErrorAjaxResponse(response, "");
                return 0;
            }
        } else {
            CommonUtils.getCommonErrorAjaxResponse(response, "");
            return 0;
        }
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-associated-reasons")
    @ResponseBody
    public List<DelayReasonModel> getAssociatedReasons(@RequestParam(value = "typeId") int typeId) {

        LOGGER.error("getAssociatedReasons is used!!!! :)");

        List<DelayReasonModel> reasons = delayService.getAssocReasons(typeId);
        return reasons;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-edit-delay-modal-content")
    @ResponseBody
    public ModelAndView getEditDelayModalContent(DelayModel delay) {

        LOGGER.error("getEditDelayModalContent is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-modal-content");
        List<DelayReasonModel> reasons = null;
        String currDateType = "";
        List<DelayPoModel> POs = delayService.getPOs();

        DelayModel delayModelDB = delayService.getTypeId(delay.getDelayId()); // GET AssocId and
        // datetype
        if (delayModelDB != null) {
            currDateType = delayModelDB.getDateType();
            // GET type and reason id from assoc table
            DelayTypeReason typeReason = delayService.getAssocTypeReasons(delayModelDB.getDelayTypeReasonId());
            if (typeReason != null) {
                reasons = delayService.getAssocReasons(typeReason.getTypeId());
            }
        }
        List<DelayTypeModel> types = delayService.getTypes();
        List<DateType> dateTypes = delayService.getDateTypes();
        CommonUtils.sortDateType(dateTypes);
        mav.addObject("dateTypes", dateTypes);
        mav.addObject("currDateType", currDateType);
        mav.addObject("reasons", reasons);
        mav.addObject("types", types);
        mav.addObject("POs", POs);
        mav.addObject("delay", delay);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "edit-delay")
    @ResponseBody
    public void editDelay(DelayModel delay, HttpServletResponse response) throws Exception {

        LOGGER.error("editDelay is used!!!! :)");

        DelayTypeReason typeReason = delayService.getAssocByTypeReasonId(delay.getDelayTypeId(), delay.getDelayReasonId());
        if (typeReason != null) {
            delay.setDelayTypeReasonId(typeReason.getDelayAssocid());
            boolean alreadyExists = delayService.checkDelayFroModify(delay);
            if (alreadyExists) {

                // String errorMessage = "Delay with these credentials already exists.";
                // throw new DelayReasonAlreadyExistsException(errorMessage);
                CommonUtils.getCommonErrorAjaxResponse(response, "Delay with these credentials already exists.");
            } else {
                // modify the delay if one doesn't already exist with the passed credentials
                delayService.modifyDelay(delay);
            }
        }
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-delete-delay-modal-content")
    @ResponseBody
    public ModelAndView getDeleteDelayModalContent(DelayModel delay) {

        LOGGER.error("getDeleteDelayModalContent is used!!!! :)");

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-modal-content");
        mav.addObject("delay", delay);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("delete-delay")
    @ResponseBody
    public void deleteDelay(@RequestParam(value = "delayId") int delayId) {
        LOGGER.error("deleteDelay is used!!!! :)");
        delayService.deleteDelay(delayId);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-delete-delay-type-modal-page")
    @ResponseBody
    public ModelAndView getDeleteDelayTypeModalPage(DelayTypeModel type) {
        LOGGER.error("getDeleteDelayTypeModalPage is used!!!! :)");
        // This needs to have functionality implemented assuming that there will be a multiple
        // delete option in the future.
        List<DelayTypeModel> typeList = new ArrayList<DelayTypeModel>();
        typeList.add(type);
        return new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-type-modal", "type", typeList);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("delete-delay-type")
    @ResponseBody
    public void deleteDelayType(@RequestParam(value = "typeId") int typeId) {
        LOGGER.error("deleteDelayType is used!!!! :)");
        // delete type association, any delays with this reason, and finally the reason itself
        delayService.deleteDelayType(typeId);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "get-edit-delay-type-modal-page")
    @ResponseBody
    public ModelAndView getEditDelayTypeModalPage(DelayTypeModel type) {
        LOGGER.error("getEditDelayTypeModalPage is used!!!! :)");
        return new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-type-modal", "type", type);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("edit-delay-type")
    @ResponseBody
    // public void editDelayType(DelayTypeModel delayType,HttpServletResponse response) throws
    // Exception{
    public void editDelayType(@RequestParam(value = "typeId") int typeId, @RequestParam(value = "oldVal") String oldVal, @RequestParam(value = "delayType") String delayType, HttpServletResponse response) throws Exception {
        LOGGER.error("editDelayType is used!!!! :)");
        DelayTypeModel delayTypeModel = new DelayTypeModel();
        delayTypeModel.setTypeId(typeId);
        delayTypeModel.setDelayType(delayType);
        if (!oldVal.equalsIgnoreCase(delayType)) {
            boolean result = delayService.checkDelayTypeExist(delayTypeModel.getDelayType());
            if (result) {

                // String errorMessage = "A Delay Type with this name already exists.";
                // throw new DelayReasonAlreadyExistsException(errorMessage);
                CommonUtils.getCommonErrorAjaxResponse(response, "A Delay Type with this name already exists.");
            } else {
                delayService.modifyDelayType(delayTypeModel);
            }
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping(value = "get-delete-delay-reason-modal-page")
    @ResponseBody
    public ModelAndView getDeleteDelayReasonModalPage(DelayReasonModel reason) {

        // this is for the possibility of future functionality which will allow the user to delete
        // more than one row from the table at a time
        List<DelayReasonModel> reasons = new ArrayList<DelayReasonModel>();
        reasons.add(reason);
        return new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-reason-modal", "reason", reasons);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "get-delete-delay-type-reason-page")
    @ResponseBody
    public ModelAndView getDeleteDelayTypeReasonModal(@RequestParam(value = "reasonVal") String reasonVal, @RequestParam(value = "typeVal") String typeVal, @RequestParam(value = "typeReasonId") int typeReasonId) {
        LOGGER.error("getDeleteDelayTypeReasonModal is used!!!! :)");
        ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-type-reason-modal");
        model.addObject("reason", reasonVal);
        model.addObject("type", typeVal);
        model.addObject("typeReasonId", typeReasonId);
        return model;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping("delete-delay-reason")
    @ResponseBody
    public void deleteDelayReason(@RequestParam(value = "reasonId") int reasonId) {

        delayService.deleteDelayReason(reasonId);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("delete-delay-type-reason")
    @ResponseBody
    public void deleteDelayTypeReason(@RequestParam(value = "assocId") int assocId) {
        LOGGER.error("deleteDelayTypeReason is used!!!! :)");
        delayService.deleteDelayTypeReason(assocId);
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping(value = "get-edit-delay-reason-modal-page")
    @ResponseBody
    public ModelAndView getEditDelayReasonModalPage(DelayReasonModel reason) {

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-reason-modal");
        // need to get a list of all the types in order to populate the Delay Type dropdown
        List<DelayTypeModel> types = delayService.getTypes();
        mav.addObject("types", types);
        mav.addObject("reason", reason);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "get-edit-delay-type-reason-page")
    @ResponseBody
    public ModelAndView getEditDelayTypeReasonModalPage(@RequestParam(value = "typeReasonId") int typeReasonId) {
        LOGGER.error("getEditDelayTypeReasonModalPage is used!!!! :)");
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-type-reason-assoc-modal");
        // need to get a list of all the types in order to populate the Delay Type dropdown
        List<DelayTypeModel> types = delayService.getTypes();
        List<DelayReasonModel> reason = delayService.getReasons();
        DelayTypeReason delayReasonModel = delayService.getAssocTypeReasons(typeReasonId);
        if (delayReasonModel != null) {
            delayReasonModel.setDelayAssocid(typeReasonId);
            mav.addObject("delayReasonModel", delayReasonModel);
            // mav.addObject("typeId", delayReasonModel.getTypeId());
            // mav.addObject("reasonId", delayReasonModel.getReasonId());
        }
        mav.addObject("types", types);
        mav.addObject("reason", reason);
        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("edit-delay-type-reason")
    @ResponseBody
    public DelayTypeReason editDelayReason(@RequestParam(value = "oldTypeId") int oldTypeId, @RequestParam(value = "newTypeId") int newTypeId, @RequestParam(value = "oldReasonId") int oldReasonId, @RequestParam(value = "newReasonId") int newReasonId, @RequestParam(value = "assocId") int assocId, HttpServletResponse response) throws Exception {
        LOGGER.error("editDelayReason is used!!!! :)");
        if ((oldTypeId != newTypeId) || (oldReasonId != newReasonId)) {
            DelayReasonModel delayReasonModel = new DelayReasonModel();
            delayReasonModel.setTypeId(newTypeId);
            delayReasonModel.setReasonId(newReasonId);
            boolean result = delayService.checkAssociation(delayReasonModel);
            if (result) {
                CommonUtils.getCommonErrorAjaxResponse(response, "");
                return null;
            } else {
                DelayTypeReason typeReason = new DelayTypeReason();
                typeReason.setTypeId(newTypeId);
                typeReason.setReasonId(newReasonId);
                typeReason.setDelayAssocid(assocId);
                return delayService.modifyDelayTypeReasonAssoc(typeReason);
            }
        }
        return null;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping("edit-delay-reason")
    @ResponseBody
    public void editDelayReason(@RequestParam(value = "reasonId") int reasonId, @RequestParam(value = "oldReasonName") String oldReasonName, @RequestParam(value = "newReasonName") String newReasonName, HttpServletResponse response) throws Exception {

        if (!oldReasonName.equalsIgnoreCase(newReasonName)) {
            // check if the delay reason already exists
            boolean result = delayService.checkReason(newReasonName);
            if (result) {
                // if so, tell user it already exists
                // String errorMessage = "Delay Reason with this name already exists.";
                // throw new DelayReasonAlreadyExistsException(errorMessage);
                CommonUtils.getCommonErrorAjaxResponse(response, "Delay Reason with this name already exists.");
            } else {

                delayService.modifyReason(newReasonName, reasonId);
            }
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping(value = "get-add-delay-reason-modal-page")
    @ResponseBody
    public ModelAndView getAddDelayReasonModalPage() {

        List<DelayTypeModel> types = delayService.getTypes();
        return new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-reason-modal", "types", types);
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "get-add-delay-type-reason-page")
    @ResponseBody
    public ModelAndView getAddDelayTypeReasonModal() {
        LOGGER.error("getAddDelayTypeReasonModal is used!!!! :)");
        ModelAndView model = new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-type-reason-assoc-modal");
        List<DelayTypeModel> types = delayService.getTypes();
        List<DelayReasonModel> reason = delayService.getReasons();
        model.addObject("types", types);
        model.addObject("reasons", reason);
        return model;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "add-delay-type-reason")
    @ResponseBody
    public DelayTypeReason addDelayTypeReason(@RequestParam("typeId") int typeId, @RequestParam("reasonId") int reasonId, HttpServletResponse response) throws Exception {
        LOGGER.error("addDelayTypeReason is used!!!! :)");
        // check if Reason Name already exists
        DelayReasonModel delayReasonModel = new DelayReasonModel();
        delayReasonModel.setTypeId(typeId);
        delayReasonModel.setReasonId(reasonId);
        boolean result = delayService.checkAssociation(delayReasonModel);
        if (result) {
            CommonUtils.getCommonErrorAjaxResponse(response, "");
            return null;
        } else {
            DelayTypeReason typeReason = delayService.addDelayTypeReason(typeId, reasonId);
            return typeReason;
        }
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping(value = "add-delay-reason")
    @ResponseBody
    public DelayReasonModel addDelayReason(@RequestParam("reasonName") String reasonName, HttpServletResponse response) throws Exception {

        // check if Reason Name already exists
        boolean result = delayService.checkReason(reasonName);
        if (result) {

            // if so, tell user it already exists
            // String errorMessage = "Delay Reason with this name already exists.";
            // throw new DelayReasonAlreadyExistsException(errorMessage);
            CommonUtils.getCommonErrorAjaxResponse(response, "");
            return null;
        } else {
            DelayReasonModel reason = delayService.addDelayReason(reasonName);
            return reason;
        }
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping(value = "add-new-delay-type")
    @ResponseBody
    public DelayTypeModel addDelayType(@RequestParam(value = "delayType") String delayType, HttpServletResponse response) throws Exception {
        LOGGER.error("addDelayType is used!!!! :)");
        boolean result = delayService.checkDelayTypeExist(delayType);
        if (result) {

            // String errorMessage = "A Delay Type with this name already exists.";
            // throw new DelayReasonAlreadyExistsException(errorMessage);
            CommonUtils.getCommonErrorAjaxResponse(response, "A Delay Type with this name already exists.");
            return null;
        } else {
            // add delay type to database because no match was found.
            delayService.addDelayType(delayType);
            DelayTypeModel type = delayService.getDelayType(delayType);
            return type;
        }
    }

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
    public ModelAndView getRuleAssociationModalData(HttpServletRequest request, @ModelAttribute("componentRule") ComponentRuleAssociation componentRule, @RequestParam("componentId") int componentId, @RequestParam("componentVisibleId") int componentVisibleId, @RequestParam(value = "viewMode") String viewMode, @RequestParam(value = "displayName") String displayName) {
        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/add-rule-association-modal");
        componentRule.setRule(loadsheetManagementService.getComponentVisibilityRules(componentVisibleId));
        componentRule.setComponentVisibilityId(componentVisibleId);
        componentRule.setDisplayName(displayName);
        mav.addObject("rules", loadsheetManagementService.getComponentRules());
        mav.addObject("componentRule", componentRule);
        mav.addObject("viewMode", viewMode);

        // Adding details to session for create rule back button
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Adding component ID and Visibility ID to session category object
            LoadSheetCategoryDetails catDetails = (LoadSheetCategoryDetails) session.getAttribute("CATEGORY_DETAILS");
            catDetails.setComponentId(componentId);
            catDetails.setVisibilityId(componentVisibleId);
            session.setAttribute("CATEGORY_DETAILS", catDetails);
        }

        return mav;
    }

    /* ================== Save Rule Association ================== */
    @RequestMapping(value = "/save-rule-association-modal-data", method = RequestMethod.POST)
    @ResponseBody
    public void saveRuleAssociationModalData(@ModelAttribute("componentRule") ComponentRuleAssociation componentRule, HttpSession session, HttpServletResponse response) throws Exception {
        HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
        componentRule.setCreatedBy(currentUser.getSso());
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
