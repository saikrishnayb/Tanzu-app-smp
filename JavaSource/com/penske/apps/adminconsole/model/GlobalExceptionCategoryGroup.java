package com.penske.apps.adminconsole.model;


public class GlobalExceptionCategoryGroup {
    
    private String poCategorySubcategory;
    private Vendor vendor;
    private int poCategoryAssociationId;

    //Mybatis only
    protected GlobalExceptionCategoryGroup () {}

    public String getPoCategorySubcategory() {
        return poCategorySubcategory;
    }
    public Vendor getVendor() {
        return vendor;
    }

	public int getPoCategoryAssociationId() {
		return poCategoryAssociationId;
	}

	public void setPoCategoryAssociationId(int poCategoryAssociationId) {
		this.poCategoryAssociationId = poCategoryAssociationId;
	}

}
