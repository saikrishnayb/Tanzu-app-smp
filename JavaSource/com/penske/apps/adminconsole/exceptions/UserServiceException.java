package com.penske.apps.adminconsole.exceptions;

public class UserServiceException extends Exception {
	private int errorCode;
	private String message;
	
	/**
	 * Constructs a new exception with null as its detail message
	 */
	public UserServiceException() {
		super();
	}

	/**
	 * Constructs a new exception given the errorcode and sets the appropriate message
	 * 
	 * @param errorCode the error code that is required to identify the type of exception occurred. 
	 * The errorMessage is set to a value depending upon this errorcode and can be retrieved using the 
	 * UserServiceException.getMessage() method.
	 * 
	 */	
	public UserServiceException(int errorCode) {	
		this.errorCode=errorCode;
	}
	

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            Throwable.getMessage() method).
	 */

	public UserServiceException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the cause.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */

	public UserServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param errorMsg
	 *            the detail message (which is saved for later retrieval by the
	 *            Throwable.getMessage() method).
	 * @param throwable
	 *            the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public UserServiceException(String errorMsg, Throwable throwable) {

		super(errorMsg, throwable);
	}
	
	/**
	 * Returns the errorMessage
	 * 
	 * @return returns the error message	 
	 */
	
	public String getMessage() {
		return message;	
	}

	/**
	 * Returns the errorCode
	 * 
	 * @return Returns the errorCode.
	 */
	public int getErrorCode() {
		return errorCode;
	}
}
