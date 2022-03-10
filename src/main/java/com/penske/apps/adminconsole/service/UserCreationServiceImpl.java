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
import com.penske.apps.adminconsole.domain.VendorUser;
import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserForm;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;
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
	@Transactional(rollbackFor = {RuntimeException.class, UserServiceException.class})
	public VendorUser insertUserInfo(User currentUser, UserForm userForm, LookupContainer lookups, URL commonStaticUrl) throws UserServiceException {
		
		if(userForm.getReturnFlg() != 1){ // userObj.getReturnFlg() != 1 -- User not available in the LDAP. This flag is set after validating userid with LDAP.
			logger.info("Add User to LDAP..");
			try {
				insertUserToLDAP(userForm);
			} catch (UserServiceException userException) {
				throw userException;
			} catch (UsrCreationSvcException e) {
				throw new UserServiceException(e.getMessage(), e);
			}
		}else{
			logger.info(" Modify User to LDAP..");
			CPTSso oSSO = new CPTSso();
			CPBGESSOUser oB2BUser = oSSO.findUser(userForm.getSsoId().trim());
			oB2BUser.setGESSOStatus("A");
			oB2BUser.setCommonName(userForm.getLastName() + ", " + userForm.getFirstName());
			oB2BUser.setEmailAddress(userForm.getEmail());
			oB2BUser.setGivenName(userForm.getFirstName());
			oB2BUser.setSurName(userForm.getLastName());
			oB2BUser.setPhone(userForm.getPhone());
			userForm.setGessouid(oB2BUser.getGESSOUID());
			oSSO.modifyUser(oB2BUser, userForm.getSsoId());
		}
		
		Role role = securityDao.getRoleById(userForm.getRoleId());
		
		VendorUser vendorUser = new VendorUser(userForm, role);
		
		//Add to DB - In future, we should merge this with addUser once creating a user no longer uses EditableUser
		securityDao.addVendorUser(vendorUser);
		User newUser = userService.getUser(vendorUser.getSsoId(), false, false);
		
		String oneTimePassword = userForm.getOtp();
		boolean sendNewUserEmail = !userForm.isHoldEnrollmentEmail();
		
		userService.insertUserSecurity(currentUser, newUser, oneTimePassword, sendNewUserEmail, lookups, commonStaticUrl);
		
		return vendorUser;
	}

	private void insertUserToLDAP(UserForm userForm) throws UserServiceException, UsrCreationSvcException {
		if(!CommonUtils.validUserID(userForm.getSsoId())){
			logger.error("UserID " + userForm.getSsoId() + " does not conform to standards.");
			throw new UserServiceException(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE);
		}
		CreatedUser user=null;
		List<LDAPAttributes> attributeList= assignLDAPattribute(userForm);
		user=	remoteCreationService.createB2bLdapUser(attributeList);
		String strServerResponse =user!=null?user.getResponseMessage():null;
		if (!StringUtils.isEmpty(strServerResponse)){
			if( !strServerResponse.contains(IUserConstants.OPERATION_EXECUTED_SUCCESS)) {
				if ( strServerResponse.contains(IUserConstants.ATTR_VAL_ALREADY_EXISTS)) {
					logger.info("UserID "+ userForm.getSsoId() + " exists. Please choose a different UserID.");
					throw new UserServiceException(IUserConstants.DUP_SSO_ERROR_CODE);
				} else {
					logger.info(strServerResponse);
					throw new UserServiceException(IUserConstants.WEBSERVICE_RESPONSE_ERROR_CODE);
				}
			}
		}else {
			logger.info(" Add user operation was not successful.");
			throw new UserServiceException(IUserConstants.EMPTY_RESPONSE_ADD_ERROR_CODE);
		}
		userForm.setOtp(user.getDefaultPassword());
		userForm.setGessouid(user.getGessouid());
	}
	
	@Override
	@Transactional
	public EditableUser updateUserInfo(EditableUser userObj,boolean isDeactive)  throws UserServiceException {
		try{
			userObj.setUserName(userObj.getSsoId());
			CPTSso oSSO = null;
			CPBGESSOUser oB2BUser = null;
			//Date date;
			//SimpleDateFormat sdf, sdf2;
			
			if (userObj.getUserName() != null) {
				oSSO = new CPTSso();
				oB2BUser = oSSO.findUser(userObj.getUserName().trim());
				if (oB2BUser != null) {
					userObj.setGessouid(oB2BUser.getGESSOUID());
				} else {
					logger.info("Cant find user in LDAP.");
					throw new UserServiceException(IUserConstants.USER_NOT_FOUND_LDAP_ERROR_CODE);
				}
			}else{
				logger.info("User Object dont have sufficent data to proceed");
				throw new UserServiceException(IUserConstants.REQUEST_FAILED_ERROR_CODE);
			}
			
			/*	if (isDeactive) {
				date = new Date();
				CPTDate datStop  = null;

				sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
				sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				datStop = CPTDate.createDateFromMMDDYYYY( sdf2.format(date) );
				oB2BUser.setGESSOEffectiveEndDate(datStop.convertDateForLDAP());
				oB2BUser.setGESSOStatus("I");
			} else {
				CPTDate datStop  = null;

				sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
				sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				datStop = CPTDate.createDateFromMMDDYYYY( "12/31/4712" );
				oB2BUser.setGESSOEffectiveEndDate(datStop.convertDateForLDAP());
				oB2BUser.setGESSOStatus("A");
			}*/
			
			oB2BUser.setCommonName(userObj.getLastName() + ", " + userObj.getFirstName());
			oB2BUser.setEmailAddress(userObj.getEmail());
			oB2BUser.setGivenName(userObj.getFirstName());
			oB2BUser.setSurName(userObj.getLastName());
			oB2BUser.setPhone(userObj.getPhone());
			oSSO.modifyUser(oB2BUser, userObj.getUserName());
			if (isDeactive) {
				securityDao.modifyUserStatus(userObj.getUserId(),userObj.getModifiedBy());
			}else{
				securityDao.modifyUserInfo(userObj);
			}
			
			oB2BUser = oSSO.findUser(userObj.getUserName().trim());
		}catch (Exception e) {
			logger.error("Exception occured while updating user  " + userObj.getUserName(), e);
			throw new UserServiceException(e.getMessage());
		}
		return userObj;
	}

	public List<LDAPAttributes> assignLDAPattribute(UserForm userForm){
		List<LDAPAttributes> attributeList=new ArrayList<LDAPAttributes>();
		attributeList.add(createLDAPattribute(LDAPConstants.FIRST_NAME, userForm.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.INITIALS, null));
		attributeList.add(createLDAPattribute(LDAPConstants.LAST_NAME, userForm.getLastName()));
		attributeList.add(createLDAPattribute(LDAPConstants.UNIQUE_ID, userForm.getSsoId()));
		attributeList.add(createLDAPattribute(LDAPConstants.EMAIL_ADDRESS, userForm.getEmail()));
		attributeList.add(createLDAPattribute(LDAPConstants.FULL_NAME,userForm.getLastName() + "," + userForm.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.PHONE, userForm.getPhone()));
		attributeList.add(createLDAPattribute(LDAPConstants.GESSO_STATUS, "A"));
		return attributeList;
	}
	
	private LDAPAttributes createLDAPattribute(String key,String value){
		LDAPAttributes attribute=new LDAPAttributes();
		attribute.setLdapAtribName(key);
		attribute.setValue(value!=null?value:"");
		return  attribute;
	}
	
	@Override
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser,String currentUser) throws UserServiceException{
		EditableUser user=securityDao.getVendorUserInfo(userId);
		if(user !=null){
			user.setModifiedBy(currentUser);
			if(isVendorUser){
				updateUserInfo(user, true);
			}else{
				//Do role check for the deleting and deleted user
			}
		}
		return true;
	}
}
