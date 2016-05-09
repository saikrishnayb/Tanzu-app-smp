package com.penske.apps.adminconsole.service;

import com.penske.apps.adminconsole.exceptions.UserServiceException;
import com.penske.apps.adminconsole.model.User;

public interface UserCreationService {
	public User insertUserInfo(User userBean) throws UserServiceException;
	public User updateUserInfo(User userBean,boolean isDeactive) throws UserServiceException;
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser) throws UserServiceException;
	public String getSupportNumber();
}
