package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.model.VendorReportResults;

/**
 * This interface will define the classes that will upload excel docs.
 * @author 600139251
 *
 */
@Service
public interface UploadService {
		
	/**
	 * @return
	 * @throws Exception
	 */
	public List getMimeTypeList() throws Exception;

	/**
	 * @param transportList
	 * @return
	 * @throws Exception
	 */
	public String uploadExcelDataList(List transportList) throws Exception;
	
	public void insert(Object modelObject) throws Exception ;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List processVendorReportStoredProc(VendorReportResults vendorReportResults) throws Exception;
	
	/**
	 * Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
	 * @return
	 * @throws Exception
	 */	
	public String getUploadLimit()throws Exception;
}

