package com.penske.apps.adminconsole.model;

import java.util.Date;

import com.penske.apps.smccore.base.util.DateUtil;

/**
 * This is a model object for a Vendor purchasing summary info, which is
 * displayed on the Purchasing Details modal page.
 * 
 * @author Maxim.Timofeev
 */
public class PurchasingSummary {
	private int poCount;
	private Date lastPoDate;

	/** Protected constructor - MyBatis only */
	protected PurchasingSummary() {
	}

	@Override
	public String toString() {
		return "PurchasingSummary [poCount=" + poCount + ", lastPoDate=" + lastPoDate + "]";
	}

	public String getFormattedLastPoDate() {
		return poCount == 0 ? "(Never)" : DateUtil.formatDateUS(lastPoDate);
	}

	public int getPoCount() {
		return poCount;
	}

	public Date getLastPoDate() {
		return lastPoDate;
	}
}
