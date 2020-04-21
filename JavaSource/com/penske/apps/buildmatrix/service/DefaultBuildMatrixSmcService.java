package com.penske.apps.buildmatrix.service;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.buildmatrix.dao.BuildMatrixSmcDAO;
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
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BuildMixForm.AttributeRow;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm.BusinessAwardRow;
import com.penske.apps.smccore.base.util.Util;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBuildMatrixSmcService implements BuildMatrixSmcService {

	@Autowired
	BuildMatrixSmcDAO buildMatrixSmcDAO;
	
	static List<BodyPlantCapability> bodyPlantCapabilityList = new ArrayList<BodyPlantCapability>();
	static List<BuildMatrixAttribute> buildMatrixAttributeList = new ArrayList<BuildMatrixAttribute>();
	
	@Override
	public List<String> getAllPoCategory()
	{
		List<String> poCategoryList=getAllPoCategoryMockService();
		return poCategoryList;
	}

	@Override
	public List<String> getAllOEMNames()
	{
		List<String> oemList=getAllOEMNamesMockService();
		return oemList;
	}
	
	// Mock service methods
	public List<String> getAllPoCategoryMockService()
	{
		return new ArrayList<String>(Arrays.asList("Truck","Chassis","Body"));
	}
	
	public List<String> getAllOEMNamesMockService()
	{
		return new ArrayList<String>(Arrays.asList("AB - AMERITRANS BUS CO","AC - ARCTIC CAT","ACC - AMERICAN COLEMAN"));
	}
	
	@Override
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities() {
		List<BodyPlantCapability> bodyPlantCapabilityList=getAllCapabilitymockDataService();
		//List<BodyPlantCapability> bodyPlantCapabilityList=buildMatrixSmcDAO.getAllBuildMatrixCapabilities();
		return bodyPlantCapabilityList;
	}
	
	@Override
	public BodyPlantCapability getCapabilityDetails(int capabilityId) {
		BodyPlantCapability capability=getCapabilityMockService(capabilityId);
		//BodyPlantCapability capability=buildMatrixSmcDAO.getCapabilityDetails(capabilityId);
		return capability;
	}
	
	//Mock service methods
	private List<BodyPlantCapability> getAllCapabilitymockDataService() {
		bodyPlantCapabilityList.clear();
		BodyPlantCapability attribute=new BodyPlantCapability(1,"BODY","Length",new ArrayList<String>(Arrays.asList("16'","18'","20'","26'")),new ArrayList<String>(Arrays.asList("16'","18'","20'")));
		BodyPlantCapability attribute1=new BodyPlantCapability(2,"BODY","Width",new ArrayList<String>(Arrays.asList("96'","102'")),new ArrayList<String>(Arrays.asList("96'")));
		BodyPlantCapability attribute2=new BodyPlantCapability(3,"BODY","Height",new ArrayList<String>(Arrays.asList("91'","97'","103'")),new ArrayList<String>(Arrays.asList("91'","97'")));
		BodyPlantCapability attribute3=new BodyPlantCapability(4,"BODY","Walkramp",new ArrayList<String>(Arrays.asList("YES","NO")),new ArrayList<String>(Arrays.asList("YES","NO")));
		BodyPlantCapability attribute4=new BodyPlantCapability(5,"BODY","Side Door",new ArrayList<String>(Arrays.asList("YES","NO")),new ArrayList<String>(Arrays.asList("YES","NO")));
		bodyPlantCapabilityList.add(attribute);
		bodyPlantCapabilityList.add(attribute1);
		bodyPlantCapabilityList.add(attribute2);
		bodyPlantCapabilityList.add(attribute3);
		bodyPlantCapabilityList.add(attribute4);
		return bodyPlantCapabilityList;
	}
	
	private BodyPlantCapability getCapabilityMockService(int capabilityId) {
		BodyPlantCapability resultCapability=null;
		for(BodyPlantCapability capability:bodyPlantCapabilityList)
		{
			if(capability.getCapabilityId()==capabilityId)
				resultCapability=capability;
		}
		return resultCapability;
	}
	
	@Override
	 public List<FreightMileage> getFreightMileageData(int plantId)
	{
		List<FreightMileage> freightMileageData = buildMatrixSmcDAO.getFreightMileageData(plantId);
		return freightMileageData;
	}
	
	@Override
	public List<PlantProximity> getPlantProximity(int plantId)
	 {
		List<PlantProximity> plantProximityList = buildMatrixSmcDAO.getPlantProximity(plantId);
		return plantProximityList;
	 }
	
	@Override
	@Transactional
	public void saveDistrictProximity(List<PlantProximity> plantProximityData)
	{
		List<PlantProximity> proximityListToInsert= plantProximityData.stream().filter(p->(!p.isRemoveDistrict())).collect(Collectors.toList());
		List<PlantProximity> ProximityListToRemove= plantProximityData.stream().filter(p->(p.isRemoveDistrict())).collect(Collectors.toList());
		if(!proximityListToInsert.isEmpty()) buildMatrixSmcDAO.insertDistrictProximity(proximityListToInsert);
		if(!ProximityListToRemove.isEmpty()) buildMatrixSmcDAO.removeDistrictProximity(ProximityListToRemove);
	}
	@Override
	public List<BuildMatrixBodyPlant> getAllBodyPlants()
	{
		List<BuildMatrixBodyPlant> bodyPlantSummary= buildMatrixSmcDAO.getAllBodyPlants();
		return bodyPlantSummary;
	}
	
	@Override
	public BuildMatrixBodyPlant getPlantData(int plantId)
	{
		return buildMatrixSmcDAO.getPlantData(plantId);
	}
	
	@Override
	public int saveOfflineDates(BuildMatrixBodyPlant plantData)
	{
		return buildMatrixSmcDAO.saveOfflineDates(plantData);
	}

	@Override
	public List<BuildAttribute> getAllBuildMatrixAttributes() 
	{
		List<BuildAttribute> buildMatrixAttribute = buildMatrixSmcDAO.getAllBuildMatrixAttributes();
		return buildMatrixAttribute;
	}

	@Override
	public void updateAttribute(int attributeId, List<Integer> attrValueIds)
	{
		//attributeDao.updateAttribute(attributeData); //need to confirm attribute name update
		buildMatrixSmcDAO.updateAttributeValues(attributeId, attrValueIds);
	}
	
	@Override
	public boolean checkForUniqueAttributeValue(int attributeId, String attributeValue){
		List<String> existingNames=null;
		boolean isUnique=true;
		existingNames = buildMatrixSmcDAO.getAllAttributeValues(attributeId);
		
		for(String name:existingNames){
			if(name.equalsIgnoreCase(attributeValue.trim())){
				isUnique=false;
			}
		}
		return isUnique;
	}
	@Override
	public BuildAttributeValue addAttribute(int attributeId, String attributeValue)
	{
		BuildAttributeValue attrValue = new BuildAttributeValue(attributeValue);
		buildMatrixSmcDAO.addAttribute(attributeId,attrValue);
		return attrValue;
	}
	
	// Mock service methods
	/*private BuildMatrixAttribute getAttributeMockService(int attributeId) {
		BuildMatrixAttribute resultAttribute = null;
		for (BuildMatrixAttribute attribute : buildMatrixAttributeList) {
			if (attribute.getAttributeId() == attributeId)
				resultAttribute = attribute;
		}
		return resultAttribute;
	}*/
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	@Override
	public List<BuildSummary> getAllBuildHistory() {
		return buildMatrixSmcDAO.getAllBuildHistory();
	}
	
	// BUILD FUNCTIONS //
	@Override
	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, UserContext userContext) {
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getOrderTotalQuantity()));
		BuildSummary newBuild = new BuildSummary(bodiesOnOrder, userContext);
		buildMatrixSmcDAO.insertNewBuild(newBuild);
		int buildId = newBuild.getBuildId();
		for(ApprovedOrder order: selectedOrders) {
			buildMatrixSmcDAO.insertCroBuildRequest(buildId, order);
		}
		return newBuild;
	}
	
	@Override
	public BuildSummary updateExistingBuild(Integer buildId, List<ApprovedOrder> selectedOrders) {
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getOrderTotalQuantity()));
		BuildSummary existingBuild = buildMatrixSmcDAO.getBuildSummary(buildId);
		existingBuild.setQuantity(bodiesOnOrder);
		buildMatrixSmcDAO.updateBuild(existingBuild);
		Integer existingBuildId = existingBuild.getBuildId();
		buildMatrixSmcDAO.deleteCroBuildRequestsFromBuild(existingBuildId);
		for(ApprovedOrder order: selectedOrders) {
			buildMatrixSmcDAO.insertCroBuildRequest(buildId, order);
		}
		return existingBuild;
	}

	@Override
	public BuildSummary getBuildSummary(Integer buildId) {
		return buildMatrixSmcDAO.getBuildSummary(buildId);
	}
	
	@Override
	public void submitBuild(BuildMixForm buildMixForm, UserContext userContext) {
		int buildId = buildMixForm.getBuildId();
		
		int itemOrder = 1;
		List<BusinessAward> awardsToInsert = new ArrayList<>();
		for(AttributeRow attributeRow: buildMixForm.getAttributeRows()) {
			BusinessAward award = new BusinessAward(buildId, attributeRow.getGroupKey(), attributeRow.getAwardKey(), itemOrder, attributeRow.getAwardPercentage(), attributeRow.getAwardQuantity());
			awardsToInsert.add(award);
		}
		
		buildMatrixSmcDAO.insertBusinessAwards(awardsToInsert);
		buildMatrixSmcDAO.submitBuild(buildId, BuildStatus.SUBMITTED, userContext.getUserSSO());
	}
	
	// CRO BUILD REQUESTS //
	@Override
	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId) {
		return buildMatrixSmcDAO.getCroOrderKeysForBuild(buildId);
	}
	
	// AVAILABLE CHASSIS //
	@Override
	public int getExcludedUnitCount() {
		int year = LocalDate.now().getYear();
		return buildMatrixSmcDAO.getExcludedUnitCount(year);
	}
	
	@Override
	public List<String> getExcludedUnits() {
		int year = LocalDate.now().getYear();
		return buildMatrixSmcDAO.getExcludedUnits(year);
	}
	
	@Override
	public void excludeUnits(List<String> excludedUnits) {
		int year = LocalDate.now().getYear();
		buildMatrixSmcDAO.excludeUnits(excludedUnits.stream().map(unit->Util.getPaddedUnitNumber(unit)).collect(toList()), year);
		
	}
	
	@Override
	public void deleteExcludedUnits(List<String> excludedUnits) {
		int year = LocalDate.now().getYear();
		buildMatrixSmcDAO.deleteExcludedUnits(excludedUnits.stream().map(unit->Util.getPaddedUnitNumber(unit)).collect(toList()), year);
	}
	
	//***** OEM MIX MAINTENANCE *****//
	@Override
	public void saveBusinessAwardMaintenance(BusinessAwardForm businessAwardForm) {
		List<BusinessAwardDefault> businessAwardDefaults = buildMatrixSmcDAO.getBusinessAwardDefaults();
		if(businessAwardDefaults ==null)
			businessAwardDefaults = Collections.emptyList();
		
		Map<Integer, BusinessAwardDefault> defaultByAttributeValueId = new HashMap<>();
		for(BusinessAwardDefault awardDefault: businessAwardDefaults) {
			defaultByAttributeValueId.put(awardDefault.getAttributeValueId(), awardDefault);
		}
		
		List<BusinessAwardDefault> defaultsToInsert = new ArrayList<>();
		
		for(BusinessAwardRow row: businessAwardForm.getBusinessAwardRows()) {
			BusinessAwardDefault awardDefault = defaultByAttributeValueId.get(row.getAttributeValueId());
			if(awardDefault == null) {
				BusinessAwardDefault addedDefault = new BusinessAwardDefault(row.getGroupId(), row.getAttributeValueId(), row.getPercentage());
				defaultsToInsert.add(addedDefault);
			}
			else {
				awardDefault.setDefaultPercentage(row.getPercentage());
				buildMatrixSmcDAO.updateBusinessAwardDefault(awardDefault);
			}
		}
		
		if(!defaultsToInsert.isEmpty())
			buildMatrixSmcDAO.insertBusinessAwardDefault(defaultsToInsert);
	}
	
	//***** BUILD ATTRIBUTE *****//
	@Override
	public List<BuildAttribute> getAttributesForBuild() {
		return buildMatrixSmcDAO.getAttributesForBuild();
	}
	
	@Override
	public BuildAttribute getBuildAttributeById(int attributeId) 
	{
		BuildAttribute buildAttribute = buildMatrixSmcDAO.getBuildAttributeById(attributeId);
		return buildAttribute;
	}
}
