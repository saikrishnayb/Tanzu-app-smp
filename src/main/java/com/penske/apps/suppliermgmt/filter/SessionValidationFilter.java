package com.penske.apps.suppliermgmt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.penske.apps.suppliermgmt.servlet.SMCLogOff;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;
import com.penske.business.ldap.CPBLDAPSessionInfo;

/**
 * This filter performs a number of functions. <br>
 * 1. Validate that the user has a valid HttpSession. <br>
 * 2. Validate that the user has a valid userContext, except for when logging
 * in. <br>
 * 3. Catch and log all unhandled errors that "bubble" up to the filter <br>
 * This filter should be configured to catch all urls: /*<br>
 * <br>
 * <br>
 * &copy; Copyright 2005, Penske Truck Leasing, All Rights Reserved <br>
 * 
 * @author Donald R. Kichline
 * @version $$Revision:
 * @since 1.0
 * @see HttpSession
 */
public class SessionValidationFilter implements Filter {
    private static Logger LOGGER = LogManager.getLogger(SessionValidationFilter.class);

    /**
     * Default destroy method, does nothing
     */
    public void destroy() {
    }

    /**
     * The main filter method, gets called on every request
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /* SSO ID is set by the filter */
       HttpSession httpSession = request.getSession(false);
//       String domain = CPTBaseServlet.getDomain(request).toLowerCase();
	 
       String urlreferer = request.getHeader(ApplicationConstants.REFERER);
             
       LOGGER.info("Checking the URL request.getHeader(referer):"+urlreferer);
       
        if (httpSession == null) {
			LOGGER.info("ldap session information was not found.");
			SMCLogOff.logoff(request,response);
			return;
		}
        else
        {
        try {
        	String ssoId = (String)httpSession.getAttribute(ApplicationConstants.SSOID);
        	if (ssoId == null) {
        		LOGGER.info("ssoid is null, checking if sessioninfo exists");
				CPBLDAPSessionInfo sessionInfo = (CPBLDAPSessionInfo)	request.getAttribute("SessionInfo");
				if (sessionInfo != null)
				{ 
					LOGGER.info("session info found");
					ssoId = sessionInfo.getLoginUserID();
					httpSession.setAttribute(ApplicationConstants.SSOID, ssoId);
					request.setAttribute(ApplicationConstants.SSOID, ssoId);
					LOGGER.info(" LdapSessionInfo Object  ..... " + ssoId);
				}
        	}
        	if (ssoId == null) {
        			LOGGER.info("No SSO ID from the ldap session. Logging Off user");
        			SMCLogOff.logoff(request,response);
					return;
        	}
        	LOGGER.info("session validation filter before doFilter()");
        	chain.doFilter(req, resp);
        }
        catch (Throwable th) {
         /* All unhandled exceptions are caught at this point
            log the exception and continue*/
            this.logThrowable(request, th);
        }
        }
    }

    /**
     * Method init. Not used.
     * 
     * @param config
     * @throws javax.servlet.ServletException
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /*Provide the ability to log any throwable errors that "bubble"
   up to the filter. This means that the error was unhandled.*/
    private void logThrowable(HttpServletRequest request, Throwable th) {

        StringBuffer message = new StringBuffer();

        message.append("Unhandled Exception - [url = '");
        message.append(request.getServletPath());
        message.append("']");

        String referer = request.getHeader(ApplicationConstants.REFERER);
        if (referer != null) {
            message.append(" [referer = '");
            message.append(referer);
            message.append("']");
        }
        if (th instanceof ServletException) {
            ServletException ex = (ServletException) th;
            Throwable rootcause = ex.getRootCause();
            if (rootcause != null) {
                message.append(" ServletException.RootCause ");
                message.append(SessionValidationFilter.getStackTrace(rootcause));
            }
        }

        Throwable cause = th.getCause();

        if (cause != null) {
            message.append(" Throwable.Cause ");
            message.append(SessionValidationFilter.getStackTrace(cause));
        }

        LOGGER.error("Exception in session validation filter logThrowable() method in suppliermgmt"+message.toString(), th);
    }

   /* Dump the stack trace as a string*/
    private static String getStackTrace(Throwable th) {
    	Writer stackTrace = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stackTrace);
    	try{	        
	        th.printStackTrace(printWriter);
        }catch(Exception e){
        	LOGGER.error(e);
        }finally{
        	printWriter.close();
        }
        return stackTrace.toString();
    }
}