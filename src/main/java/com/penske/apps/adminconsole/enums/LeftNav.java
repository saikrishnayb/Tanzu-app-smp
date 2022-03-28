package com.penske.apps.adminconsole.enums;

import com.penske.apps.smccore.base.domain.enums.SecurityFunction;

/**
 * All left navigation link in Supplier Management
 */
public enum LeftNav {

    /** Security **/
    PENSKE_USERS(SecurityFunction.MANAGE_USERS, "admin-console/security/users"),
    VENDOR_USERS(SecurityFunction.MANAGE_VENDOR_USERS, "admin-console/security/vendor-users"),
    MANAGE_ROLES(SecurityFunction.MANAGE_ROLES, "admin-console/security/roles"),
    MANAGE_VENDORS(SecurityFunction.MANAGE_VENDORS, "admin-console/security/vendors"),
    MANAGE_ORG(SecurityFunction.MANAGE_ORG, "admin-console/security/org"),

    /** Components **/
    CATEGORY_ASSOCIATION(SecurityFunction.MANAGE_CATEGORY_ASSOCIATION, "admin-console/components/category-association"),
    CATEGORY_MANAGEMENT(SecurityFunction.MANAGE_CATEGORY, "admin-console/components/category-management"),
    COMPONENT_MANAGEMENT(SecurityFunction.MANAGE_COMPONENTS, "admin-console/components/component-management"),
    TEMPLATE_MANAGEMENT(SecurityFunction.MANAGE_TEMPLATE, "admin-console/components/template"),

    /** App Config **/
    LOADSHEET_MANAGEMENT(SecurityFunction.LOADSHEET_MANAGEMENT, "admin-console/app-config/loadsheet-management"),
    LOADSHEET_RULES(SecurityFunction.LOADSHEET_RULES, "admin-console/app-config/loadsheet-rule"),
    LOADSHEET_SEQUENCES(SecurityFunction.LOADSHEET_SEQUENCES, "admin-console/app-config/loadsheet-sequence"),
    DYNAMIC_RULES(SecurityFunction.DYNAMIC_RULES_MANAGEMENT, "admin-console/app-config/dynamic-rules"),
    SEARCH_TEMPLATES(SecurityFunction.SEARCH_TEMPLATES, "admin-console/app-config/search-template-management"),
    ALERTS(SecurityFunction.ALERT_MANAGEMENT, "admin-console/app-config/alerts"),
    GLOBAL_EXCEPTIONS(SecurityFunction.GLOBAL_EXCEPTIONS_MANAGEMENT, "admin-console/app-config/global-exceptions"),
    T_AND_C_MANAGEMENT(SecurityFunction.MANAGE_TC, "admin-console/app-config/terms-and-conditions"),
    EXCEL_UPLOADS(SecurityFunction.UPLOAD_EXCEL, "admin-console/app-config/excelUploads"),

	/**OEM Build Matrix**/
    BODY_PLANT_CAPABILITIES(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/bodyplant-capabilities"),
    BUILD_HISTORY(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/build-history"),
	ATTRIBUTE_MAINTENANCE(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/attribute-maintenance"),
	OEM_MIX_MAINTENANCE(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/business-award-maint"),
	PRODUCTION_SLOT_MAINTENACE(SecurityFunction.OEM_BUILD_MATRIX, "admin-console/oem-build-matrix/maintenance-summary");
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
