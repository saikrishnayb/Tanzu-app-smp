package com.penske.apps.suppliermgmt.domain;

import java.util.List;

import com.penske.apps.adminconsole.model.Vendor;

public class Organization {

    private int organizationId;
    private String name;
    private String description;
    private int parentOrganizationId;
    
    private List<Vendor> vendors;
    
    // MyBatis Only
    protected Organization() {}

    
    public int getOrganizationId() {
        return organizationId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getParentOrganizationId() {
        return parentOrganizationId;
    }
    public List<Vendor> getVendors() {
        return vendors;
    }

    @Override
    public String toString() {
        return "Organization [organizationId=" + organizationId + ", name=" + name + ", description=" + description
                + ", parentOrganizationId=" + parentOrganizationId + ", vendors=" + vendors + "]";
    };
    
}
