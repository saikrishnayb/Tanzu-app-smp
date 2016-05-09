package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.NotificationDao;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.NotificationForm;
import com.penske.apps.adminconsole.model.NotificationParty;

/**
 * Implementation of NotificationService.java. Used for accessing notification information.
 * @author 600144069
 *
 */

@Service
public class DefaultNotificationService implements NotificationService {
	
	//Member variable declarations
	@Autowired
	private NotificationDao notificationDao;
	
	
	private static Logger logger = Logger.getLogger(DefaultNotificationService.class);
	

	//Returns a list of all notifications
	@Override
	public List<Notification> getNotifications() {
		return notificationDao.selectAllNotifications();
	}

	//Returns lists of notification parties by escalation group for each notification
	@Override
	public List<Notification> getEscalationContacts(List<Notification> notifications) {
		
		for (int i = 0; i < notifications.size(); i++) {
			Notification notification = notifications.get(i);
			List<NotificationParty> notificationPartyOne = notificationDao.selectEscalationContacts(1, notification.getNotificationId());
			notification.setEscalationOneContacts(notificationPartyOne);
			
			List<NotificationParty> notificationPartyTwo = notificationDao.selectEscalationContacts(2, notification.getNotificationId());
			notification.setEscalationTwoContacts(notificationPartyTwo);
			
			List<NotificationParty> notificationPartyThree = notificationDao.selectEscalationContacts(3, notification.getNotificationId());
			notification.setEscalationThreeContacts(notificationPartyThree);
		}
		
		
		return notifications;
	}

	//Returns a single notification based on notification ID, used for populating the edit modal
	@Override
	public Notification getNotification(int notificationId) {
		if (notificationId <= 0) {
			logger.error("Notification ID was zero when attempting to open the edit notification modal.");
			
			return null;
		}
		
		Notification notification = notificationDao.selectNotification(notificationId);
		
		return notification;
	}

	//Updates the compliance type, compliance count, and escalation days for the notification
	@Override
	public void updateNotification(NotificationForm notificationForm) {
		if (notificationForm == null) {
			logger.error("Notification Form was null when attempting to save changes to the specified notification.");
			
			return;
		}
		
		if (notificationForm.getNotificationId() <= 0) {
			logger.error("Notification ID on the notification form was less than or equal to 0 when attempting to update the selected notification.");
			
			return;
		}
		
		notificationDao.updateNotification(notificationForm);
	}

	//Updates notification parties for the notification. For aliases, will check if it exists before inserting or deleting.
	//For emails, it removes all emails and then re-adds the new list of emails.
	//Runs updates based on visibility
	@Override
	public void updateNotificationParty(NotificationForm form) {
		String escalationGroup = "";
		String party = "";
		
		if (form == null) {
			logger.error("Notification Form was null when attempting to update selected notification's notification parties.");
			
			return;
		}
		
		if (form.getNotificationId() <= 0) {
			logger.error("Notification ID on the notification form was less than or equal to 0 when attempting to update the selected notification's notification parties.");
			
			return;
		}
		
		if (form.getVisibilityVendor() == 1) {
			escalationGroup = "VENDOR";
			party = "VENDOR PRIMARY";
			
			// Update vendors for escalations 1, 2, and 3.
			updateNotificationEscalation(form.getVendorPrimaryEsc1(), form.getNotificationId(), escalationGroup, 1, party);
			updateNotificationEscalation(form.getVendorPrimaryEsc2(), form.getNotificationId(), escalationGroup, 2, party);
			updateNotificationEscalation(form.getVendorPrimaryEsc3(), form.getNotificationId(), escalationGroup, 3, party);
			
			party = "VENDOR SECONDARY";
			updateNotificationEscalation(form.getVendorSecondaryEsc1(), form.getNotificationId(), escalationGroup, 1, party);
			updateNotificationEscalation(form.getVendorSecondaryEsc2(), form.getNotificationId(), escalationGroup, 2, party);
			updateNotificationEscalation(form.getVendorSecondaryEsc3(), form.getNotificationId(), escalationGroup, 3, party);
			
			// Updates additional users for escalations 1, 2, and 3.
			updateEscalationAddlUsers(form.getVendorEsc1Additional(), form.getVendorEsc1AdditionalChecked(), form.getNotificationId(), escalationGroup, 1);
			updateEscalationAddlUsers(form.getVendorEsc2Additional(), form.getVendorEsc2AdditionalChecked(), form.getNotificationId(), escalationGroup, 2);
			updateEscalationAddlUsers(form.getVendorEsc3Additional(), form.getVendorEsc3AdditionalChecked(), form.getNotificationId(), escalationGroup, 3);
		}
		
		
		if (form.getVisibilityPurchasing() == 1) {
			escalationGroup = "PURCHASING";
			party = "PENSKE VSS";
			
			// Update Penske VSS for escalations 1, 2, and 3.
			updateNotificationEscalation(form.getPenskeVssEsc1(), form.getNotificationId(), escalationGroup, 1, party);
			updateNotificationEscalation(form.getPenskeVssEsc2(), form.getNotificationId(), escalationGroup, 2, party);
			updateNotificationEscalation(form.getPenskeVssEsc3(), form.getNotificationId(), escalationGroup, 3, party);
			
			// Updates additional users for escalations 1, 2, and 3.
			updateEscalationAddlUsers(form.getPurchasingEsc1Additional(), form.getPurchasingEsc1AdditionalChecked(), form.getNotificationId(), escalationGroup, 1);
			updateEscalationAddlUsers(form.getPurchasingEsc2Additional(), form.getPurchasingEsc2AdditionalChecked(), form.getNotificationId(), escalationGroup, 2);
			updateEscalationAddlUsers(form.getPurchasingEsc3Additional(), form.getPurchasingEsc3AdditionalChecked(), form.getNotificationId(), escalationGroup, 3);
		}
		
		if (form.getVisibilityPlanning() == 1) {
			escalationGroup = "PLANNING";
			party = "PENSKE ANALYST";
			
			// Update Penske Analyst for escalations 1, 2, and 3.
			updateNotificationEscalation(form.getPenskeAnalystEsc1(), form.getNotificationId(), escalationGroup, 1, party);
			updateNotificationEscalation(form.getPenskeAnalystEsc2(), form.getNotificationId(), escalationGroup, 2, party);
			updateNotificationEscalation(form.getPenskeAnalystEsc3(), form.getNotificationId(), escalationGroup, 3, party);

			// Updates additional users for escalations 1, 2, and 3.
			updateEscalationAddlUsers(form.getPlanningEsc1Additional(), form.getPlanningEsc1AdditionalChecked(), form.getNotificationId(), escalationGroup, 1);
			updateEscalationAddlUsers(form.getPlanningEsc2Additional(), form.getPlanningEsc2AdditionalChecked(), form.getNotificationId(), escalationGroup, 2);
			updateEscalationAddlUsers(form.getPlanningEsc3Additional(), form.getPlanningEsc3AdditionalChecked(), form.getNotificationId(), escalationGroup, 3);
		}
	}
	
