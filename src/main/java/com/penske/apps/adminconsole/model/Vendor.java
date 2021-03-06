package com.penske.apps.adminconsole.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Vendor {

	private int vendorId;						// the vendor ID
	private String vendorName;					// the vendor name
	private int vendorNumber;					// the vendor number
	private String corpCode;					// the corp code
	private String notificationException;			// notification exception [1 = yes, 0 = no]
	private String annualAgreement;				// annual agreement [1 = yes, 0 = no]
	private EditableUser planningAnalyst;				// the user designated as the planning analyst
	private EditableUser supplySpecialist;				// the user designated as the supply specialist
	private String searchMfrCode;
	private List<String> mfrCodes;			// the manufacturer code
	private String companyName;					// the company name (related to the corp code)
	private int orgId;						// the org id this vendor belongs to

	/* Vendor Contacts */
	private VendorContact primaryContact;		// the primary contact
	private VendorContact secondaryContact;		// the secondary contact

	/* Accounts Payable Information */
	private String vendorAddress;				// the vendor street address
	private String attention;					// the attention line of the address
	private String city;						// the vendor city
	private String state;						// the vendor state
	private String zipCode;						// the vendor zip code

	/* Purchasing Information */
	private String paymentTerms;				// the payment terms (in days)
	private String orderName;					// the order name
	private String mailingAddress1;				// the purchase order mailing street address, line 1
	private String mailingAddress2;				// the purchase order mailing street address, line 2
	private String mailingCity;					// the purchase order mailing city
	private String mailingState;				// the purchase order mailing state
	private String mailingZipCode;				// the purchase order mailing zip code
	private String shippingAddress1;			// the shipping address, line 1
	private String shippingAddress2;			// the shipping address, line 2
	private String shippingCity;				// the shipping city
	private String shippingState;				// the shipping state
	private String shippingZipCode;				// the shipping zip code

	private String alertType;	

	// Getters
	public int getVendorId() {
		return vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public int getVendorNumber() {
		return vendorNumber;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public String getAnnualAgreement() {
		return annualAgreement;
	}

	public void setAnnualAgreement(String annualAgreement) {
		this.annualAgreement = annualAgreement;
	}

	public EditableUser getPlanningAnalyst() {
		return planningAnalyst;
	}

	public EditableUser getSupplySpecialist() {
		return supplySpecialist;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public int getOrgId() {
		return orgId;
	}

	public VendorContact getPrimaryContact() {
		return primaryContact;
	}

	public VendorContact getSecondaryContact() {
		return secondaryContact;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public String getAttention() {
		return attention;
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public String getOrderName() {
		return orderName;
	}

	public String getMailingAddress1() {
		return mailingAddress1;
	}

	public String getMailingAddress2() {
		return mailingAddress2;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public String getMailingState() {
		return mailingState;
	}

	public String getMailingZipCode() {
		return mailingZipCode;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public String getShippingZipCode() {
		return shippingZipCode;
	}
    public String getNotificationException() {
        return notificationException;
    }

	// Setters
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setVendorNumber(int vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setNotificationException(String notificationException) {
		this.notificationException = notificationException;
	}

	public void setPlanningAnalyst(EditableUser planningAnalyst) {
		this.planningAnalyst = planningAnalyst;
	}

	public void setSupplySpecialist(EditableUser supplySpecialist) {
		this.supplySpecialist = supplySpecialist;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPrimaryContact(VendorContact primaryContact) {
		this.primaryContact = primaryContact;
	}

	public void setSecondaryContact(VendorContact secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public void setMailingAddress1(String mailingAddress1) {
		this.mailingAddress1 = mailingAddress1;
	}

	public void setMailingAddress2(String mailingAddress2) {
		this.mailingAddress2 = mailingAddress2;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}
	
    public List<String> getMfrCodes() {
        return mfrCodes;
    }
    
    public String getFirstMfrCodes() {
        return mfrCodes == null || mfrCodes.isEmpty()? "" : mfrCodes.get(0);
    }
    
    //***** MODIFIED ACCESSORS *****//
    /**
     * Package-private setter - MyBatis only.
     * Business logic should not call this method.
     * This is because the result set from the query contains dependent components as a
     *  comma-separated string, but we need to split it here into separate mfr codes.
     */
    void setMfrCodes(String commaSeperatedMfrCodes) {
        
        ArrayList<String> mfrCodes = new ArrayList<String>();
        
        String[] parts = StringUtils.split(commaSeperatedMfrCodes, ",");
        
        for(String part : parts) {
            boolean noneBlank = StringUtils.isNoneBlank(part);
            if(noneBlank) mfrCodes.add(part);
        }
        
        this.mfrCodes = mfrCodes;
        
    }

    public void setMailingZipCode(String mailingZipCode) {
		this.mailingZipCode = mailingZipCode;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}

	@Override
    public String toString() {
        return "Vendor [vendorId=" + vendorId + ", vendorName=" + vendorName + ", vendorNumber=" + vendorNumber
               + ", corpCode=" + corpCode + ", notificationException=" + notificationException + ", annualAgreement="
               + annualAgreement + ", planningAnalyst=" + planningAnalyst + ", supplySpecialist=" + supplySpecialist
               + ", mfrCodes=" + mfrCodes + ", companyName=" + companyName + ", primaryContact=" + primaryContact
               + ", secondaryContact=" + secondaryContact + ", vendorAddress=" + vendorAddress + ", attention="
               + attention + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", paymentTerms="
               + paymentTerms + ", orderName=" + orderName + ", mailingAddress1=" + mailingAddress1
               + ", mailingAddress2=" + mailingAddress2 + ", mailingCity=" + mailingCity + ", mailingState="
               + mailingState + ", mailingZipCode=" + mailingZipCode + ", shippingAddress1=" + shippingAddress1
               + ", shippingAddress2=" + shippingAddress2 + ", shippingCity=" + shippingCity + ", shippingState="
               + shippingState + ", shippingZipCode=" + shippingZipCode + ", alertType=" + alertType + "]";
    }

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

    public String getSearchMfrCode() {
        return searchMfrCode;
    }

    public void setSearchMfrCode(String searchMfrCode) {
        this.searchMfrCode = searchMfrCode;
    }
}
