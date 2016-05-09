package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.PrimaryDatabase;
import com.penske.apps.adminconsole.model.SearchTemplateForm;


public interface TabDao {
	public void updateDefaultTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
