package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.NotificationForm;

/**
 * Interface for the Notification Service layer. It is implemented in DefaultNotificationService.java.
 * 
 * @author 600144069
 *
 */
public interface NotificationService {
	public Notification getNotification(int notificationId);
	
	public List<Notification> getNotifications();
	
	public void getSortedNotificationParties(Notification notification);
	
	public List<Notification> getEscalationContacts(List<Notification> notifications);
	
	public void updateNotification(NotificationForm notificationForm);
	
	public void updateNotificationParty(NotificationForm notificationForm);
	
	public List<String> getUserEmails();
}
