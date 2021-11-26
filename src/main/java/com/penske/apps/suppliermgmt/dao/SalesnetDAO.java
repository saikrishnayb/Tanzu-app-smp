package com.penske.apps.suppliermgmt.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSalesnet;
import com.penske.apps.suppliermgmt.model.Buddies;

@DBSalesnet
public interface SalesnetDAO {
	@NonVendorQuery
    public void deleteBuddyList(String userSSO) throws  SQLException;

    @NonVendorQuery
    public void addBuddyList(@Param("newBuddyList") List<Buddies> newBuddyList, @Param("sso") String sso)throws  SQLException;

}
