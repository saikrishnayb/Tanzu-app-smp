package com.penske.apps.adminconsole.dao;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.SearchTemplateForm;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

@DBSmc
public interface TabDao {

    @NonVendorQuery
    public void updateDefaultTemplate(@Param("searchTemplateForm") SearchTemplateForm searchTemplateForm);
}