	// Insert/Delete the notification for a specified escalation.
	public void updateNotificationEscalation(boolean escalation, int notificationId, String escalationGroup, int level, String party) {
		boolean partyExists = notificationDao.doesNotificationPartyExist(notificationId, escalationGroup, level, party, 0) >= 1;
		
		if (escalation && !partyExists) {
				notificationDao.insertNotificationParty(notificationId, escalationGroup, level, party, 0);
		}
		else if (!escalation && partyExists) {
				notificationDao.deleteNotificationParty(notificationId, escalationGroup, level, party, 0);
		}
	}
	
	// Update the escalation with any additional users specified.
	public void updateEscalationAddlUsers(List<String> additionalUsers, boolean checked, int notificationId, String escalationGroup, int level) {
		// If the checkbox for adding additional users to the notification is checked and there exists additional users,
		// then add them to the notification escalation.
		if (checked && additionalUsers != null) {
			// The emails of all previous users will be deleted before the ones from the form are inserted.
			notificationDao.deleteAllUserEmailsForNotificationGroupAndLevel(notificationId, escalationGroup, level);
			
			for (int i = 0; i < additionalUsers.size(); i++) {
				notificationDao.insertNotificationParty(notificationId, escalationGroup, level, additionalUsers.get(i), 1);
			}
		}
		// Otherwise, just delete all current users from the notification escalation.
		else {
			notificationDao.deleteAllUserEmailsForNotificationGroupAndLevel(notificationId, escalationGroup, level);
		}
	}

	//Sorts the notification parties for each notification into VENDOR, PURCHASING, and PLANNING
	@Override
	public void getSortedNotificationParties(Notification notification) {
		notification.setVendorNotificationParties(notificationDao.selectAllNotificationPartiesByGroup(notification.getNotificationId(), "VENDOR"));
		notification.sortContacts(notification.getVendorNotificationParties());
		
		notification.setPurchasingNotificationParties(notificationDao.selectAllNotificationPartiesByGroup(notification.getNotificationId(), "PURCHASING"));
		notification.sortContacts(notification.getPurchasingNotificationParties());
		
		notification.setPlanningNotificationParties(notificationDao.selectAllNotificationPartiesByGroup(notification.getNotificationId(), "PLANNING"));
		notification.sortContacts(notification.getPlanningNotificationParties());
	}

	//Returns a list of all user emails - used for populating the additional users dropdown.
	@Override
	public List<String> getUserEmails() {
		return notificationDao.selectAllUserEmails();
	}

}
