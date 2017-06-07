package com.penske.apps.adminconsole.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.penske.apps.adminconsole.model.LoadSheetCategoryDetails;
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
	@RequestMapping("/loadsheet-sequence")
	public ModelAndView getLoadsheetSequences(){
		ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-sequence");
		mav.addObject("sequences", loadsheetManagementService.getLoadsheetSequences());
		mav.addObject("categories", loadsheetManagementService.getCategoryList());
		mav.addObject("types", loadsheetManagementService.getTypeList());
		return mav;
	}
	
	
	/*==============Create Rule===================*/
	@RequestMapping("/load-create-rule")
	public ModelAndView getComponents(@RequestParam("requestedFrom") String requestedFrom){
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
		mav.addObject("requestedFrom",requestedFrom);
		return mav;
	}
	
	/*=========Go back From Create Rule screen to Rules screen or configure rule screen*=======*/ 
	@RequestMapping("/goBack-createRule")
	public ModelAndView goBackFromCreateRule(HttpServletRequest request,@RequestParam(value="requestedFrom",required=false) String requestedFrom){
		
		return redirectFromCreateRule(request, requestedFrom);
	}
	/* =============== Create New Rule ==================*/
	@RequestMapping(value={"/create-rule"})
	public ModelAndView insertRuleDetails(HttpServletRequest request,RuleMaster ruleMaster) {
		
		loadsheetManagementService.createNewRule(ruleMaster);
		
		return redirectFromCreateRule(request, ruleMaster.getRequestedFrom());
		
	}
	
	/**
	 * Method to redirect the page after save or back in create rule page
	 * @param request
	 * @param requestedFrom
	 * @return
	 */
	private ModelAndView redirectFromCreateRule(HttpServletRequest request,String requestedFrom){
		
		HttpSession session = request.getSession(false);
		ModelAndView mav=null;
		LoadSheetCategoryDetails catDetails=null;
		//Get component Details from session
		if(session != null){
			catDetails=(LoadSheetCategoryDetails)session.getAttribute("CATEGORY_DETAILS");
		}
		if(requestedFrom !=null){
			if(requestedFrom.equalsIgnoreCase("CREATE_RULE")){
				mav=getLoadsheetRuleDetails();
			}else{
				if(catDetails!=null){
					mav=getLoadsheetComponents(request, catDetails.getCategoryId(), catDetails.getCategory(), catDetails.getType(), catDetails.getViewMode());
					//details for opening the Add rule popup
					mav.addObject("componentId",catDetails.getComponentId());
					mav.addObject("visibilityId",catDetails.getVisibilityId());
					mav.addObject("viewMode",catDetails.getViewMode());
				}
			}
		}else{//If page reloads in Add rule page
			mav=getLoadsheetComponents(request, catDetails.getCategoryId(), catDetails.getCategory(), catDetails.getType(), catDetails.getViewMode());
		}
		
		return mav;
	}
	
	/* ================Edit Rule =======================*/
	@RequestMapping(value={"/edit-rule"})
	public ModelAndView getRuleDefinitions(int ruleId,@RequestParam("requestedFrom") String requestedFrom) {
		
		ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
		RuleMaster ruleMaster=loadsheetManagementService.getRuleDetails(ruleId);
		
		ruleMaster.setRequestedFrom(requestedFrom);
		mav.addObject("componentsList", loadsheetManagementService.getComponents());
		mav.addObject("pageAction","UPDATE");
		mav.addObject("ruleMaster",ruleMaster);
		mav.addObject("requestedFrom",ruleMaster.getRequestedFrom());
		return mav;
	
	}
	
	/* ================Update Rule =======================*/
	@RequestMapping(value={"/update-rule"})
	public ModelAndView updateRuleDetails(RuleMaster ruleMaster) {
		
		ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
		
		loadsheetManagementService.updateRuleDetails(ruleMaster);
		mav.addObject("componentsList", loadsheetManagementService.getComponents());
		mav.addObject("pageAction","UPDATE");
		mav.addObject("ruleMaster",ruleMaster);
		mav.addObject("requestedFrom",ruleMaster.getRequestedFrom());
		return mav;
		
	}
	
	/* ================== loadsheet components ================== */
	@RequestMapping(value={"/get-loadsheet-components"})
	public ModelAndView getLoadsheetComponents(HttpServletRequest request,@RequestParam("categoryId") String categoryId,@RequestParam("category") String category,@RequestParam(value="type") String type,@RequestParam(value="viewMode") String viewMode) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-components");
		String defaultType=type;
		if(viewMode.equals("Y")){// if uses default value is 'Y' we need to pass type as DEFAULT to load load sheet sequences.
			defaultType=ApplicationConstants.DEFAULT_TYPE;
		}
		mav.addObject("components", loadsheetManagementService.getLoadsheetComponents(category,defaultType));
		mav.addObject("category", category);
		mav.addObject("type", type);
		mav.addObject("viewMode", viewMode);
		
		//Adding details to session for create rule back button
		LoadSheetCategoryDetails catDetails=new LoadSheetCategoryDetails();
		catDetails.setCategory(category);
		catDetails.setCategoryId(categoryId);
		catDetails.setType(type);
		catDetails.setViewMode(viewMode);
		
		HttpSession session = request.getSession(false); 
		if(session != null){
			 session.setAttribute("CATEGORY_DETAILS", catDetails);
		}
		
		return mav;
	}
	
	/* ================== load sheet sequence ================== */
	@RequestMapping(value={"/get-loadsheet-sequence"})
	public ModelAndView getLoadsheetSequencePage(@RequestParam("categoryId") String categoryId,@RequestParam("category") String category,@RequestParam(value="type") String type,@RequestParam(value="viewMode") String viewMode) {
		ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-sequence");
		String defaultType=type;
		if(viewMode.equals("Y")){// if uses default value is 'Y' we need to pass type as DEFAULT to load load sheet sequences.
			defaultType=ApplicationConstants.DEFAULT_TYPE;
		}
		mav.addObject("sequences", loadsheetManagementService.getLoadsheetSequences(category,defaultType));
		mav.addObject("selectedCategory", category.trim());
		mav.addObject("selectedType", type.trim());
		mav.addObject("viewMode", viewMode);
		mav.addObject("categories", loadsheetManagementService.getCategoryList());
		mav.addObject("types", loadsheetManagementService.getTypeList());
		return mav;
	}
	
	/* ================ Create New Loadsheet Sequence ===============*/
	@RequestMapping(value={"/create-loadsheet"})
	public ModelAndView loadCreateLoadSheetSequence(){
		ModelAndView mav=new ModelAndView("/admin-console/app-config/create-loadsheet-sequence");
		
		mav.addObject("componentDetailsList",loadsheetManagementService.getUnAssignedComponents(null, null));
		mav.addObject("categoriesList", loadsheetManagementService.getCategoryList());
		mav.addObject("typesList", loadsheetManagementService.getTypeList());
		mav.addObject("mfrList", loadsheetManagementService.getMfrList());
		return mav;
	}
	
	
}
