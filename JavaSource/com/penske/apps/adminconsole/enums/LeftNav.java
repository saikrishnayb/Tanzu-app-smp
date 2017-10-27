package com.penske.apps.adminconsole.enums;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;

/**
 * All left navigation link in Supplier Management
 */
public enum LeftNav {

    /** Security **/
    PENSKE_USERS(SecurityFunction.MANAGE_USERS, "admin-console/security/users.htm"),
    VENDOR_USERS(SecurityFunction.MANAGE_VENDOR_USERS, "admin-console/security/vendorUsers.htm"),
    MANAGE_ROLES(SecurityFunction.MANAGE_ROLES, "admin-console/security/roles.htm"),
    MANAGE_VENDORS(SecurityFunction.MANAGE_VENDORS, "admin-console/security/vendors.htm"),
    MANAGE_ORG(SecurityFunction.MANAGE_ORG, "admin-console/security/org.htm"),

    /** Components **/
    CATEGORY_ASSOCIATION(SecurityFunction.MANAGE_CATEGORY_ASSOCIATION, "admin-console/components/category-association.htm"),
    CATEGORY_MANAGEMENT(SecurityFunction.MANAGE_CATEGORY, "admin-console/components/category-management.htm"),
    COMPONENT_MANAGEMENT(SecurityFunction.MANAGE_COMPONENTS, "admin-console/components/component-management.htm"),
    TEMPLATE_MANAGEMENT(SecurityFunction.MANAGE_TEMPLATE, "admin-console/components/template.htm"),
    EXCEL_SEQUENCE(SecurityFunction.MANAGE_TEMPLATE, "admin-console/components/load-excel-seq-templates.htm"),
    COMPONENT_VISIBILITY_OVERRIDE(SecurityFunction.MANAGE_COMPONENT_OVERRIDE, "admin-console/components/component-Visibility-Override.htm"),

    /** App Config **/
    SUBJECT_MANAGEMENT(SecurityFunction.MANAGE_SUBJECTS, "admin-console/app-config/subject-management.htm"),
    LOADSHEET_MANAGEMENT(null, "admin-console/app-config/loadsheet-management.htm"),
    LOADSHEET_RULES(null, "admin-console/app-config/loadsheet-rule.htm"),
    LOADSHEET_SEQUENCES(null, "admin-console/app-config/loadsheet-sequence.htm"),
    DYNAMIC_RULES(SecurityFunction.DYNAMIC_RULES_MANAGEMENT, "admin-console/app-config/dynamic-rules.htm"),
    SEARCH_TEMPLATES(SecurityFunction.SEARCH_TEMPLATES, "admin-console/app-config/search-template-management.htm"),
    ALERTS(SecurityFunction.ALERT_MANAGEMENT, "admin-console/app-config/alerts.htm"),
    GLOBAL_EXCEPTIONS(SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT, "admin-console/app-config/global-exceptions.htm"),
    DELAY_REASON_CODES(SecurityFunction.MANAGE_DELAY_REASONS, "admin-console/app-config/delay-reason-codes.htm"),
    T_AND_C_MANAGEMENT(SecurityFunction.MANAGE_TC, "admin-console/app-config/terms-and-conditions.htm"),
    EXCEL_UPLOADS(SecurityFunction.UPLOAD_EXCEL, "admin-console/app-config/excelUploads.htm");

    private final SecurityFunction securityFunction;
    private final String urlEntry;

    private LeftNav(SecurityFunction securityFunction, String urlEntry) {
        this.securityFunction = securityFunction;
        this.urlEntry = urlEntry;
    }

    public SecurityFunction getSecurityFunction() {
        return securityFunction;
    }

    public String getUrlEntry() {
        return urlEntry;
    }

}