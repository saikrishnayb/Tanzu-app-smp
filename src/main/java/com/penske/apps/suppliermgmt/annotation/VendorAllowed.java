package com.penske.apps.suppliermgmt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.penske.apps.suppliermgmt.interceptor.SmcSecurityInterceptor;

/**
 * Annotation to be used on any controller method that allows for vendor users to access. This
 * should be picked up by the {@link SmcSecurityInterceptor}.
 * 
 * @author erik.munoz 600139451
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VendorAllowed {}
