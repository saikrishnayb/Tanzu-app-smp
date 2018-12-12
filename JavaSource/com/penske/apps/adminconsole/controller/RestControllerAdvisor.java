package com.penske.apps.adminconsole.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.exceptions.UserAlreadyExistsException;
import com.penske.apps.adminconsole.model.AjaxError;

@ControllerAdvice
public class RestControllerAdvisor {

    private static Logger logger = Logger.getLogger(RestControllerAdvisor.class);

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public AjaxError handleUserAlreadyExists(UserAlreadyExistsException ex){

        logger.error(ex);

        AjaxError error = new AjaxError();

        String errorMessage = ex.getErrorMessage();
        error.setErrorDescription( errorMessage );

        return error;
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TemplateNameAlreadyExistsException.class)
    @ResponseBody
    public AjaxError handleTemplateNameAlreadyExists(TemplateNameAlreadyExistsException ex) {

        logger.error(ex);

        AjaxError error = new AjaxError();

        String errorMessage = ex.getErrorMessage();
        error.setErrorDescription(errorMessage);

        return error;
    }

    /**
    @ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxError handleUnauthorizedSecurityFunctionException(UnauthorizedSecurityFunctionException exception) {

        AjaxError error = new AjaxError();

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

            logger.error(errorStringBuilder.toString(), exception);
        }

        error.setErrorDescription("You do not have authorization for the requested resource.");

        return error;
    }
     **/

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handleIOException(Exception ex) {
        logger.error(ex);
    }
}
