package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.SecurityDao;
import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.adminconsole.util.CommonUtils;
import com.penske.apps.adminconsole.util.IUserConstants;
import com.penske.apps.suppliermgmt.common.util.LookupManager;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.ucsc.exception.UsrCreationSvcException;
import com.penske.apps.ucsc.model.CreatedUser;
import com.penske.apps.ucsc.model.LDAPAttributes;
import com.penske.apps.ucsc.service.UserInfoService;
import com.penske.apps.ucsc.util.LDAPConstants;
import com.penske.business.ldap.sso.CPBGESSOUser;
import com.penske.util.security.priv.CPTSso;
@Service
public class UserCreationServiceImpl implements UserCreationService {

	private static final Logger logger = Logger.getLogger(UserCreationServiceImpl.class);
	
	@Autowired
	private SecurityDao securityDao;
	
	@Autowired
	private UserInfoService remoteCreationService;
	
	@Autowired
	private IMailService mailService;
	
	@Autowired
	private LookupManager lookupManager;

	
	@Override
	@Transactional
	public User insertUserInfo(User userObj) throws UserServiceException {
		try{
			userObj.setUserName(userObj.getSsoId());
			if(userObj.getReturnFlg() != 1){ // userObj.getReturnFlg() != 1 -- User not available in the LDAP. This flag is set after validating userid with LDAP.
				logger.info("Add User to LDAP..");
				insertUserToLDAP(userObj);
			}else{
				logger.info(" Modify User to LDAP..");
				CPTSso oSSO = new CPTSso();
				CPBGESSOUser oB2BUser = oSSO.findUser(userObj.getUserName().trim());
				//CPTDate datStop  = null;
				//datStop = CPTDate.createDateFromMMDDYYYY( "12/31/4712" );
				//oB2BUser.setGESSOEffectiveEndDate(datStop.convertDateForLDAP());
				oB2BUser.setGESSOStatus("A");
				oB2BUser.setCommonName(userObj.getLastName() + ", " + userObj.getFirstName());
				oB2BUser.setEmailAddress(userObj.getEmail());
				oB2BUser.setGivenName(userObj.getFirstName());
				oB2BUser.setSurName(userObj.getLastName());
				oB2BUser.setPhone(userObj.getPhone());
				userObj.setGessouid(oB2BUser.getGESSOUID());
				oSSO.modifyUser(oB2BUser, userObj.getUserName());
			}
			//Add to DB
			boolean status=securityDao.addUser(userObj);
			if(status){
				logger.info(" Add user operation was successful. Send mail to Vendor User");
				try{
						MailRequest mailRequest=populateMailRequestObj(userObj);
						mailRequest.setUserId(userObj.getCreatedBy());
						if(mailRequest.getToRecipientsList() !=null && !mailRequest.getToRecipientsList().isEmpty()){
							mailRequest.setToList(mailRequest
								.getToRecipientsList()
								.toString()
								.substring(1,mailRequest.getToRecipientsList().toString().length() - 1)
								.replace(", ", ","));
						}
						securityDao.addEmailSent(mailRequest);//Email Content to SMC_EMAIL-- Start.
						int emailAuditId=mailRequest.getEmailAuditId();
						mailService.sendEmail(mailRequest);
						//Email Content to SMC_EMAIL-- update after email sent. Need to remove when moved to EBS.
						securityDao.updateEmailSent(emailAuditId);
				}catch (Exception e) {
					logger.error("Mail Sending failed for user [ "+userObj.getUserName()+" ]",e);
				}
			}
		}
		catch (UserServiceException userException) {
			throw userException;
		}		
		catch (Exception e) {
			logger.error("Exception occured while creating user  " + userObj.getUserName(), e);
			throw new UserServiceException(e.getMessage());
		}
		
		return userObj;
	}


