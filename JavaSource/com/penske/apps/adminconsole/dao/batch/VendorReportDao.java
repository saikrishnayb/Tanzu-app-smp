package com.penske.apps.adminconsole.dao.batch;

import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
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

	@NonVendorQuery //TODO: Review Query
	public String processVendorReportStoredProc(VendorReportResults aVendorReportResults) throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public List<MimeTypeModel> getMimeTypeList() throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public void insertVendorReport(VendorReport vendorReport) throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public String getUploadLimit() throws Exception;
	
}
