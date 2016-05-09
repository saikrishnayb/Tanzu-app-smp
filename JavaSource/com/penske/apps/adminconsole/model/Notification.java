package com.penske.apps.adminconsole.model;

import java.util.ArrayList;
import java.util.List;

public class Notification {
	private int notificationId;
	private String alertMetric;
	private int visibilityVendor;
	private int visibilityPlanning;
	private int visibilityPurchasing;
	private String complianceType;
	private int complianceCount;
	private int escalationOneDays;
	private int escalationTwoDays;
	private int escalationThreeDays;
	private List<NotificationParty> vendorNotificationParties;
	private boolean vendorEsc1Additional;
	private boolean vendorEsc2Additional;
	private boolean vendorEsc3Additional;
	private List<NotificationParty> vendorEsc1Parties;
	private List<NotificationParty> vendorEsc2Parties;
	private List<NotificationParty> vendorEsc3Parties;
	private boolean esc1VendorPrimary;
	private boolean esc1VendorSecondary;
	private boolean esc2VendorPrimary;
	private boolean esc2VendorSecondary;
	private boolean esc3VendorPrimary;
	private boolean esc3VendorSecondary;
	private List<NotificationParty> purchasingNotificationParties;
	private boolean purchasingEsc1Additional;
	private boolean purchasingEsc2Additional;
	private boolean purchasingEsc3Additional;
	private List<NotificationParty> purchasingEsc1Parties;
	private List<NotificationParty> purchasingEsc2Parties;
	private List<NotificationParty> purchasingEsc3Parties;
	private boolean esc1PenskeVss;
	private boolean esc2PenskeVss;
	private boolean esc3PenskeVss;
	private List<NotificationParty> planningNotificationParties;
	private boolean planningEsc1Additional;
	private boolean planningEsc2Additional;
	private boolean planningEsc3Additional;
	private List<NotificationParty> planningEsc1Parties;
	private List<NotificationParty> planningEsc2Parties;
	private List<NotificationParty> planningEsc3Parties;
	private boolean esc1PenskeAnalyst;
	private boolean esc2PenskeAnalyst;
	private boolean esc3PenskeAnalyst;
	private List<NotificationParty> escalationOneContacts;
	private List<NotificationParty> escalationTwoContacts;
	private List<NotificationParty> escalationThreeContacts;
	
	//Set and get for notification id
	public void setNotificationId(int newNotificationId) {
		this.notificationId = newNotificationId;
	}
	
	public int getNotificationId() {
		return this.notificationId;
	}
	
	//Set and get for alert metric
	public void setAlertMetric(String newAlertMetric) {
		this.alertMetric = newAlertMetric;
	}
	
	public String getAlertMetric() {
		return this.alertMetric;
	}
	
	//Set and get for visibility vendor
	public void setVisibilityVendor(int newVisibilityVendor) {
		this.visibilityVendor = newVisibilityVendor;
	}
	
	public int getVisibilityVendor() {
		return this.visibilityVendor;
	}
	
	//Set and get for visibility purchasing
	public void setVisibilityPurchasing(int newVisibilityPurchasing) {
		this.visibilityPurchasing = newVisibilityPurchasing;
	}
	
	public int getVisibilityPurchasing() {
		return this.visibilityPurchasing;
	}
	
	//Set and get for visibility planning
	public void setVisibilityPlanning(int newVisibilityPlanning) {
		this.visibilityPlanning = newVisibilityPlanning;
	}
	
	public int getVisibilityPlanning() {
		return this.visibilityPlanning;
	}
	
	//Set and get for compliance type
	public void setComplianceType(String newComplianceType) {
		this.complianceType = newComplianceType;
	}
	
	public String getComplianceType() {
		return this.complianceType;
	}
	
	//Set and get for compliance count
	public void setComplianceCount(int newComplianceCount) {
		this.complianceCount = newComplianceCount;
	}
	
	public int getComplianceCount() {
		return this.complianceCount;
	}
	
	//Set and get for number of days for escalation one
	public void setEscalationOneDays(int newEscOneDays) {
		this.escalationOneDays = newEscOneDays;
	}
	
	public int getEscalationOneDays() {
		return this.escalationOneDays;
	}
	
