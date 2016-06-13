/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File: 		DataUtil.java
 * Package: 	com.penske.apps.vsportal.util
 * Version: 	1.0
 * Date:		14-Sep-2008	
 * Description: Data Utility
 * ************************************************************************************/
package com.penske.apps.adminconsole.model;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * This is a utility class can be used for various data validation,
 * null checking , formatting and other general purposes.
 *
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public class DataUtil {

	
	private static Logger logger = Logger.getLogger(DataUtil.class);
	
	/** 
	 * Holds the value of the formatter to be used for formatting data 
	 */
	public static final String FORMATTER_STRING = "-";
	
	/**
	 *  Holds the default double value
	 */
	public static final double DEFAULT_DOUBLE = 0.0;

	/**  
	 * Holds the value of the default return string
	 */
	public static final String DEFAULT_RETURN_STRING = "";
	
	/** 
	* Holds the static string code used to replace SINGLE QUOTE [']
	*/
	public static final String SING_QUOTE_CD = "X~X";
	
	/** 
	 * Holds the SINGLE QUOTE 
	 */
	public static final String SINGLE_QUOTE = "'";
	
	/** 
	 * Holds the comma delimiter
	 */
	public static final String DELIMITER = ",";
	
	/** 
	 * Holds the yes indicator
	 */
	public static final String YES = "Y";
	
	/** 
	 * Holds the no indicator
	 */
	public static final String NO = "N";

	private static HashMap months = new HashMap();
	
	static{
		months.put("01", "JAN");
		months.put("02", "FEB");
		months.put("03", "MAR");
		months.put("04", "APR");
		months.put("05", "MAY");
		months.put("06", "JUN");
		months.put("07", "JULY");
		months.put("08", "AUG");
		months.put("09", "SEP");
		months.put("10", "OCT");
		months.put("11", "NOV");
		months.put("12", "DEC");		
	}

	public static final String CENTURY_21_PREFIX = "20";   
	public static final int CENTURY_21_AS400_PREFIX = 1;
	/**
	 * DataUtils constructor comment.

	 */
	protected DataUtil() {
		super();

		
	}
	/**
	 * This method is used to convert a String to a Int value.
	 */
	public static final int convertToInt(String val, int defaultVal) {
		String value = replaceNull(val, "0");
		
		int intVal = 0;
		double doubleVal = 0.0;

		try{
			intVal = Integer.parseInt(value);
		}catch(NumberFormatException nfe){
			try{
				doubleVal = Double.parseDouble(value);
				intVal = (int)doubleVal;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (intVal == 0) {
			return defaultVal;
		}
		else {

			return intVal;
		}
	}

	
	/**
	 * This method is used to convert a String to a Int value.
	 */
	public static final long convertToLong(String val, long defaultVal) {
		String value = replaceNull(val, "0");
		
		long longVal = 0;
		double doubleVal = 0.0;

		try{
			longVal = Long.parseLong(value);
		}catch(NumberFormatException nfe){
			try{
				doubleVal = Double.parseDouble(value);
				longVal = (int)doubleVal;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (longVal == 0) {
			return defaultVal;
		}
		else {

			return longVal;
		}
	}
	
	/**
	 * Convert the value to double value
	 * 
	 * @param val java.lang.String
	 * @return double
	 */
	public static final double convertToDouble(String val) {
		String value = replaceNull(val, "0");
		

		double doubleVal = 0.0;


			try{
				doubleVal = Double.parseDouble(value);
				
			}catch(Exception e){
				e.printStackTrace();
			}

		if (doubleVal == 0.0) {
			return doubleVal;
		}
		else {

			return doubleVal;
		}
	}
	
	/**
	 * Method to decode XML data.
	 * 
	 * Replaces all the Single character sequence with Single Quotes.
	 */
	public final static String decodeXML(String xml) {
		return DataUtil.replaceString(xml, SING_QUOTE_CD, "\'");
	}
	
	/**
	 * Method to encode xml.
	 * Replaces all the Single Quotes using a character sequence.
	 * 
	 */
	public final static String encodeXML(String xml) {
		return DataUtil.replaceString(xml, "'", SING_QUOTE_CD);
	}
	
	/**
	 * This function can be used to check if string
	 * is null.
	 */
	public final static boolean isEmpty(String str) {

		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function can be used to check if Date
	 * is null.
	 */
	public final static boolean isEmpty(Date date) {

		if (date == null) {
			return true;
		}
		return false;
	}
	/**
	 * This function can be used to check if Enumeration
	 * is null.
	 */
	public final static boolean isEmpty(Enumeration list) {

		if ((list == null) || !(list.hasMoreElements())) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function can be used to check if Iterator
	 * is null.
	 */
	public final static boolean isEmpty(Iterator list) {

		if ((list == null) || !(list.hasNext())) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function can be used to check if Vector
	 * is null or Empty.
	 */
	public final static boolean isEmpty(Vector vec) {

		if (vec == null || vec.isEmpty()) {
			return true;
		}

		return false;

	}
	
	/**
	 * This function can be used to check if Vector
	 * is null or Empty.
	 */
	public final static boolean isEmpty(List list) {

		if (list == null || list.size() == 0) {
			return true;
		}
		return false;

	}
	
	/**
	 * converts string to double
	 * 
	 */
	public static double toDouble(String source) {
		double targetValue = 0;
		if (!isEmpty(source)) {
			targetValue = Double.parseDouble(source);
		}

		return targetValue;
	}

	/**
	 * converts string to int
	 * 
	 */
	public static int toInt(String source) {
		int targetValue = 0;
		if (!isEmpty(source)) {
			String str = source.trim();
			if (str.charAt(0) == '+') {
				if (str.length() > 1) {
					str = str.substring(1);
				} 
				else {
					str = "0"; //$NON-NLS-1$
				}
			}
			targetValue = Integer.parseInt(str);
		}

		return targetValue;
	}
	

	/**
	 * Checks whether the passed string is null or not.
	 * If the object is null, return the 'defaultVal' else return
	 * an empty string.
	 */
	public static String replaceNull(Object obj) {

		return DataUtil.replaceNull(obj, DataUtil.DEFAULT_RETURN_STRING);

	}
	/**
	 * Checks whether the passed string is null or not.
	 */
	public static String replaceNull(Object obj, String defaultVal) {

		if ((obj == null)
			|| ("null".equalsIgnoreCase(obj.toString()))
			|| obj.toString().length() == 0) {
			return defaultVal;
		}
		else {
			return obj.toString().trim();
		}

	}
	/**
	 * Replace all the occurences of OLD string with NEW string.
	 * 
	 * Use this method only when the new string is dont have any character of the old string.
	 * 
	 * Eg;.,Replacing , with "," will not work
	 */
	public final static String replaceString(
		String str,
		String oldStr,
		String newStr) {
		String value = DataUtil.replaceNull(str);
		StringBuffer valueBuf = new StringBuffer(value);
		int index = value.indexOf(oldStr);
		final int oldLen = replaceNull(oldStr).length();

		while (index != -1) {
			valueBuf.replace(index, index + oldLen, newStr);
			value = valueBuf.toString();
			index = value.indexOf(oldStr);
		}
		return valueBuf.toString();
	}

	/**
	 * This method is used to format the data.
	 */
	public final static String formatData(
		String data1ToBeFormatted,
		String data2ToBeFormatted) {
		StringBuffer formatter = new StringBuffer(data1ToBeFormatted);
		formatter.append(FORMATTER_STRING);
		formatter.append(data2ToBeFormatted);
		return formatter.toString();
	}
	
	public static String safeTrim(String str) {
		return str != null ? str.trim() : null;
	}


	public final static String prefixZero(String inputString, int reqLength) {

		if (inputString == null || inputString.length() == 0) {
			inputString = "0";
		}
		int inputLength = inputString.length();
		int padLength = reqLength - inputLength;
		StringBuffer buf = new StringBuffer();
		while (padLength > 0) {
			buf.append("0");
			padLength--;
		}
		buf.append(inputString);
		return buf.toString();
	}


	public static float convertToFloat(String strNum) {
		return Float.parseFloat(strNum);
	}
	
	/**
	 * Creates a string out of the String array delimited by the delimiter specified
	 * 
	 * @param inputArray java.lang.String[]
	 * @param delimiter java.lang.String
	 * @return java.lang.String
	 */
	public static String createString(String[] inputArray, String delimiter) {
		StringBuffer resultString = new StringBuffer("");
		String currString = null;
		if (inputArray != null) {
			int len = inputArray.length;
			for (int count = 0; count < len; count++) {
				currString = inputArray[count];
				if (!DataUtil.isEmpty(currString)) {
					resultString.append(currString);
					resultString.append(delimiter);
				}
			}
			if (resultString.length() > 1) {
				resultString.setLength(resultString.length() - 1);
			}
		}
		return resultString.toString();
	}
	
	/**
	 * To trim data
	 * 
	 * @param data java.lang.String
	 * @return java.lang.String
	 */
	public static String trim(String data){
		String trimmedData = "";
		if(null != data){
			trimmedData = data.trim();
		}
		return trimmedData;
	}
	
	//Temp Util method . To be moved to seperate class.
	
	public static HSSFCellStyle buildStyle(HSSFWorkbook workBook , String styleName,boolean isLocked){

		HSSFCellStyle cellStyle = workBook.createCellStyle();
		HSSFFont cellFont = workBook.createFont();

		if(null != styleName){
			if(styleName.equalsIgnoreCase("CELL")){
				
				cellStyle = workBook.createCellStyle();			
				cellStyle.setFont(cellFont);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);								
		
				cellStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN );
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	    	
		    
				cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
				cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);	
				
				if(isLocked){
					cellStyle.setLocked(isLocked);
				}else{
					cellStyle.setLocked(false);
				}
			}
		}		
		return cellStyle;
	}
	
	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static double getDouble(String s) {
		double value = 0.0;
		try {
			value = Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) {
			return value;
		}
		return value;
	}
	
	public static String formatFixedDecimal(Double value, String decimalFormat){
		String returnValue = "0.00";
		
		DecimalFormat twoDForm = null;
		if( null != decimalFormat && null != value){
	        twoDForm = new DecimalFormat(decimalFormat);
	        returnValue = twoDForm.format(value.doubleValue());			
		}
		return returnValue;
		
	}
	public static HashMap getMonths() {
		return months;
	}
	public static void setMonths(HashMap months) {
		DataUtil.months = months;
	}
	
	public static String getCenturyPrefix(int as400Century){
		String returnValue = "";
		if(as400Century == CENTURY_21_AS400_PREFIX){
			returnValue = CENTURY_21_PREFIX;
		}else if(as400Century > CENTURY_21_AS400_PREFIX){
			int diff = as400Century - CENTURY_21_AS400_PREFIX;
			returnValue = String.valueOf(Integer.parseInt(CENTURY_21_PREFIX) + diff); 
		}
		return returnValue;
	}
	
	public static String as400ToCalendarYear(String as400Year){
		String yearPrefix ="";
		String yearSuffix = "";
		String century = "";
		String year = "";
		if(!DataUtil.isEmpty(as400Year)){			
			
			if(as400Year.length()> 2){
				century = as400Year.substring(0,as400Year.length()-2);
				yearPrefix = DataUtil.getCenturyPrefix(Integer.parseInt(century));
			}
			yearSuffix = as400Year.substring(as400Year.length()-2,as400Year.length());
		}
		year = yearPrefix+yearSuffix;
		return year;
	}
	
	
	public static final int convertToInt(String val) {
		String value = replaceNull(val, "0");
		
		int defaultVal =0; 
		int intVal = -1;
		double doubleVal = 0.0;

		try{
			intVal = Integer.parseInt(value);
		}catch(NumberFormatException nfe){
			try{
				doubleVal = Double.parseDouble(value);
				intVal = (int)doubleVal;
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		if (intVal == -1){
			return defaultVal;
		}else {

			return intVal;
		}
	}	
	
	public static final long convertToLong(String val) {
		String value = replaceNull(val, "0");
		
		long defaultVal =0; 
		long longValue = -1;
		double doubleVal = 0.0;

		try{
			longValue = Long.parseLong(value);
		}catch(NumberFormatException nfe){
			try{
				doubleVal = Double.parseDouble(value);
				longValue = (long)doubleVal;
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		if (longValue == -1){
			return defaultVal;
		}else {

			return longValue;
		}
	}	
	
	private static final boolean isLong(long longValue){
		if(longValue <= Integer.MAX_VALUE){
			return false;
		}
		return true;
	}
	
	public static String getStringValue(String value){
		String returnVal  = value;
		if(DataUtil.isNumber(value)){			
			int indexVal = value.indexOf(".");
			if(indexVal != -1){
				returnVal = value.substring(0,indexVal);
			}
		}
		return returnVal;		
	}
	
	//Any one of the methods should be used. 
	public static String getStringValue2(String value){
		String returnVal  = value;	
		long longVal = 0;
		if(DataUtil.isNumber(value)){			
			longVal = convertToLong(value);
			returnVal = String.valueOf(longVal);
		}
		 
		return returnVal;	
	}
	


}
