/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum representing the different PO Categories that the loadsheet application recognizes.
 * This is <b>not</b> an exhaustive list, since PO Categories are data-driven and user-maintainable.
 * This is just a list of the ones that the Loadsheet application cares about.
 * 
 * Again, having a null value for this enum does not mean the thing in question has no PO Category, just
 * that, if it does have one, it isn't in this list.
 */
public enum PoCategoryType
{
	TRUCK("TRUCK", "C"),
	TRACTOR("TRACTOR", "C"),
	TRAILER("TRAILER", "C"),
	CAR("CAR", "C"),
	DOLLY("DOLLY", "C"),
	REEFER("REEFER", "R"),
	BODY("BODY", "B"),
	DEMOUNT("DEMOUNT", "B"),
	LIFTGATE("LIFTGATE", "L"),
	FIVE_WHEEL("5WHEEL", "5"),
	MISC("MISC", "M"),
	TIRES("TIRES", "X"),
	DECAL("DECAL", null),
	OTHER("OTHER", null),
	NONE("NONE", null),
	CONTAINR("CONTAINR", null);
	
	/**
	 * The PO Categories that should be checked for freight costs.
	 * Loadsheets that don't have one of these categories will not have their freight amount validated.
	 */
	private static final List<PoCategoryType> FREIGHT_PO_CATEGORIES = Arrays.asList(TRUCK, TRACTOR, TRAILER, BODY);
	
	/**
	 * The PO Categories that require a vehicle size.
	 * Loadsheets that don't have one of these categories should not have any size.
	 */
	private static final List<PoCategoryType> SIZE_PO_CATEGORIES = Arrays.asList(TRUCK, TRACTOR, TRAILER, DOLLY, CAR, DEMOUNT, OTHER);

	/**
	 * The PO Categories that require a vehicle type.
	 * Loadsheets that don't have one of these categories should not have any vehicle type.
	 */
	private static final List<PoCategoryType> VEHICLE_TYPE_CATEGORIES = Arrays.asList(TRUCK, TRACTOR, TRAILER, BODY, CAR, DEMOUNT, DOLLY, MISC, OTHER);

	/**
	 * The PO categpries that do not require manufacture information (make / model / model year)
	 */
	private static final List<PoCategoryType> NO_MANUFACTURER_CATEGORIES = Arrays.asList(NONE, DECAL);
	
	/**
	 * Static map to speed lookups by the PO Category's name.
	 */
	private static final Map<String, PoCategoryType> typesByCategoryName = new HashMap<String, PoCategoryType>();
	/**
	 * The name of the PO Category, as stored in the database.
	 * We need a separate name because some of the names are not valid Java identifiers.
	 */
	private final String poCategoryName;
	/**
	 * The one-letter code that will be used in the manufacturer table if a vendor is able to produce specs of this PO Category.
	 * This is also the one-letter suffix of the the field name itself. For example, a manufacturer that can produce Chassis, will have
	 * a "C" in the field "MFTYPC".
	 */
	private final String mfrFieldCode;
	
	static {
		//Initialize the lookup map at class load time
		for(PoCategoryType type : values())
			typesByCategoryName.put(type.getPoCategoryName(), type);
	}
	
	/**
	 * Looks up a PoCategoryType by its database name. <b>This method can return null.</b>
	 * @param poCategoryName The database name of the PoCategoryType to lookup.
	 * @return The PoCategoryType that matches the given name.
	 * 	{@code null} will be returned if the given name is null, or if no enum constant matches the given name.
	 * 	NOTE: just because null is returned does not mean there is no such PoCategory or that the thing in question has no PoCategory.
	 * 	It just means the Loadsheet application doesn't have logic pertaining to such a PO Category. 
	 */
	public static PoCategoryType findTypeByName(String poCategoryName)
	{
		if(poCategoryName == null)
			return null;
		return typesByCategoryName.get(poCategoryName);
	}
	
	private PoCategoryType(String poCategoryName, String mfrFieldCode)
	{
		this.poCategoryName = poCategoryName;
		this.mfrFieldCode = mfrFieldCode;
	}

	//***** MODIFIED ACCESSORS *****//
	/**
	 * Checks if a loadsheet with the given PO Category requires a non-zero freight cost or not.
	 * @param categoryType The PO Category Type to check
	 * @return True if loadsheets with the PO Category require freight; false otherwise.
	 */
	public static boolean isFreightRequired(PoCategoryType categoryType)
	{
		return PoCategoryType.FREIGHT_PO_CATEGORIES.contains(categoryType);
	}
	
	/**
	 * Checks if a loadsheet with the given PO Category requires a vehicle size or not.
	 * @param categoryType The PO Category Type to check
	 * @return True if loadsheets with the PO Category require vehicle size; false otherwise.
	 */
	public static boolean isSizeRequired(PoCategoryType categoryType)
	{
		return PoCategoryType.SIZE_PO_CATEGORIES.contains(categoryType);
	}
	
	/**
	 * Checks if a loadsheet with the given PO Category requires a vehicle type or not.
	 * @param categoryType The PO Category Type to check
	 * @return True if loadsheets with thePO Category require vehicle type; false otherwise.
	 */
	public static boolean isVehicleTypeRequired(PoCategoryType categoryType)
	{
		return PoCategoryType.VEHICLE_TYPE_CATEGORIES.contains(categoryType);
	}

	/**
     * Checks if a spec with the given PO Category requires manufacture infor or not.
     * @param categoryType The PO Category Type to check
     * @return True if loadsheets with the PO Category require purchase agreements; false otherwise.
     */
	public static boolean isManufacturerInfoRequired(PoCategoryType categoryType)
	{
	    return !PoCategoryType.NO_MANUFACTURER_CATEGORIES.contains(categoryType);
	}
	
	//***** DEFAULT ACCESSORS *****//
	/**
	 * @return the poCategoryName
	 */
	public String getPoCategoryName()
	{
		return poCategoryName;
	}

	/**
	 * @return the mfrFieldCode
	 */
	public String getMfrFieldCode()
	{
		return mfrFieldCode;
	}
}
