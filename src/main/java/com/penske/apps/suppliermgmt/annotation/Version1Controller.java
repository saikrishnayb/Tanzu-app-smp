package com.penske.apps.suppliermgmt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

/**
* Annotation to denote a legacy controller whose pages use the v1 page template.
* New controllers should not use this annotation
*/
@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface Version1Controller
{}
