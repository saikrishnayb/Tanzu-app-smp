/**
 *****************************************************************************
 * File Name     : BaseController
 * Description   : Controller class for common functionalities 
 * Project       : SMC
 * Package       : com.penske.apps.smc.controller
 * Author        : 502299699
 * Date			 : Mar 24, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 */
package com.penske.apps.suppliermgmt.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.LookupKey;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.ErrorModel;

@Version1Controller
public class BaseController {
	 private static Logger LOGGER = LogManager.getLogger(BaseController.class);
	
	 @Autowired
	 private SuppliermgmtSessionBean sessionBean;
	 @Autowired
	 private LookupManager lookupManager;
	
 /**
	 * Global exception handler for handling exceptions via the errors
	 * and warnings collection in the page header.
	 * 
	 * @param ex the caught exception.
 * @return
	 */
	public ModelAndView handleException(Exception ex) {
		//Check if this exception has a human-readable exception somewhere in its stack trace.
    	int humanReadableExceptionIndex = ExceptionUtils.indexOfType(ex, HumanReadableException.class);
    	if(humanReadableExceptionIndex != -1)
    	{
    		Throwable[] chain = ExceptionUtils.getThrowables(ex);
    		if(chain != null && chain.length > humanReadableExceptionIndex)
    		{
    			Throwable th = chain[humanReadableExceptionIndex];
    			if(HumanReadableException.class.isAssignableFrom(th.getClass()))
    				return handleHumanReadableException((HumanReadableException) th);
    		}
    	}
		
		ModelAndView mv = new ModelAndView("error/v1/GlobalErrorPage");
		ErrorModel model = new ErrorModel();
		try{
			//getting support num from lookup
			LookupContainer lookups = lookupManager.getLookupContainer();
			String supportPhoneNumber = lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM);
			
			User user = sessionBean.getUser();
			String userSSO = user == null ? "" : user.getSso();
			String randomNumber = getRandomNumber();
			LOGGER.error("Caught Exception  Reference Number is:"+randomNumber+" And Logged in User is:"+userSSO+" Exception is::"+ex.toString(),ex);
			model.setMessage("Application Error Occured. Reference Number is "+randomNumber+".");
			
			if(StringUtils.isNotBlank(supportPhoneNumber))
				mv.addObject("supportNum",supportPhoneNumber);
		}
		catch(Exception e){
			LOGGER.error("Exception occured in handleException method of BaseController"+e.toString(),e);
			mv.addObject("supportNum","1-866-926-7240");
			return mv;
		}
		mv.addObject(model);
		return mv;
	}

    public ModelAndView handleHumanReadableException(HumanReadableException ex)
    {    	
    	ModelAndView mv = new ModelAndView("error/v1/GlobalErrorPage");
        ErrorModel model = new ErrorModel();
        try {
            User user = sessionBean.getUser();
            String userSSO = user == null ? "" : user.getSso();
            String randomNumber = UUID.randomUUID().toString();
            LOGGER.error("Caught Unhandled Exception.  Reference Number is:" + randomNumber + " And Logged in User is:" + userSSO
            + " and Exception is::" + ex.toString(), ex);
            model.setMessage(ex.getHumanReadableMessage() + " Reference number is " + randomNumber);
            
            // getting support num from lookup
            LookupContainer lookups = lookupManager.getLookupContainer();
			String supportPhoneNumber = lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM);
            if (StringUtils.isNotBlank(supportPhoneNumber))
                mv.addObject("supportNum", supportPhoneNumber);
            
        } catch (Exception e) {
            LOGGER.error("Exception occured in handleException method of BaseController" + e.toString(), e);
            mv.addObject("supportNum", "1-866-926-7240");
            return mv;
        }
        mv.addObject(model);
        return mv;
    }
	
	/**
	 * Global exception handler for handling ajax exceptions via the errors
	 * and warnings collection in the page header.
	 * @param ex the caught exception.
	 * @return
	 */
	public void handleAjaxException(Exception e, HttpServletResponse response) {
		User user = sessionBean.getUser();
		String userSSO = user == null ? "" : user.getSso();
		LOGGER.error("Exception in Ajax Request.  Reference Number is:"+getRandomNumber()+" And Logged in User is:"+userSSO+" and Exception is"+ e.toString(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);/* setting response status to 500*/
	}
		
	/**
	 * Method to generate secure random number
	 * @return
	 */
	private String getRandomNumber(){
		UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
	}

}
