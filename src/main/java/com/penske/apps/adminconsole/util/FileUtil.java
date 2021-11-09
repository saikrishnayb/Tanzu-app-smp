package com.penske.apps.adminconsole.util;
/**
 *****************************************************************************************************************
 * File Name     : FileUtil
 * Description   : Util class for File Operations
 * Project       : SMC
 * Package       : com.penske.apps.smcop.common.util
 * Author        : 502157052
 * Date			 : Apr 14, 2015
 * Copyright (C) 2015 Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 **/
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtil
{
	/** Uploaded files may be no larger than 10 MB. */
	private static final long MAX_FILE_SIZE_IN_BYTES = 1024 * 1024 * 10;
	
	/** Private constructor - can't instantiate this class, since it just contains static utility functions */
	private FileUtil() {}
	
	/**
	 * Checks whether a given uploaded file is valid (i.e. whether it is within size limits, and has a valid extension)
	 * @param file The file to validate
	 * @param validExtensionsInLowerCase The extensions that are considered valid for this file upload. They must be in lower-case.
	 * @return If the file is ok to upload, this method returns null. If the file is invalid, returns a reason it is not valid.
	 */
    public static UploadInvalidReason validateFileUpload(MultipartFile file, Collection<String> validExtensionsInLowerCase)
    {
    	Pair<String, String> fileNameData = getFileNameInfo(file.getOriginalFilename());
    	String extension = fileNameData.getRight();
    	
    	long sizeInBytes = file.getSize();
    	
    	if(validExtensionsInLowerCase == null || validExtensionsInLowerCase.isEmpty())
    		throw new IllegalArgumentException("Can not validate file upload. No valid extensions specified.");
    	
    	if(!validExtensionsInLowerCase.contains(extension))
    		return UploadInvalidReason.INVALID_FILE_TYPE;
    	
    	if(sizeInBytes <= 0)
    		return UploadInvalidReason.EMPTY_FILE;
    	
    	if(sizeInBytes > MAX_FILE_SIZE_IN_BYTES)
    		return UploadInvalidReason.FILE_TOO_BIG;
    	
    	return null;
    }
    
    /**
     * Gets a normalized version of the file name. Assigns a default extension of ".pdf" if it has none.
     * @param originalFileName The file name before being normalized.
     * @return The normalized file name.
     */
    public static String getConvertedFileName(String originalFileName)
    {
    	return getFileNameInfo(originalFileName).getLeft();
    }
    
    /**
     * Gets the normalized file name and extension for the given file name. Assigns a default extension of ".pdf" if it has none.
     * @param originalFileName The file name before being normalized
     * @return A pair whose left element is the normalized file name, and whose right element is the extension.
     */
    private static Pair<String, String> getFileNameInfo(String originalFileName)
    {
    	String fileName = StringUtils.trim(originalFileName);
    	if(StringUtils.isBlank(originalFileName))
    		return Pair.of("", "");
    	
    	//If the file has no extension, assume it's a PDF
    	String extension = StringUtils.lowerCase(StringUtils.trim(FilenameUtils.getExtension(fileName)));
    	if(StringUtils.isBlank(extension))
    	{
    		fileName = fileName + ".pdf";
    		extension = "pdf";
    	}
    	
    	return Pair.of(fileName, extension);
    }
	
	//***** HELPER CLASSES *****//
	/** 
	 * Enum containing a list of reasons an uploaded file may be invalid
	 */
	public enum UploadInvalidReason
	{
		/** The extension of the file was not in the acceptable list of extensions for this upload type. */
		INVALID_FILE_TYPE {
			@Override public String getErrorMessage(String fileName) {
				return fileName + " is not a valid type, Please upload a valid file type.";
			}
		},
		/** The file had no data */
		EMPTY_FILE {
			@Override public String getErrorMessage(String fileName) {
				return "Cannot upload " + fileName + ". The file appears to be empty or invalid.  Please check the document and try again.";
			}
		},
		/** The file was too large */
		FILE_TOO_BIG {
			@Override public String getErrorMessage(String fileName) {
				return fileName + " exceeds maximum file size, Please upload documents less than 10 MB.";
			}
		}
		;

		/**
		 * Gets a formatted error message that includes the file name for this type of error.
		 * @param fileName The name of the file to display in the message
		 * @return The formatted message
		 */
		public abstract String getErrorMessage(String fileName);
	}
}
