package com.penske.apps.adminconsole.model;

public class SignatureInitial {
	private int userId;
	private String signString;
	private String initString;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	public String getInitString() {
		return initString;
	}
	public void setInitString(String initString) {
		this.initString = initString;
	}
}
