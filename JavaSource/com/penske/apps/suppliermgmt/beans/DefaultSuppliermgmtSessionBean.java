package com.penske.apps.suppliermgmt.beans;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;
import com.penske.apps.suppliermgmt.model.AppConfigSessionData;
import com.penske.apps.suppliermgmt.model.UserContext;

public class DefaultSuppliermgmtSessionBean implements SuppliermgmtSessionBean, Serializable {

    private static final long serialVersionUID = -1472826879151363042L;

    private UserContext userContext;
    private String baseUrl;
    private Date lastUserLoginDate;
    private boolean buddyListApplied;
    private boolean vendorFilterApplied;
    
    private AppConfigSessionData appConfigSessionData = new AppConfigSessionData();

    /** {@inheritDoc} */
    @Override
    public void initialize(UserContext userContext, String baseUrl, Date lastUserLoginDate, boolean buddyListApplied, boolean vendorFilterApplied)
    {
       	if(userContext == null)
    		throw new IllegalArgumentException("Can not initialize user session without a logged-in user.");
    	if(StringUtils.isBlank(baseUrl))
    		throw new IllegalArgumentException("Can not initialize user session without a base URL.");
    	
    	this.userContext = userContext;
    	this.baseUrl = baseUrl;
    	this.lastUserLoginDate = lastUserLoginDate;
    	this.appConfigSessionData = new AppConfigSessionData();
    	this.buddyListApplied = buddyListApplied;
    	this.vendorFilterApplied = vendorFilterApplied;
    }
    
    /** {@inheritDoc} */
    @Override
    public UserContext getUserContext()
    {
        return userContext;
    }

    /** {@inheritDoc} */
    @Override
    public String getFormattedUserLoginDate() {
        return lastUserLoginDate == null? null : "Last Logged In On " + DateUtil.formatDateTimeUS(lastUserLoginDate);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getBaseUrl()
    {
    	return baseUrl;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isBuddyListApplied()
    {
    	return buddyListApplied;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isVendorFilterApplied()
    {
    	return vendorFilterApplied;
    }
    
    /** {@inheritDoc} */
    @Override
    public AppConfigSessionData getAppConfigSessionData()
    {
    	return appConfigSessionData;
    }
}
