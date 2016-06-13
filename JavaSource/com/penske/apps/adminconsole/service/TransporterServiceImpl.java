package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.TransporterDao;
import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.Transport;
import com.penske.apps.adminconsole.model.VendorReportResults;

/**
 * This class will handle the uploading of excel transport docs.
 * @author 600139251
 *
 */
@Service
public class TransporterServiceImpl  implements UploadService{

	private static Logger logger = Logger.getLogger(TransporterServiceImpl.class);
	
	@Autowired
	TransporterDao objDao;

	/**
	 * Method to get the list of mime type
	 * @return java.util.List
	 */
	public List<MimeTypeModel> getMimeTypeList() throws Exception 
	{
		List<MimeTypeModel> obj = objDao.getMimeTypeList();
		if (obj == null)
		{
			obj = new ArrayList<MimeTypeModel>();
		}
		logger.debug("List size in getMimeTypeList:" + obj.size());
		return obj;
	}

	/**
	 * Method to insert the Transporter Data
	 * 
	 * @author 600123480
	 * @param obj java.lang.Object
	 * @exception java.lang.Exception
	 */
	public void insert(Object obj) throws Exception 
	{
		Transport transporter = (Transport)obj;
		try 
		{
			objDao.insertTransporter(transporter);	
		}
		catch (Exception e) 
		{
			logger.debug("Exception in insertTransporter. Exception is "+ e.getMessage());
			throw e;
		}
	}


	/**
	 * Method to upload Excel Data List
	 * 
	 * @author 600123480
	 * @penskeEmailList java.util.List
	 * @exception java.lang.Exception
	 */	
	@Override
	public String uploadExcelDataList(List transportList) throws Exception {

		Transport transport = null;
		String message = "";
		Iterator<Transport> It = transportList.iterator();

		while (It.hasNext()) {
			try {
				transport = (Transport) It.next();
				insert(transport);
			} catch (Exception e) {
				logger.debug(" ERROR while trying to insert the records "
						+ e.getMessage());
				throw e;
			}
		}
		return message;

	}

	/* (non-Javadoc)
	 * @see com.penske.apps.vsportal.service.IUploadService#processVendorReportStoredProc(com.penske.apps.vsportal.model.VendorReportResults)
	 */
	public List processVendorReportStoredProc(VendorReportResults vendorReportResults) throws Exception {
		// not requried!
		return null;
	}

	/*
	 * THis is Implemented to Limit number of rows uploaded, Current implementation is only for Vendor upload Flow.
	 * @see com.penske.apps.vsportal.service.IUploadService#getUploadLimit()
	 */
	public String getUploadLimit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
