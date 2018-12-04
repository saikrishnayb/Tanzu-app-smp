package com.penske.apps.suppliermgmt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.service.HomeDashboardService;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.util.LookupManager;


/************************************************************************************
 * @Author         : 502299699
 * @Version        : 1.0
 * @FileName       : HomeController
 * @Date Created	  : May 15,2015
 * @Date Modified  : May 13,2016
 * @Modified By    : Seenu
 * @Contact        :
 * @Description    : Controller class for Home/dashboard page. This class is used to
 * 					process request for displaying Home/Dashboard page details.
 *
 ************************************************************************************/

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{

    @Autowired
    private HomeDashboardService homeService;

    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    private static final Logger LOGGER = Logger.getLogger(HomeController.class);

    /**
     * @param request
     * @return
     */
    @VendorAllowed
    @RequestMapping("/displayHome")
    public ModelAndView displayHome(){
        LOGGER.debug("Inside displayHome()");
        ModelAndView modelandView = null;
        // String helpLink=null;
        try{
            UserContext userModel = sessionBean.getUserContext();
            modelandView=new ModelAndView("app-container/appContainer");
            //getting support num from lookup
            LookupManager lookupManger=new LookupManager();
            List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
            LookUp lookUp=null;
            if(suppNumlist!=null){
                lookUp=suppNumlist.get(0);
                LOGGER.debug("Suport Num :"+lookUp.getLookUpValue());
                modelandView.addObject("supportNum",lookUp.getLookUpValue());
            }

            /* Help Info will be loaded from Database as HTML going forward

			if(userModel.getUserType()==ApplicationConstants.PENSKE_USER_TYPE){
				helpLink=lookupManger.getLookUpListByValue(ApplicationConstants.HELP_LINK,ApplicationConstants.INT_ONE);
				LOGGER.debug("HELP_LINK :"+helpLink);
			}
			else{
				helpLink=lookupManger.getLookUpListByValue(ApplicationConstants.HELP_LINK,ApplicationConstants.INT_TWO);
				LOGGER.debug("HELP_LINK :"+helpLink);
			}
			modelandView.addObject("helpLink",helpLink);
             */

            modelandView.addObject("userDetails",userModel);
        }catch(Exception e){
            modelandView = handleException(e);
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
    @VendorAllowed
    @RequestMapping(value = "/homePage", method = {RequestMethod.GET})
    public ModelAndView getHomePage(@RequestParam("tabId") String tabId){
        LOGGER.debug("Inside getHomePage()");
        ModelAndView modelandView = new ModelAndView("/home/home");
        String defaultTab="";
        List<String> tabIdList=new ArrayList<String>();
        List<AlertHeader> alertHeaders=new ArrayList<AlertHeader>();
        try{
            UserContext userModel = sessionBean.getUserContext();
            List<Tab> tabs = homeService.selectTabs(userModel);
            for(Tab tab:tabs) {
                tabIdList.add(tab.getTabKey());
            }

            if(tabIdList.contains(tabId)) {
                defaultTab=tabId;
            }else{
                if(tabs!=null && !tabs.isEmpty()){
                    defaultTab = tabs.get(0).getTabKey();

                    if (defaultTab.equals("TAB_OF") && userModel.isVendorUser())
                        defaultTab = tabs.get(1).getTabKey();

                }
            }

            if(!"".equalsIgnoreCase(defaultTab)){
                alertHeaders = homeService.getAlerts(userModel.getUserSSO(), defaultTab,userModel.getUserType());
            }

            LookupManager lookupManger=new LookupManager();
            List<LookUp> suppNumlist=lookupManger.getLookUpListByName(ApplicationConstants.SUPP_NUM);
            LookUp lookUp=null;
            if(suppNumlist!=null){
                lookUp=suppNumlist.get(0);
                LOGGER.debug("Suport Num :"+lookUp.getLookUpValue());
                modelandView.addObject("supportNum",lookUp.getLookUpValue());
            }
            modelandView.addObject("tabs",tabs);
            modelandView.addObject("TabKey", defaultTab);
            modelandView.addObject("alertHeaders", alertHeaders);//To display alerts with count
        }catch(Exception e){
            modelandView = handleException(e);
        }

        return modelandView;
    }


    /**
     * This method used is used to get alert count to display in Home page
     * Input : current tabId
     * Output : String contains alertid and count corresponding to tabid
     */

    @VendorAllowed
    @RequestMapping(value = "/getAlerts", method = {RequestMethod.GET})
    public @ResponseBody ModelAndView getAlerts(HttpServletResponse response,@RequestParam("tabKey") String tabKey){
        LOGGER.debug("Inside getAlerts()");
        ModelAndView model = new ModelAndView("/home/home");
        try{
            UserContext userModel = sessionBean.getUserContext();
            model.addObject("TabKey", tabKey);
            model.addObject("alertHeaders", homeService.getAlerts(userModel.getUserSSO(), tabKey,userModel.getUserType()));
        }catch(Exception e ){
            LOGGER.debug("Error while excecuting method");
            handleAjaxException(e, response);
        }
        return model;
    }


}
