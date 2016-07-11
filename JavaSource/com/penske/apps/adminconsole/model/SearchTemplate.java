package com.penske.apps.adminconsole.model;

public class SearchTemplate {
	private int templateId;				// the template ID
	private int  templateKey;
	private String templateName;		// the template name
	private int displaySequence;
	private String visibilityVendor;
	private String visibilityPenske;
	private String tabName;
	private int tabId;
	private int defaultForTab;
	
	//Set and get for template id
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
	
	
	
	public String getVisibilityVendor() {
		return visibilityVendor;
	}

	public void setVisibilityVendor(String visibilityVendor) {
		this.visibilityVendor = visibilityVendor;
	}

	public String getVisibilityPenske() {
		return visibilityPenske;
	}

	public void setVisibilityPenske(String visibilityPenske) {
		this.visibilityPenske = visibilityPenske;
	}

	//Set and get for tab
	public void setTabName(String newTabName) {
		this.tabName = newTabName;
	}
	
	public String getTabName() {
		return this.tabName;
	}
	
	//Set and get for tab Id
	public void setTabId(int newTabId) {
		this.tabId = newTabId;
	}
	
	public int getTabId() {
		return this.tabId;
	}
	
	//Set and get for default for tab
	public void setDefaultForTab(int newDefaultForTab) {
		this.defaultForTab = newDefaultForTab;
	}
	
	public int getDefaultForTab() {
		return this.defaultForTab;
	}
	
	public String toString() {
		return "[templateId=" + this.templateId + ";templateName=" + this.templateName + ";Tab=" + this.tabName + "]";
	}

	public int getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(int templateKey) {
		this.templateKey = templateKey;
	}
}
