/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt;

import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;

/**
 * Contains generic dummy test data for various unit tests.
 * To use it, instantiate this class in your unit test class and refer to it. Since the variables are not static, they get re-initialized every test.
 */
public class TestData
{
	public final PoCategory categoryTruck = TestUtil.createPoCategory("TRUCK", true);
	public final PoCategory categoryBody = TestUtil.createPoCategory("BODY", true);
	public final SubCategory subcategoryNone = TestUtil.createPoSubCategory("NONE", true);
	public final SubCategory subcategoryLightDuty = TestUtil.createPoSubCategory("LIGHTDUTY", false);
}
