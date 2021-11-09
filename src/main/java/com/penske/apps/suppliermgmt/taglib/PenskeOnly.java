package com.penske.apps.suppliermgmt.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.suppliermgmt.util.SpringBeanHelper;

public class PenskeOnly extends BodyTagSupport {

    private static final long serialVersionUID = -8478400408868440996L;

    @Override
    public int doStartTag() throws JspException {

        User user = SpringBeanHelper.getUser();

        if (user == null) return SKIP_BODY;

        if (user.isPenskeUser())
            return EVAL_BODY_INCLUDE;
        else
            return SKIP_BODY;

    }
}
