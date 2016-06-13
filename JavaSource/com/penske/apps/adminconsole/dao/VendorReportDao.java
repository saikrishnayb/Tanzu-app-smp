package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.VendorReport;
import com.penske.apps.adminconsole.model.VendorReportResults;

/**
 * This interface will map to vendor-report-mapper
 * and will provide database functionality for
 * uploading Vendor excel files.
 * @author 600139251
 *
 */
public interface VendorReportDao {

	public String processVendorReportStoredProc(VendorReportResults aVendorReportResults) throws Exception;
	
	public List<MimeTypeModel> getMimeTypeList() throws Exception;
	
	public void insertVendorReport(VendorReport vendorReport) throws Exception;
	
	public String getUploadLimit() throws Exception;
	
}
