package com.penske.apps.suppliermgmt.exception;

import com.penske.apps.suppliermgmt.model.ErrorModel;

/**
 *****************************************************************************
 * File Name     : SMCException
 * Description   : Exception class to handle application exception
 * Project       : SMC
 * Package       : com.penske.apps.smc.common.exception
 * Author        : 502299699
 * Date			 : Apr 09, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************/
public class SMCException extends Exception{
	
	protected ErrorModel errorDetails = null;
	private static final long serialVersionUID = 12424L;

	/**
	 * Default constructor
	 */
	public SMCException() {
		super();
	}

	/**
	 * @param errorDetails
	 */
	public SMCException(ErrorModel errorDetails) {
		super(errorDetails.getMessage(), errorDetails.getExceptionDetails());
		this.errorDetails = errorDetails;
	}

	/**
	 * @param id
	 * @param errorCode
	 * @param errorMessage
	 * @param exception
	 */
	public SMCException(int errorCode, final String errorMessage, final Exception exception) {
		super(errorMessage, exception);
		errorDetails = new ErrorModel(errorCode, errorMessage, exception);
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public SMCException(int errorCode, final String errorMessage) {
		this(errorCode, errorMessage, null);
	}

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public SMCException(final String errorMessage,final Exception exception) {
		this(0,errorMessage, exception);
	}

	/**
	 * @return the errorDetails
	 */
	public ErrorModel getErrorDetails() {
		return errorDetails;
	}

	/**
	 * @param errorDetails the errorDetails to set
	 */
	public void setErrorDetails(ErrorModel errorDetails) {
		this.errorDetails = errorDetails;
	}
}
