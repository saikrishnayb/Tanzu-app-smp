package com.penske.apps.suppliermgmt.service;

import java.util.Collection;
import java.util.List;

import com.penske.apps.suppliermgmt.exception.SMCException;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.OrgFilter;
import com.penske.apps.suppliermgmt.model.VendorFilter;

public interface SuppliermgmtUserService {

	public void  addBuddyList(List<Buddies> newBuddyList, String sso) throws SMCException;
	
	public String getSelectionType(String loggedInSso);

	public void deleteBuddyList(String userSSO) throws SMCException;
	
	public List<Buddies> getExistingBuddiesList(String userSSO);

	public List<LabelValue> getDeptDetailList();

	public String getTermsAndCondition();
	
	public List<OrgFilter> getAllOrgFilters();
	public List<VendorFilter> getAllVendorFilters(int organizationId);
	public void saveUserVendorFilterSelections(Collection<Integer> vendorIds);

	public void toggleVendorFilter();

	void addBuddyBasedOnselectionType(Buddies buddy, String user) throws SMCException;

}
