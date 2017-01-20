package com.penske.apps.suppliermgmt.model;

import org.apache.commons.lang3.StringEscapeUtils;

import com.penske.apps.suppliermgmt.domain.AlertCount;

/**
 * The Alert object is displayed on the dashboard screen below its respective
 * Alert Header. This categorizes the users alerts into categories that the user
 * can click through to view their workloads.
 * 
 * @author Seth.Bauman 600143568
 */
public class Alert {
	private int alertId; 				// the alert ID
	private String alertType; 			// the alert type
	private String alertName; 			// the alert name
	private int displaySequence;		// the display sequence
	private String helpText; 			// the alert help text
	private String tabKey;					// the tab ID
	private String tabName;				// the tab name
	private int headerId;				// the alert header ID
	private String headerName;			// the alert header name
	//private int templateId;				// the associated template ID
	private String templateKey;				// the associated template Key
	private String templateName;		// the associated template name
	private int visibility;				// the visibility from the edit form
	private int visibilityPenske;		// the visibility for Penske users
	private int visibilityVendor;		// the visibility for Vendor users
	private int actionable; 			// whether the alert is actionable or not
	private String link;				// the link for the user when the alert is clicked on
	private int count;
	private String flag;
	private String alertKey;
	private String complianceText;
	
	//Modified Accessors
		public void updateAlertCount(AlertCount alertCount) {
		    
		    if(alertCount == null)
		        throw new IllegalArgumentException("AlertCount must not be null");
		    
		    this.count = alertCount.getAlertCount();
		    this.flag = alertCount.getNonCompliant();
		}

	// Getters
	public int getAlertId() {
		return alertId;
	}

	public String getAlertType() {
		return alertType;
	}
	public String getTabName() {
		return tabName;
	}

	public int getDisplaySequence() {
		return displaySequence;
	}
	
	public int getHeaderId() {
		return headerId;
	}

	public String getHeaderName() {
		return headerName;
	}

	public String getAlertName() {
		return alertName;
	}

	public int getVisibility() {
		return visibility;
	}
	
	public int getVisibilityPenske() {
		return visibilityPenske;
	}

	public int getVisibilityVendor() {
		return visibilityVendor;
	}

//	public int getTemplateId() {
//		return templateId;
//	}

	public String getTemplateName() {
		return templateName;
	}

	public int getActionable() {
		return actionable;
	}

	public String getHelpText() {
		return helpText;
	}
	
	public String getLink() {
		return link;
	}

	
	// Setters
	public void setAlertId(int alertId) {
		this.alertId = alertId;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public void setDisplaySequence(int displaySequence) {
		this.displaySequence = displaySequence;
	}
	
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}
	
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	
	public void setVisibilityVendor(int visibilityVendor) {
		this.visibilityVendor = visibilityVendor;
	}
	
	public void setVisibilityPenske(int visibilityPenske) {
		this.visibilityPenske = visibilityPenske;
	}

//	public void setTemplateId(int templateId) {
//		this.templateId = templateId;
//	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setActionable(int actionable) {
		this.actionable = actionable;
	}

	public void setHelpText(String helpText) {
		// Escape this user supplied text to prevent it from breaking home page HTML
		this.helpText = StringEscapeUtils.escapeHtml4(helpText);
	}
	
	public void setLink(String link) {
		this.link = link;
	}
		
	public String getTabKey() {
		return tabKey;
	}

	public void setTabKey(String tabKey) {
		this.tabKey = tabKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the alertKey
	 */
	public String getAlertKey() {
		return alertKey;
	}

	/**
	 * @param alertKey the alertKey to set
	 */
	public void setAlertKey(String alertKey) {
		this.alertKey = alertKey;
	}

	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public String getComplianceText() {
		return complianceText;
	}
}