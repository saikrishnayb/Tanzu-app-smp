package com.penske.apps.suppliermgmt.domain;

import java.util.Date;

public class UserLoginHistory {

    private int loginCount;
    private Date firstLoginDate;
    private Date lastLoginDate;

    protected UserLoginHistory() {};


    public int getLoginCount() {
        return loginCount;
    }
    public Date getFirstLoginDate() {
        return firstLoginDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }
}
