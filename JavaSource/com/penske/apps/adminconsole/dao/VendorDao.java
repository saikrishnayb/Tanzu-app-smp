package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;


public interface VendorDao {
    public List<Vendor> getAllVendors(@Param("orgId")int orgId);

    public List<Vendor> getVendorsBySearchConditions(@Param("orgId")int orgId,@Param("vendor") Vendor vendor);

    @NonVendorQuery
    public List<User> getAllPlanningAnalysts();

    @NonVendorQuery
    public List<User> getAllSupplySpecialists();

    @NonVendorQuery // TODO: No harm to leave this annotation on here, but we should prob never look
                    // athis in the future
    public Integer getVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);

    public Vendor getViewVendorInformation(int vendorId);

    public Vendor getEditVendorInformation(int vendorId);

    @NonVendorQuery
    public void modifyVendorInfo(Vendor vendor);

    @NonVendorQuery
    public void modifyVendorContactInfo(VendorContact contact);

    @NonVendorQuery
    public void addVendorContact(VendorContact contact);

    @NonVendorQuery
    public void removeVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);

    @NonVendorQuery
    public List<Alert> getAllAlerts();
}
