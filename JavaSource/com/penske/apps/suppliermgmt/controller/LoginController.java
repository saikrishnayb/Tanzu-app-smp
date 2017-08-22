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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.VendorAllowed;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.service.HeaderService;
import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.model.User;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.LoginService;


@Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private HeaderService headerService;
    @Autowired
    private LookupManager lookUpManager;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @VendorAllowed
    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.POST })
    protected  ModelAndView validateUser(HttpServletRequest request) {
        LOGGER.debug("Inside validateUser() ");
        UserContext userContext = new UserContext();
        User usermodel = new User();
        ModelAndView model=new ModelAndView();
        String forward=null;
        try{
            userContext = getUserContext(request);
            forward="forward:/home/displayHome.htm";

            //Reloading LookupData
            lookUpManager.reloadLookupData();



            //getting support num from lookup

            List<LookUp> suppNumlist=lookUpManager.getLookUpListByName(ApplicationConstants.SUPP_NUM);
            LookUp lookUp=null;
            if(suppNumlist!=null){
                lookUp=suppNumlist.get(0);
                model.addObject("supportNum",lookUp.getLookUpValue());
            }

            userContext = getUserContext(request);

            if(userContext != null){
                usermodel.setSso(userContext.getUserSSO());
                usermodel.setStatus(ApplicationConstants.ACTIVE);
                usermodel = loginService.getUserDetails(usermodel);

                HttpSession session = request.getSession(false);

                if(null != usermodel) {

                    HeaderUser currentUser = headerService.getApplicationUserInfo(usermodel.getUserId());
                    session.setAttribute("currentUser", currentUser);
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
                    /*if logged in user is vendor user checking for associated vendors*/
                    if(userContext.getTabSecFunctionMap() == null || userContext.getTabSecFunctionMap().isEmpty()){
                        LOGGER.debug("Security functions not found for the logged in user");
                        List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.SEC_FUNCTION_NOT_FOUND);
                        if(null!=message){
                            model.addObject(ApplicationConstants.SEC_FUNCTION_NOT_FOUND, message.get(0).getLookUpValue());
                        }
                        forward="error/GlobalErrorPage";
                    }
                    else if(userContext.getUserType()==ApplicationConstants.VENDOR_USER_TYPE){
                        userContext.setAssociatedVendorList(loginService.getAssociatedVendors(userContext.getOrgId()));
                        if(userContext.getAssociatedVendorList().isEmpty() || userContext.getAssociatedVendorList()==null){
                            LOGGER.debug("Associated vendors not found for the logged in vendor user");
                            userContext.setTabSecFunctionMap(null);
                            List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.ASSOCIATED_VENDORS_NOT_FOUND);
                            if(null!=message){
                                model.addObject(ApplicationConstants.ASSOCIATED_VENDORS_NOT_FOUND, message.get(0).getLookUpValue());
                            }
                            forward="error/GlobalErrorPage";
                        }
                    }
                }
                else{
                    LOGGER.debug("Logged in user not present in SMC tables");
                    List<LookUp> message=lookUpManager.getLookUpListByName(ApplicationConstants.USER_NOT_FOUND);
                    if(null!=message){
                        model.addObject(ApplicationConstants.USER_NOT_FOUND, message.get(0).getLookUpValue());
                    }
                    forward="error/GlobalErrorPage";
                }
            }else{
                LOGGER.debug("User session context is null in Suppliermgmt");
                return null;
            }
            model.addObject("supportNum",lookUp.getLookUpValue());
            model.setViewName(forward);
        }catch(Exception e){
            model = handleException(e, request);
        }
        return model;

    }

    @VendorAllowed
    @RequestMapping(value = "/validateSession", method = {RequestMethod.GET, RequestMethod.POST })
    protected void validateSession(HttpServletRequest request,HttpServletResponse response)
    {
        LOGGER.info("Inside suppliermgmt validateSession() ");

    }


}
