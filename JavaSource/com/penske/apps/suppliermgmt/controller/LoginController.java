package com.penske.apps.suppliermgmt.controller;
/**
 *****************************************************************************************************************
 * File Name     : LoginController
 * Description   : This class is used set logged in user details to the session
 * Project       : SMC
 * Package       : com.penske.apps.smcop.controller
 * Author        : 502403391
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.LoginService;
import com.penske.apps.suppliermgmt.service.UserService;
import com.penske.apps.suppliermgmt.servlet.ApplicationEntry;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.util.LookupManager;


@Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LookupManager lookUpManager;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @VendorAllowed
    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.POST })
    protected  ModelAndView validateUser(HttpServletRequest request, HttpSession session) {
        LOGGER.debug("Inside validateUser() ");
        User usermodel = new User();
        ModelAndView model=new ModelAndView();
        String forward=null;
        try{
            forward="forward:/app/home/displayHome.htm";

            //Reloading LookupData
            lookUpManager.reloadLookupData();



            //getting support num from lookup

            List<LookUp> suppNumlist=lookUpManager.getLookUpListByName(ApplicationConstants.SUPP_NUM);
            LookUp lookUp=null;
            if(suppNumlist!=null){
                lookUp=suppNumlist.get(0);
                model.addObject("supportNum",lookUp.getLookUpValue());
            }

            //Pull the user's ID out of the session, and then look up the actual user object, and put it in the session bean
            String userSSO = (String) session.getAttribute(ApplicationEntry.USER_SSO);

            LookUp errorMessage = null;
            
            if(StringUtils.isNotBlank(userSSO)){
                usermodel.setSso(userSSO);
                usermodel.setStatus(ApplicationConstants.ACTIVE);
                usermodel = loginService.getUserDetails(usermodel);

                if(null != usermodel) {

                    UserContext userContext = new UserContext();
                    userContext.setUserId(usermodel.getUserId());
                    userContext.setUserName(usermodel.getSso());
                    userContext.setUserType(usermodel.getUserType());
                    userContext.setFirstName(usermodel.getFirstName());
                    userContext.setLastName(usermodel.getLastName());
                    userContext.setPhone(usermodel.getPhone());
                    userContext.setRoleId(usermodel.getRoleId());
                    userContext.setStatus(usermodel.getStatus());
                    userContext.setUserDept(usermodel.getUserDept());
                    userContext.setUserSSO(usermodel.getSso());
                    userContext.setEmail(usermodel.getEmail());
                    userContext.setExtension(usermodel.getExtension());
                    userContext.setUserDeptName(usermodel.getUserDeptName());
                    userContext.setOrgId(usermodel.getOrgId());
                    userContext.setTabSecFunctionMap(loginService.getTabs(userContext.getRoleId()));
                    userContext.setSecurityFunctions(loginService.getAllUserSecurityFunctions(userContext));
                    
                    boolean hasBuddies = false;
                    boolean hasVendors = false;
                    if(userContext.getUserType()==ApplicationConstants.PENSKE_USER_TYPE){
                        List<Buddies> existingBuddies=userService.getExistingBuddiesList(userContext.getUserName());
                        List<UserVendorFilterSelection> userVendorFilterSelection = loginService.getUserVendorFilterSelections(usermodel.getUserId());
                        hasBuddies = existingBuddies.size() > 1;
                        hasVendors = !userVendorFilterSelection.isEmpty();
                    }
                    /*if logged in user is vendor user checking for associated vendors*/
                    if(userContext.getTabSecFunctionMap() == null || userContext.getTabSecFunctionMap().isEmpty())
                    {
                        LOGGER.debug("Security functions not found for the logged in user");
                        List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.SEC_FUNCTION_NOT_FOUND);
                        errorMessage = message == null ? null : message.get(0);
                    }
                    else if(userContext.getUserType()==ApplicationConstants.VENDOR_USER_TYPE){
                        userContext.setAssociatedVendorList(loginService.getAssociatedVendors(userContext.getOrgId()));
                        if(userContext.getAssociatedVendorList().isEmpty() || userContext.getAssociatedVendorList()==null)
                        {
                            LOGGER.debug("Associated vendors not found for the logged in vendor user");
                            userContext.setTabSecFunctionMap(null);
                            List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.ASSOCIATED_VENDORS_NOT_FOUND);
                            errorMessage = message == null ? null : message.get(0);
                        }
                    }

                    UserLoginHistory previousLoginHistory = loginService.recordUserLogin(request, userContext);
                    Date lastLoginDate = previousLoginHistory == null ? null : previousLoginHistory.getLastLoginDate();
                    
                    String contextPath = request.getContextPath();
                    sessionBean.initialize(userContext, contextPath, lastLoginDate, hasBuddies, hasVendors);
                }
                else
                {
                    LOGGER.debug("Logged in user not present in SMC tables");
                    List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.USER_NOT_FOUND);
                    errorMessage = message == null ? null : message.get(0);
                }
            }else{
                LOGGER.debug("User session context is null in Suppliermgmt");
                return null;
            }
            
            if(errorMessage != null)
            {
            	ErrorModel errorModel = new ErrorModel(errorMessage.getLookUpValue());
            	model.addObject("errorModel", errorModel);
            	forward = "app-container/GlobalErrorContainerPage";
            }
            model.addObject("lastLogin", sessionBean.getFormattedUserLoginDate());
            model.addObject("supportNum",lookUp.getLookUpValue());
            model.setViewName(forward);
        }catch(Exception e){
            model = handleException(e);
        }
        return model;

    }

    @VendorAllowed
    @RequestMapping(value = "/validateSession", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    protected void validateSession()
    {
        LOGGER.info("Inside suppliermgmt validateSession() ");
    }


}
