package com.penske.apps.adminconsole.filter;

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

import org.apache.log4j.Logger;

import com.penske.business.ldap.CPBLDAPSessionInfo;
import com.penske.util.CPTBaseServlet;

public class SessionValidationFilter implements Filter{
    private static final String SESSION_INFO = "SessionInfo";
    private static Logger logger = Logger.getLogger(SessionValidationFilter.class); 
  
    public void init(FilterConfig arg0) throws ServletException
    {}
    public void destroy()
    {}
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        boolean hasloggedOff = logOffIfApplicable(request, response);
        boolean shouldExitMethod = hasloggedOff;
        
        if(shouldExitMethod) return;
        
        filterChain.doFilter(request, response);
    }
    
    private boolean logOffIfApplicable(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        CPBLDAPSessionInfo sessionInfoFromSession = (CPBLDAPSessionInfo) session.getAttribute(SESSION_INFO);
        CPBLDAPSessionInfo sessionInfoFromRequest = (CPBLDAPSessionInfo) request.getAttribute(SESSION_INFO);

        boolean hasLdapInformationInSession = sessionInfoFromSession != null;
        boolean hasLdapInformationInRequest = sessionInfoFromRequest != null;
        boolean hasLdapInformation = hasLdapInformationInSession || hasLdapInformationInRequest; 
        boolean shouldLogOff = !hasLdapInformation;
        boolean hasLoggedOff = shouldLogOff;
        if(shouldLogOff) logOff(request,response);
        
        return hasLoggedOff;
    }
    
    private void logOff(HttpServletRequest request, HttpServletResponse response){
        String remoteAddr = request.getRemoteAddr();
        String message = "Logging off user:" + remoteAddr;
        logger.info(message);
        CPTBaseServlet.logoff(request,response);
    }
}
