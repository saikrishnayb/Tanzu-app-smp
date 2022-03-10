package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.SecurityDAO;
import com.penske.apps.adminconsole.domain.VendorUser;
import com.penske.apps.adminconsole.model.AdminConsoleUserDept;
import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.SignatureInitial;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.exception.SMCException;
import com.penske.apps.suppliermgmt.model.ErrorModel;
import com.penske.business.ldap.sso.CPBGESSOUser;
import com.penske.util.security.priv.CPTSso;


/**
 * this is the service implementation for the security service.
 * This class contains the methods that do the business logic for
 * security controller.
 * 
 * @author kenneth.french 600143640
 *
 *
 */

@Service
public class DefaultSecurityService implements SecurityService{

	@Autowired
	private SecurityDAO securityDao;

	@Override
	public EditableUser getEditInfo(String userId, String userType) {
		boolean isPenskeUser = "Penske".equalsIgnoreCase(userType);
		int userIdNo = Integer.parseInt(userId);

		EditableUser user = new EditableUser();

		if(isPenskeUser){
			user = securityDao.getPenskeUserInfo(userIdNo);
			if(user.getSignFile() != null && !"".equalsIgnoreCase(user.getSignString()))
				user.setHasSignFile(true);
			else
				user.setHasSignFile(false);
			if(user.getInitFile() != null && !"".equalsIgnoreCase(user.getInitString()))
				user.setHasInitFile(true);
			else
				user.setHasInitFile(false);
		} else{
			/*user = securityDao.getVendorUserInfo(userIdNo);
			if(user.getVendor() != null && user.getVendor().getVendorName()!= null && !"".equalsIgnoreCase(user.getVendor().getVendorName()))
			  user.getVendor().setVendorLocations(securityDao.getVendorUserLocations(user.getUserId()));
			else
				user.setVendor(null);*/
			user = securityDao.getVendorUserInfo(userIdNo);
		}

		return user;
	}

	@Override
	public void modifyUserStatus(int userId, User currentUser) {
		
		String userSSO="";
		if(currentUser.getUserType() == UserType.PENSKE){
			List<Role> myRoleList = securityDao.getPenskeRoles(currentUser.getRoleId());
			EditableUser user = securityDao.getUser(userId);
			userSSO = user.getUserName();
			boolean deactivatible = false;
			for(Role role: myRoleList){
				if(role.getRoleId() == user.getRole().getRoleId()){
					deactivatible = true;
					break;
				}
			}
			if(!deactivatible)
				return;
		}
		securityDao.modifyUserStatus(userId,currentUser.getSso());
		securityDao.deleteUserFromBuddy(userSSO);
	}

	@Override
	public HashMap<String, List<Permission>> getPermissions(String roleId) {
		int roleIdNo = Integer.parseInt(roleId);

		List<Permission> permissionList = securityDao.getPermissions(roleIdNo);
		List<String> tabNameList = securityDao.getAllTabNames();

		HashMap<String, List<Permission>> tabPermissionMap = null;
		if(tabNameList !=null && !tabNameList.isEmpty()){
			for(String tabName: tabNameList){
				List<Permission> tabPermissionList = new ArrayList<Permission>();
				for(Permission permission: permissionList){
					if(permission.getTabName().equalsIgnoreCase(tabName))	
						tabPermissionList.add(permission);
				}
				if(tabPermissionList.size() > 0){
					if(tabPermissionMap == null){
						tabPermissionMap=new HashMap<String, List<Permission>>();
					}
					tabPermissionMap.put(tabName, tabPermissionList);
				}
			}
		}
		return tabPermissionMap;
	}

	@Override
	public List<Role> getUserRoles(int roleId) {
		
		List<Role> allRoleList = securityDao.getAllUserRoles();
		
		List<Role> myRoleList = securityDao.getPenskeRoles(roleId);
		
		List<Role> allPenskeRolesList = securityDao.getPenskeRoles(4);
		
		Iterator<Role> myRoleIt = myRoleList.iterator();
		Iterator<Role> allPenskeRoleIt = allPenskeRolesList.iterator();
		while(myRoleIt.hasNext()){
			Role role = myRoleIt.next();
			while(allPenskeRoleIt.hasNext()){
				Role penskeRole = allPenskeRoleIt.next();
				if(penskeRole.getRoleId() == role.getRoleId()){
					allPenskeRoleIt.remove();
					break;
				}
			}
		}
		myRoleIt = allRoleList.iterator();
		allPenskeRoleIt = allPenskeRolesList.iterator();
		while(allPenskeRoleIt.hasNext()){
			Role role = allPenskeRoleIt.next();
			while(myRoleIt.hasNext()){
				if(myRoleIt.next().getRoleId() == role.getRoleId()){
					myRoleIt.remove();
					break;
				}
			}
		}
		return allRoleList;
	}

