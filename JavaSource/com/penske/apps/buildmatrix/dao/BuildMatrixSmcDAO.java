package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.BusinessAwardDefault;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixSmcDAO {
	
	public List<FreightMileage> getFreightMileageData(int plantId);
	
	public List<PlantProximity> getPlantProximity(int plantId);
	
	public void saveDistrictProximity(@Param("plantProximityData")List<PlantProximity> plantProximityData);
	
	public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public void saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public boolean removeOfflineDates(@Param("offlineDateToRemove")List<Integer> offlineDateToRemove);
	
	public List<String> getDropdownOptionGrpList();

	public List<String> getDropdownAttrValueList();
	
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void addAttribute(@Param("attributeId") int attributeId, @Param("attributeValue") BuildAttributeValue attributeValue);
	
    public List<String> getAllAttributeValues(@Param("attributeId") int attributeId);
    
	// PRODUCTION SLOT RESULTS //
    public List<ProductionSlotResult> getProductionSlotResults(@Param("buildId")int buildId);
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	public List<BuildSummary> getAllBuildHistory();
	
	// BUILD FUNCTIONS //
	public void insertNewBuild(@Param("newBuild") BuildSummary newBuild);
	
	public void updateBuild(@Param("existingBuild") BuildSummary existingBuild);
	
	public BuildSummary getBuildSummary(@Param("buildId") Integer buildId);
	
	public void submitBuild(@Param("buildId") int buildId, @Param("status") BuildStatus submitted, @Param("sso") String userSSO);
	
	// CRO BUILD REQUESTS //
	public void insertCroBuildRequest(@Param("buildId") int buildId, @Param("order") ApprovedOrder order);

	public void deleteCroBuildRequestsFromBuild(@Param("existingBuildId") Integer existingBuildId);

	public List<CroOrderKey> getCroOrderKeysForBuild(@Param("buildId") Integer buildId);
	
	// AVAILABLE CHASSIS //
	public int getExcludedUnitCount(@Param("year") int year);
	
	public List<String> getExcludedUnits(@Param("year") int year);

	public void excludeUnits(@Param("excludedUnits") List<String> excludedUnits, @Param("year") int year);

	public void deleteExcludedUnits(@Param("excludedUnits") List<String> excludedUnits, @Param("year") int year);
	
	// BUSINESS AWARDS //
	public void insertBusinessAwards(@Param("awardsToInsert") List<BusinessAward> awardsToInsert);
	
	//***** OEM MIX MAINTENANCE *****//
	public List<BusinessAwardDefault> getBusinessAwardDefaults();

	public void updateBusinessAwardDefault(@Param("awardDefault") BusinessAwardDefault awardDefault);

	public void insertBusinessAwardDefault(@Param("defaultsToInsert") List<BusinessAwardDefault> defaultsToInsert);

	//***** BUILD ATTRIBUTE *****//
	public List<BuildAttribute> getAllBuildMatrixAttributes();
	
	public List<BuildAttribute> getAttributesForBuild();
	
	public BuildAttribute getBuildAttributeById(@Param("attributeId") int attributeId);
	
	public void updateAttributeValues(@Param("attributeId") int attributeId, @Param("attrValueIds") List<Integer> attrValueIds);
	
	// BODY PLANT EXCEPTIONS //
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();
	
	public BodyPlantCapability getAllBuildMatrixExceptions(@Param("plantId") int plantId);
	
	public void updateCapability(@Param("plantId") int plantId, @Param("attributeKey") String attributeKey, @Param("disallowedAttributeValues") String disallowedAttributeValues);

	public List<BodyPlantCapability> getAttributesbyId(int attributeId);
	
}
