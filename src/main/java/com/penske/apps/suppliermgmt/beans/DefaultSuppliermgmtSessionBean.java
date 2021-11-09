package com.penske.apps.suppliermgmt.beans;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.util.DateUtil;
import com.penske.apps.suppliermgmt.model.AppConfigSessionData;

public class DefaultSuppliermgmtSessionBean implements SuppliermgmtSessionBean, Serializable {

    private static final long serialVersionUID = -1472826879151363042L;

    private User user;
    private String baseUrl;
    private Date lastUserLoginDate;
    private boolean buddyListApplied;
    private boolean vendorFilterApplied;
    private boolean vendorFilterActive;
    
    private AppConfigSessionData appConfigSessionData = new AppConfigSessionData();

    /** {@inheritDoc} */
    @Override
    public void initialize(User user, String baseUrl, Date lastUserLoginDate, boolean buddyListApplied, boolean vendorFilterApplied, boolean vendorFilterActive)
    {
       	if(user == null)
    		throw new IllegalArgumentException("Can not initialize user session without a logged-in user.");
    	if(StringUtils.isBlank(baseUrl))
    		throw new IllegalArgumentException("Can not initialize user session without a base URL.");
    	
    	this.user = user;
    	this.baseUrl = baseUrl;
    	this.lastUserLoginDate = lastUserLoginDate;
    	this.appConfigSessionData = new AppConfigSessionData();
    	this.buddyListApplied = buddyListApplied;
    	this.vendorFilterApplied = vendorFilterApplied;
    	this.vendorFilterActive = vendorFilterActive;
    }
    
    /** {@inheritDoc} */
    @Override
    public User getUser()
    {
        return user;
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
    @Override
    public boolean isVendorFilterActive()
    {
    	return vendorFilterActive;
    }
    
    /** {@inheritDoc} */
    @Override
    public AppConfigSessionData getAppConfigSessionData()
    {
    	return appConfigSessionData;
    }
}
