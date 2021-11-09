package com.penske.apps.adminconsole.exceptions;

public class DynamicRulePriorityException extends RuntimeException {

/**
	 * 
	 */
private static final long serialVersionUID = 1L;
private String errorMessage;
	
	public DynamicRulePriorityException(String errorMessage){
		
		super();
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage(){
		
		return errorMessage;
	}
}
