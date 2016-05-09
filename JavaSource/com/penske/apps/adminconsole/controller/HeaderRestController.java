package com.penske.apps.adminconsole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.service.HeaderService;
@Controller
public class HeaderRestController {

	@Autowired
	private HeaderService headerService;

	@RequestMapping("get-role-modal-content")
	public ModelAndView getRoleModalContent(@RequestParam("roleId") int roleId, @RequestParam("roleName") String roleName){
		
		boolean isHeaderRoleInfo = true;
		
		ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/permissions-accordion");
		
		mav.addObject("tabPermissionsMap", headerService.getRoleInformation(roleId));
		mav.addObject("isHeaderRoleInfo", isHeaderRoleInfo);
		mav.addObject("roleName", roleName);
		
		return mav;
	}
	
}
