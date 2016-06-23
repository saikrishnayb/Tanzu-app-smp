package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.RoleDao;
import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.adminconsole.model.Tab;
import com.penske.apps.adminconsole.util.CommonUtils;

@Service
public class DefaultRoleService implements RoleService {
	@Autowired
	RoleDao roleDao;
	
	@Override
	public List<Role> getAllRoles(String status) {
		return roleDao.getAllRoles(status);
	}
	
	@Override
	public List<Role> getRolesBySearchContent(Role role) {
		// Base Role ID cannot be negative.
		if (role.getBaseRoleId() < 0 && role.getBaseRoleId() != -1) {
			return null;
		}
		
		// Status can only be 1 (Active) or 0 (Inactive).
		if (role.getStatus() == null ) {
			return null;
		}
		
		return roleDao.getRolesBySearchContent(role);
	}

	@Override
	public List<Tab> getCreateRolePermissions(int roleId) {
		List<Tab> tabs = roleDao.getSecurityFunctionTabs(roleId);
		
		// Since the role hasn't been created yet, just get all permissions of the base role.
		for (Tab currentTab : tabs) {
			if (roleId != 0) {
				currentTab.setPermissions(roleDao.getRolePermissions(roleId, currentTab.getTabId()));
			}
			else {
				currentTab.setPermissions(roleDao.getAllRolePermissions(currentTab.getTabId()));
			}
		}
			
		return tabs;
	}
	
	@Override
	public List<Tab> getEditRolePermissions(int roleId) {
		Role role = roleDao.getBaseRoleId(roleId);
		List<Tab> tabs = roleDao.getSecurityFunctionTabs(role.getBaseRoleId());
		
		// For each tab, get the security functions for the role based on the role's permissions.
		for (Tab currentTab : tabs) {
			if (role.getBaseRoleId() != 0) {
				List<Permission> baseRolePermissions = roleDao.getRolePermissions(role.getBaseRoleId(), currentTab.getTabId());
				List<Permission> rolePermissions = roleDao.getRolePermissions(role.getRoleId(), currentTab.getTabId());
				
				// For each base role permission, if the current role also has that permission, then set available to 'true'.
				// This is used to automatically check the security functions for a user editing a role.
				for (Permission basePermission : baseRolePermissions) {
					for (Permission permission : rolePermissions) {
						if (permission.getSecurityFunctionId() == basePermission.getSecurityFunctionId()) {
							basePermission.setAvailable(true);
							break;
						}
					}
				}
				
				currentTab.setPermissions(baseRolePermissions);
			}
			else {
				List<Permission> baseRolePermissions = roleDao.getAllRolePermissions(currentTab.getTabId());
				List<Permission> rolePermissions = roleDao.getRolePermissions(role.getRoleId(), currentTab.getTabId());
				
				// For each base role permission, if the current role also has that permission, then set available to 'true'.
				// This is used to automatically check the security functions for a user editing a role.
				for (Permission basePermission : baseRolePermissions) {
					for (Permission permission : rolePermissions) {
						if (permission.getSecurityFunctionId() == basePermission.getSecurityFunctionId()) {
							basePermission.setAvailable(true);
							break;
						}
					}
				}
				
				currentTab.setPermissions(baseRolePermissions);
			}
		}
			
		return tabs;
	}
	
	@Override
	public Role getRoleById(int roleId) {
		return roleDao.getBaseRoleId(roleId);
	}
	
