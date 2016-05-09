package com.penske.apps.adminconsole.model;

public class UnitException {

	private int exceptionId;		// ID of the Unit Exception
	private int dataId; 			// reference ID to get the componentName
	private String dataType; 		// table name to send dataId reference to
	private String componentName; 	// the component name that will be fetched with dataId and dataType
	private String unitNumber; 		// unit number of the vehicle with this exception
	private String providerPo; 		// to be provided by PO
	private String providerSubPo; 	// to be provided by PO Sub-Category
	private int creatorId; 			// Id of the creator from the Unit exceptions table
	private String createdBy; 		// name of the creator of this exception
	private String poGroup; 		// the full poGroup for this exception

	// Getters
	public int getExceptionId() {
		return exceptionId;
	}

	public int getDataId() {
		return dataId;
	}

	public String getDataType() {
		return dataType;
	}

	public String getComponentName() {
		return componentName;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getProviderPo() {
		return providerPo;
	}

	public String getProviderSubPo() {
		return providerSubPo;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getPoGroup() {
		return poGroup;
	}

	// Setters
	public void setExceptionId(int exceptionId) {
		this.exceptionId = exceptionId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public void setProviderPo(String providerPo) {
		this.providerPo = providerPo;
	}

	public void setProviderSubPo(String providerSubPo) {
		this.providerSubPo = providerSubPo;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setPoGroup(String poGroup) {
		this.poGroup = poGroup;
	}
}