	//Set and get for number of days for escalation two
	public void setEscalationTwoDays(int newEscTwoDays) {
		this.escalationTwoDays = newEscTwoDays;
	}
	
	public int getEscalationTwoDays() {
		return this.escalationTwoDays;
	}
	
	//Set and get for number of days for escalation three
	public void setEscalationThreeDays(int newEscThreeDays) {
		this.escalationThreeDays = newEscThreeDays;
	}
	
	public int getEscalationThreeDays() {
		return this.escalationThreeDays;
	}
	
	//Set and get for list of contacts for escalation one
	public void setEscalationOneContacts(List<NotificationParty> escOneContacts) {
		this.escalationOneContacts = escOneContacts;
	}
	
	public List<NotificationParty> getEscalationOneContacts() {
		return this.escalationOneContacts;
	}
	
	//Set and get for list of contacts for escalation two
	public void setEscalationTwoContacts(List<NotificationParty> escTwoContacts) {
		this.escalationTwoContacts = escTwoContacts;
	}
	
	public List<NotificationParty> getEscalationTwoContacts() {
		return this.escalationTwoContacts;
	}
	
	//Set and get for list of contacts for escalation three
	public void setEscalationThreeContacts(List<NotificationParty> escThreeContacts) {
		this.escalationThreeContacts = escThreeContacts;
	}
	
	public List<NotificationParty> getEscalationThreeContacts() {
		return this.escalationThreeContacts;
	}
	
	//Set and get for ALL vendor contacts
	public void setVendorNotificationParties(List<NotificationParty> newVendorNotificationParties) {
		this.vendorNotificationParties = newVendorNotificationParties;
	}
	
	public List<NotificationParty> getVendorNotificationParties() {
		return this.vendorNotificationParties;
	}
	
	//Set and get for if vendor has additional level 1 contacts
	public void setVendorEsc1Additional(boolean newVendorEsc1Additional) {
		this.vendorEsc1Additional = newVendorEsc1Additional;
	}
	
	public boolean getVendorEsc1Additional() {
		return this.vendorEsc1Additional;
	}
	
	//Set and get for if vendor has additional level 2 contacts
	public void setVendorEsc2Additional(boolean newVendorEsc2Additional) {
		this.vendorEsc2Additional = newVendorEsc2Additional;
	}
		
	public boolean getVendorEsc2Additional() {
		return this.vendorEsc2Additional;
	}
	
	//Set and get for if vendor has additional level 3 contacts
	public void setVendorEsc3Additional(boolean newVendorEsc3Additional) {
		this.vendorEsc3Additional = newVendorEsc3Additional;
	}
			
	public boolean getVendorEsc3Additional() {
		return this.vendorEsc3Additional;
	}
	
	//Set and get for escalation 1 vendor contacts
	public void setVendorEsc1Parties(List<NotificationParty> newVendorEsc1Parties) {
		this.vendorEsc1Parties = newVendorEsc1Parties;
	}
	
	public List<NotificationParty> getVendorEsc1Parties() {
		return this.vendorEsc1Parties;
	}
	
	//Set and get for escalation 2 vendor contacts
	public void setVendorEsc2Parties(List<NotificationParty> newVendorEsc2Parties) {
		this.vendorEsc2Parties = newVendorEsc2Parties;
	}
	
	public List<NotificationParty> getVendorEsc2Parties() {
		return this.vendorEsc2Parties;
	}
	
	//Set and get for escalation 3 vendor contacts
	public void setVendorEsc3Parties(List<NotificationParty> newVendorEsc3Parties) {
		this.vendorEsc3Parties = newVendorEsc3Parties;
	}
	
	public List<NotificationParty> getVendorEsc3Parties() {
		return this.vendorEsc3Parties;
	}
	
	//Set and get for if escalation 1 has primary vendor
	public void setEsc1VendorPrimary(boolean newEsc1VendorPrimary) {
		this.esc1VendorPrimary = newEsc1VendorPrimary;
	}
	
	public boolean getEsc1VendorPrimary() {
		return this.esc1VendorPrimary;
	}
	
	//Set and get for if escalation 2 has primary vendor
	public void setEsc2VendorPrimary(boolean newEsc2VendorPrimary) {
		this.esc2VendorPrimary = newEsc2VendorPrimary;
	}
	
