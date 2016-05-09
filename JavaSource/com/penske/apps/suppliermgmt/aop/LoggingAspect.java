package com.penske.apps.suppliermgmt.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.penske.apps.suppliermgmt.common.constants.ApplicationConstants;

/**
 *****************************************************************************************************************
 * File Name     : LoggingAspect
 * Description   : Class for logging before and after methods for the given package 
 * Project       : SMC
 * Package       : com.penske.apps.smc.aop
 * Author        : 502299699
 * Date			 : Apr 204, 2015
 * Copyright (C) 2015 GE Penske Truck Leasing
 * Modifications :
 * --------------------------------------------------------------------------------------------------------------
 * Version  |   Date    |   Change Details
 * --------------------------------------------------------------------------------------------------------------
 *
 * ****************************************************************************************************************
 */

@Aspect
@Component
public class LoggingAspect {
	
private final static Logger LOGGER = Logger.getLogger(LoggingAspect.class);
	
	@Before(ApplicationConstants.EXE_FOR_SERVICE_PKG)
	public void logBeforeService(JoinPoint joinPoint) {
 
		LOGGER.info(ApplicationConstants.STARTING_METHOD+joinPoint.getSignature().getName());
	}
	
	
	@After(ApplicationConstants.EXE_FOR_SERVICE_PKG)
	public void logAfterService(JoinPoint joinPoint) {
 
		LOGGER.info(ApplicationConstants.ENDING_METHOD+joinPoint.getSignature().getName());
 
	}

}
