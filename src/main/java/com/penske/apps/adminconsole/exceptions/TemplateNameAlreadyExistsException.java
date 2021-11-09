package com.penske.apps.adminconsole.exceptions;

public class TemplateNameAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 644807407628160617L;
	private String errorMessage;
	
	public TemplateNameAlreadyExistsException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public void setErrorMessage(String newErrorMessage) {
		this.errorMessage = newErrorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
