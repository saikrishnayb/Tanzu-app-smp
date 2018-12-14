package com.penske.apps.adminconsole.service;

import java.util.HashMap;
import java.util.List;

import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.UserDept;
import com.penske.apps.adminconsole.model.UserType;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.suppliermgmt.exception.SMCException;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface SecurityService {
	public User getUser(int userId);
	public User getEditInfo(String userId, String userType);
	public List<String> getVendorNames();
	public List<UserDept> getUserDepts();
	public List<Role> getUserRoles(int roleId);
	public List<UserType> getUserTypes();
	public void modifyUserStatus(int userId, UserContext currentUser);
	public HashMap<String, List<Permission>> getPermissions(String roleId);
	public void modifyUserInfo(User user, int[] vendorIds, UserContext currentUser);
	public String getSignatureImage(int userId);
	public String getInitialsImage(int userId);
	public void addUser(User user, int[] vendorIds, UserContext currentUser);
	public List<Role> getPenskeRoles(int roleId);
	public List<Role> getSupplierRoles(String manufacturer, UserContext currentUser);
	public List<User> getSearchUserList(HeaderUser userSearchForm, UserContext currentUser);
	public boolean doesUserExist(String userName, int userId);
	public User doesUserExistInPenske(String ssoId) throws SMCException;
	public void refreshUserWithSSOData(User editableUser);
	public boolean deleteInitialsImage(int userId, String ssoId);
	public boolean deleteSignatureImage(int userId, String ssoId);
	public List<Org> getOrgList(Org orgSearch, UserContext currentUser);
	public  List<Org> getPenskeUserOrgList();
	public void addOrg(Org org);
	public void deleteOrg(int orgId, String modifiedBy);
	public Org getEditOrgInfo(int orgId);
	public void updateOrg(Org org);
	public List<VendorTree> getVendorList(String corp,String vendor,int orgId);
	public List<Integer> getOrgVendor(int orgId);
	public boolean checkForUsers(int orgId);
	public List<Org> getOrgChild(int orgId);
	
	//Vendor User Change - 03/02/16
	public List<User> getVendorUserList(UserContext currentUser);
	public List<Role> getVendorRoles(boolean isVendor, int roleId,int orgId);
	public List<UserType> getVendorUserTypes();
	public List<Org> getVendorOrg(boolean isVendor,int orgId);
	public User doesUserExistVendor(String userName, int userId,boolean isVandorFlow,String isCreateOrEdit);
	public List<Org>  removeCurrentOrgAndChild(int orgId,List<Org> orgs);
}
