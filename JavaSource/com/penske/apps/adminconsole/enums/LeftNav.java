package com.penske.apps.adminconsole.enums;

import com.penske.apps.suppliermgmt.annotation.SmcSecurity.SecurityFunction;

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

    /** App Config **/
    LOADSHEET_MANAGEMENT(SecurityFunction.LOADSHEET_MANAGEMENT, "admin-console/app-config/loadsheet-management.htm"),
    LOADSHEET_RULES(SecurityFunction.LOADSHEET_RULES, "admin-console/app-config/loadsheet-rule.htm"),
    LOADSHEET_SEQUENCES(SecurityFunction.LOADSHEET_SEQUENCES, "admin-console/app-config/loadsheet-sequence.htm"),
    DYNAMIC_RULES(SecurityFunction.DYNAMIC_RULES_MANAGEMENT, "admin-console/app-config/dynamic-rules.htm"),
    SEARCH_TEMPLATES(SecurityFunction.SEARCH_TEMPLATES, "admin-console/app-config/search-template-management.htm"),
    ALERTS(SecurityFunction.ALERT_MANAGEMENT, "admin-console/app-config/alerts.htm"),
    GLOBAL_EXCEPTIONS(SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT, "admin-console/app-config/global-exceptions.htm"),
    T_AND_C_MANAGEMENT(SecurityFunction.MANAGE_TC, "admin-console/app-config/terms-and-conditions.htm"),
    EXCEL_UPLOADS(SecurityFunction.UPLOAD_EXCEL, "admin-console/app-config/excelUploads.htm"),

	/**OEM Build Matrix**/
    BODY_PLANT_CAPABILITIES(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/bodyplant-capabilities.htm"),
    BUILD_HISTORY(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/build-history.htm"),
	ATTRIBUTE_MAINTENANCE(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/attribute-maintenance.htm");
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
