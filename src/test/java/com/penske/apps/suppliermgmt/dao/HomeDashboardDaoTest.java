package com.penske.apps.suppliermgmt.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.domain.enums.SmcTab;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
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
    	homeDashboardDao.selectHeaders(SmcTab.ORDER_FULFILLMENT);
    }

	@Test
    public void shouldSelectAlerts() {
		homeDashboardDao.selectAlerts(1, UserType.PENSKE, UserType.PENSKE);
		homeDashboardDao.selectAlerts(1, UserType.VENDOR, UserType.PENSKE);
    }

}
