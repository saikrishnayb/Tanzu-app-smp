package com.penske.apps.adminconsole.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.SecurityDAO;
import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserForm;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.exception.HumanReadableException;
import com.penske.apps.smccore.base.service.UserService;
import com.penske.apps.ucsc.exception.UsrCreationSvcException;
import com.penske.apps.ucsc.model.CreatedUser;
import com.penske.apps.ucsc.model.LDAPAttributes;
import com.penske.apps.ucsc.service.UserInfoService;
import com.penske.apps.ucsc.util.LDAPConstants;
import com.penske.business.ldap.sso.CPBGESSOUser;
import com.penske.util.security.priv.CPTSso;

@Service
public class UserCreationServiceImpl implements UserCreationService {

	private static final Logger logger = LogManager.getLogger(UserCreationServiceImpl.class);

	@Autowired
	private SecurityDAO securityDao;
	@Autowired
	private UserInfoService remoteCreationService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, UserServiceException.class })
	public EditableUser insertUserInfo(User currentUser, UserForm userForm, LookupContainer lookups,
			URL commonStaticUrl) {

		Role role = securityDao.getRoleById(userForm.getRoleId());
		CPBGESSOUser ldapUser = userService.getUserFromUserStore(userForm.getSsoId());

		EditableUser editableUser = new EditableUser();
		editableUser.setEmail(userForm.getEmail());
		editableUser.setSsoId(userForm.getSsoId());
		editableUser.setFirstName(userForm.getFirstName());
		editableUser.setLastName(userForm.getLastName());
		editableUser.setPhone(userForm.getPhone());
		editableUser.setExtension(userForm.getExtension());
		AdminConsoleUserType userType = new AdminConsoleUserType();
		userType.setUserTypeId(userForm.getUserTypeId());
		editableUser.setUserType(userType);
		editableUser.setOrgId(userForm.getOrgId());
		editableUser.setRole(role);
		editableUser.setDailyOptIn(userForm.isDailyOptIn());
		editableUser.setCreatedBy(currentUser.getSso());
		
		//Not the best way to handle validation but since we don't have a domain object this will have to do for now
		validateEditableUser(editableUser);
		
		if(ldapUser == null || !"A".equals(ldapUser.getGESSOStatus())){ // User not available in the LDAP. This flag is set after validating userid with LDAP.
			logger.info("Add User to LDAP..");
			insertUserToLDAP(editableUser);
		} else {
			logger.info(" Modify User to LDAP..");
			CPTSso oSSO = new CPTSso();
			ldapUser.setGESSOStatus("A");
			ldapUser.setCommonName(editableUser.getLastName() + ", " + editableUser.getFirstName());
			ldapUser.setEmailAddress(editableUser.getEmail());
			ldapUser.setGivenName(editableUser.getFirstName());
			ldapUser.setSurName(editableUser.getLastName());
			ldapUser.setPhone(editableUser.getPhone());
			editableUser.setGessouid(ldapUser.getGESSOUID());
			oSSO.modifyUser(ldapUser, editableUser.getSsoId());
		}

		// Add to DB - In future, we should merge this with addUser once creating a user
		// no longer uses EditableUser
		securityDao.addUser(editableUser);
		User newUser = userService.getUser(editableUser.getSsoId(), false, false);

		String oneTimePassword = editableUser.getDefaultPassword();
		boolean sendNewUserEmail = !userForm.isHoldEnrollmentEmail();

		userService.insertUserSecurity(currentUser, newUser, oneTimePassword, sendNewUserEmail, lookups,
				commonStaticUrl);

		return editableUser;
	}

	private void insertUserToLDAP(EditableUser editableUser) {
		if (!CommonUtils.validUserID(editableUser.getSsoId())) {
			throw new HumanReadableException("UserID " + editableUser.getSsoId() + " does not conform to standards.",
					false);
		}
		CreatedUser user = null;
		List<LDAPAttributes> attributeList = assignLDAPattribute(editableUser);
		try {
			user = remoteCreationService.createB2bLdapUser(attributeList);
		} catch (UsrCreationSvcException ex) {
			throw new HumanReadableException(ex.getMessage(), ex, true);
		}
		String strServerResponse = user != null ? user.getResponseMessage() : null;
		if (!StringUtils.isEmpty(strServerResponse)) {
			if (!strServerResponse.contains(IUserConstants.OPERATION_EXECUTED_SUCCESS)) {
				if (strServerResponse.contains(IUserConstants.ATTR_VAL_ALREADY_EXISTS)) {
					throw new HumanReadableException(
							"UserID " + editableUser.getSsoId() + " exists. Please choose a different UserID.", false);
				} else {
					throw new HumanReadableException(strServerResponse, true);
				}
			}
		} else {
			throw new HumanReadableException("Add user operation was not successful.", false);
		}
		editableUser.setDefaultPassword(user.getDefaultPassword());
		editableUser.setGessouid(user.getGessouid());
	}

	@Override
	@Transactional
	public EditableUser updateUserInfo(EditableUser userObj,boolean isDeactive)
	{
			validateEditableUser(userObj);
			userObj.setUserName(userObj.getSsoId());
			CPTSso oSSO = null;
			CPBGESSOUser oB2BUser = null;
			// Date date;
			// SimpleDateFormat sdf, sdf2;

			if (userObj.getUserName() != null) {
				oSSO = new CPTSso();
				oB2BUser = oSSO.findUser(userObj.getUserName().trim());
				if (oB2BUser != null) {
					userObj.setGessouid(oB2BUser.getGESSOUID());
				} else {
					throw new HumanReadableException("Cannot Find User in LDAP", false);
				}
			} else {
				throw new HumanReadableException("User object does not have sufficient data to proceed.", false);
			}

			/*
			 * if (isDeactive) { date = new Date(); CPTDate datStop = null;
			 * 
			 * sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss"); sdf2 = new
			 * SimpleDateFormat("MM/dd/yyyy"); datStop = CPTDate.createDateFromMMDDYYYY(
			 * sdf2.format(date) );
			 * oB2BUser.setGESSOEffectiveEndDate(datStop.convertDateForLDAP());
			 * oB2BUser.setGESSOStatus("I"); } else { CPTDate datStop = null;
			 * 
			 * sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss"); sdf2 = new
			 * SimpleDateFormat("MM/dd/yyyy"); datStop = CPTDate.createDateFromMMDDYYYY(
			 * "12/31/4712" );
			 * oB2BUser.setGESSOEffectiveEndDate(datStop.convertDateForLDAP());
			 * oB2BUser.setGESSOStatus("A"); }
			 */

			oB2BUser.setCommonName(userObj.getLastName() + ", " + userObj.getFirstName());
			oB2BUser.setEmailAddress(userObj.getEmail());
			oB2BUser.setGivenName(userObj.getFirstName());
			oB2BUser.setSurName(userObj.getLastName());
			oB2BUser.setPhone(userObj.getPhone());
			oSSO.modifyUser(oB2BUser, userObj.getUserName());
			if (isDeactive) {
				securityDao.modifyUserStatus(userObj.getUserId(), userObj.getModifiedBy());
			} else {
				securityDao.modifyUserInfo(userObj);
			}

			oB2BUser = oSSO.findUser(userObj.getUserName().trim());
		return userObj;
	}

	public List<LDAPAttributes> assignLDAPattribute(EditableUser editableUser) {
		List<LDAPAttributes> attributeList = new ArrayList<LDAPAttributes>();
		attributeList.add(createLDAPattribute(LDAPConstants.FIRST_NAME, editableUser.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.INITIALS, editableUser.getInitString()));
		attributeList.add(createLDAPattribute(LDAPConstants.LAST_NAME, editableUser.getLastName()));
		attributeList.add(createLDAPattribute(LDAPConstants.UNIQUE_ID, editableUser.getSsoId()));
		attributeList.add(createLDAPattribute(LDAPConstants.EMAIL_ADDRESS, editableUser.getEmail()));
		attributeList.add(createLDAPattribute(LDAPConstants.FULL_NAME,
				editableUser.getLastName() + "," + editableUser.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.PHONE, editableUser.getPhone()));
		attributeList.add(createLDAPattribute(LDAPConstants.GESSO_STATUS, "A"));
		attributeList.add(createLDAPattribute("ptlaccttype", "SMC"));
		return attributeList;
	}

	private LDAPAttributes createLDAPattribute(String key, String value) {
		LDAPAttributes attribute = new LDAPAttributes();
		attribute.setLdapAtribName(key);
		attribute.setValue(value != null ? value : "");
		return attribute;
	}

	@Override
	public boolean isEligibleToDeactivate(int userId, boolean isVendorUser, String currentUser) {
		EditableUser user = securityDao.getVendorUserInfo(userId);
		if (user != null) {
			user.setModifiedBy(currentUser);
			if (isVendorUser) {
				updateUserInfo(user, true);
			} else {
				// Do role check for the deleting and deleted user
			}
		}
		return true;
	}
	
	private void validateEditableUser(EditableUser editableUser) {
		if("".equals(editableUser.getEmail().trim()))
			throw new HumanReadableException("Email cannot be empty", false);
		if("".equals(editableUser.getSsoId().trim()))
			throw new HumanReadableException("SSO cannot be empty", false);
		if("".equals(editableUser.getFirstName().trim()))
			throw new HumanReadableException("First name cannot be empty", false);
		if("".equals(editableUser.getLastName().trim()))
			throw new HumanReadableException("Last name cannot be empty", false);
		if("".equals(editableUser.getPhone().trim()))
			throw new HumanReadableException("Phone cannot be empty", false);
		if(editableUser.getUserType().getUserTypeId() == 0)
			throw new HumanReadableException("UserId cannot be 0", false);
		if(editableUser.getOrgId() == 0)
			throw new HumanReadableException("OrgId cannot be 0", false);
		if(editableUser.getRole().getRoleId() == 0)
			throw new HumanReadableException("RoleId cannot be 0", false);
	}
}
