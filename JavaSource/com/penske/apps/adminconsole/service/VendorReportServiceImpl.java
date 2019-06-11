package com.penske.apps.adminconsole.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.collections4.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.batch.dao.VendorReportDao;
import com.penske.apps.adminconsole.model.MimeTypeModel;
import com.penske.apps.adminconsole.model.VendorReport;
import com.penske.apps.adminconsole.model.VendorReportResults;
import com.penske.apps.adminconsole.util.VsportalConstants;
import com.penske.apps.suppliermgmt.annotation.VendorUploadService;

/**
 * This class will implement the excel upload service.
 * @author 600139251
 *
 */
@Service
@VendorUploadService
public class VendorReportServiceImpl implements UploadService<VendorReport>
{
    private static Logger logger = Logger.getLogger(VendorReportServiceImpl.class);

    @Autowired
    VendorReportDao objDao;
    String reportId = "";

    private List<VendorReportResults> processVendorReportStoredProc(VendorReportResults aVendorReportResults) throws Exception
    {
        objDao.processVendorReportStoredProc(aVendorReportResults);
        ArrayList<VendorReportResults> list = new ArrayList<VendorReportResults>();

        list.add(aVendorReportResults);

        logger.debug("List size in processVendorReportStoredProc:" + list.size());
        return list;
    }

    /* (non-Javadoc)
     * @see com.penske.apps.vsportal.service.IUploadService#getMimeTypeList()
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.penske.apps.vsportal.service.IUploadService#insert(java.lang.Object)
     */
    @Override
    public void insert(Collection<VendorReport> vendorReports) throws Exception
    {
        try
        {
            objDao.insertVendorReport(vendorReports);
        }
        catch (Exception e)
        {
            logger.debug("Exception in insertTransporter. Exception is "+ e.getMessage());
            throw e;
        }
    }
    

    /* (non-Javadoc)
     * @see com.penske.apps.vsportal.service.IUploadService#uploadExcelDataList(java.util.List)
     */
    @Transactional
    @Override
    public String uploadExcelDataList(List<VendorReport> vendorReportList) throws Exception
    {
        String message = "";

        //added for debug - Rajkumar
        long start = System.currentTimeMillis();
        int i=0;
        logger.info("Inserting "+i+" record Started at time : " + start);
        
        
        try {
            
            List<List<VendorReport>> subLists = ListUtils.partition(vendorReportList, 2000);

            for (List<VendorReport> subList : subLists)
                insert(subList);
            
            i=vendorReportList.size();
            reportId = vendorReportList.get(vendorReportList.size() - 1).getReportId();

        }
        catch (Exception e) {
            logger.debug(" ERROR while trying to insert the records "
                    + e.getMessage());
            throw e;
        }

        //added for debug - Rajkumar
        long elapsed = System.currentTimeMillis() - start;
        DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins,' ss 'seconds'");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        logger.info("Insert Completed "+i+" record Took : : " + df.format(new Date(elapsed)));
        //end

        VendorReportResults vendorReportResults = new VendorReportResults();
        vendorReportResults.setReportId(reportId);
        vendorReportResults.setCount(new BigDecimal(vendorReportList.size()));

        //added for debug - Rajkumar
        start = System.currentTimeMillis();
        logger.info("processVendorReportStoredProc Started at time : " + start);
        //end

        List<VendorReportResults> vendorReportResultsLst = processVendorReportStoredProc(vendorReportResults);
        if(vendorReportResultsLst.size()>0)
        {
            Iterator<VendorReportResults> itr = vendorReportResultsLst.iterator();
            while(itr.hasNext())
            {
                VendorReportResults vendorReportResult = itr.next();
                message = vendorReportResult.getMessage();

                if(null!=message && message.length()>0)
                {
                    message = message.trim();
                }
            }
            if(null==message || message.trim().length()==0 || !message.trim().equalsIgnoreCase(VsportalConstants.VENDOR_REPORT_RETURN_MSG))
            {
                logger.debug("ERROR raised by Stored Proc#NVOBNIMSP while trying to process the records");
                throw new Exception(message);
            }
        }
        else
        {
            logger.debug("ERROR while trying to process the records..");
            throw new Exception("Stored Proc#NVOBNIMSP didn't return any message, while processing the records");
        }

        //added for debug - Rajkumar
        elapsed = System.currentTimeMillis() - start;
        df = new SimpleDateFormat("HH 'hours', mm 'mins,' ss 'seconds'");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        logger.info("processVendorReportStoredProc Completed time :  " + df.format(new Date(elapsed)));
        //end

        return message;
    }

    /**
     * Retrieves Upload Row limit for Vendor Upload from vfjapplnk table
     * @return
     * @throws Exception
     */
    @Override
    public String getUploadLimit() throws Exception
    {
        String uploadlimit = "0";

        try
        {
            uploadlimit = objDao.getUploadLimit();
        }
        catch (Exception e)
        {
            logger.debug("Exception in insertTransporter. Exception is "+ e.getMessage());
            throw e;
        }

        return uploadlimit.trim();
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

}
