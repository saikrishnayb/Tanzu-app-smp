package com.penske.apps.adminconsole.service;

import java.net.URL;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.UserForm;
import com.penske.apps.smccore.base.domain.LookupContainer;
import com.penske.apps.smccore.base.domain.User;

public interface UserCreationService {
	public EditableUser insertUserInfo(User user, UserForm userForm, LookupContainer lookups, URL commonStaticUrl);
	public EditableUser updateUserInfo(EditableUser userBean,boolean isDeactive);
	public boolean isEligibleToDeactivate(int userId,boolean isVendorUser,String currentUser);
}