	@Override
	public void modifyUserInfo(EditableUser user, int[] vendorIds, User currentUser) {
		//check edit information
		boolean validUser = !user.validateUserInfo();
		
		if(validUser)
			return;
		
		int userTypeId = user.getUserType().getUserTypeId();
		
		boolean isPenskeUser = (ApplicationConstants.PENSKE_USER == userTypeId);
		boolean isSupplierUser = (ApplicationConstants.SUPPLIER_USER == userTypeId);
		user.setModifiedBy(currentUser.getSso());
		//make edit changes
		//updated for adding comments to the base64 changes
		if (isPenskeUser) {
			SignatureInitial signatureInitial=securityDao.getSignatureInitialByUserId(user.getUserId());
			if (user.getSignFile() == null && !user.isHasSignFile()) {
				user.setSignString(new String(Base64.encodeBase64("".getBytes())));
			}else if(user.getSignFile() !=null){
                String signString = new String(Base64.encodeBase64(user.getSignFile().getBytes()));
                user.setSignString(signString);
			}else{
				if(user.isHasSignFile()){
					user.setSignString(signatureInitial.getSignString());
							//securityDao.getSignatureImage(user.getUserId()));
				}
			}
			if (user.getInitFile() == null && !user.isHasInitFile()) {
				user.setInitString(new String(Base64.encodeBase64("".getBytes())));
			}else if(user.getInitFile() !=null){
				String initString = new String(Base64.encodeBase64(user.getInitFile().getBytes()));
				user.setInitString(initString);
			}else{
				if(user.isHasInitFile()){
					user.setInitString(signatureInitial.getInitString());
							//securityDao.getInitialsImage(user.getUserId()));
				}
			}
			if(signatureInitial== null){
				user.setCreatedBy(currentUser.getSso());
				securityDao.addUserInitials(user);
			}else{
				securityDao.modifyPenskeUser(user);
			}
		} else if (isSupplierUser) {
			boolean isEmptyVendorList = vendorIds.length == 0;
			if (isEmptyVendorList)
				return;
		}
		securityDao.modifyUserInfo(user);
		if(user.getUserDept() !=null){
			AdminConsoleUserDept depart=securityDao.getUserDeptsById(user.getUserDept().getUserDeptId());
			if(depart !=null){
				user.getUserDept().setUserDept(depart.getUserDept());
				securityDao.updateBuddies(user);
			}
		}
	}

	@Override
	public String getInitialsImage(int userId) {
	//	ImageFile imageFile = new ImageFile();
		String strInitFile = securityDao.getInitialsImage(userId);
		
			return strInitFile;
		
	}
	
	@Override
	public String getSignatureImage(int userId) {
		String signString = securityDao.getSignatureImage(userId);
		
				return signString;
		
	}

	@Override
	public void addUser(EditableUser user, int[] vendorIds, User currentUser) {
		user.setCreatedBy(currentUser.getSso());
		//user.setGessouid(user.getSsoId()); // If it is pensker
		securityDao.addUser(user);
		if(user.getUserDept() !=null){
			AdminConsoleUserDept depart=securityDao.getUserDeptsById(user.getUserDept().getUserDeptId());
			if(depart !=null){
				user.getUserDept().setUserDept(depart.getUserDept());
				securityDao.addBuddies(user);
			}
		}
		boolean isSupplierUser = (ApplicationConstants.SUPPLIER_USER == user.getUserType().getUserTypeId());
		//updated for adding comments to the base64 changes
		if(isSupplierUser){
		}else{
			if (user.getSignFile() == null) {
				user.setSignString(new String(Base64.encodeBase64("".getBytes())));
			}else{
                String signString = new String(Base64.encodeBase64(user.getSignFile().getBytes()));
                user.setSignString(signString);
			}
			if (user.getInitFile() == null) {
				user.setInitString(new String(Base64.encodeBase64("".getBytes())));
			}else{
				String initString = new String(Base64.encodeBase64(user.getInitFile().getBytes()));
				user.setInitString(initString);
			}
 			securityDao.addUserInitials(user);
		}
		
	}

