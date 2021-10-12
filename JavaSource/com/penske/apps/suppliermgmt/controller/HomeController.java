package com.penske.apps.suppliermgmt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.smccore.base.beans.LookupManager;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.LookupKey;
import com.penske.apps.smccore.base.domain.enums.SmcTab;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.AlertHeader;
import com.penske.apps.suppliermgmt.model.AlertView;
import com.penske.apps.suppliermgmt.model.Tab;
import com.penske.apps.suppliermgmt.service.HomeDashboardService;


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

@Version1Controller
@RequestMapping("/home")
public class HomeController extends BaseController{

    @Autowired
    private HomeDashboardService homeService;

    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    
    @Autowired
    private LookupManager lookupManager;

    @VendorAllowed
    @RequestMapping("/displayHome")
    public ModelAndView displayHome(){
    	LookupContainer lookups = lookupManager.getLookupContainer();
        ModelAndView modelAndView = null;
        try{
            User user = sessionBean.getUser();
            modelAndView=new ModelAndView("app-container/appContainer");
            
            //getting support num from lookup
            String supportNumber = lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM);
            modelAndView.addObject("supportNum", supportNumber);

            modelAndView.addObject("vendorUser", user.getUserType() == UserType.VENDOR);
            modelAndView.addObject("userDetails",user);
        }catch(Exception e){
            modelAndView = handleException(e);
        }
        return modelAndView;
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
    	LookupContainer lookups = lookupManager.getLookupContainer();
        ModelAndView modelandView = new ModelAndView("/home/home");
        List<AlertHeader> alertHeaders=new ArrayList<AlertHeader>();
        Map<Integer, List<AlertView>> alertsByHeaderId = new HashMap<>();
        try{
        	User user = sessionBean.getUser();
        	SmcTab smcTab = SmcTab.findByTabKey(tabId);
        	
            List<Tab> tabs = homeService.selectTabs(user);
            SmcTab defaultTab = tabs.isEmpty() ? null : tabs.stream()
            	.map(Tab::getSmcTab)
            	.filter(t -> t == smcTab)
            	.findAny()
            	.orElse(tabs.get(0).getSmcTab());
            
            if(defaultTab == SmcTab.ORDER_FULFILLMENT && user.getUserType() == UserType.VENDOR)
            	defaultTab = tabs.get(1).getSmcTab();
            
            
            if(defaultTab != null) {
            	alertHeaders = homeService.getAlertHeaders(defaultTab);
                alertsByHeaderId = homeService.getAlertsByHeaderId(user, defaultTab, alertHeaders);
            }

            String supportNumber = lookups.getSingleLookupValue(LookupKey.SUPPORT_PHONE_NUM);
            modelandView.addObject("supportNum", supportNumber);
            modelandView.addObject("tabs",tabs);
            modelandView.addObject("TabKey", defaultTab != null ? defaultTab.getTabKey() : null);
            modelandView.addObject("alertHeaders", alertHeaders);//To display alerts with count
            modelandView.addObject("alertsByHeaderId", alertsByHeaderId);
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
        ModelAndView model = new ModelAndView("/home/home");
        try{
        	SmcTab smcTab = SmcTab.findByTabKey(tabKey);
            User user = sessionBean.getUser();
            
            List<AlertHeader> alertHeaders = homeService.getAlertHeaders(smcTab);
            Map<Integer, List<AlertView>> alertsByHeaderId = homeService.getAlertsByHeaderId(user, smcTab, alertHeaders);
            
            model.addObject("TabKey", tabKey);
            model.addObject("alertHeaders", alertHeaders);
            model.addObject("alertsByHeaderId", alertsByHeaderId);
        }catch(Exception e ){
            handleAjaxException(e, response);
        }
        return model;
    }


}
