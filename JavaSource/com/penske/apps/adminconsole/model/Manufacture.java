package com.penske.apps.adminconsole.model;

import java.util.List;
/**
 * 
 * @author 600144005
 * Ths class represents all manufacturers that donot have a template
 */
public class Manufacture {
	
	private String manufacture;
	
	private List<CorpCode> corpCodes;
	
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public List<CorpCode> getCorpCodes() {
		return corpCodes;
	}
	public void setCorpCodes(List<CorpCode> corpCodes) {
		this.corpCodes = corpCodes;
	}
	@Override
	public String toString() {
		return "Manufacture [manufacture=" + manufacture + ", corpCodes="
				+ corpCodes + "]";
	}
}
