/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File		: IsAuthorized.java
 * Package	: 
 * Desc		: Custom tag class for Security implementation
 * Author:          			Date:			Change Description:
 * PG					    20-Feb-2015			  Initial Version 
 * ************************************************************************************/
package com.penske.apps.suppliermgmt.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.suppliermgmt.util.SpringBeanHelper;


public class IsAuthorized extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3774778278319349446L;
	/**
	 * 
	 */

	
	
	public IsAuthorized() {
		super();
	}

	private String tabName = null;
	private String secFunction=null;


	/**
	 * @return the tabName
	 */
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public void setSecFunction(String secFunction) {
		this.secFunction = secFunction;
	}
	/**
	 * @return the secFunction
	 */
	public String getSecFunction() {
		return secFunction;
	}
	/**
	 * checks for tabName while JSP encounters custom tag,
	 * Validate the tabName from JSP in the functionList.
	 */
	public int  doStartTag() throws JspException
	{
		SecurityFunction securityFunction = SecurityFunction.findByName(secFunction);
		User user =   SpringBeanHelper.getUser();
		
		if(user == null)
			return SKIP_BODY;
		if(secFunction == null)
			return SKIP_BODY;
		
		if(user.hasSecurityFunction(securityFunction))
			return EVAL_BODY_INCLUDE;
		else
			return SKIP_BODY;
	}

	/**
	 * called after doStartTag method
	 * Will proceed to evaluate or skip the page based on return value
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/**
	 * Releases all instance variables.
	 */
	public void release() {
		super.release();
	}

}
