package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
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
	
	@NonVendorQuery //TODO: Review Query
	public List<ComponentVisibilityModel> getLoadsheetComponents(@Param("category") String category,@Param("type") String type);
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadsheetSequenceMaster> getLoadsheetSequences(@Param("category") String category,@Param("type") String type);
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadsheetSequenceMaster> getLoadsheetSequence();
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadsheetManagement> getLoadsheetManagementDetails();
	
	@NonVendorQuery //TODO: Review Query
	public String getUsesDefaultForCategoryAndType(@Param("category") String category,@Param("type") String type);
	
	@NonVendorQuery //TODO: Review Query
	public List<RuleMaster> getLoadsheetRules();
	
	@NonVendorQuery //TODO: Review Query
	public List<RuleMaster> getComponentRules();
	
	@NonVendorQuery //TODO: Review Query
	public List<ConfigureRule> getComponentVisibilityRules(@Param("componentVisibleId")int componentVisibleId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteComponentVisibilityRules(@Param("componentVisibilityId")int componentVisibilityId);
	
	@NonVendorQuery //TODO: Review Query
	public void saveComponentVisibilityRules(ComponentRuleAssociation componentRule);
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadSheetComponentDetails> getComponents();
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllRuleNames(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public void insertRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void insertRuleDefinitions(@Param("ruleDefList") List<RuleDefinitions> ruleDefList,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void updateRuleMasterDetails(@Param("rule") RuleMaster rule,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void updateRuleDefinitions(@Param("ruleDef") RuleDefinitions ruleDef,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public RuleMaster getRuleDetails(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteRuleDefinitions(@Param("ruleDefIdList") List<Integer> ruleDefIdList);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteRuleMasterDetails(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteRuleAssociation(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteRuleDefDetails(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadsheetManagement> getAssignedLoadsheetCategories(@Param("ruleId") int ruleId);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getCategoryList();
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getTypeList(@Param("category") String category);
	
	@NonVendorQuery //TODO: Review Query
	public List<String>  getMfrList(@Param("poCategoryType") PoCategoryType poCategoryType);
	
	@NonVendorQuery //TODO: Review Query
	public List<LoadSheetComponentDetails> getUnAssignedComponents(@Param("category") String category,@Param("type") String type);
	
	@NonVendorQuery //TODO: Review Query
	public void insertSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void insertGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void insertCmpGrpSeqDetails(@Param("cmpGrpSeqList") List<LoadsheetCompGrpSeq> cmpGrpSeqList,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public LoadsheetSequenceMaster getSequenceMasterDetails(@Param("seqMasterId") int seqMasterId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateSeqMasterDetails(@Param("seqMaster") LoadsheetSequenceMaster seqMaster,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void updateGrpMasterDetails(@Param("grpMaster") LoadsheetSequenceGroupMaster grpMaster,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteGrpMasterDetails(@Param("groupMasterIdList") List<Integer> groupMasterIdList,@Param("seqMasterId") int seqMasterId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteGrpMaster(@Param("seqMasterId") int seqMasterId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateCmpGrpSeqDeatils(@Param("cmpGrpSeq") LoadsheetCompGrpSeq cmpGrpSeq,@Param("user") UserContext user);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteCmpGrpSeqDetailsUsingGrpId(@Param("groupMasterIdList") List<Integer> groupMasterIdList);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteCmpGrpSeqDetails(@Param("compGrpSeqIdList") List<Integer> compGrpSeqIdList,@Param("grpMasterId") int grpMasterId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteCmpGrpSeq(@Param("grpMasterId") int grpMasterId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteLoadsheetSequenceMaster(@Param("sequenceId") int sequenceId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteLoadsheetGroupMaster(@Param("sequenceId") int sequenceId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteLoadsheetGroupSequnece(@Param("sequenceId") int sequenceId);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllSequenceNames(@Param("sequenceId") int sequenceId);
	
	@NonVendorQuery //TODO: Review Query
	public int getSequenceCount(@Param("category") String category,@Param("type") String type,@Param("mfr") String mfr,@Param("sequenceId") int sequenceId);
}
