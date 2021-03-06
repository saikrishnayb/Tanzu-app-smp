
package com.penske.apps.suppliermgmt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.enums.LeftNav;
import com.penske.apps.adminconsole.enums.Tab;
import com.penske.apps.adminconsole.enums.Tab.SubTab;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.service.HelpService;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;

/************************************************************************************
 * @Author : 502403391
 * @Version : 1.0
 * @FileName : NavigationController
 * @Date Created : May 15,2015
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Class for redirecting to different application
 * @History : 
 ************************************************************************************/
@Version1Controller
@RequestMapping("navigation")
public class NavigationController extends BaseController {

    @Autowired
    private HelpService helpService;
    @Autowired
    private Environment springEnvironment;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    private static Logger LOGGER = LogManager.getLogger(NavigationController.class);

    @VendorAllowed
    @RequestMapping(value = "/navigate", method = RequestMethod.POST)
    @ResponseBody
    public String navigateapplication(@RequestParam(value = "path") String destination, @RequestParam(value = "controllerName") String controllerName, @RequestParam(value = "templateKey") String templateKey, HttpServletRequest request) throws Exception {

    	StringBuffer url = new StringBuffer();
    	String app = destination.toLowerCase();
        User user = sessionBean.getUser();

        if ("adminconsole".equalsIgnoreCase(destination)) {

            if (!templateKey.equalsIgnoreCase("0")) {
                // If user comes from Home page dash board
                url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append(ApplicationConstants.VENDOR_TEMPLATE_URL).append(templateKey);
            } else {
                // Dircetly On click of admin consoleTab

                Set<SecurityFunction> securityFunctions = user.getSecurityFunctions();

                List<SubTab> subTabs = Tab.ADMIN_CONSOLE.getSubTabs();

                subTabLoop: for (SubTab subTab : subTabs) {

                    List<LeftNav> leftNavs = subTab.getLeftNavs();

                    for (LeftNav leftNav : leftNavs) {

                        SecurityFunction securityFunction = leftNav.getSecurityFunction();

                        boolean noAccess = securityFunction != null && !securityFunctions.contains(securityFunction);
                        if (noAccess) continue;

                        url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append("app/").append(leftNav.getUrlEntry());
                        break subTabLoop;
                    }

                }

            }
        } else if ("Home".equalsIgnoreCase(destination)) {
            url.append(request.getContextPath()).append(ApplicationConstants.SLASH).append("app/home/homePage?tabId=").append(controllerName);
        } else {

            boolean isDevOrLocalEnvironment = isDevOrLocalEvironment();
            boolean isQa2Environment = isQA2Environment();

            url.append(ApplicationConstants.SLASH);
            url.append(app);
            if (isQa2Environment) url.append("2");

            if (isDevOrLocalEnvironment)
                url.append(ApplicationConstants.DEV_ENTRY_SERVLET).append(user.getSso());
            else
                url.append(ApplicationConstants.ENTRY_SERVLET);

            if (controllerName != null) {

                if (isDevOrLocalEnvironment)
                    url.append(ApplicationConstants.DEV_CONTROLLER_NAME);
                else
                    url.append(ApplicationConstants.CONTROLLER_NAME);

                url.append(controllerName);
            }
            if (!templateKey.equalsIgnoreCase("0")) {
                url.append(ApplicationConstants.DEV_TEMPLATE_KEY).append(templateKey);
            }
        }

        LOGGER.info("Navigating the iframe to the URL" + url);
        
        return url.toString();
    }

    @VendorAllowed
    @RequestMapping(value = "/getHelp")
    protected ModelAndView getHelp() {

        ModelAndView model = new ModelAndView();
        
        try {
            User user = sessionBean.getUser();
            String help = helpService.getHelp(user.getUserType());
            model.addObject("helpContent", help);
            model.setViewName("app-container/modal/help");

        } catch (Exception e) {
            model = handleException(e);
        }
        return model;
    }

    @VendorAllowed
    @RequestMapping(value = "/getHowTo")
    protected ModelAndView getHowTo() {
        return new ModelAndView("app-container/modal/howTo");
    }

    private boolean isDevOrLocalEvironment()
    {
    	List<String> profiles = getActiveSpringProfiles();
    	return profiles.contains(ProfileType.DEVELOPMENT) || profiles.contains(ProfileType.LOCAL);
    }    
    private boolean isQA2Environment()
    {
    	List<String> profiles = getActiveSpringProfiles();
    	return profiles.contains(ProfileType.QA2);
    }
    
    private List<String> getActiveSpringProfiles() {

        String[] activeProfiles = springEnvironment.getActiveProfiles();
        
        List<String> result = new ArrayList<String>();
        for(String profile : activeProfiles)
        {
        	if(StringUtils.isNotBlank(profile))
        		result.add(profile);
        }
        return result;
    }
}
