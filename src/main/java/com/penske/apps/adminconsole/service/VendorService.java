package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.smccore.base.domain.User;

public interface VendorService {
	public List<Vendor> getAllVendors(int orgId);
	
	public List<Vendor> getVendorsBySearchConditions(int orgId,Vendor vendor);
	
	public List<EditableUser> getAllPlanningAnalysts();
	
	public List<EditableUser> getAllSupplySpecialists();
	
	public Vendor getViewVendorInformation(int vendorId);
	
	public Vendor getEditVendorInformation(int vendorId);
	
	public Vendor modifyVendorSingleUpdate(Vendor vendor, User user);
	
	public void modifyVendorsMassUpdate(Vendor vendor, User user, int... vendorIdsToApplyChange);
	
	public List<Alert> getAllAlerts();

	void sendEmailToAnalyst(Vendor vendor, User user);

	Vendor getVendorById(int vendorId);
}
