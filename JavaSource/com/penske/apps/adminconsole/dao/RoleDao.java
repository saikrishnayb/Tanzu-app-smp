package com.penske.apps.adminconsole.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;


public interface RoleDao {

    @NonVendorQuery
    public List<Tab> getSecurityFunctionTabs(int roleId);

    @NonVendorQuery
    public List<Permission> getRolePermissions(@Param("roleId") int roleId, @Param("tabKey") String tabKey);

    @NonVendorQuery
    public List<Integer> getRoleSecurityFunctions(int roleId);

    @NonVendorQuery
    public Role getBaseRoleId(int roleId);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public Integer getUsersByRoleId(int roleId);

    @NonVendorQuery
    public Integer getNewRoleId();

    @NonVendorQuery
    public void addRole(Role role);

    @NonVendorQuery
    public void addRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public void removeRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

    @NonVendorQuery
    public void modifyRoleName(Role role);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public void modifyRoleStatus( @Param("roleId")int roleId,  @Param("modifiedBy")String modifiedBy);

    @SkipQueryTest("CONNECT BY")
    @NonVendorQuery
    public List<Role> getMyRoles(@Param("currUserRoleId") int currUserRoleId,@Param("currOrgId") int currOrgId,@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);

    @NonVendorQuery
    public Integer checkRoleExist(Role role);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public List<Role> getMyDescendRoleWithParentOthOrg( @Param("roleId") int roleId,@Param("orgId") int orgId);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public void removeAllFunctionByRoleAndDescend(@Param("roleId") int roleId);

    @SkipQueryTest("START WWITH CONNECT BY")
    @NonVendorQuery
    public Set<String> getMyDescendRoleByOrgId( @Param("orgId") int orgId);

    @SkipQueryTest("START WITH CONNECT BY")
    @NonVendorQuery
    public List<Role>  getMyDescendRoleByRoleIdOrgId(@Param("roleId") int roleId,@Param("orgId") int orgId);
}
