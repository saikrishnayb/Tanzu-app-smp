package com.penske.apps.adminconsole.service;

import java.util.List;
import java.util.Set;

import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;

public interface RoleService {
	public List<Role> getAllRoles(String status);

	public List<Role> getRolesBySearchContent(Role role);

	public List<Tab> getCreateRolePermissions(int roleId);

	public List<Tab> getEditRolePermissions(int roleId);
	
	public Role getRoleById(int roleId);

	public Role getCreateRoleHierarchy(int roleId,int orgId);

	public Role getEditRoleHierarchy(int roleId, int flag,int orgId);
	
	public boolean checkForUsers(int roleId);
	
	public void addRole(Role role, int[] functionIds) throws Exception;
	
	public void modifyRole(Role role, int[] functionIds);
	
	public void modifyRoleStatus(int roleId, String modifiedBy);

	public  List<Role>  getAllRolesForVendor(int roleId);
	
	public List<Role> getMyRoleDescend(int roleId,int currOrgId,boolean isSupplier);
	
	public List<Role> getMyRoles(int currUserRoleId,int currUserOEM, int baseRoleId,String roleName,boolean isSupplier);
	
	public boolean checkRoleExist(Role role,boolean isCreat);
	
	public List<Role> getMyDescendRoleByRoleIdOrgId(int roleId,int orgId);
	
	public Set<String> getMyDescendRoleByOrgId(int orgId);
	
	public List<Role>  removeCurrentRoleAndChild(int roleId,List<Role> roles,int orgId);
	
	//public List<Role> getMyDescendRoleWithParentOthOrg(int roleId,int orgId);
}
