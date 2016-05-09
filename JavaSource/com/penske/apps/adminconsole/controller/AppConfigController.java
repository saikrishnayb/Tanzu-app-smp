package com.penske.apps.adminconsole.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.UnitException;
import com.penske.apps.adminconsole.service.AlertService;
import com.penske.apps.adminconsole.service.DefaultSubjectService;
import com.penske.apps.adminconsole.service.DelayService;
import com.penske.apps.adminconsole.service.DynamicRuleService;
import com.penske.apps.adminconsole.service.ExceptionService;
import com.penske.apps.adminconsole.service.NotificationService;
import com.penske.apps.adminconsole.service.SearchTemplateService;
import com.penske.apps.adminconsole.service.TabService;
import com.penske.apps.adminconsole.service.TermsAndConditionsService;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;


/**
 * Controller handling all mapping and functionality for the Admin Console App Config sub tab
 * @author erik.munoz 600139451
 * @author mark.weaver 600144069
 * @author michael.leis 600132441 (Delay Management, Delay Types, Delay Reasons, Global Exceptions, Unit Exceptions)
 */
@DefaultController
@RequestMapping("/admin-console/app-config")
public class AppConfigController {
	
	@Autowired
	private ExceptionService exceptionService;
	
	@Autowired
	private DelayService delayService;
	
	@Autowired
	private DefaultSubjectService subjectService;
	
	@Autowired 
	private NotificationService notificationService;
	
	@Autowired
	private DynamicRuleService dynamicRuleService;

	@Autowired 
	private SearchTemplateService searchTemplateService;
	
	@Autowired
	private TabService tabService;
	
	@Autowired 
	private AlertService alertService;
	
	@Autowired
	private TermsAndConditionsService termsAndConditionsService;

	/* ================== Subject Management ================== */
	@RequestMapping(value={"/subject-management"})
	public ModelAndView getSubjectManagementPage() {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/subject-management");
		
		mav.addObject("subjects", subjectService.getAllSubjects());
		
		return mav;
	}

	/* ================== Notifications ================== */
	@RequestMapping("/notifications")
	public ModelAndView getNotificationsPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/notifications");
		List<Notification> notificationList = notificationService.getNotifications();
		
		for (Notification notification : notificationList) {
			notificationService.getSortedNotificationParties(notification);
		}
		
		mav.addObject("notificationsList", notificationList);
		
		return mav;
	}
	
	/* ================== Dynamic Rules ================== */
	@RequestMapping("/dynamic-rules")
	public ModelAndView getDynamicRulesPage(HttpSession session){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/dynamic-rules");
		
		mav.addObject("activeDynamicRules", dynamicRuleService.getAllDynamicRulesByStatus("A"));
		mav.addObject("inactiveDynamicRules", dynamicRuleService.getAllDynamicRulesByStatus("I"));
		mav.addObject("corpCodes", dynamicRuleService.getAllCorpCodes());
		mav.addObject("makes", dynamicRuleService.getAllVehicleMakes());
		mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.DYNAMICRULE, session));
		return mav;
	}
	
	/* ================== Search Templates ================== */
	@RequestMapping("/search-template-management")
	public ModelAndView getDefaultTemplatePage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/search-template-management");
		
		mav.addObject("searchTemplates", searchTemplateService.getAllSearchTemplates());
		
		return mav;
	}
	
	/* ===================== Alerts ===================== */
	@RequestMapping("/alerts")
	public ModelAndView getHoverOverHelpPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/alerts");
		
		// Load the Alerts and Alert Headers for the data-table.
		mav.addObject("alerts", alertService.getAllAlertsAndHeaders());
		
		return mav;
	}
	
	/* ================== Global Exceptions ================== */
	@RequestMapping("/global-exceptions")
	public ModelAndView getGlobalExceptionsPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/global-exceptions");
		List<GlobalException> exceptions = exceptionService.getGlobalExceptions();
		
		mav.addObject("exceptions", exceptions);
		
		return mav;
	}
	
	/* ================== Unit Exceptions ================== */
	@RequestMapping("/unit-exceptions")
	public ModelAndView getUnitExcpetionsPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/unit-exceptions");
		List<UnitException> exceptions = exceptionService.getUnitExceptions();
		
		mav.addObject("exceptions", exceptions);
//		Commented code here was used to test the Error Page only
//		String x = null;
//		x.equals("cool");
		
		return mav;
	}
	
	/* ================== Delay ================== */
	@RequestMapping("/delay-management")
	public ModelAndView getDelayManagementPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-management");
		
		mav.addObject("delays", delayService.getDelays());
		
		return mav;
	}

	/* ================== Delay Reasons ================== */
	@RequestMapping("/delay-reason-types")
	public ModelAndView getReasonTypesPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-reason-types");
		
		mav.addObject("types", delayService.getTypes());
		
		return mav;
	}
	
	@RequestMapping("/delay-reason-codes")
	public ModelAndView getReasonCodesPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-reason-codes");
		
		mav.addObject("delays", delayService.getAssociations());

		return mav;
	}

	/* ================== Terms And Conditions ================== */
	@RequestMapping("/terms-and-conditions")
	public ModelAndView getTermsAndConditionsPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/terms-and-conditions");
		
		mav.addObject("tandcList", termsAndConditionsService.getAllTermsAndConditions());
		mav.addObject("tandcFrequency", termsAndConditionsService.getTermsAndConditionsFrequency());
		
		return mav;
	}
}
