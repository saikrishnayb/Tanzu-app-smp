package com.penske.apps.adminconsole.model;

import java.util.Comparator;
import java.util.List;

public class Org {
	
	public static final Comparator<Org> ORG_NAME_ASC = new OrgNameComparator();
	
	private String orgName;
	private String orgDescription;
	private int orgId;
	private int parentOrgId;
	private String status;
	private List<String> vendor;
	private String vendorStr;
	private String uncheckedVendorStr;
	private String createdBy;
	private String  modifiedBy;
	private boolean deactivatible;
	private String parentOrgName;
	
	
	
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	public boolean isDeactivatible() {
		return deactivatible;
	}
	public void setDeactivatible(boolean deactivatible) {
		this.deactivatible = deactivatible;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDescription() {
		return orgDescription;
	}
	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public int getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(int parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getVendor() {
		return vendor;
	}
	public void setVendor(List<String> vendor) {
		this.vendor = vendor;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getVendorStr() {
		return vendorStr;
	}
	public void setVendorStr(String vendorStr) {
		this.vendorStr = vendorStr;
	}
	
	public String getUncheckedVendorStr() {
		return uncheckedVendorStr;
	}
	public void setUncheckedVendorStr(String uncheckedVendorStr) {
		this.uncheckedVendorStr = uncheckedVendorStr;
	}
	
	private static class OrgNameComparator implements Comparator<Org> {

	        @Override
	        public int compare(Org org, Org otherOrg) {

	            String orgName = org.getOrgName();
	            String otherOrgName = otherOrg.getOrgName();
	            
	            if (orgName == otherOrgName) return 0;// This takes care of nulls
	            return orgName == null ? 1 : orgName.compareToIgnoreCase(otherOrgName);

	        }
	}
	
	
}
