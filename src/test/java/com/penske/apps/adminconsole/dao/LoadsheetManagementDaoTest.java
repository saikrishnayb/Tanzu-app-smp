package com.penske.apps.adminconsole.dao;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadsheetCompGrpSeq;
import com.penske.apps.adminconsole.model.LoadsheetSequenceGroupMaster;
import com.penske.apps.adminconsole.model.LoadsheetSequenceMaster;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.adminconsole.model.TemplateComponentRuleAssociation;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.TestData;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class LoadsheetManagementDaoTest extends MyBatisDaoTest {

	@Autowired
	private LoadsheetManagementDao loadsheetManagementDao;

	private final TestData data = new TestData();
	
	private User user = data.user;

	@Before
	public void setup()
	{
		loadsheetManagementDao = this.trackMethodCalls(loadsheetManagementDao, LoadsheetManagementDao.class);
	}
	
    @Test
    public void shouldGetLoadsheetComponents() {
    	loadsheetManagementDao.getLoadsheetComponents("Cat", "Type");
    }

    @Test
    public void shouldGetLoadsheetSequences() {
    	loadsheetManagementDao.getLoadsheetSequences("Cat", "Type");
    }

    @Test
    public void shouldGetLoadsheetSequence() {
    	loadsheetManagementDao.getLoadsheetSequence();
    }

    @Test
    public void shouldGetLoadsheetManagementDetails() {
    	loadsheetManagementDao.getLoadsheetManagementDetails();
    }

    @Test
    public void shouldGetUsesDefaultForCategoryAndType() {
    	loadsheetManagementDao.getUsesDefaultForCategoryAndType("Cat", "Type");
    }

    @Test
    public void shouldGetLoadsheetRules() {
    	loadsheetManagementDao.getLoadsheetRules();
    }

    @Test
    public void shouldGetComponentRules() {
    	loadsheetManagementDao.getComponentRules();
    }

    @Test
    public void shouldGetComponentVisibilityRules() {
    	loadsheetManagementDao.getComponentVisibilityRules(1);
    }

    @Test
    public void shouldDeleteComponentVisibilityRules() {
    	loadsheetManagementDao.deleteComponentVisibilityRules(1);
    }

    @Test
    public void shouldSaveComponentVisibilityRules() {
    	ComponentRuleAssociation componentRule = new ComponentRuleAssociation();
    	componentRule.setComponentVisibilityId(1);
    	componentRule.setCreatedBy(user.getSso());
    	componentRule.setDisplayName("Rule");
    	ConfigureRule rule = new ConfigureRule();
    	rule.setRuleId(1);
    	rule.setPriority("1");
    	rule.setLsOverride("Y");
    	componentRule.setRule(Arrays.asList(rule));
    	loadsheetManagementDao.saveComponentVisibilityRules(componentRule);
    }

    @Test
    public void shouldGetComponents() {
    	loadsheetManagementDao.getComponents();
    }

    @Test
    public void shouldGetAllLoadsheetRuleNames() {
    	loadsheetManagementDao.getAllLoadsheetRuleNames(1);
    }

    @Test
    public void shouldGetAllUnitTemplateRuleNamesByTemplateId() {
    	loadsheetManagementDao.getAllUnitTemplateRuleNamesByTemplateId(1, 1);
    }

    @Test
    public void shouldInsertRuleMasterDetails() {
    	RuleMaster rule = new RuleMaster();
    	rule.setRuleName("Rule");
    	rule.setDescription("Rule desc");
    	
    	loadsheetManagementDao.insertRuleMasterDetails(rule, data.user);
    }

    @Test
    public void shouldInsertRuleDefinitions() {
    	RuleDefinitions rule = new RuleDefinitions();
    	rule.setRuleId(1);
    	rule.setCriteriaGroup(2);
    	rule.setComponentId("5");
    	rule.setComponentType("T");
    	rule.setOperand("+");
    	rule.setValue("val");
    	loadsheetManagementDao.insertRuleDefinitions(Arrays.asList(rule), data.user);
    }

    @Test
    public void shouldUpdateRuleMasterDetails() {
    	RuleMaster rule = new RuleMaster();
    	rule.setRuleName("Rule");
    	rule.setDescription("Rule desc");
    	loadsheetManagementDao.updateRuleMasterDetails(rule, data.user);
    }

    @Test
    public void shouldUpdateRuleDefinitions() {
    	RuleDefinitions rule = new RuleDefinitions();
    	rule.setRuleId(1);
    	rule.setCriteriaGroup(2);
    	rule.setComponentId("5");
    	rule.setComponentType("T");
    	rule.setOperand("+");
    	rule.setValue("val");
    	loadsheetManagementDao.updateRuleDefinitions(rule, data.user);
    }

    @Test
    public void shouldGetRuleDetails() {
    	loadsheetManagementDao.getRuleDetails(1);
    }

    @Test
    public void shouldDeleteRuleDefinitions() {
    	loadsheetManagementDao.deleteRuleDefinitions(Arrays.asList(1));
    }

    @Test
    public void shouldDeleteRuleMasterDetails() {
    	loadsheetManagementDao.deleteRuleMasterDetails(1);
    }

    @Test
    public void shouldDeleteRuleAssociation() {
    	loadsheetManagementDao.deleteRuleAssociation(1);
    }

    @Test
    public void shoudlDeleteRuleDefDetails() {
    	loadsheetManagementDao.deleteRuleDefDetails(1);
    }

    @Test
    public void shouldGetAssignedLoadsheetCategories() {
    	loadsheetManagementDao.getAssignedLoadsheetCategories(1);
    }

    @Test
    public void shouldGetCategoryList() {
    	loadsheetManagementDao.getCategoryList();
    }

    @Test
    public void shouldGetTypeList() {
    	loadsheetManagementDao.getTypeList("cat");
    }

    @Test
    public void  shouldGetMfrList() {
    	loadsheetManagementDao.getMfrList(PoCategoryType.BODY);
    }

    @Test
    public void shouldGetUnAssignedComponents() {
    	loadsheetManagementDao.getUnAssignedComponents("cat", "type");
    }

    @Test
    public void shouldInsertSeqMasterDetails() {
    	LoadsheetSequenceMaster seqMaster = new LoadsheetSequenceMaster();
    	seqMaster.setName("seqName");
    	seqMaster.setDescription("desc");
    	seqMaster.setCategory("cat");
    	seqMaster.setType("type");
    	seqMaster.setOem("oem");
    	loadsheetManagementDao.insertSeqMasterDetails(seqMaster, data.user);
    }

    @Test
    public void shouldInsertGrpMasterDetails() {
    	LoadsheetSequenceGroupMaster grpMaster = new LoadsheetSequenceGroupMaster();
    	grpMaster.setSeqMasterId(1);
    	grpMaster.setName("grpMas");
    	grpMaster.setDisplaySeq(1);
    	loadsheetManagementDao.insertGrpMasterDetails(grpMaster, data.user);
    }

    @Test
    public void shouldInsertCmpGrpSeqDetails() {
    	LoadsheetCompGrpSeq cmpGrpSeq = new LoadsheetCompGrpSeq();
    	cmpGrpSeq.setGrpMasterId(1);
    	cmpGrpSeq.setComponentId("2");
    	cmpGrpSeq.setDisplaySeq(3);
    	loadsheetManagementDao.insertCmpGrpSeqDetails(Arrays.asList(cmpGrpSeq), data.user);
    }

    @Test
    public void shouldGetSequenceMasterDetails() {
    	loadsheetManagementDao.getSequenceMasterDetails(1);
    }

    @Test
    public void shouldUpdateSeqMasterDetails() {
    	LoadsheetSequenceMaster seqMaster = new LoadsheetSequenceMaster();
    	seqMaster.setName("seqName");
    	seqMaster.setDescription("desc");
    	seqMaster.setCategory("cat");
    	seqMaster.setType("type");
    	seqMaster.setOem("oem");
    	seqMaster.setId(1);
    	loadsheetManagementDao.updateSeqMasterDetails(seqMaster, data.user);
    }

    @Test
    public void shouldUpdateGrpMasterDetails() {
    	LoadsheetSequenceGroupMaster grpMaster = new LoadsheetSequenceGroupMaster();
    	grpMaster.setSeqMasterId(1);
    	grpMaster.setName("grpMas");
    	grpMaster.setDisplaySeq(1);
    	loadsheetManagementDao.updateGrpMasterDetails(grpMaster, data.user);
    }

    @Test
    public void shouldDeleteGrpMasterDetails() {
    	loadsheetManagementDao.deleteGrpMasterDetails(Arrays.asList(2), 1);
    }

    @Test
    public void shouldDeleteGrpMaster() {
    	loadsheetManagementDao.deleteGrpMaster(1);
    }

    @Test
    public void shouldUpdateCmpGrpSeqDeatils() {
    	LoadsheetCompGrpSeq cmpGrpSeq = new LoadsheetCompGrpSeq();
    	cmpGrpSeq.setGrpMasterId(1);
    	cmpGrpSeq.setComponentId("2");
    	cmpGrpSeq.setDisplaySeq(3);
    	loadsheetManagementDao.updateCmpGrpSeqDeatils(cmpGrpSeq, data.user);
    }

    @Test
    public void shouldDeleteCmpGrpSeqDetails() {
    	loadsheetManagementDao.deleteCmpGrpSeqDetails(Arrays.asList(2), 1);
    }

    @Test
    public void shouldDeleteCmpGrpSeq() {
    	loadsheetManagementDao.deleteCmpGrpSeq(1);
    }

    @Test
    public void shouldDeleteLoadsheetSequenceMaster() {
    	loadsheetManagementDao.deleteLoadsheetSequenceMaster(1);
    }

    @Test
    public void shoudlDeleteLoadsheetGroupMaster() {
    	loadsheetManagementDao.deleteLoadsheetGroupMaster(1);
    }

    @Test
    public void shouldDeleteLoadsheetGroupSequnece() {
    	loadsheetManagementDao.deleteLoadsheetGroupSequnece(1);
    }

    @Test
    public void shouldGetAllSequenceNames() {
    	loadsheetManagementDao.getAllSequenceNames(1);
    }

    @Test
    public void shouldGetSequenceCount() {
    	loadsheetManagementDao.getSequenceCount("category", "Type", "FTL", 1);
    }
    
    @Test
    public void shouldGetRulesByTemplateComponentId() {
    	loadsheetManagementDao.getRulesByTemplateComponentId(1);
    }
    
    @Test
    public void shouldGetRulesByComponentIdAndTemplateId() {
    	loadsheetManagementDao.getRulesByComponentIdAndTemplateId(1, 2);
    }

    @Test
	public void shouldSaveTemplateComponentVisibilityRules() {
    	TemplateComponentRuleAssociation templateComponentRuleAssociation = new TemplateComponentRuleAssociation();
    	templateComponentRuleAssociation.setTemplateComponentId(1);
    	templateComponentRuleAssociation.setRuleId(2);
    	templateComponentRuleAssociation.setPriority(3);
    	templateComponentRuleAssociation.setLsOverride('Y');
    	templateComponentRuleAssociation.setCreatedBy(user.getSso());
    	loadsheetManagementDao.saveTemplateComponentVisibilityRules(templateComponentRuleAssociation);
    }

    @Test
	public void shouldUpdateComponentRulePriority() {
    	loadsheetManagementDao.updateComponentRulePriority(1, 1, 3, user.getSso());
    }
    
    @Test
  	public void shouldUpdateTemplateComponentVisibilityRules() {
    	TemplateComponentRuleAssociation templateComponentRuleAssociation = new TemplateComponentRuleAssociation();
    	templateComponentRuleAssociation.setTemplateComponentId(1);
    	templateComponentRuleAssociation.setRuleId(2);
    	templateComponentRuleAssociation.setPriority(3);
    	templateComponentRuleAssociation.setLsOverride('Y');
    	templateComponentRuleAssociation.setCreatedBy(user.getSso());
    	loadsheetManagementDao.updateTemplateComponentVisibilityRules(templateComponentRuleAssociation);
    }
   
    @Test
	public void shouldGetTemplateComponentRuleVisibilty() {
    	loadsheetManagementDao.getTemplateComponentRuleVisibilty(1, 2);
    }
    
    @Test
    public void shouldGetRuleCountByTemplateComponentId() {
    	loadsheetManagementDao.getRuleCountByTemplateComponentId(1);
    }
}