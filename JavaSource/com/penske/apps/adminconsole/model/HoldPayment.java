package com.penske.apps.adminconsole.model;

public class HoldPayment {
	
	private int holdPaymentId;
    private int componentId;
    private int vendorId;
    
    protected HoldPayment() {}
    
    public HoldPayment(int componentId, int vendorId) {
    	this.vendorId = vendorId;
    	this.componentId = componentId;
    }
    
    public int getComponentId() {
		return componentId;
	}
    
    public int getHoldPaymentId() {
		return holdPaymentId;
	}
    
    public int getVendorId() {
		return vendorId;
	}

}
