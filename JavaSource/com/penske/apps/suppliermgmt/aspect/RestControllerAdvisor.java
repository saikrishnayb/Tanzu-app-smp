package com.penske.apps.suppliermgmt.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.suppliermgmt.exception.AppValidationException;
import com.penske.apps.suppliermgmt.model.AjaxError;
import com.penske.apps.suppliermgmt.model.ValidationError;
import com.penske.apps.suppliermgmt.util.ApplicationConstants;

/**
 * Controller Advice class that only targets the ajax controllers (annotated with @RestController). Should
 * return a JSON object to the front end for the javascript to pick up
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestControllerAdvisor
{
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AppValidationException.class)
	public AjaxError handleValidationException(AppValidationException ex)
	{
		List<ValidationError> validationErrors = new ArrayList<>();
		if(StringUtils.isNotBlank(ex.getHumanReadableMessage()))
			validationErrors.add(new ValidationError(ex.getHumanReadableMessage()));
		
		AjaxError result = new AjaxError(validationErrors, ApplicationConstants.SUPPORT_NUMBER, ex);
		return result;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HumanReadableException.class)
    public AjaxError handleHumanReadableException(HumanReadableException exception) {
        String humanReadableMessage = exception.getHumanReadableMessage();
        AjaxError ajaxError = new AjaxError(null, humanReadableMessage, ApplicationConstants.SUPPORT_NUMBER, exception);
        return ajaxError;
    }
	
	@ExceptionHandler(UnauthorizedSecurityFunctionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxError handleUnauthorizedSecurityFunctionException() {
		
        String humanReadableMessage = "You do not have authorization for the requested resource.";
        AjaxError ajaxError = new AjaxError("Unauthorized Access", humanReadableMessage, ApplicationConstants.SUPPORT_NUMBER);

        return ajaxError;
    }

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public AjaxError handleException(Exception exception) {

        AjaxError ajaxError = new AjaxError(null, null, ApplicationConstants.SUPPORT_NUMBER, exception);

        return ajaxError;
    }
}