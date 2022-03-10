package com.penske.apps.adminconsole.service;

import java.util.Collection;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorPoInformation;
import com.penske.apps.smccore.base.domain.User;

public interface VendorService {
	public List<Vendor> getAllVendors(int orgId);
	
	public List<Vendor> getVendorsBySearchConditions(int orgId,Vendor vendor);
	
	public List<EditableUser> getAllPlanningAnalysts();
	
	public List<EditableUser> getAllSupplySpecialists();
	
	public Vendor modifyVendorSingleUpdate(Vendor vendor, User user);
	
	public void modifyVendorsMassUpdate(Vendor vendor, User user, int... vendorIdsToApplyChange);
	
	public List<Alert> getAllAlerts();

	void sendEmailToAnalyst(Vendor vendor, User user);

	Vendor getVendorById(int vendorId);

	public SXSSFWorkbook exportVendorActivity(User user, List<EditableUser> vendorUsers);
	
	public List<VendorPoInformation> getVendorPoInformation(Collection<Integer> vendorIds);
}
