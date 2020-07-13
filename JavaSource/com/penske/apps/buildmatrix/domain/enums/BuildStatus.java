package com.penske.apps.buildmatrix.domain.enums;

import com.penske.apps.smccore.base.domain.enums.MappedEnum;

public enum BuildStatus implements MappedEnum
{
	STARTED("P", "Started", "The run has been created but not yet submitted for processing"),
	SUBMITTED("S", "Submitted", "Build was submitted and waiting to be picked up by the reservation rules engine"),
	RUNNING("R", "Running", "Reservation engine is working to generate a build plan based on the OEM mix,Plant Rules,Dates,and District proximity"),
	COMPLETED("C", "Completed", "Build has run successfully and all requests have been fulfilled"),
	APPROVED("A", "Approved", "Build has run completed and approved by vehicle supply"),
	FAILED("F", "Failed", "A technical / IT error occured during the build process - We don't anticipate this to be a common outcome but are calling out any possible outcomes on this page"),
	VOID("V", "Void", "A run which had been previously submitted and processed but later had all results removed will render the run as void."),
	;

	/** Code used to represent this status in the database. */
	private final String code;
	/** Human-readable label used in UI for this status */
	private final String label;
	/** Description of the code */
	private final String description;
	
	/**
	 * Finds a status by its database code
	 * @param code The database code
	 * @return The status matching the given database code, or null if no status matches the given code
	 */
	public static BuildStatus findByCode(String code)
	{
		for(BuildStatus status : values())
		{
			if(status.code.equals(code))
				return status;
		}
		return null;
	}
	
	private BuildStatus(String code, String label, String description)
	{
		this.code = code;
		this.label = label;
		this.description = description;
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
	
	public String getDescription()
	{
		return description;
	}
}
