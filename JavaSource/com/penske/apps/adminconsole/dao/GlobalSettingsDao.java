package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
/**
 * Global Settings DAO
 * 
 * @author mark.weaver 600144069
 *
 */
@DBSmc
public interface GlobalSettingsDao {
	@NonVendorQuery
	public void updateGlobalSettingsByKeyName(@Param("value") String value, @Param("keyName") String keyName);
	
	@NonVendorQuery
	public String selectGlobalSettingsValueByKeyName(@Param("keyName") String keyName);
}
