package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.SearchTemplateForm;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;


public interface TabDao {

    @NonVendorQuery
    public void updateDefaultTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
