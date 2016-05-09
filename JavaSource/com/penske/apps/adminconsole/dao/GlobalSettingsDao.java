package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
/**
 * Global Settings DAO
 * 
 * @author mark.weaver 600144069
 *
 */

public interface GlobalSettingsDao {
	public void updateGlobalSettingsByKeyName(@Param("value") String value, @Param("keyName") String keyName);
	
	public String selectGlobalSettingsValueByKeyName(@Param("keyName") String keyName);
}
