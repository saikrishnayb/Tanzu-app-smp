package com.pensle.apps.adminconsole.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.GlobalSettingsDao;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@SqlGroup({
    @Sql(scripts = "/setup/create-smc-database.sql"),
    @Sql(scripts = "/setup/drop-smc-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
    @Sql(scripts = "/setup/create-corp-database.sql"),
    @Sql(scripts = "/setup/drop-corp-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
}) 
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