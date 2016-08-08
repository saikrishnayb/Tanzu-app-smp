package com.penske.apps.adminconsole.model;

/**
 * This is the basic model object for a Global Exception, which is
 * displayed in the datatable on the Global Exceptions page.
 * @author 600132441 M.Leis
 *
 */
public class GlobalException {

	private int exceptionId;		// exception ID
	private int dataId;				// data ID
	private String dataType;		// data type
	private String componentName; 	// component name
	private String providerPo;		// provider PO category
	private String providerPoSub;	// provider PO Sub-category
	private int createdById;		// ID of user/group that created the exception
	private String createdByName;	// name of user/group that created the exception
	private String poGroup;			// PO Group
	private String isNew;		
	
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
	
	public String getProviderPo() {
		return providerPo;
	}

	public String getProviderPoSub() {
		return providerPoSub;
	}

	public int getCreatedById() {
		return createdById;
	}

	public String getCreatedByName() {
		return createdByName;
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

	public void setProviderPo(String providerPo) {
		this.providerPo = providerPo;
	}

	public void setProviderPoSub(String providerPoSub) {
		this.providerPoSub = providerPoSub;
	}

	public void setCreatedById(int createdById) {
		this.createdById = createdById;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public void setPoGroup(String poGroup) {
		this.poGroup = poGroup;
	}

	@Override
	public String toString() {
		return "GlobalException [exceptionId=" + exceptionId + ", dataId="
				+ dataId + ", dataType=" + dataType + ", componentName="
				+ componentName + ", providerPo=" + providerPo
				+ ", providerPoSub=" + providerPoSub + ", createdById="
				+ createdById + ", createdByName=" + createdByName
				+ ", poGroup=" + poGroup + "]";
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
}
