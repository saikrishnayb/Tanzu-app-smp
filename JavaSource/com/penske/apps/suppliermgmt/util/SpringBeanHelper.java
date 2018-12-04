/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.util;

import org.springframework.context.ApplicationContext;

import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * Spring-managed singleton bean that allows non-spring-managed beans (like MyBatis plugins)
 * 	to obtain a reference to some selected Spring beans.
 */
public class SpringBeanHelper
{
	/**
	 * Stores a reference to the application-context, so we can use it to get beans globally.
	 * 
	 * <p>
	 * This warning should go without saying, but it bears repeating, that this reference should <u>only</u>
	 * be used when there are no other alternative ways to access the bean and such access is truly necessary.
	 * Fetching beans from the context this way breaks the IoC model that Spring prefers to employ.
	 * </p>
	 * @see https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/access/BeanFactoryLocator.html
	 */
	private static ApplicationContext CTX;
	
	public SpringBeanHelper(ApplicationContext context)
	{
		CTX = context;
	}
	
	/**
	 * Gets the currently-logged-in user from the session bean. This should only be used when normal Spring dependency injection is impossible (ex: MyBatis plugins).
	 * @return The object for the currently-logged-in user, or null if no such object exists in the session bean.
	 */
	public static UserContext getUserContext()
	{
		if(CTX == null)
			return null;
		SuppliermgmtSessionBean sessionBean = CTX.getBean(SuppliermgmtSessionBean.class);
		if(sessionBean == null)
			return null;
		return sessionBean.getUserContext();
	}
}
