package com.penske.apps.adminconsole.model;

public class CategoryAssociation {

public int associationId;
public int poCategoryId;
public int subCategoryId;
public String poCategoryName;
public String subCategoryName;
public String poCatStatus;
public String subCatStatus;
public String assocStatus;

public int getAssociationId() {
	return associationId;
}
public void setAssociationId(int associationId) {
	this.associationId = associationId;
}
public int getPoCategoryId() {
	return poCategoryId;
}
public void setPoCategoryId(int poCategoryId) {
	this.poCategoryId = poCategoryId;
}
public int getSubCategoryId() {
	return subCategoryId;
}
public void setSubCategoryId(int subCategoryId) {
	this.subCategoryId = subCategoryId;
}
public String getPoCategoryName() {
	return poCategoryName;
}
public void setPoCategoryName(String poCategoryName) {
	this.poCategoryName = poCategoryName;
}
public String getSubCategoryName() {
	return subCategoryName;
}
public void setSubCategoryName(String subCategoryName) {
	this.subCategoryName = subCategoryName;
}
@Override
public String toString() {
	return "CategoryAssociation [associationId=" + associationId
			+ ", poCategoryId=" + poCategoryId + ", subCategoryId="
			+ subCategoryId + ", poCategoryName=" + poCategoryName
			+ ", subCategoryName=" + subCategoryName + "]";
}
public String getPoCatStatus() {
	return poCatStatus;
}
public void setPoCatStatus(String poCatStatus) {
	this.poCatStatus = poCatStatus;
}
public String getSubCatStatus() {
	return subCatStatus;
}
public void setSubCatStatus(String subCatStatus) {
	this.subCatStatus = subCatStatus;
}
public String getAssocStatus() {
	return assocStatus;
}
public void setAssocStatus(String assocStatus) {
	this.assocStatus = assocStatus;
}


}
