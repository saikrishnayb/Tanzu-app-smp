/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import static com.penske.apps.smccore.CoreTestUtil.newInstance;
import static com.penske.apps.smccore.CoreTestUtil.set;

import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.suppliermgmt.model.LookUp;

/**
 * A class containing utility methods for testing, including methods for creating basic
 * 	versions of some of the domain objects in the application.
 * NOTE: this class should only be used for jUnit tests, not for actual business logic.
 */
public final class TestUtil
{
	/** Private constructor - can't instantiate this class. Utility methods only. */
	private TestUtil() {}
	
	//***** DOMAIN OBJECT CREATION METHODS *****//
	public static LookUp createLookup(String name, String value)
	{
		LookUp result = newInstance(LookUp.class);
		set(result, "lookUpName", name);
		set(result, "lookUpValue", value);
		return result;
	}
	
	public static PoCategory createPoCategory(String categoryName, boolean active)
	{
		PoCategory result = newInstance(PoCategory.class);
		set(result, "categoryName", categoryName);
		set(result, "status", active ? "A" : "I");
		set(result, "createdBy", "TESTUSER");
		return result;
	}
	
	public static SubCategory createPoSubCategory(String subcategoryName, boolean active)
	{
		SubCategory result = newInstance(SubCategory.class);
		set(result, "subCategoryName", subcategoryName);
		set(result, "status", active ? "A" : "I");
		set(result, "createdBy", "TESTUSER");
		return result;
	}
}
