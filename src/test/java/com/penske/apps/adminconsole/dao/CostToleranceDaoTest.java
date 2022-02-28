package com.penske.apps.adminconsole.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.domain.CostTolerance;
import com.penske.apps.adminconsole.enums.PoCategoryType;
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
	public void shouldGetTolerances() {
		costToleranceDao.getTolerances(null);
		costToleranceDao.getTolerances(1234);
	}

	@Test
	public void shouldAddTolerance() {
		CostTolerance tolerance = new CostTolerance(PoCategoryType.TRUCK, "FTL", null, new BigDecimal("123.45"));
		costToleranceDao.addTolerance(tolerance);
		
		assertThat(tolerance.getToleranceId(), is(notNullValue()));
	}

	@Test
	public void shouldUpdateTolerance() {
		CostTolerance tolerance = new CostTolerance(PoCategoryType.TRUCK, "FTL", null, new BigDecimal("123.45"));
		costToleranceDao.addTolerance(tolerance);

		tolerance.updateTolerance(new BigDecimal(543.21));
		costToleranceDao.updateTolerance(tolerance);
	}

	@Test
	public void shouldDeleteTolerance() {
		costToleranceDao.deleteTolerance(1);
	}
}
