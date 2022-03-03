package com.penske.apps.adminconsole.domain;

/**
 * Represents the association between a vendor and an org
 */
public class OrgVendorAssociation {
	private Integer associationId;
	private int orgId;
	private int vendorId;
	
	protected OrgVendorAssociation() {}
	
	public OrgVendorAssociation(int orgId, int vendorId) {
		this.orgId = orgId;
		this.vendorId = vendorId;
	}

	public Integer getAssociationId() {
		return associationId;
	}

	public int getOrgId() {
		return orgId;
	}

	public int getVendorId() {
		return vendorId;
	}
	
}
