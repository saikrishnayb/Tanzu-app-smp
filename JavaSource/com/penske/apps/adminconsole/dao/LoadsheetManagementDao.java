package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.adminconsole.model.ComponentRuleAssociation;
import com.penske.apps.adminconsole.model.ComponentVisibilityModel;
import com.penske.apps.adminconsole.model.ConfigureRule;
import com.penske.apps.adminconsole.model.LoadSheetComponentDetails;
import com.penske.apps.adminconsole.model.LoadsheetCompGrpSeq;
import com.penske.apps.adminconsole.model.LoadsheetManagement;
import com.penske.apps.adminconsole.model.LoadsheetSequenceGroupMaster;
import com.penske.apps.adminconsole.model.LoadsheetSequenceMaster;
import com.penske.apps.adminconsole.model.RuleDefinitions;
import com.penske.apps.adminconsole.model.RuleMaster;
import com.penske.apps.adminconsole.model.TemplateComponentRuleAssociation;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface LoadsheetManagementDao {

    @NonVendorQuery
    public List<ComponentVisibilityModel> getLoadsheetComponents(@Param("category") String category,@Param("type") String type);

    @NonVendorQuery
    public List<LoadsheetSequenceMaster> getLoadsheetSequences(@Param("category") String category,@Param("type") String type);

    @NonVendorQuery
    public List<LoadsheetSequenceMaster> getLoadsheetSequence();

    @NonVendorQuery
    public List<LoadsheetManagement> getLoadsheetManagementDetails();

    @NonVendorQuery
    public String getUsesDefaultForCategoryAndType(@Param("category") String category,@Param("type") String type);

    @NonVendorQuery
    public List<RuleMaster> getLoadsheetRules();

    @NonVendorQuery
    public List<RuleMaster> getComponentRules();

    @NonVendorQuery
    public List<ConfigureRule> getComponentVisibilityRules(@Param("componentVisibleId")int componentVisibleId);

    @NonVendorQuery
    public void deleteComponentVisibilityRules(@Param("componentVisibilityId")int componentVisibilityId);

    @NonVendorQuery
    public void saveComponentVisibilityRules(ComponentRuleAssociation componentRule);

    @NonVendorQuery
    public List<LoadSheetComponentDetails> getComponents();

    @NonVendorQuery
    public List<String> getAllRuleNames(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public void insertRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);

    @NonVendorQuery
    public void insertRuleDefinitions(@Param("ruleDefList") List<RuleDefinitions> ruleDefList,@Param("user") UserContext user);

    @NonVendorQuery
    public void updateRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);

    @NonVendorQuery
    public void updateRuleDefinitions(@Param("ruleDef") RuleDefinitions ruleDef,@Param("user") UserContext user);

    @NonVendorQuery
    public RuleMaster getRuleDetails(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public void deleteRuleDefinitions(@Param("ruleDefIdList") List<Integer> ruleDefIdList);

    @NonVendorQuery
    public void deleteRuleMasterDetails(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public void deleteRuleAssociation(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public void deleteRuleDefDetails(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public List<LoadsheetManagement> getAssignedLoadsheetCategories(@Param("ruleId") int ruleId);

    @NonVendorQuery
    public List<String> getCategoryList();

    @NonVendorQuery
    public List<String> getTypeList(@Param("category") String category);

    @NonVendorQuery
    public List<String>  getMfrList(@Param("poCategoryType") PoCategoryType poCategoryType);

    @NonVendorQuery
    public List<LoadSheetComponentDetails> getUnAssignedComponents(@Param("category") String category,@Param("type") String type);

    @NonVendorQuery
    public void insertSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);

    @NonVendorQuery
    public void insertGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);

    @NonVendorQuery
    public void insertCmpGrpSeqDetails(@Param("cmpGrpSeqList") List<LoadsheetCompGrpSeq> cmpGrpSeqList,@Param("user") UserContext user);

    @NonVendorQuery
    public LoadsheetSequenceMaster getSequenceMasterDetails(@Param("seqMasterId") int seqMasterId);

    @NonVendorQuery
    public void updateSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);

    @NonVendorQuery
    public void updateGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);

    @NonVendorQuery
    public void deleteGrpMasterDetails(@Param("groupMasterIdList") List<Integer> groupMasterIdList,@Param("seqMasterId") int seqMasterId);

    @NonVendorQuery
    public void deleteGrpMaster(@Param("seqMasterId") int seqMasterId);

    @NonVendorQuery
    public void updateCmpGrpSeqDeatils(@Param("cmpGrpSeq") LoadsheetCompGrpSeq cmpGrpSeq,@Param("user") UserContext user);

    @NonVendorQuery
    public void deleteCmpGrpSeqDetails(@Param("compGrpSeqIdList") List<Integer> compGrpSeqIdList,@Param("grpMasterId") int grpMasterId);

    @NonVendorQuery
    public void deleteCmpGrpSeq(@Param("grpMasterId") int grpMasterId);

    @NonVendorQuery
    public void deleteLoadsheetSequenceMaster(@Param("sequenceId") int sequenceId);

    @NonVendorQuery
    public void deleteLoadsheetGroupMaster(@Param("sequenceId") int sequenceId);

    @NonVendorQuery
    public void deleteLoadsheetGroupSequnece(@Param("sequenceId") int sequenceId);

    @NonVendorQuery
    public List<String> getAllSequenceNames(@Param("sequenceId") int sequenceId);

    @NonVendorQuery
    public int getSequenceCount(@Param("category") String category,@Param("type") String type,@Param("mfr") String mfr,@Param("sequenceId") int sequenceId);
    
    @NonVendorQuery
    public List<RuleMaster> getRulesByTemplateComponentId(@Param("templateComponentId") int templateComponentId);
    
    @NonVendorQuery
    public List<String> getRulesByComponentIdAndTemplateId(@Param("templateId") int templateId,@Param("componentId") int componentId);

    @NonVendorQuery
	public void saveTemplateComponentVisibilityRules(TemplateComponentRuleAssociation templateComponentRuleAssociation);

    @NonVendorQuery
	public void updateComponentRulePriority(@Param("templateComponentId")int templateComponentId,@Param("ruleId") Integer ruleId,@Param("priority") int priority,@Param("modifiedBy") String modifiedBy);
    
    @NonVendorQuery
  	public void updateTemplateComponentVisibilityRules(TemplateComponentRuleAssociation templateComponentRuleAssociation);
   
    @NonVendorQuery
	public TemplateComponentRuleAssociation getTemplateComponentRuleVisibilty(@Param("templateComponentId")int templateComponentId,@Param("ruleId")int ruleId);
    
    @NonVendorQuery
    public int getRuleCountByTemplateComponentId(@Param("templateComponentId") int templateComponentId);
}