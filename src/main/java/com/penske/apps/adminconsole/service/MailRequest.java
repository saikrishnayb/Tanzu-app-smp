package com.penske.apps.adminconsole.service;

import java.util.List;

public class MailRequest implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String fromAddress;
	private String subject;
	private List<String> toRecipientsList;
	private String messageContent;
	private String userId;
	private String toList="";
	private int emailAuditId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getToRecipientsList() {
		return toRecipientsList;
	}
	public void setToRecipientsList(List<String> toRecipientsList) {
		this.toRecipientsList = toRecipientsList;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public int getEmailAuditId() {
		return emailAuditId;
	}
	public void setEmailAuditId(int emailAuditId) {
		this.emailAuditId = emailAuditId;
	}
}
