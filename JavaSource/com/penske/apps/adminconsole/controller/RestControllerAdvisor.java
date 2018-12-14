package com.penske.apps.adminconsole.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.penske.apps.adminconsole.exceptions.TemplateNameAlreadyExistsException;
import com.penske.apps.adminconsole.model.AjaxError;

@ControllerAdvice
public class RestControllerAdvisor {

    private static Logger logger = Logger.getLogger(RestControllerAdvisor.class);

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

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handleIOException(Exception ex) {
        logger.error(ex);
    }
}
