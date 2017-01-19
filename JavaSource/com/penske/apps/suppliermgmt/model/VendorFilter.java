package com.penske.apps.suppliermgmt.model;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.adminconsole.model.Vendor;

public class VendorFilter {

    private int vendorId;
    private int vendorNumber;
    private String vendorCorp;
    private String city;
    private String state;
    private  String zipCode; 
    
    private boolean selected = false;
    
    public VendorFilter (Vendor vendor) {
        this(vendor, false);
    }
    
    public VendorFilter (Vendor vendor, boolean selected) {
        
        if(vendor == null)
            throw new IllegalArgumentException("Vendor must not be null");
        
        this.vendorId = vendor.getVendorId();
        this.vendorNumber = vendor.getVendorNumber();
        this.vendorCorp = vendor.getCorpCode();
        this.city = vendor.getCity();
        this.state = vendor.getState();
        this.zipCode = vendor.getZipCode();
        this.selected = selected;
    }
    
    //Modified Accessors
    public String getFormattedAddress() {
        //Atm, if it has at least one of these then we should display it I guess
        boolean noAddressInfo = StringUtils.isBlank(city) && StringUtils.isBlank(state) && StringUtils.isBlank(zipCode);
        return noAddressInfo? "" : "- " + city + ", " + state + " " + zipCode;
    }

    public int getVendorId() {
        return vendorId;
    }
    public int getVendorNumber() {
        return vendorNumber;
    }
    public String getVendorCorp() {
        return vendorCorp;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {

        return "VendorFilter [vendorId=" + vendorId + ", vendorNumber=" + vendorNumber + ", vendorCorp=" + vendorCorp
                + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", selected=" + selected + "]";
    }
    
}
