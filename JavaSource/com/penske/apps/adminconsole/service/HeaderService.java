package com.penske.apps.adminconsole.service;

import java.util.HashMap;
import java.util.List;

import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;

public interface HeaderService {
	public HashMap<String, List<Permission>> getRoleInformation(int roleId);
}
