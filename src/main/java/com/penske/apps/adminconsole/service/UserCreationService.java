package com.penske.apps.adminconsole.service;

import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.smccore.base.domain.User;

public interface UserCreationService {
	public EditableUser insertUserInfo(User user, EditableUser userBean) throws UserServiceException;
	public EditableUser updateUserInfo(EditableUser userBean,boolean isDeactive) throws UserServiceException;
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser,String currentUser) throws UserServiceException;
	public void resendVendorEmail(User user, EditableUser editableUser);
}
