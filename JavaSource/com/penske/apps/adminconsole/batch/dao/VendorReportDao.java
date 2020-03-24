package com.penske.apps.adminconsole.batch.dao;

import java.util.Collection;
import java.util.List;

import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.VendorReport;
import com.penske.apps.adminconsole.model.VendorReportResults;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface will map to vendor-report-mapper
 * and will provide database functionality for
 * uploading Vendor excel files.
 * @author 600139251
 *
 */
@DBSmc
public interface VendorReportDao {

	@NonVendorQuery //TODO: Review Query
	public String processVendorReportStoredProc(VendorReportResults aVendorReportResults) throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public List<MimeTypeModel> getMimeTypeList() throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public void insertVendorReport(Collection<VendorReport> vendorReports) throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public String getUploadLimit() throws Exception;
	
}
