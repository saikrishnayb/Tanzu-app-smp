package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.HeaderUser;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;

public interface VendorService {
	public List<Vendor> getAllVendors(int orgId);
	
	public List<Vendor> getVendorsBySearchConditions(int orgId,Vendor vendor);
	
	public List<User> getAllPlanningAnalysts();
	
	public List<User> getAllSupplySpecialists();
	
	public Vendor getViewVendorInformation(int vendorId);
	
	public Vendor getEditVendorInformation(int vendorId);
	
	public void modifyVendorInformation(Vendor vendor,HeaderUser user);
	
	public void modifyVendorsMassUpdate(int[] vendorIds, Vendor vendor);
	
	public List<Alert> getAllAlerts();
}