	public boolean getEsc2VendorPrimary() {
		return this.esc2VendorPrimary;
	}
	
	//Set and get for if escalation 3 has primary vendor
	public void setEsc3VendorPrimary(boolean newEsc3VendorPrimary) {
		this.esc3VendorPrimary = newEsc3VendorPrimary;
	}
	
	public boolean getEsc3VendorPrimary() {
		return this.esc3VendorPrimary;
	}
	
	//Set and get for if escalation 1 has secondary vendor
	public void setEsc1VendorSecondary(boolean newEsc1VendorSecondary) {
		this.esc1VendorSecondary = newEsc1VendorSecondary;
	}
	
	public boolean getEsc1VendorSecondary() {
		return this.esc1VendorSecondary;
	}
	
	//Set and get for if escalation 2 has secondary vendor
	public void setEsc2VendorSecondary(boolean newEsc2VendorSecondary) {
		this.esc2VendorSecondary = newEsc2VendorSecondary;
	}
	
	public boolean getEsc2VendorSecondary() {
		return this.esc2VendorSecondary;
	}
	
	//Set and get for if escalation 3 has secondary vendor
	public void setEsc3VendorSecondary(boolean newEsc3VendorSecondary) {
		this.esc3VendorSecondary = newEsc3VendorSecondary;
	}
	
	public boolean getEsc3VendorSecondary() {
		return this.esc3VendorSecondary;
	}
	
	//Set and get for purchasing contacts
	public void setPurchasingNotificationParties(List<NotificationParty> newPurchasingNotificationParties) {
		this.purchasingNotificationParties = newPurchasingNotificationParties;
	}
	
	public List<NotificationParty> getPurchasingNotificationParties() {
		return this.purchasingNotificationParties;
	}
	
	
	//Set and get for if purchasing level 1 has additional contacts
	public void setPurchasingEsc1Additional(boolean newPurchasingEsc1Additional) {
		this.purchasingEsc1Additional = newPurchasingEsc1Additional;
	}
	
	public boolean getPurchasingEsc1Additional() {
		return this.purchasingEsc1Additional;
	}
	
	
	
	//Set and get for if purchasing level 2 has additional contacts
	public void setPurchasingEsc2Additional(boolean newPurchasingEsc2Additional) {
		this.purchasingEsc2Additional = newPurchasingEsc2Additional;
	}
	
	public boolean getPurchasingEsc2Additional() {
		return this.purchasingEsc2Additional;
	}
	
	
	
	//Set and get for if purchasing level 3 has additional contacts
	public void setPurchasingEsc3Additional(boolean newPurchasingEsc3Additional) {
		this.purchasingEsc3Additional = newPurchasingEsc3Additional;
	}
	
	public boolean getPurchasingEsc3Additional() {
		return this.purchasingEsc3Additional;
	}
	
	
	
	//Set and get for escalation 1 purchasing contacts
	public void setPurchasingEsc1Parties(List<NotificationParty> newPurchasingEsc1Parties) {
		this.purchasingEsc1Parties = newPurchasingEsc1Parties;
	}
		
	public List<NotificationParty> getPurchasingEsc1Parties() {
		return this.purchasingEsc1Parties;
	}
	
	//Set and get for escalation 2 purchasing contacts
	public void setPurchasingEsc2Parties(List<NotificationParty> newPurchasingEsc2Parties) {
		this.purchasingEsc2Parties = newPurchasingEsc2Parties;
	}
	
	public List<NotificationParty> getPurchasingEsc2Parties() {
		return this.purchasingEsc2Parties;
	}
	
	//Set and get for escalation 3 purchasing contacts
	public void setPurchasingEsc3Parties(List<NotificationParty> newPurchasingEsc3Parties) {
		this.purchasingEsc3Parties = newPurchasingEsc3Parties;
	}
		
	public List<NotificationParty> getPurchasingEsc3Parties() {
		return this.purchasingEsc3Parties;
	}
	
	//Set and get for if escalation 1 has Penske VSS
	public void setEsc1PenskeVss(boolean newEsc1PenskeVss) {
		this.esc1PenskeVss = newEsc1PenskeVss;
	}
	
	public boolean getEsc1PenskeVss() {
		return this.esc1PenskeVss;
	}
	
