package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
/**
 * Global Settings DAO
 * 
 * @author mark.weaver 600144069
 *
 */

public interface GlobalSettingsDao {
	@NonVendorQuery //TODO: Review Query
	public void updateGlobalSettingsByKeyName(@Param("value") String value, @Param("keyName") String keyName);
	
	@NonVendorQuery //TODO: Review Query
	public String selectGlobalSettingsValueByKeyName(@Param("keyName") String keyName);
}
