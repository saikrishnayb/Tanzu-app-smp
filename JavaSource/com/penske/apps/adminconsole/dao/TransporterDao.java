package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.Transport;

/**
 * This interface will map to transport-search-mapper.
 * It provides functionality for uploading transport excel documents.
 * @author 600139251
 *
 */
public interface TransporterDao {
	public List<MimeTypeModel> getMimeTypeList() throws Exception;
	public void insertTransporter(Transport transporter) throws Exception;
}
