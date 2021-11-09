package com.penske.apps.adminconsole.service;

import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.EditableUser;

public interface UserCreationService {
	public EditableUser insertUserInfo(EditableUser userBean) throws UserServiceException;
	public EditableUser updateUserInfo(EditableUser userBean,boolean isDeactive) throws UserServiceException;
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser,String currentUser) throws UserServiceException;
}
