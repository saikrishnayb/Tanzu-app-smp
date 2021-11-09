package com.pensle.apps.adminconsole.dao;

import java.util.Arrays;

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

import com.penske.apps.adminconsole.dao.ComponentDao;
import com.penske.apps.adminconsole.model.Components;
import com.penske.apps.adminconsole.model.HoldPayment;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.TestData;
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
public class ComponentDaoTest extends MyBatisDaoTest
{
	@Autowired
	private ComponentDao componentDao;
	
	private TestData data = new TestData();

	@Before
	public void setup()
	{
		componentDao = this.trackMethodCalls(componentDao, ComponentDao.class);
	}
	
    @Test
    public void shouldGetAllTemplates() {
    	componentDao.getAllTemplates();
    }

    @Test
    public void shouldGetTemplatesById() {
    	componentDao.getTemplatesById(1);
    }

    @Test
    public void shouldGetAllComponent() {
    	componentDao.getAllComponent();
    }

    @Test
    public void shouldAddTemplateComponent() {
    	Components components =  new Components();
    	components.setComponentId("1234");
    	components.setTemplateId(1);
    	components.setEditRequiredStr("Y");
    	components.setDispOtherPOStr("Y");
    	components.setExcelStr("Y");
    	components.setCreatedBy("600166698");
    	componentDao.addTemplateComponent(components);
    }
    
    @Test
    public void shouldUpdateTemplateComponent() {
    	Components components =  new Components();
    	componentDao.updateTemplateComponent(components);
    }

    @Test
    public void shouldUpdateTemplate() {
    	Template template = new Template();
    	componentDao.updateTemplate(template);
    }
    
    @Test
    public void shouldGetIsTemplateComponentExist() {
    	componentDao.isTemplateComponentExist(1, 1);
    }
    
    @Test
    public void shouldDeActivateTemplate() {
    	componentDao.deActivateTemplate(1);
    }
    
    @Test
    public void shouldActivateTemplate() {
    	componentDao.activateTemplate(1);
    }

    @Test
    public void shouldDeleteTemplateComponents() {
    	componentDao.deleteTemplateComponents(Arrays.asList(1), 1);
    }
    
    @Test
    public void shouldGetTemplateComponentById() {
    	componentDao.getTemplateComponentById(1);
    }

    @Test
    public void shouldFindTemplateExist() {
    	Template template = new Template();
    	componentDao.findTemplateExist(template);
    }

    @Test
    public void shouldGetAllPoAssociationForAdd() {
    	componentDao.getAllPoAssociationForAdd();
    }

    @Test
    public void shouldGetAllPoAssociationForEdit() {
    	componentDao.getAllPoAssociationForEdit();
    }

    @Test
    public void shouldLoadAllAvailableComponents() {
    	componentDao.loadAllAvailableComponents();
    }

    @Test
    public void shouldGetComponentGroup() {
    	componentDao.getComponentGroup(1);
    }

    @Test
    public void shouldCopyCorpComponentGroupRow() {
    	componentDao.copyCorpComponentGroupRow(1);
    }

    @Test
    public void shouldCopyCorpComponentRow() {
    	componentDao.copyCorpComponentRow(1);
    }

    @Test
	public void shouldGetTemplateComponentByTempId() {
    	componentDao.getTemplateComponentByTempId(1);
    }
    
    @Test
    public void shouldAllowDuplicateComponents() {
    	componentDao.allowDuplicateComponents(1, true);
    }

    @Test
    public void shouldGetComponentById() {
    	componentDao.getComponentById(1);
    }
    
    @Test
    public void shouldGetAllHoldPayments() {
    	componentDao.getAllHoldPayments();
    }
    
    @Test
    public void shouldGetHoldPaymentsByComponentId() {
    	componentDao.getHoldPaymentsByComponentId(1);
    }

    @Test
	public void shouldAddHoldPayments() {
    	HoldPayment test = new HoldPayment(1,1);
    	componentDao.addHoldPayments(Arrays.asList(test), data.user);
    }

    @Test
	public void shouldDeleteHoldPayments() { 
    	HoldPayment test = new HoldPayment(1,1);
    	componentDao.deleteHoldPayments(Arrays.asList(test));
    }
}
