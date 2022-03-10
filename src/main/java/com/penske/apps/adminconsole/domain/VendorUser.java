package com.penske.apps.adminconsole.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserForm;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.smccore.base.util.DateUtil;

/**
 *	Represents a vendor user in Admin Console. Perhaps should extend the User object in SMC Core one day when we get rid of 
 *	EditableUser and update other User functions in Admin Console.
 */
public class VendorUser {
	/** The internal DB id of the vendor user */
	private Integer userId;
	/** The email address of the vendor user */
	private String email;
	/** The sso/username the vendor user */
	private String ssoId;
	/** The vendor user's first name */
	private String firstName;
	/** The vendor user's last name */
	private String lastName;
	/** The vendor user's phone number */
	private String phone;
	/** The vendor user's extension (if they have one) */
	private String extension;
	/** The vendor user's user type (will always be vendor, this is only really used for inserting the user into the DB) */
	private UserType userType;
	/** The ID of the org this user belongs to */
	private int orgId;
	/** The name of the org this user belongs to */
	private String org;
	/** The gessouid for this vendor user */
	private String gessouid;
	/** Whether this user has opted into the Daily Summary Emails put out by SMC Notify */
	private boolean dailyOptIn;
	/** Whether this user has not logged in and has a one time password still in SMC_USER_SECURITY */
	private boolean hasOtp;
	/** The date of the user's last login */
	private Date lastLoginDate;
	/** The date the user was created */
	private Date createdDate;
	
	private Role role;
	
	protected VendorUser() {};

	/**
	 *	The constructor for creating a vendor user from the form submitted on the Vendor Users page
	 */
	public VendorUser(UserForm userForm, Role role) {
		this.userId = null;
		this.email= userForm.getEmail();
		this.ssoId = userForm.getSsoId();
		this.firstName = userForm.getFirstName();
		this.lastName= userForm.getLastName();
		this.phone= userForm.getPhone();
		this.extension= userForm.getExtension();
		this.userType = UserType.VENDOR;
		this.org = null;
		this.orgId = userForm.getOrgId();
		this.gessouid = userForm.getGessouid();
		this.dailyOptIn = userForm.isDailyOptIn();
		this.hasOtp = false;
		this.createdDate = null;
		this.lastLoginDate = null;
		
		
		this.role = role;
	}
	
	/**
	 *	The constructor for creating a vendor user from an Editable User. Used when loading the Vendor Users page
	 */
	public VendorUser(EditableUser editableUser) {
		this.userId = editableUser.getUserId();
		this.email= editableUser.getEmail();
		this.ssoId = editableUser.getSsoId();
		this.firstName = editableUser.getFirstName();
		this.lastName= editableUser.getLastName();
		this.phone= editableUser.getPhone();
		this.extension= editableUser.getExtension();
		this.userType = UserType.VENDOR;
		this.org = editableUser.getOrg();
		this.orgId = editableUser.getOrgId();
		this.gessouid = editableUser.getGessouid();
		this.dailyOptIn = editableUser.isDailyOptIn();
		this.hasOtp = editableUser.isHasOtp();
		this.createdDate = editableUser.getCreatedDate();
		this.lastLoginDate= editableUser.getLastLoginDate();
		
		
		this.role = editableUser.getRole();
		
	}
	
	// ***** MODIFIED ACCESSORS ***** //
	/**
	 *	Method to format the last login date to display
	 */
	public String getFormattedLastLoginDate() {
		if(lastLoginDate == null)
			return "(Never)";
		
		String formattedLastLoginDate = DateUtil.formatDateTimeUS(lastLoginDate);
		return formattedLastLoginDate;
	}

	/**
	 *	Method to format the created date to display
	 */
	public String getFormattedCreatedDate() {
		return DateUtil.formatDateTimeUS(createdDate);
	}
	
	/**
	 *	Method to format the phone number with the extension (if one exists) to display
	 */
	public String getFormattedPhone() {
		if(StringUtils.isBlank(extension)) {
			return phone;
		}
		else
			return phone + " ext. " + extension;
	}

	// ***** DEFAULT ACCESSORS ***** //
	public Integer getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getSsoId() {
		return ssoId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getExtension() {
		return extension;
	}

	public UserType getUserType() {
		return userType;
	}

	public int getOrgId() {
		return orgId;
	}

	public String getOrg() {
		return org;
	}

	public String getGessouid() {
		return gessouid;
	}

	public boolean isDailyOptIn() {
		return dailyOptIn;
	}

	public boolean isHasOtp() {
		return hasOtp;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Role getRole() {
		return role;
	}
}
