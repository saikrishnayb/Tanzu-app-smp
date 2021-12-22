/**
 * 
 */
package com.penske.apps.adminconsole.model;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.penske.apps.adminconsole.service.UploadService;
import com.penske.apps.adminconsole.util.VsportalConstants;

/**
 * @author 600125544
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public class VendorUploadHandler extends ExcelUploadHandler<VendorReport>{
	
	private static final Logger logger = LogManager.getLogger(VendorUploadHandler.class);
	private static final String EOR = "Report Completed!";
	
	private int penkseIdColNum = 0;
	private boolean isValidFile = false;
	private List<VendorReport> vendorReportLst = new ArrayList<VendorReport>();
	private String reportId = new String();
	
	/* (non-Javadoc)
	 * @see com.penske.apps.vsportal.excel.handler.ExcelUploadHandler#collectExcelDataList(boolean, java.util.List, java.lang.Object)
	 */
	@Override
	protected void collectExcelDataList(boolean isReadable, List<VendorReport> vendorReportList, VendorReport modelObject) throws Exception {
		if (isReadable == true) {
			if (isValidFile){
				vendorReportList.addAll(vendorReportLst);
				vendorReportLst = new ArrayList<VendorReport>(); //clear the old list
				
				//assign PENSKE REPORT ID to all the records (incl header)
				VendorReport vr = new VendorReport();
				Iterator<VendorReport> itr = vendorReportList.iterator();
				while(itr.hasNext()){
					vr = (VendorReport) itr.next();
					vr.setReportId(reportId);
				}
			}else{
				Exception e = new Exception("Row does not contain Penske Report Id");
				logger.debug("File contains no records to upload");
				throw e;
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.penske.apps.vsportal.excel.handler.ExcelUploadHandler#createModelObject()
	 */
	@Override
	protected VendorReport createModelObject(boolean pilot) {
		return new VendorReport();
	}

	/* (non-Javadoc)
	 * @see com.penske.apps.vsportal.excel.handler.ExcelUploadHandler#getStartRow()
	 */
	@Override
	protected int getStartRow(boolean pilot) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.penske.apps.vsportal.excel.handler.ExcelUploadHandler#populateExcelData(java.lang.String, java.lang.Object, int, org.apache.poi.ss.usermodel.Cell)
	 */
	@Override
	protected boolean populateExcelData(String value, VendorReport vendorReportObj, int cellNum, Row row) throws Exception {
		//VendorReport vendorReport = (VendorReport)vendorReportObj;
		VendorReport vendorReport = new VendorReport();
		boolean readRecords = true;
		int rowNum = row.getRowNum();
		
		
    	if(null != value){
    		if(value.equals(EOR)){
    			readRecords = false;
    		}else{	
    			if(rowNum==0 && !isValidFile){
    				Iterator<Cell> colItr = row.cellIterator();
    				while(colItr.hasNext()){
    					Cell nextCell = colItr.next();
    					//We are using POI 3.15 - once we upgrade to POI 4.0, this will need to be changed.
    			        @SuppressWarnings("deprecation")
    					CellType nextCellType = nextCell.getCellTypeEnum();
    					if(nextCellType == CellType.STRING   
    						&& VsportalConstants.PENSKE_REPORT_ID.equalsIgnoreCase(String.valueOf(nextCell.getRichStringCellValue()).trim())){
    						penkseIdColNum = nextCell.getColumnIndex();
    						isValidFile = true;
    					}
    				}//end while for first now all cells
    			}//rowNum=0 condition
    			//if(isValidFile && cellNum!=penkseIdColNum){
    			if(isValidFile){
    				//if(null!=value && value.trim().length()>0){
    					value = value.trim();
    					if(value.length()>55){
    						value = value.substring(0, 54);
    					}
    					if(row.getRowNum()==1 && cellNum== penkseIdColNum){
    						reportId = value;
    					}
	    				vendorReport.setColSeq(new BigDecimal(cellNum));
	        			vendorReport.setRowSeq(new BigDecimal(rowNum));
	        			vendorReport.setReportId(reportId);
	        			if(null==value || value.length()==0){
	        				value = " ";
	        				//logger.info("Cell Content=NOTHING"+ "cellNum"+cellNum+" Row"+rowNum + ". Replacing cell value with blank space: "+value);
	        			}
	        			vendorReport.setColValue(value);
	        			if(rowNum>0){
	        				vendorReport.setIsHeader(VsportalConstants.VENDOR_REPORT_CONTENT);
	        			}else{
	        				vendorReport.setIsHeader(VsportalConstants.VENDOR_REPORT_HEADER);
	        			}
	        			vendorReport.setUserId(getUserId());
	        			readRecords = true;
	        			vendorReportLst.add(vendorReport);
	        			//logger.info("Report Id:"+ reportId +"cellNum"+cellNum+" Row"+rowNum + " value"+value);
    				//}
    			}
    		}
    		
    	}
    	return readRecords;
	}
	
    /**
	 * Method to validate , if the Excel file is valid or not.
	 * 
	 * @param fileName java.lang.String
	 * @param uploadService com.penske.apps.vsportal.service.IUploadService
	 * @return java.lang.String
	 */
	 @Override
	public String validateFile(String fileName, InputStream input, UploadService<VendorReport> uploadService, boolean pilot){
		String message = "";

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
}
