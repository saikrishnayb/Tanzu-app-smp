package com.penske.apps.suppliermgmt.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.smccore.base.annotation.SmcSecurity;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.SecurityFunction;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.annotation.VendorAllowed;
import com.penske.apps.suppliermgmt.beans.SuppliermgmtSessionBean;

/**
 * Intercepter that checks to see if the method has a @{@link SmcSecurity} annotation and if it
 * does, check to see the user is authorized to view and continue the current request. if they are
 * not, they probably be hacking and the internet police will be called. In the meantime we will
 * just redirect the to an error page. No mercy for these lil hacker punks.
 * 
 * @author erik.munoz 600139451
 */
@Component
public class SmcSecurityInterceptor implements HandlerInterceptor{

    @Autowired
    private SuppliermgmtSessionBean sessionBean;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

    	if(!(handler instanceof HandlerMethod))
    		return true;
    	
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //During initial login, the user won't have a user object in session. The login URL is allowed for all users.
        if(StringUtils.endsWith(httpServletRequest.getRequestURI(), "/validate"))
            return true;
        if(StringUtils.endsWith(httpServletRequest.getRequestURI(), "/error"))
            return true;

        VendorAllowed vendorAllowed = handlerMethod.getMethod().getAnnotation(VendorAllowed.class);
        SmcSecurity smcSecurity = handlerMethod.getMethod().getAnnotation(SmcSecurity.class);
        String requestURI = httpServletRequest.getRequestURI();

        User user = sessionBean.getUser();

        boolean isVendorUserAccesingPenskeOnly = user.getUserType() != UserType.PENSKE && vendorAllowed == null;
        if (isVendorUserAccesingPenskeOnly)
            throw new UnauthorizedSecurityFunctionException(handlerMethod, requestURI);

        boolean noSecurityChecks = smcSecurity == null;
        if (noSecurityChecks) return true;

        SecurityFunction[] securityFunctions = smcSecurity.securityFunction();
        Set<SecurityFunction> userSecurityFunctions = user.getSecurityFunctions();

        boolean doesNotHaveSecurityAccess = true;
        for (SecurityFunction securityFunction : securityFunctions) {

            boolean hasSecurityClearance = userSecurityFunctions.contains(securityFunction);
            if (hasSecurityClearance) {
                doesNotHaveSecurityAccess = false;
                break;
            }
        }

        if (doesNotHaveSecurityAccess)
            throw new UnauthorizedSecurityFunctionException(securityFunctions, handlerMethod, requestURI);

        return true;
    }
}
