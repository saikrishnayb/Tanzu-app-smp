package com.penske.apps.adminconsole.model;

import java.util.List;

/**
 * This model object is used to submit a notification update from the form on
 * the modal on the Notifications page.
 * 
 * Created On: October 30th, 2014
 * 
 * @author 600144069
 * 
 */
public class NotificationForm {
	private int notificationId;
	private String complianceType;
	private int complianceCount;
	private int escalationOneDays;
	private int escalationTwoDays;
	private int escalationThreeDays;
	private boolean vendorPrimaryEsc1;
	private boolean vendorSecondaryEsc1;
	private boolean vendorPrimaryEsc2;
	private boolean vendorSecondaryEsc2;
	private boolean vendorPrimaryEsc3;
	private boolean vendorSecondaryEsc3;
	private boolean penskeVssEsc1;
	private boolean penskeVssEsc2;
	private boolean penskeVssEsc3;
	private boolean penskeAnalystEsc1;
	private boolean penskeAnalystEsc2;
	private boolean penskeAnalystEsc3;
	private boolean vendorEsc1AdditionalChecked;
	private List<String> vendorEsc1Additional;
	private boolean vendorEsc2AdditionalChecked;
	private List<String> vendorEsc2Additional;
	private boolean vendorEsc3AdditionalChecked;
	private List<String> vendorEsc3Additional;
	private boolean purchasingEsc1AdditionalChecked;
	private List<String> purchasingEsc1Additional;
	private boolean purchasingEsc2AdditionalChecked;
	private List<String> purchasingEsc2Additional;
	private boolean purchasingEsc3AdditionalChecked;
	private List<String> purchasingEsc3Additional;
	private boolean planningEsc1AdditionalChecked;
	private List<String> planningEsc1Additional;
	private boolean planningEsc2AdditionalChecked;
	private List<String> planningEsc2Additional;
	private boolean planningEsc3AdditionalChecked;
	private List<String> planningEsc3Additional;
	private int visibilityVendor;
	private int visibilityPurchasing;
	private int visibilityPlanning;

	// Getters
	public int getNotificationId() {
		return notificationId;
	}

	public String getComplianceType() {
		return complianceType;
	}

	public int getComplianceCount() {
		return complianceCount;
	}

	public int getEscalationOneDays() {
		return escalationOneDays;
	}

	public int getEscalationTwoDays() {
		return escalationTwoDays;
	}

	public int getEscalationThreeDays() {
		return escalationThreeDays;
	}

	public boolean getVendorPrimaryEsc1() {
		return vendorPrimaryEsc1;
	}

	public boolean getVendorSecondaryEsc1() {
		return vendorSecondaryEsc1;
	}

	public boolean getVendorPrimaryEsc2() {
		return vendorPrimaryEsc2;
	}

	public boolean getVendorSecondaryEsc2() {
		return vendorSecondaryEsc2;
	}

	public boolean getVendorPrimaryEsc3() {
		return vendorPrimaryEsc3;
	}

	public boolean getVendorSecondaryEsc3() {
		return vendorSecondaryEsc3;
	}

	public boolean getPenskeVssEsc1() {
		return penskeVssEsc1;
	}

	public boolean getPenskeVssEsc2() {
		return penskeVssEsc2;
	}

	public boolean getPenskeVssEsc3() {
		return penskeVssEsc3;
	}

	public boolean getPenskeAnalystEsc1() {
		return penskeAnalystEsc1;
	}

	public boolean getPenskeAnalystEsc2() {
		return penskeAnalystEsc2;
	}

	public boolean getPenskeAnalystEsc3() {
		return penskeAnalystEsc3;
	}

	public boolean getVendorEsc1AdditionalChecked() {
		return vendorEsc1AdditionalChecked;
	}

	public List<String> getVendorEsc1Additional() {
		return vendorEsc1Additional;
	}

	public boolean getVendorEsc2AdditionalChecked() {
		return vendorEsc2AdditionalChecked;
	}

	public List<String> getVendorEsc2Additional() {
		return vendorEsc2Additional;
	}

