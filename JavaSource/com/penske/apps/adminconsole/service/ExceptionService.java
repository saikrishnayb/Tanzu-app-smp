package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.UnitException;

/**
 * This Interface contains the method declarations for the
 * DefaultExceptionService class which contains the service-layer
 * logic for the Global Exceptions and Unit Exceptions pages.
 * @author 600132441 M.Leis
 *
 */
public interface ExceptionService {

	// Global Exception Management service methods
	public List<GlobalException> getException(int exceptionId);
	public List<GlobalException> getGlobalExceptions();
	public void modifyGlobalException(int exceptionId,int providervendorId,int poCategoryAssociationId,String createdBy);
	public List<GlobalException> getGlobalExceptionSearch(String unitNumber,Integer poNumber);
	public void deleteGlobalException(int exceptionId);
	// Unit Exception Management service methods
	public List<UnitException> getUnitExceptions();
	public UnitException getUnitException(int exceptionId);
	public void deleteUnitException(int exceptionId);
	public void modifyUnitException(int exceptionId, String provider, String subProvider);
	public void addGlobalException(UnitException exception);
	// misc Service methods
	public List<String> splitGroup(String textGroup);
	public List<String> getSubGroups(String primaryGroup);
}
