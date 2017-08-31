package com.penske.apps.suppliermgmt.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.penske.apps.adminconsole.annotation.SmcSecurity;
import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;
import com.penske.apps.adminconsole.annotation.VendorAllowed;
import com.penske.apps.adminconsole.exceptions.UnauthorizedSecurityFunctionException;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.UserContext;

/**
 * Intercepter that checks to see if the method has a @{@link SmcSecurity} annotation and if it
 * does, check to see the user is authorized to view and continue the current request. if they are
 * not, they probably be hacking and the internet police will be called. In the meantime we will
 * just redirect the to an error page. No mercy for these lil hacker punks.
 * 
 * @author erik.munoz 600139451
 */
@Component
public class SmcSecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        VendorAllowed vendorAllowed = handlerMethod.getMethod().getAnnotation(VendorAllowed.class);
        SmcSecurity smcSecurity = handlerMethod.getMethod().getAnnotation(SmcSecurity.class);

        UserContext user = (UserContext) httpSession.getAttribute(ApplicationConstants.USER_MODEL);

        boolean isVendorUserAccesingPenskeOnly = !user.isVisibleToPenske() && vendorAllowed == null;
        if (isVendorUserAccesingPenskeOnly)
            throw new UnauthorizedSecurityFunctionException(httpServletRequest.getRequestURI());

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
            throw new UnauthorizedSecurityFunctionException(securityFunctions);

        return true;
    }
}
