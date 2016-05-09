/*******************************************************************************
 *
 * @Author 		: 502403391
 * @Version 	: 1.0
 * @Date Created: Jul 16, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : This servlet is used to log off the user session.
 * @History		:
 *
 ******************************************************************************/
package com.penske.apps.suppliermgmt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.util.CPTBaseServlet;


public class SMCLogOff extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6798430280467550983L;
	//	 Initialize Logger Object
	private static final Logger LOGGER = Logger.getLogger(SMCLogOff.class);
	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logoff(req, resp);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logoff(req, resp);
	}

	
		
	public static void logoff(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String realPath = null;
		String strUrl = null;
		String url = null;
		String cookieDomain = getCookieDomain(CPTBaseServlet.getDomain(req));
		try
        {	
			if(StringUtils.equalsIgnoreCase(cookieDomain, ApplicationConstants.COOKIE_DOMAIN_DEV)){	//if it is dev environment
				
				 realPath ="https://cm.gopenske.com/cookie/ssoLogout.jsp";	//no need to get the ssologouturl bean
				 LOGGER.debug("Setting Dev environment Logoffpath");
				
			}
			else{
				
			}
			 WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
					 req.getSession().getServletContext());
			 realPath = (String)applicationContext.getBean("ssoLogoutUrl");
			 
        }
        catch (Exception nex)
        {
           LOGGER.error("There was an error while looking up the ssologout URL provider."+nex.getMessage(),nex);
           realPath ="https://cm.gopenske.com/cookie/ssoLogout.jsp";
        }
        // invalidate the existing session
        HttpSession session = req.getSession(false);
        if(session != null){
        	session.invalidate();
        }
		//Get the Application context to get the lookup value for APPLICATION URL 
		try{
			
			LOGGER.debug("App domain->"+CPTBaseServlet.getDomain(req)+" / cookieDomain->"+cookieDomain);
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equalsIgnoreCase("SMSESSION") || cookie.getName().equalsIgnoreCase("SMSESSION=")) {
		                cookie.setValue(null);
		                cookie.setMaxAge(0);
		                cookie.setDomain(cookieDomain);
		                cookie.setPath("/");
		                LOGGER.debug("SMSESSION cookie cleared.");
		                resp.addCookie(cookie);
		            }
		        }
		    }
			if (req.getHeader("host").length() > 0) { 	//host check for Local 
				LOGGER.debug("After servlet context lost due to invalid session....");
	            strUrl = req.getScheme()+"://"+req.getHeader("host")+req.getContextPath()+"/entry/ApplicationEntry";
	            url = realPath + "?returnURL=" + strUrl;
			} 
		}catch(Exception ex){
			LOGGER.error("Error occured while resetting the SMSESSION cookie to Null on logoff"+ex.getMessage(),ex);
		}
		
		LOGGER.debug("Logout url to be forwarded while logging off-->"+url);
		//Set next screen to be displayed - Logout URL 
		resp.sendRedirect(url);
	}
	
	public static String getCookieDomain(String strDomain ){
		String   strExtranetDomain     = ApplicationConstants.EXTRANET_DOMAIN;
		String cookieDomain = null;
		//if it is PROD/QA
		if ( strDomain!= null && strDomain.toUpperCase().indexOf( strExtranetDomain )==0 )
		{
			cookieDomain = ApplicationConstants.COOKIE_DOMAIN_PRODQA;
		}	
		else{
			cookieDomain = ApplicationConstants.COOKIE_DOMAIN_DEV;
		}
		
    	return cookieDomain;

	}

}
