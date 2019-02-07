package com.penske.apps.suppliermgmt.domain;

import com.penske.apps.adminconsole.enums.PoCategoryType;

public class ShipThruLeadTime {

    private int leadTimeId;
    private PoCategoryType poCategory;
    private String oem;
    private String model;
    private String shipThruUpFitter;
    private String destinationState;
    private int leadDays;
    
    //MyBatis constructer
    protected ShipThruLeadTime() {};
    
    public ShipThruLeadTime(PoCategoryType poCategory, int leadDays) {
        
        if(poCategory == null)
            throw new IllegalArgumentException("poCategory can not be null");
        
        if(leadDays < 1)
            throw new IllegalArgumentException("leadDays should be greater than 0");
        
        this.poCategory = poCategory;
        this.leadDays = leadDays;
    }
    
    public int getLeadTimeId() {
        return leadTimeId;
    }
    public PoCategoryType getPoCategory() {
        return poCategory;
    }
    public String getOem() {
        return oem;
    }
    public String getModel() {
        return model;
    }
    public String getShipThruUpFitter() {
        return shipThruUpFitter;
    }
    public String getDestinationState() {
        return destinationState;
    }
    public int getLeadDays() {
        return leadDays;
    }
    
    
    @Override
    public String toString() {
        return "ShipThruLeadTime [leadTimeId=" + leadTimeId + ", poCategory=" + poCategory + ", oem=" + oem + ", model="
               + model + ", shipThruUpFitter=" + shipThruUpFitter + ", destinationState=" + destinationState
               + ", leadDays=" + leadDays + "]";
    }
    
}
