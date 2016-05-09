package com.penske.apps.adminconsole.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.annotation.DefaultController;

@DefaultController
public class WebErrorCodeController {

	private static Logger logger = Logger.getLogger(WebErrorCodeController.class);
	
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public ModelAndView getError404Page(HttpServletRequest request){
		
		String errorPageUserAccessed = request.getAttribute("javax.servlet.error.request_uri").toString();
		
		logger.error("404 Error code from user trying to access: " + errorPageUserAccessed);
		
		ModelAndView mav = new ModelAndView("/error/error");
		
		StringBuilder sb = new StringBuilder();
		sb.append("This is pretty embarrassing, the page you are looking for does not exist. ");
		sb.append("See if you can go back and try again.");
		
		String message = sb.toString();
		mav.addObject("errorMessage", message);
		return mav;
	}
}
