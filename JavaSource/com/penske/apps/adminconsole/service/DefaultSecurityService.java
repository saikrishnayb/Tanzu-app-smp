package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.SecurityDao;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.SignatureInitial;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.UserDept;
import com.penske.apps.adminconsole.model.UserType;
import com.penske.apps.adminconsole.model.VendorLocation;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.exception.SMCException;
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
	private SecurityDao securityDao;

	@Override
	public User getEditInfo(String userId, String userType) {
		boolean isPenskeUser = "Penske".equalsIgnoreCase(userType);
		int userIdNo = Integer.parseInt(userId);

		User user = new User();

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
	public void modifyUserStatus(int userId, HeaderUser currentUser) {
		
		String userSSO="";
		if(currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER){
		List<VendorLocation> currentUserVendorLoc = securityDao.getVendorUserLocations(currentUser.getUserId());
		List<VendorLocation> searchUserVendorLoc = securityDao.getVendorUserLocations(userId);
		boolean deactivatible = true;
		for(VendorLocation vendLocTwo: searchUserVendorLoc){
			boolean match = false;
			for(VendorLocation vendLocOne: currentUserVendorLoc){
				if(vendLocOne.getVendorId() == vendLocTwo.getVendorId()){
					match = true;
					break;
				}
			}
			if(!match){
				deactivatible = false;
				break;
			}
		}
		if(!deactivatible)
			return;
		} else if(currentUser.getUserTypeId() == ApplicationConstants.PENSKE_USER){
			//List<Role> roleList = getUserRoles(currentUser.getRoleId());
			List<Role> myRoleList = securityDao.getPenskeRoles(currentUser.getRoleId());
			User user = securityDao.getUser(userId);
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
	public List<VendorLocation> getVendorLocations(String vendorName, int userId, int userTypeId) {
		
		List<VendorLocation> vendorLocations = securityDao.getVendorLocations(vendorName);
		
		boolean isSupplierUser = userTypeId == ApplicationConstants.SUPPLIER_USER;
		
		if(isSupplierUser){
			List<VendorLocation> currentUserVendLocs = securityDao.getVendorUserLocations(userId);
			
			for(VendorLocation vendLoc: vendorLocations){
				for(VendorLocation vendLocTest: currentUserVendLocs){
					if(vendLoc.getVendorId() == vendLocTest.getVendorId()){
						vendLoc.setCheckable(false);
						break;
					} else{
						vendLoc.setCheckable(true);
					}
				}
			}
		}
		return vendorLocations;
	}

	@Override
	public void modifyUserInfo(User user, int[] vendorIds, HeaderUser currentUser) {
		//check edit information
		boolean validUser = !user.validateUserInfo();
		
		if(validUser)
			return;
		
		int userId = user.getUserId();
		int userTypeId = user.getUserType().getUserTypeId();
		
		//check that current user has permission to edit
		//List<Role> roleList = getUserRoles(currentUser.getRoleId());
		//User userInfo = securityDao.getUser(user.getUserId());
		/*boolean editable = false;
		for(Role role: roleList){
			if(role.getRoleId() == userInfo.getRole().getRoleId())
				editable = true;
		}
		if(!editable)
			return;
		*/
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
			if (currentUser.getUserTypeId() == ApplicationConstants.SUPPLIER_USER) {
				
			} else if (currentUser.getUserTypeId() == ApplicationConstants.PENSKE_USER) {
				List<VendorLocation> editUserOld = securityDao.getVendorUserLocations(userId);
				for (VendorLocation vendLoc : editUserOld) {
					boolean chuckIt = true;
					for (int vendId : vendorIds) {
						if (vendId == vendLoc.getVendorId()) {
							chuckIt = false;
							break;
						}
					}
					if (chuckIt) {
						securityDao.removeVendorUserAssoc(userId, vendLoc.getVendorId());
					}
				}
				/*for (int vendId : vendorIds) {
					boolean addIt = true;
					for (VendorLocation vendLoc : editUserOld) {
						if (vendId == vendLoc.getVendorId()) {
							addIt = false;
							break;
						}
					}
					if (addIt) {
						securityDao.addVendorUserAssoc(userId, vendId);
					}
				}*/
			}
		}
		securityDao.modifyUserInfo(user);
		if(user.getUserDept() !=null){
			UserDept depart=securityDao.getUserDeptsById(user.getUserDept().getUserDeptId());
			if(depart !=null){
				user.getUserDept().setUserDept(depart.getUserDept());
				securityDao.updateBuddies(user);
			}
		}
	}

	public List<VendorLocation> getVendorTemplates(List<VendorLocation> vendorLocations) {
		
		List<VendorLocation> vendorLocationList = new ArrayList<VendorLocation>();
		
		for(VendorLocation location: vendorLocations){
			
			VendorLocation vendLoc = securityDao.getVendorUserLocationInfo(location.getVendorId());
			if(vendLoc == null || vendLoc.getTemplateId() == 0)
				continue;
			
			List<TemplatePoCategorySubCategory> vendorTemplateList = securityDao.getVendorTemplates(vendLoc.getTemplateId());
			
			for(TemplatePoCategorySubCategory temp: vendorTemplateList){
				List<TemplateComponents> componentList = securityDao.getTemplateComponent(temp);
				for(TemplateComponents component: componentList){
					boolean isVehicleComponent = "smc_vehicle_info".equalsIgnoreCase(component.getDataType());
					if(isVehicleComponent){
						component.setComponentName(securityDao.getVehicleComponentName(component.getComponentId()));
					} else {
						component.setComponentName(securityDao.getRegularComponentName(component.getComponentId()));
					}
				}
				if(componentList.size() > 0)
					temp.setTemplateComponents(componentList);
			}
			if(vendorTemplateList.size() > 0){
				vendLoc.setTemplatePoCategorySubCategory(vendorTemplateList);
				vendorLocationList.add(vendLoc);
			}
		}
		if(vendorLocationList.size() > 0)
			return vendorLocationList;
		else
			return null;
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
	public void addUser(User user, int[] vendorIds, HeaderUser currentUser) {
		user.setCreatedBy(currentUser.getSso());
		//user.setGessouid(user.getSsoId()); // If it is pensker
		securityDao.addUser(user);
		user.setUserId(securityDao.getNewUserId());
		if(user.getUserDept() !=null){
			UserDept depart=securityDao.getUserDeptsById(user.getUserDept().getUserDeptId());
			if(depart !=null){
				user.getUserDept().setUserDept(depart.getUserDept());
				securityDao.addBuddies(user);
			}
		}
	//	boolean isPenskeUser = (ApplicationConstants.PENSKE_USER == user.getUserType().getUserTypeId());
		boolean isSupplierUser = (ApplicationConstants.SUPPLIER_USER == user.getUserType().getUserTypeId());
		//updated for adding comments to the base64 changes
		if(isSupplierUser){
			//for(int vendId: vendorIds){
			//	securityDao.addVendorUserAssoc(user.getUserId(), vendId);
			//}
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
	public List<Role> getSupplierRoles(String manufacturer, HeaderUser currentUser) {
		
		if(currentUser.getUserTypeId() == ApplicationConstants.PENSKE_USER){
			return securityDao.getPenskeUserSupplierRoles(manufacturer);
		} else {
			return securityDao.getVendorUserSpecificRoles( currentUser.getRoleId());
		}
	}

	/*@Override
	public List<User> getSupplierUsers(HeaderUser currentUser) {
		
		List<User> userList = securityDao.getSupplierUsers(currentUser.getManufacturer());
		List<Role> roleList = securityDao.getSupplierRoles(currentUser.getRoleId());
		
		Iterator<User> userIt = userList.iterator();
		
		while(userIt.hasNext()){
			User user = userIt.next();
			boolean keep = false;
			for(Role role: roleList){
				if(role.getRoleId() ==  user.getRole().getRoleId()){
					keep = true;
				}
			}
			if(!keep){
				userIt.remove();
			}
		}
		List<VendorLocation> currentUserVendorLoc = securityDao.getVendorUserLocations(currentUser.getUserId());
		for(User user: userList){
			user.setDeactivatible(true);
			List<VendorLocation> searchUserVendorLoc = securityDao.getVendorUserLocations(user.getUserId());
			for(VendorLocation vendLocTwo: searchUserVendorLoc){
				boolean match = false;
				for(VendorLocation vendLocOne: currentUserVendorLoc){
					if(vendLocOne.getVendorId() == vendLocTwo.getVendorId()){
						match = true;
						break;
					}
				}
				if(!match){
					user.setDeactivatible(false);
				}
			}
		}
		return userList;
	}*/

	@Override
	public List<User> getSearchUserList(HeaderUser userSearchForm, HeaderUser currentUser) {
		
		HeaderUser sqlSearchForm = new HeaderUser();
		
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
		List<User> userList = securityDao.getUserSearchList(sqlSearchForm);
		List<Role> roleList = securityDao.getPenskeRoles(currentUser.getRoleId());
		
		if(userSearchForm.getUserTypeId() == ApplicationConstants.PENSKE_USER){
			Iterator<User> userIt = userList.iterator();
			while(userIt.hasNext()){
				User user = userIt.next();
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
			roleList = securityDao.getVendorRoles(true,currentUser.getRoleId(),currentUser.getOrgId());
			Iterator<User> userIt = userList.iterator();
			while(userIt.hasNext()){
				User user = userIt.next();
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
			/*	List<VendorLocation> currentUserVendorLoc = securityDao.getVendorUserLocations(currentUser.getUserId());
			for(User user: userList){
				user.setDeactivatible(true);
				List<VendorLocation> searchUserVendorLoc = securityDao.getVendorUserLocations(user.getUserId());
				for(VendorLocation vendLocTwo: searchUserVendorLoc){
					boolean match = false;
					for(VendorLocation vendLocOne: currentUserVendorLoc){
						if(vendLocOne.getVendorId() == vendLocTwo.getVendorId()){
							match = true;
							break;
						}
					}
					if(!match){
						user.setDeactivatible(false);
					}
				}
			}
			
		*/}
		return userList;
	}

	@Override
	public List<Role> getVendorUserSpecificRoles(String manufacturer, int roleId) {
		return securityDao.getVendorUserSpecificRoles( roleId);
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
	public List<UserDept> getUserDepts() {
		return securityDao.getAllUserDepts();
	}

	@Override
	public List<UserType> getUserTypes() {
		return  securityDao.getAllUserTypes();
	}
	
	@Override
	public User getUser(int userId) {
		return securityDao.getUser(userId);
	}
	
	@Override
	public boolean doesUserExist(String userName, int userId) {
		List<String> userNameList = securityDao.getUserName(userName.toUpperCase(), userId);
		return userNameList.size() > 0;
	}
	
	@Override
	public User doesUserExistVendor(String userName, int userId,boolean isVandorFlow,String isCreateOrEdit) {
		User rtnUserBean = new User();
		rtnUserBean.setReturnFlg(-1);
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
	public List<Role> getSupplierAdminRole() {
		return securityDao.getSupplierAdminRole();
	}
	
	@Override
	public User doesUserExistInPenske(String userId) throws SMCException
	{
		CPTSso oSSO = null;
		CPBGESSOUser oUser = null;
		User rtnUserBean = new User();
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

	private User populateUserBean(CPBGESSOUser oUser,User rtnUserBean) {
		// TODO Auto-generated method stub
		rtnUserBean.setEmail(oUser.getEmailAddress());
		rtnUserBean.setFirstName(oUser.getGivenName());
		rtnUserBean.setLastName(oUser.getSurName());
		rtnUserBean.setPhoneFromSSOLookup(oUser.getPhone());
		rtnUserBean.setGessouid(oUser.getGESSOUID());
		return rtnUserBean;
	}

	@Override
	public void refreshUserWithSSOData(User editableUser) {
		// TODO Auto-generated method stub
		securityDao.refreshUserWithSSOData(editableUser);
	}

	@Override
	public boolean deleteInitialsImage(int userId, String ssoId) {
		// TODO Auto-generated method stub
		securityDao.deleteInitialsImage(userId, ssoId);
		return true;
	}

	@Override
	public boolean deleteSignatureImage(int userId,String ssoId) {
		// TODO Auto-generated method stub
		
		securityDao.deleteSignatureImage(userId,ssoId);
		return true;
	}

	@Override
	public List<String> getAllVendorFullNames() {
		// TODO Auto-generated method stub
		return securityDao.getAllVendorFullNames();
	}

	@Override
	public List<Org> getOrgList(HeaderUser currentUser) {
		List<Org> orgList=securityDao.getOrgList(currentUser);
	/*	if(currentUser.getUserTypeId() != ApplicationConstants.SUPPLIER_USER){
			orgList.addAll(securityDao.getAllVendorOrg(currentUser));
		}
	*/
		return orgList;
	}

	@Override
	public List<Org> getPenskeUserOrgList(HeaderUser currentUser) {
		// TODO Auto-generated method stub
		return securityDao.getPenskeUserOrgList(currentUser);
	}
	
	@Override
	public List<Org> getPenskeUserOrgSearch(HeaderUser currentUser) {
		// TODO Auto-generated method stub
		return securityDao.getPenskeUserOrgSearch(currentUser);
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
		// TODO Auto-generated method stub
		return securityDao.getEditOrgInfo(orgId);
	}

	@Override
	@Transactional
	public void updateOrg(Org org) {
		securityDao.updateOrg(org);
		int orgId=org.getOrgId();
		securityDao.deleteVendorAssoc(orgId);
		if(org.getVendorStr() !=null && !org.getVendorStr().isEmpty()){
			String [] str=org.getVendorStr().split(",");
			if(str != null && str.length>0){
				for (String vendorNumber : str) {
					securityDao.addOrgVendor(orgId, vendorNumber);
				}
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
	public List<User> getVendorUserList(HeaderUser currentUser) {
		List<User> userList=securityDao.getVendorUserList(currentUser);
	//	if(currentUser.getUserTypeId()==ApplicationConstants.SUPPLIER_USER){
			List<Role> roleList = securityDao.getVendorRoles(true,currentUser.getRoleId(),currentUser.getOrgId());
			Iterator<User> userIt = userList.iterator();
			while(userIt.hasNext()){
				User user = userIt.next();
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
	//	}
		return userList;
	}

	@Override
	public List<Role> getVendorRoles(boolean isVendor, int roleId, int orgId) {
		return securityDao.getVendorRoles(isVendor,roleId,orgId);
	}

	@Override
	public List<UserType> getVendorUserTypes() {
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
}
