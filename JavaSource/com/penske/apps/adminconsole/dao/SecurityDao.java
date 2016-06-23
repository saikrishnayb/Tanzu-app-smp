package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
import com.penske.apps.adminconsole.service.MailRequest;

/**
 * dao methods for security-mapper.
 * 
 * @author kenneth.french
 *
 */



public interface SecurityDao {

	public User getPenskeUserInfo(int userId);
	public User getVendorUserInfo(@Param("userId") int userId);
	
//user form content
	public User getUser(int userId);
	public Role getRole(int roleId);
	//vendor
	public List<String> getAllVendorNames();
	public List<Role> getPenskeUserSupplierRoles(String manufacturer);
	public List<Role> getSupplierAdminRole();
	public List<Role> getSupplierRoles(@Param("roleId")int roleId);
	public List<Permission> getPermissions(int roleId);
	public List<String> getAllTabNames();
	public List<VendorLocation> getVendorLocations(String vendorName);
	public List<VendorLocation> getVendorUserLocations(int userId);
	public VendorLocation getVendorUserLocationInfo(int vendorId);
	public String getRegularComponentName(int componentId);
	public String getVehicleComponentName(int componentId);
	public List<TemplateComponents> getTemplateComponent(TemplatePoCategorySubCategory temp);
	public List<TemplatePoCategorySubCategory> getVendorTemplates(int vendorId);

	//penske
	public List<UserDept> getAllUserDepts();
	public List<UserType> getAllUserTypes();
	public List<Role> getPenskeRoles(int roleId);
	public String getInitialsImage(int userId);
	public String getSignatureImage(int userId);

	//user summary page
	//public List<User> getSupplierUsers(String manufacturer);
	public List<Role> getVendorUserSpecificRoles( @Param("roleId")int roleId);
	public List<Role> getAllUserRoles();
	public List<User> getUserSearchList(HeaderUser userSearchForm);
	
	//create user
	public List<String> getUserName(@Param("userName")String userName, @Param("userId")int userId);
	public int getNewUserId();
	
	//table modifications
	public void addVendorUserAssoc(int userId, int vendorId);
	public void removeVendorUserAssoc(@Param("userId")int userId, @Param("vendorId")int vendorId);
	public void modifyUserInfo(User user);
	public void modifyPenskeUser(User user);
	public void addPenskeUser(User user);
	public void removePenskeUserAssoc(int userId);
	public boolean addUser(User user);
	public void modifyUserStatus(@Param("userId")int userId,  @Param("modifiedBy")String modifiedBy);
	public void addUserInitials(User user);
	public void refreshUserWithSSOData(User editableUser);
	public void deleteInitialsImage(@Param("userId")int userId,@Param("ssoId")String ssoId);
	public void deleteSignatureImage(@Param("userId")int userId,@Param("ssoId")String ssoId);
	public List<String> getAllVendorFullNames();
	public List<Org> getOrgList(HeaderUser currentUser);
	public List<Org> getPenskeUserOrgList(HeaderUser currentUser);
	public void addOrg(Org org);
	public void deleteOrg(int orgId);
	public Org getEditOrgInfo(int orgId);
	public void updateOrg(Org org);
	public List<VendorTree> getVendorList(@Param("corp") String corp,@Param("vendor") String vendor,@Param("orgId") int orgId);
	public int getOrgId();
	public void addOrgVendor(@Param("orgId")int orgId,@Param("vendorNumber")String vendorNumber);
	public int deleteVendorAssocaWithChild(@Param("orgId")int orgId);
	public int deleteVendorAssoc(@Param("orgId")int orgId);
	public List<Integer> getOrgVendor(@Param("orgId")int orgId);
	public List<Org> getPenskeUserOrgSearch(HeaderUser currentUser);
	public SignatureInitial getSignatureInitialByUserId(int userId);
	public void addBuddies(User user);
	public UserDept getUserDeptsById(@Param("deptId") int deptId);
	public void updateBuddies(User user);
	public Integer getUsersByOrgId(@Param("orgId")int orgId);
	public List<Org> getOrgChild(@Param("orgId")int orgId);
	//Modify Role status to 'I' when Org is Deactivate
	public void removeAllRoleFunctionByOrgId(@Param("orgId") int roleId);
	public void modifyRoleStatusByOrgId( @Param("orgId")int roleId,  @Param("modifiedBy")String modifiedBy);
	
	//Vendor User Change - 03/02/16
	public List<User> getVendorUserList(HeaderUser currentUser);
	public List<Role> getVendorRoles(@Param("isVendor") boolean isVendor,@Param("roleId") int roleId,@Param("orgId") int orgId);
	public List<UserType> getVendorUserTypes();
	public List<Org> getVendorOrg(@Param("isVendor") boolean isVendor,@Param("orgId") int orgId);
	public List<Org> getAllVendorOrg(HeaderUser currentUser);
	public void addEmailSent(MailRequest emailData);
	public void updateEmailSent(@Param("emailAuditId") int emailAuditId);
	public void deleteUserFromBuddy(@Param("userSSO") String userSSO);
	public List<Org> getMyOrgAndChild(@Param("orgId")int orgId);
	public int removeVendorAssocFromDescendent(@Param("orgId")int orgId);
}