	@Override
	public List<Role> getSupplierRoles(String manufacturer, User currentUser) {
		
		if(currentUser.getUserType() == UserType.PENSKE){
			return securityDao.getPenskeUserSupplierRoles(manufacturer);
		} else {
			return securityDao.getVendorUserSpecificRoles( currentUser.getRoleId());
		}
	}

	@Override
	public List<EditableUser> getSearchUserList(UserSearchForm userSearchForm, User currentUser) {
		
		UserSearchForm sqlSearchForm = new UserSearchForm();
		
		StringBuilder emailBuff = new StringBuilder();
		StringBuilder firstNameBuff = new StringBuilder();
		StringBuilder lastNameBuff = new StringBuilder();
		if(userSearchForm.getEmail() != null){
		emailBuff.append(userSearchForm.getEmail().toUpperCase());
		emailBuff.append('%');}
		if(userSearchForm.getFirstName() != null){
		firstNameBuff.append(userSearchForm.getFirstName().toUpperCase());
		firstNameBuff.append('%');
		}
		if(userSearchForm.getLastName() != null){
		lastNameBuff.append(userSearchForm.getLastName().toUpperCase());
		lastNameBuff.append('%');
		}
		sqlSearchForm.setEmail(emailBuff.toString());
		sqlSearchForm.setFirstName(firstNameBuff.toString());
		sqlSearchForm.setLastName(lastNameBuff.toString());
		if(userSearchForm.getRoleId() > 0)
			sqlSearchForm.setRoleId(userSearchForm.getRoleId());
		if(userSearchForm.getUserTypeId() > 0)
			sqlSearchForm.setUserTypeId(userSearchForm.getUserTypeId());
		
		sqlSearchForm.setOrgId(currentUser.getOrgId());
		sqlSearchForm.setOrgIds(userSearchForm.getOrgIds());
		List<EditableUser> userList = securityDao.getUserSearchList(sqlSearchForm);
		List<Role> roleList = securityDao.getPenskeRoles(currentUser.getRoleId());
		
		if(userSearchForm.getUserTypeId() == ApplicationConstants.PENSKE_USER){
			Iterator<EditableUser> userIt = userList.iterator();
			while(userIt.hasNext()){
				EditableUser user = userIt.next();
				user.setDeactivatible(true);
				if(user.getUserType().getUserType().equalsIgnoreCase("Penske")){
					boolean keep = false;
					for(Role role: roleList){
						if(role.getRoleId() ==  user.getRole().getRoleId()){
							keep = true;
							break;
						}
					}
					if(!keep){
						userIt.remove();
					}
				}
			}
		} else if(userSearchForm.getUserTypeId() == ApplicationConstants.SUPPLIER_USER){
			roleList = securityDao.getVendorRoles(currentUser.getRoleId(),currentUser.getOrgId());
			Iterator<EditableUser> userIt = userList.iterator();
			while(userIt.hasNext()){
				EditableUser user = userIt.next();
				boolean keep = false;
				for(Role role: roleList){
					if(role.getRoleId() ==  user.getRole().getRoleId()){
						keep = true;
						break;
					}
				}
				if(!keep){
					userIt.remove();
				}
			}
		}
		return userList;
	}

	@Override
	public List<Role> getPenskeRoles(int roleId) {
		return securityDao.getPenskeRoles(roleId);
	}
	
	@Override
	public List<String> getVendorNames() {
		return securityDao.getAllVendorNames();
	}

	@Override
	public List<AdminConsoleUserDept> getUserDepts() {
		return securityDao.getAllUserDepts();
	}

	@Override
	public List<AdminConsoleUserType> getUserTypes() {
		return  securityDao.getAllUserTypes();
	}
	
	@Override
	public EditableUser getUser(int userId) {
		return securityDao.getUser(userId);
	}
	
	@Override
	public boolean doesUserExist(String userName, int userId) {
		List<String> userNameList = securityDao.getUserName(userName.toUpperCase(), userId);
		return userNameList.size() > 0;
	}
	
