package com.penske.apps.suppliermgmt.beans;

import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.model.UserContext;


public interface SuppliermgmtSessionBean {

    public void setUserContext(UserContext userContext);
    public UserContext getUserContext();

    public void setLastUserLoginDate(UserLoginHistory loginHistory);
    public String getFormattedUserLoginDate();
}
