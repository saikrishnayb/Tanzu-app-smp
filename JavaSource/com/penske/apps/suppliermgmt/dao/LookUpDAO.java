package com.penske.apps.suppliermgmt.dao;
/**
 *****************************************************************************************************************
 * File Name     : LookUpDAO
 * Description   : DAO class for fetching lookup data
 * Project       : SMC
 * Package       : com.penske.apps.smcop.dao
 * Author        : 502403391
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.model.LookUp;

public interface LookUpDAO {

	@NonVendorQuery
	public List<LookUp> getAllLookupList();
	@NonVendorQuery
	public LookUp getLookupDetails();

}
