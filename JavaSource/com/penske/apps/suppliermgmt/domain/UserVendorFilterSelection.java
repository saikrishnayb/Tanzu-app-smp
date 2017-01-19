package com.penske.apps.suppliermgmt.domain;


public class UserVendorFilterSelection {

    private int orgId;
    private int vendorId;
    
    //MyBatis only
    protected UserVendorFilterSelection () {}

    public int getOrgId() {
        return orgId;
    }
    public int getVendorId() {
        return vendorId;
    }

    @Override
    public String toString() {
        return "UserVendorFilterSelection [orgId=" + orgId + ", vendorId=" + vendorId + "]";
    };
    
}
