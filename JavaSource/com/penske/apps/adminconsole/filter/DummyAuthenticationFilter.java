package com.penske.apps.adminconsole.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.penske.apps.dlinks.filters.DLinksAuthenticationFilter;
import com.penske.business.ldap.CPBLDAPSessionInfo;

/**
 * This class is dummy filter class. It should not be used anywhere besides locally.
 */
public class DummyAuthenticationFilter extends DLinksAuthenticationFilter implements Filter{
	
	private static final String SESSION_INFO = "SessionInfo";
    private static final String SSO_ID = "SSOID";
    private static Logger logger = Logger.getLogger(DummyAuthenticationFilter.class);	
    
	public void init(FilterConfig config) throws ServletException 
	{}	
	public void destroy() 
	{}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
	    logger.debug("Inside Dummy Filter...");

	    HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = getSession(request);
		
		String loginUserID = request.getParameter(SSO_ID);		
		CPBLDAPSessionInfo sessionInfo = getSessionInfo(loginUserID);
		
		request.setAttribute(SESSION_INFO, sessionInfo);
		session.setAttribute(SESSION_INFO, sessionInfo);

		filterChain.doFilter(servletRequest, servletResponse);
	}

    private HttpSession getSession(HttpServletRequest request){
    	
        HttpSession session = request.getSession(true);
        session.invalidate();
        session = request.getSession(true);
        
        return session;
    }

    private CPBLDAPSessionInfo getSessionInfo(String loginUserID){

		CPBLDAPSessionInfo sessionInfo = new CPBLDAPSessionInfo();
		sessionInfo.setLoginUserID(loginUserID);
		
		//TODO add anything else needed to the session info
//		sessionInfo.setLoginUserID(loginUserID);
//		sessionInfo.setCompany(gessoBusinessUnit);
//      sessionInfo.setFirstName(firstName);
//      sessionInfo.setLastName(lastName);
//      sessionInfo.setGESSOMailStop(gessoMailStop);
//      sessionInfo.setGESSOTitle(gessoAssignment);

        return sessionInfo; 
    }

}
