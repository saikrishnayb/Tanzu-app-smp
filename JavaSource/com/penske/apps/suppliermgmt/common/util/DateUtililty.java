/**
 *****************************************************************************
 * File Name     : DateUtililty
 * Description   : Util class for date related functionalities
 * Project       : SMC
 * Package       : com.penske.apps.common.util
 * Author        : 502299699
 * Date			 : Mar 30, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * ---------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * ---------------------------------------------------------------------------
 *
 * ***************************************************************************
 */
package com.penske.apps.suppliermgmt.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;



public class DateUtililty {
	private static final Logger logger = Logger.getLogger(DateUtililty.class);

	private static final String DATE_FORMAT ="MM/dd/yyyy";
	private static final String FOLDER_NAME_FORMAT ="yyyyMMdd";


	public static String getFormattedDate(Date date) {
		if (date == null){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);

	}
	
	/**
	 * Method to convert String to Date
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String dateString) throws ParseException {
		if (dateString == null)
		{
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.parse(dateString);

	}
	
	/**
	 * Method to get current year
	 * @return year
	 */
	public static int getCurrentYear(){
		int year = 0;
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		logger.debug("Current Year is::"+year);
		return year;
	}
	
	/**
	 * Method to get date for a Folder name in download attachments
	 * @return date in format yyyymmdd
	 */
	public static String getDateForFolderName(){
		
		DateFormat dateFormat = new SimpleDateFormat(FOLDER_NAME_FORMAT);
		Date date = new Date();
		dateFormat.format(date); 
		logger.debug("Folder Name in Download attachments"+dateFormat.format(date));
		return dateFormat.format(date);
	}
	/**
	 * Method to get list  of years +10/-10 from current year
	 * @return list of Strings
	 */
	public static List<String> getModelYearDates()
	{
		List<String> modelYearList=new ArrayList<String>();
	    
	    int curYear  = Calendar.getInstance().get(Calendar.YEAR);
	    int minYear=curYear-10;
	    int maxYear=curYear+3;
	   

	    for (int year = maxYear; year>=minYear; year--){
	    	
	    	modelYearList.add(Integer.toString(year));
	    }
		return modelYearList;
	}
	
}
