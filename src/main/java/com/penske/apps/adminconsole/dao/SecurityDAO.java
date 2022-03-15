package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.AdminConsoleUserDept;
import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.SignatureInitial;
import com.penske.apps.adminconsole.model.UserSearchForm;
import com.penske.apps.adminconsole.model.VendorTree;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * dao methods for security-mapper.
 * 
 * @author kenneth.french
 *
 */
@DBSmc
public interface SecurityDAO {

    @NonVendorQuery
    public EditableUser getPenskeUserInfo(int userId);

    @NonVendorQuery
    public EditableUser getVendorUserInfo(@Param("userId") int userId);

    //user form content
    @NonVendorQuery
    public EditableUser getUser(int userId);

    //vendor
    @NonVendorQuery
    public List<String> getAllVendorNames();

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<Role> getPenskeUserSupplierRoles(String manufacturer);

    @NonVendorQuery
    public List<Permission> getPermissions(int roleId);

    @NonVendorQuery
    public List<String> getAllTabNames();

    //penske
    @NonVendorQuery
    public List<AdminConsoleUserDept> getAllUserDepts();

    @NonVendorQuery
    public List<AdminConsoleUserType> getAllUserTypes();

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<Role> getPenskeRoles(int roleId);

    @NonVendorQuery
    public String getInitialsImage(int userId);

    @NonVendorQuery
    public String getSignatureImage(int userId);

    //user summary page
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<Role> getVendorUserSpecificRoles( @Param("roleId")int roleId);

    @SkipQueryTest("Uses heirarchical queries, which HSQLDB does not support")
    @NonVendorQuery
    public List<Role> getAllUserRoles();

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<EditableUser> getUserSearchList(UserSearchForm userSearchForm);

    //create user
    @NonVendorQuery
    public List<String> getUserName(@Param("userName")String userName, @Param("userId")int userId);

    //table modifications
    @NonVendorQuery
    public void modifyUserInfo(EditableUser user);

    @NonVendorQuery
    public void modifyPenskeUser(EditableUser user);

    @NonVendorQuery
    public void addUser(EditableUser user);

    @NonVendorQuery
    public void modifyUserStatus(@Param("userId")int userId,  @Param("modifiedBy")String modifiedBy);

    @NonVendorQuery
    public void addUserInitials(EditableUser user);

    @NonVendorQuery
    public void refreshUserWithSSOData(EditableUser editableUser);

    @NonVendorQuery
    public void deleteInitialsImage(@Param("userId")int userId,@Param("ssoId")String ssoId);

    @NonVendorQuery
    public void deleteSignatureImage(@Param("userId")int userId,@Param("ssoId")String ssoId);

    @NonVendorQuery
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    public List<Org> getOrgList(@Param("orgId") int orgId, @Param("orgName") String orgName, @Param("parentOrgId") Integer parentOrgId);

    @NonVendorQuery
    public List<Org> getPenskeUserOrgList();

    @NonVendorQuery
    public void addOrg(Org org);

    @NonVendorQuery
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    public void deleteOrg(int orgId);

    @NonVendorQuery
    public Org getEditOrgInfo(int orgId);

    @NonVendorQuery
    public void updateOrg(Org org);

    public List<VendorTree> getVendorList(@Param("corp") String corp,@Param("vendor") String vendor,@Param("orgId") int orgId);

    @NonVendorQuery
    public void addOrgVendor(@Param("orgId")int orgId,@Param("vendorNumber")String vendorNumber);

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public int deleteVendorAssocaWithChild(@Param("orgId")int orgId);

    @NonVendorQuery
    public int deleteVendorAssoc(@Param("orgId")int orgId);

    public List<Integer> getOrgVendor(@Param("orgId")int orgId);

    @NonVendorQuery
    public SignatureInitial getSignatureInitialByUserId(int userId);

    @NonVendorQuery
    public void addBuddies(EditableUser user);

    @NonVendorQuery
    public AdminConsoleUserDept getUserDeptsById(@Param("deptId") int deptId);

    @NonVendorQuery
    public void updateBuddies(EditableUser user);

    @NonVendorQuery
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    public Integer getUsersByOrgId(@Param("orgId")int orgId);

    @NonVendorQuery
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    public List<Org> getOrgChild(@Param("orgId")int orgId);

    @SkipQueryTest("Uses heirarchical queries, which HSQLDB does not support")
    @NonVendorQuery
    public void removeAllRoleFunctionByOrgId(@Param("orgId") int roleId);

    @NonVendorQuery
    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    public void modifyRoleStatusByOrgId( @Param("orgId")int roleId,  @Param("modifiedBy")String modifiedBy);

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<EditableUser> getVendorUserList(@Param("user") User currentUser);

    @SkipQueryTest("Uses heirarchical queries, which HSQLDB does not support")
    @NonVendorQuery
    public List<Role> getVendorRoles(@Param("roleId") int roleId,@Param("orgId") int orgId);
    
    @NonVendorQuery
    public Role getRoleById(@Param("roleId") int roleId);

    @NonVendorQuery
    public List<AdminConsoleUserType> getVendorUserTypes();

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<Org> getVendorOrg(@Param("isVendor") boolean isVendor,@Param("orgId") int orgId);

    @NonVendorQuery
    public void deleteUserFromBuddy(@Param("userSSO") String userSSO);

    @SkipQueryTest("Uses heirarchical query, which HSQLDB does not support")
    @NonVendorQuery
    public List<Org> getMyOrgAndChild(@Param("orgId")int orgId);

    @SkipQueryTest("Uses heirarchical queries, which HSQLDB does not support")
    @NonVendorQuery
    public int removeVendorAssocFromDescendent(@Param("orgId")int orgId);

    @NonVendorQuery
	public void deleteOrgVendor(@Param("orgId") int orgId, @Param("uncheckedIds") String[] strArray);
}