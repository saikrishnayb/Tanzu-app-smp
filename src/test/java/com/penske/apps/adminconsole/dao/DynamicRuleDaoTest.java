package com.penske.apps.adminconsole.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.model.DynamicRule;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class DynamicRuleDaoTest extends MyBatisDaoTest {

	@Autowired
	private DynamicRuleDao dynamicRuleDao;

	@Before
	public void setup()
	{
		dynamicRuleDao = this.trackMethodCalls(dynamicRuleDao, DynamicRuleDao.class);
	}
	
    @Test
    public void shouldGetAllDynamicRulesByStatus() {
    	dynamicRuleDao.getAllDynamicRulesByStatus("A");
    }

    @Test
    public void shouldGetDynamicRuleByPriority() {
    	dynamicRuleDao.getDynamicRuleByPriority(1);
    }

    @Test
    public void shouldGetDynamicRuleById() {
    	dynamicRuleDao.getDynamicRuleById(1);
    }

    @Test
    public void shouldGetAllCorpCodes() {
    	dynamicRuleDao.getAllCorpCodes();
    }

    @Test
    public void shouldGetAllVehicleMakes() {
    	dynamicRuleDao.getAllVehicleMakes();
    }

    @Test
    public void shouldGetVehicleModelsByMake() {
    	dynamicRuleDao.getVehicleModelsByMake("FTL");
    }

    @Test
    public void shouldAddDynamicRule() {
    	DynamicRule rule = new DynamicRule();
    	rule.setPriority(1);
    	rule.setCorpCode("HPTL");
 		rule.setManufacturer("FTL");
 		rule.setModel("A300");
 		rule.setModelYear(2019);
 		rule.setStatus("A");
 		rule.setCreatedBy("600166698");
 		rule.setModifiedBy("600166698");
 		rule.setDynamicRuleId(1);
    	dynamicRuleDao.addDynamicRule(rule);
    }

    @Test
    public void shouldModifyDynamicRule() {
    	DynamicRule rule = new DynamicRule();
    	rule.setPriority(1);
    	rule.setCorpCode("HPTL");
 		rule.setManufacturer("FTL");
 		rule.setModel("A300");
 		rule.setModelYear(2019);
 		rule.setStatus("A");
 		rule.setCreatedBy("600166698");
 		rule.setModifiedBy("600166698");
 		rule.setDynamicRuleId(1);
    	dynamicRuleDao.modifyDynamicRule(rule);
    }

    @Test
    public void shouldGetAvailableStatus() {
    	dynamicRuleDao.getAvailableStatus();
    }

    @Test
    public void shouldGetDynamicRuleByNonPriority() {
    	DynamicRule rule = new DynamicRule();
    	rule.setPriority(1);
    	rule.setCorpCode("HPTL");
 		rule.setManufacturer("FTL");
 		rule.setModel("A300");
 		rule.setModelYear(2019);
 		rule.setStatus("A");
 		rule.setCreatedBy("600166698");
 		rule.setModifiedBy("600166698");
 		rule.setDynamicRuleId(1);
    	dynamicRuleDao.getDynamicRuleByNonPriority(rule);
    }
    

    @Test
    public void shouldReOrderPriority() {
    	dynamicRuleDao.reOrderPriority(1);
    }

    @Test
    public void shouldGetMaxPriority() {
    	dynamicRuleDao.getMaxPriority();
    }

    @Test
    public void shouldDeleteDynamicRule() {
    	dynamicRuleDao.deleteDynamicRule(1);
    }
}
