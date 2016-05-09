package com.penske.apps.adminconsole.exceptions;

public class DelayReasonAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 644807407628160616L;
	private String errorMessage;
	
	public DelayReasonAlreadyExistsException(String errorMessage){
		
		super();
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage(){
		
		return errorMessage;
	}
}
