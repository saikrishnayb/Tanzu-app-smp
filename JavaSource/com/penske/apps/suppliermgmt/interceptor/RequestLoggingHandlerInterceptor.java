/**
 * @author John Shiffler
 */
package com.penske.apps.suppliermgmt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * Logs the server name, the SSO of the user, the request URL, and the time each request came into the server.
 * On the way out, also logs various information for Splunk and processing time.
 */
@Component
public class RequestLoggingHandlerInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = Logger.getLogger(RequestLoggingHandlerInterceptor.class);
	private static final Logger splunk = Logger.getLogger("splunk");

	/**
	 * Override: @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		try {
			long startTime = System.currentTimeMillis();
			request.setAttribute("startTime", startTime);
		} catch(RuntimeException ex) {
			//Just log an error message, and keep going, as this is a non-critical function.
			String msg = new StringBuilder("Failed to log request information: ")
				.append(ex.getMessage()).append("\n")
				.append(StringUtils.join(ex.getStackTrace(), "\n"))
				.toString();
			logger.error(msg);
		}
		
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * We use {@link #afterCompletion(HttpServletRequest, HttpServletResponse, Object, Exception)} instead of {@link #postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView)}
	 * since <code>postHandle</code> does not get called if the handler throws an exception, and we want to log even if there was an exception.
	 * 
	 * Override: @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
		try {
			long endTime = System.currentTimeMillis();
			long startTime = (Long) request.getAttribute("startTime");
			long executeTime = endTime - startTime;
			
			StringBuffer requestUrlBuffer = request.getRequestURL();
			String requestUrl = requestUrlBuffer == null ? null : requestUrlBuffer.toString();
			
			String serverName = request.getLocalName();
			String ssoid = getSSSOID(request);
			String module = "suppliermgmt";
			
			String moduleSpecificInformation = getModuleSpecificInformation();
			
			StringBuilder logBuilder = new StringBuilder(serverName).append(" - ")
				.append("ssoid ").append(ssoid).append(" - ")
				.append("module ").append(module).append(" - ")
				.append(requestUrl).append(" - ")
				.append(executeTime).append(" ms");
			
			//Get module-specific fields
			if(StringUtils.isNotBlank(moduleSpecificInformation))
				logBuilder.append(" !! ").append(moduleSpecificInformation);
			
			String logMsg = logBuilder.toString();
			
			logger.info("Finished Request: " + logMsg);
			splunk.info(logMsg);
		} catch(RuntimeException ex2) {
			//Just log an error message, and keep going, as this is a non-critical function.
			String msg = new StringBuilder("Failed to log performance information due to exception in postHandle: ")
				.append(ex2.getMessage()).append("\n")
				.append(StringUtils.join(ex2.getStackTrace(), "\n"))
				.toString();
			logger.error(msg);
		}
		
		super.afterCompletion(request, response, handler, ex);
	}
	
	private String getSSSOID(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session == null)
			return null;
		
		UserContext userContext = (UserContext) session.getAttribute(ApplicationConstants.USER_MODEL);
		if(userContext == null)
			return null;
		
		return userContext.getUserSSO();
	}
	
	/**
	 * This method can be used to return information that is specific to this app, as opposed to information shared across all SMC web applications.
	 * The default implementation just returns an empty string. If further information is needed to be aggregated by Splunk in the future, it can be written here.
	 * @return Fields that should be tracked in splunk that are specific to this application
	 */
	private String getModuleSpecificInformation()
	{
		return "";
	}
}
