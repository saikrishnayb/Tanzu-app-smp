package com.penske.apps.suppliermgmt.domain;


public class UserVendorFilterSelection {

    private int orgId;
    private int vendorId;
    private boolean isActive;
    
    //MyBatis only
    protected UserVendorFilterSelection () {}

    public int getOrgId() {
        return orgId;
    }
    public int getVendorId() {
        return vendorId;
    }
    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "UserVendorFilterSelection [orgId=" + orgId + ", vendorId=" + vendorId + "]";
    };
    
}
