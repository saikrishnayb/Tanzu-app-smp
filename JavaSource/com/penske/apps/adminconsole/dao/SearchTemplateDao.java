package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.SearchTemplate;
import com.penske.apps.adminconsole.model.SearchTemplateForm;

/**
 * This interface is used for queries to the database for the Template Management page.
 * 
 * @author Mark.Weaver 600144069
 */

public interface SearchTemplateDao {
	@NonVendorQuery //TODO: Review Query
	public List<SearchTemplate> selectAllSearchTemplates();
	
	@NonVendorQuery //TODO: Review Query
	public SearchTemplate selectSearchTemplate(@Param("templateId") int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public SearchTemplate selectSearchTemplateByName(@Param("templateName") String templateName, @Param("templateId") int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateSearchTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
