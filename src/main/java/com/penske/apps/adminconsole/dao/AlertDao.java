package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
@DBSmc
public interface AlertDao {

    @NonVendorQuery
    public List<Alert> getAllAlertsAndHeaders();

    @NonVendorQuery
    public List<SearchTemplate> getAllTemplateNames();

    @NonVendorQuery
    public void modifyAlertHeader(Alert alert);

    @NonVendorQuery
    public void modifyAlertDetail(Alert alert);
}
