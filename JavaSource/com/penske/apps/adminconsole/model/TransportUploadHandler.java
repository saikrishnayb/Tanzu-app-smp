/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File: 		ExcelUploadHandler.java
 * Package: 	com.penske.apps.vsportal.excel
 * Version: 	1.0
 * Date:		14-Sep-2008
 * Description: Class used to handle the process of uploading the Transporter Excel Data
 * ************************************************************************************/

package com.penske.apps.adminconsole.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.penske.apps.adminconsole.service.UploadService;
import com.penske.apps.adminconsole.util.VsportalConstants;
import com.penske.apps.smccore.base.util.DateUtil;

/**
 * Class used to handle the process of uploading the Transporter Excel Data
 * 
 * @author Johnson Jayaraj
 *
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to
 *handle the uploading of excel docs.
 *
 *@author 600139251
 *@author 600139252
 */
public class TransportUploadHandler extends ExcelUploadHandler<Transport>{

    private static final Logger logger = Logger.getLogger(TransportUploadHandler.class);
    private static final String EOR = "Report Completed!";

    /**
     * Method to Create the Model object.
     * 
     * @author Johnson Jayaraj
     * @return java.lang.Object
     */
    @Override
    public Transport createModelObject(boolean pilot){
        return new Transport(pilot);
    }

    /**
     * Method to Populate the Excel Data into the Model objects
     * 
     * @author Johnson Jayaraj
     * @return boolean
     */
    @Override
    public boolean populateExcelData(String value, Transport transport,int cellNum, Row row){
        boolean readRecords = true;
        if(null != value){
            if(value.equals(EOR)){
                readRecords = false;
            }else{
                populateTransport(transport,value,cellNum);
            }
        }
        return readRecords;

    }

    /**
     * Method to add each and every Model object into a Collection
     * 
     * @author Johnson Jayaraj
     */
    @Override
    public void collectExcelDataList(boolean readRecords,List<Transport> transportList, Transport modelObject) throws Exception{

        if (readRecords == true) {
            if (  (modelObject).getUnitNo() != null 			  &&  (modelObject).getVehicleCategory() != null &&
                    !(modelObject).getUnitNo().trim().equals("0") && !(modelObject).getVehicleCategory().equals(" ") ){
                transportList.add(modelObject);
            }else{
                Exception e = new Exception("Row does not contain the Unit and Category primary keys");
                logger.debug("File contains no records to upload");
                throw e;
            }
        }

    }

