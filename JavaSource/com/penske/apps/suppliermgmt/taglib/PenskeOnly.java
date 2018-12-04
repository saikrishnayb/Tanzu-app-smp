package com.penske.apps.suppliermgmt.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.util.SpringBeanHelper;

public class PenskeOnly extends BodyTagSupport {

    private static final long serialVersionUID = -8478400408868440996L;

    @Override
    public int doStartTag() throws JspException {

        UserContext userContext = SpringBeanHelper.getUserContext();

        if (userContext == null) return SKIP_BODY;

        boolean visibleToPenske = userContext.isVisibleToPenske();

        if (visibleToPenske)
            return EVAL_BODY_INCLUDE;
        else
            return SKIP_BODY;

    }
}
