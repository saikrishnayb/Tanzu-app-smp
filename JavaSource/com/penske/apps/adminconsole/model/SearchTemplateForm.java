package com.penske.apps.adminconsole.model;

public class SearchTemplateForm {
	private int templateId;
	private String templateName;
	private int displaySequence;
	private String visibility;
	private int tabId; 
	private int visibilityPenske;
	private int visibilityVendor;
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
			this.setVisibilityPenske(1);
			this.setVisibilityVendor(1);
		} else if (this.visibility.equals("PENSKE")) {
			this.setVisibilityPenske(1);
			this.setVisibilityVendor(0);
		} else {
			this.setVisibilityPenske(0);
			this.setVisibilityVendor(1);
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
	public void setVisibilityPenske(int newVisibilityPenske) {
		this.visibilityPenske = newVisibilityPenske;
	}
	
	public int getVisibilityPenske() {
		return this.visibilityPenske;
	}
	
	//Set and get for visibility vendor
	public void setVisibilityVendor(int newVisibilityVendor) {
		this.visibilityVendor = newVisibilityVendor;
	}
	
	public int getVisibilityVendor() {
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
