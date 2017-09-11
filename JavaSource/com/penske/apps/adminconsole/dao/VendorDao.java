package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;


public interface VendorDao {
	@NonVendorQuery //TODO: Review Query
	public List<Vendor> getAllVendors(@Param("orgId")int orgId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Vendor> getVendorsBySearchConditions(@Param("orgId")int orgId,@Param("vendor") Vendor vendor);
	
	@NonVendorQuery //TODO: Review Query
	public List<User> getAllPlanningAnalysts();
	
	@NonVendorQuery //TODO: Review Query
	public List<User> getAllSupplySpecialists();
	
	@NonVendorQuery //TODO: Review Query
	public Integer getVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public Vendor getViewVendorInformation(int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public Vendor getEditVendorInformation(int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyVendorInfo(Vendor vendor);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyVendorContactInfo(VendorContact contact);
	
	@NonVendorQuery //TODO: Review Query
	public void addVendorContact(VendorContact contact);
	
	@NonVendorQuery //TODO: Review Query
	public void removeVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Alert> getAllAlerts();
}
