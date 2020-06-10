package com.penske.apps.buildmatrix.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.BusinessAwardDefault;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixSmcDAO {
	// PLANT MAINTENANCE WORKFLOW //
	public List<FreightMileage> getFreightMileageData(int plantId);
	
	public List<PlantProximity> getPlantProximity(int plantId);
	
	public void saveDistrictProximity(@Param("plantProximityData")List<PlantProximity> plantProximityData);
	
	public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public void saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public boolean removeOfflineDates(@Param("offlineDateToRemove")List<Integer> offlineDateToRemove);
	
	public List<RegionPlantAssociation> getRegionAssociationData(int plantId);
	
	public void savePlantRegionAssociation(@Param("regionPlantAssociationData")List<RegionPlantAssociation> regionPlantAssociationList);
	
	// ATTRIBUTE MAINTENANCE WORKFLOW //
	public List<String> getDropdownOptionGrpList();

	public List<String> getDropdownAttrValueList();
	
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void addOrUpdateAttribute(@Param("attributeId") int attributeId, @Param("attributeValue") BuildAttributeValue attributeValue);
	
    public List<String> getAllAttributeValues(@Param("attributeId") int attributeId);
    
	// PRODUCTION SLOT RESULTS //
    public List<ProductionSlotResult> getProductionSlotResults(@Param("buildId")int buildId);
    
    public List<ProductionSlotResult> getSlotResultsByFilter(@Param("buildId")int buildId, @Param("selectedFilters") List<String> selectedFilters);
    
    public List<String> getAllPlants();
	
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
	
	public void deleteBusinessAwards(@Param("buildId") Integer buildId);
	
	//***** OEM MIX MAINTENANCE *****//
	public List<BusinessAwardDefault> getBusinessAwardDefaults();

	public void updateBusinessAwardDefault(@Param("awardDefault") BusinessAwardDefault awardDefault);

	public void insertBusinessAwardDefault(@Param("defaultsToInsert") List<BusinessAwardDefault> defaultsToInsert);

	//***** BUILD ATTRIBUTE *****//
	public List<BuildAttribute> getAllBuildMatrixAttributes();
	
	public List<BuildAttribute> getAttributesForBuild();
	
	public BuildAttribute getBuildAttributeById(@Param("attributeId") int attributeId);
	
	public void updateAttributeValues(@Param("attributeId") int attributeId, @Param("attributeValues") List<String> attributeValues);
	
	public String getDeletedFlagValue(@Param("attributeId") int attributeId, @Param("attributeValue") String attributeValue);
	
	// BODY PLANT EXCEPTIONS //
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();
	
	public BodyPlantCapability getAllBuildMatrixExceptions(@Param("plantId") int plantId);
	
	public void updateCapability(@Param("plantId") int plantId, @Param("attributeKey") String attributeKey, @Param("disallowedAttributeValues") String disallowedAttributeValues);

	public List<BodyPlantCapability> getAttributesbyId(int attributeId);
	
	//PRODUCTION SLOT MAINTENANCE//
	public List<BuildMatrixSlotType> getAllVehicleTypes();
	
	public List<Integer> getYearsforSLotMaintenance();
	
	public List<BuildMatrixBodyPlant> getAllBodyPlantsforSlotMaintenance();
	
	public List<BuildMatrixSlotDate> getSlotMaintenanceSummary(@Param("slotTypeId") int slotTypeId,@Param("selectedYear")int selectedYear);
	
	public int getBuildMaximumWeeksBefore();

	public int getBuildMaximumWeeksAfter();

	public void updateBuildParams(@Param("summary") BuildSummary summary);

	public List<BusinessAward> getBusinessAwards(@Param("buildId") int buildId);

	public List<RegionPlantAssociation> getAllRegionAssociationData();
	
	public List<BuildMatrixSlotRegionAvailability> getRegionAvailability(@Param("slotIds") Set<Integer> slotIds);
	
	public BuildMatrixSlotDate getSlotDate(@Param("slotDateId") int slotDateId);

	public List<String> getReservedUnitNumbers(@Param("slotId") int slotId, @Param("region") String region);
	
}
