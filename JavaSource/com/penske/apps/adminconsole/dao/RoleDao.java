package com.penske.apps.adminconsole.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;


public interface RoleDao {
	@NonVendorQuery //TODO: Review Query
	public List<Role> getAllRoles(String status);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getRolesBySearchContent(Role role);
	
	@NonVendorQuery //TODO: Review Query
	public List<Tab> getSecurityFunctionTabs(int roleId);

	@NonVendorQuery //TODO: Review Query
	public List<Permission> getRolePermissions(@Param("roleId") int roleId, @Param("tabKey") String tabKey);
	
	@NonVendorQuery //TODO: Review Query
	public List<Permission> getAllRolePermissions(@Param("tabKey") String tabKey);

	@NonVendorQuery //TODO: Review Query
	public List<Integer> getRoleSecurityFunctions(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public Role getBaseRoleId(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getAllChildRoles(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public Integer getUsersByRoleId(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public Integer getNewRoleId();
	
	@NonVendorQuery //TODO: Review Query
	public void addRole(Role role);

	@NonVendorQuery //TODO: Review Query
	public void addRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);

	@NonVendorQuery //TODO: Review Query
	public void removeRoleSecurityFunction(@Param("roleId") int roleId, @Param("functionId") int functionId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyRoleName(Role role);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyRoleStatus( @Param("roleId")int roleId,  @Param("modifiedBy")String modifiedBy);

	@NonVendorQuery //TODO: Review Query
	public  List<Role>  getAllRolesForVendor(int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getMyRoleDescend( @Param("roleId") int roleId, @Param("currOrgId") int currOrgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getMyRoles(@Param("currUserRoleId") int currUserRoleId,@Param("currOrgId") int currOrgId,@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);
	
	@NonVendorQuery //TODO: Review Query
	public Integer checkRoleExist(Role role);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role> getMyDescendRoleWithParentOthOrg( @Param("roleId") int roleId,@Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public void removeAllFunctionByRoleAndDescend(@Param("roleId") int roleId);
	
	@NonVendorQuery //TODO: Review Query
	public Set<String> getMyDescendRoleByOrgId( @Param("orgId") int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role>  getAllVendorRoles(@Param("baseRoleId") int baseRoleId,@Param("roleName") String roleName);
	
	@NonVendorQuery //TODO: Review Query
	public List<Role>  getMyDescendRoleByRoleIdOrgId(@Param("roleId") int roleId,@Param("orgId") int orgId);
}
