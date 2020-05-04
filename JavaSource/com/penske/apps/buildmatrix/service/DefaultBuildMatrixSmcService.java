package com.penske.apps.buildmatrix.service;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.penske.apps.buildmatrix.domain.ProductionSlotResult;
import com.penske.apps.buildmatrix.domain.enums.BuildStatus;
import com.penske.apps.buildmatrix.model.BuildMixForm;
import com.penske.apps.buildmatrix.model.BuildMixForm.AttributeRow;
import com.penske.apps.buildmatrix.model.BusinessAwardForm;
import com.penske.apps.buildmatrix.model.BusinessAwardForm.BusinessAwardRow;
import com.penske.apps.smccore.base.util.Util;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBuildMatrixSmcService implements BuildMatrixSmcService {

	private static Logger logger = Logger.getLogger(DefaultBuildMatrixSmcService.class);
	
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
	public BuildMatrixBodyPlant getPlantData(int plantId) {
		return buildMatrixSmcDAO.getPlantData(plantId);
	}
	
	@Override
	public int saveOfflineDates(BuildMatrixBodyPlant plantData) {
		return buildMatrixSmcDAO.saveOfflineDates(plantData);
	}

	//*****ATTRIBUTE MAINENANCE WORKFLOW *****//
	@Override
	public List<BuildAttribute> getAllBuildMatrixAttributes() {
		List<BuildAttribute> buildMatrixAttribute = buildMatrixSmcDAO.getAllBuildMatrixAttributes();
		return buildMatrixAttribute;
	}

	@Override
	public void updateAttribute(int attributeId, List<Integer> attrValueIds) {
		// attributeDao.updateAttribute(attributeData); //need to confirm attribute name update
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
	//****PRODUCTION SLOT RESULTS WORKFLOW****//
	public List<ProductionSlotResult> getProductionSlotResults(int buildId) {
		List<ProductionSlotResult> productionSlotResults = buildMatrixSmcDAO.getProductionSlotResults(buildId);
		return productionSlotResults;
	}
	
	@Override
	public BuildAttributeValue addAttribute(int attributeId, String attributeValue) {
		BuildAttributeValue attrValue = new BuildAttributeValue(attributeValue);
		buildMatrixSmcDAO.addAttribute(attributeId, attrValue);
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
	@Override
	public Workbook downloadProductionSlotResultsDocument(int buildId) throws  Exception{
		Workbook workbook = null;
		List<ProductionSlotResult> productionSlotResult = buildMatrixSmcDAO.getProductionSlotResults(buildId);
		 if(productionSlotResult!=null && !productionSlotResult.isEmpty()){
			workbook = generateProductionSlotResultsExcel(productionSlotResult);
        }
        return workbook;
	}
	 private Workbook generateProductionSlotResultsExcel(List<ProductionSlotResult> productionSlotResult) throws IOException, ParseException {
	        SXSSFWorkbook workbook = new SXSSFWorkbook();
	        SXSSFSheet  workSheet = workbook.createSheet("Production Slot Results"); // creating new work sheet
	        
	        workbook.setCompressTempFiles(true);
	        createHeader(workbook,workSheet);
	        
	        int rowId = 1;
	        if(productionSlotResult == null)
	        	productionSlotResult = Collections.emptyList();	
			for(ProductionSlotResult ProductionSlotResultData :productionSlotResult ){
			
			SXSSFRow dataRow = workSheet.createRow(rowId);
			
				SXSSFCell datacell1=dataRow.createCell(0);
				datacell1.setCellValue(ProductionSlotResultData.getOrderId());
	
				SXSSFCell datacell2=dataRow.createCell(1);
				datacell2.setCellValue(ProductionSlotResultData.getUnitNumber());
	
				SXSSFCell datacell3=dataRow.createCell(2);
				datacell3.setCellValue(ProductionSlotResultData.getProgramName());
				
				SXSSFCell datacell4=dataRow.createCell(3);
				datacell4.setCellValue(ProductionSlotResultData.getRegion());
	
				SXSSFCell datacell5=dataRow.createCell(4);
				datacell5.setCellValue(ProductionSlotResultData.getArea());
	
				SXSSFCell datacell6=dataRow.createCell(5);
				datacell6.setCellValue(ProductionSlotResultData.getDistrictNumber());
	
				SXSSFCell datacell7=dataRow.createCell(6);
				datacell7.setCellValue(ProductionSlotResultData.getDistrictName());
	
				SXSSFCell datacell8=dataRow.createCell(7);
				if (ProductionSlotResultData.getRequestedDeliveryDate() != null && !ProductionSlotResultData.getRequestedDeliveryDate().equals("")) {
				    datacell8.setCellValue(ProductionSlotResultData.getRequestedDeliveryDate());
				    formatDateCell(workbook, datacell8);
				}
	
				SXSSFCell datacell9=dataRow.createCell(8);
				datacell9.setCellValue(ProductionSlotResultData.getProductionSlot());
	
				SXSSFCell datacell10=dataRow.createCell(9);
				String productionDateString = ProductionSlotResultData.getProductionDate();
				if (productionDateString != null && productionDateString != "") {
					Date productionDate = convertStringToDate(productionDateString);
					datacell10.setCellValue(productionDate);
					formatDateCell(workbook, datacell10);
				}
				rowId++;
		    }
	        return workbook;
	    }
	 
	 /**
		 * Method for creating the header
		 * @param workbook
		 * @param workSheet
		 */
		private void createHeader(SXSSFWorkbook workbook,SXSSFSheet  workSheet)
		{
			SXSSFRow row = workSheet.createRow(0);
			row.setHeight((short)550);

			workSheet.setColumnWidth(0, 20*150);
			workSheet.setColumnWidth(1, 20*150);
			workSheet.setColumnWidth(2, 20*256);
			workSheet.setColumnWidth(3, 20*150);
			workSheet.setColumnWidth(4, 20*150);
			workSheet.setColumnWidth(5, 20*150);
			workSheet.setColumnWidth(6, 20*256);
			workSheet.setColumnWidth(7, 20*256);
			workSheet.setColumnWidth(8, 20*256);
			workSheet.setColumnWidth(9, 20*256);
			workSheet.trackAllColumnsForAutoSizing();
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

			SXSSFCell cell1=row.createCell(0);
			cell1.setCellValue("Order #");
			cell1.setCellStyle(cellStyle);

			SXSSFCell cell2=row.createCell(1);
			cell2.setCellValue("Unit");
			cell2.setCellStyle(cellStyle);
			
			SXSSFCell cell3=row.createCell(2);
			cell3.setCellValue("Program Name");
			cell3.setCellStyle(cellStyle);
			
			SXSSFCell cell4=row.createCell(3);
			cell4.setCellValue("Region");
			cell4.setCellStyle(cellStyle);

			SXSSFCell cell5=row.createCell(4);
			cell5.setCellValue("Area");
			cell5.setCellStyle(cellStyle);

			SXSSFCell cell6=row.createCell(5);
			cell6.setCellValue("District");
			cell6.setCellStyle(cellStyle);

			SXSSFCell cell7=row.createCell(6);
			cell7.setCellValue("District Name");
			cell7.setCellStyle(cellStyle);

			SXSSFCell cell8=row.createCell(7);
			cell8.setCellValue("Requested Delivery Date");
			cell8.setCellStyle(cellStyle);

			SXSSFCell cell9=row.createCell(8);
			cell9.setCellValue("Production Slot");
			cell9.setCellStyle(cellStyle);

			SXSSFCell cell10=row.createCell(9);
			cell10.setCellValue("Production Date");
			cell10.setCellStyle(cellStyle);
		}
		
		/**
		 * Method for formatting the date cell
		 * @param workbook
		 * @param cell
		 */
		private void formatDateCell(SXSSFWorkbook workbook, SXSSFCell cell) {
	        CellStyle cellStyle = workbook.createCellStyle();
	        CreationHelper creationHelper = workbook.getCreationHelper();
	        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy"));
	        cell.setCellStyle(cellStyle);
	    }
		
		/**
		 * Method for converting string to date
		 * @param productionDate
		 */
		private Date convertStringToDate(String productionDateString) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date parsedDate = new Date();
			try {
			parsedDate = dateFormat.parse(productionDateString);
			} catch (ParseException ex) {
				logger.error(ex);
			}
			return parsedDate;
	    }
		
}
