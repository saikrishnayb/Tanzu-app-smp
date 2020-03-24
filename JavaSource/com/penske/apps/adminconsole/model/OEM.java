package com.penske.apps.adminconsole.model;

public class OEM {
private int oemId;
private String poCategory;
private String oemName;
private float value;

public OEM(int oemId, String poCategory, String oemName, float value) {
	super();
	this.oemId = oemId;
	this.poCategory = poCategory;
	this.oemName = oemName;
	this.value = value;
}
//getters
public int getOemId() {
	return oemId;
}
public String getPoCategory() {
	return poCategory;
}
public String getOemName() {
	return oemName;
}
public float getValue() {
	return value;
}
public void setOemId(int oemId) {
	this.oemId = oemId;
}
//setters
public void setPoCategory(String poCategory) {
	this.poCategory = poCategory;
}
public void setOemName(String oemName) {
	this.oemName = oemName;
}
public void setValue(float value) {
	this.value = value;
}


}
