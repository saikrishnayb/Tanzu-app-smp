/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.buildmatrix.domain.enums;

import com.penske.apps.smccore.base.domain.enums.MappedEnum;

/**
 * Indicates where an order is in the approval process, once it has been submitted.
 */
public enum ApprovalStatus implements MappedEnum
{
	/** The order has not yet been submitted for approval, and is still being worked on. */
	WORKING("W", "Working"),
	/** The order is waiting approval from a regional approver (this is only used for user-submitted orders, not approver-submitted ones). */
	PENDING("P", "Pending"),
	/** The order has been approved by an approver. It may or may not have been actually purchased by Vehicle Supply yet. */
	APPROVED("A", "Approved"),
	/** The order has been rejected by either an approver or Vehicle Supply. No further work can be done on it. */
	REJECTED("R", "Rejected"),
	/** The order has been deleted and doesn't show up in most searches anymore. No further work can be done on it. */
	DELETED("D", "Deleted"),
	;
	
	/** Code used to represent this status in the database. */
	private final String code;
	/** Human-readable label used in UI for this status */
	private final String label;

	/**
	 * Finds a status by its database code
	 * @param code The database code
	 * @return The status matching the given database code, or null if no status matches the given code
	 */
	public static ApprovalStatus findByCode(String code)
	{
		for(ApprovalStatus status : values())
		{
			if(status.code.equals(code))
				return status;
		}
		return null;
	}
	
	private ApprovalStatus(String code, String label)
	{
		this.code = code;
		this.label = label;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getMappedValue()
	{
		return code;
	}

	//***** DEFAULT ACCESSORS *****//
	public String getCode()
	{
		return code;
	}

	public String getLabel()
	{
		return label;
	}
}
