package com.penske.apps.adminconsole.annotation;

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
public @interface SmcSecurity {

    public SecurityFunction[] securityFunction();

    /**
     * Enum containing all the security functions pertaining to the SMCOP module. This is not an
     * exhaustive list.
     */
    public static enum SecurityFunction {

        ADMIN_CONSOLE_TAB,
        ALERT_MANAGEMENT,
        DYNAMIC_RULES_MANAGEMENT,
        GLOBAL_EXCEPTIONS_MANAGEMENT,
        MANAGE_CATEGORY,
        MANAGE_CATEGORY_ASSOCIATION,
        MANAGE_COMPONENT_OVERRIDE,
        MANAGE_COMPONENTS,
        MANAGE_DELAY,
        MANAGE_DELAY_REASONS,
        MANAGE_DELAY_TYPE,
        MANAGE_NOTIFICATIONS,
        MANAGE_ORG,
        MANAGE_ROLES,
        MANAGE_SUBJECTS,
        MANAGE_TC,
        MANAGE_TEMPLATE,
        MANAGE_USERS,
        MANAGE_VENDOR_TEMPLATES,
        MANAGE_VENDOR_USERS,
        MANAGE_VENDORS,
        SEARCH_TEMPLATES,
        UNIT_EXCEPTIONS_MANAGEMENT,
        UPLOAD_EXCEL,
        VENDOR_FILTER;

        public static SecurityFunction findByName(String name) {
            for (SecurityFunction securityFunction : values())
                if (securityFunction.name().equals(name)) return securityFunction;
            return null;
        }

    }
}
