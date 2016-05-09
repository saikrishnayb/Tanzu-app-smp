package com.penske.apps.suppliermgmt.common.util;

/**
 *****************************************************************************************************************
 * File Name     : Lookup Manger
 * Description   : This class is used for loading the lookup data while server is starting 
 * Project       : SMC
 * Package       : com.penske.apps.smcop.util
 * Author        : 502403391
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;
import com.penske.apps.suppliermgmt.common.exception.SMCException;
import com.penske.apps.suppliermgmt.model.LookUp;
import com.penske.apps.suppliermgmt.service.LookUpDataService;

@Component
public class LookupManager {

	@Autowired
	private LookUpDataService lookUpDataService;

	private transient static final Logger LOGGER = Logger
			.getLogger(LookupManager.class);

	private static Map<String, List<LookUp>> lookupMapData;

	/**
	 * Call the loadLookUpData method in application startup
	 * 
	 * @throws SMCException
	 */

	public void loadLookUpData() throws SMCException {
		if (lookupMapData == null || lookupMapData.isEmpty()) {
			loadSMCLookupList();
		}

	}

	/**
	 * Get the default,status,days of week drop down values based on the lookup
	 * name
	 */
	private void loadSMCLookupList() throws SMCException {
		lookupMapData = new HashMap<String, List<LookUp>>();
		List<LookUp> lookuplist = null;
		List<LookUp> tempLookUpData = new ArrayList<LookUp>();
		LookUp lookUpTmp = new LookUp();
		LookUp lookupDeatils = null;

		try {
			lookuplist = new ArrayList<LookUp>();
			lookuplist = lookUpDataService.getAllLookupList();
			LOGGER.info("Lookup Data Loaded Successfully..!");
		} catch (Exception ex) {
			LOGGER.error("Exception " + ex);
			throw new SMCException(0, ex.getMessage(), ex);
		}
		if (lookuplist != null) {
			for (LookUp lookUp : lookuplist) {
				String lookupName = lookUp.getLookUpName();
				tempLookUpData = lookupMapData.get(lookupName);
				if (tempLookUpData == null) {
					tempLookUpData = new ArrayList<LookUp>();
					tempLookUpData.add(lookUp);
					lookupMapData.put(lookupName, tempLookUpData);
				} else {
					tempLookUpData.add(lookUp);
				}
			}

			// Adding Max of created By and Max of modified by and Count to the
			// list

			try {
				LOGGER.info("Getting max created Date and modified date from DB");
				// get max dates and count from DB
				lookupDeatils = lookUpDataService.getLookupDetails();
			} catch (Exception ex) {
				LOGGER.error("Exception " + ex);
				throw new SMCException(0, ex.getMessage(), ex);
			}

			if (lookupDeatils != null) {

				// storing max created date in Map
				lookUpTmp = new LookUp();
				lookUpTmp.setLookUpName(ApplicationConstants.MAX_CREATED_DATE);
				lookUpTmp.setLookUpValue(lookupDeatils.getMaxCreatedDate());
				LOGGER.info("max created date"
						+ lookupDeatils.getMaxCreatedDate());
				tempLookUpData = new ArrayList<LookUp>();
				tempLookUpData.add(lookUpTmp);
				lookupMapData.put(ApplicationConstants.MAX_CREATED_DATE,
						tempLookUpData);

				// storing max modified date in map
				lookUpTmp = new LookUp();
				lookUpTmp.setLookUpName(ApplicationConstants.MAX_MODIFIED_DATE);
				LOGGER.info("max modified date"
						+ lookupDeatils.getMaxModifiedDate());
				lookUpTmp.setLookUpValue(lookupDeatils.getMaxModifiedDate());
				tempLookUpData = new ArrayList<LookUp>();
				tempLookUpData.add(lookUpTmp);
				lookupMapData.put(ApplicationConstants.MAX_MODIFIED_DATE,
						tempLookUpData);

				// storing count in MAP
				lookUpTmp = new LookUp();
				lookUpTmp.setLookUpName(ApplicationConstants.COUNT);
				lookUpTmp.setLookUpValue(Integer.toString(lookupDeatils.getCount()));
				LOGGER.info("count" + lookupDeatils.getCount());
				tempLookUpData = new ArrayList<LookUp>();
				tempLookUpData.add(lookUpTmp);
				lookupMapData.put(ApplicationConstants.COUNT, tempLookUpData);

			}

		}
	}

	/**
	 * Get the default,status,days of week drop down values from lookupMap
	 * 
	 * @param lookUpName
	 * @return
	 */
	public List<LookUp> getLookUpListByName(String lookUpName) {

		return lookupMapData.get(lookUpName);
	}

	/**************************************************************************************************************
	 * Get the look up values from lookupMap
	 * 
	 * @param lookUpName
	 * @param sequence
	 * @return lookUpValue
	 **************************************************************************************************************/
	public String getLookUpListByValue(String lookUpName, int sequence) {

		LOGGER.debug("lookup name :" + lookUpName);
		LOGGER.debug("lookup sequence :" + sequence);
		List<LookUp> rawList = lookupMapData.get(lookUpName);
		String lookUpValue = "";
		for (LookUp value : rawList) {
			if (value.getLookUpSeq() == sequence) {
				lookUpValue = value.getLookUpValue();
			}
		}
		return lookUpValue;
	}

	/**
	 * To reload the lookup data when user logged in
	 * 
	 * @throws SMCException
	 */
	public void reloadLookupData() throws SMCException {

		LookUp tempLookup = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				ApplicationConstants.TIMESTAMP_FORMAT);
		Date parsedDate = null;
		String maxCreatedDateOld = null;
		String maxmodifiedDateOld = null;
		Timestamp maxCreatedDateOldTS = null;
		Timestamp maxCreatedDateNewTS = null;
		Timestamp maxmodifiedDateOldTS = null;
		Timestamp maxModifiedDateNewTS = null;
		int createdDateCheck = 0;
		int modifiedDateCheck = 0;
		int countOld = 0;
		int countNew = 0;

		LOGGER.info("Checking for the changes in Lookup Data");
		// getting new lookup details from lookup table
		try {

			LookUp lookupDeatils = lookUpDataService.getLookupDetails();

			if (lookupDeatils != null) {

				// getting the lookup details from the Map
				List<LookUp> maxCreatedDate = lookupMapData
						.get(ApplicationConstants.MAX_CREATED_DATE);
				// max created date old from map
				if (maxCreatedDate != null) {
					tempLookup = maxCreatedDate.get(0);
					maxCreatedDateOld = tempLookup.getLookUpValue();
					parsedDate = dateFormat.parse(maxCreatedDateOld);
					maxCreatedDateOldTS = new java.sql.Timestamp(
							parsedDate.getTime());
				}

				// max modified date old from map
				List<LookUp> maxmodifiedDate = lookupMapData
						.get(ApplicationConstants.MAX_MODIFIED_DATE);
				if (maxmodifiedDate != null) {
					tempLookup = maxmodifiedDate.get(0);
					maxmodifiedDateOld = tempLookup.getLookUpValue();
					parsedDate = dateFormat.parse(maxmodifiedDateOld);
					maxmodifiedDateOldTS = new java.sql.Timestamp(
							parsedDate.getTime());
				}

				// count from map
				List<LookUp> countList = lookupMapData
						.get(ApplicationConstants.COUNT);
				if (countList != null) {
					tempLookup = countList.get(0);
					if (tempLookup.getLookUpValue() != null) {
						countOld = NumberUtils.toInt(tempLookup
								.getLookUpValue());
					}
				}

				// count from DB
				countNew = lookupDeatils.getCount();
				

				// max created Date from DB
				if (lookupDeatils.getMaxCreatedDate() != null) {
					parsedDate = dateFormat.parse(lookupDeatils
							.getMaxCreatedDate());
					maxCreatedDateNewTS = new java.sql.Timestamp(
							parsedDate.getTime());

				}

				// max modified date from DB
				if (lookupDeatils.getMaxModifiedDate() != null) {
					parsedDate = dateFormat.parse(lookupDeatils
							.getMaxModifiedDate());
					maxModifiedDateNewTS = new java.sql.Timestamp(
							parsedDate.getTime());
				}

				// comparing created dates
				if (maxCreatedDateNewTS != null && maxCreatedDateOldTS != null) {
					createdDateCheck = maxCreatedDateNewTS
							.compareTo(maxCreatedDateOldTS);
				}
				// comparing modified dates
				if (maxModifiedDateNewTS != null
						&& maxmodifiedDateOldTS != null) {
					modifiedDateCheck = maxModifiedDateNewTS
							.compareTo(maxmodifiedDateOldTS);
				}

				if (createdDateCheck == 1 || modifiedDateCheck == 1
						|| countOld != countNew) {
					LOGGER.info("There are some Changes in Lookup Data,Reloading the lookup list");
					loadSMCLookupList();
				} else {
					LOGGER.info("No changes in the LookupData");
				}

			}

		} catch (ParseException e) {
			LOGGER.error("Error in parsing the dates" + e.getLocalizedMessage());
			throw new SMCException(0, e.getMessage(), e);
		} catch (SMCException e) {
			LOGGER.error("Exception " + e.getLocalizedMessage());
			throw new SMCException(0, e.getMessage(), e);
		}

	}

}
