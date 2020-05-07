package com.penske.apps.buildmatrix.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildAttributeValue;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
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
	
	public BuildAttributeValue addAttribute(int attributeId, String attributeValue);
	
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

	void updateAttribute(int attributeId, List<Integer> attrValueIds);
	
	public Workbook downloadProductionSlotResultsDocument(int buildId) throws Exception;
	
	// BODY PLANT EXCEPTIONS //
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();
	
	public BodyPlantCapability getAllBuildMatrixExceptions(int plantId);
}