	@Override
	public boolean checkForUsers(int roleId) {
		if (roleDao.getUsersByRoleId(roleId) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public Role getCreateRoleHierarchy(int roleId,int orgId) {
		/*Role rootRole = roleDao.getBaseRoleId(roleId);
		
		if (rootRole.getBaseRoleId() == 0) {
			rootRole.setSubRoles(roleDao.getAllChildRoles(rootRole.getRoleId()));
		}

		// Recurse back up through roles by the base role ID until the root role is reached (one without a base role).
		while (rootRole.getBaseRoleId() != 0) {
			Role tempRole = roleDao.getBaseRoleId(rootRole.getBaseRoleId());
			
			// *** If the current rootRole is the one the user chose, then get all sibling roles for display.
			if (rootRole.getRoleId() == roleId) {
				rootRole.setSubRoles(roleDao.getAllChildRoles(rootRole.getRoleId()));
			}
			
			// Add the current role to the list of sub-roles of the base role.
			List<Role> subRoles = new ArrayList<Role>(1);
			subRoles.add(rootRole);
			tempRole.setSubRoles(subRoles);
			
			rootRole = tempRole;
		}
		*/
		List<Role> roleList=roleDao.getMyDescendRoleWithParentOthOrg(roleId,orgId);
		//List<Role> roleList=roleDao.getMyDescendRoleByRoleIdOrgId(roleId,orgId);
		 Role topRole=null;
		 Map<Integer, Role> roleMap=new HashMap<Integer, Role>();
		if(roleList !=null && !roleList.isEmpty()){
			topRole=roleList.get(0);
			for (Role role : roleList) {
				roleMap.put(role.getRoleId(), role);
			}
			CommonUtils.buildHierarchy(topRole,roleMap);
		}
		return topRole;
	}
	
	
	
	
	@Override
	public Role getEditRoleHierarchy(int roleId,int  flag,int orgId) {
		int baseRoleId=roleId;
		if(flag==0){
			Role rootRole = roleDao.getBaseRoleId(roleId);
			baseRoleId=rootRole.getBaseRoleId();
		}
	/*	// Recurse back up through roles by the base role ID until the root role is reached (one without a base role).
		while (rootRole.getBaseRoleId() != 0) {
			Role baseRole = roleDao.getBaseRoleId(rootRole.getBaseRoleId());

			// Add the current role to the list of sub-roles of the base role.
			List<Role> subRoles = new ArrayList<Role>(1);
			subRoles.add(rootRole);
			baseRole.setSubRoles(subRoles);

			rootRole = baseRole;
		}
		
		return rootRole;*/
		
		List<Role> roleList=roleDao.getMyDescendRoleWithParentOthOrg(baseRoleId,orgId);
		//List<Role> roleList=roleDao.getMyDescendRoleByRoleIdOrgId(roleId,orgId);
		 Role topRole=null;
		 Map<Integer, Role> roleMap=new HashMap<Integer, Role>();
		if(roleList !=null && !roleList.isEmpty()){
			topRole=roleList.get(0);
			for (Role role : roleList) {
				roleMap.put(role.getRoleId(), role);
			}
			CommonUtils.buildHierarchy(topRole,roleMap);
		}
		return topRole;
	}
	
	@Override
	@Transactional
	public void addRole(Role role, int[] functionIds, String manufacturer) throws Exception {
		int newRoleId = 0;
		/*int isRoleExist=roleDao.checkRoleExist(role);
		if(isRoleExist==0){*/
			//role.setVendor(manufacturer);
			
			// Role name cannot be null or blank.
			if (StringUtils.isEmpty(role.getRoleName())) {
				return;
			}
			// Base Role ID cannot be negative.
			else if (role.getBaseRoleId() < 0) {
				return;
			}
			// The role must have permissions
			else if (functionIds.length == 0) {
				return;
			}
			try{
					roleDao.addRole(role);
			}catch(Exception e){
				throw e;
			}	
			newRoleId = roleDao.getNewRoleId();
			
			// The just-created role ID cannot be negative or 0.
			if (newRoleId <= 0) {
				return;
			}
			else {
				for (int i = 0; i < functionIds.length; i++) {
					// The security function ID cannot be negative or 0.
					if (functionIds[i] > 0) {
						roleDao.addRoleSecurityFunction(newRoleId, functionIds[i]);
					}
				}
			}
		/*}else{
			throw new Exception("An active role already exists with the role name "+role.getBaseRoleName());
		}*/
	}
	
	@Override
	@Transactional
	public void modifyRole(Role role, int[] functionIds) {
		//Role prevRole = null;
		
		// The role name cannot be null or blank.
		if (StringUtils.isEmpty(role.getRoleName())) {
			return;
		}
		// The base role ID cannot be negative.
		else if (role.getBaseRoleId() < 0) {
			return;
		}
		// The array of security function IDs cannot be empty.
		else if (functionIds.length == 0) {
			return;
		}
		
	/*	prevRole = roleDao.getBaseRoleId(role.getRoleId());
		
		// If the current role could not be found in the database, then return.
		if (prevRole == null) {
			return;
		}
		*/
		// If the role name has changed, then update it.
		//if (!prevRole.getRoleName().equals(role.getRoleName())) {
			roleDao.modifyRoleName(role);
		//}
		
		// Get the current permissions for the role. Compare the list of current permissions to the list of new, user-specified permissions.
		// For each new permission not currently in the role, add the permission to the database.
		// For each current permission not in the list of new permissions, delete the permission from the role AND all of its child roles.
		List<Integer> roleFunctions = roleDao.getRoleSecurityFunctions(role.getRoleId());
		
		if (roleFunctions != null) {
			for (int i = 0; i < functionIds.length; i++) {
				// The security function ID cannot be negative.
				if (functionIds[i] > 0) {
					boolean found = false;
					
					Iterator<Integer> funcIter = roleFunctions.iterator();
					
					// Look for the new permission in the current list of permissions.
					while (funcIter.hasNext()) {
						Integer function = funcIter.next();
						
						if (function.intValue() == functionIds[i]) {
							// Remove the found permission from the current list.
							roleFunctions.remove(function);
							found = true;
							
							break;
						}
					}
					
					// If the new permission could not be found, then add it in the database.
					if (!found) {
						roleDao.addRoleSecurityFunction(role.getRoleId(), functionIds[i]);
					}
				}
			}
			
			// Any remaining current role permissions will be removed in the database.
			for (int j = 0; j < roleFunctions.size(); j++) {
				roleDao.removeRoleSecurityFunction(role.getRoleId(), roleFunctions.get(j));
			}
		}
	}
	
	@Override
	@Transactional
	public void modifyRoleStatus(int roleId,String modifiedBy) {
		// The role ID cannot be negative or 0.
		if (roleId <= 0) {
			return;
		}
		
		// A role cannot be deactivated while there are users with that role or any of its sub-roles.
		if (!checkForUsers(roleId)) {
			roleDao.removeAllFunctionByRoleAndDescend(roleId);
			roleDao.modifyRoleStatus(roleId,modifiedBy);
		}
	}

	@Override
	public  List<Role>  getAllRolesForVendor(int roleId) {
		// TODO Auto-generated method stub
		return roleDao.getAllRolesForVendor(roleId);
	}

	@Override
	public List<Role> getMyRoleDescend(int roleId,int currOrgId,boolean isSupplier) {
	//	List<Role> myRoles =  roleDao.getMyRoleDescend(roleId,currOrgId);
		List<Role> myRoles =  roleDao.getMyDescendRoleWithParentOthOrg(roleId, currOrgId);
		/*if(!isSupplier){
			myRoles.addAll(roleDao.getAllVendorRoles( 0,null));
		}*/
		return myRoles;
	}
	

	//@Override
	//public List<Role> getMyDescendRoleWithParentOthOrg(int roleId,int orgId) {
	//	List<Role> roleList=roleDao.getMyDescendRoleWithParentOthOrg(baseRoleId,orgId);
	//}
	
	@Override
	public List<Role> getMyRoles(int currUserRoleId,int currOrgId, int baseRoleId,String roleName,boolean isSupplier){
		List<Role> myRoles = roleDao.getMyRoles(currUserRoleId,currOrgId,baseRoleId,roleName);
		/*if(!isSupplier){
			myRoles.addAll(roleDao.getAllVendorRoles(baseRoleId,roleName));
		}*/
		return myRoles;
	}

	@Override
	public boolean checkRoleExist(Role role,boolean isCreat) {
		Integer roleId=roleDao.checkRoleExist(role);
		if(isCreat){
			if(roleId !=null && roleId>0){
				return true;
			}else{
				return false;
			}
		}else{
			if(roleId !=null && roleId>0 && roleId !=role.getRoleId()){
				return true;
			}else{
				return false;
			}
		}
	}
	
	@Override
	public List<Role> getMyDescendRoleByRoleIdOrgId(int roleId,int orgId){
		return roleDao.getMyDescendRoleByRoleIdOrgId(roleId,orgId);
	}
	
	@Override
	public Set<String> getMyDescendRoleByOrgId(int orgId){
		return roleDao.getMyDescendRoleByOrgId(orgId);
	}

	@Override
	public List<Role> removeCurrentRoleAndChild(int roleId,List<Role> roles,int orgId) {
		List<Role> myRoles=roleDao.getMyDescendRoleByRoleIdOrgId(roleId,orgId);
		List<Integer> loopCnt=new ArrayList<Integer>();
		for (Iterator<Role> iterator = roles.iterator();iterator.hasNext();) {
			int tempRoleId=iterator.next().getRoleId();
			for (Role role : myRoles) {
				if(tempRoleId==role.getRoleId()){
					iterator.remove();
					loopCnt.add(1);
				}
			}
			if(loopCnt.size()==myRoles.size()){
				break;
			}
		}
		return roles;
	}

	//@Override
	//public List<Role> getAllVendorRoles() {
	//	return roleDao.getAllVendorRoles();
	//}
}
