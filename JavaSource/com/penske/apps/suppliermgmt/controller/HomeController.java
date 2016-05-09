package com.penske.apps.suppliermgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.LookUp;



import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.UpstreamVendor;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.HomeDashboardService;


/************************************************************************************
* @Author         : 502299699
* @Version        : 1.0
* @FileName       : HomeController
* @Date Created	  : May 15,2015
* @Date Modified  : 
* @Modified By    : 
* @Contact        :
* @Description    : 
* @History        :
*
************************************************************************************/

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{
	
	@Autowired
	private HomeDashboardService homeService;
	
	private static final Logger LOGGER = Logger.getLogger(HomeController.class);
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping("/displayHome")
	public ModelAndView displayHome(HttpServletRequest request){
		LOGGER.debug("Inside displayHome()");
		ModelAndView modelandView = null;
		String helpLink=null;
		try{
			UserContext userModel = getUserContext(request);
			modelandView=new ModelAndView("appContainer");
			//getting support num from lookup
			LookupManager lookupManger=new LookupManager();
			List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
			LookUp lookUp=null;
			if(suppNumlist!=null){
				lookUp=suppNumlist.get(0);
				LOGGER.debug("Suport Num :"+lookUp.getLookUpValue());
				modelandView.addObject("supportNum",lookUp.getLookUpValue());
			}
			
			
			if(userModel.getUserType()==ApplicationConstants.PENSKE_USER_TYPE){
				helpLink=lookupManger.getLookUpListByValue(ApplicationConstants.HELP_LINK,ApplicationConstants.INT_ONE);
				LOGGER.debug("HELP_LINK :"+helpLink);
			}
			else{
				helpLink=lookupManger.getLookUpListByValue(ApplicationConstants.HELP_LINK,ApplicationConstants.INT_TWO);
				LOGGER.debug("HELP_LINK :"+helpLink);
			}
			
			
			modelandView.addObject("helpLink",helpLink);
			modelandView.addObject("userDetails",userModel);
		}catch(Exception e){
			modelandView = handleException(e, request);
		}
		return modelandView;
	}
	
	/**
	 * This is the method used for the Home Page (Dashboard) of the SMC. This method is primarily used to
	 * populate a user's dashboard with the appropriate Tabs, Alert Headers, and Alerts and also to direct the
	 * user to the appropriate application tab when a link is clicked on the dashboard.
	 * 
	 * @author 600143568
	 */
	@RequestMapping(value = "/homePage", method = {RequestMethod.GET})
	public ModelAndView getHomePage(HttpServletRequest request){
		LOGGER.debug("Inside getHomePage()");
		ModelAndView modelandView = new ModelAndView("/home/home");
		try{
			UserContext userModel = getUserContext(request);
			List<Tab> tabs = homeService.selectTabs(userModel);
			int defaultTab = tabs.get(0).getTabId();
			modelandView.addObject("tabs",tabs);
			modelandView.addObject("tabID", defaultTab);
			modelandView.addObject("alertHeaders", homeService.getAlerts(userModel.getUserSSO(), defaultTab));//To display alerts with count
		}catch(Exception e){
			modelandView = handleException(e, request);
		}
		
		return modelandView;
	}
	
	public List<UpstreamVendor> displayUpstreamVendor(HttpServletRequest request,int vendorNo){
		return null;
    	
    }
	
	
	/**
	 * This method used is used to get alert count to display in Home page
	 * Input : current tabId
	 * Output : String contains alertid and count corresponding to tabid
	 */
	
	@RequestMapping(value = "/getAlerts", method = {RequestMethod.GET})
	public @ResponseBody ModelAndView getAlerts(HttpServletRequest request,HttpServletResponse response,@RequestParam("tabId") int tabID){
		LOGGER.debug("Inside getAlerts()");
		ModelAndView model = new ModelAndView("/home/home");
		try{
			UserContext userModel = getUserContext(request);	
			model.addObject("tabID", tabID);
			model.addObject("alertHeaders", homeService.getAlerts(userModel.getUserSSO(), tabID));
		}catch(Exception e ){
			LOGGER.debug("Error while excecuting method");
			handleAjaxException(e, response, request);
		}
		return model;
	}

	
}
