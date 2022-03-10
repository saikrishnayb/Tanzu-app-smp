package com.penske.apps.adminconsole.service;

import java.util.HashMap;
import java.util.List;

import com.penske.apps.adminconsole.domain.VendorUser;
import com.penske.apps.adminconsole.model.AdminConsoleUserDept;
import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.exception.SMCException;

public interface SecurityService {
	public EditableUser getUser(int userId);
	public EditableUser getEditInfo(String userId, String userType);
	public List<String> getVendorNames();
	public List<AdminConsoleUserDept> getUserDepts();
	public List<Role> getUserRoles(int roleId);
	public List<AdminConsoleUserType> getUserTypes();
	public void modifyUserStatus(int userId, User currentUser);
	public HashMap<String, List<Permission>> getPermissions(String roleId);
	public void modifyUserInfo(EditableUser user, int[] vendorIds, User currentUser);
	public String getSignatureImage(int userId);
	public String getInitialsImage(int userId);
	public void addUser(EditableUser user, int[] vendorIds, User currentUser);
	public List<Role> getPenskeRoles(int roleId);
	public List<Role> getSupplierRoles(String manufacturer, User currentUser);
	public List<EditableUser> getSearchUserList(UserSearchForm userSearchForm, User currentUser);
	public boolean doesUserExist(String userName, int userId);
	public EditableUser doesUserExistInPenske(String ssoId) throws SMCException;
	public void refreshUserWithSSOData(EditableUser editableUser);
	public boolean deleteInitialsImage(int userId, String ssoId);
	public boolean deleteSignatureImage(int userId, String ssoId);
	public List<Org> getOrgList(Org orgSearch, User currentUser);
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
	public List<EditableUser> getVendorUserList(User currentUser);
	public List<Role> getVendorRoles(int roleId, int orgId);
	public List<AdminConsoleUserType> getVendorUserTypes();
	public List<Org> getVendorOrg(boolean isVendor,int orgId);
	public EditableUser doesUserExistVendor(String userName, int userId,boolean isVandorFlow,String isCreateOrEdit);
	public List<Org>  removeCurrentOrgAndChild(int orgId,List<Org> orgs);
	
	public List<VendorUser> getVendorUsers(UserSearchForm userSearchForm, User user);
}
