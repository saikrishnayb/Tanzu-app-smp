package com.penske.apps.adminconsole.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class GlobalSettingsDaoTest extends MyBatisDaoTest{
	
	@Autowired
	private GlobalSettingsDao globalSettingsDao;

	@Before
	public void setup()
	{
		globalSettingsDao = this.trackMethodCalls(globalSettingsDao, GlobalSettingsDao.class);
	}
	
	@Test
	public void shouldUpdateGlobalSettingsByKeyName() {
		globalSettingsDao.updateGlobalSettingsByKeyName("value", "key");
	}
	
	@Test
	public void shouldSelectGlobalSettingsValueByKeyName() {
		globalSettingsDao.selectGlobalSettingsValueByKeyName("key");
	}
}
