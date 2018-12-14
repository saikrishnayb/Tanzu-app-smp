package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.GlobalException;

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
}
