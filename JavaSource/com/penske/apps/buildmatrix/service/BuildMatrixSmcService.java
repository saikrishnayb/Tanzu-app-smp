package com.penske.apps.buildmatrix.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.CROBuildRequest;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.buildmatrix.model.AvailableChassisSummaryModel;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.ImportRegionSlotsResults;
import com.penske.apps.buildmatrix.model.ImportSlotsResults;
import com.penske.apps.buildmatrix.model.InvalidSlotsForm;
import com.penske.apps.buildmatrix.model.InvalidSlotsSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary;
import com.penske.apps.buildmatrix.model.SaveRegionSlotsForm;
import com.penske.apps.buildmatrix.model.SaveSlotsForm;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface BuildMatrixSmcService {
	
	public List<String> getAllPoCategory();
	
	public List<String> getAllOEMNames();

	// PLANT MAINTENANCE WORKFLOW //
    public List<FreightMileage> getFreightMileageData(int plantId);
	
    public List<PlantProximity> getPlantProximity(int plantId);
    
    public void saveDistrictProximity(List<PlantProximity> plantProximityData);
    
    public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public void saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public List<RegionPlantAssociation> getRegionAssociationData(int plantId);
	
	public void savePlantRegionAssociation(List<RegionPlantAssociation> regionPlantAssociationList);
	
	// ATTRIBUTE MAINTENANCE //
	public BuildAttributeValue addOrUpdateAttribute(int attributeId, String attributeValue);
	
	public boolean checkForUniqueAttributeValue(int attributeId, String attributeValue);
	
	// PRODUCTION SLOT RESULTS //
	public ProductionSlotResult getProductionSlotResults(int buildId,int slotReservationId);
	
	public List<ProductionSlotResult> getSlotResultsByFilter(int buildId, List<String> selectedFilters);
	
	public List<ProductionSlotResult> getAllPlantsAvailableToDistrict(String district);
	
	public List<ProductionSlotResult> getAllPlants();
	
	public List<ProductionSlotResult> getProductionSlotList(int buildId, String unitNumber);
	
	public void updateRunSummary(int buildId);
	
	public List<BuildMatrixSlotDate> getSlotDatesForPlant(int plantId, String vehicleType, String region);
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	public List<BuildSummary> getAllBuildHistory();

	// BUILD FUNCTIONS //
	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, Map<CroOrderKey, Integer> unitsToConsiderByCroOrderKey, boolean guidance, UserContext userContext);

	public BuildSummary updateExistingBuild(Integer buildId, Map<CroOrderKey, Integer> unitsToConsiderByCroOrderKey, List<ApprovedOrder> selectedOrders);

	public BuildSummary getBuildSummary(Integer buildId);
	
	public void submitBuild(BuildMixForm buildMixForm, UserContext userContext);
	
	// CRO BUILD REQUESTS //
	List<CROBuildRequest> getCroOrdersForBuild(Integer buildId);
	
	public List<ApprovedOrder> getUnfulfilledOrders(List<ApprovedOrder> approvedOrders);
	
	public Map<CroOrderKey, Pair<ApprovedOrder, CROBuildRequest>> getCroOrderMap(Map<CroOrderKey, CROBuildRequest> croBuildRequestsByOrderKey,
			List<ApprovedOrder> selectedOrders);
	
	// AVAILABLE CHASSIS //
	public int getExcludedUnitCount();
	
	public List<String> getExcludedUnits();

	public void excludeUnits(List<String> excludedUnits);

	public void deleteExcludedUnits(List<String> excludedUnits);
	
	// BUILD ATTRIBUTES //
	public List<BuildAttribute> getAttributesForBuild();
	
	public List<BuildAttribute> getAllBuildMatrixAttributes();
	
	// BUSINESS AWARDS //
	
	//***** OEM MIX MAINTENANCE *****//
	public void saveBusinessAwardMaintenance(BusinessAwardForm businessAwardForm);

	BuildAttribute getBuildAttributeById(int attributeId);

	void updateAttribute(int attributeId, List<String> attributeValues);
	
	public SXSSFWorkbook downloadProductionSlotResultsDocument(int buildId);
	
	// BODY PLANT EXCEPTIONS //
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();
	
	public List<BodyPlantCapability> getAllBuildMatrixExceptions(int plantId);

	public void updateCapability(int plantId, String attributeKey, String disalloAttributeValues);

	public List<BodyPlantCapability> getAttributesbyId(int attributeId);

	public List<BodyPlantCapability> getBodyPlantExceptionsById(int plantId, int attributeId);

	public BodyPlantCapability getAttributeValuesMap(List<BodyPlantCapability> bodyPlantCapabilityList, BodyPlantCapability bodyPlantCapability);
	
	//PRODUCTION SLOT MAINTENANCE//
	public List<BuildMatrixSlotType> getAllVehicleTypes();
	
	public List<Integer> getYearsforSLotMaintenance();
	
	public List<BuildMatrixBodyPlant> getAllBodyPlantsforSlotMaintenance();
	
	public ProductionSlotsMaintenanceSummary getSlotMaintenanceSummary(int slotTypeId,int selectedYear);

	public void updateBuildParams(BuildSummary summary);

	public Map<String, Map<String, BusinessAward>> getExistingBuildMixData(int buildId);

	public ProductionSlotsUtilizationSummary getUtilizationSummary(Integer slotTypeId, Integer year, String region);

	public BuildMatrixBodyPlant getBodyPlantById(int plantId);

	public BuildMatrixSlotDate getSlotDate(int slotDateId);

	public Map<String, RegionPlantAssociation> getRegionAssociationDataMap();

	public boolean checkSlotsExist(int year, int slotTypeId);

	public void createSlots(int year, int slotTypeId);
	
	public void deleteBuild(int buildId);
	
	public void reworkBuild(int buildId);

	public List<ProductionSlotResult> getSlotReservationsByIdAndRegion(int slotId, String region);

	public void releaseUnits(List<Integer> slotReservationIds);

	public SXSSFWorkbook exportSlotMaintenance(int year, int slotTypeId, List<Integer> plantIds);

	public BuildMatrixSlotType getVehicleTypeById(int slotTypeId);

	public Map<String, String> getMfrListForExport();

	public List<BuildMatrixBodyPlant> getBodyPlantsByMfrCode(String mfrCode);
	public ImportSlotsResults importSlotMaintenace(MultipartFile file, String fileName, int slotTypeId, int year)
			throws IOException;

	public void deleteReservationData(List<Integer> slotReservationIdList);
	
	public void saveSlots(SaveSlotsForm form);

	public void saveRegionSlots(SaveRegionSlotsForm form);

	public List<BuildMatrixBodyPlant> getBodyPlantsByPlantIds(Collection<Integer> plantIds);

	public SXSSFWorkbook exportRegionSlotMaintenance(int year, int slotTypeId, String region);

	public ImportRegionSlotsResults importRegionSlotMaintenace(MultipartFile file, String fileName, int slotTypeId,
			int year, String region) throws IOException;

	void updateReservationData(int slotReservationId, int slotId, String bodyMfr, int plantId, String unitNumber, UserContext user);
	
	public List<String> getDebugInformation(int slotReservationId, int buildId);
	
	public boolean checkUpdateResUnitNumber(String unitNumber, AvailableChassisSummaryModel summaryModel);

	public Map<Integer, Pair<BuildMatrixSlot, BuildMatrixSlotDate>> getSlotsAndSlotDatesBySlotIds(
			Set<Integer> invalidSlotIds);

	public Set<Integer> getInvalidSlotIds();

	public BuildMatrixSlot getSlotById(Integer slotId);

	public InvalidSlotsSummary getInvalidSlotSummaryForPlantAndSlotType(String plantId, String slotTypeId);

	public Set<Integer> getInvalidSlotTypesforPlant(int plantId);

	public List<BuildMatrixBodyPlant> getInvalidBodyPlantsByMfrCode(String mfrCode);

	public List<BuildMatrixSlotType> getVehicleTypeByIds(Set<Integer> slotTypeIds);

	public Map<String, String> getInvalidMfrList();

	public void saveInvalidSlots(InvalidSlotsForm invalidSlotsForm);

}
