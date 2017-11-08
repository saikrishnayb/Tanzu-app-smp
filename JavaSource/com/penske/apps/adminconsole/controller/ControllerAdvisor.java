package com.penske.apps.adminconsole.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;
import com.penske.apps.suppliermgmt.common.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.UserContext;

@ControllerAdvice
@EnableWebMvc
public class ControllerAdvisor {

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    private static Logger logger = Logger.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUnauthorizedSecurityFunctionException(UnauthorizedSecurityFunctionException exception) {

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

        String pathInfo = request.getServletPath();
        String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");

        String realPath = servletContext.getRealPath("WEB-INF/jsp/jsp-fragment" + leftNavDirectory + "/left-nav.jsp");

        boolean sidebarExist = true;
        InputStream is = null;
        try{
            is = new FileInputStream(realPath);
        } catch(FileNotFoundException e1){
            logger.debug(e1);
            sidebarExist = false;
        } finally{
            try {
                is.close();
            } catch (IOException e1) {
                logger.error(e1.getMessage());
            }
        }
        logger.error("exception in application>>>",e);
        ModelAndView mav = new ModelAndView("/error/error");
        mav.addObject("leftNavDirectory", leftNavDirectory);
        mav.addObject("sidebarExists", sidebarExist);
        return mav;
    }
}