	@Override
	public EditableUser doesUserExistVendor(String userName, int userId,boolean isVandorFlow,String isCreateOrEdit) {
		EditableUser rtnUserBean = new EditableUser();
		rtnUserBean.setReturnFlg(-1);
		
		//Short-circuit if the name contains anything but letters and numbers
		if (!userName.matches("[a-zA-z0-9]+")) {
			rtnUserBean.setReturnFlg(4);// username contains invalid characters
			return rtnUserBean;
		}
				
		List<String> userNameList = securityDao.getUserName(userName.toUpperCase(), userId);
		//return userNameList.size() > 0;
		
		if(userNameList.size() > 0){
			//return 0; 
			rtnUserBean.setReturnFlg(0);// User Exit in SMC
			return rtnUserBean;
		}
		CPTSso oSSO = null;
		CPBGESSOUser oUser = null;
		oSSO = new CPTSso();		
		oUser = oSSO.findUser(userName);
		//	logger.info(oB2BUser.getGESSOStatus());
		if(oUser != null){
			populateUserBean(oUser,rtnUserBean);
			rtnUserBean.setSsoId(userName);
			if(oUser.getGESSOStatus() .equalsIgnoreCase("A")){
				//return 1;
				rtnUserBean.setReturnFlg(1);// User Exit 'A' in LDAP and Not in SMC.
			}
			else if(oUser.getGESSOStatus() .equalsIgnoreCase("I")){
				//return 2; 
				rtnUserBean.setReturnFlg(2);// User Exit 'I' in LDAP and Not in SMC.
			}
		}
		return rtnUserBean;
	}
	
	@Override
	public EditableUser doesUserExistInPenske(String userId) throws SMCException
	{
		CPTSso oSSO = null;
		CPBGESSOUser oUser = null;
		EditableUser rtnUserBean = new EditableUser();
		oSSO = new CPTSso();		
		// Querying Penske's LDAP to check whether a userid is present.
		oUser = oSSO.findUser(userId);
		if(oUser == null){
			ErrorModel eModel = new ErrorModel(404, "SSO doesn't Exist");
			throw new SMCException(eModel);
		}
		populateUserBean(oUser,rtnUserBean);
		rtnUserBean.setSsoId(userId);
		return rtnUserBean;
	}

	private EditableUser populateUserBean(CPBGESSOUser oUser,EditableUser rtnUserBean) {
		rtnUserBean.setEmail(oUser.getEmailAddress());
		rtnUserBean.setFirstName(oUser.getGivenName());
		rtnUserBean.setLastName(oUser.getSurName());
		rtnUserBean.setPhoneFromSSOLookup(oUser.getPhone());
		rtnUserBean.setGessouid(oUser.getGESSOUID());
		return rtnUserBean;
	}

	@Override
	public void refreshUserWithSSOData(EditableUser editableUser) {
		securityDao.refreshUserWithSSOData(editableUser);
	}

	@Override
	public boolean deleteInitialsImage(int userId, String ssoId) {
		securityDao.deleteInitialsImage(userId, ssoId);
		return true;
	}

	@Override
	public boolean deleteSignatureImage(int userId,String ssoId) {
		securityDao.deleteSignatureImage(userId,ssoId);
		return true;
	}

	@Override
	public List<Org> getOrgList(Org orgSearch, User currentUser) {
		int orgId = currentUser.getOrgId();
		String orgName = orgSearch == null ? null : orgSearch.getOrgName();
		Integer parentOrgId = orgSearch == null ? null : orgSearch.getParentOrgId();
		List<Org> orgList=securityDao.getOrgList(orgId, orgName, parentOrgId);
		return orgList;
	}

	@Override
	public List<Org> getPenskeUserOrgList() {
		return securityDao.getPenskeUserOrgList();
	}

	@Override
	@Transactional
	public void addOrg(Org org) {
		securityDao.addOrg(org);
		int orgId=org.getOrgId();
		if(org.getVendorStr() !=null && !org.getVendorStr().isEmpty()){
			String [] str=org.getVendorStr().split(",");
			if(str != null && str.length>0){
				for (String vendorNumber : str) {
					securityDao.addOrgVendor(orgId, vendorNumber);
				}
			}
		}
	}

