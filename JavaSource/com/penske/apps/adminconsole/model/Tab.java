package com.penske.apps.adminconsole.model;

import java.util.List;

/**
 * @author 600143568
 */
public class Tab {
    private int tabId;						  // the tab ID
    private String tabName;					  // the tab name
    private String imageUrl;				  // the tab image URL
    private List<AlertHeader> alertHeaders;	  // the list of alert headers for the tab
    private List<Permission> permissions;
    private int defaultTemplateId;
    private String tabKey;

    // Getters
    public int getTabId() {
        return tabId;
    }

    public String getTabName() {
        return tabName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<AlertHeader> getAlertHeaders() {
        return alertHeaders;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public int getDefaultTemplateId() {
        return this.defaultTemplateId;
    }

    public int getPermissionsCount() {
        return permissions == null? 0 : permissions.size();
    }

    // Setters
    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAlertHeaders(List<AlertHeader> alertHeaders) {
        this.alertHeaders = alertHeaders;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setDefaultTemplateId(int defaultTemplateId) {
        this.defaultTemplateId = defaultTemplateId;
    }

    // Overridden Methods
    @Override
    public String toString() {
        return "Tab [tabId=" + tabId + ", tabName=" + tabName
                + ", imageUrl=" + imageUrl + ", alertHeaders=" + alertHeaders
                + "]";
    }

    public String getTabKey() {
        return tabKey;
    }

    public void setTabKey(String tabKey) {
        this.tabKey = tabKey;
    }
}