	//Set and get for if escalation 2 has Penske VSS
	public void setEsc2PenskeVss(boolean newEsc2PenskeVss) {
		this.esc2PenskeVss = newEsc2PenskeVss;
	}
	
	public boolean getEsc2PenskeVss() {
		return this.esc2PenskeVss;
	}
	
	//Set and get for if escalation 3 has Penske VSS
	public void setEsc3PenskeVss(boolean newEsc3PenskeVss) {
		this.esc3PenskeVss = newEsc3PenskeVss;
	}
	
	public boolean getEsc3PenskeVss() {
		return this.esc3PenskeVss;
	}
	
	//Set and get for ALL planning contacts
	public void setPlanningNotificationParties(List<NotificationParty> newPlanningNotificationParties) {
		this.planningNotificationParties = newPlanningNotificationParties;
	}
	
	public List<NotificationParty> getPlanningNotificationParties() {
		return this.planningNotificationParties;
	}
	
	
	
	//Set and get for if planning level 1 has additioinal contacts
	public void setPlanningEsc1Additional(boolean newPlanningEsc1Additional) {
		this.planningEsc1Additional = newPlanningEsc1Additional;
	}
	
	public boolean getPlanningEsc1Additional() {
		return this.planningEsc1Additional;
	}
	
	
	//Set and get for if planning level 2 has additional contacts
	public void setPlanningEsc2Additional(boolean newPlanningEsc2Additional) {
		this.planningEsc2Additional = newPlanningEsc2Additional;
	}
	
	public boolean getPlanningEsc2Additional() {
		return this.planningEsc2Additional;
	}
	
	
	//Set and get for if planning level 3 has additional contacts
	public void setPlanningEsc3Additional(boolean newPlanningEsc3Additional) {
		this.planningEsc3Additional = newPlanningEsc3Additional;
	}
	
	public boolean getPlanningEsc3Additional() {
		return this.planningEsc3Additional;
	}
	
	
	//Set and get for escalation 1 planning contacts
	public void setPlanningEsc1Parties(List<NotificationParty> newPlanningEsc1Parties) {
		this.planningEsc1Parties = newPlanningEsc1Parties;
	}
		
	public List<NotificationParty> getPlanningEsc1Parties() {
		return this.planningEsc1Parties;
	}
	
	//Set and get for escalation 2 planning contacts
	public void setPlanningEsc2Parties(List<NotificationParty> newPlanningEsc2Parties) {
		this.planningEsc2Parties = newPlanningEsc2Parties;
	}
		
	public List<NotificationParty> getPlanningEsc2Parties() {
		return this.planningEsc2Parties;
	}
	
	//Set and get for escalation 3 planning contacts
	public void setPlanningEsc3Parties(List<NotificationParty> newPlanningEsc3Parties) {
		this.planningEsc3Parties = newPlanningEsc3Parties;
	}
		
	public List<NotificationParty> getPlanningEsc3Parties() {
		return this.planningEsc3Parties;
	}
	
	//Set and get for if escalation 1 has Penske Analyst
	public void setEsc1PenskeAnalyst(boolean newEsc1PenskeAnalyst) {
		this.esc1PenskeAnalyst = newEsc1PenskeAnalyst;
	}
	
	public boolean getEsc1PenskeAnalyst() {
		return this.esc1PenskeAnalyst;
	}
	
	//Set and get for if escalation 2 has Penske Analyst
	public void setEsc2PenskeAnalyst(boolean newEsc2PenskeAnalyst) {
		this.esc2PenskeAnalyst = newEsc2PenskeAnalyst;
	}
	
	public boolean getEsc2PenskeAnalyst() {
		return this.esc2PenskeAnalyst;
	}
	
	//Set and get for if escalation 3 has Penske Analyst
	public void setEsc3PenskeAnalyst(boolean newEsc3PenskeAnalyst) {
		this.esc3PenskeAnalyst = newEsc3PenskeAnalyst;
	}
	
	public boolean getEsc3PenskeAnalyst() {
		return this.esc3PenskeAnalyst;
	}
	
