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
	public void modifyGlobalException(int exceptionId, String provider, String subProvider){
		
		exceptionDao.modifyGlobalException( exceptionId, provider, subProvider );
	}
	
	@Override
	@Transactional
	public void deleteGlobalException(int exceptionId){
		
		exceptionDao.deleteGlobalExceptionPOCatGrp(exceptionId);
		exceptionDao.deleteGlobalException(exceptionId);
		
	}

	@Override
	public GlobalException getException(int exceptionId) {

		// get the Exception through SQL, then check table name in PRIVATE checkTableName()
		// to get the component name needed to populate exception.componentName
	    
	    List<GlobalException> exceptions = exceptionDao.getGlobalExceptions(exceptionId);
	    
		//exception = getComponentName(exception);
		return exceptions.size() > 0? exceptions.get(0): null;
	}
	
	@Override
	public List<GlobalException> getGlobalExceptions(){
		
		List<GlobalException> exceptions = exceptionDao.getGlobalExceptions(null);
	/*	
	 	Iterator<GlobalException> iter = exceptions.iterator();
		
		List<GlobalException> completeExceptions = new ArrayList<GlobalException>();
		while(iter.hasNext()){
			
			GlobalException exc = iter.next();
			exc = getComponentName(exc);
			exc = getCreator(exc);
			completeExceptions.add(exc);
		}
	*/
		return exceptions;
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
	/**
	 * This helper methods fetches either the component name or vehicle info
	 * dependent on what the DATA_TYPE field was from the prior SQL query results
	 * @param exception - the exception to fetch a componentName for
	 * @return exception - after setting the new componentName, return the whole Exception
	 * @author 600132441 M.Leis
	 */
	private GlobalException getComponentName(GlobalException exception){
		
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
	/**
	 * This method will fetch the First and Last name from user_info based
	 * on the creator_id in the SMC_GLOBAL_EXCEPTIONS table.
	 * @param exception - Global Exception model that will be having the name added
	 * @return exception - Returning the same Global Exception that was passed, but with the added name
	 * @author 600132441 M.Leis
	 */
	private GlobalException getCreator(GlobalException exception){
		
		int id = exception.getCreatedById();
		String creatorFirst = exceptionDao.getCreatorFirstName( id );
		String creatorLast = exceptionDao.getCreatorLastName( id );
		String creator = creatorFirst + " " + creatorLast;
		exception.setCreatedByName(creator);
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
