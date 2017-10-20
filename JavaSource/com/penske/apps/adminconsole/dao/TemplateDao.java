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
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllTemplateNames();
	
	@NonVendorQuery //TODO: Review Query
	public List<SearchTemplate> getAllTemplates();
	
	@NonVendorQuery //TODO: Review Query
	public SearchTemplate selectSearchTemplate(@Param("templateId") int templateId);
}
