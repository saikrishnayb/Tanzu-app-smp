/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.penske.apps.adminconsole.enums.PoCategoryType;

/**
 * A supplier of equipment, or an installer for such equipment, which Penske deals with during the ordering process.
 * 
 * This object has no setters and no public constructor, since it is treated by the Loadsheet application as read-only.
 */
public class Manufacturer
{
	/** The manufacturer code for Freightliner. There is some logic that is specific just to Freightliner. */
	public static final String FREIGHTLINER_CODE = "FTL";
	/** The manufacturer code for International. There is some logic that is specific just to International. */
	public static final String INTERNATIONAL_CODE = "IHC";
	
	/**
	 * The 3-character code for this manufacturer.
	 */
	private String mfrCode;
	/**
	 * The full human-readable name of this manufacturer.
	 */
	private String mfrName;
	/**
	 * A list of the PO Category Types (see {@link PoCategoryType}) that this manufacturer can be used to produce.
	 */
	private List<PoCategoryType> poCategoryTypes = new ArrayList<PoCategoryType>();
	
	/** Null constructor - MyBatis only */
	protected Manufacturer() {}

	//***** MODIFIED ACCESSORS *****//
	/**
	 * @return the poCategories
	 */
	public List<PoCategoryType> getPoCategoryTypes()
	{
		return Collections.unmodifiableList(poCategoryTypes);
	}
	
	//***** DEFAULT ACCESSORS *****//
	/**
	 * @return the mfrCode
	 */
	public String getMfrCode()
	{
		return mfrCode;
	}

	/**
	 * @return the mfrName
	 */
	public String getMfrName()
	{
		return mfrName;
	}

}