    /**
     * Method to validate , if the Excel file is valid or not.
     * 
     * @param fileName java.lang.String
     * @param uploadService com.penske.apps.vsportal.service.IUploadService
     * @return java.lang.String
     */
    @Override
    public String validateFile(String fileName, InputStream input, UploadService<Transport> uploadService, boolean pilot){
        String message = "";

        Sheet sheet = null;
        try {
            Workbook wb = WorkbookFactory.create(input);

            sheet = wb.getSheetAt(0);

            Iterator<Row> rows = sheet.rowIterator();
            if(rows.hasNext()) {
                Row row = rows.next();

                String estDelDateText = row.getCell(VsportalConstants.TRANSPORTER_EST_DEL_DATE).getStringCellValue();
                if(estDelDateText.compareToIgnoreCase("Estimated\nDelivery\nDate") != 0) {
                    message = "Estimated Delivery Date Field not found at expected index. Check format of report.";
                }

                int cellCount = row.getLastCellNum();
                if(cellCount < 50) {
                    message = "Invalid Number Of Columns, check the spreadsheet and try again!";
                }
            }

        } catch (FileNotFoundException e) {
            logger.debug(e.getMessage());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        int extensionCheck = 3;
        try {
            List<MimeTypeModel> mimeTypeList = uploadService.getMimeTypeList(); //To get the list of Mimetype so that the uploaded file type can be compared with the list

            if (fileName != null){
                extensionCheck = checkExtension(fileName, mimeTypeList);
            }

            if (fileName == null || fileName.trim().length() == 0) {
                message = "File Name can't be blank";
            } else if (extensionCheck == 1) {
                message = "The file has no extension. Upload Stopped for "	+ fileName;
            } /*else if (extensionCheck == 2) {
					message = "The file extension must be 'xls' for "+ fileName;
				} */else if (extensionCheck == 3) {
				    message = "Error Occured while verifying extension for "+ fileName;
				}
        }catch(Exception exp){
            logger.debug("Error in ExcelUploadHandler.upload "	+ exp.getMessage());
        }
        return message;
    }

    /**
     * Method to get the starting Row Number from which the data needs to be read.
     * 
     * @author 600123480
     * @return int
     */
    @Override
    public int getStartRow(boolean pilot){
        return VsportalConstants.TRANSPORT_START_ROW;
    }

    /**
     * Method to populateTransport object
     * @param transport com.penske.apps.vsportal.Transport
     * @param value java.lang.String
     * @param cellNum int
     */
    public void populateTransport(Transport transport ,String value, int cellNum){

        if(null != value && !value.equals(EOR)){
            populateTransporterCells(transport ,value, cellNum);
        }
    }

    private void populateTransporterCells(Transport transport ,String value, int cellNum) {
        switch ( cellNum ) {
            case VsportalConstants.TRANSPORTER_PICKUP_VENDOR:
                transport.setPickupVendor(value);
                break;
            case VsportalConstants.TRANSPORTER_STUS:
                transport.setStatus(value);
                break;
            case VsportalConstants.TRANSPORTER_UNIT_NO:
                transport.setUnitNo(value);
                break;
            case VsportalConstants.TRANSPORTER_PICKUP_DATE:
                try{
                    transport.setRequestedPickupDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Requested Pickup Date", e);
                    transport.setRequestedPickupDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_PROD_DATE:
                try{
                    transport.setProductionDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Production Date", e);
                    transport.setProductionDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_EST_DEL_DATE:
                try{
                    transport.setEstimatedDeliveryDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Estimated Delivery Date", e);
                    transport.setEstimatedDeliveryDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_HOLD_DATE:
                try{
                    transport.setHoldNotificationDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Hold Notification Date", e);
                    transport.setHoldNotificationDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_CANCEL_DATE:
                try{
                    transport.setAdvanceNoticeCancelDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Cancel Date", e);
                    transport.setAdvanceNoticeCancelDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_PLANT_OEM:
                transport.setOem(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_CODE:
                transport.setPlantCode(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_NAME:
                transport.setPlantName(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_ADRS_1:
                transport.setPlantAdrs1(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_ADRS_2:
                transport.setPlantAdrs2(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_CITY:
                transport.setOriginCity(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_STATE:
                transport.setOriginState(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_ZIP:
                transport.setOriginZipCode(value);
                break;
            case VsportalConstants.TRANSPORTER_PLANT_CONTACT:
                transport.setOriginContactInfo(value);
                break;
            case VsportalConstants.TRANSPORTER_DEST_CODE:
                transport.setDestCode(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_NAME:
                transport.setDlvName(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_ADRS_1:
                transport.setDlvAdrs1(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_ADRS_2:
                transport.setDlvAdrs2(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_CITY:
                transport.setDestCity(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_STATE:
                transport.setDestState(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_ZIP:
                transport.setDestZipCode(value);
                break;
            case VsportalConstants.TRANSPORTER_DLV_CONTACT:
                transport.setDestContactInfo(value);
                break;
            case VsportalConstants.TRANSPORTER_MILEAGE:
                transport.setMileage(value);
                break;
            case VsportalConstants.TRANSPORTER_VIN:
                transport.setVin(value);
                break;
            case VsportalConstants.TRANSPORTER_BODY_SERIAL:
                transport.setBodySerial(value);
                break;
            case VsportalConstants.TRANSPORTER_UNIT_GVW:
                transport.setUnitGvw(value);
                break;
            case VsportalConstants.TRANSPORTER_VEH_CAT:
                transport.setVehicleCategory(value);
                break;
            case VsportalConstants.TRANSPORTER_VEH_TYPE:
                transport.setVehicleType(value);
                break;
            case VsportalConstants.TRANSPORTER_FUEL_TYPE:
                transport.setFuelType(value);
                break;
            case VsportalConstants.TRANSPORTER_SLPR_CODE:
                transport.setSlprCode(value);
                break;
            case VsportalConstants.TRANSPORTER_AERO_ROOF:
                transport.setAeroRoof(value);
                break;
            case VsportalConstants.TRANSPORTER_AERO_SIDE:
                transport.setAeroSide(value);
                break;
            case VsportalConstants.TRANSPORTER_AERO_CAB:
                transport.setAeroCab(value);
                break;
            case VsportalConstants.TRANSPORTER_DECKING:
                transport.setDecking(value);
                break;
            case VsportalConstants.TRANSPORTER_NUM_DECKING:
                transport.setNumDecks(value);
                break;
            case VsportalConstants.TRANSPORTER_PENSKE_CHARGE:
                transport.setPenskeAddOnCharge(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_TOTAL_AMOUNT:
                transport.setTotalAmount(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_TRNST_PO:
                transport.setTrnstPo(value);
                break;
            case VsportalConstants.TRANSPORTER_TRNST_PO_DATE:
                try{
                    transport.setTransitPoDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Transit PO Date", e);
                    transport.setTransitPoDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_UNIT_DLV_DATE:
                try{
                    transport.setActDelvry(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Act Delivery Date", e);
                    transport.setActDelvry(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_ACT_DELVRY:
                try{
                    transport.setActualDeliveryReported(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Delivery Date Reported", e);
                    transport.setActualDeliveryReported(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_FLUID_AMOUNT:
                transport.setFluids(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_BREAKDWN_AMOUNT:
                transport.setBreakDown(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_TOLL_AMOUNT:
                transport.setTolls(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_MISC_AMOUNT:
                transport.setMisc(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_REVISED_AMOUNT:
                transport.setRevisedPOAmount(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_ACCEPT_AMOUNT:
                transport.setIsPOAmountAccepted(value);
                break;
            case VsportalConstants.PENSKE_COMMENT:
                transport.setPenskeComment(value);
                break;
            case VsportalConstants.TRANSPORTER_COMMENT:
                transport.setTransporterComment(value);
                break;
            case VsportalConstants.TRANSPORTER_ASSIGN_DATE:
                try {
                    transport.setTransporterAssignDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Last Changed Date", e);
                    transport.setLastChangedDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_LAST_UPDATED:
                try{
                    transport.setLastChangedDate(getDate(value));
                }catch(Exception e){
                    logger.debug("Error Occured while Setting the Last Changed Date", e);
                    transport.setLastChangedDate(null);
                }
                break;
            case VsportalConstants.TRANSPORTER_CORP_CODE:
                transport.setCompanyCode(value);
                break;
            case VsportalConstants.TRANSPORTER_LIFTGATE_MAKE:
                transport.setLiftgateMake(value);
                break;
            case VsportalConstants.TRANSPORTER_LIFTGATE_MODEL:
                transport.setLiftgateModel(value);
                break;
            case VsportalConstants.TRANSPORTER_REPORT_ID:
                transport.setReportId(value);
                break;
            case VsportalConstants.TRANSPORTER_CANADA_TAX:
                transport.setCanadaTax(toDouble(value));
                break;
            case VsportalConstants.TRANSPORTER_TRANSMISSION_TYPE:
                transport.setTransmissionType(value);
                break;
            
        }
    }

    private double toDouble(String source) {
        double targetValue = 0;
        if (StringUtils.isNotBlank(source)) {
            targetValue = Double.parseDouble(source);
        }

        return targetValue;
    }

    /**
     * Method to ge the Date as java.util.Date 
     * 
     * @author 600104283
     * @param value java.lang.String
     * @return java.util.Date
     * @throws java.lang.Exception
     */
    /*
     * NOTE: 2018-11-28 - JS - this method was tested with the following unit test code, and it conforms to the way POIUtil.getDate() behaved.
     * 
     	String format = "MM/dd/yy";
		Date defaultDate = DateUtil.parseDate("0001-01-01");
		Date augNinth = DateUtil.parseDate("1989-08-09");
		Date augNinth2 = DateUtil.parseDate("2009-08-09");
		Date firstCentury = DateUtil.parseDate("0089-08-09");
		Date firstCentury2 = DateUtil.parseDate("0009-08-09");
		
		assertThat(POIUtil.getDate(null), is(defaultDate));
		assertThat(POIUtil.getDate("40123.4"), is(nullValue()));		//Excel number formatted as a date
		assertThat(POIUtil.getDate("foobar"), is(nullValue()));
		assertThat(POIUtil.getDate("1989-08-09"), is(augNinth));
		assertThat(POIUtil.getDate("1989-8-9"), is(augNinth));
		assertThat(POIUtil.getDate("89-8-9"), is(firstCentury));
		assertThat(POIUtil.getDate("8/9/89"), is(augNinth));
		assertThat(POIUtil.getDate("08/09/89"), is(augNinth));
		assertThat(POIUtil.getDate("8/9/1989"), is(augNinth));
		assertThat(POIUtil.getDate("08/09/2009"), is(augNinth2));
		assertThat(POIUtil.getDate("8/9/9"), is(firstCentury2));
		assertThat(POIUtil.getDate("8/9/09"), is(augNinth2));
		assertThat(POIUtil.getDate("7/40/09"), is(augNinth2));		//Improper date - should roll over to next month
     */
    private Date getDate(String value ) throws Exception {
    	
    	Date date = null;
    	if(StringUtils.isNotBlank(value)){
    		try{
    			date = DateUtil.parseDate(value);
    		}catch(Exception e){
    			logger.error(e.getMessage());
    		}
    		
    	} else {
    		
    		date = DateUtil.parseDate("0001-01-01");
    		
    	}
    	
    	return date;
    }
}


