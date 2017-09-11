
package com.penske.apps.suppliermgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.HelpService;


/************************************************************************************
* @Author         : 502403391
* @Version        : 1.0
* @FileName       : NavigationController
* @Date Created	  : May 15,2015
* @Date Modified  : 
* @Modified By    : 
* @Contact        :
* @Description    : Class for redirecting to different application
* @History        :
*This class is under targeted folder, make sure the changes to be merged in targeted folder also
************************************************************************************/
@Controller
@RequestMapping("/navigation")
public class NavigationController extends BaseController {

	/**
	 * Navigateapplication.
	 *
	 * @param destination the destination
	 * @param session the session
	 * @param request the request
	 * @return the string
	 */
	
	@Autowired
	private HelpService helpService;
	@Autowired
    private SuppliermgmtSessionBean sessionBean;
	
	/** The logger. */
	private static Logger LOGGER = Logger.getLogger(NavigationController.class);
	
	@VendorAllowed
	@ResponseBody
	@RequestMapping(value = "/navigate", method = RequestMethod.POST)
	public String navigateapplication(@RequestParam(value="path") String destination, 
			@RequestParam(value="controllerName") String controllerName,
			@RequestParam(value="templateKey") String templateKey,
            HttpServletRequest request) throws Exception{
		
		
		String app = "";
		StringBuffer url = null;

		UserContext userContext = new UserContext();
		try
		{
      
			url=new StringBuffer();
			app = destination.toLowerCase();
			 userContext = sessionBean.getUserContext();
			if("adminconsole".equalsIgnoreCase(destination)){
				if(!templateKey.equalsIgnoreCase("0")){
					//If user comes from Home page dash board
					url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append(ApplicationConstants.VENDOR_TEMPLATE_URL).append(templateKey);
				}else{
					//Dircetly On click of admin consoleTab
					url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append(ApplicationConstants.PENSKE_USER_URL);
				}
			}else if("Home".equalsIgnoreCase(destination)){
				url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append("home/homePage.htm?tabId=").append(controllerName);
			}else{

			
				url.append(ApplicationConstants.SLASH).append(app).append(ApplicationConstants.DEV_ENTRY_SERVLET).append(userContext.getUserSSO());

				if(controllerName!=null){
					url.append(ApplicationConstants.DEV_CONTROLLER_NAME).append(controllerName);
				}
				if(!templateKey.equalsIgnoreCase("0")){
					url.append(ApplicationConstants.DEV_TEMPLATE_KEY).append(templateKey);
				}
			}
			LOGGER.info("Navigating the iframe to the URL"+url);
		}

		catch(Exception e)
		{
			handleException(e);
		}
		LOGGER.debug("Tab Application URL ::"+ url.toString());
		return url.toString();
	}
	
	@VendorAllowed
	@RequestMapping(value = "/getHelp")
	protected  ModelAndView getHelp() {
		ModelAndView model=new ModelAndView();
		UserContext userContext = new UserContext();
		
		try
		{		
			userContext = sessionBean.getUserContext();
			String help = helpService.getHelp(userContext.getUserType());
			model.addObject("helpContent",help);
			model.setViewName("home/help");
				 
		  }catch(Exception e){
			  model=handleException(e);
		  }
		return model;
	}	
}
