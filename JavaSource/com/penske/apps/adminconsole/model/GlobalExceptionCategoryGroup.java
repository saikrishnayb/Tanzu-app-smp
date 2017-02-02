package com.penske.apps.adminconsole.model;


public class GlobalExceptionCategoryGroup {
    
    private String poCategorySubcategory;
    private Vendor vendor;

    //Mybatis only
    protected GlobalExceptionCategoryGroup () {}

    public String getPoCategorySubcategory() {
        return poCategorySubcategory;
    }
    public Vendor getVendor() {
        return vendor;
    };
    
}
