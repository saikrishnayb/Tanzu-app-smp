package com.penske.apps.adminconsole.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.model.CostAdjustmentOption;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class })
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class CostAdjustmentOptionDaoTest extends MyBatisDaoTest {

	@Autowired
	private CostAdjustmentOptionDao costAdjustmentOptionDao;

	@Before
	public void setup() {
		costAdjustmentOptionDao = this.trackMethodCalls(costAdjustmentOptionDao, CostAdjustmentOptionDao.class);
	}

	@Test
	public void shouldGetAllAdjustmentOptions() {
		costAdjustmentOptionDao.getAllAdjustmentOptions();
	}

	@Test
	public void shouldGetAdjustmentOption() {
		costAdjustmentOptionDao.getAdjustmentOption(1);
	}

	@Test
	public void shouldAddAdjustmentOption() {
		CostAdjustmentOption caOption = new CostAdjustmentOption();
		caOption.setOrderCode("Order Code New");
		costAdjustmentOptionDao.addAdjustmentOption(caOption);
	}

	@Test
	public void shouldUpdateAdjustmentOption() {
		CostAdjustmentOption caOption = new CostAdjustmentOption();
		caOption.setOptionId(1);
		caOption.setOrderCode("Order Code New");
		costAdjustmentOptionDao.addAdjustmentOption(caOption);

		caOption.setOrderCode("Order Code Updated");
		costAdjustmentOptionDao.updateAdjustmentOption(caOption);
	}

	@Test
	public void shouldDeleteAdjustmentOption() {
		costAdjustmentOptionDao.deleteAdjustmentOption(1);
	}
}
