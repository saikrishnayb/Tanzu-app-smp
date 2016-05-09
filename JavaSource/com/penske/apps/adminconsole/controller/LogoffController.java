package com.penske.apps.adminconsole.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.penske.util.CPTBaseServlet;

/**
* Controller that handles all the logoff mapping and functionality
*
* @author erik.munoz 600139451
*/
@Controller
public class LogoffController {
	
	@RequestMapping(value = {"/exit", "/logoff"})
	public void loadWelcome(HttpServletRequest request, HttpServletResponse response, HttpSession session){ 	
	    
		CPTBaseServlet.logoff(request,response);
	}
	
}