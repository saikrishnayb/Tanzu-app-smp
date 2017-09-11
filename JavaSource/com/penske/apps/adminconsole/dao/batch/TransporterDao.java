package com.penske.apps.adminconsole.dao.batch;

import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.Transport;

/**
 * This interface will map to transport-search-mapper.
 * It provides functionality for uploading transport excel documents.
 * @author 600139251
 *
 */
public interface TransporterDao {
	@NonVendorQuery //TODO: Review Query
	public List<MimeTypeModel> getMimeTypeList() throws Exception;
	
	@NonVendorQuery //TODO: Review Query
	public void insertTransporter(Transport transporter) throws Exception;
}
