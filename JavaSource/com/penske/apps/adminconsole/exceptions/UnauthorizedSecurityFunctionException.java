package com.penske.apps.adminconsole.exceptions;

import com.penske.apps.adminconsole.annotation.SmcSecurity.SecurityFunction;

/**
 * Custom runtime exception to be thrown when a user tries to access a method he or she does not
 * have access to.
 */
public class UnauthorizedSecurityFunctionException extends RuntimeException {

    private static final long serialVersionUID = 7693943444336944152L;

    private SecurityFunction[] securityFunctions;
    private String requestURI;

    public UnauthorizedSecurityFunctionException(SecurityFunction[] securityFunctions) {
        super();
        this.securityFunctions = securityFunctions;
    }

    public UnauthorizedSecurityFunctionException(String requestURI) {
        super();
        this.requestURI = requestURI;
    }

    public SecurityFunction[] getSecurityFunctions() {
        return securityFunctions;
    }

    public String getRequestURI() {
        return requestURI;
    }

}
