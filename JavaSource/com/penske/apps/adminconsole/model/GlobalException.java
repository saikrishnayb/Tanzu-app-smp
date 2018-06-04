package com.penske.apps.adminconsole.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * This is the basic model object for a Global Exception, which is
 * displayed in the datatable on the Global Exceptions page.
 * @author 600132441 M.Leis
 *
 */
public class GlobalException {

	private int exceptionId;		// exception ID
	private String dataType;		// data type
	private String componentName; 	// component name
	private String poGroup;			// PO Group
	private String isNew;
	private Date createdDate;
	
	private Vendor providerVendor;
	private List<GlobalExceptionCategoryGroup> poCategoryGroups;
    
    public int getExceptionId() {
        return exceptionId;
    }
    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
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
    
    public String getCreatedDate() {
		return DateFormatUtils.format(createdDate, "MM/dd/yyyy");
	}
    
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
