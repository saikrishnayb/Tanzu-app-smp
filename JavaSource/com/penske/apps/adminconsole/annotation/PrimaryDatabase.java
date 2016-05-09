package com.penske.apps.adminconsole.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Annotation used in our dao's to specify which SqlSessionFactory we want to assign it
*
* @MapperScan
* @author erik.munoz 600139451
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryDatabase
{}
