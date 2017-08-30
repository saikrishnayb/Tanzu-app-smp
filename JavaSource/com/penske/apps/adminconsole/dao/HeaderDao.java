package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;



public interface HeaderDao {
	@NonVendorQuery
	public HeaderUser getApplicationUser(int userID);
	
	public List<Permission> getPermissions(int roleIdId);
	
	public List<String> getAllTabNames();
}
