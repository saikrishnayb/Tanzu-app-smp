package com.penske.apps.adminconsole.service;

import java.util.Collection;
import java.util.List;

import com.penske.apps.adminconsole.model.MimeTypeModel;

/**
 * This interface will define the classes that will upload excel docs.
 * @author 600139251
 *
 */
public interface UploadService<T> {
		
	/**
	 * @return
	 * @throws Exception
	 */
	public List<MimeTypeModel> getMimeTypeList() throws Exception;

	/**
	 * @param transportList
	 * @return
	 * @throws Exception
	 */
	public String uploadExcelDataList(List<T> transportList) throws Exception;
	
	/**
	 * Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
	 * @return
	 * @throws Exception
	 */	
	public String getUploadLimit()throws Exception;

    void insert(Collection<T> collection) throws Exception;

}

