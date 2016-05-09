package com.penske.apps.adminconsole.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.penske.apps.adminconsole.util.SmcCustomNumberEditor;

@ControllerAdvice
public class GlobalControllerAdvisor {

	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(int.class, new SmcCustomNumberEditor(Integer.class));
	}
}
