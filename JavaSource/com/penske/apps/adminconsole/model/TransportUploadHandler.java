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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.penske.apps.adminconsole.service.UploadService;
import com.penske.apps.adminconsole.util.VsportalConstants;




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
 */
public class TransportUploadHandler extends ExcelUploadHandler{
		
		Logger logger = Logger.getLogger("TransportUploader");
		public static String EOR = "Report Completed!";	
		
		/**
		 * Method to Create the Model object. 
		 *  
		 * @author Johnson Jayaraj 
		 * @return java.lang.Object
		 */			
		public Object createModelObject(boolean pilot){
			return new Transport(pilot);
		}
		
		/**
		 * Method to Populate the Excel Data into the Model objects
		 * 
		 * @author Johnson Jayaraj 
		 * @return boolean
		 */	
		public boolean populateExcelData(String value,Object transportObj,int cellNum, HSSFCell cell, HSSFRow row){
			Transport transport = (Transport)transportObj;
			boolean readRecords = true;
	    	if(null != value){
	    		if(value.equals(EOR)){
	    			readRecords = false;
	    		}else{		                	    	
	    			populateTransport(transport,value,cellNum, cell);
	    		}
	    	}
	    	return readRecords;
			
		}

		/**
		 * Method to add each and every Model object into a Collection
		 * 
		 * @author Johnson Jayaraj 
		 */
	    public void collectExcelDataList(boolean readRecords,List transportList, Object modelObject) throws Exception{

			if (readRecords == true) {
				if (  ((Transport)modelObject).getUnitNo() != null 			  &&  ((Transport)modelObject).getCat() != null &&
			         !((Transport)modelObject).getUnitNo().trim().equals("0") && !((Transport)modelObject).getCat().equals(" ") ){
					transportList.add((Transport) modelObject);
				}else{
					Exception e = new Exception("Row does not contain the Unit and Category primary keys");
					logger.debug("File contains no records to upload");
					throw e;
				}
			}
	    	
	    }
	    
