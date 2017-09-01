package com.penske.apps.adminconsole.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.apps.suppliermgmt.model.UserContext;

@ControllerAdvice
@EnableWebMvc
public class ControllerAdvisor {


    @Autowired
    private ServletContext servletContext;
    @Autowired
    private HttpSession httpSession;

    private static Logger logger = Logger.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUnauthorizedSecurityFunctionException(UnauthorizedSecurityFunctionException exception) {

        ModelAndView modelAndView = new ModelAndView("error/GlobalErrorPage");

        ErrorModel model = new ErrorModel();
        UserContext userContext = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);

        HandlerMethod handlerMethod = exception.getHandlerMethod();

        Method method = handlerMethod.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();

        String requestURI = exception.getRequestURI();

        if (requestURI != null) {
            logger.error("UnauthorizedSecurityFunctionException. Vendor " + userContext.getUserSSO()
                    + " tried accesing the following request mapping: " + requestURI + " located in  " + declaringClass.getName() + "::"
                    + method.getName(), exception);
        } else {

            String userType = userContext.isVisibleToPenske() ? "Penske" : "Vendor";

            StringBuilder errorStringBuilder = new StringBuilder();

            errorStringBuilder.append("UnauthorizedSecurityFunctionException. " + userType + " user " + userContext.getUserSSO()
                    + " does not have access to the following security functions: ");

            SecurityFunction[] securityFunctions = exception.getSecurityFunctions();

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



    /**
     * Messy exception handler logic below
     ********************************************************************************************************/

    @ExceptionHandler(Exception.class)
    public ModelAndView globalExceptionCatcher(Exception e, HttpServletRequest request){

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
