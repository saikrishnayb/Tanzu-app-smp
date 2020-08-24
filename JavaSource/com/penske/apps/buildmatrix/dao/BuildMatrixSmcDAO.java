package com.penske.apps.buildmatrix.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.BusinessAwardDefault;
import com.penske.apps.buildmatrix.domain.CROBuildRequest;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.ImportSlotsHeader;
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
	
	public List<String> getDistrictsFromFreightMileage(@Param("plantId") int plantId, @Param("regionsList")List<String> regionsList);
	
	public void deleteProximityDataForRegion(@Param("plantId") int plantId, @Param("districtsList")List<String> districtsList);
	
	// ATTRIBUTE MAINTENANCE WORKFLOW //
	public List<String> getDropdownOptionGrpList();

	public List<String> getDropdownAttrValueList();
	
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void addOrUpdateAttribute(@Param("attributeId") int attributeId, @Param("attributeValue") BuildAttributeValue attributeValue);
	
    public List<String> getAllAttributeValues(@Param("attributeId") int attributeId);
    
	// PRODUCTION SLOT RESULTS //
    public List<ProductionSlotResult> getProductionSlotResults(@Param("buildId")int buildId ,@Param("slotReservationId")int slotReservationId);
    
    public List<ProductionSlotResult> getSlotResultsByFilter(@Param("buildId")int buildId, @Param("selectedFilters") List<String> selectedFilters);
    
    public List<ProductionSlotResult> getAllPlants();
    
    public List<ProductionSlotResult> getProductionSlotList(@Param("buildId") int buildId, @Param("unitNumber") String unitNumber);
    
    public void updateRunSummary(@Param("buildId") int buildId);
    
    public List<BuildMatrixSlotDate> getSlotDatesForPlant(@Param("plantId")int plantId, @Param("vehicleType") String vehicleType, @Param("region") String region);
    
    public List<String> getDebugInformation(@Param("slotReservationId") int slotReservationId, @Param("buildId") int buildId);
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	public List<BuildSummary> getAllBuildHistory();
	
	// BUILD FUNCTIONS //
	public void insertNewBuild(@Param("newBuild") BuildSummary newBuild);
	
	public void updateBuild(@Param("existingBuild") BuildSummary existingBuild);
	
	public BuildSummary getBuildSummary(@Param("buildId") Integer buildId);
	
	public void submitBuild(@Param("buildId") int buildId, @Param("status") BuildStatus submitted, @Param("sso") String userSSO);
	
	// CRO BUILD REQUESTS //
	public void insertCroBuildRequest(@Param("buildId") int buildId, @Param("order") ApprovedOrder order, @Param("unitsToConsider") int unitsToConsider);

	public void deleteCroBuildRequestsFromBuild(@Param("existingBuildId") Integer existingBuildId);

	public List<CROBuildRequest> getCroOrdersForBuild(@Param("buildId") Integer buildId);
	
	public List<CROBuildRequest> getCroOrdersForAllRuns(@Param("approvedOrders")List<ApprovedOrder> approvedOrders);
	
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
	
	public List<BuildMatrixSlotType> getVehicleTypesByIds(@Param("slotTypeIds") List<Integer> slotTypeIds);
	
	public List<Integer> getYearsforSLotMaintenance();
	
	public List<BuildMatrixBodyPlant> getAllBodyPlantsforSlotMaintenance();
	
	public int getBuildMaximumWeeksBefore();

	public int getBuildMaximumWeeksAfter();

	public void updateBuildParams(@Param("summary") BuildSummary summary);

	public List<BusinessAward> getBusinessAwards(@Param("buildId") int buildId);

	public List<RegionPlantAssociation> getAllRegionAssociationData();
	
	public List<BuildMatrixSlotRegionAvailability> getRegionAvailabilityBySlotIdsAndRegion(@Param("slotIds") Collection<Integer> slotIds, @Param("region") String region);
	
	public BuildMatrixSlotDate getSlotDate(@Param("slotDateId") int slotDateId);

	public List<ProductionSlotResult> getSlotReservationsByIdAndRegion(@Param("slotId") int slotId, @Param("region") String region);

	public List<RegionPlantAssociation> getRegionAssociationDataByRegion(@Param("region") String region);

	public List<BuildMatrixBodyPlant> getBodyPlantsByPlantIds(@Param("plantIds") Collection<Integer> plantIds);
	
	public List<BuildMatrixSlot> getSlotsBySlotDates(@Param("slotTypeId") int slotTypeId, @Param("slotDateIds") List<Integer> slotDateIds);
	
	public List<BuildMatrixSlotDate> getSlotDatesForYear(@Param("year") int year);

	public int checkSlotsExist(@Param("year") int year, @Param("slotTypeId") int slotTypeId);

	public void insertSlotDates(@Param("list") List<BuildMatrixSlotDate> slotDates);

	public void insertSlots(@Param("list") List<BuildMatrixSlot> slots);
	
	public void insertSlot(@Param("slot") BuildMatrixSlot slot);
	
	public void insertSlotRegionAvailability(@Param("regionAvailability") BuildMatrixSlotRegionAvailability regionAvailability);

	public void insertSlotRegionAvailabilities(@Param("list") List<BuildMatrixSlotRegionAvailability> regionAvailabilityList);
	
	public void deleteBuild(@Param("runId")int buildId);
	
	public void reworkBuild(@Param("runId")int buildId); 
	
	public BuildMatrixSlotType getVehicleTypeById(@Param("slotTypeId") int slotTypeId);
	
	public void removeSlotResult(@Param("slotReservationId") int slotReservationId);
	
	public void updateSlotReservations(@Param("slotReservationId") int slotReservationId, @Param("slotId") int slotId, @Param("plantId") int plantId, @Param("unitNumber") String unitNumber, @Param("sso") String sso);

	public List<Integer> getSlotIdForPlantId(@Param("plantId")int plantId);
	
	public void deleteSlotDataForRegion(@Param("slotIdList") List<Integer> slotIdList, @Param("regionsList") List<String> regionsList);

	public List<BuildMatrixBodyPlant> getBodyPlantsByMfrCode(@Param("mfrCode") String mfrCode);

	public List<BuildMatrixSlot> getSlotsBySlotDatesAndPlantIds(@Param("slotTypeId") int slotTypeId, @Param("slotDateIds") List<Integer> slotDateIds, @Param("plantIds") List<Integer> plantIds);

	public BuildMatrixBodyPlant getBodyPlantForImport(@Param("header") ImportSlotsHeader header);

	public List<BuildMatrixSlot> getSlotsBySlotIds(@Param("slotIds") Collection <Integer> slotIds);

	public void updateSlot(@Param("slot") BuildMatrixSlot slot);

	public List<BuildMatrixSlotRegionAvailability> getRegionAvailabilityBySlotRegionIds(@Param("slotRegionIds") Collection<Integer> slotRegionIds);

	public void updateRegionAvailability(@Param("regionAvailability") BuildMatrixSlotRegionAvailability regionAvailability);

	public void mergeSlotRegionAvailabilities(@Param("regionAvailabilityList") List<BuildMatrixSlotRegionAvailability> regionAvailabilities);

	public Set<Integer> getInvalidSlotIds();

	public List<BuildMatrixSlotDate> getSlotDatesByIds(@Param("slotDateIds") Collection<Integer> slotDateIds);

	public List<BuildMatrixSlotRegionAvailability> getRegionAvailabilityBySlotIds(@Param("slotIds") Collection<Integer> slotIds);

	public List<RegionPlantAssociation> getRegionAssociationByPlantIds(@Param("bodyPlantIds") Collection<Integer> list);

	public List<BuildMatrixSlot> getInvalidSlotIdsByPlantAndSlotType(@Param("slotTypeId") int slotTypeId, @Param("plantId") int plantId);
	
	public Set<Integer> getInvalidSlotTypesforPlant(@Param("plantId") int plantId);

}
