package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.ExceptionDao;
import com.penske.apps.adminconsole.model.GlobalException;
import com.penske.apps.adminconsole.model.UnitException;

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
	public void addGlobalException(UnitException exception){
		
		exceptionDao.addGlobalException(exception);
	}
	@Override
	public void modifyUnitException(int exceptionId, String provider, String subProvider){
		
		exceptionDao.modifyUnitException( exceptionId, provider, subProvider );
	}
	@Override
	public void deleteUnitException(int exceptionId){
		
		exceptionDao.deleteUnitException(exceptionId);
	}
	@Override
	public UnitException getUnitException(int exceptionId){
		
		UnitException exception = exceptionDao.getUnitException(exceptionId);
		exception = getComponentName(exception);
		return exception;
	}
	@Override
	public List<UnitException> getUnitExceptions(){
		
		List<UnitException> exceptions = exceptionDao.getUnitExceptions();
		Iterator<UnitException> iter = exceptions.iterator();
		
		List<UnitException> completeExceptions = new ArrayList<UnitException>();
		while(iter.hasNext()){
			
			UnitException thisExc = iter.next();
			thisExc = getComponentName(thisExc);
			thisExc = getCreator(thisExc);
			completeExceptions.add(thisExc);
		}
		return completeExceptions;
	}
	
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
	@Override
	public List<String> getSubGroups(String primaryGroup){
		
		List<String> subGroup = exceptionDao.getSubGroup(primaryGroup);
		return subGroup;
	}
	public List<String> splitGroup(String textGroup){
		
		String[] items;
		items = textGroup.split(",");
		List<String> splitItems = new ArrayList<String>();
		
		for(int i = 0; i < items.length; i++){
			
			if( items[i].contains(":") ){
				
				String[] subSplitList = items[i].split(":");
				splitItems.add(subSplitList[0]);
				// discarding the second item in subSplitList
			}
			else{
				
				splitItems.add(items[i]);
			}
		}
		
		return splitItems;
	}
	private UnitException getComponentName(UnitException exception){
		
		int id = exception.getDataId();
		String componentName = "";
		
		if( exception.getDataType().equals( "smc_component_info" ) ){
			
			componentName = exceptionDao.getComponent( id );
		}
		else if( exception.getDataType().equals( "smc_vehicle_info" ) ){
			
			componentName = exceptionDao.getVehicle( id );
		}
		exception.setComponentName(componentName);
		return exception;
	}

	private UnitException getCreator(UnitException exception){
		
		int id = exception.getCreatorId();
		String creatorFirst = exceptionDao.getCreatorFirstName( id );
		String creatorLast = exceptionDao.getCreatorLastName( id );
		String creator = creatorFirst + " " + creatorLast;
		exception.setCreatedBy(creator);
		return exception;
	}
	
}
