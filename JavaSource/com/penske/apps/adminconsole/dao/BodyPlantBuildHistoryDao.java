package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.BodyPlantBuildHistory;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
import com.penske.apps.suppliermgmt.model.UserContext;

@DBSmc
public interface BodyPlantBuildHistoryDao {

	@NonVendorQuery
	public List<BodyPlantBuildHistory> getAllBuildHistory();

	@NonVendorQuery
	public List<String> getAllbuildStatusTerms();
	
	@NonVendorQuery
	public int insertNewBuild(@Param("build") BodyPlantBuildHistory build,@Param("user") UserContext user);
}
