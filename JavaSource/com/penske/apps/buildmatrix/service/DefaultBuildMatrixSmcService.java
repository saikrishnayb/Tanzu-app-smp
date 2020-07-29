package com.penske.apps.buildmatrix.service;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.buildmatrix.dao.BuildMatrixCroDAO;
import com.penske.apps.buildmatrix.dao.BuildMatrixSmcDAO;
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
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.FreightMileage;
import com.penske.apps.buildmatrix.domain.PlantProximity;
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.buildmatrix.domain.ReportResultOptionModel;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.BuildMatrixSlotKey;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BuildMixForm.AttributeRow;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm.BusinessAwardRow;
import com.penske.apps.buildmatrix.model.ImportRegionSlotsResults;
import com.penske.apps.buildmatrix.model.ImportSlotsHeader;
import com.penske.apps.buildmatrix.model.ImportSlotsResults;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary.ProductionSlotsMaintenanceCell;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary.ProductionSlotsMaintenanceRow;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary.ProductionSlotsUtilizationCell;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary.ProductionSlotsUtilizationRow;
import com.penske.apps.buildmatrix.model.SaveRegionSlotsForm;
import com.penske.apps.buildmatrix.model.SaveSlotsForm;
import com.penske.apps.smccore.base.util.BatchRunnable;
import com.penske.apps.smccore.base.util.Util;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBuildMatrixSmcService implements BuildMatrixSmcService {

	private static Logger logger = Logger.getLogger(DefaultBuildMatrixSmcService.class);
	
	@Autowired
	BuildMatrixSmcDAO buildMatrixSmcDAO;
	
	@Autowired
	BuildMatrixCroDAO buildMatrixCroDAO;
	
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
	
	//***** DISTRICT PROXIMITY WORKFLOW *****//
	@Override
	public List<FreightMileage> getFreightMileageData(int plantId) {
		List<FreightMileage> freightMileageData = buildMatrixSmcDAO.getFreightMileageData(plantId);
		return freightMileageData;
	}

	@Override
	public List<PlantProximity> getPlantProximity(int plantId) {
		List<PlantProximity> plantProximityList = buildMatrixSmcDAO.getPlantProximity(plantId);
		return plantProximityList;
	}
	
	@Override
	@Transactional
	public void saveDistrictProximity(List<PlantProximity> plantProximityData) {
		buildMatrixSmcDAO.saveDistrictProximity(plantProximityData);
	}
	
	//***** PLANT MAINTENANCE WORKFLOW *****//
	@Override
	public List<BuildMatrixBodyPlant> getAllBodyPlants() {
		List<BuildMatrixBodyPlant> bodyPlantSummary = buildMatrixSmcDAO.getAllBodyPlants();
		return bodyPlantSummary;
	}
	
	@Override
	public BuildMatrixBodyPlant getBodyPlantById(int plantId) {
		return buildMatrixSmcDAO.getPlantData(plantId);
	}
	
	@Override
	public BuildMatrixBodyPlant getPlantData(int plantId) {
		return buildMatrixSmcDAO.getPlantData(plantId);
	}
	
	@Transactional
	@Override
	public void saveOfflineDates(BuildMatrixBodyPlant plantData) {
		if(null!=plantData.getOfflineDateToRemove() && !plantData.getOfflineDateToRemove().isEmpty()) {
			buildMatrixSmcDAO.removeOfflineDates(plantData.getOfflineDateToRemove());
		}
		
		if(null != plantData.getOfflineDates() && !plantData.getOfflineDates().isEmpty())
			buildMatrixSmcDAO.saveOfflineDates(plantData);
	}

	@Override
	public List<RegionPlantAssociation> getRegionAssociationData(int plantId) {
		return buildMatrixSmcDAO.getRegionAssociationData(plantId);
	}

	@Override
	public void savePlantRegionAssociation(List<RegionPlantAssociation> regionPlantAssociationList) {
		List<RegionPlantAssociation> regionsToDelete = new ArrayList<RegionPlantAssociation>();
		List<RegionPlantAssociation> regionsToAdd = new ArrayList<RegionPlantAssociation>();
		int plantId = regionPlantAssociationList.get(0).getPlantId();
		List<Integer> slotIdList = buildMatrixSmcDAO.getSlotIdForPlantId(plantId);
		
		for (RegionPlantAssociation regionPlantAssociation : regionPlantAssociationList) {
			if (regionPlantAssociation.getIsAssociated().equalsIgnoreCase(ApplicationConstants.NO)) {
				regionsToDelete.add(regionPlantAssociation);
			}
			else
				regionsToAdd.add(regionPlantAssociation);
		}
		
		if (regionsToDelete != null && !regionsToDelete.isEmpty()) {
			List<String> regionsList = regionsToDelete.stream().map(rpa->rpa.getRegion()).collect(toList());
			List<String> districtsList = new ArrayList<String>();
			districtsList = buildMatrixSmcDAO.getDistrictsFromFreightMileage(plantId, regionsList);
			if (districtsList != null && !districtsList.isEmpty()) {
				buildMatrixSmcDAO.deleteProximityDataForRegion(plantId, districtsList);
			}
			if (slotIdList != null && !slotIdList.isEmpty()) {
				buildMatrixSmcDAO.deleteSlotDataForRegion(slotIdList, regionsList);
			}
		}
		buildMatrixSmcDAO.savePlantRegionAssociation(regionPlantAssociationList);
		if(regionsToAdd != null && !regionsToAdd.isEmpty()) {
			List<BuildMatrixSlotRegionAvailability> regionAvailabilities = new ArrayList<>();
			for(Integer slotId: slotIdList) {
				for(RegionPlantAssociation rpa: regionsToAdd)
					regionAvailabilities.add(new BuildMatrixSlotRegionAvailability(slotId, rpa));
			}
			new BatchRunnable<BuildMatrixSlotRegionAvailability>(regionAvailabilities, 60) {
	            @Override protected void runBatch(List<BuildMatrixSlotRegionAvailability> items){
	            	buildMatrixSmcDAO.mergeSlotRegionAvailabilities(items);
	            }
			}.run();
		}
	}

	//*****ATTRIBUTE MAINENANCE WORKFLOW *****//
	@Override
	public List<BuildAttribute> getAllBuildMatrixAttributes() {
		List<BuildAttribute> buildMatrixAttribute = buildMatrixSmcDAO.getAllBuildMatrixAttributes();
		return buildMatrixAttribute;
	}

	@Override
	public void updateAttribute(int attributeId, List<String> attributeValues) {
		buildMatrixSmcDAO.updateAttributeValues(attributeId, attributeValues);
	}
	
	@Override
	public boolean checkForUniqueAttributeValue(int attributeId, String attributeValue){
		String deletedFlagValue = null;
		boolean isUnique = true;
		deletedFlagValue = buildMatrixSmcDAO.getDeletedFlagValue(attributeId, attributeValue);
		if (deletedFlagValue != null && deletedFlagValue.equalsIgnoreCase("N")) {
				isUnique = false;
		} else {
			isUnique = true;
		}
		return isUnique;
	}
	//****PRODUCTION SLOT RESULTS WORKFLOW****//
	public ProductionSlotResult getProductionSlotResults(int buildId,int slotReservationId) {
		List<ProductionSlotResult> productionSlotResults = buildMatrixSmcDAO.getProductionSlotResults(buildId,slotReservationId);
		return productionSlotResults.get(0);
	}
	
	public List<ProductionSlotResult> getSlotResultsByFilter(int buildId, List<String> selectedFilters) {
		List<ProductionSlotResult> SlotResultsByFilter = buildMatrixSmcDAO.getSlotResultsByFilter(buildId, selectedFilters);
		return SlotResultsByFilter;
	}
	
	public List<ProductionSlotResult> getAllPlants() {
		List<ProductionSlotResult> allPlants = buildMatrixSmcDAO.getAllPlants();
		return allPlants;
	}
	
	public List<ProductionSlotResult> getProductionSlotList(int buildId, String unitNumber) {
		List<ProductionSlotResult> allPlants = buildMatrixSmcDAO.getProductionSlotList(buildId, unitNumber);
		return allPlants;
	}
	
	@Override
	public void updateRunSummary(int buildId) {
		buildMatrixSmcDAO.updateRunSummary(buildId);
	}
	
	public List<BuildMatrixSlotDate> getSlotDatesForPlant(int plantId, String vehicleType, String region) {
		List<BuildMatrixSlotDate> slotDatesList = buildMatrixSmcDAO.getSlotDatesForPlant(plantId, vehicleType, region);
		return slotDatesList;
	}
	
	@Override
	public BuildAttributeValue addOrUpdateAttribute(int attributeId, String attributeValue) {
		BuildAttributeValue attrValue = new BuildAttributeValue(attributeValue);
		buildMatrixSmcDAO.addOrUpdateAttribute(attributeId, attrValue);
		return attrValue;
	}
	
	//***** BUILD MATRIX WORKFLOW *****//
	
	// BUILD HISTROY //
	@Override
	public List<BuildSummary> getAllBuildHistory() {
		return buildMatrixSmcDAO.getAllBuildHistory();
	}
	
	// BUILD FUNCTIONS //
	@Override
	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, UserContext userContext) {
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getUnfulfilledQty()));
		int maxBeforeWeeks = buildMatrixSmcDAO.getBuildMaximumWeeksBefore();
		int maxAfterWeeks = buildMatrixSmcDAO.getBuildMaximumWeeksAfter();
		
		BuildSummary newBuild = new BuildSummary(bodiesOnOrder, userContext, maxBeforeWeeks, maxAfterWeeks);
		buildMatrixSmcDAO.insertNewBuild(newBuild);
		int buildId = newBuild.getBuildId();
		for(ApprovedOrder order: selectedOrders) {
			buildMatrixSmcDAO.insertCroBuildRequest(buildId, order);
		}
		return newBuild;
	}
	
	@Override
	public BuildSummary updateExistingBuild(Integer buildId, List<ApprovedOrder> selectedOrders) {
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getUnfulfilledQty()));
		BuildSummary existingBuild = buildMatrixSmcDAO.getBuildSummary(buildId);
		existingBuild.setReqQty(bodiesOnOrder);
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
		
		//delete any existing rows associated with this build
		buildMatrixSmcDAO.deleteBusinessAwards(buildId);
		
		buildMatrixSmcDAO.insertBusinessAwards(awardsToInsert);
		buildMatrixSmcDAO.submitBuild(buildId, BuildStatus.SUBMITTED, userContext.getUserSSO());
	}
	
	// CRO BUILD REQUESTS //
	@Override
	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId) {
		return buildMatrixSmcDAO.getCroOrderKeysForBuild(buildId);
	}
	
	@Override
	public List<ApprovedOrder> getUnfulfilledOrders(List<ApprovedOrder> approvedOrders){
		List<CROBuildRequest> croOrdersForAllRuns = buildMatrixSmcDAO.getCroOrdersForAllRuns(approvedOrders);
		for(ApprovedOrder order:approvedOrders)
		{
			for(CROBuildRequest croOrderReq: croOrdersForAllRuns)
			{
				if(order.getOrderId() == croOrderReq.getOrderId() && order.getDeliveryId() == croOrderReq.getDeliveryId())
				{
					order.setFulfilledQty(order.getFulfilledQty()+croOrderReq.getFulfilledQty());
				}
			}
		}
		approvedOrders.removeIf(order -> order.getFulfilledQty()==order.getOrderTotalQuantity());
		return approvedOrders;
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
	@Override
	public SXSSFWorkbook downloadProductionSlotResultsDocument(int buildId){
		SXSSFWorkbook workbook = null;
		List<ProductionSlotResult> productionSlotResult = buildMatrixSmcDAO.getProductionSlotResults(buildId,0);
		if (productionSlotResult != null && !productionSlotResult.isEmpty()) {
			List<Integer> orderIds = new ArrayList<>();
			for (ProductionSlotResult psr : productionSlotResult) {
				if (psr != null) {
					Integer orderId = (int) psr.getOrderId();
					if (!orderIds.contains(orderId)) {
						orderIds.add(orderId);
					}
				}
			}
			List<ReportResultOptionModel> reportResultOptions = buildMatrixCroDAO.getOrderReportOptions(orderIds);
			workbook = generateProductionSlotResultsExcel(productionSlotResult, reportResultOptions);
		}
		return workbook;
	}

	private SXSSFWorkbook generateProductionSlotResultsExcel(List<ProductionSlotResult> productionSlotResult, List<ReportResultOptionModel> reportResultOptions)
	{
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		SXSSFSheet workSheet = workbook.createSheet(ApplicationConstants.PRODUCTION_SLOT_RESULTS); // creating new work sheet
		
		workbook.setCompressTempFiles(true);
		int headerColIndex = createHeader(workbook, workSheet);

		//Create a map to hold the option group description and the column offset
		// This will allow us to map each option group to an appropriate column
		//Also cache the options by order ID, too, for faster lookup
		Map<Integer, Integer> groupAndColumn = new HashMap<Integer, Integer>();
		Map<Integer, List<ReportResultOptionModel>> optionsByOrderId = new HashMap<>();
		CellStyle cellStyle = getCellStyle(workbook);
		CellStyle changeRequired = getChangeRequiredCellStyle(workbook);
		for(ReportResultOptionModel reportResultOptionModel : reportResultOptions)
		{
			Integer groupId = reportResultOptionModel.getOptionGroupId();
			Integer orderId = reportResultOptionModel.getOrderId();
			
			//Add a new list if we don't have any for this order ID yet, and then add the option into the corresponding list.
			optionsByOrderId.computeIfAbsent(orderId, k -> new ArrayList<>()).add(reportResultOptionModel);
			
			if(groupAndColumn.containsKey(groupId))
				continue;
			
			String groupDescription = reportResultOptionModel.getOptionGroupDescription();
			
			workSheet.setColumnWidth(headerColIndex, 20 * 200);
			Cell cell = getCell(workSheet, 0, headerColIndex);
			cell.setCellValue(StringUtils.trimToEmpty(groupDescription));
			cell.setCellStyle(cellStyle);
			//Add the column index to our map of column index by option group IDs
			groupAndColumn.put(groupId, headerColIndex);
			
			headerColIndex++;
		}

		int rowId = 1;
		if (productionSlotResult == null)
			productionSlotResult = Collections.emptyList();
		for (ProductionSlotResult ProductionSlotResultData : productionSlotResult) {

			SXSSFRow dataRow = workSheet.createRow(rowId);
			int column = 0;

			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getOrderId());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getUnitNumber());
			
			SXSSFCell vehicleTypeCell = dataRow.createCell(column++);
			vehicleTypeCell.setCellValue(ProductionSlotResultData.getVehicleType());
			SXSSFCell vehicleTypeRequiredCell = dataRow.createCell(column++);
			vehicleTypeRequiredCell.setCellValue(ProductionSlotResultData.getVehicleTypeChangeRequired() != null ? ProductionSlotResultData.getVehicleTypeChangeRequired() : "");
			if(ProductionSlotResultData.getVehicleTypeChangeRequired() != null) {
				vehicleTypeCell.setCellStyle(changeRequired);
				vehicleTypeRequiredCell.setCellStyle(changeRequired);
			}
			
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getProgramName());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getRegion());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getArea());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getDistrictNumber());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getDistrictName());

			SXSSFCell datacell8 = dataRow.createCell(column++);
			if (ProductionSlotResultData.getRequestedDeliveryDate() != null
					&& !ProductionSlotResultData.getRequestedDeliveryDate().equals("")) {
				datacell8.setCellValue(ProductionSlotResultData.getRequestedDeliveryDate());
				formatDateCell(workbook, datacell8);
			}

			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getProductionSlot());

			SXSSFCell datacell10 = dataRow.createCell(column++);
			Date productionDate = ProductionSlotResultData.getProductionDate();
			if (productionDate != null) {
				datacell10.setCellValue(productionDate);
				formatDateCell(workbook, datacell10);
			}

			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getChassisMake());
			
			SXSSFCell modelCell = dataRow.createCell(column++);
			modelCell.setCellValue(ProductionSlotResultData.getChassisModel());
			SXSSFCell modelRequiredCell = dataRow.createCell(column++);
			modelRequiredCell.setCellValue(ProductionSlotResultData.getChassisModelChangeRequired() != null ? ProductionSlotResultData.getChassisModelChangeRequired() : "");
			if(ProductionSlotResultData.getChassisModelChangeRequired() != null) {
				modelCell.setCellStyle(changeRequired);
				modelRequiredCell.setCellStyle(changeRequired);
			}
			
			SXSSFCell yearCell = dataRow.createCell(column++);
			yearCell.setCellValue(ProductionSlotResultData.getChassisModelYear());
			SXSSFCell yearRequiredCell = dataRow.createCell(column++);
			yearRequiredCell.setCellValue(ProductionSlotResultData.getChassisModelYearChangeRequired() != null ? ProductionSlotResultData.getChassisModelYearChangeRequired() : "");
			if(ProductionSlotResultData.getChassisModelYearChangeRequired() != null) {
				yearCell.setCellStyle(changeRequired);
				yearRequiredCell.setCellStyle(changeRequired);
			}
			
			SXSSFCell colorCell = dataRow.createCell(column++);
			colorCell.setCellValue(ProductionSlotResultData.getChassisColor());
			SXSSFCell colorRequiredCell = dataRow.createCell(column++);
			colorRequiredCell.setCellValue(ProductionSlotResultData.getChassisColorChangeRequired() != null ? ProductionSlotResultData.getChassisColorChangeRequired() : "");
			if(ProductionSlotResultData.getChassisColorChangeRequired() != null) {
				colorCell.setCellStyle(changeRequired);
				colorRequiredCell.setCellStyle(changeRequired);
			}
			
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getBodyMake());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getChassisLength());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getRearDoorMake());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getReeferMake());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getLiftgateInstalled());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getLiftgateMake());
			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getLiftgateType());
			
			SXSSFCell wheelCell = dataRow.createCell(column++);
			wheelCell.setCellValue(ProductionSlotResultData.getWheelMaterial());
			SXSSFCell wheelRequiredCell = dataRow.createCell(column++);
			wheelRequiredCell.setCellValue(ProductionSlotResultData.getChassisWheelMatChangeRequired() != null ? ProductionSlotResultData.getChassisWheelMatChangeRequired() : "");
			if(ProductionSlotResultData.getChassisWheelMatChangeRequired() != null) {
				wheelCell.setCellStyle(changeRequired);
				wheelRequiredCell.setCellStyle(changeRequired);
			}

			//For each of the selected options, map it to the right column
			for (ReportResultOptionModel option : reportResultOptions)
			{
				if (option.getOrderId() != ProductionSlotResultData.getOrderId()) {
					continue;
				}

				int optionColumnIndex = groupAndColumn.get(option.getOptionGroupId());
				Cell cell = getCell(workSheet, rowId, optionColumnIndex);

				//If the cell is empty, just write the value
				if(StringUtils.isBlank(cell.getStringCellValue()))
					cell.setCellValue(StringUtils.trimToEmpty(option.getOptionDescription()));
				else
				{
					//Otherwise, store the value, append the extra value
					// (for multi-select groups), and write them both to the cell
					StringBuilder sb = new StringBuilder()
						.append(cell.getStringCellValue())
						.append(", ")
						.append(StringUtils.trimToEmpty(option.getOptionDescription()));
					cell.setCellValue(sb.toString());
				}
			}

			rowId++;
		}
		return workbook;
	}

	private Cell getCell(Sheet sheet, int rowIndex, int colIndex) {
		Row row = CellUtil.getRow(rowIndex, sheet);
		return CellUtil.getCell(row, colIndex);
	}

	private CellStyle getCellStyle(SXSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 10);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THICK);
		cellStyle.setBorderLeft(BorderStyle.THICK);
		cellStyle.setBorderTop(BorderStyle.THICK);
		cellStyle.setBorderRight(BorderStyle.THICK);
		cellStyle.setFont(font);
		cellStyle.setLocked(true);

		return cellStyle;
	}
	
	private CellStyle getChangeRequiredCellStyle(SXSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	private void createHeaderCell(int column, String value, CellStyle style, SXSSFRow row) {
		SXSSFCell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	/**
	 * Method for creating the header
	 * 
	 * @param workbook
	 * @param workSheet
	 */
	private int createHeader(SXSSFWorkbook workbook, SXSSFSheet workSheet) {
		SXSSFRow row = workSheet.createRow(0);
		row.setHeight((short) 550);

		//workSheet.trackAllColumnsForAutoSizing();

		CellStyle cellStyle = getCellStyle(workbook);
		int column = 0;

		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.ORDER_NUMBER, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.UNIT, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.VEHICLE_TYPE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 285);
		createHeaderCell(column++, ApplicationConstants.VEHICLE_TYPE_CHANGE_REQUIRED, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.PROGRAM_NAME, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.REGION, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.AREA, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 150);
		createHeaderCell(column++, ApplicationConstants.DISTRICT, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.DISTRICT_NAME, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.REQUESTED_DELIVERY_DATE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.PRODUCTION_SLOT, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.PRODUCTION_DATE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_MAKE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_MODEL, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_MODEL_CHANGE_REQUIRED, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_MODEL_YEAR, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 285);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_YEAR_CHANGE_REQUIRED, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_COLOR, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_COLOR_CHANGE_REQUIRED, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.BODY_MAKE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.CHASSIS_LENGTH, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.REAR_DOOR_MAKE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.REEFER_MAKE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.LIFTGATE_INSTALLED, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.LIFTGATE_MAKE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.LIFTGATE_TYPE, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 200);
		createHeaderCell(column++, ApplicationConstants.WHEEL_MATERIAL, cellStyle, row);
		workSheet.setColumnWidth(column, 20 * 256);
		createHeaderCell(column++, ApplicationConstants.WHEEL_MATERIAL_CHANGE_REQUIRED, cellStyle, row);

		return column;
	}

	/**
	 * Method for formatting the date cell
	 * 
	 * @param workbook
	 * @param cell
	 */
	private void formatDateCell(SXSSFWorkbook workbook, SXSSFCell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(ApplicationConstants.DATE_FORMAT));
		cell.setCellStyle(cellStyle);
	}

		//*****BODY PLANT EXCEPTIONS WORKFLOW *****//
	@Override
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities() {
		List<BodyPlantCapability> bodyPlantCapabilityList = buildMatrixSmcDAO.getAllBuildMatrixCapabilities();
		return bodyPlantCapabilityList;
	}
	
	@Override
	public List<BodyPlantCapability> getAllBuildMatrixExceptions(int plantId) {
		BodyPlantCapability bodyPlantCapability = buildMatrixSmcDAO.getAllBuildMatrixExceptions(plantId);
		
		List<BodyPlantCapability> bodyPlantCapabilityList = getAllBuildMatrixCapabilities();
		
		getAttributeValuesMap(bodyPlantCapabilityList, bodyPlantCapability);
		
		return bodyPlantCapabilityList;
	}
	
	@Override
	public BodyPlantCapability getAttributeValuesMap(List<BodyPlantCapability> bodyPlantCapabilityList, BodyPlantCapability bodyPlantCapability) {
		for(BodyPlantCapability plantCapability : bodyPlantCapabilityList) {
			String disallowedPlantCapability = getDisallowedPlantCapability(plantCapability.getAttributeKey(), bodyPlantCapability);
					
			List<String> disallowedPlantCapabilityList = Stream.of(disallowedPlantCapability.split(","))
					  										   .map(String::trim)
					  										   .collect(Collectors.toList());
			
			List<String> attributeValues = plantCapability.getValues();

			/*
			 * This map is prepared to show the attribute values in green or
			 * pink based on Disallowed Attributes already selected for a plant
			 */
			Map<String, Boolean> attributeValuesMap = attributeValues.stream().collect(Collectors.toMap(attributeValue -> attributeValue, 
																										attributeValue -> disallowedPlantCapabilityList.contains(attributeValue) ? true : false,
																										(oldValue, newValue) -> oldValue,
																										LinkedHashMap::new));
			plantCapability.setAttributeValuesMap(attributeValuesMap);
		}
		return bodyPlantCapability;
	}
	
	/**
	 * Since there is no mapping between OBM_ATTRIBUTE and OBM_PLANT_CAPABILITY,
	 * we've to manually identify which attribute matched with capability table columns.
	 * 
	 * If any new attribute is added, we should add a case statement
	 * 
	 * @param attributeKey
	 * @param bodyPlantCapability
	 * @return
	 */
	private String getDisallowedPlantCapability(String attributeKey, BodyPlantCapability bodyPlantCapability) {
		String disallowedValue = "";
		
		if(bodyPlantCapability != null){
		switch (attributeKey) {
		case "WHEELBASE":
			disallowedValue = bodyPlantCapability.getWheelbase();
			break;
		case "GVW":
			disallowedValue = bodyPlantCapability.getGvw();
			break;
		case "WHEELMATERIAL":
			disallowedValue = bodyPlantCapability.getWheelMaterial();
			break;
		case "COLOR":
			disallowedValue = bodyPlantCapability.getChassisColor();
			break;
		case "FUELTYPE":
			disallowedValue = bodyPlantCapability.getFuelType();
			break;
		case "LIFTGATEMAKE":
			disallowedValue = bodyPlantCapability.getLiftgateMake();
			break;
		case "LIFTGATETYPE":
			disallowedValue = bodyPlantCapability.getLiftgateType();
			break;
		case "CHASSISMAKE":
			disallowedValue = bodyPlantCapability.getChassisMake();
			break;
		case "REARDOORMAKE":
			disallowedValue = bodyPlantCapability.getRearDoorMake();
			break;
		case "VEHICLETYPE":
			disallowedValue = bodyPlantCapability.getVehicleType();
			break;
		case "REARDOORINSTALLED":
			disallowedValue = bodyPlantCapability.getRearDoorInstalled();
			break;
		case "LIFTGATEINSTALLED":
			disallowedValue = bodyPlantCapability.getLiftgateInstalled();
			break;
		case "REEFERINSTALLED":
			disallowedValue = bodyPlantCapability.getReeferInstalled();
			break;
		case "BODYMAKE":
			disallowedValue = bodyPlantCapability.getBodyMake();
			break;
		case "CHASSISLENGTH":
			disallowedValue = bodyPlantCapability.getChassisLength();
			break;
		case "CORP":
			disallowedValue = bodyPlantCapability.getCorp();
			break;
		case "CHASSISMODEL":
			disallowedValue = bodyPlantCapability.getChassisModel();
			break;
		case "CHASSISMODELYEAR":
			disallowedValue = bodyPlantCapability.getChassisModelYear();
			break;
		case "TRANSMISSIONMAKE":
			disallowedValue = bodyPlantCapability.getTransmissionMake();
			break;
		case "REEFERMAKE":
			disallowedValue = bodyPlantCapability.getReeferMake();
			break;
		case "SIDEDOORINSTALLED":
			disallowedValue = bodyPlantCapability.getSideDoorInstalled();
			break;
		case "BRAKETYPE":
			disallowedValue = bodyPlantCapability.getBreakType();
			break;
		case "SUSPENSIONTYPE":
			disallowedValue = bodyPlantCapability.getSuspensionType();
			break;
		}
		
		}
		return disallowedValue != null ? disallowedValue : "";
	}
	
	public void updateCapability(int plantId, String attributeKey, String disallowedAttributeValues) {
		buildMatrixSmcDAO.updateCapability(plantId, attributeKey, disallowedAttributeValues);
	}

	@Override
	public List<BodyPlantCapability> getAttributesbyId(int attributeId) {
		return buildMatrixSmcDAO.getAttributesbyId(attributeId);
	}

	@Override
	public List<BodyPlantCapability> getBodyPlantExceptionsById(int plantId, int attributeId) {
		BodyPlantCapability bodyPlantCapability = buildMatrixSmcDAO.getAllBuildMatrixExceptions(plantId);
		List<BodyPlantCapability> bodyPlantCapabilityList = getAttributesbyId(attributeId);
		getAttributeValuesMap(bodyPlantCapabilityList, bodyPlantCapability);
		return bodyPlantCapabilityList;
	}
	
	// PRODUCTION SLOT MAINTENANCE//
	public List<BuildMatrixSlotType> getAllVehicleTypes() {
		List<BuildMatrixSlotType> vehicleTypes=buildMatrixSmcDAO.getAllVehicleTypes();
		return vehicleTypes;
	}
	
	public List<Integer> getYearsforSLotMaintenance(){
		List<Integer> years=buildMatrixSmcDAO.getYearsforSLotMaintenance();
		return years;
	}
	
	public List<BuildMatrixBodyPlant> getAllBodyPlantsforSlotMaintenance(){
		List<BuildMatrixBodyPlant> bodyPlantSummary = buildMatrixSmcDAO.getAllBodyPlantsforSlotMaintenance();
		return bodyPlantSummary;
	}
	
	public ProductionSlotsMaintenanceSummary getSlotMaintenanceSummary(int slotTypeId,int selectedYear){
		List<BuildMatrixSlotDate> slotDates = buildMatrixSmcDAO.getSlotDatesForYear(selectedYear);
		List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDates.isEmpty() || slotDates == null) {
			slotDates = Collections.emptyList();
			slots = Collections.emptyList();
		}
		else {
			slots = buildMatrixSmcDAO.getSlotsBySlotDates(slotTypeId, slotDates.stream().map(BuildMatrixSlotDate::getSlotDateId).collect(toList()));
		}
			
		List<BuildMatrixBodyPlant> bodyPlantList = buildMatrixSmcDAO.getAllBodyPlantsforSlotMaintenance();
		ProductionSlotsMaintenanceSummary summary = new ProductionSlotsMaintenanceSummary(bodyPlantList, slotDates, slots, false);
		return summary;
	}
	
	@Override
	public void updateBuildParams(BuildSummary summary) {
		buildMatrixSmcDAO.updateBuildParams(summary);	
	}
	
	@Override
	public Map<String, Map<String, BusinessAward>> getExistingBuildMixData(int buildId) {
		List<BusinessAward> businessAwards = buildMatrixSmcDAO.getBusinessAwards(buildId);
		Map<String, Map<String, BusinessAward>> buildMixMap = new HashMap<>();
		
		for(BusinessAward award: businessAwards) {
			Map<String, BusinessAward> awardByAwardKey = buildMixMap.computeIfAbsent(award.getGroupKey(), map-> new HashMap<>());
			awardByAwardKey.put(award.getAttributeValueKey(), award);
			buildMixMap.put(award.getGroupKey(), awardByAwardKey);
		}
		
		return buildMixMap;
	}
	
	@Override
	public ProductionSlotsUtilizationSummary getUtilizationSummary(Integer slotTypeId, Integer selectedYear, String region) {
		List<RegionPlantAssociation> regionPlantList = buildMatrixSmcDAO.getRegionAssociationDataByRegion(region);
		List<BuildMatrixBodyPlant> bodyPlantSummary = new ArrayList<>();
		if(regionPlantList.isEmpty() || regionPlantList == null) {
			regionPlantList = Collections.emptyList();
			bodyPlantSummary = Collections.emptyList();
		}
		else {
			bodyPlantSummary = buildMatrixSmcDAO.getBodyPlantsByPlantIds(regionPlantList.stream().map(RegionPlantAssociation::getPlantId).collect(toList()));
		}
		 
		List<BuildMatrixSlotDate> slotDates = buildMatrixSmcDAO.getSlotDatesForYear(selectedYear);
		List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDates.isEmpty() || slotDates == null) {
			slotDates = Collections.emptyList();
			slots = Collections.emptyList();
		}
		else {
			List<Integer> slotDateIds = slotDates.stream().map(sld->sld.getSlotDateId()).collect(toList());
			List<Integer> plantIds = bodyPlantSummary.stream().map(bp->bp.getPlantId()).collect(toList());
			slots = buildMatrixSmcDAO.getSlotsBySlotDatesAndPlantIds(slotTypeId, slotDateIds, plantIds);
		}
		
		Set<Integer> slotIds = slots.stream().map(BuildMatrixSlot::getSlotId).collect(toSet());
		List<BuildMatrixSlotRegionAvailability> regionAvailability = new ArrayList<>();
		if(slots.isEmpty() || slots == null)
			regionAvailability = Collections.emptyList();
		else
			regionAvailability = buildMatrixSmcDAO.getRegionAvailabilityBySlotIdsAndRegion(slotIds, region);
		ProductionSlotsUtilizationSummary summary = new ProductionSlotsUtilizationSummary(bodyPlantSummary, slotDates, regionAvailability, slots, false);
		
		return summary;
	}
	
	@Override
	public BuildMatrixSlotDate getSlotDate(int slotDateId) {
		return buildMatrixSmcDAO.getSlotDate(slotDateId);
	}
	
	@Override
	public List<ProductionSlotResult> getSlotReservationsByIdAndRegion(int slotId, String region) {
		return buildMatrixSmcDAO.getSlotReservationsByIdAndRegion(slotId, region);
	}
	
	@Override
	public Map<String, RegionPlantAssociation> getRegionAssociationDataMap() {
		List<RegionPlantAssociation> regionPlantList = buildMatrixSmcDAO.getAllRegionAssociationData();
		Map<String, RegionPlantAssociation> regionMap = new HashMap<>();
		for(RegionPlantAssociation rpa: regionPlantList) {
			if(regionMap.containsKey(rpa.getRegion()))
				continue;
			else
				regionMap.put(rpa.getRegion(), rpa);
		}
		return regionMap;
	}
	@Override
	public void deleteBuild(int buildId) {
		buildMatrixSmcDAO.deleteBuild(buildId);
	}
	
	@Override
	public void reworkBuild(int buildId) {
		buildMatrixSmcDAO.reworkBuild(buildId);
	}
	@Override
	public boolean checkSlotsExist(int year, int slotTypeId) {
		int count = buildMatrixSmcDAO.checkSlotsExist(year, slotTypeId);
		if(count > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void createSlots(int year, int slotTypeId) {
		BuildMatrixSlotType slotType = buildMatrixSmcDAO.getVehicleTypeById(slotTypeId);
		
		List<BuildMatrixSlotDate> slotDatesForYear = buildMatrixSmcDAO.getSlotDatesForYear(year);
		if(slotDatesForYear == null || slotDatesForYear.isEmpty()) {
			slotDatesForYear = new ArrayList<>();
			WeekFields weekFields = WeekFields.ISO;
			LocalDate date = LocalDate.now().with(IsoFields.WEEK_BASED_YEAR, year)
	                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 1)
	                .with(ChronoField.DAY_OF_WEEK, 1);
			long maxWeekOfYear = weekFields.weekOfWeekBasedYear().rangeRefinedBy(date).getMaximum();
			
			List<LocalDate> dates = new ArrayList<>();
			dates.add(date);
			for(int i=1; i<maxWeekOfYear; i++) {
				LocalDate newDate = date;
				dates.add(newDate.plusWeeks(i));
			}
			
			for(LocalDate localDate: dates) {
				slotDatesForYear.add(new BuildMatrixSlotDate(localDate, year));
			}
			buildMatrixSmcDAO.insertSlotDates(slotDatesForYear);
		}
		
		List<BuildMatrixBodyPlant> plants = buildMatrixSmcDAO.getAllBodyPlantsforSlotMaintenance();
		List<BuildMatrixSlot> slots = new ArrayList<>();
		for(BuildMatrixSlotDate slotDate: slotDatesForYear) {
			for(BuildMatrixBodyPlant plant: plants) {
				slots.add(new BuildMatrixSlot(slotType, slotDate, plant));
			}
		}
		new BatchRunnable<BuildMatrixSlot>(slots, 500) {
            @Override protected void runBatch(List<BuildMatrixSlot> items){
            	buildMatrixSmcDAO.insertSlots(items);
            }
		}.run();
		
		
		Map<Integer, List<BuildMatrixSlot>> slotsByPlantId = new HashMap<>();
		for(BuildMatrixSlot slot: slots) {
			List<BuildMatrixSlot> list = slotsByPlantId.computeIfAbsent(slot.getPlantId(), l -> new ArrayList<>());
			list.add(slot);
		}
		
		List<RegionPlantAssociation> associationList = buildMatrixSmcDAO.getAllRegionAssociationData();
		List<BuildMatrixSlotRegionAvailability> regionAvailabilityList = new ArrayList<>();
		for(RegionPlantAssociation assoc: associationList) {
			List<BuildMatrixSlot> slotList = slotsByPlantId.get(assoc.getPlantId());
			if(slotList != null) {
				for(BuildMatrixSlot slot: slotList) {
					regionAvailabilityList.add(new BuildMatrixSlotRegionAvailability(slot.getSlotId(), assoc));
				}
			}
		}
		
		new BatchRunnable<BuildMatrixSlotRegionAvailability>(regionAvailabilityList, 500) {
            @Override protected void runBatch(List<BuildMatrixSlotRegionAvailability> items){
            	buildMatrixSmcDAO.insertSlotRegionAvailabilities(items);
            }
		}.run();
		
	}
	
	@Override
	public void releaseUnits(List<Integer> slotReservationIds) {
		for(Integer slotReservationId: slotReservationIds) {
			buildMatrixSmcDAO.removeSlotResult(slotReservationId);
		}
	}
	
	@Override
	public BuildMatrixSlotType getVehicleTypeById(int slotTypeId) {
		return buildMatrixSmcDAO.getVehicleTypeById(slotTypeId);
	}
	
	@Override
	public Map<String, String> getMfrListForExport() {
		List<BuildMatrixBodyPlant> bodyPlantList  = buildMatrixSmcDAO.getAllBodyPlantsforSlotMaintenance();
		Map<String, String> mfrMap = new HashMap<>();
		
		for(BuildMatrixBodyPlant plant: bodyPlantList) {
			if(!mfrMap.containsKey(plant.getPlantMfrCode()))
					mfrMap.put(plant.getPlantMfrCode(), plant.getPlantManufacturer());
		}

		return mfrMap;
	}
	
	@Override
	public List<BuildMatrixBodyPlant> getBodyPlantsByMfrCode(String mfrCode) {
		return buildMatrixSmcDAO.getBodyPlantsByMfrCode(mfrCode);
	}
	
	@Override
	public SXSSFWorkbook exportSlotMaintenance(int year, int slotTypeId, List<Integer> plantIds) {
		List<BuildMatrixSlotDate> slotDates = buildMatrixSmcDAO.getSlotDatesForYear(year);
		List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDates.isEmpty() || slotDates == null) {
			slotDates = Collections.emptyList();
			slots = Collections.emptyList();
		}
		else {
			slots = buildMatrixSmcDAO.getSlotsBySlotDatesAndPlantIds(slotTypeId, slotDates.stream().map(BuildMatrixSlotDate::getSlotDateId).collect(toList()), plantIds);
		}
			
		List<BuildMatrixBodyPlant> bodyPlantList = buildMatrixSmcDAO.getBodyPlantsByPlantIds(plantIds);
		ProductionSlotsMaintenanceSummary summary = new ProductionSlotsMaintenanceSummary(bodyPlantList, slotDates, slots, false);
		
		return generateSlotMaintenanceExcel(summary, bodyPlantList);
	}
	
	private SXSSFWorkbook generateSlotMaintenanceExcel(ProductionSlotsMaintenanceSummary summary, List<BuildMatrixBodyPlant> bodyPlantList) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		SXSSFSheet workSheet = workbook.createSheet(ApplicationConstants.SLOT_MAINTENANCE_RESULTS); // creating new work sheet
		
		workbook.setCompressTempFiles(true);
		int headerColIndex = 0;
		CellStyle cellStyle = getCellStyle(workbook);
		CellStyle unlocked = workbook.createCellStyle();
		unlocked.setLocked(false);
		
		workSheet.setColumnWidth(headerColIndex, 20 * 300);
		Cell dateHeaderCell = getCell(workSheet, 0, headerColIndex);
		dateHeaderCell.setCellValue("Production Date");
		dateHeaderCell.setCellStyle(cellStyle);
		headerColIndex++;
		
		for(BuildMatrixBodyPlant plantData: bodyPlantList)
		{
			String cellContents = plantData.getPlantManufacturer() + " - " + plantData.getCity() + ", " + plantData.getState();
			workSheet.setColumnWidth(headerColIndex, 20 * 300);
			Cell cell = getCell(workSheet, 0, headerColIndex);
			cell.setCellValue(cellContents);
			cell.setCellStyle(cellStyle);
			//Add the column index to our map of column index by option group IDs
			
			headerColIndex++;
		}
		
		int rowId = 1;
		for (ProductionSlotsMaintenanceRow slotRow : summary.getRows()) {

			SXSSFRow dataRow = workSheet.createRow(rowId);
			int column = 0;
			
			SXSSFCell dateCell = dataRow.createCell(column++);
			dateCell.setCellStyle(cellStyle);
			dateCell.setCellValue(slotRow.getSlotDate().getFormattedSlotDate());
			
			for(ProductionSlotsMaintenanceCell slotCell : slotRow.getCells()) {
				SXSSFCell cell  = dataRow.createCell(column++);
				cell.setCellValue(slotCell.getSlot().getAvailableSlots());
				cell.setCellStyle(unlocked);
				
			}
			
			rowId++;
		}
		
		workSheet.protectSheet("SMCAdm!n"); 
		
		return workbook;
	}
	
	@Override
	public SXSSFWorkbook exportRegionSlotMaintenance(int year, int slotTypeId, String region) {
		List<RegionPlantAssociation> regionPlantList = buildMatrixSmcDAO.getRegionAssociationDataByRegion(region);
		List<BuildMatrixBodyPlant> bodyPlantSummary = new ArrayList<>();
		if(regionPlantList.isEmpty() || regionPlantList == null) {
			regionPlantList = Collections.emptyList();
			bodyPlantSummary = Collections.emptyList();
		}
		else {
			bodyPlantSummary = buildMatrixSmcDAO.getBodyPlantsByPlantIds(regionPlantList.stream().map(RegionPlantAssociation::getPlantId).collect(toList()));
		}
		 
		List<BuildMatrixSlotDate> slotDates = buildMatrixSmcDAO.getSlotDatesForYear(year);
		List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDates.isEmpty() || slotDates == null) {
			slotDates = Collections.emptyList();
			slots = Collections.emptyList();
		}
		else {
			List<Integer> slotDateIds = slotDates.stream().map(sld->sld.getSlotDateId()).collect(toList());
			List<Integer> plantIds = bodyPlantSummary.stream().map(bp->bp.getPlantId()).collect(toList());
			slots = buildMatrixSmcDAO.getSlotsBySlotDatesAndPlantIds(slotTypeId, slotDateIds, plantIds);
		}
		
		Set<Integer> slotIds = slots.stream().map(BuildMatrixSlot::getSlotId).collect(toSet());
		List<BuildMatrixSlotRegionAvailability> regionAvailability = new ArrayList<>();
		if(slots.isEmpty() || slots == null)
			regionAvailability = Collections.emptyList();
		else
			regionAvailability = buildMatrixSmcDAO.getRegionAvailabilityBySlotIdsAndRegion(slotIds, region);
		ProductionSlotsUtilizationSummary summary = new ProductionSlotsUtilizationSummary(bodyPlantSummary, slotDates, regionAvailability, slots, false);
		
		return generateRegionSlotMaintenanceExcel(summary, bodyPlantSummary);
	}
	
	private SXSSFWorkbook generateRegionSlotMaintenanceExcel(ProductionSlotsUtilizationSummary summary, List<BuildMatrixBodyPlant> bodyPlantList) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		SXSSFSheet workSheet = workbook.createSheet(ApplicationConstants.SLOT_MAINTENANCE_RESULTS); // creating new work sheet
		
		workbook.setCompressTempFiles(true);
		int headerColIndex = 0;
		CellStyle cellStyle = getCellStyle(workbook);
		CellStyle unlocked = workbook.createCellStyle();
		unlocked.setLocked(false);
		
		workSheet.setColumnWidth(headerColIndex, 20 * 300);
		Cell dateHeaderCell = getCell(workSheet, 0, headerColIndex);
		dateHeaderCell.setCellValue("Production Date");
		dateHeaderCell.setCellStyle(cellStyle);
		headerColIndex++;
		
		for(BuildMatrixBodyPlant plantData: bodyPlantList)
		{
			String cellContents = plantData.getPlantManufacturer() + " - " + plantData.getCity() + ", " + plantData.getState();
			workSheet.setColumnWidth(headerColIndex, 20 * 300);
			Cell cell = getCell(workSheet, 0, headerColIndex);
			cell.setCellValue(cellContents);
			cell.setCellStyle(cellStyle);
			//Add the column index to our map of column index by option group IDs
			
			headerColIndex++;
		}
		
		int rowId = 1;
		for (ProductionSlotsUtilizationRow slotRow : summary.getRows()) {

			SXSSFRow dataRow = workSheet.createRow(rowId);
			int column = 0;
			
			SXSSFCell dateCell = dataRow.createCell(column++);
			dateCell.setCellStyle(cellStyle);
			dateCell.setCellValue(slotRow.getSlotDate().getFormattedSlotDate());
			
			for(ProductionSlotsUtilizationCell slotCell : slotRow.getCells()) {
				SXSSFCell cell  = dataRow.createCell(column++);
				cell.setCellValue(slotCell.getRegionAvailability().getSlotAvailable());
				cell.setCellStyle(unlocked);
				
			}
			
			rowId++;
		}
		
		workSheet.protectSheet("SMCAdm!n"); 
		
		return workbook;
	}

	@Override
	public ImportSlotsResults importSlotMaintenace(MultipartFile file, String fileName, int slotTypeId, int year) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	    XSSFSheet worksheet = workbook.getSheetAt(0);
	    
	    Map<Integer, ImportSlotsHeader> headerByColIndex = new HashMap<>();
	    Map<String, Map<Integer, Integer>> availableSlotsByColIndexByDate = new HashMap<>();
	    
	    for(int i=0; i<=worksheet.getLastRowNum(); i++) {
	    	XSSFRow row = worksheet.getRow(i);
	    	Map<Integer, Integer> availableSlotsByColIndex = new HashMap<>();
	    	for(int j=0; j<row.getLastCellNum(); j++) {
	    		XSSFCell cell = row.getCell(j);
	    		if(i==0) {
	    			if(j==0)
	    				continue;
	    			else {
	    				String cellValue = cell.getStringCellValue();
	    				String[] strArray = cellValue.split("-");
	    				String manufacturer = strArray[0].trim();
	    				String[] cityState = strArray[1].trim().split(",");
	    				String city = cityState[0].trim();
	    				String state = cityState[1].trim();
	    				
	    				headerByColIndex.put(j, new ImportSlotsHeader(manufacturer, city, state));
	    				
	    			}
	    		}
	    		else {
	    			if(j==0)
	    				continue;
	    			else {
	    				availableSlotsByColIndex.put(j, (int) cell.getNumericCellValue());
	    			}
	    		}
	    	}
	    	if(i!=0)
	    		availableSlotsByColIndexByDate.put(row.getCell(0).getStringCellValue().trim(), availableSlotsByColIndex);    
	    }
	    
	    workbook.close();
	    
	    Map<Integer, BuildMatrixBodyPlant> bodyPlantByColIndex = new HashMap<>();
	    Map<Integer, ImportSlotsHeader> plantsNotFound = new HashMap<>();
	    for(Entry<Integer,ImportSlotsHeader> entry: headerByColIndex.entrySet()) {
	    	BuildMatrixBodyPlant plant = buildMatrixSmcDAO.getBodyPlantForImport(entry.getValue());
	    	if(plant == null)
	    		plantsNotFound.put(entry.getKey(), entry.getValue());
	    	else
	    		bodyPlantByColIndex.put(entry.getKey(), plant);
	    }
	    
	    List<BuildMatrixSlotDate> slotDatesForYear = buildMatrixSmcDAO.getSlotDatesForYear(year);
	    Map<String, BuildMatrixSlotDate> slotDatesByYearStrings = slotDatesForYear.stream().collect(toMap(BuildMatrixSlotDate::getFormattedSlotDate, sd -> sd));
	    List<String> datesNotInYear = new ArrayList<>();
	    Map<BuildMatrixSlotKey, Integer> availableUnitsBySlotKey = new HashMap<>();
	    for(Entry<String, Map<Integer, Integer>> entry: availableSlotsByColIndexByDate.entrySet()) {
	    	if(!slotDatesByYearStrings.keySet().contains(entry.getKey())) {
	    		datesNotInYear.add(entry.getKey());
	    	}
	    	else {
	    		BuildMatrixSlotDate slotDate = slotDatesByYearStrings.get(entry.getKey());
	    		for(Entry<Integer, Integer> innerEntry: entry.getValue().entrySet()) {
	    			if(!plantsNotFound.keySet().contains(innerEntry.getKey())) {
	    				BuildMatrixBodyPlant bodyPlant = bodyPlantByColIndex.get(innerEntry.getKey());
	    				availableUnitsBySlotKey.put(new BuildMatrixSlotKey(slotDate.getSlotDateId(), bodyPlant.getPlantId(), slotTypeId), innerEntry.getValue());
	    			}
	    		}
	    	}
	    }
	    
	    List<Integer> slotDateIds = availableUnitsBySlotKey.keySet().stream().map(BuildMatrixSlotKey::getSlotDateId).collect(toList());
	    List<Integer> plantIds = availableUnitsBySlotKey.keySet().stream().map(BuildMatrixSlotKey::getPlantId).collect(toList());
	    List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDateIds.isEmpty() || slotDateIds == null || plantIds.isEmpty() || plantIds == null)
			slots = Collections.emptyList();
		else	
			slots = buildMatrixSmcDAO.getSlotsBySlotDatesAndPlantIds(slotTypeId, slotDateIds, plantIds);
		if(!slots.isEmpty()) {
			for(BuildMatrixSlot slot: slots) {
				Integer newAvailableSlots = availableUnitsBySlotKey.get(new BuildMatrixSlotKey(slot.getSlotDateId(), slot.getPlantId(), slot.getSlotTypeId()));
				slot.updateAvailableSlots(newAvailableSlots, true);
			}
		}
		
		List<BuildMatrixBodyPlant> bodyPlantList = new ArrayList<>(bodyPlantByColIndex.values());
		ProductionSlotsMaintenanceSummary summary = new ProductionSlotsMaintenanceSummary(bodyPlantList, slotDatesForYear, slots, true);
		ImportSlotsResults results = new ImportSlotsResults(summary, bodyPlantList, datesNotInYear, plantsNotFound);
		
		return results;
	}
	
	@Override
	public ImportRegionSlotsResults importRegionSlotMaintenace(MultipartFile file, String fileName, int slotTypeId,
			int year, String region) throws IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	    XSSFSheet worksheet = workbook.getSheetAt(0);
	    
	    Map<Integer, ImportSlotsHeader> headerByColIndex = new HashMap<>();
	    Map<String, Map<Integer, Integer>> availableSlotsByColIndexByDate = new HashMap<>();
	    
	    for(int i=0; i<=worksheet.getLastRowNum(); i++) {
	    	XSSFRow row = worksheet.getRow(i);
	    	Map<Integer, Integer> availableSlotsByColIndex = new HashMap<>();
	    	for(int j=0; j<row.getLastCellNum(); j++) {
	    		XSSFCell cell = row.getCell(j);
	    		if(i==0) {
	    			if(j==0)
	    				continue;
	    			else {
	    				String cellValue = cell.getStringCellValue();
	    				String[] strArray = cellValue.split("-");
	    				String manufacturer = strArray[0].trim();
	    				String[] cityState = strArray[1].trim().split(",");
	    				String city = cityState[0].trim();
	    				String state = cityState[1].trim();
	    				
	    				headerByColIndex.put(j, new ImportSlotsHeader(manufacturer, city, state));
	    				
	    			}
	    		}
	    		else {
	    			if(j==0)
	    				continue;
	    			else {
	    				availableSlotsByColIndex.put(j, (int) cell.getNumericCellValue());
	    			}
	    		}
	    	}
	    	if(i!=0)
	    		availableSlotsByColIndexByDate.put(row.getCell(0).getStringCellValue().trim(), availableSlotsByColIndex);    
	    }
	    
	    workbook.close();
	    
	    List<RegionPlantAssociation> regionAssociations = buildMatrixSmcDAO.getRegionAssociationDataByRegion(region);
	    Map<Integer, RegionPlantAssociation> associationByPlantId = regionAssociations.stream().collect(toMap(RegionPlantAssociation::getPlantId, ras -> ras));
	    
	    Map<Integer, BuildMatrixBodyPlant> bodyPlantByColIndex = new HashMap<>();
	    Map<Integer, ImportSlotsHeader> plantsNotFound = new HashMap<>();
	    for(Entry<Integer,ImportSlotsHeader> entry: headerByColIndex.entrySet()) {
	    	BuildMatrixBodyPlant plant = buildMatrixSmcDAO.getBodyPlantForImport(entry.getValue());
	    	if(plant == null || !associationByPlantId.keySet().contains(plant.getPlantId()))
	    		plantsNotFound.put(entry.getKey(), entry.getValue());
	    	else
	    		bodyPlantByColIndex.put(entry.getKey(), plant);
	    }
	    
	    List<BuildMatrixSlotDate> slotDatesForYear = buildMatrixSmcDAO.getSlotDatesForYear(year);
	    Map<String, BuildMatrixSlotDate> slotDatesByYearStrings = slotDatesForYear.stream().collect(toMap(BuildMatrixSlotDate::getFormattedSlotDate, sd -> sd));
	    List<String> datesNotInYear = new ArrayList<>();
	    Map<BuildMatrixSlotKey, Integer> availableUnitsBySlotKey = new HashMap<>();
	    for(Entry<String, Map<Integer, Integer>> entry: availableSlotsByColIndexByDate.entrySet()) {
	    	if(!slotDatesByYearStrings.keySet().contains(entry.getKey())) {
	    		datesNotInYear.add(entry.getKey());
	    	}
	    	else {
	    		BuildMatrixSlotDate slotDate = slotDatesByYearStrings.get(entry.getKey());
	    		for(Entry<Integer, Integer> innerEntry: entry.getValue().entrySet()) {
	    			if(!plantsNotFound.keySet().contains(innerEntry.getKey())) {
	    				BuildMatrixBodyPlant bodyPlant = bodyPlantByColIndex.get(innerEntry.getKey());
	    				availableUnitsBySlotKey.put(new BuildMatrixSlotKey(slotDate.getSlotDateId(), bodyPlant.getPlantId(), slotTypeId), innerEntry.getValue());
	    			}
	    		}
	    	}
	    }
	    
	    List<Integer> slotDateIds = availableUnitsBySlotKey.keySet().stream().map(BuildMatrixSlotKey::getSlotDateId).collect(toList());
	    List<Integer> plantIds = availableUnitsBySlotKey.keySet().stream().map(BuildMatrixSlotKey::getPlantId).collect(toList());
	    List<BuildMatrixSlot> slots = new ArrayList<>();
		if(slotDateIds.isEmpty() || slotDateIds == null || plantIds.isEmpty() || plantIds == null)
			slots = Collections.emptyList();
		else	
			slots = buildMatrixSmcDAO.getSlotsBySlotDatesAndPlantIds(slotTypeId, slotDateIds, plantIds);
		
		Map<Integer,BuildMatrixSlot> slotByslotId = slots.stream().collect(toMap(BuildMatrixSlot::getSlotId, slot->slot));
		List<BuildMatrixSlotRegionAvailability> regionAvailabilityList = new ArrayList<>();
		if(slots.isEmpty() || slots == null)
			regionAvailabilityList = Collections.emptyList();
		else
			regionAvailabilityList = buildMatrixSmcDAO.getRegionAvailabilityBySlotIdsAndRegion(slotByslotId.keySet(), region);
		
		if(!regionAvailabilityList.isEmpty()) {
			for(BuildMatrixSlotRegionAvailability regionSlot: regionAvailabilityList) {
				BuildMatrixSlot slot = slotByslotId.get(regionSlot.getSlotId());
				Integer newAvailableSlots = availableUnitsBySlotKey.get(new BuildMatrixSlotKey(slot.getSlotDateId(), slot.getPlantId(), slot.getSlotTypeId()));
				regionSlot.updateAvailableSlots(newAvailableSlots, slot, true);
			}
		}
		
		List<BuildMatrixBodyPlant> bodyPlantList = new ArrayList<>(bodyPlantByColIndex.values());
		ProductionSlotsUtilizationSummary summary = new ProductionSlotsUtilizationSummary(bodyPlantList, slotDatesForYear, regionAvailabilityList, slots, true);
		ImportRegionSlotsResults results = new ImportRegionSlotsResults(summary, bodyPlantList, datesNotInYear, plantsNotFound);
	    
		return results;
	}

	@Override
	public void deleteReservationData(List<Integer> slotReservationIdList)
	{
		if (!slotReservationIdList.isEmpty()) {
			for (Integer slotReservationId : slotReservationIdList) {
				buildMatrixSmcDAO.removeSlotResult(slotReservationId);
			}
		}
	}
	
	@Override
	public void updateReservationData(int slotReservationId, int slotId, int plantId, String unitNumber)
	{
			buildMatrixSmcDAO.updateSlotReservations(slotReservationId, slotId, plantId, unitNumber);
	}
	
	@Override
	public void saveSlots(SaveSlotsForm form) {
		Map<Integer, Integer> availableSlotsById = form.getSlotInfos().stream().collect(toMap(si->si.getSlotId(),si->si.getAvailableSlots()));
		List<BuildMatrixSlot> slots = buildMatrixSmcDAO.getSlotsBySlotIds(availableSlotsById.keySet());
		
		for(BuildMatrixSlot slot: slots) {
			int newAvailableSlots = availableSlotsById.get(slot.getSlotId());
			if(newAvailableSlots != slot.getAvailableSlots()) {
				slot.updateAvailableSlots(newAvailableSlots, false);
				buildMatrixSmcDAO.updateSlot(slot);
			}
		}
		
	}

	@Override
	public void saveRegionSlots(SaveRegionSlotsForm form) {
		Map<Integer, Integer> availableSlotsById = form.getRegionSlotInfos().stream().collect(toMap(si->si.getSlotRegionId(),si->si.getSlotAvailable()));
		List<BuildMatrixSlotRegionAvailability> regionAvailabilities = buildMatrixSmcDAO.getRegionAvailabilityBySlotRegionIds(availableSlotsById.keySet());
		Set<Integer> slotIds = regionAvailabilities.stream().map(ra -> ra.getSlotId()).collect(toSet());
		List<BuildMatrixSlot> slots = buildMatrixSmcDAO.getSlotsBySlotIds(slotIds);
		Map<Integer, BuildMatrixSlot> slotById = slots.stream().collect(toMap(BuildMatrixSlot::getSlotId, sl -> sl));
		
		for(BuildMatrixSlotRegionAvailability regionAvailability: regionAvailabilities) {
			int newAvailableSlots = availableSlotsById.get(regionAvailability.getSlotRegionId());
			if(newAvailableSlots != regionAvailability.getSlotAvailable()) {
				BuildMatrixSlot slot = slotById.get(regionAvailability.getSlotId());
				regionAvailability.updateAvailableSlots(newAvailableSlots, slot, false);
				buildMatrixSmcDAO.updateRegionAvailability(regionAvailability);
			}
		}
	}

	@Override
	public List<BuildMatrixBodyPlant> getBodyPlantsByPlantIds(Collection<Integer> plantIds) {
		return buildMatrixSmcDAO.getBodyPlantsByPlantIds(plantIds);
	}
	
}
