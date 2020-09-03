package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface VendorService {
	public List<Vendor> getAllVendors(int orgId);
	
	public List<Vendor> getVendorsBySearchConditions(int orgId,Vendor vendor);
	
	public List<User> getAllPlanningAnalysts();
	
	public List<User> getAllSupplySpecialists();
	
	public Vendor getViewVendorInformation(int vendorId);
	
	public Vendor getEditVendorInformation(int vendorId);
	
	public Vendor modifyVendorSingleUpdate(Vendor vendor, UserContext userContext);
	
	public void modifyVendorsMassUpdate(Vendor vendor, UserContext user, int... vendorIdsToApplyChange);
	
	public List<Alert> getAllAlerts();

	void sendEmailToAnalyst(Vendor vendor, UserContext user);

	Vendor getVendorById(int vendorId);
}
