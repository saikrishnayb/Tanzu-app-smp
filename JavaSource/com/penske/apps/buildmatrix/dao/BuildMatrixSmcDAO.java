package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAwardDefault;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.DistrictProximity;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixSmcDAO {
	
	public List<BuildSummary> getAllBuildHistory();

	public void insertNewBuild(@Param("newBuild") BuildSummary newBuild);

	public void insertCroBuildRequest(@Param("buildId") int buildId, @Param("order") ApprovedOrder order);

	public void updateBuild(@Param("existingBuild") BuildSummary existingBuild);

	public void deleteCroBuildRequestsFromBuild(@Param("existingBuildId") Integer existingBuildId);

	public BuildSummary getBuildSummary(@Param("buildId") Integer buildId);

	public List<CroOrderKey> getCroOrderKeysForBuild(@Param("buildId") Integer buildId);
	
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities();

	public BodyPlantCapability getCapabilityDetails(int capabilityId);
	
	public List<DistrictProximity> getDistrictProximity();
	
    public void insertProximityValues(DistrictProximity districtProximity);
	
	public List<BuildMatrixBodyPlant> getAllBodyPlants();
	
	public BuildMatrixBodyPlant getPlantData(int plantId);
	
	public int saveOfflineDates(BuildMatrixBodyPlant plantData);
	
	public List<String> getDropdownOptionGrpList();

	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes();

	public BuildMatrixAttribute getAttributeDetails(int attributeId);
	
	public List<String> getDropdownAttrValueList();
	
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void updateAttributeValues(BuildMatrixAttribute attributeData);
	
	public void addAttribute(@Param("attributeId") int attributeId, @Param("attributeValue") String attributeValue);
	
    public List<String> getAllAttributeValues(@Param("attributeId") int attributeId);
    
    public List<BuildAttribute> getAttributesForBuild();
    
    public List<String> getExcludedUnits(@Param("year") int year);

	public void excludeUnits(@Param("excludedUnits") List<String> excludedUnits, @Param("year") int year);

	public void deleteExcludedUnits(@Param("excludedUnits") List<String> excludedUnits, @Param("year") int year);
	
	public int getExcludedUnitCount(@Param("year") int year);

	public List<BusinessAwardDefault> getBusinessAwardDefaults();

	public void updateBusinessAwardDefault(@Param("awardDefault") BusinessAwardDefault awardDefault);

	public void insertBusinessAwardDefault(@Param("defaultsToInsert") List<BusinessAwardDefault> defaultsToInsert);
	
}
