package com.penske.apps.suppliermgmt.aspect;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.Version1Controller;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.interceptor.CommonModelAttributesHandlerInterceptor;
import com.penske.apps.suppliermgmt.model.AjaxError;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;

/**
 * Contains methods that augment the controllers using the v1 page template. New controllers should use either {@link @RestController} or {@link @DefaultController} and use the v2 page template.
 */
@ControllerAdvice
@Version1Controller
public class Version1ControllerAdvisor {

    @Autowired(required=false)
    private ServletContext servletContext;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    @Autowired
    @CommonStaticUrl
    private URL commonStaticUrl;

    @ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUnauthorizedSecurityFunctionException(HttpServletRequest request) {

        String pathInfo = request.getPathInfo();
        String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");

        boolean sidebarExists = false;
        if(servletContext != null)
        {
        	String realPath = servletContext.getRealPath("WEB-INF/jsp/global/navigation" + leftNavDirectory + "/left-nav.jsp");
            File leftNavFile = new File(realPath);
            sidebarExists = leftNavFile.isFile();
        }
        
        ModelAndView mav = new ModelAndView("error/v1/error");
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExists);
        mav.addObject("errorMessage", "You do not have authorization for the requested resource.");
        CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);
        
        return mav;
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TemplateNameAlreadyExistsException.class)
    @ResponseBody
    public AjaxError handleTemplateNameAlreadyExists(TemplateNameAlreadyExistsException ex) {

        AjaxError error = new AjaxError(null, ex.getErrorMessage(), ApplicationConstants.SUPPORT_NUMBER);

        return error;
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView globalExceptionCatcher(Exception e, HttpServletRequest request){

        //Check if this exception has a human-readable exception somewhere in its stack trace.
        int humanReadableExceptionIndex = ExceptionUtils.indexOfType(e, HumanReadableException.class);
        if(humanReadableExceptionIndex != -1)
        {
            Throwable[] chain = ExceptionUtils.getThrowables(e);
            if(chain != null && chain.length > humanReadableExceptionIndex)
            {
                Throwable th = chain[humanReadableExceptionIndex];
                if(HumanReadableException.class.isAssignableFrom(th.getClass()))
                    return handleHumanReadableException((HumanReadableException) th, request);
            }
        }

        String pathInfo = request.getPathInfo();
        String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");

        boolean sidebarExists = false;
        if(servletContext != null)
        {
        	String realPath = servletContext.getRealPath("WEB-INF/jsp/global/navigation" + leftNavDirectory + "/left-nav.jsp");
            File leftNavFile = new File(realPath);
            sidebarExists = leftNavFile.isFile();
        }
        
        ModelAndView mav = new ModelAndView("error/v1/error");
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExists);
        CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);
        return mav;
    }

    private ModelAndView handleHumanReadableException(HumanReadableException ex, HttpServletRequest request)
    {
        String pathInfo = request.getPathInfo();
        String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");
        
        boolean sidebarExists = false;
        if(servletContext != null)
        {
        	String realPath = servletContext.getRealPath("WEB-INF/jsp/global/navigation" + leftNavDirectory + "/left-nav.jsp");
            File leftNavFile = new File(realPath);
            sidebarExists = leftNavFile.isFile();
        }
        
        ModelAndView mav = new ModelAndView("error/v1/error");
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExists);
        mav.addObject("errorMessage", ex.getHumanReadableMessage());
        CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);
        
        return mav;
    }
}
