package com.penske.apps.adminconsole.model;

public class TemplatePoAssociation {
	private int poCatAssocID;
	private int poCatID;
	private int poSubCatID;
	private String poCatSubCatDesc;
	public int getPoCatAssocID() {
		return poCatAssocID;
	}
	public void setPoCatAssocID(int poCatAssocID) {
		this.poCatAssocID = poCatAssocID;
	}
	public int getPoCatID() {
		return poCatID;
	}
	public void setPoCatID(int poCatID) {
		this.poCatID = poCatID;
	}
	public int getPoSubCatID() {
		return poSubCatID;
	}
	public void setPoSubCatID(int poSubCatID) {
		this.poSubCatID = poSubCatID;
	}
	public String getPoCatSubCatDesc() {
		return poCatSubCatDesc;
	}
	public void setPoCatSubCatDesc(String poCatSubCatDesc) {
		this.poCatSubCatDesc = poCatSubCatDesc;
	}
}
