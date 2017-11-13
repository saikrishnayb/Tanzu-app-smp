package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.SearchTemplateForm;


public interface TabDao {

    @NonVendorQuery
    public void updateDefaultTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
