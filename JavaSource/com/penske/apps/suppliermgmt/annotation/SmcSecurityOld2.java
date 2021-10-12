package com.penske.apps.suppliermgmt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used on any method that requires SMC security checks. This is to be intercepted
 * by a spring intercepter and validated on a request basis.
 * 
 * @author erik.munoz 600139451
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SmcSecurityOld2 {

    public SecurityFunctionOld2[] securityFunction();

    /**
     * Enum containing all the security functions pertaining to the SMCOP module. This is not an
     * exhaustive list.
     */
    public static enum SecurityFunctionOld2 {
        ADMIN_CONSOLE_TAB,

        MANAGE_USERS,
        MANAGE_VENDOR_USERS,
        MANAGE_ROLES,
        MANAGE_VENDORS,
        MANAGE_ORG,

        MANAGE_CATEGORY_ASSOCIATION,
        MANAGE_CATEGORY,
        MANAGE_COMPONENTS,
        MANAGE_TEMPLATE,

        DYNAMIC_RULES_MANAGEMENT,
        SEARCH_TEMPLATES,
        ALERT_MANAGEMENT,
        GLOBAL_EXCEPTIONS_MANAGEMENT,
        MANAGE_TC,
        UPLOAD_EXCEL,
        VENDOR_FILTER,
        LOADSHEET_MANAGEMENT, 
        LOADSHEET_RULES, 
        LOADSHEET_SEQUENCES,
        COST_SHEET_ADJUSTMENT_OPTIONS,
        COST_SHEET_TOLERANCES,
        
        OEM_BUILD_MATRIX,
    	OEM_BUILD_MATRIX_DEBUG;
    	
        public static SecurityFunctionOld2 findByName(String name) {
            for (SecurityFunctionOld2 securityFunction : values())
                if (securityFunction.name().equals(name)) return securityFunction;
            return null;
        }

    }
}