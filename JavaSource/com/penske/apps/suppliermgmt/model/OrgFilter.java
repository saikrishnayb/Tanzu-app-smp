package com.penske.apps.suppliermgmt.model;

import java.util.ArrayList;
import java.util.List;

import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.suppliermgmt.domain.Organization;

public class OrgFilter {

    private int orgId;
    private String orgName;
    private String orgDescription;
    private List<VendorFilter> vendorFilters = new ArrayList<VendorFilter>();
    
    private boolean orgSelected;
    private boolean noSelectedVendors;
    
    public OrgFilter (Organization organization) {
        this(organization, null);
    }
    
    public OrgFilter (Organization organization, List<Integer> userVendorSelections) {
        
        if(organization == null)
            throw new IllegalArgumentException("Organization must not be null");
        
        boolean hasNoUserVendorSelections = userVendorSelections == null || userVendorSelections.size() == 0;;
        
        this.orgId = organization.getOrganizationId();
        this.orgName = organization.getName();
        this.orgDescription = organization.getDescription();
        
        if(hasNoUserVendorSelections) return;
        
        boolean allVendorsSelected = true;
        
        for (Vendor vendor : organization.getVendors()) {
            
            int vendorId = vendor.getVendorId();
            boolean isSelectedVendor = userVendorSelections.contains(vendorId);
            
            if(!isSelectedVendor) allVendorsSelected = false;
            
            vendorFilters.add(new VendorFilter(vendor, isSelectedVendor));
            
        }
        
        this.orgSelected = allVendorsSelected;
        
    }

    public int getOrgId() {
        return orgId;
    }
    public String getOrgName() {
        return orgName;
    }
    public String getOrgDescription() {
        return orgDescription;
    }
    public List<VendorFilter> getVendorFilters() {
        return vendorFilters;
    }
    public boolean isOrgSelected() {
        return orgSelected;
    }
    public boolean isNoSelectedVendors() {
        return noSelectedVendors;
    }

    @Override
    public String toString() {

        return "OrgFilter [orgId=" + orgId + ", orgName=" + orgName + ", orgDescription=" + orgDescription
                + ", vendorFilters=" + vendorFilters + ", orgSelected=" + orgSelected + ", noSelectedVendors="
                + noSelectedVendors + "]";
    }
    
}
