package com.penske.apps.suppliermgmt.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


public class SessionListener implements HttpSessionListener {
	
	private static Logger LOGGER = Logger.getLogger(SessionListener.class);

private static int totalActiveSessions;
 
  public static int getTotalActiveSession(){
	return totalActiveSessions;
  }
 
  @Override
  public void sessionCreated(HttpSessionEvent event) {
	totalActiveSessions++;
	HttpSession  session  = event.getSession();
	LOGGER.debug("sessionCreated - add one session into counter");
	LOGGER.debug("Session Interval::"+session.getMaxInactiveInterval());
	LOGGER.debug("Session Id::"+session.getId());
	LOGGER.debug("Last access time::"+session.getLastAccessedTime());
	LOGGER.debug("Last access time::"+session.getAttributeNames());
	
  }
 
  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
		
	  HttpSession  session  = event.getSession();
		LOGGER.debug("Session Interval::"+session.getMaxInactiveInterval());
		LOGGER.debug("Session Id::"+session.getId());
		LOGGER.debug("Last access time::"+session.getLastAccessedTime());
		totalActiveSessions--;
		LOGGER.debug("sessionDestroyed - deduct one session from counter");
  }


}
