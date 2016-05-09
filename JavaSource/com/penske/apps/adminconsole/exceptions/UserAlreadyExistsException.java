package com.penske.apps.adminconsole.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 644807407628160617L;
	private String errorMessage;
	
	public UserAlreadyExistsException(String errorMessage){
		super();
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage(){
		
		return errorMessage;
	}
}
