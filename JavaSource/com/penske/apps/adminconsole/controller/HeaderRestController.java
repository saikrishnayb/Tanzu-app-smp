package com.penske.apps.adminconsole.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.penske.apps.adminconsole.service.HeaderService;
@Controller
public class HeaderRestController {

    private static final Logger LOGGER = Logger.getLogger(ComponentsRestController.class);

    @Autowired
    private HeaderService headerService;

    // TODO SMCSEC is this even used?????
    @RequestMapping("get-role-modal-content")
    public ModelAndView getRoleModalContent(@RequestParam("roleId") int roleId, @RequestParam("roleName") String roleName){

        LOGGER.error("getRoleModalContent is used!!!! :)");
        boolean isHeaderRoleInfo = true;

        ModelAndView mav = new ModelAndView("/jsp-fragment/admin-console/security/permissions-accordion");

        mav.addObject("tabPermissionsMap", headerService.getRoleInformation(roleId));
        mav.addObject("isHeaderRoleInfo", isHeaderRoleInfo);
        mav.addObject("roleName", roleName);

        return mav;
    }

}
