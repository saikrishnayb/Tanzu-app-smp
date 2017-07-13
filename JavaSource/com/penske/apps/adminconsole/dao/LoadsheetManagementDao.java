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
import com.penske.apps.suppliermgmt.model.UserContext;

public interface LoadsheetManagementDao {
	
	public List<ComponentVisibilityModel> getLoadsheetComponents(@Param("category") String category,@Param("type") String type);
	public List<LoadsheetSequenceMaster> getLoadsheetSequences(@Param("category") String category,@Param("type") String type);
	public List<LoadsheetSequenceMaster> getLoadsheetSequence();
	public List<LoadsheetManagement> getLoadsheetManagementDetails();
	public String getUsesDefaultForCategoryAndType(@Param("category") String category,@Param("type") String type);
	public List<RuleMaster> getLoadsheetRules();
	public List<RuleMaster> getComponentRules();
	public List<ConfigureRule> getComponentVisibilityRules(@Param("componentVisibleId")int componentVisibleId);
	public void deleteComponentVisibilityRules(@Param("componentVisibilityId")int componentVisibilityId);
	public void saveComponentVisibilityRules(ComponentRuleAssociation componentRule);
	public List<LoadSheetComponentDetails> getComponents();
	public List<String> getAllRuleNames(@Param("ruleId") int ruleId);
	public void insertRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	public void insertRuleDefinitions(@Param("ruleDefList") List<RuleDefinitions> ruleDefList,@Param("user") UserContext user);
	public void updateRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	public void updateRuleDefinitions(@Param("ruleDef") RuleDefinitions ruleDef,@Param("user") UserContext user);
	public RuleMaster getRuleDetails(@Param("ruleId") int ruleId);
	public void deleteRuleDefinitions(@Param("ruleDefIdList") List<Integer> ruleDefIdList);
	public void deleteRuleMasterDetails(@Param("ruleId") int ruleId);
	public void deleteRuleAssociation(@Param("ruleId") int ruleId);
	public void deleteRuleDefDetails(@Param("ruleId") int ruleId);
	public List<LoadsheetManagement> getAssignedLoadsheetCategories(@Param("ruleId") int ruleId);
	public List<String> getCategoryList();
	public List<String> getTypeList(@Param("category") String category);
	public List<String>  getMfrList(@Param("poCategoryType") PoCategoryType poCategoryType);
	public List<LoadSheetComponentDetails> getUnAssignedComponents(@Param("category") String category,@Param("type") String type);
	public void insertSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);
	public void insertGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);
	public void insertCmpGrpSeqDetails(@Param("cmpGrpSeqList") List<LoadsheetCompGrpSeq> cmpGrpSeqList,@Param("user") UserContext user);
	public LoadsheetSequenceMaster getSequenceMasterDetails(@Param("seqMasterId") int seqMasterId);
	public void updateSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);
	public void updateGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);
	public void deleteGrpMasterDetails(@Param("groupMasterIdList") List<Integer> groupMasterIdList,@Param("seqMasterId") int seqMasterId);
	public void deleteGrpMaster(@Param("seqMasterId") int seqMasterId);
	public void updateCmpGrpSeqDeatils(@Param("cmpGrpSeq") LoadsheetCompGrpSeq cmpGrpSeq,@Param("user") UserContext user);
	public void deleteCmpGrpSeqDetailsUsingGrpId(@Param("groupMasterIdList") List<Integer> groupMasterIdList);
	public void deleteCmpGrpSeqDetails(@Param("compGrpSeqIdList") List<Integer> compGrpSeqIdList,@Param("grpMasterId") int grpMasterId);
	public void deleteCmpGrpSeq(@Param("grpMasterId") int grpMasterId);
	public void deleteLoadsheetSequenceMaster(@Param("sequenceId") int sequenceId);
	public void deleteLoadsheetGroupMaster(@Param("sequenceId") int sequenceId);
	public void deleteLoadsheetGroupSequnece(@Param("sequenceId") int sequenceId);
	public List<String> getAllSequenceNames(@Param("sequenceId") int sequenceId);
	public int getSequenceCount(@Param("category") String category,@Param("type") String type,@Param("mfr") String mfr,@Param("sequenceId") int sequenceId);
	
}
