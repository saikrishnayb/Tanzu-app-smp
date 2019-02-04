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

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.penske.apps.suppliermgmt.model.UserContext;
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
		
		
		UserContext userRuleModel =   SpringBeanHelper.getUserContext();
		if(userRuleModel!=null){
			Map<String, Map<String, String>> userRuleMap =userRuleModel.getTabSecFunctionMap();	
			if(userRuleMap != null){
				
				Map<String,String> secFunctions=userRuleMap.get(tabName);
				if(secFunction.equalsIgnoreCase("USERS_MANAGEMENT")){
					if (userRuleMap.containsKey(tabName)&&(secFunctions!=null && (secFunctions.containsKey("MANAGE_VENDOR_USERS")
							|| secFunctions.containsKey("MANAGE_USERS")))){
							{
								
								return EVAL_BODY_INCLUDE;
							}
						}
				}
				
				if (userRuleMap.containsKey(tabName)&&secFunctions.containsKey(secFunction)&&secFunctions!=null){
					{
						
						return EVAL_BODY_INCLUDE;
					}
				}
			}
		}
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