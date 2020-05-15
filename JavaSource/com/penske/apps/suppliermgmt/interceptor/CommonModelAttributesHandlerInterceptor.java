/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.interceptor;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;

/**
 * A Spring HandlerInterceptor that adds named objects to the model that need to be globally available for all pages,
 * 	either because they're used in the page header / footer, or because they are utility information (like session beans)
 * 	that we want to be always accessible to JSTL.
 */
@Component
public class CommonModelAttributesHandlerInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	@CommonStaticUrl
	private URL commonStaticUrl;
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	
	/** {@inheritDoc} */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
		addMissingRequestAttributes(request, sessionBean, commonStaticUrl, modelAndView);
		
		super.postHandle(request, response, handler, modelAndView);
	}

	public static void addMissingRequestAttributes(HttpServletRequest request, SuppliermgmtSessionBean sessionBean, URL staticUrl, ModelAndView modelAndView) {
		//We don't need to add all of these things to the request if there isn't a ModelAndView, since the only responses that are
		// going to render to JSPs are ones with ModelAndView
		if(modelAndView != null)
		{
			addAttributeIfAbsent(request, modelAndView, "commonStaticUrl", staticUrl.toString());
			
			//User object, to print user info on pages
			addAttributeIfAbsent(request, modelAndView, "currentUser", sessionBean.getUserContext());
			
			//baseUrl contains the URL segment that should be prepended to static resources (ex: JavaScript, CSS, images) in JSP pages
			addAttributeIfAbsent(request, modelAndView, "baseUrl", sessionBean.getBaseUrl());
			//baseAppUrl contains the URL segment that should be prepended to AJAX calls and other requests that reference Spring-mapped URLs.
			addAttributeIfAbsent(request, modelAndView, "baseAppUrl", sessionBean.getBaseUrl() + "/app");
			
			addAttributeIfAbsent(request, modelAndView, "baseAdminConsoleUrl", sessionBean.getBaseUrl() + "/app/admin-console");
			addAttributeIfAbsent(request, modelAndView, "baseBuildMatrixUrl", sessionBean.getBaseUrl() + "/app/admin-console/oem-build-matrix");

			addAttributeIfAbsent(request, modelAndView, "formattedUserLoginDate", sessionBean.getFormattedUserLoginDate());
			addAttributeIfAbsent(request, modelAndView, "hasBuddies", sessionBean.isBuddyListApplied());
			addAttributeIfAbsent(request, modelAndView, "hasVendors", sessionBean.isVendorFilterApplied());
			if(sessionBean.isVendorFilterApplied())
				addAttributeIfAbsent(request, modelAndView, "hasVendorFilterActivated", sessionBean.isVendorFilterActive());
			
			addAttributeIfAbsent(request, modelAndView, "supportNum", ApplicationConstants.SUPPORT_NUMBER);
		}
	}
	
	private static void addAttributeIfAbsent(HttpServletRequest request, ModelAndView mav, String name, Object objectToAdd)
	{
		if(request == null || name == null || objectToAdd == null)
			return;
		
		//If they have provided a ModelAndView that uses the given attribute, then use that one,
		// since ModelAndView objects should override the default values set by this HandlerInterceptor
		if(mav != null)
		{
			if(mav.getModel().get(name) != null)
				return;
		}
		
		if(request.getAttribute(name) == null)
			request.setAttribute(name, objectToAdd);
	}
}
