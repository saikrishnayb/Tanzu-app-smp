package com.penske.apps.adminconsole.model;

/**
 * This is a general AjaxError model object which will be used
 * to store error information that will be output to the user.
 * @author 600132441 M.Leis
 *
 */
public class AjaxError {

	private String errorDescription; // description of the error
	private String errorReason; // the reason the error happened
	
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String toString() {
		return "AjaxError [errorDescription=" + errorDescription
				+ ", errorReason=" + errorReason + "]";
	}
}
