/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.model;

import java.io.Serializable;

/**
 * Class to hold session parameters used by the pages on the App Config tab.
 * Ideally, it would be better to re-write the pages from that tab so they didn't have to use a lot of session data.
 * When that re-writing happens, this class should be removed.
 * Please do not add further to this class, but instead find other, non-session-based ways of structuring pages
 */
public class AppConfigSessionData implements Serializable
{
	private static final long serialVersionUID = 7881830238991934100L;
	
	//Create / Edit Rule Page
	private Integer ruleId;
	private String editRuleRequestedFrom;
	
	//Get Loadsheet Components Page
	private String loadsheetComponentsRequestedFrom;
	private LoadSheetCategoryDetails categoryDetails;
	
	//Create / Edit Loadsheet Sequence Page
	private String sequenceCategory;
	private String sequenceType;
	private String sequenceViewMode;
	
	//***** HELPER CLASSES *****//
	/**
	 * LoadSheetCategoryDetails object is used to store in session for Back button in create rule page
	 */
	public static class LoadSheetCategoryDetails implements Serializable  {

		private static final long serialVersionUID = -8706020110066527988L;
		private Integer categoryId;
		private String category;
		private String type;
		private String viewMode;
		private Integer componentId;
		private Integer visibilityId;
		
		public LoadSheetCategoryDetails(Integer categoryId, String category, String type, String viewMode)
		{
			this.categoryId = categoryId;
			this.category = category;
			this.type = type;
			this.viewMode = viewMode;
		}
		
		public void updateComponentVisibleId(Integer componentId, Integer visibilityId)
		{
			this.componentId = componentId;
			this.visibilityId = visibilityId;
		}
		
		//***** DEFAULT ACCESSORS *****//
		public Integer getCategoryId()
		{
			return categoryId;
		}

		public String getCategory()
		{
			return category;
		}

		public String getType()
		{
			return type;
		}

		public String getViewMode()
		{
			return viewMode;
		}

		public Integer getComponentId()
		{
			return componentId;
		}

		public Integer getVisibilityId()
		{
			return visibilityId;
		}
	}
	
	//***** DEFAULT ACCESSORS *****//
	public Integer getRuleId()
	{
		return ruleId;
	}
	public void setRuleId(Integer ruleId)
	{
		this.ruleId = ruleId;
	}
	public String getEditRuleRequestedFrom()
	{
		return editRuleRequestedFrom;
	}
	public void setEditRuleRequestedFrom(String editRuleRequestedFrom)
	{
		this.editRuleRequestedFrom = editRuleRequestedFrom;
	}
	public String getLoadsheetComponentsRequestedFrom()
	{
		return loadsheetComponentsRequestedFrom;
	}
	public void setLoadsheetComponentsRequestedFrom(String loadsheetComponentsRequestedFrom)
	{
		this.loadsheetComponentsRequestedFrom = loadsheetComponentsRequestedFrom;
	}
	public LoadSheetCategoryDetails getCategoryDetails()
	{
		return categoryDetails;
	}
	public void setCategoryDetails(LoadSheetCategoryDetails categoryDetails)
	{
		this.categoryDetails = categoryDetails;
	}
	public String getSequenceCategory()
	{
		return sequenceCategory;
	}
	public void setSequenceCategory(String sequenceCategory)
	{
		this.sequenceCategory = sequenceCategory;
	}
	public String getSequenceType()
	{
		return sequenceType;
	}
	public void setSequenceType(String sequenceType)
	{
		this.sequenceType = sequenceType;
	}
	public String getSequenceViewMode()
	{
		return sequenceViewMode;
	}
	public void setSequenceViewMode(String sequenceViewMode)
	{
		this.sequenceViewMode = sequenceViewMode;
	}
}