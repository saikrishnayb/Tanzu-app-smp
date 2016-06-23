/**************************************************************************************
 * Restrictions: GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 * 
 * File: 		POIUtil.java
 * Package: 	com.penske.apps.vsportal.excel.util
 * Version: 	1.0
 * Date:		03-Feb-2010	
 * Description: Utility Class to provide POI related features
 * 				This requires POI jar to be included in the class path.
 * ************************************************************************************/

package com.penske.apps.adminconsole.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * Utility Class to provide POI related features.This requires POI jar to be included in the class path.
 * 
 * @author Johnson Jayaraj
 *
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public class POIUtil{

	private static Logger logger = Logger.getLogger(POIUtil.class);
	
	    /**
	     * Method to ge the Date as java.util.Date 
	     * 
	     * @author 600123480
	     * @param value java.lang.String
	     * @param cell org.apache.poi.hssf.usermodel.HSSFCell
	     * @return java.util.Date
	     * @throws java.lang.Exception
	     */
	    public static Date getDate(String value, HSSFCell cell,String dateFormat ) throws Exception {
	    	
	    	double doubleDate = 0.0;
	    	Date date = null;
	    	if(!DataUtil.isEmpty(value)){
	    		try{
	    			//Method to Check if the value read(interpreted) from the excel sheet is a number.
		    		if(DataUtil.isNumber(value)){
		    			doubleDate = Double.parseDouble(value);	    		
		    			if(HSSFDateUtil.isValidExcelDate(doubleDate)){
	    					// To Check if the value was originally a date or just a number by itself.
	    					// i.e ., date 12/09/2009 can be read(interpreted) by the POI as 40123.4. In this case, even though the value is in the form
	    					// of a number it is a valid date.This should be converted to Date format using getJavaDate method.
	    					// If the Excel cell has the number 345621.0, it can be converted to some date form(e.g., 05/09/1978 using getJavaDate method.
	    					// But if done so, it will be an invalid date.This data should be ignored.
	    					// To do this validation, the following method isCellDateFormatted is used.
		    				if(HSSFDateUtil.isCellDateFormatted(cell)){
		    					date = HSSFDateUtil.getJavaDate(doubleDate);   
		    					date = DateUtil.getDate(date);
		    				}
		    			}
		    			//Method to Check if the value read(interpreted) from the excel sheet is not a number.(Just a String)		 
		    			//User can enter any date formats MM/dd/yyyy , MM/dd/yy, MM-dd-yyyy, MM-dd-yy, etc., 
		    			
		    		}else{			
		    			if(isValidDate(value,dateFormat)){
		    				date = DateUtil.parseDate(value,dateFormat);
		    			}else if(isValidDate(value,DateUtil.MM_DD_YYYY)){
		    				date = DateUtil.parseDate(value,DateUtil.MM_DD_YYYY);		    				
			    		}
		    		}
	    		}catch(Exception e){
	    			logger.error(e.getMessage());
	    		}
	    		
	    	} else {
	    		
	    		date = new Date(-1899,0,1);
	    		
	    	}
	    	
        	return date;
	    }

	    /**
	     * Method to ge the Date as String object
	     * 
	     * @author 600123480
	     * @param value java.lang.String
	     * @param cell org.apache.poi.hssf.usermodel.HSSFCell
	     * @return java.util.Date
	     * @throws java.lang.Exception
	     */
	    public static String getDateString(String value, HSSFCell cell ) throws Exception {	    	
	    	
	    	return getDateString(value,cell,DateUtil.MM_DD_YY);
	    }

	    /**
	     * Method to ge the Date as String object
	     * 
	     * POI Read the Date Values from the Excel sheet in two forms. 1) Number 2) String
	     * 
	     * If it reads the Date Value as number it needs to be converted to a proper date format
	     * If the value itself is a number( not a date, like 342567.9, 324 etc) then this number should be ignored
	     * 
	     * If it reads the Date Value as a String, then the String date should be checked if it is a valid Date or not.
	     * If the value itself is not a proper date (like alpha nummeric, 13/34/2010 etc., ) then they should be ignored
	     * 
	     * @author 600123480
	     * @param value java.lang.String
	     * @param cell org.apache.poi.hssf.usermodel.HSSFCell
	     * @return java.util.Date
	     * @throws java.lang.Exception
	     */
	    public static String getDateString(String value, HSSFCell cell , String dateFormat) throws Exception {
	    	
	    	double doubleDate = 0.0;
	    	Date date = null;
	    	String returnDate = "";
	    	if(!DataUtil.isEmpty(value)){
	    		try{
	    			//Method to Check if the value read(interpreted) from the excel sheet is a number.
		    		if(DataUtil.isNumber(value)){
		    			doubleDate = Double.parseDouble(value);		    			
		    				//To check if it is a valid date
		    				if(HSSFDateUtil.isValidExcelDate(doubleDate)){
		    					//To Check if the value was originally a date or just a number by itself.
		    					//i.e ., date 12/09/2009 can be read by the POI as 40123.4. In this case, even though the value is in the form
		    					// of a number it is a valid date.This should be converted to Date format using getJavaDate method.
		    					// If the Excel cell has the number 345621.0, it can be converted to some date form(e.g., 05/09/1978 using getJavaDate method.
		    					// But if done so, it will be an invalid date.This data should be ignored.
		    					// To do this validation, the following method isCellDateFormatted is used.
		    					if(HSSFDateUtil.isCellDateFormatted(cell)){
		    						date = HSSFDateUtil.getJavaDate(doubleDate);   
		    						date = DateUtil.getDate(date);
		    						returnDate = DateUtil.formatDate(date, dateFormat);
		    					}	
		    				}

		    		//Method to Check if the value read(interpreted) from the excel sheet is not a number.(Just a String)	
		    		}else{
		    			// Method to check if the date is a valid one in the "given date format"
		    			// Even though user enters MM/dd/yy the excel displays it as MM/dd/yyyy.
		    			// To resolve this issue this method validates the date against MM/dd/yyyy too.
		    			//Also,
		    			// User can enter any date formats MM/dd/yyyy , MM/dd/yy, MM-dd-yyyy, MM-dd-yy,yyyy-MM-dd etc., 
		    			
		    			
		    			// New Method can be created to accept additional date formats by including if else conditon or looping 
		    			// through all the date formats and selecting the right one.
		    			// Important thing to keep in mind is that some dates will be valided to be "correct" in multiple formats.
		    			// E.g., 01/02/2009 is a valid against MM/dd/yyyy aswell as dd/MM/yyyy. 
		    			// In that case priority should be assigned to the date formats to decide the dateformat that should have high priority than the other. 
		    			if(isValidDate(value,dateFormat)){
		    				date = new Date(value.trim());
		    				returnDate = DateUtil.formatDate(date, dateFormat);
			    		}else if(isValidDate(value,DateUtil.MM_DD_YYYY)){
			    			date = new Date(value.trim());
		    				//date = DateUtil.parseDate(value,DateUtil.MM_DD_YYYY);	
		    				returnDate = DateUtil.formatDate(date, dateFormat);
			    		}
		    		}
	    		
	    		}catch(Exception e){
	    			logger.error(e.getMessage());
	    		}
	    		
	    	}	
        	return returnDate;
	    }
	    
	    /**
	     * Method to check if the entered String date is valid or not
	     * 
	     * @author 600123480
	     * @since 16-Feb-2010
	     * @param inDate java.lang.String
	     * @param format java.lang.String
	     * @return
	     */
	    public static boolean isValidDate(String inDate, String format) {

	        if (inDate == null)
	          return false;

	        //set the format to use as a constructor argument
	        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	        
	        /*
	        if (inDate.trim().length() != dateFormat.toPattern().length())
	          return false;
			*/
	        dateFormat.setLenient(false);
	        
	        try {
	          //parse the inDate parameter
	        	dateFormat.format(new Date(inDate.trim()));
	        } catch (Exception ex){
	        	logger.debug(ex);
	        	return false;
	        }
	        return true;
	      }
	    
	}	
