package com.penske.apps.suppliermgmt.service;

import java.util.List;





import com.penske.apps.suppliermgmt.common.exception.SMCException;
import com.penske.apps.suppliermgmt.model.Buddies;
import com.penske.apps.suppliermgmt.model.LabelValue;
import com.penske.apps.suppliermgmt.model.User;

public interface UserService {

	public List<User> getUserDetails() throws SMCException;

	public void  addBuddyList(List<Buddies> newBuddyList) throws SMCException;
	
	public void addBuddyBasedOnselectionType(Buddies buddy);
	
	public String getSelectionType(String loggedInSso);

	public void deleteBuddyList(String userSSO) throws SMCException;
	
	public List<Buddies> getExistingBuddiesList(String userSSO);

	public List<LabelValue> getDeptDetailList();

	public String getTermsAndCondition(); 

}
