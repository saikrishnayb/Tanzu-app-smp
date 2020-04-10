package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.DistrictProximity;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface BuildMatrixSmcService {
	
	public List<String> getAllPoCategory();
	
	public List<String> getAllOEMNames();

	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();

	public BodyPlantCapability getCapabilityDetails(int capabilityId);
	
    public List<DistrictProximity> getDistrictProximity();
	
	public void insertProximityValues(DistrictProximity districtProximity);
	
	public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public int saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes();

	public BuildMatrixAttribute getAttributeDetails(int attributeId);

	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void addAttribute(int attributeId, String attributeValue);
	
	public boolean checkForUniqueAttributeValue(int attributeId, String attributeValue);
	
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
	
	// BUSINESS AWARDS //
	
	//***** OEM MIX MAINTENANCE *****//
	public void saveBusinessAwardMaintenance(BusinessAwardForm businessAwardForm);

	BuildAttribute getBuildAttributeById(int attributeId);

}
