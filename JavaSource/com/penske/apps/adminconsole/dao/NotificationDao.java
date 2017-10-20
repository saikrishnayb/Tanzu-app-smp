package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Notification;
import com.penske.apps.adminconsole.model.NotificationForm;
import com.penske.apps.adminconsole.model.NotificationParty;

/**
 * Uses SQL statements defined in notification-mapper.xml to obtain relevant information related to notifications. Some of these may need to be deleted.
 * 
 * Created On: October 28th, 2014
 * @author 600144069
 *
 */


public interface NotificationDao {
	@NonVendorQuery //TODO: Review Query
	public Notification selectNotification(@Param("notificationId") int notificationId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Notification> selectAllNotifications();
	
	@NonVendorQuery //TODO: Review Query
	public List<NotificationParty> selectAllNotificationPartiesByGroup(@Param("notificationId") int notificationId, @Param("escalationGroup") String escalationGroup);
	
	@NonVendorQuery //TODO: Review Query
	public List<NotificationParty> selectEscalationContacts(@Param("escalationLevel") int escLevel, @Param("notificationId") int notificationId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateNotification(@Param("notificationForm") NotificationForm notificationForm);
	
	@NonVendorQuery //TODO: Review Query
	public int doesNotificationPartyExist(@Param("notificationId") int notificationId, @Param("escalationGroup") String escalationGroup, 
			@Param("escalationLevel") int escalationLevel, @Param("value") String value, @Param("isEmail") int isEmail);
	
	@NonVendorQuery //TODO: Review Query
	public void insertNotificationParty(@Param("notificationId") int notificationId, @Param("escalationGroup") String escalationGroup, 
			@Param("escalationLevel") int escalationLevel, @Param("value") String value, @Param("isEmail") int isEmail);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteNotificationParty(@Param("notificationId") int notificationId, @Param("escalationGroup") String escalationGroup, 
			@Param("escalationLevel") int escalationLevel, @Param("value") String value, @Param("isEmail") int isEmail);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> selectAllUserEmails();
	
	@NonVendorQuery //TODO: Review Query
	public void deleteAllUserEmailsForNotificationGroupAndLevel(@Param("notificationId") int notificationId, 
			@Param("escalationGroup") String escalationGroup, @Param("escalationLevel") int escalationLevel);
}
