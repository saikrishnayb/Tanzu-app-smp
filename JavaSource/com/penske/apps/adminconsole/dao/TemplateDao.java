package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
import com.penske.apps.adminconsole.model.SearchTemplate;

/**
 * This interface is used for queries to the database for the Template Management page.
 * 
 * @author 600143568
 */

public interface TemplateDao {
	public List<String> getAllTemplateNames();
	
	public List<SearchTemplate> getAllTemplates();
	
	public SearchTemplate selectSearchTemplate(@Param("templateId") int templateId);
}
