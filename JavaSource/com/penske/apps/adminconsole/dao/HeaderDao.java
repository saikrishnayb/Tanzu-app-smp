package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.Permission;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;



public interface HeaderDao {

    @NonVendorQuery
    public List<Permission> getPermissions(int roleIdId);

    @NonVendorQuery
    public List<String> getAllTabNames();
}