	public boolean getVendorEsc3AdditionalChecked() {
		return vendorEsc3AdditionalChecked;
	}

	public List<String> getVendorEsc3Additional() {
		return vendorEsc3Additional;
	}

	public boolean getPurchasingEsc1AdditionalChecked() {
		return purchasingEsc1AdditionalChecked;
	}

	public List<String> getPurchasingEsc1Additional() {
		return purchasingEsc1Additional;
	}

	public boolean getPurchasingEsc2AdditionalChecked() {
		return purchasingEsc2AdditionalChecked;
	}

	public List<String> getPurchasingEsc2Additional() {
		return purchasingEsc2Additional;
	}

	public boolean getPurchasingEsc3AdditionalChecked() {
		return purchasingEsc3AdditionalChecked;
	}

	public List<String> getPurchasingEsc3Additional() {
		return purchasingEsc3Additional;
	}

	public boolean getPlanningEsc1AdditionalChecked() {
		return planningEsc1AdditionalChecked;
	}

	public List<String> getPlanningEsc1Additional() {
		return planningEsc1Additional;
	}

	public boolean getPlanningEsc2AdditionalChecked() {
		return planningEsc2AdditionalChecked;
	}

	public List<String> getPlanningEsc2Additional() {
		return planningEsc2Additional;
	}

	public boolean getPlanningEsc3AdditionalChecked() {
		return planningEsc3AdditionalChecked;
	}

	public List<String> getPlanningEsc3Additional() {
		return planningEsc3Additional;
	}

	public int getVisibilityVendor() {
		return visibilityVendor;
	}

	public int getVisibilityPurchasing() {
		return visibilityPurchasing;
	}

	public int getVisibilityPlanning() {
		return visibilityPlanning;
	}

	// Setters
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public void setComplianceType(String complianceType) {
		this.complianceType = complianceType;
	}

	public void setComplianceCount(int complianceCount) {
		this.complianceCount = complianceCount;
	}

	public void setEscalationOneDays(int escalationOneDays) {
		this.escalationOneDays = escalationOneDays;
	}

	public void setEscalationTwoDays(int escalationTwoDays) {
		this.escalationTwoDays = escalationTwoDays;
	}

	public void setEscalationThreeDays(int escalationThreeDays) {
		this.escalationThreeDays = escalationThreeDays;
	}

	public void setVendorPrimaryEsc1(boolean vendorPrimaryEsc1) {
		this.vendorPrimaryEsc1 = vendorPrimaryEsc1;
	}

	public void setVendorSecondaryEsc1(boolean vendorSecondaryEsc1) {
		this.vendorSecondaryEsc1 = vendorSecondaryEsc1;
	}

	public void setVendorPrimaryEsc2(boolean vendorPrimaryEsc2) {
		this.vendorPrimaryEsc2 = vendorPrimaryEsc2;
	}

	public void setVendorSecondaryEsc2(boolean vendorSecondaryEsc2) {
		this.vendorSecondaryEsc2 = vendorSecondaryEsc2;
	}

	public void setVendorPrimaryEsc3(boolean vendorPrimaryEsc3) {
		this.vendorPrimaryEsc3 = vendorPrimaryEsc3;
	}

	public void setVendorSecondaryEsc3(boolean vendorSecondaryEsc3) {
		this.vendorSecondaryEsc3 = vendorSecondaryEsc3;
	}

	public void setPenskeVssEsc1(boolean penskeVssEsc1) {
		this.penskeVssEsc1 = penskeVssEsc1;
	}

	public void setPenskeVssEsc2(boolean penskeVssEsc2) {
		this.penskeVssEsc2 = penskeVssEsc2;
	}

	public void setPenskeVssEsc3(boolean penskeVssEsc3) {
		this.penskeVssEsc3 = penskeVssEsc3;
	}

	public void setPenskeAnalystEsc1(boolean penskeAnalystEsc1) {
		this.penskeAnalystEsc1 = penskeAnalystEsc1;
	}

