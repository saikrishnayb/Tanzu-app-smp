/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File: 		ExcelUploadHandler.java
 * Package: 	com.penske.apps.vsportal.excel
 * Version: 	1.0
 * Date:		14-Sep-2008	
 * Description: This is a Generic class used for uploading simple excel sheets.
 * ************************************************************************************/

package com.penske.apps.adminconsole.model;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.penske.apps.adminconsole.service.UploadService;

/**
 * Class used to handle the process of uploading the Excel Data
 * information.
 * This is a Generic class used for uploading simple excel sheets.
 * All that developers need to do is to extend this class and implement the abstract methods.
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
public abstract class ExcelUploadHandler<T> {

	/**
	 * Declare Variables
	 */
	private static final Logger logger = LogManager.getLogger("ExcelUploadHandler");

	private String userId = "";

	/**
	 * Method to Create the Model object. This object will vary based on the excel sheet uploaded.
	 * Eg., Transporter Excel sheet data is populated into Transporter Model object.
	 * Ford Data Excel Sheet is populated into PenskeEmail Model Object.
	 * 
	 * @author Johnson Jayaraj 
	 * @return java.lang.Object
	 */
	protected abstract T createModelObject(boolean pilot);

	/**
	 * Method to Populate the Excel Data into the Model objects
	 * 
	 * @author Johnson Jayaraj 
	 * @return boolean
	 */
	protected abstract boolean populateExcelData(String value, T transportObj, int cellNum, Row row) throws Exception;

	/**
	 * Method to add each and every Model object into a Collection
	 * 
	 * @author Johnson Jayaraj 
	 */
	protected abstract void collectExcelDataList(boolean isReadable, List<T> transportList, T modelObject) throws Exception;

	/**
	 * Method to get the starting Row Number from which the data needs to be read.
	 * 
	 * @author 600123480
	 * @return int
	 */
	protected abstract int getStartRow(boolean pilot);
	
	/**
	 * Method to save Document
	 * 
	 * @author 600123480
	 * @param fileName java.lang.String
	 * @param fileToUpload org.apache.struts.upload.FormFile
	 * @param objUploadService com.penske.apps.vsportal.service.IUploadService
	 * @return java.lang.String
	 * @throws java.lang.Exception
	 */
	public String saveDocument(String fileName, MultipartFile fileToUpload, UploadService<T> objUploadService, boolean pilot) throws Exception {	
		
		//This message is to be sent back to the screen.
		String message ="";
		
		if (!fileToUpload.isEmpty()) //make sure there is something to upload 
		{
			InputStream is = null;
			try {
					is = fileToUpload.getInputStream(); //get the file's input stream
					//To validate the uploaded file; returned message is an error
					message = validateFile(fileName, is, objUploadService, pilot); 

				if(StringUtils.isBlank(message))
				{
					try {
						logger.debug("is " + is);
						try {
							is.close(); //close the input stream?
							is = fileToUpload.getInputStream(); //open it again?
							upload(is, objUploadService, pilot); // To upload the data
							message = fileName + " was successfully uploaded";
							logger.debug(fileName + " File was successfully uploaded");
						} catch (Exception e) {
							String msg = e.getMessage();
							if (msg.indexOf("Duplicate key value specified")>0){
								message="Duplicate key value for "+fileName;
							}else if (msg.indexOf("Null values not allowed in column or variable")>0){
								message="Null values not allowed in columns ";
							}else if (msg.indexOf("Data type mismatch")>0){
								message="Data type mismatch for "+fileName;
							}else if(msg.indexOf("Data truncation")>0){	
								message = buildDataTruncationErrorMsg(msg,fileName);
							}else if(msg.indexOf("please limit to")>0){
								message=e.getMessage()+" ~ "+fileName;
							}else{
								message="Error loading file "+fileName;
							}
							logger.debug(fileName + " File not uploaded"); 
						}

					} catch (Exception e) {
						logger.debug(e);
						message = "Error Occured while reading file " + fileName + " to upload";
					} finally {
						is.close();
					}
				}
			} catch (Exception e) {
				logger.error("Exception in  ExcelUploadHandler for "+fileName +". Exception is "+e.getMessage());
				message = "FORWARD";
			}
		}else {
			message = "File has no data or does not exist:" + fileName;
		}		
		return message;
	}
	
	/**
	 * Method to get the Data truncation exception message
	 * 
	 * @author 600123480
	 * @since 07-Jan-2010
	 * @param msg java.lang.String
	 * @param fileName java.lang.String
	 * @return java.lang.String
	 */
	 private String buildDataTruncationErrorMsg(String msg, String fileName){
		 String message = "";
			int i = msg.indexOf("Check the parameter mapping for the ");
			int iLenght = "Check the parameter mapping for the ".length();
			int j = msg.indexOf("property");
			if(i>0){
				String fieldName = msg.substring(i+iLenght,j);
				message="Please correct the Data size of the field, "+fieldName+"in the file '"+fileName+"'";
			}else{
				message="Data Truncation Error on loading the file "+fileName;
			}	
		 return message;
	 }

	/**
	 * Method to validate , if the Excel file is valid or not.
	 * 
	 * @param fileName java.lang.String
	 * @param uploadService com.penske.apps.vsportal.service.IUploadService
	 * @return java.lang.String
	 */
	 protected abstract String validateFile(String fileName, InputStream input, UploadService<T> uploadService, boolean pilot);

	/**
	 * Method to check the Extension of the uploaded file
	 * 
	 * @author 600123480
	 * @param fileName java.lang.String
	 * @param mimeTypeList java.util.List
	 * @return int
	 * @throws java.lang.Exception
	 */
	protected int checkExtension(String fileName, List<MimeTypeModel> mimeTypeList) throws Exception {

		int retVal = 2;
		try {

			int index = fileName.lastIndexOf(".");
			if (index == -1) {
				// File Name without any extension
				retVal = 1;
			} else {
				/*
				 * Checking the extension of input file equals the allowed extension
				 */
				String contentType = getContentType(fileName, mimeTypeList);

				// Some content type found against the extension of the file
				if (!contentType.equals(""))
					retVal = 0;// 0 means ok

			}
		} catch (Exception e) {
			retVal = 3;// 3 means some error has occured
			throw e;
		}

		return retVal;
	}

	private static String getContentType(String fileName, List<MimeTypeModel> mimeTypeList) {
		String retVal = "";
		int size = 0;
		if (mimeTypeList != null) {
			size = mimeTypeList.size();
		}
		MimeTypeModel objModel = null;
		String extn = getExtn(fileName);

		for (int i = 0; i < size; i++) {
			objModel = mimeTypeList.get(i);
			if (objModel.getExtn().trim().equalsIgnoreCase(extn)) {
				retVal = objModel.getMimeType();
				break;
			}
		}
		return retVal;
	}
	
	private static String getExtn(String fileName) {
		String retVal = "";
		int index = 0;
		if (fileName != null) {
			index = fileName.lastIndexOf(".");
			int len = fileName.length();
			retVal = fileName.substring(index + 1, len);
			retVal = retVal.trim();
		}
		return retVal;
	}
	
	/**
	 * Method to upload Excel sheet
	 * 
	 * @author 600123480
	 * @param input
	 *            java.io.InputStream
	 * @throws java.lang.Exception
	 */
	private String upload(InputStream input, UploadService<T> objUploadService, boolean pilot) throws Exception {
		List<T> modelObjectList = new ArrayList<T>();
		String message = "";
		try {			
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			/**
			 * Adding limit to number of rows uploaded using Vendor exception
			 * Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
			 */
//			if(objUploadService.getClass().getName().indexOf("VendorReportService") != -1){
//				logger.debug("*****************VENDOR REPORT**************************");
//				/*
//				 *  Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
//				 */
//				int uploadLimit= Integer.parseInt(objUploadService.getUploadLimit());
//				logger.debug("*****************VENDOR REPORT******uploadLimit*******************"+uploadLimit);
//				if(totalrows > uploadLimit ){
//					Exception e = new Exception("File contains "+totalrows+" records to upload. please limit to "+uploadLimit+" Rows");
//					logger.debug("File contains "+totalrows+" records to upload. please limit to "+uploadLimit+"");
//					throw e;
//				}
//			}
				rows = sheet.rowIterator(); // Get the rows and  iterate
				populateRecords(rows,modelObjectList,pilot);// Populate the data from each row into the model object			
				if (modelObjectList.size()>0){
					message = objUploadService.uploadExcelDataList(modelObjectList); //Upload the Excel Data List
				}else{
					Exception e = new Exception("File contains no records to upload");
					logger.debug("File contains no records to upload");
					throw e;
				}
			
		} catch (IOException ex) {
			logger.debug("IO Error in ExcelUploadHandler.upload "
					+ ex.getMessage());
			throw ex;
		} catch (Exception e) {
			logger.debug("Error in ExcelUploadHandler.upload "
							+ e.getMessage());
			throw e;
		}
		return message;
	}

	/**
	 * Populate each records into its appropriate Model Object
	 * 
	 * @author 600123480
	 * @param rows java.util.Iterator
	 * @param modelObjectList java.util.List
	 * @throws Exception java.lang.Exception
	 */
	private void populateRecords(Iterator<Row> rows,List<T> modelObjectList, boolean pilot) throws Exception{
		
		boolean readRecords = true;
		int rowNum = 0;
	
		T modelObject = null;
		while (rows.hasNext()&& readRecords == true) {
			Row row = rows.next();
			
			rowNum = row.getRowNum();

			//logger.debug("row number:"+rowNum);
			if (rowNum >= getStartRow(pilot) && readRecords == true) {
				modelObject = createModelObject(pilot);
				// Iterate over each cell in the row and print out the
				// cell's content				
				readRecords = populateCell(row,readRecords,modelObject); // Populate the data from each cell into the appropriate attributes of Model object
				collectExcelDataList(readRecords, modelObjectList, modelObject); // Once all the data for a row / record has been obtained, 
																				 //then add them to the list.
			}
		
		}
		
	}
	
	/**
	 * Method to Populate to each cell value into the appropriate attributes / properties in the Model object
	 * 
	 * @author 600104283
	 * @param row org.apache.poi.ss.usermodel.Row
	 * @param readRecords boolean
	 * @param modelObject java.lang.Object
	 */
	private boolean populateCell(Row row,boolean readRecords, T modelObject) throws Exception{
		Iterator<Cell> cells = row.cellIterator();
		int cellNum = 0;
		String value = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		while (cells.hasNext() && readRecords == true) {
			Cell cell = cells.next();
			cellNum = cell.getColumnIndex();

	        // POI 3.15 deprecated both getCellType() and getCellTypeEnum() in preparation for transitioning to
	        // making getCellType() return an enum in 4.0.
	        // As of 2018-11-28, we are using 3.15. If we upgrade to 4.0, this should be switched to use the
	        // getCellType() method which returns an enum and is not deprecated.
	        @SuppressWarnings("deprecation")
			CellType cellType = cell.getCellTypeEnum();
			
			switch (cellType) {
			case NUMERIC:
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					try{
						value = sdf.format(cell.getDateCellValue());
					} catch(Exception e) {
						logger.debug("Something went wrong converting excel date. --- " + e.getMessage());
					}
				} else {
					if(cell.getCellStyle().getDataFormat() == 0) {
						value = new HSSFDataFormatter().formatCellValue(cell);
					} else {
						value = String.valueOf(cell.getNumericCellValue());                 
					}
				}
				readRecords = populateExcelData(value, modelObject,cellNum,row);
				break;
			case STRING:
				value = String.valueOf(cell.getRichStringCellValue());
				readRecords = populateExcelData(value, modelObject,cellNum,row);
				break;
			case FORMULA:
				value=String.valueOf(cell.getNumericCellValue());
	            readRecords = populateExcelData(value,modelObject,cellNum,row);
	            break;
			case BLANK:
				value = "";
				readRecords = populateExcelData(value,modelObject,cellNum,row);      
				break;
			default:
				value = "unsuported cell type";
				break;
			}                   
		}
		return readRecords;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}	