	//Function used to sort contacts into appropriate list
	//Also sets booleans for checkboxes
	public void sortContacts(List<NotificationParty> contacts) {
		List<NotificationParty> escalation1 = new ArrayList<NotificationParty>();
		List<NotificationParty> escalation2 = new ArrayList<NotificationParty>();
		List<NotificationParty> escalation3 = new ArrayList<NotificationParty>();
		String group = null;
		
		if (contacts.size() == 0) {
			return;
		}
		
		group = contacts.get(0).getEscalationGroup();
		
		for (NotificationParty np : contacts) {
			if (np.getEscalationLevel() == 1) {
				escalation1.add(np);
				
				if (group.equals("VENDOR")) {
					if (np.getContact().equals("VENDOR PRIMARY")) {
						setEsc1VendorPrimary(true);
					}
					else if (np.getContact().equals("VENDOR SECONDARY")) {
						setEsc1VendorSecondary(true);
					}
					else if (np.getIsEmail() == 1) {
						setVendorEsc1Additional(true);
					}
				}
				else if (group.equals("PURCHASING")) {
					if (np.getContact().equals("PENSKE VSS")) {
						setEsc1PenskeVss(true);
					}
					else if (np.getIsEmail() == 1) {
						setPurchasingEsc1Additional(true);
					}
				}
				else if (group.equals("PLANNING")) {
					if (np.getContact().equals("PENSKE ANALYST")) {
						setEsc1PenskeAnalyst(true);
					}
					else if (np.getIsEmail() == 1) {
						setPlanningEsc1Additional(true);
					}
				}
			}
			else if (np.getEscalationLevel() == 2) {
				escalation2.add(np);
				
				if (group.equals("VENDOR")) {
					if (np.getContact().equals("VENDOR PRIMARY")) {
						setEsc2VendorPrimary(true);
					}
					else if (np.getContact().equals("VENDOR SECONDARY")) {
						setEsc2VendorSecondary(true);
					}
					else if (np.getIsEmail() == 1) {
						setVendorEsc2Additional(true);
					}
				}
				else if (group.equals("PURCHASING")) {
					if (np.getContact().equals("PENSKE VSS")) {
						setEsc2PenskeVss(true);
					}
					else if (np.getIsEmail() == 1) {
						setPurchasingEsc2Additional(true);
					}
				}
				else if (group.equals("PLANNING")) {
					if (np.getContact().equals("PENSKE ANALYST")) {
						setEsc2PenskeAnalyst(true);
					}
					else if (np.getIsEmail() == 1) {
						setPlanningEsc2Additional(true);
					}
				}
			}
			else if (np.getEscalationLevel() == 3) {
				escalation3.add(np);
				
				if (group.equals("VENDOR")) {
					if (np.getContact().equals("VENDOR PRIMARY")) {
						setEsc3VendorPrimary(true);
					}
					else if (np.getContact().equals("VENDOR SECONDARY")) {
						setEsc3VendorSecondary(true);
					}
					else if (np.getIsEmail() == 1) {
						setVendorEsc3Additional(true);
					}
				}
				else if (group.equals("PURCHASING")) {
					if (np.getContact().equals("PENSKE VSS")) {
						setEsc3PenskeVss(true);
					}
					else if (np.getIsEmail() == 1) {
						setPurchasingEsc3Additional(true);
					}
				}
				else if (group.equals("PLANNING")) {
					if (np.getContact().equals("PENSKE ANALYST")) {
						setEsc3PenskeAnalyst(true);
					}
					else if (np.getIsEmail() == 1) {
						setPlanningEsc3Additional(true);
					}
				}
			}
		}
		
		if (group.equals("VENDOR")) {
			this.setVendorEsc1Parties(escalation1);
			this.setVendorEsc2Parties(escalation2);
			this.setVendorEsc3Parties(escalation3);
		}
		else if (group.equals("PURCHASING")) {
			this.setPurchasingEsc1Parties(escalation1);
			this.setPurchasingEsc2Parties(escalation2);
			this.setPurchasingEsc3Parties(escalation3);
		}
		else if (group.equals("PLANNING")) {
			this.setPlanningEsc1Parties(escalation1);
			this.setPlanningEsc2Parties(escalation2);
			this.setPlanningEsc3Parties(escalation3);
		}
	}

	@Override
	public String toString() {
		return "[" + notificationId + ", " + alertMetric + ", " + escalationOneDays + ", " +
				escalationTwoDays + ", " + escalationThreeDays + "]";
	}

}
