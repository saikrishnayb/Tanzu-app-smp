package com.penske.apps.buildmatrix.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAward;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface BuildMatrixSmcService {
	
	public List<String> getAllPoCategory();
	
	public List<String> getAllOEMNames();

    public List<FreightMileage> getFreightMileageData(int plantId);
	
    public List<PlantProximity> getPlantProximity(int plantId);
    
    public void saveDistrictProximity(List<PlantProximity> plantProximityData);
    
    public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public void saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public BuildAttributeValue addOrUpdateAttribute(int attributeId, String attributeValue);
	
	public boolean checkForUniqueAttributeValue(int attributeId, String attributeValue);
	
	// PRODUCTION SLOT RESULTS //
	public List<ProductionSlotResult> getProductionSlotResults(int buildId);
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	public List<BuildSummary> getAllBuildHistory();

	// BUILD FUNCTIONS //
	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, UserContext userContext);

	public BuildSummary updateExistingBuild(Integer buildId, List<ApprovedOrder> selectedOrders);

	public BuildSummary getBuildSummary(Integer buildId);
	
	public void submitBuild(BuildMixForm buildMixForm, UserContext userContext);
	
	// CRO BUILD REQUESTS //
	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId);
	
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
	
	public List<BuildMatrixSlotDate> getSlotMaintenanceSummary(int slotTypeId,int selectedYear);

	public void updateBuildParams(BuildSummary summary);

	public Map<String, Map<String, BusinessAward>> getExistingBuildMixData(int buildId);
	
}
