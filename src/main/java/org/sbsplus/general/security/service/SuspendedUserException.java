package org.sbsplus.general.security.service;

import org.springframework.security.core.AuthenticationException;

public class SuspendedUserException extends AuthenticationException {

    public SuspendedUserException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message and
     * root cause.
     * @param msg the detail message
     * @param cause root cause
     */
    public SuspendedUserException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
