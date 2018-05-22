package com.penske.apps.adminconsole.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;
import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.adminconsole.model.ExcelUploadHandler;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.LoadSheetCategoryDetails;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequenceGroupMaster;
import com.penske.apps.adminconsole.model.LoadsheetSequenceMaster;
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
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
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

    private static final Logger LOGGER = Logger.getLogger(AppConfigController.class);

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
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @RequestMapping(value = {"/navigate-app-config"})
    public ModelAndView navigateAppConfig(HttpServletRequest request) {

        Set<SecurityFunction> securityFunctions = sessionBean.getUserContext().getSecurityFunctions();

        List<LeftNav> leftNavs = SubTab.APP_CONFIG.getLeftNavs();

        for (LeftNav leftNav : leftNavs) {

            SecurityFunction securityFunction = leftNav.getSecurityFunction();

            boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
            if (noAccess) continue;

            return new ModelAndView("redirect:/" + leftNav.getUrlEntry());
        }

        return new ModelAndView("/admin-console/security/noAccess");
    }

    /* ================== Subject Management ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_SUBJECTS)
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
    @SmcSecurity(securityFunction = SecurityFunction.UPLOAD_EXCEL)
    @RequestMapping("/excelUploads")
    public ModelAndView getExcelUploadPage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/excelUploads");
        UserContext userContext = sessionBean.getUserContext();
        mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.UPLOAD_EXCEL, userContext));
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
    @SmcSecurity(securityFunction = SecurityFunction.UPLOAD_EXCEL)
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ModelAndView uploadTransportExcelFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "uploadSelect") String uploadSelect) throws Exception
    {
        UserContext userContext = sessionBean.getUserContext();

        ModelAndView mav = new ModelAndView("/admin-console/app-config/excelUploads");
        mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.UPLOAD_EXCEL, userContext));

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
    // TODO SMCSEC is this even used?????
    @RequestMapping("/notifications")
    public ModelAndView getNotificationsPage(){

        LOGGER.error("getNotificationsPage is used!!!! :)");

        ModelAndView mav = new ModelAndView("/admin-console/app-config/notifications");
        List<Notification> notificationList = notificationService.getNotifications();

        for (Notification notification : notificationList) {
            notificationService.getSortedNotificationParties(notification);
        }

        mav.addObject("notificationsList", notificationList);

        return mav;
    }

    /* ================== Dynamic Rules ================== */
    @SmcSecurity(securityFunction = SecurityFunction.DYNAMIC_RULES_MANAGEMENT)
    @RequestMapping("/dynamic-rules")
    public ModelAndView getDynamicRulesPage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/dynamic-rules");

        UserContext userContext = sessionBean.getUserContext();

        mav.addObject("activeDynamicRules", dynamicRuleService.getAllDynamicRulesByStatus("A"));
        mav.addObject("inactiveDynamicRules", dynamicRuleService.getAllDynamicRulesByStatus("I"));
        mav.addObject("corpCodes", dynamicRuleService.getAllCorpCodes());
        mav.addObject("makes", dynamicRuleService.getAllVehicleMakes());
        mav.addObject("access",CommonUtils.hasAccess(ApplicationConstants.DYNAMICRULE, userContext));
        return mav;
    }

    /* ================== Search Templates ================== */
    @SmcSecurity(securityFunction = SecurityFunction.SEARCH_TEMPLATES)
    @RequestMapping("/search-template-management")
    public ModelAndView getDefaultTemplatePage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/search-template-management");

        mav.addObject("searchTemplates", searchTemplateService.getAllSearchTemplates());

        return mav;
    }

    /* ===================== Alerts ===================== */
    @SmcSecurity(securityFunction = SecurityFunction.ALERT_MANAGEMENT)
    @RequestMapping("/alerts")
    public ModelAndView getHoverOverHelpPage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/alerts");

        // Load the Alerts and Alert Headers for the data-table.
        mav.addObject("alerts", alertService.getAllAlertsAndHeaders());

        return mav;
    }

    /* ================== Global Exceptions ================== */
    @SmcSecurity(securityFunction = SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT)
    @RequestMapping("/global-exceptions")
    public ModelAndView getGlobalExceptionsPage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/global-exceptions");
        List<GlobalException> exceptions = exceptionService.getGlobalExceptions();

        mav.addObject("exceptions", exceptions);

        return mav;
    }

    /* ================== Unit Exceptions ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("/unit-exceptions")
    @Deprecated
    public ModelAndView getUnitExcpetionsPage(){

        LOGGER.error("getUnitExcpetionsPage is used!!!! :)");

        ModelAndView mav = new ModelAndView("/admin-console/app-config/unit-exceptions");
        List<UnitException> exceptions = exceptionService.getUnitExceptions();

        mav.addObject("exceptions", exceptions);
        //		Commented code here was used to test the Error Page only
        //		String x = null;
        //		x.equals("cool");

        return mav;
    }

    /* ================== Delay ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("/delay-management")
    public ModelAndView getDelayManagementPage(){

        LOGGER.error("getDelayManagementPage is used!!!! :)");

        ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-management");

        mav.addObject("delays", delayService.getDelays());

        return mav;
    }

    /* ================== Delay Reasons ================== */
    // TODO SMCSEC is this even used?????
    @RequestMapping("/delay-reason-types")
    public ModelAndView getReasonTypesPage(){

        LOGGER.error("getReasonTypesPage is used!!!! :)");

        ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-reason-types");

        mav.addObject("types", delayService.getTypes());

        return mav;
    }

    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_DELAY_REASONS)
    @RequestMapping("/delay-reason-codes")
    public ModelAndView getReasonCodesPage(){
        ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-reason-codes");

        mav.addObject("delays", delayService.getReasons());

        return mav;
    }

    // TODO SMCSEC is this even used?????
    @RequestMapping("/delay-type-reason-assoc")
    public ModelAndView getTypeReasonassocPage(){

        LOGGER.error("getTypeReasonassocPage is used!!!! :)");

        ModelAndView mav = new ModelAndView("/admin-console/app-config/delay-type-reason-assoc");

        mav.addObject("delays", delayService.getAssociations());

        return mav;
    }

    /* ================== Terms And Conditions ================== */
    @SmcSecurity(securityFunction = SecurityFunction.MANAGE_TC)
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
        mav.addObject("types", loadsheetManagementService.getTypeList(ApplicationConstants.BLANK));
        mav.addObject("viewMode", "");
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
    public ModelAndView goBackFromCreateRule(HttpServletRequest request,@RequestParam(value="requestedFrom",required=false) String requestedFrom,@RequestParam(value="componentId",required=false) String componentId){

        return redirectFromCreateRule(request, requestedFrom,componentId);
    }
    /* =============== Create New Rule ==================*/
    @RequestMapping(value={"/create-rule"})
    public ModelAndView insertRuleDetails(HttpServletRequest request,RuleMaster ruleMaster){

        loadsheetManagementService.createNewRule(ruleMaster);

        return redirectFromCreateRule(request, ruleMaster.getRequestedFrom(),null);

    }

    @RequestMapping("/goBack-componets")
    public ModelAndView goBackFromComponetsPage(HttpServletRequest request,@RequestParam("requestedFrom") String requestedFrom){

        ModelAndView mav=null;
        int ruleId=0;
        String editRuleRequestedFrom=null;
        HttpSession session = request.getSession(false);
        if(requestedFrom.equalsIgnoreCase("LOADSHEET_MANAGEMENT")){

            mav=getLoadsheetManagementDetails();

        }else if(requestedFrom.equalsIgnoreCase("EDIT_RULE")){

            //Get Rule Details from session
            if(session != null){
                ruleId=(Integer)session.getAttribute("RULE_ID");
                editRuleRequestedFrom=(String)session.getAttribute("REQUESTED_FROM");
                mav=getRuleDefinitions(request, ruleId, editRuleRequestedFrom);
            }
        }

        return mav;

    }

    /**
     * Method to redirect the page after save or back in create rule page
     * @param request
     * @param requestedFrom
     * @return
     */
    private ModelAndView redirectFromCreateRule(HttpServletRequest request,String requestedFrom,String componentId){

        HttpSession session = request.getSession(false);
        ModelAndView mav=null;
        LoadSheetCategoryDetails catDetails=null;
        String compRequestedFrom=null;
        //Get component Details from session
        if(session != null){
            catDetails=(LoadSheetCategoryDetails)session.getAttribute("CATEGORY_DETAILS");
            compRequestedFrom=(String)session.getAttribute("COMP_REQUESTED_FROM");
        }
        if(requestedFrom !=null){
            if(requestedFrom.equalsIgnoreCase("CREATE_RULE")){
                return new ModelAndView("redirect:/admin-console/app-config/loadsheet-rule.htm");
            }else{
                if(catDetails!=null){
                    mav=getLoadsheetComponents(request, catDetails.getCategoryId(), catDetails.getCategory(), catDetails.getType(), catDetails.getViewMode(),compRequestedFrom,componentId);
                    //details for opening the Add rule popup
                    mav.addObject("componentId",catDetails.getComponentId());
                    mav.addObject("visibilityId",catDetails.getVisibilityId());
                    mav.addObject("viewMode",catDetails.getViewMode());
                }
            }
        }else{//If page reloads in Add rule page
            mav=getLoadsheetComponents(request, catDetails.getCategoryId(), catDetails.getCategory(), catDetails.getType(), catDetails.getViewMode(),compRequestedFrom,componentId);
        }

        return mav;
    }

    /* ================Edit Rule =======================*/
    @RequestMapping(value={"/edit-rule"})
    public ModelAndView getRuleDefinitions(HttpServletRequest request,int ruleId,@RequestParam("requestedFrom") String requestedFrom) {

        ModelAndView mav = new ModelAndView("/admin-console/app-config/create-rule");
        List<LoadsheetManagement> loadSheetManagementList;
        RuleMaster ruleMaster=loadsheetManagementService.getRuleDetails(ruleId,requestedFrom);


        ruleMaster.setRequestedFrom(requestedFrom);

        loadSheetManagementList = loadsheetManagementService.getAssignedLoadsheetCategories(ruleId);

        mav.addObject("componentsList", loadsheetManagementService.getComponents());
        mav.addObject("pageAction","UPDATE");
        mav.addObject("ruleMaster",ruleMaster);
        mav.addObject("requestedFrom",ruleMaster.getRequestedFrom());
        mav.addObject("loadSheetManagementList",loadSheetManagementList);

        //Adding details to session to display page after clicking on GOTO link
        HttpSession session = request.getSession(false);
        if(session != null){
            session.setAttribute("RULE_ID", ruleId);
            session.setAttribute("REQUESTED_FROM", requestedFrom);
        }


        return mav;

    }

    /* ================Update Rule =======================*/
    @RequestMapping(value={"/update-rule"})
    public String updateRuleDetails(RuleMaster ruleMaster){

        loadsheetManagementService.updateRuleDetails(ruleMaster);

        return "redirect:/admin-console/app-config/edit-rule.htm?ruleId="+ruleMaster.getRuleId()+"&requestedFrom="+ruleMaster.getRequestedFrom();

    }

    /* ================== loadsheet components ================== */
    @RequestMapping(value={"/get-loadsheet-components"})
    public ModelAndView getLoadsheetComponents(HttpServletRequest request,@RequestParam("categoryId") String categoryId,@RequestParam("category") String category,@RequestParam(value="type") String type,
            @RequestParam(value="viewMode") String viewMode,@RequestParam("compRqstdFrom") String compRequestedFrom,@RequestParam(value="componentId",required=false) String componentId) {
        ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-components");
        HttpSession session = request.getSession(false);
        String defaultType=type;
        if(viewMode.equals("Y")){// if uses default value is 'Y' we need to pass type as DEFAULT to load load sheet sequences.
            defaultType=ApplicationConstants.DEFAULT_TYPE;
        }
        mav.addObject("components", loadsheetManagementService.getLoadsheetComponents(category,defaultType));
        mav.addObject("category", category);
        mav.addObject("type", type);
        mav.addObject("viewMode", viewMode);
        mav.addObject("compRequestedFrom",compRequestedFrom);
        mav.addObject("selectedComponentId",componentId);

        session.setAttribute("COMP_REQUESTED_FROM", compRequestedFrom);

        //Adding details to session for create rule back button
        LoadSheetCategoryDetails catDetails=new LoadSheetCategoryDetails();
        catDetails.setCategory(category);
        catDetails.setCategoryId(categoryId);
        catDetails.setType(type);
        catDetails.setViewMode(viewMode);

        if(session != null){
            session.setAttribute("CATEGORY_DETAILS", catDetails);
        }

        return mav;
    }

    /* ================== load sheet sequence ================== */
    @RequestMapping(value={"/get-loadsheet-sequence"})
    public ModelAndView getLoadsheetSequencePage(@RequestParam("categoryId") String categoryId,@RequestParam("category") String category,@RequestParam(value="type") String type,@RequestParam(value="viewMode") String viewMode) {
        ModelAndView mav = new ModelAndView("/admin-console/app-config/loadsheet-sequence");
        //if type is DEFAULT we should load all sequence for selected category irrespective of type(VOD-309)
        if(type.trim().equals(ApplicationConstants.DEFAULT_TYPE)){
            type=ApplicationConstants.BLANK;
        }
        String defaultType=type;
        // to check for the selected category and type uses default is 'Y' or 'N'.
        if(StringUtils.isNotBlank(category) && StringUtils.isNotBlank(type)){
            defaultType=loadsheetManagementService.getUsesDefaultForCategoryAndType(category, type);
            if(defaultType!=null && !(defaultType.equals("N"))){
                defaultType=ApplicationConstants.DEFAULT_TYPE;// if uses default value is 'Y' we need to pass type as DEFAULT to load load sheet sequences.
            }else{
                defaultType=type;
            }
        }
        mav.addObject("sequences", loadsheetManagementService.getLoadsheetSequences(category,defaultType));
        mav.addObject("selectedCategory", category.trim());
        mav.addObject("selectedType", type.trim());
        mav.addObject("viewMode", viewMode);
        mav.addObject("categories", loadsheetManagementService.getCategoryList());
        mav.addObject("types", loadsheetManagementService.getTypeList(category));
        return mav;
    }

    /* ================ Create New Loadsheet Sequence ===============*/
    @RequestMapping(value={"/open-create-sequence"})
    public ModelAndView loadCreateLoadSheetSequence(LoadsheetSequenceMaster seqMaster,HttpServletRequest request){
        ModelAndView mav=new ModelAndView("/admin-console/app-config/create-loadsheet-sequence");
        List<String> mfrList=new ArrayList<String>();
        List<LoadSheetComponentDetails> allComponents=new ArrayList<LoadSheetComponentDetails>();
        List<String> typeList=new ArrayList<String>();

        //Add category and type values to the session for back button functionality.
        HttpSession session = request.getSession(false);
        if(session != null && seqMaster.getPageAction()!=null && seqMaster.getPageAction().equals("BACK") ){
            session.setAttribute("selectedCategoryinListPage", seqMaster.getCategory());
            session.setAttribute("selectedTypeinListPage", seqMaster.getType());
            session.setAttribute("selectedViewMode", seqMaster.getViewMode());
        }
        seqMaster.setPageAction("");
        List<LoadsheetSequenceGroupMaster> grpMasterList=new ArrayList<LoadsheetSequenceGroupMaster>();
        //Adding one empty Group onload
        LoadsheetSequenceGroupMaster grpMaster=new LoadsheetSequenceGroupMaster();
        grpMaster.setDisplaySeq(1);
        grpMaster.setName("GROUP NAME");

        grpMasterList.add(grpMaster);
        seqMaster.setGroupMasterList(grpMasterList);
        // to get manufacture list for selected category
        if(StringUtils.isNotEmpty(seqMaster.getCategory())){
            PoCategoryType poCategoryType = PoCategoryType.findTypeByName(seqMaster.getCategory());
           	mfrList=loadsheetManagementService.getMfrList(poCategoryType);
            allComponents=loadsheetManagementService.getUnAssignedComponents(seqMaster);
            typeList=loadsheetManagementService.getTypeList(seqMaster.getCategory());
        }
        mav.addObject("unassignedComponents",allComponents);
        mav.addObject("categoriesList", loadsheetManagementService.getCategoryList());
        mav.addObject("typesList", typeList);
        mav.addObject("mfrList", mfrList);
        mav.addObject("seqMaster",seqMaster);
        return mav;
    }

    /* ================= Save Loadsheet Sequence ==================*/
    @RequestMapping(value={"/create-sequence"})
    public ModelAndView saveLoadSheetSequence(LoadsheetSequenceMaster seqMaster){

        loadsheetManagementService.createLoadSheetSequencing(seqMaster);
        return new ModelAndView("redirect:/admin-console/app-config/loadsheet-sequence.htm");
    }

    /* ==================== to open the Edit loadsheet sequence page==================*/
    @RequestMapping(value={"/open-edit-sequence"})
    public ModelAndView editLoadSheetSequence(@RequestParam(value="seqMasterId") int seqMasterId,@RequestParam(value="action") String action,@RequestParam(value="category") String category,@RequestParam(value="type") String type,
            @RequestParam(value="viewMode") String viewMode,HttpServletRequest request){
        ModelAndView mav=new ModelAndView("/admin-console/app-config/create-loadsheet-sequence");
        List<String> mfrList=new ArrayList<String>();

        LoadsheetSequenceMaster seqMaster=loadsheetManagementService.getSequenceMasterDetails(seqMasterId);
        //Add category and type values to the session for back button functionality.
        HttpSession session = request.getSession(false);
        if(session != null){
            session.setAttribute("selectedCategoryinListPage", category);
            session.setAttribute("selectedTypeinListPage", type);
            session.setAttribute("selectedViewMode", viewMode);
        }
        seqMaster.setPageAction(action);
        // to get manufacture list for selected category
        if(StringUtils.isNotEmpty(seqMaster.getCategory())){
            PoCategoryType poCategoryType = PoCategoryType.findTypeByName(seqMaster.getCategory());
           	mfrList=loadsheetManagementService.getMfrList(poCategoryType);
        }
        mav.addObject("unassignedComponents",loadsheetManagementService.getUnAssignedComponents(seqMaster));
        mav.addObject("categoriesList", loadsheetManagementService.getCategoryList());
        mav.addObject("typesList", loadsheetManagementService.getTypeList(seqMaster.getCategory()));
        mav.addObject("mfrList", mfrList);
        mav.addObject("seqMaster",seqMaster);

        return mav;
    }

    /* ================= Update the loadsheet seqeuncing details =================*/
    @RequestMapping(value={"/update-sequence"})
    public ModelAndView updateLoadsheetSequencingDetails(LoadsheetSequenceMaster seqMaster,HttpServletRequest request){

        loadsheetManagementService.updateLoadsheetSequencingDetails(seqMaster);
        HttpSession session = request.getSession(false);
        return new ModelAndView("redirect:/admin-console/app-config/open-edit-sequence.htm?seqMasterId="+seqMaster.getId()+"&action="+seqMaster.getPageAction()+
                "&category="+session.getAttribute("selectedCategoryinListPage").toString()+"&type="+session.getAttribute("selectedTypeinListPage").toString()+"&viewMode="+session.getAttribute("selectedViewMode").toString());
    }


}
