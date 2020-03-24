package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.SignatureInitial;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.UserDept;
import com.penske.apps.adminconsole.model.UserType;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.adminconsole.service.MailRequest;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * dao methods for security-mapper.
 * 
 * @author kenneth.french
 *
 */
@DBSmc
public interface SecurityDao {

    @NonVendorQuery
    public User getPenskeUserInfo(int userId);

    @NonVendorQuery
    public User getVendorUserInfo(@Param("userId") int userId);

    //user form content
    @NonVendorQuery
    public User getUser(int userId);

    //vendor
    @NonVendorQuery
    public List<String> getAllVendorNames();

    @NonVendorQuery
    public List<Role> getPenskeUserSupplierRoles(String manufacturer);

    @NonVendorQuery
    public List<Permission> getPermissions(int roleId);

    @NonVendorQuery
    public List<String> getAllTabNames();

    //penske
    @NonVendorQuery
    public List<UserDept> getAllUserDepts();

    @NonVendorQuery
    public List<UserType> getAllUserTypes();

    @NonVendorQuery
    public List<Role> getPenskeRoles(int roleId);

    @NonVendorQuery
    public String getInitialsImage(int userId);

    @NonVendorQuery
    public String getSignatureImage(int userId);

    //user summary page
    @NonVendorQuery
    public List<Role> getVendorUserSpecificRoles( @Param("roleId")int roleId);

    @NonVendorQuery
    public List<Role> getAllUserRoles();

    @NonVendorQuery
    public List<User> getUserSearchList(HeaderUser userSearchForm);

    //create user
    @NonVendorQuery
    public List<String> getUserName(@Param("userName")String userName, @Param("userId")int userId);

    @NonVendorQuery
    public int getNewUserId();

    //table modifications
    @NonVendorQuery
    public void modifyUserInfo(User user);

    @NonVendorQuery
    public void modifyPenskeUser(User user);

    @NonVendorQuery
    public boolean addUser(User user);

    @NonVendorQuery
    public void modifyUserStatus(@Param("userId")int userId,  @Param("modifiedBy")String modifiedBy);

    @NonVendorQuery
    public void addUserInitials(User user);

    @NonVendorQuery
    public void refreshUserWithSSOData(User editableUser);

    @NonVendorQuery
    public void deleteInitialsImage(@Param("userId")int userId,@Param("ssoId")String ssoId);

    @NonVendorQuery
    public void deleteSignatureImage(@Param("userId")int userId,@Param("ssoId")String ssoId);

    @NonVendorQuery
    public List<Org> getOrgList(@Param("orgId") int orgId, @Param("orgName") String orgName, @Param("parentOrgId") Integer parentOrgId);

    @NonVendorQuery
    public List<Org> getPenskeUserOrgList();

    @NonVendorQuery
    public void addOrg(Org org);

    @NonVendorQuery
    public void deleteOrg(int orgId);

    @NonVendorQuery
    public Org getEditOrgInfo(int orgId);

    @NonVendorQuery
    public void updateOrg(Org org);

    public List<VendorTree> getVendorList(@Param("corp") String corp,@Param("vendor") String vendor,@Param("orgId") int orgId);

    @NonVendorQuery
    public void addOrgVendor(@Param("orgId")int orgId,@Param("vendorNumber")String vendorNumber);

    @NonVendorQuery
    public int deleteVendorAssocaWithChild(@Param("orgId")int orgId);

    @NonVendorQuery
    public int deleteVendorAssoc(@Param("orgId")int orgId);

    public List<Integer> getOrgVendor(@Param("orgId")int orgId);

    @NonVendorQuery
    public SignatureInitial getSignatureInitialByUserId(int userId);

    @NonVendorQuery
    public void addBuddies(User user);

    @NonVendorQuery
    public UserDept getUserDeptsById(@Param("deptId") int deptId);

    @NonVendorQuery
    public void updateBuddies(User user);

    @NonVendorQuery
    public Integer getUsersByOrgId(@Param("orgId")int orgId);

    @NonVendorQuery
    public List<Org> getOrgChild(@Param("orgId")int orgId);
    //Modify Role status to 'I' when Org is Deactivate
    @NonVendorQuery
    public void removeAllRoleFunctionByOrgId(@Param("orgId") int roleId);

    @NonVendorQuery
    public void modifyRoleStatusByOrgId( @Param("orgId")int roleId,  @Param("modifiedBy")String modifiedBy);

    //Vendor User Change - 03/02/16
    @NonVendorQuery
    public List<User> getVendorUserList(UserContext currentUser);

    @NonVendorQuery
    public List<Role> getVendorRoles(@Param("isVendor") boolean isVendor,@Param("roleId") int roleId,@Param("orgId") int orgId);

    @NonVendorQuery
    public List<UserType> getVendorUserTypes();

    @NonVendorQuery
    public List<Org> getVendorOrg(@Param("isVendor") boolean isVendor,@Param("orgId") int orgId);

    @NonVendorQuery
    public void addEmailSent(MailRequest emailData);

    @NonVendorQuery
    public void deleteUserFromBuddy(@Param("userSSO") String userSSO);

    @NonVendorQuery
    public List<Org> getMyOrgAndChild(@Param("orgId")int orgId);

    @NonVendorQuery
    public int removeVendorAssocFromDescendent(@Param("orgId")int orgId);
}