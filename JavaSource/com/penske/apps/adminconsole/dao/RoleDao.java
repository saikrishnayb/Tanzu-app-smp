package com.penske.apps.adminconsole.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;


public interface RoleDao {
	public List<Role> getAllRoles(String status);
	
	public List<Role> getRolesBySearchContent(Role role);
	
	public List<Tab> getSecurityFunctionTabs(int roleId);

	public List<Permission> getRolePermissions(@Param("roleId") int roleId, @Param("tabId") int tabId);
	
	public List<Permission> getAllRolePermissions(int tabId);

	public List<Integer> getRoleSecurityFunctions(int roleId);
	
	public Role getBaseRoleId(int roleId);
	
	public List<Role> getAllChildRoles(int roleId);
	
	public Integer getUsersByRoleId(int roleId);
	
	public Integer getNewRoleId();
	
	public void addRole(Role role);

	public void addRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

	public void removeRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);
	
	public void modifyRoleName(Role role);
	
	public void modifyRoleStatus( @Param("roleId")int roleId,  @Param("modifiedBy")String modifiedBy);

	public  List<Role>  getAllRolesForVendor(int roleId);
	
	public List<Role> getMyRoleDescend( @Param("roleId") int roleId, @Param("currOrgId") int currOrgId);
	
	public List<Role> getMyRoles(@Param("currUserRoleId") int currUserRoleId,@Param("currOrgId") int currOrgId,@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);
	
	public Integer checkRoleExist(Role role);
	
	public List<Role> getMyDescendRoleWithParentOthOrg( @Param("roleId") int roleId,@Param("orgId") int orgId);
	
	public void removeAllFunctionByRoleAndDescend(@Param("roleId") int roleId);
	
	public Set<String> getMyDescendRoleByOrgId( @Param("orgId") int orgId);
	
	public List<Role>  getAllVendorRoles(@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);
	
	public List<Role>  getMyDescendRoleByRoleIdOrgId(@Param("roleId") int roleId,@Param("orgId") int orgId);
}
