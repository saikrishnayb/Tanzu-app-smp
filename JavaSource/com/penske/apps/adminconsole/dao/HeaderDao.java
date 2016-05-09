package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.Permission;



public interface HeaderDao {
	public HeaderUser getApplicationUser(int userID);
	
	public List<Permission> getPermissions(int roleIdId);
	
	public List<String> getAllTabNames();
}
