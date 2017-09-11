package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
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

	@NonVendorQuery //TODO: Review Query
	public User getPenskeUserInfo(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public User getVendorUserInfo(@Param("userId") int userId);
	
	//user form content
	@NonVendorQuery //TODO: Review Query
	public User getUser(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public Role getRole(int roleId);
	
	//vendor
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllVendorNames();
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getPenskeUserSupplierRoles(String manufacturer);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getSupplierAdminRole();
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getSupplierRoles(@Param("roleId")int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Permission> getPermissions(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllTabNames();
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorLocation> getVendorLocations(String vendorName);
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorLocation> getVendorUserLocations(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public VendorLocation getVendorUserLocationInfo(int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public String getRegularComponentName(int componentId);
	
	@NonVendorQuery //TODO: Review Query
	public String getVehicleComponentName(int componentId);
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplateComponents> getTemplateComponent(TemplatePoCategorySubCategory temp);
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplatePoCategorySubCategory> getVendorTemplates(int vendorId);

	//penske
	@NonVendorQuery //TODO: Review Query
	public List<UserDept> getAllUserDepts();
	
	@NonVendorQuery //TODO: Review Query
	public List<UserType> getAllUserTypes();
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getPenskeRoles(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public String getInitialsImage(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public String getSignatureImage(int userId);

	//user summary page
	//public List<User> getSupplierUsers(String manufacturer);
	@NonVendorQuery //TODO: Review Query
	public List<Role> getVendorUserSpecificRoles( @Param("roleId")int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getAllUserRoles();
	
	@NonVendorQuery //TODO: Review Query
	public List<User> getUserSearchList(HeaderUser userSearchForm);
	
	//create user
	@NonVendorQuery //TODO: Review Query
	public List<String> getUserName(@Param("userName")String userName, @Param("userId")int userId);
	
	@NonVendorQuery //TODO: Review Query
	public int getNewUserId();
	
	//table modifications
	@NonVendorQuery //TODO: Review Query
	public void addVendorUserAssoc(int userId, int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public void removeVendorUserAssoc(@Param("userId")int userId, @Param("vendorId")int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyUserInfo(User user);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyPenskeUser(User user);
	
	@NonVendorQuery //TODO: Review Query
	public void addPenskeUser(User user);
	
	@NonVendorQuery //TODO: Review Query
	public void removePenskeUserAssoc(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public boolean addUser(User user);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyUserStatus(@Param("userId")int userId,  @Param("modifiedBy")String modifiedBy);
	
	@NonVendorQuery //TODO: Review Query
	public void addUserInitials(User user);
	
	@NonVendorQuery //TODO: Review Query
	public void refreshUserWithSSOData(User editableUser);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteInitialsImage(@Param("userId")int userId,@Param("ssoId")String ssoId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteSignatureImage(@Param("userId")int userId,@Param("ssoId")String ssoId);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllVendorFullNames();
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getOrgList(HeaderUser currentUser);
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getPenskeUserOrgList(HeaderUser currentUser);
	
	@NonVendorQuery //TODO: Review Query
	public void addOrg(Org org);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteOrg(int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public Org getEditOrgInfo(int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateOrg(Org org);
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorTree> getVendorList(@Param("corp") String corp,@Param("vendor") String vendor,@Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public int getOrgId();
	
	@NonVendorQuery //TODO: Review Query
	public void addOrgVendor(@Param("orgId")int orgId,@Param("vendorNumber")String vendorNumber);
	
	@NonVendorQuery //TODO: Review Query
	public int deleteVendorAssocaWithChild(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public int deleteVendorAssoc(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Integer> getOrgVendor(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getPenskeUserOrgSearch(HeaderUser currentUser);
	
	@NonVendorQuery //TODO: Review Query
	public SignatureInitial getSignatureInitialByUserId(int userId);
	
	@NonVendorQuery //TODO: Review Query
	public void addBuddies(User user);
	
	@NonVendorQuery //TODO: Review Query
	public UserDept getUserDeptsById(@Param("deptId") int deptId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateBuddies(User user);
	
	@NonVendorQuery //TODO: Review Query
	public Integer getUsersByOrgId(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getOrgChild(@Param("orgId")int orgId);
	//Modify Role status to 'I' when Org is Deactivate
	@NonVendorQuery //TODO: Review Query
	public void removeAllRoleFunctionByOrgId(@Param("orgId") int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyRoleStatusByOrgId( @Param("orgId")int roleId,  @Param("modifiedBy")String modifiedBy);
	
	//Vendor User Change - 03/02/16
	@NonVendorQuery //TODO: Review Query
	public List<User> getVendorUserList(HeaderUser currentUser);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getVendorRoles(@Param("isVendor") boolean isVendor,@Param("roleId") int roleId,@Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<UserType> getVendorUserTypes();
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getVendorOrg(@Param("isVendor") boolean isVendor,@Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getAllVendorOrg(HeaderUser currentUser);
	
	@NonVendorQuery //TODO: Review Query
	public void addEmailSent(MailRequest emailData);
	
	@NonVendorQuery //TODO: Review Query
	public void updateEmailSent(@Param("emailAuditId") int emailAuditId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteUserFromBuddy(@Param("userSSO") String userSSO);
	
	@NonVendorQuery //TODO: Review Query
	public List<Org> getMyOrgAndChild(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public int removeVendorAssocFromDescendent(@Param("orgId")int orgId);
}
