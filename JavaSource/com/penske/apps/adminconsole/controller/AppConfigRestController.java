package com.penske.apps.adminconsole.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.DynamicRulePriorityException;
import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.DelayModel;
import com.penske.apps.adminconsole.model.DelayPoModel;
import com.penske.apps.adminconsole.model.DelayReasonModel;
import com.penske.apps.adminconsole.model.DelayTypeModel;
import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.HeaderUser;
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
	
	/* ================== Subject Management ================== */
	@RequestMapping(value="/modify-subject")
	@ResponseBody
	public void modifySubject(Subject subject) {
		subjectService.modifySubject(subject);
	}
	
	@RequestMapping(value="/modify-subject-status")
	@ResponseBody
	public void modifySubjectStatus(@RequestParam(value="subjectId") int subjectId) {
		subjectService.modifySubjectStatus(subjectId);
	}
	
	@RequestMapping(value="/add-subject")
	@ResponseBody
	public void addSubject(Subject subject) {
		subjectService.addSubject(subject);
	}
	
	/* ================== Notifications ================== */
	@RequestMapping("get-notification-modal-content")
	@ResponseBody
	public ModelAndView getNotificationModalContent(@RequestParam(value="notificationId") int notificationId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/notification-modal-content");
		
		Notification notification = notificationService.getNotification(notificationId);
		notificationService.getSortedNotificationParties(notification);
		mav.addObject("notification", notification);
		
		List<String> userEmails = notificationService.getUserEmails();
		mav.addObject("userEmails", userEmails);
		
		return mav;
	}
	
	@RequestMapping("get-user-emails")
	@ResponseBody
	public String getUserEmails() {
		StringBuilder emails = new StringBuilder();
		List<String> userEmails = notificationService.getUserEmails();
		
		if (userEmails == null) {
			//List of user emails was null, what to do here?
			
			return null;
		}
		
		for (String email : userEmails) {
			emails.append(email + ";");
		}
		
		return emails.toString();
	}
	
	@RequestMapping("update-notification")
	@ResponseBody
	public Notification updateNotification(NotificationForm notificationForm) {
		notificationService.updateNotification(notificationForm);
		notificationService.updateNotificationParty(notificationForm);
		
		Notification notification = notificationService.getNotification(notificationForm.getNotificationId());
		notificationService.getSortedNotificationParties(notification);
		
		return notification;
	}
	
	/* ================== Global Exceptions ================== */
	@RequestMapping("get-global-exceptions-edit-modal")
	@ResponseBody
	public ModelAndView getGlobalExceptionsEditModal(@RequestParam(value="exceptionId") int exceptionId){
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-global-exceptions-modal");
		
		GlobalException exception = exceptionService.getException(exceptionId);
		List<String> poGroup = exceptionService.splitGroup( exception.getPoGroup() );
		mav.addObject("poGroup", poGroup);
		mav.addObject("exception", exception);
		return mav;
	}
	
	@RequestMapping("get-sub-groups")
	@ResponseBody
	public List<String> getSubGroups(@RequestParam(value="selectedOption") String selectedOption) {
		
		List<String> subGroups = exceptionService.getSubGroups(selectedOption);
		return subGroups;
	}
	
	@RequestMapping("edit-global-exception")
	@ResponseBody
	public void modifyGlobalException(@RequestParam(value="exceptionId") int exceptionId,@RequestParam(value="provider") String provider,@RequestParam(value="subProvider") String subProvider) {
		
		exceptionService.modifyGlobalException(exceptionId, provider, subProvider);
	}
	
	@RequestMapping("get-global-exceptions-delete-modal")
	@ResponseBody
	public ModelAndView getGlobalExceptionsDeleteModal(@RequestParam(value="exceptionId") int exceptionId) {
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-global-exception-modal");
		
		GlobalException exception = exceptionService.getException(exceptionId);
		List<String> poGroup = exceptionService.splitGroup( exception.getPoGroup() );
		mav.addObject("poGroup", poGroup);
		mav.addObject("exception", exception);
		return mav;
	}
	
	@RequestMapping("delete-global-exception")
	@ResponseBody
	public void deleteGlobalException(@RequestParam(value="exceptionId")  int exceptionId){
		
		exceptionService.deleteGlobalException(exceptionId);
	}
	
	/* ================== Dynamic Rules ================== */
	@RequestMapping(value="/get-rule-modal-data", method=  RequestMethod.POST)
	@ResponseBody
	public ModelAndView getModalData(@RequestParam("make") String make, @RequestParam("modalName") String modalName,
			@RequestParam("status") String status) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/" + modalName + "-rule-modal");
		
		mav.addObject("corpCodes", dynamicRuleService.getAllCorpCodes());
		mav.addObject("makes", dynamicRuleService.getAllVehicleMakes());
		mav.addObject("statusValues", dynamicRuleService.getAvailableStatus());
		
		if (make.length() > 0) {
			mav.addObject("models", dynamicRuleService.getVehicleModelsByMake(make));
		}
		//if(status !=null && !status.isEmpty() && "Inactive".equalsIgnoreCase(status.trim())){
			mav.addObject("maxPriority",dynamicRuleService.getMaxPriority());
		//}
		return mav;
	}
	
	@RequestMapping(value="/get-models-by-make",method= RequestMethod.POST)
	@ResponseBody
	public ModelAndView getVehicleModelsByMake(@RequestParam("make") String make, @RequestParam("modalName") String modalName) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/" + modalName + "-rule-modal");
		
		mav.addObject("models", dynamicRuleService.getVehicleModelsByMake(make));
		
		return mav;
	}
	
	@RequestMapping(value="/add-dynamic-rule",method = RequestMethod.POST)
	@ResponseBody
	public void addDynamicRule(DynamicRule rule,HttpSession session, HttpServletResponse response){
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		rule.setCreatedBy(currentUser.getSso());
		rule.setModifiedBy(currentUser.getSso());
		try{
			dynamicRuleService.addDynamicRule(rule);
		}catch(DynamicRulePriorityException dpe){
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, dpe.getErrorMessage());
				  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			        response.getWriter().write(dpe.getErrorMessage());
			        response.flushBuffer();
			       // throw dpe;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/modify-dynamic-rule",method = RequestMethod.POST)
	@ResponseBody
	public void modifyDynamicRule(DynamicRule rule, HttpServletResponse response,HttpSession session){
	try{
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		rule.setModifiedBy(currentUser.getSso());
		dynamicRuleService.modifyDynamicRule(rule);
	}catch(DynamicRulePriorityException dpe){
		try {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, dpe.getErrorMessage());
			  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        response.getWriter().write(dpe.getErrorMessage());
		        response.flushBuffer();
		       // throw dpe;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	
	@RequestMapping(value="/delete-dynamic-rule",method = RequestMethod.POST)
	@ResponseBody
	public void modifyDynamicRuleStatus(@RequestParam("dynamicRuleId") int dynamicRuleId,HttpSession session){
		HeaderUser currentUser = (HeaderUser) session.getAttribute("currentUser");
		
		//dynamicRuleService.modifyDynamicRuleStatus(dynamicRuleId, priority,currentUser.getSso());
		dynamicRuleService.deleteDynamicRule(dynamicRuleId);
	}
	
	/* ================== Search Templates ================== */
	@RequestMapping("get-search-template-modal-content")
	@ResponseBody
	public ModelAndView getSearchTemplateModalContent(@RequestParam(value="templateId") int templateId) {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/search-template-modal-content");
		
		SearchTemplate searchTemplate = searchTemplateService.getSearchTemplate(templateId);
		
		mav.addObject("searchTemplate", searchTemplate);
		
		return mav;
	}
	
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
	
	@RequestMapping("is-search-template-name-available")
	@ResponseBody
	public void isSearchTemplateNameAvailable(@RequestParam("templateName")String templateName, @RequestParam("templateId") int templateId) {
		if (searchTemplateService.doesSearchTemplateNameExist(templateName, templateId)) {
			String errorMessage = "A search template already exists with the name " + templateName + ".";
			throw new TemplateNameAlreadyExistsException(errorMessage);
		}
	}
	
	@RequestMapping("/get-search-templates")
	@ResponseBody
	public ModelAndView getSearchTemplates() {
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-alert-detail-modal");
		
		// Load the Alerts and Alert Headers for the datatable.
		mav.addObject("templates", alertService.getAllTemplateNames());
		
		return mav;
	}
	
	/* ================== Alerts ================== */
	@RequestMapping("/update-alert-header")
	@ResponseBody
	public void updateAlertHeader(Alert alert) {
		// Update the Alert Header based on information provided by the user.
		alertService.modifyAlertHeader(alert);
	}
	
	@RequestMapping("/update-alert-detail")
	@ResponseBody
	public void updateAlertDetail(Alert alert) {
		// Update the Alert Header based on information provided by the user.
		alertService.modifyAlertDetail(alert);
	}
	
	/* ================== Unit Exceptions ================== */
	@RequestMapping("get-delete-unit-exception-modal")
	@ResponseBody
	public ModelAndView getUnitExceptionsDeleteModal(@RequestParam(value="exceptionId") int exceptionId) {
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-unit-exception-modal");
		UnitException exception = exceptionService.getUnitException(exceptionId);
		List<String> poGroup = exceptionService.splitGroup( exception.getPoGroup() );
		mav.addObject("poGroup", poGroup);
		mav.addObject("exception", exception);
		return mav;
	}
	
	@RequestMapping("delete-unit-exception")
	@ResponseBody
	public void deleteUnitException(int exceptionId) {
		exceptionService.deleteUnitException(exceptionId);
	}
	
	@RequestMapping("get-unit-exceptions-edit-modal")
	@ResponseBody
	public ModelAndView getUnitExceptionsEditModal(@RequestParam(value="exceptionId") int exceptionId) {
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-unit-exceptions-modal");
		
		UnitException exception = exceptionService.getUnitException(exceptionId);
		List<String> poGroup = exceptionService.splitGroup( exception.getPoGroup() );
		mav.addObject("poGroup", poGroup);
		mav.addObject("exception", exception);
		return mav;
	}
	
	@RequestMapping("edit-unit-exception")
	@ResponseBody
	public void editUnitException(@RequestParam(value="exceptionId") int exceptionId,@RequestParam(value="provider") String provider,@RequestParam(value="subProvider") String subProvider,@RequestParam(value="globalFlag") boolean globalFlag){
		
		if(globalFlag){
			// get Unit Exception by passed id, delete unit exception, then add new global exception
			UnitException exception = exceptionService.getUnitException(exceptionId);
			exceptionService.deleteUnitException(exceptionId);
			exceptionService.addGlobalException(exception);
		}
		else{
			
			exceptionService.modifyUnitException(exceptionId, provider, subProvider);
		}
	}
	
	/* ================== Delays ================== */
	@RequestMapping("get-add-delay-modal-content")
	@ResponseBody
	public ModelAndView getAddDelayModalContent() {
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-modal-content");
		List<DelayPoModel> POs = delayService.getPOs();
		List<DelayReasonModel> reasons = delayService.getReasons();
		List<DelayTypeModel> types = delayService.getTypes();
		List<String> dateTypes = delayService.getDateTypes();
		mav.addObject("dateTypes", dateTypes);
		mav.addObject("POs", POs);
		mav.addObject("reasons", reasons);
		mav.addObject("types", types);
		return mav;
	}
	
	@RequestMapping("add-new-delay")
	@ResponseBody
	public int addDelay(DelayModel delay,HttpServletResponse response) throws Exception{
	
		// check if delay with these values already exists
		boolean alreadyExists = delayService.checkDelay(delay);
		if(alreadyExists){
			
			//String errorMessage = "Delay with these credentials already exists.";
			//throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"Delay with these credentials already exists.");
			return 0;
		}else{
			// add the delay
			delayService.addDelay(delay);
			int delayId = delayService.getId( delay.getDateTypeId(), delay.getPoCategoryId(), delay.getDelayTypeId(), delay.getDelayReasonId() );
			return delayId;
		}
	}
	
	@RequestMapping("get-associated-reasons")
	@ResponseBody
	public List<DelayReasonModel> getAssociatedReasons(@RequestParam(value="typeId") int typeId){
		
		List<DelayReasonModel> reasons = delayService.getAssocReasons(typeId);
		return reasons;
	}
	@RequestMapping("get-edit-delay-modal-content")
	@ResponseBody
	public ModelAndView getEditDelayModalContent(DelayModel delay){
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-modal-content");
		List<DelayPoModel> POs = delayService.getPOs();
		int delayTypeId = delayService.getTypeId( delay.getDelayId() );
		List<DelayReasonModel> reasons = delayService.getAssocReasons( delayTypeId );
		List<DelayTypeModel> types = delayService.getTypes();
		List<String> dateTypes = delayService.getDateTypes();
		mav.addObject("dateTypes", dateTypes);
		mav.addObject("reasons", reasons);
		mav.addObject("types", types);
		mav.addObject("POs", POs);
		mav.addObject("delay", delay);
		return mav;
	}
	
	@RequestMapping(value = "edit-delay")
	@ResponseBody
	public void editDelay(DelayModel delay,HttpServletResponse response) throws Exception{
		
		boolean alreadyExists = delayService.checkDelay(delay);
		if(alreadyExists){
			
			//String errorMessage = "Delay with these credentials already exists.";
			//throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"Delay with these credentials already exists.");
		}else{
		// modify the delay if one doesn't already exist with the passed credentials
			delayService.modifyDelay(delay);
		}
	}

	@RequestMapping("get-delete-delay-modal-content")
	@ResponseBody
	public ModelAndView getDeleteDelayModalContent(DelayModel delay){
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-modal-content");
		mav.addObject("delay", delay);
		return mav;
	}
	
	@RequestMapping("delete-delay")
	@ResponseBody
	public void deleteDelay(int delayId){
		
		delayService.deleteDelay( delayId );
	}
	
	@RequestMapping("get-delete-delay-type-modal-page")
	@ResponseBody
	public ModelAndView getDeleteDelayTypeModalPage(DelayTypeModel type){
		
		// This needs to have functionality implemented assuming that there will be a multiple
		// delete option in the future.
		List<DelayTypeModel> typeList = new ArrayList<DelayTypeModel>();
		typeList.add(type);
		return new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-type-modal", "type", typeList);
	}
	
	@RequestMapping("delete-delay-type")
	@ResponseBody
	public void deleteDelayType(@RequestParam(value="typeId") int typeId){
		
		// delete type association, any delays with this reason, and finally the reason itself
		delayService.deleteDelayType(typeId);
	}
	
	@RequestMapping(value = "get-edit-delay-type-modal-page")
	@ResponseBody
	public ModelAndView getEditDelayTypeModalPage(DelayTypeModel type){
		
		return new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-type-modal", "type", type);
	}
	
	@RequestMapping("edit-delay-type")
	@ResponseBody
	public void editDelayType(DelayTypeModel delayType,HttpServletResponse response) throws Exception{
		
		boolean result = delayService.checkForExistingType( delayType.getDelayType() );
		if(result){
			
			//String errorMessage = "A Delay Type with this name already exists.";
			//throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"A Delay Type with this name already exists.");
		}else{
			delayService.modifyDelayType(delayType);
		}
	}

	@RequestMapping(value = "get-delete-delay-reason-modal-page")
	@ResponseBody
	public ModelAndView getDeleteDelayReasonModalPage(DelayReasonModel reason){
		
		// this is for the possibility of future functionality which will allow the user to delete
		// more than one row from the table at a time
		List<DelayReasonModel> reasons = new ArrayList<DelayReasonModel>();
		reasons.add(reason);
		return new ModelAndView("/jsp-fragment/admin-console/app-config/delete-delay-reason-modal", "reason", reasons);
	}
	
	@RequestMapping("delete-delay-reason")
	@ResponseBody
	public void deleteDelayReason(@RequestParam(value="reasonId") int reasonId,@RequestParam(value="typeId") int typeId){
		
		delayService.deleteDelayReason(reasonId, typeId);
	}
	
	@RequestMapping(value = "get-edit-delay-reason-modal-page")
	@ResponseBody
	public ModelAndView getEditDelayReasonModalPage(DelayReasonModel reason){
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/app-config/edit-delay-reason-modal");
		// need to get a list of all the types in order to populate the Delay Type dropdown
		List<DelayTypeModel> types = delayService.getTypes();
		mav.addObject("types", types);
		mav.addObject("reason", reason);
		return mav;
	}
	
	@RequestMapping("edit-delay-reason")
	@ResponseBody
	public void editDelayReason(@RequestParam(value="oldTypeId") int oldTypeId,@RequestParam(value="reasonId") int reasonId,@RequestParam(value="oldReasonName") String oldReasonName,
			@RequestParam(value="newTypeId") int newTypeId,@RequestParam(value="newReasonName") String newReasonName,HttpServletResponse response) throws Exception{
		
		DelayReasonModel newReason = new DelayReasonModel();
		newReason.setTypeId(newTypeId);
		/* 
		 * 	This if is necessary in order to prevent the program from throwing an error
 			when the user attempts to edit the reason where the type is the same as the original.
 			example:  old TypeId was 5, new TypeId is still 5.  Old name is "X", new name is "Y".
			Because the reasonId does not change when the name is changed, the program
		 	would throw an error whenever the user attempted to use the same Delay Type
			and only change the Delay Reason's name.  This is a workaround for that instance.
			The reasonId must still be set back to the passed in reasonId parameter right before
			the final modification is done below, though.
		 */
		if(oldReasonName.equals(newReasonName)) newReason.setReasonId(reasonId);
		else newReason.setReasonId(-1);
		
		newReason.setDelayReason(newReasonName);
		
		DelayReasonModel oldReason = new DelayReasonModel();
		oldReason.setTypeId(oldTypeId);
		oldReason.setReasonId(reasonId);
		oldReason.setDelayReason(oldReasonName);
		// check if new Reason exists in tables
		boolean result = delayService.checkAssociation(newReason);
		if(result){
			
			//String errorMessage = "Delay Reason already linked to selected Delay Type.";
		//	throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"Delay Reason already linked to selected Delay Type.");
		}else{
			// check if the delay reason already exists
			result = delayService.checkReason(newReason.getDelayReason());
			if(result){
				// if so, tell user it already exists
				//String errorMessage = "Delay Reason with this name already exists.";
			//	throw new DelayReasonAlreadyExistsException(errorMessage);
				CommonUtils.getCommonErrorAjaxResponse(response,"Delay Reason with this name already exists.");
			}else{
				// modify the old reason with the new reason's info
				// if we made it this far, it's necessary to reset the newReason's reasonId
				// to what was originally passed into the method in order to make the modifyReason
				// SQL statement work.  
				// old reason is sent so we know where/what to modify with the new data
				newReason.setReasonId(reasonId);
				delayService.modifyReason(newReason, oldReason);
			}
		}
	}
	
	@RequestMapping(value = "get-add-delay-reason-modal-page")
	@ResponseBody
	public ModelAndView getAddDelayReasonModalPage(){
		
		List<DelayTypeModel> types = delayService.getTypes();
		return new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-reason-modal", "types", types);
	}
	
	@RequestMapping(value = "add-delay-reason")
	@ResponseBody
	public DelayReasonModel addDelayReason(@RequestParam("typeId")int typeId, @RequestParam("reasonName")String reasonName,HttpServletResponse response) throws Exception{
		
		// check if Reason Name already exists
		boolean result = delayService.checkReason(reasonName);
		if(result){
			
			// if so, tell user it already exists
			//	String errorMessage = "Delay Reason with this name already exists.";
			//	throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"");
			return null;
		}else{
			DelayReasonModel reason = delayService.addDelayReason(typeId, reasonName);
			return reason;
		}
	}

	@RequestMapping(value = "add-new-delay-type")
	@ResponseBody
	public DelayTypeModel addDelayType(@RequestParam(value="delayType") String delayType,HttpServletResponse response) throws Exception{
		
		boolean result = delayService.checkForExistingType( delayType );
		if(result){

			//String errorMessage = "A Delay Type with this name already exists.";
			//throw new DelayReasonAlreadyExistsException(errorMessage);
			CommonUtils.getCommonErrorAjaxResponse(response,"A Delay Type with this name already exists.");
			return null;
		}else{
			// add delay type to database because no match was found.
			delayService.addDelayType( delayType );
			DelayTypeModel type = delayService.getDelayType( delayType );
			return type;
		}
	}

	@RequestMapping("get-add-delay-type-modal-page")
	@ResponseBody
	public ModelAndView getAddDelayTypeModal() {
		
		return new ModelAndView("/jsp-fragment/admin-console/app-config/add-delay-type-modal");
	}
	
	/* ================== Terms And Conditions ================== */
	@RequestMapping("update-t-and-c-frequency")
	@ResponseBody
	public void updateTermsAndConditionsFrequency(@RequestParam(value="frequencyDays") String frequency) {
		try {
			Integer.parseInt(frequency);
		} 
		catch (Exception e) {
			//Frequency was not a number, what to do here?
			
			
			return;
		}
		
		termsAndConditionsService.updateTermsAndConditionsFrequency(frequency);
	}
	
	@RequestMapping("view-t-and-c")
	@ResponseBody
	public String viewTermsAndConditions(@RequestParam(value="versionNumber") int versionNumber) {
		return termsAndConditionsService.getTermsAndConditionsText(versionNumber);
	}
}
