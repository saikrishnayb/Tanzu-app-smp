/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File: 		DateUtil.java
 * Package: 	com.penske.apps.excelreport.util
 * Version: 	1.0
 * Date:		14-Sep-2008	
 * Description: Date Utility
 * ************************************************************************************/

package com.penske.apps.adminconsole.model;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Date Utility
 * 
 * @author Johnson Jayaraj
 * @version 1.0
 * 
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public final class DateUtil {

	/**  
	 * This holds the final value of thedate format
	 */	
	
	public static final String	M 					=	"M";
	public static final String	M_yy 				=	"M-yy";
	public static final String	MMM_yy 				=	"MMM-yy";
	public static final String  MM_DD_YY 			= 	"MM/dd/yy";
	public static final String  MM_DD_YYYY 			= 	"MM/dd/yyyy";
	public static final String	month_yy 			=	"month-yy";
	public static final String	MM_dd_yy_HH_mm 		=	"MM/dd/yy HH:mm";
	public static final String  MM_DD_YYYY_HH_MM_SS = 	"MM/dd/yyyy HH:mm:ss";
		
	public static final String	dd_MM		= 	"dd-MM";
	public static final String	dd_MMM 		=	"dd-MMM";
	public static final String	dd_MM_yy 	=	"dd-MM-yy";
	public static final String	ddMMyy 		=	"dd/MM/yy";	
	public static final String	dd_MMM_yy 	=	"dd-MMM-yy";
	public static final String	ddMMyyyy 	=	"dd/MM/yyyy";
	public static final String	dd_MMM_yyyy =	"dd-MMM-yyyy";
	
	
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";	
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";	
		
	public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
	

	private static String currentDate ="";
	private static Logger logger = Logger.getLogger(DateUtil.class);


	/**
	 * Holds the static information about a working week
	 * Starts from Monday and end on Sunday 
	 */
	public final static HashMap WEEKINFO = new HashMap();
	
	static {
		WEEKINFO.put("mon", new Integer(0));
		WEEKINFO.put("tue", new Integer(1));
		WEEKINFO.put("wed", new Integer(2));
		WEEKINFO.put("thu", new Integer(3));
		WEEKINFO.put("fri", new Integer(4));
		WEEKINFO.put("sat", new Integer(5));
		WEEKINFO.put("sun", new Integer(6));

		currentDate = DateUtil.getCurrentDateString(DateUtil.MM_DD_YYYY, DateUtil.DEFAULT_LOCALE);
	}

	public final static HashMap DATEFORMATS = new HashMap();
	static {

		DATEFORMATS.put("DB_DATE_FORMAT", "yyyy-MM-dd");	
		
	}
	
	public DateUtil() {
		super();
	}
	/**
	 * This function formats date in the specified pattern and locale.
	 *
	 */
	public static String formatDate(Date date, String pattern) {

		String dispDate = null;
		if (date != null) {

			SimpleDateFormat formatter =
				new SimpleDateFormat(pattern, DEFAULT_LOCALE);
			dispDate = formatter.format(date);
		}
		return dispDate;
	}
	/**
	 * This function formats date in the specified pattern and locale.
	 *
	 */
	public static String formatDate(Date date, String pattern, Locale locale) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		String dispDate = formatter.format(date);
		return dispDate;
	}
	/**
	 * This function formats date according to the default date display
	 * format specified.
	 *
	 */
	public static String formatDate(Date date, Locale locale) {
		String formattedDate = "";
		if( null != date && null != locale){
			formattedDate =  DateUtil.formatDate(date, DateUtil.MM_DD_YYYY, locale);
		}
		return formattedDate;
	}

	/**
	 * This method returns the current date based on the locale passed
	 */

	public static final String getCurrentDateString(
		String format,
		Locale locale) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
		return formatter.format(date);
	}
	/**
	 * This method returns the current timestamp 
	 */
	public static Date getCurrentTimeStamp() {
		return new java.util.Date();
	}

	/**
	 * This method is used to parse a date.
	 */
	public static Date parseDate(String dateString) throws ParseException {

		return DateUtil.parseDate(
			dateString,
			MM_DD_YYYY_HH_MM_SS,
			Locale.getDefault());
	}
	/**
	 * This function parses the input date string using the input
	 * pattern and the default locale , returns the date obtained.
	 */
	public static Date parseDate(String dateString, String pattern)
		throws ParseException {

		Date retDate = null;
		if (!DataUtil.isEmpty(dateString)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			retDate = new Date(dateFormat.format(new Date(dateString.trim())));
		}
		return retDate;
	}
	
	/**
	 * This function parses the input date string using the pattern
	 * and the locale specified and returns the date obtained.
	 *
	 */

	public static Date parseDate(
		String dateString,
		String pattern,
		Locale locale)
		throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		Date date = formatter.parse(dateString);
		return date;
	}

	/**
	 * This function is used to add the months to the date.
	 *
	 */
	public static Date addMonths(Date startDate, int interval) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		return new GregorianCalendar(
			calendar.get(GregorianCalendar.YEAR),
			calendar.get(GregorianCalendar.MONTH) + interval,
			calendar.get(GregorianCalendar.DAY_OF_MONTH))
			.getTime();
	}

	/**
	 * This function is used to add the days to the date.
	 *
	 */
	public static Date addDays(Date startDate, int interval) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		return new GregorianCalendar(
			calendar.get(GregorianCalendar.YEAR),
			calendar.get(GregorianCalendar.MONTH),
			calendar.get(GregorianCalendar.DAY_OF_MONTH) + interval)
			.getTime();
	}

	/**
	 * This function is used to return the number of days in the month.
	 *
	 */
	public static int noOfDaysInMonth(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * This function is used to return the week in the month.
	 *
	 */
	public static int weekInMonth(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		final int day = dayInMonth(date);
		int weekInMonthVal = 0;
		weekInMonthVal = day / 7 + 1;
		return weekInMonthVal;
	}

	/**
	 * This function is used to return the week in the month.
	 *
	 */
	public static int dayInMonth(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return gCal.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * This function is used to return the week in the month.
	 *
	 */
	public static int dayInMonth(Date date, int week) {
		Date startDate = date;
		GregorianCalendar gCal = new GregorianCalendar();
		int noOfDaysInWeekVal = 0;
		for (int i = 1; i < week; i++) {
			noOfDaysInWeekVal = noOfDaysInWeekOfMonth(startDate);
			startDate = addDays(startDate, noOfDaysInWeekVal);

		}
		gCal.setTime(startDate);
		return gCal.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * This function is used to return the day in theweek.
	 *
	 */
	public static int dayInWeek(Date date) {
		int dayInWeekVal = 0;
		final int dayInMonthVal = dayInMonth(date);
		final int weekInMonthVal = weekInMonth(date);
		dayInWeekVal = 7 - (weekInMonthVal * 7 - dayInMonthVal);
		return dayInWeekVal;
	}

	/**
	 * This function is used to return the number of weeks in the month.
	 *
	 */
	public static int noOfDaysInWeekOfMonth(Date date) {
		int noOfDaysInWeekOfMonthVal = 0;
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		final int weekInMonthVal = weekInMonth(date);
		final int maxWeeksInMonth = noOfWeeksInMonth(date);
		final int startDayInWeek = gCal.getFirstDayOfWeek();
		final int dayOfWeekInMonth = gCal.get(GregorianCalendar.DAY_OF_WEEK);
		final int noOfDaysInWeekVal =
			gCal.getActualMaximum(GregorianCalendar.DAY_OF_WEEK);
		final int noOfDaysInMonthVal = noOfDaysInMonth(date);
		final int dayInMonthVal = dayInMonth(date);
		if (weekInMonthVal == 1) {
			noOfDaysInWeekOfMonthVal =
				noOfDaysInWeekVal - (dayOfWeekInMonth - startDayInWeek);
		}
		else if (weekInMonthVal == maxWeeksInMonth) {
			noOfDaysInWeekOfMonthVal = (noOfDaysInMonthVal - dayInMonthVal) + 1;
		}
		else {
			noOfDaysInWeekOfMonthVal = noOfDaysInWeekVal;
		}

		return noOfDaysInWeekOfMonthVal;
	}

	/**
	 * This function is used to return the number of weeks in the month.
	 *
	 */
	public static int noOfWeeksInMonth(Date date) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (maxDays > 28) {
			return 5;
		}
		else {
			return 4;
		}
	}

	/**
	 * This function is used to return the number of weeks in the month.
	 *
	 */
	public static int noOfWorkingWeeksInMonth(Date date) {

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int noofSun = 0;
		int noOfMon = 0;
		int noOfWeeks = 0;
		int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String dtStr = null;

		for (int i = 1; i <= maxDays; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			dtStr = formatDate(cal.getTime(), "EEE").toLowerCase();
			if ("mon".equals(dtStr)) {
				noOfMon++;
			}
			if ("sun".equals(dtStr)) {
				noofSun++;
			}
		}
		if (noOfMon > noofSun) {
			noOfWeeks = noOfMon;
			if (!"mon".equals(dtStr)) {
				noOfWeeks++;
			}
		}
		else {
			noOfWeeks = noofSun;
			if (!"sun".equals(dtStr)) {
				noOfWeeks++;
			}
		}

		return noOfWeeks;
	}

	/**
	 * This function is used to return the month in the year.
	 *
	 */
	public static int monthInYear(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return gCal.get(GregorianCalendar.MONTH);
	}

	/**
	 * This function is used to return the day of the month.
	 *
	 */
	public static int dayOfMonth(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return gCal.get(GregorianCalendar.DAY_OF_WEEK);
	}

	/**
	 * This function is used to return the number of days in a week.
	 *
	 */
	public static int noOfDaysInWeek(Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		return gCal.getActualMaximum(GregorianCalendar.DAY_OF_WEEK);
	}

	/**
	 * This method is used to returns the short months.
	 */
	public static String[] getShortMonths(Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		return symbols.getShortMonths();
	}

	/**
	 * This method is used to returns the short day.
	 */
	public static String getShortDay(Locale locale, Date date) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(date);
		final int day = gCal.get(GregorianCalendar.DAY_OF_WEEK);
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[] shortDays = symbols.getShortWeekdays();
		return shortDays[day];
	}

	/**
	 * This function parses the input date string using the default
	 * pattern and the locale specified and returns the date obtained.
	 *
	 */

	public static Date parseDate(String dateString, Locale locale)
		throws ParseException {

		return DateUtil.parseDate(
			dateString,
			DateUtil.MM_DD_YYYY_HH_MM_SS,
			locale);
	}

	/**
	 * This method returns the difference between two dates.
	 */
	public static long differenceInDate(Date date1, Date date2) {
		long timeDifference = 0;
		if (date1 != null && date2 != null) {
			timeDifference = date1.getTime() - date2.getTime();
		}
		return timeDifference;
	}

	/**
	 * Method sets the start of the day as 0:0:0 
	 */
	public static Date getStartOfDay(Date dt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Sets the end of the day as 23:59:59
	 */
	public static Date getEndOfDay(Date dt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * Given a date, finds out the start of the week in which the date belongs to
	 */
	public static Date getWeekStart(Date dt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dt);
		int week = cal.get(Calendar.WEEK_OF_MONTH);
		int dayOfMonth = 0;
		String dtStr = null;
		int diff = 0;

		if (week == 1) {
			//If first week, set the start date as day-1
			cal.set(Calendar.DAY_OF_MONTH, 1);
			return cal.getTime();
		}
		else if (week == cal.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
			//if last week of the month, set the date as last day of month
			cal.set(
				Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}

		dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		//Calculate the difference from the given date
		dtStr = formatDate(cal.getTime(), "EEE").toLowerCase();
		diff = 0 - ((Integer) WEEKINFO.get(dtStr)).intValue();
		dayOfMonth += diff;
		//Set the difference as the start of the week
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return cal.getTime();
	}

	/**
	 * Finds out the end of the week in which the given date belongs to
	 */
	public static Date getWeekEnd(Date dt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dt);
		int week = cal.get(Calendar.WEEK_OF_MONTH);
		int dayOfMonth = 0;
		String dtStr = formatDate(dt, "EEE").toLowerCase();
		int diff = 0;

		if (week == 1) {
			//If first week
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		else if (week == cal.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
			cal.set(
				Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			return cal.getTime();
		}
		dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		dtStr = formatDate(cal.getTime(), "EEE").toLowerCase();
		diff = 6 - ((Integer) WEEKINFO.get(dtStr)).intValue();
		dayOfMonth += diff;
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		return cal.getTime();
	}

	/**
	 * Calculates the number days between the dates including the end date
	 */
	public static int getNoOfDays(Date startDt, Date endDt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(startDt);
		int start = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(endDt);
		int end = cal.get(Calendar.DAY_OF_MONTH);
		return (end - start) + 1;
	}
	
	public static Date getDate(Date newDate) throws Exception{
		
		Iterator itr = DATEFORMATS.values().iterator();
		String dateFormat = DB_DATE_FORMAT;
		Date returnDate = null;
		Date tempDate = null;
		String tempDateStr = "";
		while(itr.hasNext()){
			dateFormat = (String)itr.next();
		try{
				tempDateStr = formatDate(newDate,dateFormat);
				tempDate = parseDate(tempDateStr,dateFormat);
				
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			if(null != tempDate ){
				returnDate = tempDate;
			}
		}
		return returnDate;
	}
	

	public static String parseDate(String dateToBeConverted , String inputFormat , String outputFormat){
		String returnDate = null;
		try{
		if(!DataUtil.isEmpty(dateToBeConverted) & !DataUtil.isEmpty(inputFormat) & !DataUtil.isEmpty(outputFormat)){			
			returnDate = formatDate(parseDate(dateToBeConverted,inputFormat),outputFormat);
		}
		}catch(Exception exp){
			logger.error(exp.getMessage()); 
		} 
		return returnDate;		
	}

	public static Date convertStringToDate(String dateToBeConverted , String inputFormat , String outputFormat){
		Date returnDate = null;
		try{
		if(!DataUtil.isEmpty(dateToBeConverted) & !DataUtil.isEmpty(inputFormat) & !DataUtil.isEmpty(outputFormat)){			
			returnDate = parseDate(formatDate(parseDate(dateToBeConverted,inputFormat),outputFormat));
		}
		}catch(Exception exp){
			logger.error(exp.getMessage()); 
		} 
		return returnDate;		
	}

	public static int getCurrentYear(){
		
		String yyyy = currentDate.substring(6,10);	
		return Integer.parseInt(yyyy);
	}
	
	public static int getCurrentMonth(){
				
		String month = currentDate.substring(0,2);	
		return Integer.parseInt(month);
	}
	
	public static int getCurrentDay(){
		
		String day = currentDate.substring(3,5);
		return Integer.parseInt(day);
	}

	public static String getAs400Month(String as400Dt){
    	
    	String day = "";
    	if(!DataUtil.isEmpty(as400Dt)){
    		day = as400Dt.substring((as400Dt.length() - 4), as400Dt.length()-2);
    	}	
    	
    	return day;
	}

	public static String getAs400Day(String as400Dt){
    	
    	String month = "";
    	if(!DataUtil.isEmpty(as400Dt)){
    		month = as400Dt.substring((as400Dt.length() - 2), as400Dt.length());
    	}	
    	
    	return month;
	}
	
	
}
