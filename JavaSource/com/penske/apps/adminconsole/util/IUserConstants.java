package com.penske.apps.adminconsole.util;

public interface IUserConstants {


	/**
	 * Represents the error code indicating that the user already exists in LDAP server.
	 */
	public static final int DUP_SSO_ERROR_CODE = 1;
	
	public static final int NOT_STANDARD_SSO_ERROR_CODE = 11;


	public static final int WEBSERVICE_RESPONSE_ERROR_CODE = 2;

	/**
	 * Represents the error code indicating that the add user service was unsuccessful
	 */
	public static final int EMPTY_RESPONSE_ADD_ERROR_CODE = 3;

	/**
	 * Represents the error code indicating that the modify user service was unsuccessful
	 */
	public static final int EMPTY_RESPONSE_MODIFY_ERROR_CODE = 4;

	/**
	 * Represents the error code indicating that the user wasn't found in LDAP
	 */
	public static final int USER_NOT_FOUND_LDAP_ERROR_CODE = 5;
	
	/**
	 * Represents the error code indicating that there was an invalid parameter
	 */
	public static final int INVALID_PARAMETER_EMAIL_ERROR_CODE = 6;

	/**
	 * Represents the error code indicating there was an error sending the mail
	 */
	public static final int EMAIL_ERROR_CODE = 7;

	/**
	 * Represents the error code indicating there was an error while data was inserted in SMC_USER_MASTER table
	 */
	public static final int USER_INFO_INSERT_ERROR_CODE = 8;

	/**
	 * Represents the error code indicating there was an error while data was updated in SMC_USER_MASTER table
	 */
	public static final int USER_INFO_UPDATE_ERROR_CODE = 9;
	
	/**
	 * Represents the error code indicating that the request failed
	 */
	public static final int REQUEST_FAILED_ERROR_CODE = 10;


	/**
	 * Represents the error message indicating that the user already exists in LDAP server.
	 */
	public static final String DUP_SSO_ERROR_MESSAGE = "UserID already exists. Please choose a different UserID";


	public static final String WEBSERVICE_RESPONSE_ERROR_MESSAGE = "Unable to process your request at this moment. Please try later";

	/**
	 * Represents the error message indicating that the add user service was unsuccessful
	 */
	public static final String EMPTY_RESPONSE_ADD_ERROR_MESSAGE = "Add user operation was not successful.";

	/**
	 * Represents the error message indicating that the modify user service was unsuccessful
	 */
	public static final String EMPTY_RESPONSE_MODIFY_ERROR_MESSAGE = "Modify user operation was not successful.";

	/**
	 * Represents the error messsage indicating that the request failed
	 */
	public static final String REQUEST_FAILED_ERROR_MESSAGE = "Sending request failed";


	/**
	 * Represents the error messsage indicating that there was an invalid parameter
	 */
	public static final String INVALID_PARAMETER_EMAIL_ERROR_MESSAGE = "Invalid parameter. Email could not be sent!";

	/**
	 * Represents the error messsage indicating there was an error sending the mail
	 */
	public static final String EMAIL_ERROR_MESSAGE = "Exception while sending mail";

	/**
	 * Represents the error messsage indicating there was an error while data was inserted in SMC_USER_MASTER table
	 */
	public static final String USER_INFO_INSERT_ERROR_MESSAGE = "Error while  inserting error in database after unsuccessful service in SMC_USER_MASTER table";

	/**
	 * Represents the error messsage indicating there was an error while data was updated in SMC_USER_MASTER table
	 */
	public static final String USER_INFO_UPDATE_ERROR_MESSAGE = "Error while  updating error in database after unsuccessful service in SMC_USER_MASTER table";

	/**
	 * Represents that the client's emailType is NO_EMAIL
	 */
	public static final String NO_EMAIL = "NO_EMAIL";
	
	public static final String OPERATION_EXECUTED_SUCCESS = "operation executed successfully";

	public static final String ATTR_VAL_ALREADY_EXISTS = "attribute value already exists";
	
	/**
	 * Represents the error messsage indicating that the user wasn't found in LDAP
	 */
	public static final String USER_NOT_FOUND_LDAP_ERROR_MESSAGE = "Cant find user in LDAP";

	public static final String NOT_STANDARD_SSO_ERROR_MESSAGE = "UserID does not conform to standards.'";
}
