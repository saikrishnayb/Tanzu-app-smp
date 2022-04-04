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
	private String componentName; 	// component name
	private String poGroup;			// PO Group
	private String isNew;
	private Date modifiedDate;
	
	private List<Vendor> providerVendors;
	private List<GlobalExceptionCategoryGroup> poCategoryGroups;
    
    public int getExceptionId() {
        return exceptionId;
    }
    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
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
	    if(providerVendors == null || providerVendors.isEmpty())
	    	return null;
	    
	    return providerVendors.get(0);
    }
    public List<GlobalExceptionCategoryGroup> getPoCategoryGroups() {
        return poCategoryGroups;
    }
    public void setPoCategoryGroups(List<GlobalExceptionCategoryGroup> poCategoryGroups) {
        this.poCategoryGroups = poCategoryGroups;
    }
    
    public String getModifiedDate() {
		return DateFormatUtils.format(modifiedDate, "MM/dd/yyyy");
	}
    
	public void setModifiedDate(Date createdDate) {
		this.modifiedDate = createdDate;
	}

	
}
