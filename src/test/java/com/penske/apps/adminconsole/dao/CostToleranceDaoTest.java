package com.penske.apps.adminconsole.dao;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.CostTolerance;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class })
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class CostToleranceDaoTest extends MyBatisDaoTest {

	@Autowired
	private CostToleranceDao costToleranceDao;

	@Before
	public void setup() {
		costToleranceDao = this.trackMethodCalls(costToleranceDao, CostToleranceDao.class);
	}

	@Test
	public void shouldGetAllManufacturers() {
		costToleranceDao.getAllManufacturers();
	}

	@Test
	public void shouldGetAllTolerances() {
		costToleranceDao.getAllTolerances();
	}

	@Test
	public void shouldGetTolerance() {
		costToleranceDao.getTolerance(1);
	}

	@Test
	public void shouldAddTolerance() {
		CostTolerance costTolerance = new CostTolerance();
		costTolerance.setPoCategory(PoCategoryType.TRUCK);
		costTolerance.setMfrCode("FTL");
		costTolerance.setTolerance(new BigDecimal(123.45));
		costToleranceDao.addTolerance(costTolerance);
	}

	@Test
	public void shouldUpdateTolerance() {
		CostTolerance costTolerance = new CostTolerance();
		costTolerance.setToleranceId(1);
		costTolerance.setPoCategory(PoCategoryType.TRUCK);
		costTolerance.setMfrCode("FTL");
		costTolerance.setTolerance(new BigDecimal(123.45));
		costToleranceDao.addTolerance(costTolerance);

		costTolerance.setTolerance(new BigDecimal(543.21));
		costToleranceDao.updateTolerance(costTolerance);
	}

	@Test
	public void shouldDeleteTolerance() {
		costToleranceDao.deleteTolerance(1);
	}
}
