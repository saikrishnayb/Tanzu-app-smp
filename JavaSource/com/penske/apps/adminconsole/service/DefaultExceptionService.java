package com.penske.apps.adminconsole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ExceptionDao;
import com.penske.apps.adminconsole.model.GlobalException;

/**
 * This class contains all of the method definitions for the ExceptionService
 * Interface.  It contains methods for both Global Exceptions and Unit Exceptions.
 * @author 600132441 M.Leis
 *
 */
@Service
public class DefaultExceptionService implements ExceptionService {

	@Autowired
	private ExceptionDao exceptionDao;

	@Override
	public void modifyGlobalException(int exceptionId, int providervendorId, int poCategoryAssociationId,String modifiedBy){
		exceptionDao.modifyGlobalException( exceptionId, providervendorId, poCategoryAssociationId,modifiedBy);
	}
	
	@Override
	@Transactional
	public void deleteGlobalException(int exceptionId){
		exceptionDao.deleteGlobalException(exceptionId);
	}

	@Override
	public List<GlobalException> getException(int exceptionId) {
		return  exceptionDao.getGlobalExceptions(exceptionId,null, null);
	}
	
	@Override
	public List<GlobalException> getGlobalExceptions(){
		return  exceptionDao.getGlobalExceptions(null,null,null);
	}
	
	@Override
	public List<GlobalException> getGlobalExceptionSearch(String unitNumber,Integer poNumber){
		return exceptionDao.getGlobalExceptions(null,unitNumber,poNumber);
	}
}
