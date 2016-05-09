package com.penske.apps.adminconsole.model;

/**
 * This is a basic supporting model object that contains the PO information for
 * the Delay Management page. It's used for populating select dropdowns with PO
 * options.
 * 
 * @author 600132441 M.Leis
 * 
 */
public class DelayPoModel {

	private int categoryId; 		// category ID
	private String categoryName; 	// category name

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
}
