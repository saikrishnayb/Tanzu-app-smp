/**
 * @author John Shiffler
 */
package com.penske.apps.suppliermgmt.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.exception.ExceptionLoggingInfo;

/**
 * Designed to catch all exceptions that bubble up from controller methods, and log them to the error log.
 */
@Component
public class LoggingHandlerExceptionResolver implements HandlerExceptionResolver, Ordered
{
    private static final Logger logger = Logger.getLogger(LoggingHandlerExceptionResolver.class);

    @Autowired(required=false)
    private SuppliermgmtSessionBean sessionBean;

    public static String formatExceptionMessage(Throwable ex, String sso, String serverName, String requestUrl)
    {
        StringBuilder error = new StringBuilder();
        if(serverName != null)
        {
            error.append("Server Name: ")
            .append(serverName)
            .append("\n");
        }
        if(sso != null)
        {
            error.append("User SSO: ")
            .append(sso)
            .append("\n");
        }
        if(requestUrl != null)
        {
            error.append("Request URL: ")
            .append(requestUrl)
            .append("\n");
        }
        
        if(ex instanceof UnauthorizedSecurityFunctionException)
        {
        	UnauthorizedSecurityFunctionException securityEx = (UnauthorizedSecurityFunctionException) ex;
        	
			error.append("UnauthorizedSecurityFunctionException. ")
				.append("User ")
				.append(sso)
				.append(" does not have access to the following security functions: ");

			SecurityFunction[] securityFunctions = securityEx.getSecurityFunctions();
			error.append(StringUtils.join(securityFunctions, ", "))
				.append("\n");
        }

        error.append(ex.getClass())
	        .append(": ")
	        .append(ex.getMessage())
	        .append("\n")
	        .append(StringUtils.join(ex.getStackTrace(), "\n"));

        return error.toString();
    }

    public static ExceptionLoggingInfo getExceptionLoggingInfo(SuppliermgmtSessionBean sessionBean, HttpServletRequest request)
    {
        //Try to determine the user and IDs that were being worked on at the time of the error
        String sso;
        try {
            sso = sessionBean.getUser() == null ? null : sessionBean.getUser().getSso();
            
            //If it's not in the session bean, try to get them from the session itself
            if(sso == null)
            {
            	HttpSession session = request.getSession(false);
                sso = (String) session.getAttribute("SSOID");
            }

            //If it's not in the session bean or the session, check the request attributes as one more last-ditch effort.
            if(sso == null)
                sso = (String) request.getAttribute("SSOID");
        } catch(RuntimeException ex2) {
            //Eat any exception that gets thrown as a result of being unable to get the spec data bean or session.
            ex2.toString();
            sso = null;
            logger.warn("Exception while fetching user information from session for error logging.");
            logger.warn(ex2.getMessage());
            logger.warn(StringUtils.join(ex2.getStackTrace(), "\n"));
        }

        String serverName;
        String requestUrl;
        try {
            serverName = request.getLocalName();
            StringBuffer url = request.getRequestURL();
            requestUrl = url == null ? null : url.toString();
        } catch(RuntimeException ex2) {
            //Eat any exception that gets thrown as a result of being unable to get the server name.
            ex2.toString();
            serverName = null;
            requestUrl = null;
        }

        return new ExceptionLoggingInfo(sso, serverName, requestUrl);
    }

    /**
     * This always returns {@link Integer#MIN_VALUE}, because we want this handler to
     * always get called in the resolver chain.
     * Override: @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder()
    {
        return Integer.MIN_VALUE;
    }

    /**
     * This handler just logs the exception, and passes it on for further exception handling, if necessary.
     * Override: @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex)
    {
        ExceptionLoggingInfo loggingInfo = getExceptionLoggingInfo(sessionBean, req);
        String error = formatExceptionMessage(ex, loggingInfo.getSso(), loggingInfo.getServerName(), loggingInfo.getRequestUrl());

        //If this is an exception our application threw, check if it should be logged as an error or a warning
        boolean logAsError = true;
        if(ex instanceof HumanReadableException)
        {
            HumanReadableException humanReadable = (HumanReadableException) ex;
            if(!humanReadable.isCritical())
                logAsError = false;
        }

        if(logAsError)
            logger.error(error.toString());
        else
            logger.warn(error.toString());
        return null;
    }
}
