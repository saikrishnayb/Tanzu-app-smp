package com.penske.apps.adminconsole.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

/**
* Annotation used for all the standard non ajax controllers. This is so we can
* split up our ControllerAdvice class to be able to have two ControllerAdvices to be able
* to handle exceptions differently.
*
* @MapperScan
* @author erik.munoz 600139451
*/
@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface DefaultController
{}
