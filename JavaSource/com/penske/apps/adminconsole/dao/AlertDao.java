package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;

/**
 * This interface is used for queries to the database for the Alerts page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface AlertDao {

    @NonVendorQuery
    public List<Alert> getAllAlertsAndHeaders();

    @NonVendorQuery
    public List<SearchTemplate> getAllTemplateNames();

    @NonVendorQuery
    public int checkForTemplateId(@Param("alertId") int alertId, @Param("templateId") int templateId);

    @NonVendorQuery
    public void modifyAlertHeader(Alert alert);

    @NonVendorQuery
    public void modifyAlertDetail(Alert alert);
}
