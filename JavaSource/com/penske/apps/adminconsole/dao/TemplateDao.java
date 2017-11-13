package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Template Management page.
 * 
 * @author 600143568
 */

public interface TemplateDao {

    @NonVendorQuery
    public List<String> getAllTemplateNames();

    @NonVendorQuery
    public List<SearchTemplate> getAllTemplates();

    @NonVendorQuery
    public SearchTemplate selectSearchTemplate(@Param("templateId") int templateId);
}