	    /**R
		 * Method to validate , if the Excel file is valid or not.
		 * 
		 * @param fileName java.lang.String
		 * @param uploadService com.penske.apps.vsportal.service.IUploadService
		 * @return java.lang.String
		 */
		 public String validateFile(String fileName, InputStream input, UploadService uploadService, boolean pilot){
			String message = "";

	        HSSFSheet sheet = null;
	        try {
				POIFSFileSystem fs = new POIFSFileSystem(input); // Getting the instance of POI File System
				HSSFWorkbook wb = new HSSFWorkbook(fs); // Creating a workbook for the uploaded file
				sheet = wb.getSheetAt(0);
				
				Iterator rows = sheet.rowIterator();
	            if(rows.hasNext()) {
	            	HSSFRow row = (HSSFRow) rows.next();
	                
	                String estDelDateText = row.getCell((int)VsportalConstants.TRANSPORTER_EST_DEL_DATE).getStringCellValue();
	                if(estDelDateText.compareToIgnoreCase("Estimated Delivery Date") != 0) {
	                	message = "Estimated Delivery Date Field not found at expected index. Check format of report.";
	                }
	                
	                int cellCount = row.getLastCellNum();
	                if(cellCount < 50) {
	                	message = "Invalid Number Of Columns, check the spreadsheet and try again!";
	                }
	            }
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (Exception e) {
				e.printStackTrace();
			} 
						
			int extensionCheck = 3;
			try {
			List mimeTypeList = uploadService.getMimeTypeList(); //To get the list of Mimetype so that the uploaded file type can be compared with the list
			
			if (fileName != null){
					extensionCheck = checkExtension(fileName, mimeTypeList);
				}	
				
				if (fileName == null || fileName.trim().length() == 0) {
					message = "File Name can't be blank";
				} else if (extensionCheck == 1) {
					message = "The file has no extension. Upload Stopped for "	+ fileName;
				} else if (extensionCheck == 2) {
					message = "The file extension must be 'xls' for "+ fileName;
				} else if (extensionCheck == 3) {
					message = "Error Occured while verifying extension for "+ fileName;
				}	
			}catch(Exception exp){
				logger.debug("Error in ExcelUploadHandler.upload "	+ exp.getMessage());
			}
			return message;
		}

	    
		/**
		 * Method to upload the Excel Data
		 * 
		 * @author Johnson Jayaraj 
		 */
	    public String uploadExcelDataList(List transportList, UploadService objUploadService) throws Exception{
	       		
       		Transport transport = null;
       		String message = "";
    		Iterator It = (Iterator)transportList.iterator();
     	   		
    		while ( It.hasNext() )
    		{
    			try{
	    			transport = (Transport)It.next();
	    			objUploadService.insert( transport);
    		 	}catch(Exception e){	       		
    	       		logger.debug(" ERROR while trying to insert the records "+ e.getMessage());
    	       		
    	       		throw e;
    	       	}	    	
    		}
    		return message;
	    }
	    
		/**
		 * Method to get the starting Row Number from which the data needs to be read.
		 * 
		 * @author 600123480
		 * @return int
		 */		
		public int getStartRow(boolean pilot){
			return VsportalConstants.TRANSPORT_START_ROW;
		}
		
	    /**
	     * Method to populateTransport object
	     * @param transport com.penske.apps.vsportal.Transport
	     * @param value java.lang.String
	     * @param cellNum int
	     */
	    		
	    public void populateTransport(Transport transport ,String value, int cellNum, HSSFCell cell){
	    	
	    	if(null != value && !value.equals(EOR)){
	    		populateTransporterCells(transport ,value, cellNum, cell);
	    	}	
	    }
	    
	    private void populateTransporterCells(Transport transport ,String value, int cellNum, HSSFCell cell) {
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
			    		transport.setRequestedPickupDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Requested Pickup Date"); 
	    				transport.setRequestedPickupDate(null);
	    			}
	    			break;
		    	case VsportalConstants.TRANSPORTER_PROD_DATE: 
		    		try{
			    		transport.setProductionDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Production Date"); 
	    				transport.setProductionDate(null);
	    			}
	    			break;	 
		    	case VsportalConstants.TRANSPORTER_EST_DEL_DATE: 
		    		try{
			    		transport.setEstimatedDeliveryDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Estimated Delivery Date"); 
	    				transport.setEstimatedDeliveryDate(null);
	    			}
	    			break;	 	    			
		    	case VsportalConstants.TRANSPORTER_HOLD_DATE: 
		    		try{
			    		transport.setHoldNotificationDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Hold Notification Date"); 
	    				transport.setHoldNotificationDate(null);
	    			}
	    			break;	
		    	case VsportalConstants.TRANSPORTER_CANCEL_DATE: 
		    		try{
			    		transport.setAdvanceNoticeCancelDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Cancel Date"); 
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
		    	case VsportalConstants.TRANSPORTER_CAT:
    				transport.setCat(value);
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
		    	case VsportalConstants.TRANSPORTER_FREIGHT_AMOUNT:
    				transport.setFrieghtAmount(toDouble(value));
    				break;
		    	case VsportalConstants.TRANSPORTER_FUEL_SUR_CHARGE:
    				transport.setFuelSurcharge(toDouble(value));
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
			    		transport.setTransitPoDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Transit PO Date"); 
	    				transport.setTransitPoDate(null);
	    			}
	    			break;
		    	case VsportalConstants.TRANSPORTER_UNIT_DLV_DATE:
		    		try{
			    		transport.setActDelvry(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Act Delivery Date"); 
	    				transport.setActDelvry(null);
	    			}
	    			break;	
		    	case VsportalConstants.TRANSPORTER_ACT_DELVRY:
		    		try{
			    		transport.setActualDeliveryReported(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Delivery Date Reported"); 
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
    					transport.setTransporterAssignDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
					}catch(Exception e){
						logger.debug("Error Occured while Setting the Last Changed Date"); 
						transport.setLastChangedDate(null);
					}
    				break;
		    	case VsportalConstants.TRANSPORTER_LAST_UPDATED:
		    		try{
			    		transport.setLastChangedDate(POIUtil.getDate(value, cell, DateUtil.MM_DD_YY));
	    			}catch(Exception e){
	    				logger.debug("Error Occured while Setting the Last Changed Date"); 
	    				transport.setLastChangedDate(null);
	    			}
	    			break; 
		    	case VsportalConstants.TRANSPORTER_CORP_CODE:
    				transport.setCompanyCode(value);
    				break;
		    	case VsportalConstants.TRANSPORTER_PARENT_VENDOR:
    				transport.setParentVendor(value);
    				break;
		    	case VsportalConstants.TRANSPORTER_PURCH_PO_NUM:
    				transport.setPurchasePoNum(value);
    				break;
		    	case VsportalConstants.TRANSPORTER_ADV_NOT_SEQ:
    				transport.setAdvancedNoticeSequence(value);
    				break;
		    	case VsportalConstants.TRANSPORTER_REPORT_ID:
    				transport.setReportId(value);
    				break;	      				    				
	    	}
	    }
		
	    /**
	     * Method to ge the Date
	     * 
	     * @param value java.lang.String
	     * @param cell org.apache.poi.hssf.usermodel.HSSFCell
	     * @return java.util.Date
	     * @throws java.lang.Exception
	     */
	    private Date getDate(String value, HSSFCell cell ) throws Exception {
	    	
	    	double doubleDate = 0.0;
	    	Date date = null;
	    	if(!DataUtil.isEmpty(value)){
	    		
	    		if(isNumber(value)){
	    			doubleDate = Double.parseDouble(value);	    		
	    			if(HSSFDateUtil.isValidExcelDate(doubleDate)){
	        			date = HSSFDateUtil.getJavaDate(doubleDate);   
	        			date = DateUtil.getDate(date);
	    			}
	    		}else{			
	    			date = DateUtil.parseDate(value,DateUtil.MM_DD_YY);
	    		}
	    		
	    	}	
        	return date;
	    }
	    
		private static boolean isNumber(String s) {
			try {
				Double.parseDouble(s);
			}
			catch (NumberFormatException nfe) {
				return false;
			}
			return true;
		}
	    
		/**
		 * converts string to double
		 * 
		 */
		private double toDouble(String source) {
			double targetValue = 0;
			if (!DataUtil.isEmpty(source)) {
				targetValue = Double.parseDouble(source);
			}

			return targetValue;
		}
	    
	    
	}	


