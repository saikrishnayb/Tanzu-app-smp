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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.model.UserContext;

public class BaseController {
	 private static Logger LOGGER = Logger.getLogger(BaseController.class);
	
	
 /**
	 * Global exception handler for handling exceptions via the errors
	 * and warnings collection in the page header.
	 * 
	 * @param ex the caught exception.
 * @param request 
	 * @return
	 */
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("error/GlobalErrorPage");
		ErrorModel model = new ErrorModel();
		try{
			UserContext userContext = new UserContext();
			//getting support num from lookup
			LookupManager lookupManger=new LookupManager();
			List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
			LookUp lookUp=null;
			userContext = getUserContext(request);
			String randomNumber=getRandomNumber();
			LOGGER.error("Caught Exception  Reference Number is:"+randomNumber+" And Logged in User is:"+userContext.getUserSSO()+" Exception is::"+ex.toString(),ex);
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
		mv.addObject("isNavigationExists",true);
		return mv;
	}
	
	/**
	 * Method to get user session object
	 * @param request
	 * @return UserContext
	 */
	public UserContext getUserContext(HttpServletRequest request){
		HttpSession session = request.getSession(false); 
		UserContext userContext = new UserContext();
		if(session != null){
			 userContext = (UserContext) session.getAttribute(ApplicationConstants.USER_MODEL);
		}
		return userContext;
	}
	/**
	 * Method to set user session object
	 * @param request
	 * @param userModel
	 */
	public void setUserContext(HttpServletRequest request,UserContext userModel){
		HttpSession session = request.getSession(false); 
		if(session != null){
			session.setAttribute(ApplicationConstants.USER_MODEL, userModel);
		}
		
	}
	
	/**
	 * Global exception handler for all other exceptions.
	 * @param request 
	 * 
	 * @param ex the caught exception.
	 * @return
	 */
	public ModelAndView handleException(RuntimeException ex, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("error/GlobalErrorPage");
		ErrorModel model = new ErrorModel();
		try{
		UserContext userContext = new UserContext();
		userContext = getUserContext(request);
		String randomNumber=getRandomNumber();
		LOGGER.error("Caught Unhandled Exception.  Reference Number is :"+randomNumber+" And Logged in User is:"+userContext.getUserSSO()+" and Exception is::"+ex.toString(),ex);
		model.setMessage("There was an unexcepted problem. Reference Number is "+randomNumber+".");
		//getting support num from lookup
		LookupManager lookupManger=new LookupManager();
		List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
		LookUp lookUp=suppNumlist.get(0);
		mv.addObject("supportNum",lookUp.getLookUpValue());
		}
		catch(Exception e){
			LOGGER.error("Exception occured in handleException method of BaseController"+e.toString(),e);
			mv.addObject("supportNum","1-866-926-7240");
			return mv;
		}
		mv.addObject(model);
		mv.addObject("isNavigationExists",true);
		return mv;

	}
	
	/**
	 * Global exception handler for handling ajax exceptions via the errors
	 * and warnings collection in the page header.
	 * @param request 
	 * 
	 * @param ex the caught exception.
	 * @return
	 */
	public void handleAjaxException(Exception e, HttpServletResponse response, HttpServletRequest request) {
		UserContext userContext = new UserContext();
		userContext = getUserContext(request);	
		LOGGER.error("Exception in Ajax Request.  Reference Number is:"+getRandomNumber()+" And Logged in User is:"+userContext.getUserSSO()+" and Exception is"+ e.toString(),e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);/* setting response status to 500*/
	}
		
	/**
	 * Method to generate secure random number
	 * @return
	 */
	/*private int getRandomNumber(){
		SecureRandom randomGenerator = new SecureRandom();
		return randomGenerator.nextInt();
		

	}*/
	private String getRandomNumber(){
		UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
	}

}
