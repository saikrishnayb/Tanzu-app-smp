package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.UnitException;
// models
/**
 * This interface contains the myBatis DAO methods that relay
 * directly to the database to retrieve data and return it to the
 * server.  It pulls data for the Global Exceptions and Unit Exceptions
 * pages.
 * @author 600132441 M.Leis
 *
 */

public interface ExceptionDao {

	// Global Exception Dao Methods
	@NonVendorQuery //TODO: Review Query
	public String getComponent(int dataId);
	
	@NonVendorQuery //TODO: Review Query
	public String getVehicle(int dataId);
	
	@NonVendorQuery //TODO: Review Query
	public List<GlobalException> getGlobalExceptions(@Param("exceptionId") Integer exceptionId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteGlobalException(int id);
	
	@NonVendorQuery //TODO: Review Query
	public String getCreatorFirstName(int id);
	
	@NonVendorQuery //TODO: Review Query
	public String getCreatorLastName(int id);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getSubGroup(String primaryGroup);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyGlobalException(@Param("id")int id, @Param("provider")String providerPo, @Param("subProvider")String subProviderPo);
	
	// Unit Exception Dao Methods
	@NonVendorQuery //TODO: Review Query
	public List<UnitException> getUnitExceptions();
	
	@NonVendorQuery //TODO: Review Query
	public UnitException getUnitException(int id);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteUnitException(@Param("id")int id);
	
	@NonVendorQuery //TODO: Review Query
	public void  deleteGlobalExceptionPOCatGrp(@Param("id")int id);
	
	@NonVendorQuery //TODO: Review Query
	public void modifyUnitException(@Param("id")int id, @Param("provider")String providerPo, @Param("subProvider")String subProviderPo);
	
	@NonVendorQuery //TODO: Review Query
	public void addGlobalException(UnitException exception);
}
