/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.model;

import org.springframework.validation.ObjectError;

/**
 * Class to encapsulate an error that ocurred as a result of validation on an AJAX request
 */
public class ValidationError
{
	/** The human-readable content of this error message */
	private final String message;
	
	public ValidationError(ObjectError objectError)
	{
		this.message = objectError.getDefaultMessage();
	}
	
	public ValidationError(String message)
	{
		this.message = message;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return "{ValidationError: " + message + "}";
	}

	public String getMessage()
	{
		return message;
	}
}
