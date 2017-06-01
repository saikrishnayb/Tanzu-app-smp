package com.penske.apps.adminconsole.model;

import java.io.Serializable;

/**
 * 
 * @author 502403391
 * @Description : LoadSheetCategoryDetails object is used to store in session for Back button in create rule page
 * 
 *
 */

public class LoadSheetCategoryDetails implements Serializable  {
	

	private static final long serialVersionUID = -8706020110066527988L;
	private String categoryId;
	private String category;
	private String type;
	private String viewMode;
	private int componentId;
	private int visibilityId;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getViewMode() {
		return viewMode;
	}
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public int getVisibilityId() {
		return visibilityId;
	}
	public void setVisibilityId(int visibilityId) {
		this.visibilityId = visibilityId;
	}

}
