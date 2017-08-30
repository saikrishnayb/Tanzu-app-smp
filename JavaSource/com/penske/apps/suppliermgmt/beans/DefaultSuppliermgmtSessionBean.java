package com.penske.apps.suppliermgmt.beans;

import java.io.Serializable;

import com.penske.apps.suppliermgmt.model.UserContext;

public class DefaultSuppliermgmtSessionBean implements SuppliermgmtSessionBean, Serializable {

	private static final long serialVersionUID = -1472826879151363042L;
	
	private UserContext userContext;

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
}