	public void setPenskeAnalystEsc2(boolean penskeAnalystEsc2) {
		this.penskeAnalystEsc2 = penskeAnalystEsc2;
	}

	public void setPenskeAnalystEsc3(boolean penskeAnalystEsc3) {
		this.penskeAnalystEsc3 = penskeAnalystEsc3;
	}

	public void setVendorEsc1AdditionalChecked(
			boolean vendorEsc1AdditionalChecked) {
		this.vendorEsc1AdditionalChecked = vendorEsc1AdditionalChecked;
	}

	public void setVendorEsc1Additional(List<String> vendorEsc1Additional) {
		this.vendorEsc1Additional = vendorEsc1Additional;
	}

	public void setVendorEsc2AdditionalChecked(
			boolean vendorEsc2AdditionalChecked) {
		this.vendorEsc2AdditionalChecked = vendorEsc2AdditionalChecked;
	}

	public void setVendorEsc2Additional(List<String> vendorEsc2Additional) {
		this.vendorEsc2Additional = vendorEsc2Additional;
	}

	public void setVendorEsc3AdditionalChecked(
			boolean vendorEsc3AdditionalChecked) {
		this.vendorEsc3AdditionalChecked = vendorEsc3AdditionalChecked;
	}

	public void setVendorEsc3Additional(List<String> vendorEsc3Additional) {
		this.vendorEsc3Additional = vendorEsc3Additional;
	}

	public void setPurchasingEsc1AdditionalChecked(
			boolean purchasingEsc1AdditionalChecked) {
		this.purchasingEsc1AdditionalChecked = purchasingEsc1AdditionalChecked;
	}

	public void setPurchasingEsc1Additional(
			List<String> purchasingEsc1Additional) {
		this.purchasingEsc1Additional = purchasingEsc1Additional;
	}

	public void setPurchasingEsc2AdditionalChecked(
			boolean purchasingEsc2AdditionalChecked) {
		this.purchasingEsc2AdditionalChecked = purchasingEsc2AdditionalChecked;
	}

	public void setPurchasingEsc2Additional(
			List<String> purchasingEsc2Additional) {
		this.purchasingEsc2Additional = purchasingEsc2Additional;
	}

	public void setPurchasingEsc3AdditionalChecked(
			boolean purchasingEsc3AdditionalChecked) {
		this.purchasingEsc3AdditionalChecked = purchasingEsc3AdditionalChecked;
	}

	public void setPurchasingEsc3Additional(
			List<String> purchasingEsc3Additional) {
		this.purchasingEsc3Additional = purchasingEsc3Additional;
	}

	public void setPlanningEsc1AdditionalChecked(
			boolean planningEsc1AdditionalChecked) {
		this.planningEsc1AdditionalChecked = planningEsc1AdditionalChecked;
	}

	public void setPlanningEsc1Additional(List<String> planningEsc1Additional) {
		this.planningEsc1Additional = planningEsc1Additional;
	}

	public void setPlanningEsc2AdditionalChecked(
			boolean planningEsc2AdditionalChecked) {
		this.planningEsc2AdditionalChecked = planningEsc2AdditionalChecked;
	}

	public void setPlanningEsc2Additional(List<String> planningEsc2Additional) {
		this.planningEsc2Additional = planningEsc2Additional;
	}

	public void setPlanningEsc3AdditionalChecked(
			boolean planningEsc3AdditionalChecked) {
		this.planningEsc3AdditionalChecked = planningEsc3AdditionalChecked;
	}

	public void setPlanningEsc3Additional(List<String> planningEsc3Additional) {
		this.planningEsc3Additional = planningEsc3Additional;
	}

	public void setVisibilityVendor(int visibilityVendor) {
		this.visibilityVendor = visibilityVendor;
	}

	public void setVisibilityPurchasing(int visibilityPurchasing) {
		this.visibilityPurchasing = visibilityPurchasing;
	}

	public void setVisibilityPlanning(int visibilityPlanning) {
		this.visibilityPlanning = visibilityPlanning;
	}
}
