package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	public String getComponent(int dataId);
	
	public String getVehicle(int dataId);
	
	public List<GlobalException> getGlobalExceptions(@Param("exceptionId") Integer exceptionId);
	
	public void deleteGlobalException(int id);
	
	public String getCreatorFirstName(int id);
	
	public String getCreatorLastName(int id);
	
	public List<String> getSubGroup(String primaryGroup);
	
	public void modifyGlobalException(@Param("id")int id, @Param("provider")String providerPo, @Param("subProvider")String subProviderPo);
	
	// Unit Exception Dao Methods
	public List<UnitException> getUnitExceptions();
	
	public UnitException getUnitException(int id);
	
	public void deleteUnitException(@Param("id")int id);
	
	public void  deleteGlobalExceptionPOCatGrp(@Param("id")int id);
	
	public void modifyUnitException(@Param("id")int id, @Param("provider")String providerPo, @Param("subProvider")String subProviderPo);
	
	public void addGlobalException(UnitException exception);
}
