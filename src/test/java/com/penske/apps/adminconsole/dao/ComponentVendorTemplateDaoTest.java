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
public class ComponentVendorTemplateDaoTest extends MyBatisDaoTest{

	@Autowired
	private ComponentVendorTemplateDao componentVendorTemplateDao;

	@Before
	public void setup()
	{
		componentVendorTemplateDao = this.trackMethodCalls(componentVendorTemplateDao, ComponentVendorTemplateDao.class);
	}
	
    @Test
    public void shouldGetPoCategories() {
    	componentVendorTemplateDao.getPoCategories();
    }

    @Test
    public void shouldGetExcelSeqTemplates() {
    	componentVendorTemplateDao.getExcelSeqTemplates(1);
    }

    @Test
    public void shouldGetTemplateComponentSequences() {
    	componentVendorTemplateDao.getTemplateComponentSequences(1);
    }

    @Test
    public void shouldUpdateTemplateComponentSequence() {
    	componentVendorTemplateDao.updateTemplateComponentSequence(1, "1234", 1);
    }
}
