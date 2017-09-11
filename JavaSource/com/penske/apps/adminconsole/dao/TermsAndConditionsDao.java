package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.TermsAndConditions;


/**
 * Uses SQL statements defined in terms-and-conditions-mapper.xml to obtain relevant information related to terms and conditions
 * 
 * @author mark.weaver 600144069
 *
 */

public interface TermsAndConditionsDao {
	@NonVendorQuery //TODO: Review Query
	public List<TermsAndConditions> selectAllTermsAndConditions();
	
	@NonVendorQuery //TODO: Review Query
	public String selectTermsAndConditionsText(@Param("versionNumber") int versionNumber);
}
