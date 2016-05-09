package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;


public interface VendorDao {
	public List<Vendor> getAllVendors(@Param("orgId")int orgId);
	
	public List<Vendor> getVendorsBySearchConditions(@Param("orgId")int orgId,@Param("vendor") Vendor vendor);
	
	public List<User> getAllPlanningAnalysts();
	
	public List<User> getAllSupplySpecialists();
	
	public Integer getVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);
	
	public Vendor getViewVendorInformation(int vendorId);
	
	public Vendor getEditVendorInformation(int vendorId);
	
	public void modifyVendorInfo(Vendor vendor);
	
	public void modifyVendorContactInfo(VendorContact contact);
	
	public void addVendorContact(VendorContact contact);
	
	public void removeVendorContact(@Param("contactType") String contactType, @Param("vendorId") int vendorId);
}
