package com.penske.apps.adminconsole.service;

import java.util.HashMap;
import java.util.List;

import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;

public interface HeaderService {
	public HeaderUser getApplicationUserInfo(int userId);

	public HashMap<String, List<Permission>> getRoleInformation(int roleId);
}
