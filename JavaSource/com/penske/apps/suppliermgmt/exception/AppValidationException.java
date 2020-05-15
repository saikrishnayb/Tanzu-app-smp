/**
] * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.exception;

import com.penske.apps.smccore.base.exception.HumanReadableException;

/**
 * Exception class that the application can throw to indicate server-side validation did not pass for a given value.
 * This counts as a non-critical HumanReadableException, so getting one of these does not indicate there is a programming bug, but rather that the user provided incorrect data.
 * 
 * Generally, this should be thrown (if at all) by the controller or service layer if we need to manually validate the user's values before handing it to the domain layer.
 *  In general, this exception should be used as a last resort for validation - if the JSP can not be structured so submitting bad values is impossible,
 *  and if JavaScript validation is too complicated, and if Bean Validation is not practical, then the Controller or Service method can check manually and throw one of these if the validation fails.
 *  
 * 
 * The difference between this and the {@link BusinessRuleException} is that this one does not indicate a programming bug, but the other kind does.
 */
public class AppValidationException extends HumanReadableException
{
	private static final long serialVersionUID = -624386176428608227L;

	/**
	 * Creates a new exception to indicate an expected server-side validation failed.
	 * @param message A message suitible for display to the user.
	 */
	public AppValidationException(String message)
	{
		super(message, false);
	}
}
