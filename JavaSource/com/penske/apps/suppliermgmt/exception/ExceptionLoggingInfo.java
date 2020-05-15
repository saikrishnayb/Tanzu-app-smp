/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.exception;

import com.penske.apps.suppliermgmt.beans.LoggingHandlerExceptionResolver;

/**
 * Information about the current session state for formatting an exception with
 * {@link LoggingHandlerExceptionResolver#getExceptionLoggingInfo(com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean, javax.servlet.http.HttpServletRequest)}
 */
public class ExceptionLoggingInfo
{
	private final String sso;
	private final String serverName;
	private final String requestUrl;
	
	public ExceptionLoggingInfo(String sso, String serverName, String requestUrl)
	{
		this.sso = sso;
		this.serverName = serverName;
		this.requestUrl = requestUrl;
	}

	//***** DEFAULT ACCESSORS *****//
	/**
	 * @return the serverName
	 */
	public String getServerName()
	{
		return serverName;
	}

	/**
	 * @return the sso
	 */
	public String getSso()
	{
		return sso;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl()
	{
		return requestUrl;
	}
}