	private User insertUserToLDAP(User userObj) throws UserServiceException, UsrCreationSvcException {
		if(!CommonUtils.validUserID(userObj.getUserName())){
			logger.error("UserID " + userObj.getUserName() + " does not conform to standards.");
			throw new UserServiceException(IUserConstants.NOT_STANDARD_SSO_ERROR_CODE);
		}
		CreatedUser user=null;
		List<LDAPAttributes> attributeList= assignLDAPattribute(userObj);
		user=	remoteCreationService.createB2bLdapUser(attributeList);
		String strServerResponse =user!=null?user.getResponseMessage():null;
		if (!StringUtils.isEmpty(strServerResponse)){
			if( !strServerResponse.contains(IUserConstants.OPERATION_EXECUTED_SUCCESS)) {
				if ( strServerResponse.contains(IUserConstants.ATTR_VAL_ALREADY_EXISTS)) {
					logger.info("UserID "+ userObj.getUserName() + " exists. Please choose a different UserID.");
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
		userObj.setDefaultPassword(user.getDefaultPassword());
		userObj.setGessouid(user.getGessouid());
		return userObj;
	}
	
	@Override
	@Transactional
	public User updateUserInfo(User userObj,boolean isDeactive)  throws UserServiceException {
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

	public List<LDAPAttributes> assignLDAPattribute(User userBean){
		List<LDAPAttributes> attributeList=new ArrayList<LDAPAttributes>();
		attributeList.add(createLDAPattribute(LDAPConstants.FIRST_NAME, userBean.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.INITIALS, userBean.getInitString()));
		attributeList.add(createLDAPattribute(LDAPConstants.LAST_NAME, userBean.getLastName()));
		attributeList.add(createLDAPattribute(LDAPConstants.UNIQUE_ID, userBean.getUserName()));
		attributeList.add(createLDAPattribute(LDAPConstants.EMAIL_ADDRESS, userBean.getEmail()));
		attributeList.add(createLDAPattribute(LDAPConstants.FULL_NAME,userBean.getLastName() + "," + userBean.getFirstName()));
		attributeList.add(createLDAPattribute(LDAPConstants.PHONE, userBean.getPhone()));
		attributeList.add(createLDAPattribute(LDAPConstants.GESSO_STATUS, "A"));
		return attributeList;
	}
	
	private LDAPAttributes createLDAPattribute(String key,String value){
		LDAPAttributes attribute=new LDAPAttributes();
		attribute.setLdapAtribName(key);
		attribute.setValue(value!=null?value:"");
		return  attribute;
	}
	
	private MailRequest populateMailRequestObj(User userObj){
		MailRequest mailRequest=new MailRequest();
		mailRequest.setFromAddress(lookupManager.getLookUpListByName(ApplicationConstants.EBS_FROM_ADDRESS).get(0).getLookUpValue());
		List<String> toAddress=new ArrayList<String>();
		toAddress.add(userObj.getEmail());
		mailRequest.setToRecipientsList(toAddress);
		mailRequest.setSubject("Welcome to Supplier Management Center");
		if(userObj.getReturnFlg()==1){
			mailRequest.setMessageContent(buildMailBodyExistingUser(userObj));
		}else{
			mailRequest.setMessageContent(buildMailBodyNewUser(userObj));
		}
		return mailRequest;
	}
	
	
	private String buildMailBodyNewUser(User userObject)
	{
		StringBuffer mailBody = new StringBuffer();		
		Calendar cal = Calendar.getInstance();
		try{
			mailBody.append("<HTML><BODY>");
			mailBody.append("<BR/>Dear ").append(userObject.getFirstName()).append(" ").append(userObject.getLastName()).append(",");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>A Single Sign-On (SSO) account has been created for you.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Please note the details of your new SSO account below.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>&nbsp;Name: ").append(userObject.getFirstName()).append(" ").append(userObject.getLastName());
			mailBody.append("<BR/>&nbsp;SSO ID: ").append(userObject.getUserName());
			mailBody.append("<BR/>&nbsp;Default one time use password: ").append(userObject.getDefaultPassword());
			mailBody.append("<BR/>&nbsp;Email Address: ").append(userObject.getEmail());
			mailBody.append("<BR/>&nbsp;Date/Time: ").append(cal.getTime());
			mailBody.append("<BR/>");
			
			String signInURL=null;
			if(lookupManager !=null){
				List<LookUp> list= lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_SIGN_ON_URL);
				if(list !=null){
					LookUp lookUpObj=lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_SIGN_ON_URL).get(0);
					if(lookUpObj !=null){
						signInURL=lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_SIGN_ON_URL).get(0).getLookUpValue();
						if(signInURL==null){
							logger.info("signInURL is null");
						}
					}else{
						logger.info("lookUpObj is null");
					}
				}else{
					logger.info("list is null for "+ApplicationConstants.PENSKE_SIGN_ON_URL);
				}
			}else{
				logger.info("lookupManager is null");
			}
			mailBody.append("<BR/>To begin you will need to activate your SSO account by changing your password and " +
					"creating a challenge question. <a href='").append(signInURL).append("'>Click here").append("</a> " +
					"to change your password and create a challenge question.");
			mailBody.append("<BR/>");
			
			String smcURL=null;
			if(lookupManager !=null){
				List<LookUp> list= lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL);
				if(list !=null){
					LookUp lookUpObj=lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL).get(0);
					if(lookUpObj !=null){
						smcURL=lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL).get(0).getLookUpValue();
						if(smcURL==null){
							logger.info("smcURL is null");
						}
					}else{
						logger.info("lookUpObj is null");
					}
				}else{
					logger.info("list is null for "+ApplicationConstants.SMC_APPLICATION_URL);
				}
			}else{
				logger.info("lookupManager is null");
			}
			mailBody.append("<BR/>You may access your SSO ID by <a href='").append(smcURL).append("'> clicking here").append("</a>, only after completing the above activation process.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>If you have any questions, contact Penske's customer service Monday through Friday at ");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Phone: ").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_CUST_SERVICE_TOLL_FREE).get(0).getLookUpValue());
			mailBody.append("<BR/>Email: ").append(lookupManager
					.getLookUpListByName(
							ApplicationConstants.PENSKE_CUST_SERVICE_EMAIL).get(0).getLookUpValue());
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Thank you,");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Penske IT Service Desk");
			mailBody.append("<BR/>").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_IT_SERVICE_TOLL_FREE).get(0).getLookUpValue());
			mailBody.append("<BR/>").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_IT_SERVICE_EMAIL).get(0).getLookUpValue());
			mailBody.append("<BR/>");
			mailBody.append("<BR/>This is an automated message; please do not reply to it.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Note: The information will be used to authorize your access to this and other " +
					"SSO-enabled sites and may be shared with other Penske affiliates to authorize your access " +
					"to SSO-enabled sites (wherever located worldwide) that they may operate and that you choose to visit.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/><I>The information contained in this e-mail is intended only for the individual or " +
					"entity to which it is addressed. Its contents (including any attachments) may contain " +
					"confidential and/or privileged information. If you are not an intended recipient, you are " +
					"prohibited from using, disclosing, disseminating, copying or printing its contents. If you " +
					"received this e-mail in error, please immediately notify the sender by reply e-mail and " +
					"delete and destroy the message.  Thank you.</I>");			
			mailBody.append("</BODY></HTML>");
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
		}
		return mailBody.toString();
	}
	
	private String buildMailBodyExistingUser(User userObject)
	{
		StringBuffer mailBody = new StringBuffer();
		try{
			mailBody.append("<HTML><BODY>");
			mailBody.append("<BR/>Dear ").append(userObject.getFirstName()).append(" ").append(userObject.getLastName()).append(",");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>You have been created as a ");
			mailBody.append("Vendor User");
			mailBody.append(" in the SUPPLIER MANAGEMENT CENTER (SMC) application.");
			mailBody.append("<BR/>");
			
			String smcURL=null;
			if(lookupManager !=null){
				List<LookUp> list= lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL);
				if(list !=null){
					LookUp lookUpObj=lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL).get(0);
					if(lookUpObj !=null){
						smcURL=lookupManager.getLookUpListByName(ApplicationConstants.SMC_APPLICATION_URL).get(0).getLookUpValue();
						if(smcURL==null){
							logger.info("smcURL is null");
						}
					}else{
						logger.info("lookUpObj is null");
					}
				}else{
					logger.info("list is null for "+ApplicationConstants.SMC_APPLICATION_URL);
				}
			}else{
				logger.info("lookupManager is null");
			}
			
			mailBody.append("<BR/>You may use your Single Sign-On (SSO) ID to access the application by ").append("<a href='").append(smcURL).append("'>clicking here").append("</a>");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>If you have any questions, contact Penske's customer service Monday through Friday at ");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Phone: ").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_CUST_SERVICE_TOLL_FREE).get(0).getLookUpValue());
			mailBody.append("<BR/>Email: ").append(lookupManager
					.getLookUpListByName(
							ApplicationConstants.PENSKE_CUST_SERVICE_EMAIL).get(0).getLookUpValue());
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Thank you,");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Penske IT Service Desk");
			mailBody.append("<BR/>").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_IT_SERVICE_TOLL_FREE).get(0).getLookUpValue());
			mailBody.append("<BR/>").append(lookupManager.getLookUpListByName(ApplicationConstants.PENSKE_IT_SERVICE_EMAIL).get(0).getLookUpValue());
			mailBody.append("<BR/>");
			mailBody.append("<BR/>This is an automated message; please do not reply to it.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/>Note: The information will be used to authorize your access to this and other " +
					"SSO-enabled sites and may be shared with other Penske affiliates to authorize your access " +
					"to SSO-enabled sites (wherever located worldwide) that they may operate and that you choose to visit.");
			mailBody.append("<BR/>");
			mailBody.append("<BR/><I>The information contained in this e-mail is intended only for the individual or " +
					"entity to which it is addressed. Its contents (including any attachments) may contain " +
					"confidential and/or privileged information. If you are not an intended recipient, you are " +
					"prohibited from using, disclosing, disseminating, copying or printing its contents. If you " +
					"received this e-mail in error, please immediately notify the sender by reply e-mail and " +
					"delete and destroy the message.  Thank you.</I>");				
			mailBody.append("</BODY></HTML>");
		}catch(Exception e){
			logger.error(e.getLocalizedMessage(), e);
		}
		return mailBody.toString();
	}
	
	@Override
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser,String currentUser) throws UserServiceException{
		User user=securityDao.getVendorUserInfo(userId);
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
	
	@Override
	public String getSupportNumber(){
		return lookupManager.getLookUpListByName(ApplicationConstants.SUPPORT_NUM).get(0).getLookUpValue();
	}
}
