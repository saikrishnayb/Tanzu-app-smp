package com.penske.apps.adminconsole.domain;

/**
 * Represents the association between a vendor and an org
 */
public class OrgVendorAssociation {
	/**
	 * The DB ID of the association
	 */
	private Integer associationId;
	/**
	 * The id of the org in this association
	 */
	private int orgId;
	/**
	 * The id of the vendor in this association
	 */
	private int vendorId;
	
	protected OrgVendorAssociation() {}

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
