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
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class LookUpDAOTest extends MyBatisDaoTest{

	@Autowired
	private LookUpDAO lookUpDAO;

	@Before
	public void setup()
	{
		lookUpDAO = this.trackMethodCalls(lookUpDAO, LookUpDAO.class);
	}
	
	@Test
	public void shouldGetAllLookupList() {
		lookUpDAO.getAllLookupList();
	}
}