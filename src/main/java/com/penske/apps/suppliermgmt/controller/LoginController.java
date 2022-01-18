package com.penske.apps.suppliermgmt.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.LookupKey;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.smccore.base.service.UserService;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.domain.UserVendorFilterSelection;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.service.LoginService;
import com.penske.apps.suppliermgmt.servlet.ApplicationEntry;


@Version1Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @VendorAllowed
    @RequestMapping(value = "/validate", method = {RequestMethod.GET, RequestMethod.POST })
    protected  ModelAndView validateUser(HttpServletRequest request, HttpSession session) {
        LOGGER.debug("Inside validateUser() ");
        ModelAndView model=new ModelAndView();
        String forward=null;
        try{
            forward="forward:/app/home/displayHome.htm";

            //Reloading LookupData
            lookupManager.checkAndRefreshLookups();

            LookupContainer lookups = lookupManager.getLookupContainer();

            //Pull the user's ID out of the session, and then look up the actual user object, and put it in the session bean
            String userSSO = (String) session.getAttribute(ApplicationEntry.USER_SSO);

            String errorMessage = null;
            
            if(StringUtils.isBlank(userSSO))
            	return null;
            
            	
        	User user = userService.getUser(userSSO, true);
        	
        	if(user == null)
            	errorMessage = "Your SSOID not configured in SMC";
        	else
        	{
        		boolean hasBuddies = false;
        		boolean hasVendors = false;
        		boolean hasVendorFilterActivated = false;
        		
        		if(user.getUserType() == UserType.PENSKE)
        		{
        			List<String> existingBuddies = userService.getExistingBuddiesList(user.getSso());
                    List<UserVendorFilterSelection> userVendorFilterSelection = loginService.getUserVendorFilterSelections(user.getUserId());
                    hasBuddies = existingBuddies.size() > 1;
                    hasVendors = !userVendorFilterSelection.isEmpty();
                    if(hasVendors)
                    	hasVendorFilterActivated = userVendorFilterSelection.get(0).getIsActive();
        		}
        		
        		if(user.getSecurityFunctions().isEmpty())
        			errorMessage = "Security function are not there for Your SSOID";
        		
        		if(user.getUserType() == UserType.VENDOR && user.getAssociatedVendorIds().isEmpty())
        			errorMessage = "Associated vendors are not there for Your SSOID";
        		
        		UserLoginHistory previousLoginHistory = loginService.recordUserLogin(request, user);
        		loginService.deleteOTP(user);
        		Date lastLoginDate = previousLoginHistory == null ? null : previousLoginHistory.getLastLoginDate();
        		
        		String contextPath = request.getContextPath();
                sessionBean.initialize(user, contextPath, lastLoginDate, hasBuddies, hasVendors, hasVendorFilterActivated);
        	}
        	
        	if(errorMessage != null)
            {
            	ErrorModel errorModel = new ErrorModel(errorMessage);
            	model.addObject("errorModel", errorModel);
            	forward = "app-container/GlobalErrorContainerPage";
            }
        	
        	String supportNumber = lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM);
            model.addObject("supportNum", supportNumber);
        	
            model.addObject("lastLogin", sessionBean.getFormattedUserLoginDate());
            model.addObject("supportNum",supportNumber);
            model.setViewName(forward);
            
        }catch(RuntimeException e){
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
