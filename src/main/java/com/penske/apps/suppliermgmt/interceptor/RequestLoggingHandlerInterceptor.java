/**
 * @author John Shiffler
 */
package com.penske.apps.suppliermgmt.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.plugins.CoreTimingType;
import com.penske.apps.smccore.base.plugins.TimingBean;
import com.penske.apps.smccore.base.plugins.TimingType;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;

/**
 * Logs the server name, the SSO of the user, the request URL, and the time each request came into the server.
 * On the way out, also logs various information for Splunk and processing time.
 */
@Component
public class RequestLoggingHandlerInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = LogManager.getLogger(RequestLoggingHandlerInterceptor.class);
	private static final Logger splunk = LogManager.getLogger("splunk");
	/** True if detailed request timing information should be written to TRACE-level logs. */
	private boolean logTimings = false;
	
	@Autowired
	private SuppliermgmtSessionBean sessionBean;

	/**
	 * Holds detailed timing information on various parts of the request processing.
	 * This is marked "required=false" because the TimingSessionBean may not be registered with
	 * Spring in some targeted deployment environments (like production, for instance), to improve performance.
	 */
	@Autowired(required=false)
	private TimingBean timingBean;
	
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
			String ssoid = getSSSOID();
			String module = "";
			
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
			logTimings(requestUrl, executeTime);
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
	
	private String getSSSOID()
	{
		User user = sessionBean.getUser();
		if(user == null)
			return null;
		
		return user.getSso();
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

	/**
	 * Writes detailed timing information to the TRACE-level logs.
	 * @param requestUrl The URL that the user requested.
	 * @param overallRequestLength The amount of time the entire request took to execute.
	 */
	private void logTimings(String requestUrl, long overallRequestLength)
	{
		if(!logTimings || timingBean == null)
			return;
		
		if(requestUrl.contains("getSaveStatus"))
			return;
		
		Map<TimingType, Pair<Long, Long>> timings = timingBean.getAllTimings();
		
		if(splunk.isTraceEnabled())
		{
			Pair<Long, Long> queryInfo = timings.get(CoreTimingType.SMC_CORE_QUERY);
			Long queryTime = queryInfo == null ? null : queryInfo.getLeft();
			String splunkString = new StringBuilder("TIMINGS - ")
				.append(CoreTimingType.SMC_CORE_QUERY.getDescription())
				.append(": ")
				.append(queryTime == null ? 0 : queryTime)
				.toString();
			splunk.trace(splunkString);
		}
		
		if(logger.isTraceEnabled())
		{
			StringBuilder loggerSb = new StringBuilder("\n== TIMINGS ==\n");
			loggerSb.append("  Overall: ").append(overallRequestLength);
			for(TimingType timingType : timings.keySet())
			{
				Pair<Long, Long> queryInfo = timings.get(timingType);
				Long elapsedTime = queryInfo.getLeft();
				loggerSb.append("\n  ")
					.append(timingType.getDescription())
					.append(": ")
					.append(elapsedTime);
			}
			logger.trace(loggerSb.toString());
		}
	}
	
	/**
	 * Setter so the Spring container can inject a flag for whether to log detailed timing information.
	 * @param logTimings True to log detailed timing information. False to omit it.
	 * 	If this is true, the {@link TimingRequestBean} must be registered as a session bean with Spring.
	 */
	public void setLogTimings(boolean logTimings)
	{
		this.logTimings = logTimings;
	}
}
