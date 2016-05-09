package com.penske.apps.adminconsole.model;

import java.util.Date;

/**
 * 
 * @author 600144005
 * This class represents all subCategories in SMC
 */
public class SubCategory {
	private int subCategoryId;
	private String subCategoryName;
	private String description;
	private String status;
	private String createdBy;
	private Date createdDate;
	
	// Getters
	public int getSubCategoryId() {
		return subCategoryId;
	}
	
	public String getSubCategoryName() {
		return subCategoryName;
	}
	
	// Setters
	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "SubCategory [subCategoryId=" + subCategoryId
				+ ", subCategoryName=" + subCategoryName + ", description="
				+ description + ", status=" + status + "]";
	}
	

}
