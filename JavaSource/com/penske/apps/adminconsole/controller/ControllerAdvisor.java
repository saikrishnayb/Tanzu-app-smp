package com.penske.apps.adminconsole.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
public class ControllerAdvisor {
	
	@Autowired
	private ServletContext servletContext;
	private static Logger logger = Logger.getLogger(ControllerAdvisor.class);
	
	@ExceptionHandler(Exception.class)
	public ModelAndView globalExceptionCatcher(Exception e, HttpServletRequest request){
		
		String pathInfo = request.getServletPath();
		String leftNavDirectory = StringUtils.substringBeforeLast(pathInfo,  "/");
		
		String realPath = servletContext.getRealPath("WEB-INF/jsp/jsp-fragment" + leftNavDirectory + "/left-nav.jsp");
		
		boolean sidebarExist = true;
		
		try{
			@SuppressWarnings({ "unused", "resource" })
			InputStream is = new FileInputStream(realPath);
		} catch(FileNotFoundException e1){
			sidebarExist = false;
		}
		e.printStackTrace();
		logger.error("exception in application>>>",e);
		ModelAndView mav = new ModelAndView("/error/error");
		mav.addObject("leftNavDirectory", leftNavDirectory);
		mav.addObject("sidebarExists", sidebarExist);
		return mav;
	}
}
