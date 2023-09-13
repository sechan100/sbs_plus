package org.sbsplus.security;


import org.springframework.security.core.AuthenticationException;

public class EmailNotAuthenticatedException extends AuthenticationException {
    /**
     * Create authentication exception.
     *
     * @param message the error message
     */
    public EmailNotAuthenticatedException(String message) {
        super(message);
    }
    
    public EmailNotAuthenticatedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
