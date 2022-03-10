package com.penske.apps.adminconsole.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.domain.OrgVendorAssociation;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;
import com.penske.apps.adminconsole.model.VendorPoInformation;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.smccore.base.annotation.SkipQueryTest;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

@DBSmc
public interface VendorDao {
	
	@SkipQueryTest("XMLSERIALIZE is not available in HSQLDB")
    public List<Vendor> getVendors(@Param("orgId") Integer orgId, @Param("vendorId") Integer vendorId, @Param("vendor") Vendor vendor);
    
    @NonVendorQuery
    public List<EditableUser> getAllPlanningAnalysts();

    @NonVendorQuery
    public List<EditableUser> getAllSupplySpecialists();

    @NonVendorQuery // TODO: No harm to leave this annotation on here, but we should prob never look
                    // athis in the future
    public Integer getVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);

    @NonVendorQuery
    public void modifyVendorInfo(@Param("vendor") Vendor vendor, @Param("updatedBy") String updatedBy);

    @NonVendorQuery
    public void modifyVendorContactInfo(VendorContact contact);

    @NonVendorQuery
    public void addVendorContact(VendorContact contact);

    @NonVendorQuery
    public void removeVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);

    @NonVendorQuery
    public List<Alert> getAllAlerts();

    @NonVendorQuery
	public List<VendorPoInformation> getVendorPoInformation(@Param("vendorIds") Collection<Integer> vendorIds);
    
    @NonVendorQuery
	public List<OrgVendorAssociation> getOrgVendorAssociationsByVendorIds(@Param("vendorIds") Collection<Integer> vendorIds);
}
