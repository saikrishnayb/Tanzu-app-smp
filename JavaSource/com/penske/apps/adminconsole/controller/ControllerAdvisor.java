package com.penske.apps.adminconsole.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.annotation.CommonStaticUrl;
import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.interceptor.CommonModelAttributesHandlerInterceptor;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.UserContext;

@ControllerAdvice
public class ControllerAdvisor {

    @Autowired(required=false)
    private ServletContext servletContext;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;
    @Autowired
    @CommonStaticUrl
    private URL commonStaticUrl;

    private static Logger logger = Logger.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUnauthorizedSecurityFunctionException(HttpServletRequest request, UnauthorizedSecurityFunctionException exception) {

        ModelAndView modelAndView = new ModelAndView("error/global-error-page");

        ErrorModel model = new ErrorModel();
        UserContext userContext = sessionBean.getUserContext();
        String userSSO = userContext == null ? "" : userContext.getUserSSO();
        String userType = userContext == null ? "Unknown" : (userContext.isVisibleToPenske() ? "Penske" : "Vendor");

        HandlerMethod handlerMethod = exception.getHandlerMethod();

        Method method = handlerMethod.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();

        SecurityFunction[] securityFunctions = exception.getSecurityFunctions();
        String requestURI = exception.getRequestURI();

        if (securityFunctions == null) {
            logger.error("UnauthorizedSecurityFunctionException. " + userType + " user " + userSSO
                    + " tried accesing the following request mapping: " + requestURI + " located in  " + declaringClass.getName() + "::"
                    + method.getName(), exception);
        } else {

            StringBuilder errorStringBuilder = new StringBuilder();

            errorStringBuilder.append("UnauthorizedSecurityFunctionException. ")
            .append(userType)
            .append(" user ")
            .append(userSSO)
            .append(" does not have access to the following security functions: ");

            for (int i = 0; i < securityFunctions.length; i++) {
                errorStringBuilder.append(securityFunctions[i]);
                if (securityFunctions.length > 1 && i != securityFunctions.length - 1) errorStringBuilder.append(", ");
            }

            errorStringBuilder.append(" located in  ");
            errorStringBuilder.append(declaringClass.getName());
            errorStringBuilder.append("::");
            errorStringBuilder.append(method.getName());

            logger.error(errorStringBuilder.toString(), exception);
        }

        model.setMessage("You do not have authorization for the requested resource.");

        modelAndView.addObject(model);
        CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, modelAndView);
        return modelAndView;
    }

    public ModelAndView handleHumanReadableException(HumanReadableException ex)
    {
        ModelAndView mv = new ModelAndView("error/GlobalErrorPage");
        ErrorModel model = new ErrorModel();
        try {
            UserContext userContext = sessionBean.getUserContext();
            String userSSO = userContext == null ? "" : userContext.getUserSSO();
            String randomNumber = UUID.randomUUID().toString();
            logger.error("Caught Unhandled Exception.  Reference Number is:" + randomNumber + " And Logged in User is:" + userSSO
                    + " and Exception is::" + ex.toString(), ex);
            model.setMessage(ex.getHumanReadableMessage() + " Reference number is " + randomNumber);
            mv.addObject("supportNum", "1-866-926-7240");
        } catch (Exception e) {
            logger.error("Exception occured in handleException method of BaseController" + e.toString(), e);
            mv.addObject("supportNum", "1-866-926-7240");
            return mv;
        }
        mv.addObject(model);
        return mv;
    }



    /**
     * Messy exception handler logic below
     ********************************************************************************************************/

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
                    return handleHumanReadableException((HumanReadableException) th);
            }
        }

        String pathInfo = request.getPathInfo();
        String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");

        boolean sidebarExists = false;
        if(servletContext != null)
        {
        	String realPath = servletContext.getRealPath("WEB-INF/jsp/jsp-fragment" + leftNavDirectory + "/left-nav.jsp");
            File leftNavFile = new File(realPath);
            sidebarExists = leftNavFile.isFile();
        }
        
        logger.error("exception in application>>>",e);
        ModelAndView mav = new ModelAndView("/error/error");
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExists);
        CommonModelAttributesHandlerInterceptor.addMissingRequestAttributes(request, sessionBean, commonStaticUrl, mav);
        return mav;
    }
}
