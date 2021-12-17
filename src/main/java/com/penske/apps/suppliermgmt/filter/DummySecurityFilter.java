package com.penske.apps.suppliermgmt.filter;
/**
 *****************************************************************************************************************
 * File Name     : DummySecurityFilter
 * Description   : Filter for checking the security 
 * Project       : SMC
 * Package       : com.penske.apps.smcop.filter
 * Author        : 502403391
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import java.io.IOException;

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

import com.penske.business.ldap.CPBLDAPSessionInfo;
import com.penske.util.CPTBaseServlet;


public class DummySecurityFilter implements Filter {
	private static Logger LOGGER = LogManager.getLogger(DummySecurityFilter.class);

	public DummySecurityFilter() 
	{
		super();
	}

	public void destroy() {	}

	/**
	 * 
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		//  Invalidate an existing session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		//  Create the session for the application
		session = request.getSession(true);
		
		//  Retrieve the SSOID from the header
		String ssoId = req.getParameter("SSOID");

		//  Determine if the credentials are valid
		boolean areCredentialsValid = (ssoId != null && ssoId.length() != 0);

		if (areCredentialsValid) {
			CPBLDAPSessionInfo ldap = new CPBLDAPSessionInfo ( );
			ldap.setLoginUserID(ssoId);
			req.setAttribute("SessionInfo", ldap);

			//  Invalidate an existing session
			if (session != null) {
				session.invalidate();
			}

			//  Create the session for the application
			request.getSession(true);

			LOGGER.debug("A valid session was created.");
			chain.doFilter(req, resp);
		}
		else {
			LOGGER.error("The user does not have valid credentials.  Sending them away.");
			CPTBaseServlet.logoff(request, response);
		}
	}

	/**
	* Method init.
	* @param config
	* @throws javax.servlet.ServletException
	*/
	public void init(FilterConfig config) throws ServletException {
	}

}

