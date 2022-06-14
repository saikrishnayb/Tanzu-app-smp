/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.aspect;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.exception.AppValidationException;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.smccore.base.util.DateUtil;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.DefaultController;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.interceptor.CommonModelAttributesHandlerInterceptor;
import com.penske.apps.suppliermgmt.model.ValidationError;

/**
 * Controller Advice class that only targets the non-ajax controllers that use the v2 page template.
 * Should return a ModelAndView
 */
@ControllerAdvice(annotations = DefaultController.class)
public class DefaultControllerAdvisor
{
	@Autowired(required=false)
	private ServletContext servletContext;
	@Autowired
	private SuppliermgmtSessionBean sessionBean;
	@Autowired
	@CommonStaticUrl
	private URL commonStaticUrl;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AppValidationException.class)
	public ModelAndView handleValidationException(AppValidationException ex, HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView("error/v2/error");
		
		List<ValidationError> validationErrors = new ArrayList<>();
		if(StringUtils.isNotBlank(ex.getHumanReadableMessage()))
			validationErrors.add(new ValidationError(ex.getHumanReadableMessage()));
		
		mav.addObject("errorMessage", "The following validation error(s) occurred:");
		mav.addObject("validationErrors", validationErrors);
		
		this.addStandardErrorModelAttributes(request, mav);
		CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);
		
		return mav;
	}
	
	@ExceptionHandler(HumanReadableException.class)
	public ModelAndView handleHumanReadableException(HumanReadableException exception, HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView("error/v2/error");

		String humanReadableMessage = exception.getHumanReadableMessage();
		String causeString = ExceptionUtils.getRootCauseMessage(exception);
		String stackTraceString = StringUtils.join(exception.getStackTrace(), "<br>");

		mav.addObject("causeString", causeString);
		mav.addObject("stackTraceString", stackTraceString);
		mav.addObject("errorMessage", humanReadableMessage);

		this.addStandardErrorModelAttributes(request, mav);
		CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);

		return mav;
	}

	@ExceptionHandler(UnauthorizedSecurityFunctionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView handleUnauthorizedSecurityFunctionException(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("error/v2/error");
		mav.addObject("errorMessage", "You do not have authorization for the requested resource.");

		this.addStandardErrorModelAttributes(request, mav);
		CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);

		return mav;
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception exception, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("error/v2/error");

		String causeString = ExceptionUtils.getRootCauseMessage(exception);
		String stackTraceString = StringUtils.join(exception.getStackTrace(), "<br>");
		
		mav.addObject("causeString", causeString);
		mav.addObject("stackTraceString", stackTraceString);

		this.addStandardErrorModelAttributes(request, mav);
		CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);

		return mav;
	}

	private void addStandardErrorModelAttributes(HttpServletRequest request, ModelAndView mav)
	{
        String pathInfo = request.getServletPath();
        String leftNavDirectory = StringUtils.substringAfter(StringUtils.substringBeforeLast(pathInfo,  "/"), "/app");
        boolean sidebarExists = false;
        if(servletContext != null)
        {
        	String realPath = servletContext.getRealPath("WEB-INF/jsp/global/navigation" + leftNavDirectory + "/left-nav.jsp");
            File leftNavFile = new File(realPath);
            sidebarExists = leftNavFile.isFile();
        }
        
        mav.addObject("currentTimeFormatted", DateUtil.formatDateTimeUS(LocalDateTime.now()));
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExists);
	}
}
