package com.penske.apps.suppliermgmt.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.smccore.base.util.DateUtil;

/**
 * This is a general AjaxError model object which will be used
 * to store error information that will be output to the user.
 * @author 600132441 M.Leis
 *
 */
public class AjaxError {

	private final String modalTitle;
	private final String modalErrorMessage;
	private final boolean critical;
	
	private final String currentTimeFormatted = DateUtil.formatDateTimeUS(LocalDateTime.now());
	
	private final String exceptionMessage;
	private final String exceptionStackTrace;
	
	private final List<ValidationError> validationErrors = new ArrayList<>();
	
	public AjaxError(String modalTitle, String modalErrorMessage, String supportPhoneNum)
	{
		this(modalTitle, modalErrorMessage, supportPhoneNum, null);
	}
	
	public AjaxError(String modalTitle, String modalErrorMessage, String supportPhoneNum, Exception exception)
	{
		if(StringUtils.isNotBlank(modalTitle))
			this.modalTitle = modalTitle;
		else
			this.modalTitle = "Error";

		if(StringUtils.isNotBlank(modalErrorMessage))
			this.modalErrorMessage = modalErrorMessage;
		else
			this.modalErrorMessage = "Something went wrong with the request. See if you can try again. If the problem persists, contact the IT Service Desk at " + supportPhoneNum + ".";

		if(exception != null) {
			this.exceptionMessage = ExceptionUtils.getRootCauseMessage(exception);
			this.exceptionStackTrace = StringUtils.join(ExceptionUtils.getRootCauseStackTrace(exception), "<br>");
			if(exception instanceof HumanReadableException)
				critical = ((HumanReadableException) exception).isCritical();
			else
				critical = true;
		}
		else
		{
			this.exceptionMessage = null;
			this.exceptionStackTrace = null;
			this.critical = true;
		}
	}
	
	public AjaxError(List<ValidationError> validationErrors, String supportPhoneNum, Exception exception)
	{
		this(null, null, supportPhoneNum, exception);
		if(validationErrors != null)
			this.validationErrors.addAll(validationErrors);
	}

	//***** MODIFIED ACCESSORS *****//
	public List<ValidationError> getValidationErrors()
	{
		return Collections.unmodifiableList(validationErrors);
	}

	//***** DEFAULT ACCESSORS *****//
	public String getModalTitle()
	{
		return modalTitle;
	}

	public String getModalErrorMessage()
	{
		return modalErrorMessage;
	}

	public boolean isCritical()
	{
		return critical;
	}

	public String getCurrentTimeFormatted()
	{
		return currentTimeFormatted;
	}

	public String getExceptionMessage()
	{
		return exceptionMessage;
	}

	public String getExceptionStackTrace()
	{
		return exceptionStackTrace;
	}	
}
