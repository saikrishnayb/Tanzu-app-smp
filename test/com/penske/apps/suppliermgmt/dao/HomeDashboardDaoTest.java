package com.penske.apps.suppliermgmt.dao;

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

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;
import com.penske.apps.suppliermgmt.dao.HomeDashboardDao;

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
public class HomeDashboardDaoTest extends MyBatisDaoTest{

	@Autowired
	private HomeDashboardDao homeDashboardDao;

	@Before
	public void setup()
	{
		homeDashboardDao = this.trackMethodCalls(homeDashboardDao, HomeDashboardDao.class);
	}
	
	@Test
    public void shouldSelectTabs() {
    	homeDashboardDao.selectTabs(1, 1, "A");
    }

	@Test
    public void shouldSelectHeaders() {
    	homeDashboardDao.selectHeaders("TAB_OF");
    }

	@Test
    public void shouldSelectAlerts() {
		homeDashboardDao.selectAlerts(1, 1);
    }

}
