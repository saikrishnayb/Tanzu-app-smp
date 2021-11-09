package com.penske.apps.suppliermgmt.model;




/*******************************************************************************
 *
 * @Author 		: 502299699
 * @Version 	: 1.0
 * @Date Created: May 15, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : CommonModel object to hold error information for critical application errors.
 * @History		:
 *
 ******************************************************************************/
public class ErrorModel {

	private int errorCode;
	private String message;
	private Exception exceptionDetails;

	/**
	 * 
	 */
	public ErrorModel() {
		super();
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param exceptionDetails
	 */
	public ErrorModel(int errorCode, String message, Exception exceptionDetails) {
		this.errorCode = errorCode;
		this.message = message;
		this.exceptionDetails = exceptionDetails;
	}

	/**
	 * @param errorCode
	 * @param message
	 */
	public ErrorModel(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public ErrorModel(String message)
	{
		this.message = message;
	}
	
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the exceptionDetails
	 */
	public Exception getExceptionDetails() {
		return exceptionDetails;
	}

	/**
	 * @param exceptionDetails the exceptionDetails to set
	 */
	public void setExceptionDetails(Exception exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}
}
