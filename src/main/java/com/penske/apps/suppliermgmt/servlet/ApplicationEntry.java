package com.penske.apps.suppliermgmt.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.penske.business.ldap.CPBLDAPSessionInfo;

/**
 * This is the entry servlet to the application. Here all the User 
 * related details and security rules are restored to the User Context 
 * for the logged in User
 */
public class ApplicationEntry extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ApplicationEntry.class);
	
	/**
	 * The name of the session variable that holds the user's SSOID.
	 * Only used during the login and authentication process, so it's not included in ApplicationConstants.
	 */
	public static final String USER_SSO = "userSSO";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strSSO = null;
		String forwardPage 	= null;		
		CPBLDAPSessionInfo oLdapSession = null;
		try
		{
			//Get the session associated with this request
			HttpSession session = request.getSession( false );
			
			//If session is available		
			if (session != null)
			{
				//Get the Signed In User's SSO_ID from the request object
				strSSO =(String) request.getAttribute( "SSOID" );	
				LOGGER.info("The value of SSO ID in Suppliermgmt::"+strSSO);
				// Signed in User
				LOGGER.info ( "SSO Mode - Signed In User details in request for Suppliermgmt : " + strSSO );
				oLdapSession = ( CPBLDAPSessionInfo ) request.getAttribute( "SessionInfo" );
				
				if( oLdapSession != null ){
					LOGGER.info("SSO from LDAP in Suppliermgmt::"+oLdapSession.getLoginUserID());
					LOGGER.info("First Name + Last Name "+oLdapSession.getFirstName()+ " "+oLdapSession.getLastName());
					session.setAttribute(USER_SSO, oLdapSession.getLoginUserID());
					forwardPage = "/app/login/validate.htm";	
				}else
				{
					LOGGER.info( " LDAP information not obtained for user in Suppliermgmt"+strSSO);
					SMCLogOff.logoff(request,response);	
				}	
				//response.sendRedirect(forwardPage);
				ServletContext context= getServletContext();
				RequestDispatcher rd= context.getRequestDispatcher(forwardPage);
				rd.forward(request, response);

			}else
			{
				LOGGER.info( "No HttpSession associated with this request in Suppliermgmt." );
				// set the next screen to be shown as the log off screen
				SMCLogOff.logoff(request,response);			
			}
		}
		catch (Throwable t)
		{
			LOGGER.error("Unexpected error in Suppliermgmt ApplicationEntry. Sending to Log Off."+t.getMessage(),t);
			//set the next screen to be shown as the log off screen
			SMCLogOff.logoff(request,response);	
		}

	}
}
