package com.penske.apps.buildmatrix.service;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BuildMixForm.AttributeRow;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm.BusinessAwardRow;
import com.penske.apps.buildmatrix.model.ProductionSlotsMaintenanceSummary;
import com.penske.apps.buildmatrix.model.ProductionSlotsUtilizationSummary;
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
		if(!plantData.getOfflineDateToRemove().isEmpty()) {
			buildMatrixSmcDAO.removeOfflineDates(plantData.getOfflineDateToRemove());
		}
		
		if(!plantData.getOfflineDates().isEmpty())
			buildMatrixSmcDAO.saveOfflineDates(plantData);
	}

	@Override
	public List<RegionPlantAssociation> getRegionAssociationData(int plantId) {
		return buildMatrixSmcDAO.getRegionAssociationData(plantId);
	}

	@Override
	public void savePlantRegionAssociation(List<RegionPlantAssociation> regionPlantAssociationList) {
		List<String> regionsList = new ArrayList<String>();
		List<String> districtsList = new ArrayList<String>();
		List<Integer> slotIdList = new ArrayList<Integer>();
		for (RegionPlantAssociation regionPlantAssociation : regionPlantAssociationList) {
			if (regionPlantAssociation.getIsAssociated().equalsIgnoreCase(ApplicationConstants.NO)) {
				regionsList.add(regionPlantAssociation.getRegion());
			}
		}
		int plantId = regionPlantAssociationList.get(0).getPlantId();
		if (regionsList != null && !regionsList.isEmpty()) {
			districtsList = buildMatrixSmcDAO.getDistrictsFromFreightMileage(plantId, regionsList);
			if (districtsList != null && !districtsList.isEmpty()) {
				buildMatrixSmcDAO.deleteProximityDataForRegion(plantId, districtsList);
			}
			slotIdList = buildMatrixSmcDAO.getSlotIdForPlantId(plantId);
			if (slotIdList != null && !slotIdList.isEmpty()) {
				buildMatrixSmcDAO.deleteSlotDataForRegion(slotIdList, regionsList);
			}
		}
		buildMatrixSmcDAO.savePlantRegionAssociation(regionPlantAssociationList);
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
	public List<ProductionSlotResult> getProductionSlotResults(int buildId) {
		List<ProductionSlotResult> productionSlotResults = buildMatrixSmcDAO.getProductionSlotResults(buildId);
		return productionSlotResults;
	}
	
	public List<ProductionSlotResult> getSlotResultsByFilter(int buildId, List<String> selectedFilters) {
		List<ProductionSlotResult> SlotResultsByFilter = buildMatrixSmcDAO.getSlotResultsByFilter(buildId, selectedFilters);
		return SlotResultsByFilter;
	}
	
	public List<String> getAllPlants() {
		List<String> allPlants = buildMatrixSmcDAO.getAllPlants();
		return allPlants;
	}
	
	@Override
	public void updateRunSummary(int buildId) {
		buildMatrixSmcDAO.updateRunSummary(buildId);
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
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getOrderTotalQuantity()));
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
		int bodiesOnOrder = selectedOrders.stream().collect(summingInt(order->order.getOrderTotalQuantity()));
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
					order.setFulfilledQty(croOrderReq.getFulfilledQty());
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
		List<ProductionSlotResult> productionSlotResult = buildMatrixSmcDAO.getProductionSlotResults(buildId);
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
			vehicleTypeRequiredCell.setCellValue(ProductionSlotResultData.isVehicleTypeChangeRequired() ? "Yes" : "No");
			if(ProductionSlotResultData.isVehicleTypeChangeRequired()) {
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
			String productionDateString = ProductionSlotResultData.getProductionDate();
			if (productionDateString != null && productionDateString != "") {
				Date productionDate = convertStringToDate(productionDateString);
				datacell10.setCellValue(productionDate);
				formatDateCell(workbook, datacell10);
			}

			dataRow.createCell(column++).setCellValue(ProductionSlotResultData.getChassisMake());
			
			SXSSFCell modelCell = dataRow.createCell(column++);
			modelCell.setCellValue(ProductionSlotResultData.getChassisModel());
			SXSSFCell modelRequiredCell = dataRow.createCell(column++);
			modelRequiredCell.setCellValue(ProductionSlotResultData.isChassisModelChangeRequired() ? "Yes" : "No");
			if(ProductionSlotResultData.isChassisModelChangeRequired()) {
				modelCell.setCellStyle(changeRequired);
				modelRequiredCell.setCellStyle(changeRequired);
			}
			
			SXSSFCell yearCell = dataRow.createCell(column++);
			yearCell.setCellValue(ProductionSlotResultData.getChassisModelYear());
			SXSSFCell yearRequiredCell = dataRow.createCell(column++);
			yearRequiredCell.setCellValue(ProductionSlotResultData.isChassisModelYearChangeRequired() ? "Yes" : "No");
			if(ProductionSlotResultData.isChassisModelYearChangeRequired()) {
				yearCell.setCellStyle(changeRequired);
				yearRequiredCell.setCellStyle(changeRequired);
			}
			
			SXSSFCell colorCell = dataRow.createCell(column++);
			colorCell.setCellValue(ProductionSlotResultData.getChassisColor());
			SXSSFCell colorRequiredCell = dataRow.createCell(column++);
			colorRequiredCell.setCellValue(ProductionSlotResultData.isChassisColorChangeRequired() ? "Yes" : "No");
			if(ProductionSlotResultData.isChassisColorChangeRequired()) {
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
			wheelRequiredCell.setCellValue(ProductionSlotResultData.isChassisWheelMatChangeRequired() ? "Yes" : "No");
			if(ProductionSlotResultData.isChassisColorChangeRequired()) {
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

	/**
	 * Method for converting string to date
	 * 
	 * @param productionDate
	 */
	private Date convertStringToDate(String productionDateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
		Date parsedDate = new Date();
		try {
			parsedDate = dateFormat.parse(productionDateString);
		} catch (ParseException ex) {
			logger.error(ex);
		}
		return parsedDate;
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
		ProductionSlotsMaintenanceSummary summary = new ProductionSlotsMaintenanceSummary(bodyPlantList, slotDates, slots);
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
			slots = buildMatrixSmcDAO.getSlotsBySlotDates(slotTypeId, slotDates.stream().map(BuildMatrixSlotDate::getSlotDateId).collect(toList()));
		}
		
		Set<Integer> slotIds = slots.stream().map(BuildMatrixSlot::getSlotId).collect(toSet());
		List<BuildMatrixSlotRegionAvailability> regionAvailability = new ArrayList<>();
		if(slots.isEmpty() || slots == null)
			regionAvailability = Collections.emptyList();
		else
			regionAvailability = buildMatrixSmcDAO.getRegionAvailability(slotIds);
		ProductionSlotsUtilizationSummary summary = new ProductionSlotsUtilizationSummary(regionPlantList, bodyPlantSummary, slotDates, regionAvailability, slots);
		
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
				slotDatesForYear.add(new BuildMatrixSlotDate(localDate));
			}
			buildMatrixSmcDAO.insertSlotDates(slotDatesForYear);
		}
		
		List<BuildMatrixBodyPlant> plants = buildMatrixSmcDAO.getAllBodyPlants();
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
			for(BuildMatrixSlot slot: slotList) {
				regionAvailabilityList.add(new BuildMatrixSlotRegionAvailability(slot, assoc));
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
	public SXSSFWorkbook exportSlotMaintenance(int year, int slotTypeId) {
		
		//export logic here
		
		return null;
	}
}