	@Override
	@Transactional
	public void deleteOrg(int orgId, String modifiedBy) {
		if(checkForUsers(orgId)){
			securityDao.modifyRoleStatusByOrgId(orgId, modifiedBy);
			securityDao.removeAllRoleFunctionByOrgId(orgId);
			securityDao.deleteVendorAssocaWithChild(orgId);
			securityDao.deleteOrg(orgId);
		}
	}

	@Override
	public Org getEditOrgInfo(int orgId) {
		return securityDao.getEditOrgInfo(orgId);
	}

	@Override
	@Transactional
	public void updateOrg(Org org) {
		securityDao.updateOrg(org);
		int orgId=org.getOrgId();
		if(org.getVendorStr() !=null && !org.getVendorStr().isEmpty()){
			String [] str=org.getVendorStr().split(",");
			if(str != null && str.length>0){
				for (String vendorNumber : str) {
					securityDao.addOrgVendor(orgId, vendorNumber);
				}
			}
		}
		if(org.getUncheckedVendorStr() !=null && !org.getUncheckedVendorStr().isEmpty()){
			String [] strArray = org.getUncheckedVendorStr().split(",");
			if(strArray != null && strArray.length>0){
				securityDao.deleteOrgVendor(orgId, strArray);
			}
		}
		//remove all the unselected vendors
		securityDao.removeVendorAssocFromDescendent(orgId);
	}

	@Override
	public List<VendorTree> getVendorList(String corp,String vendor,int orgId){
		if(vendor !=null && !vendor.isEmpty()){
			StringBuilder builder=new StringBuilder();
			builder.append("%").append(vendor.toUpperCase()).append("%");
			vendor=builder.toString();
		}
		List<VendorTree> list=securityDao.getVendorList(corp,vendor,orgId);
		return list;
	}

	@Override
	public List<Integer> getOrgVendor(int orgId) {
		return securityDao.getOrgVendor(orgId);
	}
	
	@Override
	public boolean checkForUsers(int orgId) {
		if (securityDao.getUsersByOrgId(orgId) == 0 ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<Org> getOrgChild(int orgId) {
		return securityDao.getOrgChild(orgId);
	}

	@Override
	public List<EditableUser> getVendorUserList(User currentUser) {
		List<EditableUser> userList = securityDao.getVendorUserList(currentUser);
		List<Role> roleList = securityDao.getVendorRoles(currentUser.getRoleId(),currentUser.getOrgId());
		Iterator<EditableUser> userIt = userList.iterator();
		while(userIt.hasNext()){
			EditableUser user = userIt.next();
			boolean keep = false;
			for(Role role: roleList){
				if(role.getRoleId() ==  user.getRole().getRoleId()){
					keep = true;
					break;
				}
			}
			if(!keep){
				userIt.remove();
			}
		}
		return userList;
	}

	@Override
	public List<Role> getVendorRoles(int roleId, int orgId) {
		return securityDao.getVendorRoles(roleId,orgId);
	}

	@Override
	public List<AdminConsoleUserType> getVendorUserTypes() {
		return securityDao.getVendorUserTypes();
	}

	@Override
	public List<Org> getVendorOrg(boolean isVendor, int orgId) {
		return securityDao.getVendorOrg(isVendor, orgId);
	}

	@Override
	public List<Org> removeCurrentOrgAndChild(int orgId, List<Org> orgs) {
		List<Org> myOrgs=securityDao.getMyOrgAndChild(orgId);
		List<Integer> loopCnt=new ArrayList<Integer>();
		for (Iterator<Org> iterator = orgs.iterator();iterator.hasNext();) {
			int tempOrgId=iterator.next().getOrgId();
			for (Org org : myOrgs) {
				if(tempOrgId==org.getOrgId()){
					iterator.remove();
					loopCnt.add(1);
				}
			}
			if(loopCnt.size()==myOrgs.size()){
				break;
			}
		}
		return orgs;
	}
	
	@Override
	public List<VendorUser> getVendorUsers(UserSearchForm userSearchForm, User user) {
		List<EditableUser> vendorUsers = this.getSearchUserList(userSearchForm, user);
		
		List<VendorUser> result = new ArrayList<>();
		for(EditableUser vendorUser: vendorUsers)
			result.add(new VendorUser(vendorUser));
		
		return result;
	}
}