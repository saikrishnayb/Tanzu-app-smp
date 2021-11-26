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

import com.penske.apps.adminconsole.dao.AlertDao;
import com.penske.apps.adminconsole.model.Alert;
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
public class AlertDaoTest extends MyBatisDaoTest{

	@Autowired
	private AlertDao alertDao;

	@Before
	public void setup()
	{
		alertDao = this.trackMethodCalls(alertDao, AlertDao.class);
	}
	
	@Test
    public void shouldGetAllAlertsAndHeaders() {
    	alertDao.getAllAlertsAndHeaders();
    }

	@Test
    public void shouldGetAllTemplateNames() {
    	alertDao.getAllTemplateNames();
    }

	@Test
    public void shouldCheckForTemplateId() {
    	alertDao.checkForTemplateId(1, 1);
    }

	@Test
    public void shouldModifyAlertHeader() {
    	Alert alert = new Alert();
    	alert.setHeaderName("Header");
    	alert.setHelpText("Help Text");
    	alert.setDisplaySequence(1);
    	alert.setAlertId(1);
    	alertDao.modifyAlertHeader(alert);
    }

	@Test
    public void shouldModifyAlertDetail() {
    	Alert alert = new Alert();
    	alert.setAlertName("Header");
    	alert.setTemplateId(1);
    	alert.setHelpText("Help Text");
    	alert.setDisplaySequence(1);
    	alert.setVisibilityPenske(1);
    	alert.setVisibilityVendor(0);
    	alert.setAlertId(1);
    	alertDao.modifyAlertDetail(alert);
    }
}
