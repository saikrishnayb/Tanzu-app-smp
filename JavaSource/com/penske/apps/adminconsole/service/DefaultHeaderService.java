package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.HeaderDao;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;

@Service
public class DefaultHeaderService implements HeaderService{

	@Autowired
	private HeaderDao headerDao;
	
	@Override
	public HeaderUser getApplicationUserInfo(int userId) {
		return headerDao.getApplicationUser(userId);
	}

	@Override
	public HashMap<String, List<Permission>> getRoleInformation(int roleId) {
		
		List<Permission> permissionList = headerDao.getPermissions(roleId);
		List<String> tabNameList = headerDao.getAllTabNames();

		HashMap<String, List<Permission>> tabPermissionMap = new HashMap<String, List<Permission>>();

		for(String tabName: tabNameList){
			List<Permission> tabPermissionList = new ArrayList<Permission>();
			for(Permission permission: permissionList){
				boolean isTabMatch = permission.getTabName().equalsIgnoreCase(tabName);
				
				if(isTabMatch)	
					tabPermissionList.add(permission);
			}
			if(tabPermissionList.size() > 0)
				tabPermissionMap.put(tabName, tabPermissionList);
		}
		return tabPermissionMap;
	}

}
