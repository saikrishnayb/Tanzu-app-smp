package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * This interface is used for queries to the database for the Template Management page.
 * 
 * @author Mark.Weaver 600144069
 */
@DBSmc
public interface SearchTemplateDao {

    @NonVendorQuery
    public List<SearchTemplate> selectAllSearchTemplates();

    @NonVendorQuery
    public SearchTemplate selectSearchTemplate(@Param("templateId") int templateId);

    @NonVendorQuery
    public SearchTemplate selectSearchTemplateByName(@Param("templateName") String templateName, @Param("templateId") int templateId);

    @NonVendorQuery
    public void updateSearchTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
