package com.penske.apps.suppliermgmt.domain;


public class AlertCount {

    private String alertKey;
    private int alertCount;
    private String helpText;
    private String nonCompliant;
    private int displaySequence;
    
    //MyBatis only
    protected AlertCount(){}

    
    public String getAlertKey() {
        return alertKey;
    }
    public int getAlertCount() {
        return alertCount;
    }
    public String getHelpText() {
        return helpText;
    }
    public String getNonCompliant() {
        return nonCompliant;
    }
    public int getDisplaySequence() {
        return displaySequence;
    }

    @Override
    public String toString() {
        return "AlertCount [alertKey=" + alertKey + ", alertCount=" + alertCount + ", helpText=" + helpText
                + ", nonCompliant=" + nonCompliant + ", displaySequence=" + displaySequence + "]";
    };
    
}
