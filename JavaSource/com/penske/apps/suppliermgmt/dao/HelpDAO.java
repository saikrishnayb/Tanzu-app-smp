package com.penske.apps.suppliermgmt.dao;
/**
 *****************************************************************************************************************
 * File Name     : HelpDAO
 * Description   : DAO class for fetching help 
 * Project       : SMC
 * Package       : com.penske.apps.smcop.dao
 * Author        : 600104283
 * Date			 : July 12, 2016
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */
import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;

public interface HelpDAO {

	@NonVendorQuery //TODO: Review Query
	public String getHelp(@Param("userType") String userType);
}
