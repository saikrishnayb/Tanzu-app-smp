package com.penske.apps.adminconsole.model;

import java.util.List;

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
	
	private Vendor providerVendor;
	private List<GlobalExceptionCategoryGroup> poCategoryGroups;
    
    public int getExceptionId() {
        return exceptionId;
    }
    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }
    public int getDataId() {
        return dataId;
    }
    public void setDataId(int dataId) {
        this.dataId = dataId;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getComponentName() {
        return componentName;
    }
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    public String getProviderPo() {
        return providerPo;
    }
    public void setProviderPo(String providerPo) {
        this.providerPo = providerPo;
    }
    public String getProviderPoSub() {
        return providerPoSub;
    }
    public void setProviderPoSub(String providerPoSub) {
        this.providerPoSub = providerPoSub;
    }
    public int getCreatedById() {
        return createdById;
    }
    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }
    public String getCreatedByName() {
        return createdByName;
    }
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    public String getPoGroup() {
        return poGroup;
    }
    public void setPoGroup(String poGroup) {
        this.poGroup = poGroup;
    }
    public String getIsNew() {
        return isNew;
    }
    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
    public Vendor getProviderVendor() {
        return providerVendor;
    }
    public void setProviderVendor(Vendor providerVendor) {
        this.providerVendor = providerVendor;
    }
    public List<GlobalExceptionCategoryGroup> getPoCategoryGroups() {
        return poCategoryGroups;
    }
    public void setPoCategoryGroups(List<GlobalExceptionCategoryGroup> poCategoryGroups) {
        this.poCategoryGroups = poCategoryGroups;
    } 
	
}
