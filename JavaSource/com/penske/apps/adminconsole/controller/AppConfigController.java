package com.penske.apps.adminconsole.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.model.ExcelUploadHandler;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.adminconsole.model.TransportUploadHandler;
import com.penske.apps.adminconsole.model.UnitException;
import com.penske.apps.adminconsole.model.VendorUploadHandler;
import com.penske.apps.adminconsole.service.AlertService;
import com.penske.apps.adminconsole.service.DefaultSubjectService;
import com.penske.apps.adminconsole.service.DelayService;
import com.penske.apps.adminconsole.service.DynamicRuleService;
import com.penske.apps.adminconsole.service.ExceptionService;
import com.penske.apps.adminconsole.service.LoadSheetManagementService;
import com.penske.apps.adminconsole.service.NotificationService;
import com.penske.apps.adminconsole.service.SearchTemplateService;
import com.penske.apps.adminconsole.service.TermsAndConditionsService;
import com.penske.apps.adminconsole.service.TransporterServiceImpl;
import com.penske.apps.adminconsole.service.VendorReportServiceImpl;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.adminconsole.model.LoadsheetManagement;

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
	private AlertService alertService;
	
	@Autowired
	private TermsAndConditionsService termsAndConditionsService;
	
	@Autowired
	private TransporterServiceImpl objTransporterService;
	
	@Autowired
	private VendorReportServiceImpl objVendorService;
	
	@Autowired
	private LoadSheetManagementService loadsheetManagementService;

	/* ================== Subject Management ================== */
	@RequestMapping(value={"/subject-management"})
	public ModelAndView getSubjectManagementPage() {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/subject-management");
		
		mav.addObject("subjects", subjectService.getAllSubjects());
		
		return mav;
	}

	
	/* ================== Excel Uploads    ================== */
	/**
	 * Mapping for the excel upload page.
	 * @param session
	 * @return
	 */
	@RequestMapping("/excelUploads")
	public ModelAndView getExcelUploadPage(HttpSession session){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/excelUploads");
		mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.UPLOAD_EXCEL, session));
		return mav;
	}
	
	/**
	 * This controller will handle uploading a transport excel file.
	 * The information will be modified and saved to the database.
	 * The savedocument call will always have false in the pilot param
	 * this is a hold over from when this functionality was in vsportal.
	 * @param file -- .xls file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ModelAndView uploadTransportExcelFile(@RequestParam(value = "file") MultipartFile file, HttpSession session, @RequestParam(value = "uploadSelect") String uploadSelect) throws Exception
	{
		UserContext userContext = (UserContext) session.getAttribute(ApplicationConstants.USER_MODEL);
		
		ModelAndView mav = new ModelAndView("/admin-console/app-config/excelUploads");
		mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.UPLOAD_EXCEL, session));
		
		//This will handle calling the different save functions.
		ExcelUploadHandler excelUploadHandler = null;
		
		//The file name is required for the save function.	
		String fileName = file.getOriginalFilename();
		
		//message to be displayed back to screen.
		String message = null;
		
		//t is for transport; v is for vendor; anything else is banned.
		if(null == uploadSelect || (!uploadSelect.equalsIgnoreCase("t") && !uploadSelect.equalsIgnoreCase("v")) )
		{
			mav.addObject("transport_message", "Selection not recognized.");
			return mav;
		}
		else if(uploadSelect.equalsIgnoreCase("t"))
		{
			excelUploadHandler = new TransportUploadHandler();
			
			//Message to be displayed back to the screen.
			message = excelUploadHandler.saveDocument(fileName, file, objTransporterService, false);
		}
		else if(uploadSelect.equalsIgnoreCase("v"))
		{
			excelUploadHandler = new VendorUploadHandler(); 
			
			//this is for use in the vendor upload.
			excelUploadHandler.setUserId(userContext.getUserSSO());
			
			//Message to be displayed back to the screen.
			message = excelUploadHandler.saveDocument(fileName, file, objVendorService, false);
		}

		//returns the message.
		mav.addObject("transport_message", message);

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
	@Deprecated
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
		
		mav.addObject("delays", delayService.getReasons());

		return mav;
	}
	
	@RequestMapping("/delay-type-reason-assoc")
	public ModelAndView getTypeReasonassocPage(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-type-reason-assoc");
		
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
	
	@RequestMapping("/loadsheet-management")
	public ModelAndView getLoadsheetManagementDetails(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-management");
		
		mav.addObject("loadsheets", loadsheetManagementService.getLoadsheetManagementDetails());

		return mav;
	}
	
	@RequestMapping("/loadsheet-rule")
	public ModelAndView getLoadsheetRuleDetails(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-rule");
		
		mav.addObject("loadsheetRules", loadsheetManagementService.getLoadsheetRuleDetails());

		return mav;
	}
	
	
	/*==============Create Rule===================*/
	@RequestMapping("/load-create-rule")
	public ModelAndView getComponents(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
		
		RuleMaster ruleMaster=new RuleMaster();
		List<RuleDefinitions> ruleDefLst=new ArrayList<RuleDefinitions>();
		RuleDefinitions ruleDef=new RuleDefinitions();
		ruleDef.setCriteriaGroup(1);
		ruleDef.setIsGroupHeader(true);
		ruleDefLst.add(ruleDef);	//Creating one empty row in create rule page
		ruleMaster.setRuleDefinitionsList(ruleDefLst);
		
		mav.addObject("componentsList", loadsheetManagementService.getComponents());
		mav.addObject("ruleMaster",ruleMaster);
		mav.addObject("pageAction","CREATE");
		return mav;
	}
	

	/* =============== Create New Rule ==================*/
	@RequestMapping(value={"/create-rule"})
	public ModelAndView insertRuleDetails(RuleMaster ruleMaster) {
		
		loadsheetManagementService.createNewRule(ruleMaster);
		
		return getLoadsheetRuleDetails();
		
	}
	
	/* ================Edit Rule =======================*/
	@RequestMapping(value={"/edit-rule"})
	public ModelAndView getRuleDefinitions(int ruleId) {
		
		ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
		List<LoadsheetManagement> loadSheetManagementList;
		RuleMaster ruleMaster=loadsheetManagementService.getRuleDetails(ruleId);
		
		loadSheetManagementList = loadsheetManagementService.getAssignedLoadsheetCategories(ruleId);
		
		mav.addObject("componentsList", loadsheetManagementService.getComponents());
		mav.addObject("pageAction","UPDATE");
		mav.addObject("ruleMaster",ruleMaster);
		mav.addObject("loadSheetManagementList",loadSheetManagementList);
		return mav;
	
	}
	
	/* ================Update Rule =======================*/
	@RequestMapping(value={"/update-rule"})
	public ModelAndView updateRuleDetails(RuleMaster ruleMaster) {
		
		List<LoadsheetManagement> loadSheetManagementList;
		ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
		
		loadsheetManagementService.updateRuleDetails(ruleMaster);
		loadSheetManagementList = loadsheetManagementService.getAssignedLoadsheetCategories(ruleMaster.getRuleId());
		mav.addObject("componentsList", loadsheetManagementService.getComponents());
		mav.addObject("pageAction","UPDATE");
		mav.addObject("ruleMaster",ruleMaster);
		mav.addObject("loadSheetManagementList",loadSheetManagementList);
		return mav;
		
	}
	
	
	
	
}
