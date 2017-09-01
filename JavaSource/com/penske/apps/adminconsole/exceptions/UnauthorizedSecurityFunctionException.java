package com.penske.apps.adminconsole.exceptions;

import org.springframework.web.method.HandlerMethod;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;

/**
 * Custom runtime exception to be thrown when a user tries to access a method he or she does not
 * have access to.
 */
public class UnauthorizedSecurityFunctionException extends RuntimeException {

    private static final long serialVersionUID = 7693943444336944152L;

    private SecurityFunction[] securityFunctions;
    private HandlerMethod handlerMethod;
    private String requestURI;

    public UnauthorizedSecurityFunctionException(SecurityFunction[] securityFunctions, HandlerMethod handlerMethod, String requestURI) {
        super();
        this.handlerMethod = handlerMethod;
        this.securityFunctions = securityFunctions;
        this.requestURI = requestURI;
    }

    public UnauthorizedSecurityFunctionException(HandlerMethod handlerMethod, String requestURI) {
        super();
        this.handlerMethod = handlerMethod;
        this.requestURI = requestURI;
    }

    public SecurityFunction[] getSecurityFunctions() {
        return securityFunctions;
    }

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
