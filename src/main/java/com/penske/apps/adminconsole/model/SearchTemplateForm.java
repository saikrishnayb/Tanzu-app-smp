package com.penske.apps.adminconsole.model;


public class SearchTemplateForm {
	private int templateId;
	private String templateName;
	private int displaySequence;
	private String visibility;
	private int tabId; 
	private String visibilityPenske;
	private String visibilityVendor;
	private String defaultForTab;
	
	//Set and get for template Id
	public void setTemplateId(int newTemplateId) {
		this.templateId = newTemplateId;
	}
	
	public int getTemplateId() {
		return this.templateId;
	}
	
	//Set and get for template name
	public void setTemplateName(String newTemplateName) {
		this.templateName = newTemplateName;
	}
	
	public String getTemplateName() {
		return this.templateName;
	}
	
	//Set and get for display sequence
	public void setDisplaySequence(int newDisplaySequence) {
		this.displaySequence = newDisplaySequence;
	}
	
	public int getDisplaySequence() {
		return this.displaySequence;
	}
	
	//Set and get for visibility
	public void setVisibility(String newVisibility) {
		this.visibility = newVisibility;
		if (this.visibility.equals("BOTH")) {
			this.setVisibilityPenske("Y");
			this.setVisibilityVendor("Y");
		} else if (this.visibility.equals("PENSKE")) {
			this.setVisibilityPenske("Y");
			this.setVisibilityVendor("N");
		} else if(this.visibility.equals("VENDOR")){
			this.setVisibilityPenske("N");
			this.setVisibilityVendor("Y");
		} else{
			this.setVisibilityPenske("N");
			this.setVisibilityVendor("N");
		}
	}
	
	public String getVisibility() {
		return this.visibility;
	}
	
	//Set and get for Tab ID
	public void setTabId(int newTabId) {
		this.tabId = newTabId;
	}
	
	public int getTabId() {
		return this.tabId;
	}
	
	//Set and get for visibility Penske
	public void setVisibilityPenske(String newVisibilityPenske) {
		this.visibilityPenske = newVisibilityPenske;
	}
	
	public String getVisibilityPenske() {
		return this.visibilityPenske;
	}
	
	//Set and get for visibility vendor
	public void setVisibilityVendor(String newVisibilityVendor) {
		this.visibilityVendor = newVisibilityVendor;
	}
	
	public String getVisibilityVendor() {
		return this.visibilityVendor;
	}
	
	//Set and get for default tab id
	public void setDefaultForTab(String newDefaultForTab) {
		this.defaultForTab = newDefaultForTab;
	}
	
	public String getDefaultForTab() {
		return this.defaultForTab;
	}
	
	public String toString() {
		return "[templateId=" + this.templateId + ",templateName=" + this.templateName + ",displaySequence=" + this.displaySequence +
				",visibility=" + this.visibility + ",visibilityPenske=" + this.visibilityPenske +
				",visibilityVendor=" + this.visibilityVendor + ",defaultForTab=" + this.getDefaultForTab() + "]";
	}
}
