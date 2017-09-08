package com.penske.apps.suppliermgmt.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.business.ldap.CPBLDAPSessionInfo;






/*******************************************************************************
 *
 * @Author 		: 502299699
 * @Version 	: 1.0
 * @Date Created: Feb 10, 2015
 * @Date Modified : Feb 10, 2015
 * @Modified By : 502299699
 * @Contact 	:
 * @Description : This is the entry servlet to the application. Here all the User 
 * 				  related details and security rules are restored to the User Context 
 * 				  for the logged in User
 * @History		:
 *
 ******************************************************************************/
public class ApplicationEntry extends HttpServlet {
	ServletContext smcContext = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ApplicationEntry() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		smcContext = config.getServletContext();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	private static final Logger LOGGER = Logger.getLogger(ApplicationEntry.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
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
					session.setAttribute(ApplicationConstants.USER_SSO, oLdapSession.getLoginUserID());
					forwardPage = "/login/validate.htm";	
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
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
