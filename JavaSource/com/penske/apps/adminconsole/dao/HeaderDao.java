package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;



public interface HeaderDao {
	@NonVendorQuery
	public HeaderUser getApplicationUser(int userID);
	
	@NonVendorQuery //TODO: Review Query
	public List<Permission> getPermissions(int roleIdId);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllTabNames();
}
