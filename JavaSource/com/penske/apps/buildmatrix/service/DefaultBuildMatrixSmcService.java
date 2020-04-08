package com.penske.apps.buildmatrix.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.summingInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.buildmatrix.dao.BuildMatrixSmcDAO;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BodyPlantCapability;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixAttribute;
import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.BusinessAwardMaintenance;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.DistrictProximity;
import com.penske.apps.smccore.base.util.Util;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBuildMatrixSmcService implements BuildMatrixSmcService {

	@Autowired
	BuildMatrixSmcDAO buildMatrixSmcDAO;
	
	static List<BusinessAwardMaintenance> oemList=new ArrayList<BusinessAwardMaintenance>();
	static List<BodyPlantCapability> bodyPlantCapabilityList = new ArrayList<BodyPlantCapability>();
	static List<DistrictProximity> districtProximity = new ArrayList<DistrictProximity>();
	static List<DistrictProximity> districtProximityList = new ArrayList<DistrictProximity>();
	static List<BuildMatrixAttribute> buildMatrixAttributeList = new ArrayList<BuildMatrixAttribute>();
	
	@Override
	public List<BuildSummary> getAllBuildHistory() {
		return buildMatrixSmcDAO.getAllBuildHistory();
	}
	
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
	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId) {
		return buildMatrixSmcDAO.getCroOrderKeysForBuild(buildId);
	}
	
	@Override
	public List<BusinessAwardMaintenance> getAllOEMs()
	{
		List<BusinessAwardMaintenance> oemList=getAllOEMsMockService();
		return oemList;
	}
	
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
	public List<BusinessAwardMaintenance> getAllOEMsMockService()
	{
		oemList.clear();
		BusinessAwardMaintenance oem1=new BusinessAwardMaintenance(1, "Chassis","FTL",60);
		BusinessAwardMaintenance oem2=new BusinessAwardMaintenance(2, "Chassis","HIN",30);
		BusinessAwardMaintenance oem3=new BusinessAwardMaintenance(3, "Chassis","IHC",30);
		BusinessAwardMaintenance oem4=new BusinessAwardMaintenance(4, "Body","AMH",5);
		BusinessAwardMaintenance oem5=new BusinessAwardMaintenance(5, "Body","GDT",5);
		BusinessAwardMaintenance oem6=new BusinessAwardMaintenance(6, "Body","KID",5);
		BusinessAwardMaintenance oem7=new BusinessAwardMaintenance(7, "Body","MKY",5);
		BusinessAwardMaintenance oem8=new BusinessAwardMaintenance(8, "Body","MOR",40);
		oemList.add(oem1);
		oemList.add(oem2);
		oemList.add(oem3);
		oemList.add(oem4);
		oemList.add(oem5);
		oemList.add(oem6);
		oemList.add(oem7);
		oemList.add(oem8);
		return oemList;
	}
	
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
	public List<DistrictProximity> getDistrictProximity() {
		List<DistrictProximity> districtProximity = buildMatrixSmcDAO.getDistrictProximity();
		return districtProximity;
	}
	
	// mock service methods
	public List<DistrictProximity> getDistrictProximityMockService() {
		
		districtProximityList.clear();
		DistrictProximity dp1 = new DistrictProximity("0602 - MID ATLANTIC",1);
		DistrictProximity dp2 = new DistrictProximity("0603 - NORTHEAST",1);
		DistrictProximity dp3 = new DistrictProximity("0603 - MIDDLE SOUTH",1);
		DistrictProximity dp4 = new DistrictProximity("0607 - FLORIDA",1);
		DistrictProximity dp5 = new DistrictProximity("0608 - MIDWEST",1);
		DistrictProximity dp6 = new DistrictProximity("0609 - SOUTHWEST",1);
		DistrictProximity dp7 = new DistrictProximity("0610 - NORTH WEST",1);
		DistrictProximity dp8 = new DistrictProximity("0611 - METRO NEW YORK",1);
		DistrictProximity dp9 = new DistrictProximity("0612 - CENTRAL",1);
		DistrictProximity dp10 = new DistrictProximity("0614 - SOUTH CENTRAL",1);
		DistrictProximity dp11 = new DistrictProximity("0616 - CAROLINAS",1);
		DistrictProximity dp12 = new DistrictProximity("0617 - NORTH CENTRAL",1);
		DistrictProximity dp13 = new DistrictProximity("0621 - EASTERN CANADA",1);
		DistrictProximity dp14 = new DistrictProximity("0623 - WESTERN CANADA",1);
		DistrictProximity dp15 = new DistrictProximity("0642 - MOUNTAIN AREA",1);
		DistrictProximity dp16 = new DistrictProximity("0644 - GULF STATES AREA",1);
		
		districtProximityList.add(dp1);
		districtProximityList.add(dp2);
		districtProximityList.add(dp3);
		districtProximityList.add(dp4);
		districtProximityList.add(dp5);
		districtProximityList.add(dp6);
		districtProximityList.add(dp7);
		districtProximityList.add(dp8);
		districtProximityList.add(dp9);
		districtProximityList.add(dp10);
		districtProximityList.add(dp11);
		districtProximityList.add(dp12);
		districtProximityList.add(dp13);
		districtProximityList.add(dp14);
		districtProximityList.add(dp15);
		districtProximityList.add(dp16);
		
		return districtProximityList;
	}
	
	@Override
	public void insertProximityValues(DistrictProximity districtProximity) {
		buildMatrixSmcDAO.insertProximityValues(districtProximity);		
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
	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes() 
	{
		List<BuildMatrixAttribute> buildMatrixAttribute = buildMatrixSmcDAO.getAllBuildMatrixAttributes();
				//getAllattributesmockDataService();
		return buildMatrixAttribute;
	}

	@Override
	public BuildMatrixAttribute getAttributeDetails(int attributeId) 
	{
		BuildMatrixAttribute buildMatrixAttribute = buildMatrixSmcDAO.getAttributeDetails(attributeId);
		return buildMatrixAttribute;
	}

	/*@Override
	public List<String> getDropdownOptionGrpList() 
	{
		List<String> dropdownOptionGrpList = getMockDropdownOptionGrpList();
		// List<String> dropdownOptionGrpList=attributeDao.getDropdownOptionGrpList();;
		return dropdownOptionGrpList;
	}

	@Override
	public List<String> getDropdownAttrValueList() 
	{
		List<String> dropdownAttrValueList = getMockDropdownAttrValueList();
		//List<String> dropdownAttrValueList = attributeDao.getDropdownAttrValueList();
		return dropdownAttrValueList;
	}*/

	@Override
	public void updateAttribute(BuildMatrixAttribute attributeData)
	{
		//attributeDao.updateAttribute(attributeData); //need to confirm attribute name update
		buildMatrixSmcDAO.updateAttributeValues(attributeData);
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
	public void addAttribute(int attributeId, String attributeValue)
	{
		buildMatrixSmcDAO.addAttribute(attributeId,attributeValue);
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
	
	@Override
	public List<BuildAttribute> getAttributesForBuild() {
		return buildMatrixSmcDAO.getAttributesForBuild();
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
	
	@Override
	public int getExcludedUnitCount() {
		int year = LocalDate.now().getYear();
		return buildMatrixSmcDAO.getExcludedUnitCount(year);
	}
}
