package com.penske.apps.adminconsole.model;

/**
 * Java object to hold information about notificatin parties.
 * 
 * @author 600144069
 * 
 */
public class NotificationParty {
	private String escalationGroup;
	private int escalationLevel;
	private String contact;
	private int isEmail;

	// Getters
	public String getEscalationGroup() {
		return escalationGroup;
	}

	public int getEscalationLevel() {
		return escalationLevel;
	}

	public String getContact() {
		return contact;
	}

	public int getIsEmail() {
		return isEmail;
	}

	// Setters
	public void setEscalationGroup(String escalationGroup) {
		this.escalationGroup = escalationGroup;
	}

	public void setEscalationLevel(int escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}

	@Override
	public String toString() {
		return "[" + contact + "]";
	}
}
