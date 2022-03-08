package com.penske.apps.suppliermgmt.beans;

import java.time.LocalDateTime;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.model.AppConfigSessionData;


public interface SuppliermgmtSessionBean {

	/**
	 * Set up fields in the session bean when the user first logs into the application. For most of these, this is the only time those fields will need to be set for that session.
	 * @param user The user that logged in.
	 * @param baseUrl URL fragment denoting the name of the application. Prepended to most resources and relative URLs in the application.
	 * @param lastUserLoginDate The previous time the user logged into the system, before this one
	 * @param buddyFilterApplied True if the user has buddies selected in their buddy list; false if they do not, or are not allowed to
	 * @param vendorFilterApplied True if the user has a vendor filter applied; false if they do not, or are not allowed to
	 */
	public void initialize(User user, String baseUrl, LocalDateTime lastUserLoginDate, boolean buddyFilterApplied, boolean vendorFilterApplied, boolean vendorFilterActive);

	/**
     * Gets an object for the currently logged in user.
     * @return The user that is currently logged in
     */
    public User getUser();
    
    /**
     * Gets a String representing the last time the user logged in
     * @return The string representation of the last time the user logged in
     */
    public String getFormattedUserLoginDate();
    
    /**
     * Gets a fragment to be prepended to all relative URLs in the application
     * @return The URL fragment
     */
    public String getBaseUrl();

    /**
     * Checks if the user has buddies selected in their buddy list
     * @return True if the user has buddies selected in their buddy list; false if they do not, or are not allowed to
     */
    public boolean isBuddyListApplied();

    /**
     * Checks if the user has a vendor filter applied
     * @return True if the user has a vendor filter applied; false if they do not, or are not allowed to
     */
    public boolean isVendorFilterApplied();
    
    /**
     * Gets parameters used by the App Config tab pages.
     */
    public AppConfigSessionData getAppConfigSessionData();

	boolean isVendorFilterActive();
}