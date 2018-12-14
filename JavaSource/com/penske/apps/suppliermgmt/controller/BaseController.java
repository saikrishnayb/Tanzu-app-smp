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


import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.util.LookupManager;

@Controller
public class BaseController {
	 private static Logger LOGGER = Logger.getLogger(BaseController.class);
	
	 @Autowired
	 private SuppliermgmtSessionBean sessionBean;
	
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
		
		ModelAndView mv = new ModelAndView("error/GlobalErrorPage");
		ErrorModel model = new ErrorModel();
		try{
			//getting support num from lookup
			LookupManager lookupManger=new LookupManager();
			List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
			LookUp lookUp=null;
			UserContext userContext = sessionBean.getUserContext();
			String userSSO = userContext == null ? "" : userContext.getUserSSO();
			String randomNumber=getRandomNumber();
			LOGGER.error("Caught Exception  Reference Number is:"+randomNumber+" And Logged in User is:"+userSSO+" Exception is::"+ex.toString(),ex);
			model.setMessage("Application Error Occured. Reference Number is "+randomNumber+".");
			
			if(suppNumlist!=null){
				lookUp=suppNumlist.get(0);
				mv.addObject("supportNum",lookUp.getLookUpValue());
			}
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
    	ModelAndView mv = new ModelAndView("error/GlobalErrorPage");
        ErrorModel model = new ErrorModel();
        try {
            UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext == null ? "" : userContext.getUserSSO();
            String randomNumber = UUID.randomUUID().toString();
            LOGGER.error("Caught Unhandled Exception.  Reference Number is:" + randomNumber + " And Logged in User is:" + userSSO
            + " and Exception is::" + ex.toString(), ex);
            model.setMessage(ex.getHumanReadableMessage() + " Reference number is " + randomNumber);
            // getting support num from lookup
            LookupManager lookupManger = new LookupManager();
            List<LookUp> suppNumlist = lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
            LookUp lookUp = null;
            if (suppNumlist != null) {
                lookUp = suppNumlist.get(0);
                mv.addObject("supportNum", lookUp.getLookUpValue());
            }
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
		UserContext userContext = sessionBean.getUserContext();
		String userSSO = userContext == null ? "" : userContext.getUserSSO();
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
