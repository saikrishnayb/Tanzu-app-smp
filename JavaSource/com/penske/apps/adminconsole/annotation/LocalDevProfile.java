package com.penske.apps.adminconsole.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
* Annotation used to specify what configuration files should only be used
* in a local development environment. This profile level is changed in Web.xml
* 
* @ActiveProfiles
* @author erik.munoz 600139451
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("LOCAL_DEV")
public @interface LocalDevProfile
{}
