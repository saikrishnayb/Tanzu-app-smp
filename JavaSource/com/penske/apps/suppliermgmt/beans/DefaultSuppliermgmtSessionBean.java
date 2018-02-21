package com.penske.apps.suppliermgmt.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.penske.apps.suppliermgmt.domain.UserLoginHistory;
import com.penske.apps.suppliermgmt.model.UserContext;

public class DefaultSuppliermgmtSessionBean implements SuppliermgmtSessionBean, Serializable {

    private static final long serialVersionUID = -1472826879151363042L;

    private UserContext userContext;
    private Date lastUserLoginDate;

    private final SimpleDateFormat loginDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

    /** {@inheritDoc} */
    @Override
    public void setUserContext(UserContext userContext)
    {
        this.userContext = userContext;
    }

    /** {@inheritDoc} */
    @Override
    public UserContext getUserContext()
    {
        return userContext;
    }

    @Override
    public void setLastUserLoginDate(UserLoginHistory loginHistory) {

        if (loginHistory == null) return;

        this.lastUserLoginDate = loginHistory.getLastLoginDate();
    }

    @Override
    public String getFormattedUserLoginDate() {
        return lastUserLoginDate == null? "Never" : loginDateFormat.format(lastUserLoginDate);
    }
}
