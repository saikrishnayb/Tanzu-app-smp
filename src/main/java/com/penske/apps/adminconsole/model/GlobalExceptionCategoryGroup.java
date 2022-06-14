package com.penske.apps.adminconsole.model;

import java.util.List;

public class GlobalExceptionCategoryGroup {
    
    private String poCategorySubcategory;
    private List<Vendor> vendors;
    private int poCategoryAssociationId;

    //Mybatis only
    protected GlobalExceptionCategoryGroup () {}

    public String getPoCategorySubcategory() {
        return poCategorySubcategory;
    }
    public Vendor getVendor() {
	    if(vendors == null || vendors.isEmpty())
	    	return null;
	    
	    return vendors.get(0);
    }

	public int getPoCategoryAssociationId() {
		return poCategoryAssociationId;
	}

	public void setPoCategoryAssociationId(int poCategoryAssociationId) {
		this.poCategoryAssociationId = poCategoryAssociationId;
	}

}
