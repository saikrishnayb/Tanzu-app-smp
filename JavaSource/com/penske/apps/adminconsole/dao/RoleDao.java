package com.penske.apps.adminconsole.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;


public interface RoleDao {

    @NonVendorQuery
    public List<Role> getAllRoles(String status);

    @NonVendorQuery
    public List<Role> getRolesBySearchContent(Role role);

    @NonVendorQuery
    public List<Tab> getSecurityFunctionTabs(int roleId);

    @NonVendorQuery
    public List<Permission> getRolePermissions(@Param("roleId") int roleId, @Param("tabKey") String tabKey);

    @NonVendorQuery
    public List<Permission> getAllRolePermissions(@Param("tabKey") String tabKey);

    @NonVendorQuery
    public List<Integer> getRoleSecurityFunctions(int roleId);

    @NonVendorQuery
    public Role getBaseRoleId(int roleId);

    @NonVendorQuery
    public List<Role> getAllChildRoles(int roleId);

    @NonVendorQuery
    public Integer getUsersByRoleId(int roleId);

    @NonVendorQuery
    public Integer getNewRoleId();

    @NonVendorQuery
    public void addRole(Role role);

    @NonVendorQuery
    public void addRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

    @NonVendorQuery
    public void removeRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

    @NonVendorQuery
    public void modifyRoleName(Role role);

    @NonVendorQuery
    public void modifyRoleStatus( @Param("roleId")int roleId,  @Param("modifiedBy")String modifiedBy);

    @NonVendorQuery
    public  List<Role>  getAllRolesForVendor(int roleId);

    @NonVendorQuery
    public List<Role> getMyRoleDescend( @Param("roleId") int roleId, @Param("currOrgId") int currOrgId);

    @NonVendorQuery
    public List<Role> getMyRoles(@Param("currUserRoleId") int currUserRoleId,@Param("currOrgId") int currOrgId,@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);

    @NonVendorQuery
    public Integer checkRoleExist(Role role);

    @NonVendorQuery
    public List<Role> getMyDescendRoleWithParentOthOrg( @Param("roleId") int roleId,@Param("orgId") int orgId);

    @NonVendorQuery
    public void removeAllFunctionByRoleAndDescend(@Param("roleId") int roleId);

    @NonVendorQuery
    public Set<String> getMyDescendRoleByOrgId( @Param("orgId") int orgId);

    @NonVendorQuery
    public List<Role>  getAllVendorRoles(@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);

    @NonVendorQuery
    public List<Role>  getMyDescendRoleByRoleIdOrgId(@Param("roleId") int roleId,@Param("orgId") int orgId);
}
