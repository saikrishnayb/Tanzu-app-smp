package com.penske.apps.adminconsole.model;

import java.util.Date;

/**
 * 
 * @author 600144005
 * This class represents all PoCategories in smc
 */
public class PoCategory {
	private int categoryId;
	private String categoryName;

	private String description;
	private String status;
	private String createdBy;
	private Date createdDate;


	// Getters

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	// Setters
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
		return "PoCategory [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", description=" + description + ", status="
				+ status + "]";
	}

	

}
