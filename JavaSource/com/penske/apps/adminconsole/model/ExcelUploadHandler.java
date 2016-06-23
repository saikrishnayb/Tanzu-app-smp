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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
 */
public abstract class ExcelUploadHandler {

	/**
	 * Declare Variables
	 */
	// Declare logger
	static Logger logger = null;

	// Declare workbook
	HSSFWorkbook workBook = null;

	// Declare sheet
	HSSFSheet workSheet = null;

	// Declare sheet
	HSSFCellStyle cellStyle = null;

	// Declare font
	HSSFFont cellFont = null;

	List transports = new ArrayList();

	List excelBookList = new ArrayList();
	
	String userId = new String();
	
	public static String FORWARD = "FORWARD";

	/**
	 * Constructors to create ExcelUploadHandler object
	 * 
	 */
	public ExcelUploadHandler() {
		logger = Logger.getLogger("ExcelUploadHandler");
	}
	
	/**
	 * Method to Create the Model object. This object will vary based on the excel sheet uploaded.
	 * Eg., Transporter Excel sheet data is populated into Transporter Model object.
	 * Ford Data Excel Sheet is populated into PenskeEmail Model Object.
	 * 
	 * @author Johnson Jayaraj 
	 * @return java.lang.Object
	 */
	protected abstract Object createModelObject(boolean pilot);

	/**
	 * Method to Populate the Excel Data into the Model objects
	 * 
	 * @author Johnson Jayaraj 
	 * @return boolean
	 */
	protected abstract boolean populateExcelData(String value,
			Object transportObj, int cellNum, HSSFCell cell,HSSFRow row) throws Exception;

	/**
	 * Method to add each and every Model object into a Collection
	 * 
	 * @author Johnson Jayaraj 
	 */
	protected abstract void collectExcelDataList(boolean isReadable,
			List transportList, Object modelObject) throws Exception;

	/**
	 * Method to upload the Excel Data
	 * 
	 * @author Johnson Jayaraj 
	 */
	protected abstract String uploadExcelDataList(List excelDataList,
			UploadService objUploadService) throws Exception;
	
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
	public String saveDocument(String fileName, MultipartFile fileToUpload, UploadService objUploadService, boolean pilot)
	throws Exception {	
		
		//This message is to be sent back to the screen.
		String message ="";
		
		if (!fileToUpload.isEmpty()) //make sure there is something to upload 
		{
			InputStream is = null;
			try {
					is = fileToUpload.getInputStream(); //get the file's input stream
					//To validate the uploaded file; returned message is an error
					message = validateFile(fileName, is, objUploadService, pilot); 

				if(DataUtil.isEmpty(message))
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
				message = FORWARD;
			}
			finally{
				is.close();
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
	 protected abstract String validateFile(String fileName, InputStream input, UploadService uploadService, boolean pilot);

	/**
	 * Method to check the Extension of the uploaded file
	 * 
	 * @author 600123480
	 * @param fileName java.lang.String
	 * @param mimeTypeList java.util.List
	 * @return int
	 * @throws java.lang.Exception
	 */
	protected int checkExtension(String fileName, List mimeTypeList) throws Exception {

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

				String contentType = getContentType(fileName,
						mimeTypeList);

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

	private static String getContentType(String fileName, List mimeTypeList) {
		String retVal = "";
		int size = 0;
		if (mimeTypeList != null) {
			size = mimeTypeList.size();
		}
		MimeTypeModel objModel = null;
		String extn = getExtn(fileName);

		for (int i = 0; i < size; i++) {
			objModel = (MimeTypeModel) mimeTypeList.get(i);
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
	private String upload(InputStream input,
			UploadService objUploadService,
			boolean pilot) throws Exception {
		List modelObjectList = new ArrayList();
		String message = "";
		try {

			POIFSFileSystem fs = new POIFSFileSystem(input); // Getting the instance of POI File System
			HSSFWorkbook wb = new HSSFWorkbook(fs); // Creating a workbook for the uploaded file

			HSSFSheet sheet = wb.getSheetAt(0);
            
			Iterator rows = sheet.rowIterator();
			
			int totalrows = sheet.getLastRowNum();
			/**
			 * Adding limit to number of rows uploaded using Vendor exception
			 * Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
			 */
			if(objUploadService.getClass().getName().indexOf("VendorReportService") != -1){
				logger.debug("*****************VENDOR REPORT**************************");
				/*
				 *  Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
				 */
				int uploadLimit= Integer.parseInt(objUploadService.getUploadLimit());
				logger.debug("*****************VENDOR REPORT******uploadLimit*******************"+uploadLimit);
				if(totalrows > uploadLimit ){
					Exception e = new Exception("File contains "+totalrows+" records to upload. please limit to "+uploadLimit+" Rows");
					logger.debug("File contains "+totalrows+" records to upload. please limit to "+uploadLimit+"");
					throw e;
				}
			}
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
	private void populateRecords(Iterator rows,List modelObjectList, boolean pilot) throws Exception{
		
		boolean readRecords = true;
		int rowNum = 0;
	
		Object modelObject = null;
		while (rows.hasNext()&& readRecords == true) {
			HSSFRow row = (HSSFRow) rows.next();
			
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
	 * @author 600123480
	 * @param row org.apache.poi.hssf.usermodel.HSSFRow
	 * @param readRecords boolean
	 * @param modelObject java.lang.Object
	 */
	private boolean populateCell(HSSFRow row,boolean readRecords,Object modelObject) throws Exception{
		Iterator cells = row.cellIterator();
		int cellNum = 0;
		String value = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		while (cells.hasNext() && readRecords == true) {
			HSSFCell cell = (HSSFCell) cells.next();
			cellNum = cell.getColumnIndex();

			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					 if(HSSFDateUtil.isCellDateFormatted(cell)) {
						 try{
							 value = sdf.format(cell.getDateCellValue());
						 } catch(Exception e) {
							 logger.debug("Something went wrong converting excel date. --- " + e.getMessage());
						 }
					 } else {
						 value = String.valueOf(cell.getNumericCellValue());						 
					 }
					 readRecords = populateExcelData(value, modelObject,cellNum,cell,row);
					break;
				case HSSFCell.CELL_TYPE_STRING:
					 value = String.valueOf(cell.getRichStringCellValue());
					 readRecords = populateExcelData(value, modelObject,cellNum,cell,row);
					break;
				  case HSSFCell.CELL_TYPE_BLANK:
                     value = "";
                     readRecords = populateExcelData(value,modelObject,cellNum,cell,row);	
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


