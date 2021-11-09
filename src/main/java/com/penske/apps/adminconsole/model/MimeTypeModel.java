/*
 *Data Classification : GE-PSLGDC Confidential
 */
package com.penske.apps.adminconsole.model;

/**
 * Revision History
 * ********************************************************************************************
 * Task : 76249 - Service Car Part Of Paperles 
 * Created By : Jyotish Kumar
 * Date :08/05/2008
 * ********************************************************************************************
 *------------Revision-------------
 *Added to suppliermgmt on 4/19/16 to 
 *handle the uploading of excel docs.
 *
 *@author 600139251 
 */
public class MimeTypeModel {

	private String extn = "";

	private String mimeType = "";

	/**
	 * @return Returns the extn.
	 */
	public String getExtn() {
		return extn;
	}

	/**
	 * @param extn
	 *            The extn to set.
	 */
	public void setExtn(String extn) {
		this.extn = extn;
	}

	/**
	 * @return Returns the mimeType.
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType
	 *            The mimeType to set.
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}